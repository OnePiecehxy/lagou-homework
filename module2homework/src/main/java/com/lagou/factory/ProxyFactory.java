package com.lagou.factory;

import com.lagou.utils.TransactionManager;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
//@Component("proxyFactory")
public class ProxyFactory {

//    @Autowired
//    private TransactionManager transactionManager;


    public Object getProxy(Object obj){
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object o = null;
                try{
                    TransactionManager.getInstance().beginTransaction();
                    o = method.invoke(obj,args);
                    TransactionManager.getInstance().commit();
                }catch (Exception e){
                    e.printStackTrace();
                    TransactionManager.getInstance().rollBack();
                    throw e;
                }
                return o;
            }
        });
    }

    public Object getCGlibProxy(Object obj){
        return Enhancer.create(obj.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object result = null;
                try{
                    TransactionManager.getInstance().beginTransaction();
                    result = method.invoke(obj, objects);
                    TransactionManager.getInstance().commit();
                }catch (Exception e){
                    e.printStackTrace();
                    TransactionManager.getInstance().rollBack();
                    throw e;
                }
                return o;

            }
        });
    }
}
