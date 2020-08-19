package com.lagou;

import com.lagou.bean.ConsumerComponent;
import com.lagou.service.HelloService;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class XmlConsumerMain {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consummer.xml");
        context.start();
        HelloService bean = (HelloService) context.getBean("helloService");
        while (true){
            System.in.read();
            String result = bean.sayHello("world", 100);
            Future<Object> future = RpcContext.getContext().getFuture();
            System.out.println("result=" + result);
            System.out.println("futrue result=" + future.get());


        }
    }



}
