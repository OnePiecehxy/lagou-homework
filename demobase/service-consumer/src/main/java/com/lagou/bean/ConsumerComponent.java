package com.lagou.bean;

import com.lagou.service.HelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

@Component
public class ConsumerComponent {
    @Reference
    private HelloService helloService;

    public String sayHello(String name, int timeToWait){

        return helloService.sayHello(name, timeToWait);
    }
    public String sayHello2(String name, int timeToWait){

        return helloService.sayHello2(name, timeToWait);
    }
    public String sayHello3(String name, int timeToWait){

        return helloService.sayHello3(name, timeToWait);
    }
}
