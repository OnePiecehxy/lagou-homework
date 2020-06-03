package com.lagou.sqlSession;

import com.lagou.pojo.BoundSql;
import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.utils.GenericTokenParser;
import com.lagou.utils.ParameterMapping;
import com.lagou.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimpleExecutor implements Executor {
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        Connection connection = configuration.getDataSource().getConnection();
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        //获取参数类型信息
        String parameterType = mappedStatement.getParameterType();
        String resultType = mappedStatement.getResultType();
        //使用反射获取参数属性
        Class<?> classType = getClassType(parameterType);
        Class<?> resultTypeClass = getClassType(resultType);

        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        //设置参数
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            Field declaredField = classType.getDeclaredField(parameterMapping.getContent());
            //设置暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i + 1, o);


        }

        //执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        ArrayList<Object> resultList = new ArrayList<Object>();
        //封装返回结果集
        while (resultSet.next()){
            Object resultTypeObject = resultTypeClass.newInstance();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);

                Object value = resultSet.getObject(columnName);
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(resultTypeObject, value);
            }
            resultList.add(resultTypeObject);
        }
        return (List<E>) resultList;

    }

    public boolean doModify(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Connection connection = configuration.getDataSource().getConnection();
        String id = mappedStatement.getId();
        String parameterType = mappedStatement.getParameterType();
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        Class<?> classType = getClassType(parameterType);
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            Field declaredField = classType.getDeclaredField(parameterMapping.getContent());
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i + 1, o);

        }
        boolean execute = preparedStatement.execute();
        return execute;
    }
    private  Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if (parameterType != null){
            Class<?> aClass = Class.forName(parameterType);
            return aClass;
        }
        return null;
    }

    private BoundSql getBoundSql(String sql) {
        //标记处理类，配置标记解析器完成对占位符的解析
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        //解析出来的sql
        String parseSql = genericTokenParser.parse(sql);
        //解析#{}中的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(parseSql, parameterMappings);
        return boundSql;


    }
}
