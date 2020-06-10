package com.lagou.service;

import com.lagou.bean.Account;

import java.sql.SQLException;

public interface TransferService {

    public void transfer(String fromCardNo, String toCardNo, int money) throws SQLException, Exception;
}
