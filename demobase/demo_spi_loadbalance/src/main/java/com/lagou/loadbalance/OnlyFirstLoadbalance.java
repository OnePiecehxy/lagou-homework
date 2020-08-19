package com.lagou.loadbalance;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.LoadBalance;

import java.util.List;

public class OnlyFirstLoadbalance implements LoadBalance {
    @Override
    public <T> Invoker<T> select(List<Invoker<T>> list, URL url, Invocation invocation) throws RpcException {
        return list.stream().sorted((i1,i2) -> {
            final int ipCompare = i1.getUrl().getIp().compareTo(i2.getUrl().getIp());
            if (ipCompare == 0){
                return Integer.compare(i1.getUrl().getPort(), i2.getUrl().getPort());
            }
            return ipCompare;
        }).findFirst().get();
    }
}
