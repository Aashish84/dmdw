package com.example.apriori.service.serviceimpl;

import com.example.apriori.database.ServerDatabaseOperation;
import com.example.apriori.service.AprioriService;

import java.util.*;

public class AprioriServiceImpl implements AprioriService {
    @Override
    public Set<String> getPossibleAssociation() {
        Set<String> set = new HashSet<>();
        String query = "with tmp as (select distinct itemID from apriori order by itemID asc)\n" +
                "select group_concat(\"(\" , a.itemID , b.itemID , \")\") all_assotiation from tmp as a\n" +
                "inner join tmp as b\n" +
                "where a.itemID != b.itemID;";

        ServerDatabaseOperation sdo = new ServerDatabaseOperation();
        String[] split = sdo.executeQueryString(query , null).split(",");
        for (String s : split) {
            char[] chars = s.substring(1, s.length() - 1).toCharArray();
            Arrays.sort(chars);
            String str = "(" + new String(chars) + ")";
            if (!set.contains(str)) {
                set.add(str);
            }
        }

//        System.out.println("++++++++");
//        for (String s : set) {
//            System.out.println(s);
//        }
        return set;
    }

    @Override
    public void getSupportTable() {
        Set<String> set = getPossibleAssociation();
        Map<String , Integer> map = new HashMap<>();
        String query = "with a as (select transactionID from apriori where itemID = ?) , \n" +
                "b as (select transactionID from apriori where itemID = ?) \n" +
                "select \n" +
                "\tcount(a.transactionID) frequency\n" +
                "from a\n" +
                "inner join b\n" +
                "where a.transactionID = b.transactionID;";

        String totalQuery = "select count( distinct transactionID) count from apriori";

        System.out.println("++++++++");
        for (String s : set) {
            String substring = s.substring(1, s.length() - 1);
            char[] chars = substring.toCharArray();
            Map<Integer , Object> tmpMap = new HashMap<>();
            tmpMap.put(1 , Character.toString(chars[0]));
            tmpMap.put(2 , Character.toString(chars[1]));

            ServerDatabaseOperation sdo = new ServerDatabaseOperation();
            String resultString = sdo.executeQueryString(query, tmpMap);
            String total = sdo.executeQueryString(totalQuery , null);

            double res = ((Integer.parseInt(resultString))*100 ) / (Integer.parseInt(total));
            System.out.println(s +"->" +Integer.parseInt(resultString)+"/"+  Integer.parseInt(total) +" = "+ res +"%");
        }
    }
}
