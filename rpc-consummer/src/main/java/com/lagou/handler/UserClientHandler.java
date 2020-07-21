package com.lagou.handler;

import com.lagou.pojo.RpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

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
}
