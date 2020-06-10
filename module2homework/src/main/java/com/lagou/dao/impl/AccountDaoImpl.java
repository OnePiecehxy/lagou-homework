package com.lagou.dao.impl;

import com.lagou.annotation.Autowired;
import com.lagou.annotation.Repository;
import com.lagou.bean.Account;
import com.lagou.dao.AccountDao;
import com.lagou.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
@Repository
public class AccountDaoImpl implements AccountDao {
//    @Autowired
//    private ConnectionUtils connectionUtils;


    @Override
    public void updateAccount(Account account) throws SQLException {

        Connection conn = ConnectionUtils.getCurrentThreadConnection();
//        Connection conn = connectionUtils.getCurrentThreadConnection();
        PreparedStatement preparedStatement = conn.prepareStatement("update account set money=? where card_no=?");
        preparedStatement.setInt(1, account.getMoney());
        preparedStatement.setString(2, account.getCardNo());
        preparedStatement.execute();
        preparedStatement.close();
//        conn.close();

    }

    @Override
    public Account queryAccountByCardNo(String cardNo) throws SQLException {
//        Connection conn = ConnectionUtils.getConnection();
        Connection conn = ConnectionUtils.getCurrentThreadConnection();
        Account account = new Account();
        PreparedStatement preparedStatement = conn.prepareStatement("select * from account where card_no = ?");
        preparedStatement.setString(1, cardNo);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            account.setCardNo(resultSet.getString("card_no"));
            account.setUsername(resultSet.getString("username"));
            account.setMoney(resultSet.getInt("money"));
        }
        resultSet.close();
        preparedStatement.close();
//        conn.close();
        return account;
    }
}
