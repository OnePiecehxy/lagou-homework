package com.lagou.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test1 {
    public static void main(String[] args) throws SQLException {

        Connection conn = ConnectionUtils.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement("select * from tb_resume");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            System.out.println(resultSet.getString("name"));
        }
        resultSet.close();
        preparedStatement.close();
        conn.close();
    }
}
