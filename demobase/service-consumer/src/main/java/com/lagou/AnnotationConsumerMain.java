package com.lagou;

import com.lagou.bean.ConsumerComponent;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AnnotationConsumerMain {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfigration.class);
        context.start();
        ConsumerComponent bean = context.getBean(ConsumerComponent.class);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        while (true){
//            executorService.submit(new Runnable() {
//                @Override
//                public void run() {
//                    String result = bean.sayHello("world", 10);
//                    String result2 = bean.sayHello2("world", 10);
//                    String result3 = bean.sayHello3("world", 10);
//                }
//
//            });

            String result = bean.sayHello("world", 100);
            String result2 = bean.sayHello2("world", 100);
            String result3 = bean.sayHello3("world", 100);
//            System.out.println("result3=" + result3 );


        }
    }


    @Configuration
    @PropertySource("classpath:/dubbo-sonsumer.properties")
    @ComponentScan(basePackages = "com.lagou.bean")
    @EnableDubbo
    static class ConsumerConfigration{

    }
}
