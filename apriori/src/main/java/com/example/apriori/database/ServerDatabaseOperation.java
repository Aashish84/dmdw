package com.example.apriori.database;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Component
public class ServerDatabaseOperation {
    private Connection getMainConnection(){
        try {
            return HikariCPDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String executeQueryString(String query , Map<Integer , Object> params){
        Connection con = getMainConnection();
        String resultString = null;
        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            if(params!=null){
                for(Map.Entry<Integer,Object> entry : params.entrySet()){
                    pstmt.setObject(entry.getKey() , entry.getValue());
                }
            }
            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()){
                resultString = resultSet.getString(1);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultString;
    }

}
