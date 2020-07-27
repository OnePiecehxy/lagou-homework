package com.lagou.boot;

import com.lagou.client.RPCConsummer;
import com.lagou.pojo.RpcRequest;
import com.lagou.service.common.IUserService;
import com.lagou.zookeeper.ZookeeperUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.util.Date;
import java.util.List;

public class ConsummerBoot {
//    public static CuratorFramework client;

    public static void method() throws InterruptedException {
        IUserService service = (IUserService) RPCConsummer.createProxy(IUserService.class);

        while (true) {
            String result = service.sayHello("Cosumer method");
            Thread.sleep(2000);
        }
    }
    public static void main(String[] args) throws Exception {

        chooseServer();
        IUserService service = (IUserService) RPCConsummer.createProxy(IUserService.class);

        while (true) {
            String result = service.sayHello("Cosumer method");
            System.out.println(result);
            Thread.sleep(2000);
        }



    }
    public static void chooseServer() throws Exception {
        CuratorFramework client = ZookeeperUtils.getClient("192.168.191.129:2181");


        List<String> providerMsgList = client.getChildren().forPath("/");
        String provider1Path = "/rpc-provider1";
        String provider2Path = "/rpc-provider2";
        String connectionStr = "";
        Stat stat1 = client.checkExists().forPath(provider1Path);
        Stat stat2 = client.checkExists().forPath(provider2Path);
        if (stat1 != null && stat2 != null){
            byte[] provider1Bytes = client.getData().forPath("/rpc-provider1-execution-time");
            byte[] provider2Bytes = client.getData().forPath("/rpc-provider2-execution-time");
            String provider1ExecutionTime = new String(provider1Bytes);
            String provider2ExecutionTime = new String(provider2Bytes);
            Long provider1Time = Long.parseLong(provider1ExecutionTime);
            Long provider2Time = Long.parseLong(provider2ExecutionTime);
            Long currentTime = new Date().getTime();
            if ((currentTime - provider1Time) > 5){
                client.setData().forPath("/rpc-provider1-execution-time", "0".getBytes());
            }
            if ((currentTime - provider2Time) > 5){
                client.setData().forPath("/rpc-provider2-execution-time", "0".getBytes());
            }

            if (provider1Time > provider2Time){
                byte[] bytes = client.getData().forPath("/rpc-provider2");
                connectionStr = new String(bytes);
            }
            if (provider1Time < provider2Time){
                byte[] bytes = client.getData().forPath("/rpc-provider1");
                connectionStr = new String(bytes);
            }
            if (provider1Time == provider2Time){
                int providerNum = (int) (provider2Time%2) + 1;
                byte[] bytes = client.getData().forPath("/rpc-provider" + providerNum);
                connectionStr = new String(bytes);

            }
        }else {
            if (stat1 != null){
                byte[] bytes = client.getData().forPath("/rpc-provider1");
                connectionStr = new String(bytes);
            }
            if (stat2 != null){
                byte[] bytes = client.getData().forPath("/rpc-provider2");
                connectionStr = new String(bytes);
            }
        }





        RPCConsummer.initClient(connectionStr);

    }



}
