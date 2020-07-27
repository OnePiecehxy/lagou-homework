package com.lagou.handler;

import com.lagou.boot.ConsummerBoot;
import com.lagou.client.RPCConsummer;
import com.lagou.pojo.RpcRequest;
import com.lagou.zookeeper.ZookeeperUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.curator.framework.CuratorFramework;

import java.util.List;
import java.util.concurrent.Callable;

public class UserClientHandler extends ChannelInboundHandlerAdapter implements Callable {
    private ChannelHandlerContext context;
    private Object param;
    private String result;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        System.out.println(result);
        notify();
    }

    public synchronized Object call() throws Exception {
        context.writeAndFlush(param);
        wait();
        return result;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务器断开连接");
        ConsummerBoot.chooseServer();
        ConsummerBoot.method();


    }
}
