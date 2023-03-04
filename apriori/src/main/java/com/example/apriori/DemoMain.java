package com.example.apriori;

import com.example.apriori.service.AprioriService;
import com.example.apriori.service.serviceimpl.AprioriServiceImpl;

public class DemoMain {

    public static void main(String[] args) {
        AprioriService ob = new AprioriServiceImpl();
        ob.getSupportTable();
    }
}
