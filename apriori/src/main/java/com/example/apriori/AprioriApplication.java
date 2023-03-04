package com.example.apriori;

import com.example.apriori.service.AprioriService;
import com.example.apriori.service.serviceimpl.AprioriServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AprioriApplication {

    public static void main(String[] args) {
        SpringApplication.run(AprioriApplication.class, args);
        AprioriService ob = new AprioriServiceImpl();
        ob.getSupportTable();
    }

}
