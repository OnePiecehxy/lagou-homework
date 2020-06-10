package com.lagou.test;

import com.lagou.annotation.Service;
import com.lagou.listener.MyApplicationContextListener;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Test1 {
    @Test
    public void test1() throws Exception {

        Class<?> aClass = Class.forName("com.lagou.service.impl.TransferServiceImpl");
        Service annotation = aClass.getAnnotation(Service.class);
        Field[] fields = aClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i].getName());
            System.out.println(fields[i].getType().getName());
            System.out.println(fields[i].getClass().getName());
            Annotation[] declaredAnnotations = fields[i].getDeclaredAnnotations();
            for (int j = 0; j < declaredAnnotations.length; j++) {
                System.out.println(declaredAnnotations[j].toString());
                System.out.println(declaredAnnotations[j].annotationType().getName());
            }
        }

    }
    @Test
    public void test2() throws ClassNotFoundException {
        Class<?> aClass = Class.forName("com.lagou.dao.impl.AccountDaoImpl");
        System.out.println(aClass.getSuperclass().getName());
        System.out.println(aClass.getName());
        Class<?>[] interfaces = aClass.getInterfaces();
        for (Class<?> anInterface : interfaces) {
            System.out.println(anInterface);
            System.out.println(anInterface.getName());
        }
    }

    @Test
    public void test3() throws ClassNotFoundException {
        Class<?> aClass = Class.forName("com.lagou.dao.impl.AccountDaoImpl");
        System.out.println(aClass.getSuperclass().getName());
        System.out.println(aClass.getName());
        Class<?>[] interfaces = aClass.getInterfaces();
        for (Class<?> anInterface : interfaces) {
            System.out.println(anInterface);
            System.out.println(anInterface.getName());
        }
    }

    @Test
    public void test4() throws ClassNotFoundException {
        Class<?> aClass = Class.forName("com.lagou.servlet.TransferServlet");
        System.out.println(aClass.getSuperclass().getName());
        System.out.println(aClass.getName());

    }
}
