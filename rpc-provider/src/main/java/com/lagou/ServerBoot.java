package com.lagou;

import com.lagou.service.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ServerBoot {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ServerBoot.class, args);
        UserServiceImpl.startService("127.0.0.1",8999);
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(ServerBoot.class);
//    }
}
