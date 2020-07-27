package com.lagou;

import com.lagou.service.UserServiceImpl;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerBootProvider1 {
    public static CuratorFramework client;
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ServerBootProvider1.class, args);

        UserServiceImpl.startService("127.0.0.1",8999);
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.builder()
                .connectString("192.168.191.129:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(3000)
                .retryPolicy(retryPolicy)
                .namespace("rpc")
                .build();
        client.start();
        client.create().withMode(CreateMode.EPHEMERAL).forPath("/rpc-provider1", "127.0.0.1:8999".getBytes());
        client.create().withMode(CreateMode.EPHEMERAL).forPath("/rpc-provider1-execution-time", "0".getBytes());
        System.out.println("服务节点1创建成功");

    }



}
