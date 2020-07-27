package com.lagou.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {
    private static String url;
    private static String userName;
    private static String password;
    private static String driverName;
    private static DruidDataSource dataSource = new DruidDataSource();
    static {
        try {
            getConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }

        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverName);
    }
    public static Connection getConnection() throws SQLException {

        Connection connection = dataSource.getConnection();
        return connection;

    }
    public static void getConfig() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("192.168.191.129:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(3000)
                .retryPolicy(retryPolicy)
                .namespace("config")
                .build();
        client.start();
        byte[] bytes = client.getData().forPath("/url");
        byte[] bytes1 = client.getData().forPath("/username");
        byte[] bytes2 = client.getData().forPath("/password");
        byte[] bytes3 = client.getData().forPath("/driver-name");
        url = new String(bytes);
        userName = new String(bytes1);
        password = new String(bytes2);
        driverName = new String(bytes3);
    }

}
