package com.lagou.dao;

import com.lagou.pojo.User;

import java.util.List;

public interface IUserDao {

    public User findByCondition(Object... params);

    public List<User> findAll();

    public void insertUser(User user);

    public void deleteUser(User user);

    public void updateUser(User user);

}
