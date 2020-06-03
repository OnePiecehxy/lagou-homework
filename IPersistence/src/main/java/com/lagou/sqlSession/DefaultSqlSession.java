package com.lagou.sqlSession;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;

import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;
    public DefaultSqlSession(Configuration configuration){
        this.configuration = configuration;
    }
    public <E> List<E> selectList(String statementId, Object... parmas) throws Exception {
        Map<String, MappedStatement> mappedStatementMap = configuration.getMappedStatementMap();
        Executor executor = new SimpleExecutor();
        List<Object> list = executor.query(configuration, mappedStatementMap.get(statementId), parmas);
        return (List<E>) list;
    }


    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> objects = selectList(statementId, params);
        if (objects.size() == 1){
            return (T) objects.get(0);
        }else{
            throw  new RuntimeException("未找到结果或者结果集过多");
        }
    }

    public boolean modify(String statementId, Object... params) throws ClassNotFoundException, SQLException, IllegalAccessException, NoSuchFieldException {
        Map<String, MappedStatement> mappedStatementMap = configuration.getMappedStatementMap();
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        boolean b = simpleExecutor.doModify(configuration, mappedStatementMap.get(statementId), params);
        return b;
    }
//    public boolean insert(String statementId, Object... params) throws ClassNotFoundException, SQLException, IllegalAccessException, NoSuchFieldException {
//        Map<String, MappedStatement> mappedStatementMap = configuration.getMappedStatementMap();
//        SimpleExecutor simpleExecutor = new SimpleExecutor();
//        boolean b = simpleExecutor.doInsert(configuration, mappedStatementMap.get(statementId), params);
//        return b;
//    }
//
//    public boolean update(String statementId, Object... params) throws ClassNotFoundException, SQLException, IllegalAccessException, NoSuchFieldException {
//        Map<String, MappedStatement> mappedStatementMap = configuration.getMappedStatementMap();
//        SimpleExecutor simpleExecutor = new SimpleExecutor();
//        boolean b = simpleExecutor.doUpdate(configuration, mappedStatementMap.get(statementId), params);
//        return b;
//    }
//
//    public boolean delete(String statementId, Object... params) throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException {
//        Map<String, MappedStatement> mappedStatementMap = configuration.getMappedStatementMap();
//        SimpleExecutor simpleExecutor = new SimpleExecutor();
//        boolean b = simpleExecutor.doDelete(configuration, mappedStatementMap.get(statementId), params);
//        return b;
//    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //准备statementId
                String name = method.getName();
                String className = method.getDeclaringClass().getName();
                String statementId = className + "." + name;

                Map<String, MappedStatement> mappedStatementMap = configuration.getMappedStatementMap();
                MappedStatement mappedStatement = mappedStatementMap.get(statementId);
                String sql = mappedStatement.getSql();
                //获取被调用方法的返回类型
                Type genericReturnType = method.getGenericReturnType();
                //判断是否进行了参数类型泛型化
                if (sql.indexOf("select") == 0){
                    if (genericReturnType instanceof ParameterizedType){
                        List<Object> objects = selectList(statementId);
                        return objects;
                    }else{
                        return selectOne(statementId,args);
                    }
                }else {
                    boolean flag = modify(statementId, args);
                    return flag;
                }
            }
        });
        return (T) proxyInstance;
    }
}
