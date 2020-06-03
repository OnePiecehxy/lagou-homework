package com.lagou.test;

import com.lagou.dao.IUserDao;
import com.lagou.io.Resources;
import com.lagou.pojo.User;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionConfigBuilder;
import com.lagou.sqlSession.SqlSessionFactory;
import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;

public class IPersistenceTest {

    private SqlSession sqlSession;
    @Test
    public void test() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionConfigBuilder sqlSessionConfigBuilder = new SqlSessionConfigBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionConfigBuilder.build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(1);
        user.setUsername("张三");
//        User userSelect = sqlSession.selectOne("user.selectOne", user);
//        System.out.println("@@@@@@@==" + userSelect.toString());
//        List<User> users = sqlSession.selectList("user.selectList", user);
//        for (User user1 : users) {
//            System.out.println("@@@@@@@=="  + user1);
//        }
        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);
//        User user2 = iUserDao.findByCondition(user);
//        System.out.println(user2t.toString());
        List<User> all = iUserDao.findAll();
        for (User user1 : all) {
            System.out.println(user1.toString());
        }


    }
    @Test
    public void testConnection() throws ClassNotFoundException, SQLException {
//        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/qcwebtool", "root", "123456");
        String sql = "select * from user";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            System.out.println(resultSet.getString("username"));
        }
    }

    @Before
    public void testBefore() throws PropertyVetoException, DocumentException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionConfigBuilder sqlSessionConfigBuilder = new SqlSessionConfigBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionConfigBuilder.build(resourceAsStream);
        sqlSession = sqlSessionFactory.openSession();
    }


    @Test
    public void testModify() throws Exception {

        User user = new User();
        user.setId(3);
        user.setUsername("张三");
        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);
        iUserDao.insertUser(user);
//        iUserDao.updateUser(user);
//        iUserDao.deleteUser(user);
    }
}

