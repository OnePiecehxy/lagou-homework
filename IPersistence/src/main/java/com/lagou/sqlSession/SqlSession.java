package com.lagou.sqlSession;

import com.lagou.pojo.Configuration;

import java.util.List;

public interface SqlSession {
    public <E> List<E> selectList(String statementId,Object... parmas) throws Exception;

    public <T> T selectOne(String statementId,Object... params) throws Exception;

    //为Dao接口生成代理实现类
    public <T> T getMapper(Class<?> mapperClass);
}
