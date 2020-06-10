package com.lagou.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.lagou.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
//@Component
public class ConnectionUtils {


    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public static Connection getCurrentThreadConnection() throws SQLException {
        DataSource dataSource = DruidUtils.getInstance();
        Connection conn = threadLocal.get();
        if (conn == null) {
            try {
                conn = dataSource.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            threadLocal.set(conn);
        }
        return threadLocal.get();
    }

}
