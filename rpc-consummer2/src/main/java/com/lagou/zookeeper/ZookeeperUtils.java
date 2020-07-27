package com.lagou.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZookeeperUtils {
    public static CuratorFramework client;

    public static CuratorFramework getClient(String connectStr){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.builder()
                .connectString(connectStr)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(3000)
                .retryPolicy(retryPolicy)
                .namespace("rpc")
                .build();
        client.start();
        return client;
    }
}
