package com.lagou.service.impl;

import com.lagou.service.HelloService;
import org.apache.dubbo.config.annotation.Service;

import java.util.Random;

@Service
public class HelloServiceImpl implements HelloService {
    private Random random = new Random();
    @Override
    public String sayHello(String name, int timeToWait) {
        try {
            Thread.sleep(random.nextInt(timeToWait));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello " + name;
    }

    @Override
    public String sayHello2(String name, int timeToWait) {
        try {
            Thread.sleep(random.nextInt(timeToWait));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello2 " + name;
    }

    @Override
    public String sayHello3(String name, int timeToWait) {
        try {
            Thread.sleep(random.nextInt(timeToWait));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello3 " + name;
    }
}
