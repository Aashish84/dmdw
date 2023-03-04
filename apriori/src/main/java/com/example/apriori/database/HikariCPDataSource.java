package com.example.apriori.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariCPDataSource {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    static{
        config.setJdbcUrl("jdbc:mysql://localhost:3306/dmdw");
        config.setUsername("root");
        config.setPassword("123root!@#");
        config.setMaximumPoolSize(1);
        ds = new HikariDataSource(config);
    }
    private HikariCPDataSource(){}
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
