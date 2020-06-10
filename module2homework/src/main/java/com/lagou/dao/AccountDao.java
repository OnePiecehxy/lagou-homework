package com.lagou.dao;

import com.lagou.bean.Account;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

public interface AccountDao {

    public void updateAccount(Account account) throws SQLException;

    public Account queryAccountByCardNo(String cardNo) throws SQLException;
}
