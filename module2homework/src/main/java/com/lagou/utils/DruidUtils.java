package com.lagou.utils;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidUtils {

    private static DruidDataSource dataSource = new DruidDataSource();

    static {
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mysql");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
    }
    public static DruidDataSource getInstance(){
        return dataSource;
    }
}
