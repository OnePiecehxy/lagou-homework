package com.lagou.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
//@Component
public class TransactionManager {
//    @Autowired
//    private ConnectionUtils connectionUtils;

    private static TransactionManager transactionManager = new TransactionManager();
//    private TransactionManager(){
//
//    }
    public static TransactionManager getInstance(){
        return transactionManager;
    }

    public void beginTransaction() throws SQLException {
        ConnectionUtils.getCurrentThreadConnection().setAutoCommit(false);
    }

    public void commit() throws SQLException {
        ConnectionUtils.getCurrentThreadConnection().commit();
    }

    public void rollBack() throws SQLException {
        ConnectionUtils.getCurrentThreadConnection().rollback();
    }
}
