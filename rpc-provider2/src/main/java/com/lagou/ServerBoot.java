package com.lagou;

import com.lagou.service.UserServiceImpl;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ServerBoot {
    public static CuratorFramework client;
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ServerBoot.class, args);
        UserServiceImpl.startService("127.0.0.1",9999);

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.builder()
                .connectString("192.168.191.129:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(3000)
                .retryPolicy(retryPolicy)
                .namespace("rpc")
                .build();
        client.start();
        client.create().withMode(CreateMode.EPHEMERAL).forPath("/rpc-provider2", "127.0.0.1:9999".getBytes());
        client.create().withMode(CreateMode.EPHEMERAL).forPath("/rpc-provider2-execution-time", "0".getBytes());
        System.out.println("服务节点2创建成功");
    }

}
