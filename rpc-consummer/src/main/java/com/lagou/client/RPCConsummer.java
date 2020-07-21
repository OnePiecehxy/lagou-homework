package com.lagou.client;

import com.alibaba.fastjson.JSONArray;
import com.lagou.coder.RpcEncoder;
import com.lagou.handler.UserClientHandler;
import com.lagou.pojo.RpcRequest;
import com.lagou.serializable.JsonSerializer;
import com.lagou.service.common.IUserService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RPCConsummer {

    private static ExecutorService executorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static UserClientHandler userClientHandler;

    public static void initClient() throws InterruptedException {
        userClientHandler = new UserClientHandler();

        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new RpcEncoder(RpcRequest.class, new JsonSerializer()));
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(userClientHandler);
                    }
                });
        bootstrap.connect("127.0.0.1", 8999).sync();

    }

    public static Object createProxy(Class<?> serviceClass){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serviceClass}, new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (userClientHandler == null){
                            initClient();
                        }
                        RpcRequest rpcRequest = new RpcRequest();
                        rpcRequest.setRequestId(UUID.randomUUID().toString());
                        rpcRequest.setClassName(method.getDeclaringClass().getName());
                        rpcRequest.setMethodName(method.getName());
                        rpcRequest.setParameterTypes(method.getParameterTypes());
                        rpcRequest.setParameters(args);
                        userClientHandler.setParam(rpcRequest);
                        Object result = executorService.submit(userClientHandler).get();
                        return result;
                    }
                });
    }


}
