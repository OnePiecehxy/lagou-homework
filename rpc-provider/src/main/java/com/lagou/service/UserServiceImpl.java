package com.lagou.service;

import com.lagou.coder.RpcDecoder;
import com.lagou.handler.UserServiceHandler;
import com.lagou.pojo.RpcRequest;
import com.lagou.serializable.JsonSerializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import com.lagou.service.common.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService{
    public String sayHello(String msg) {
        System.out.println(msg);
        return "服务器返回数据：" + "success";
    }
    public static void startService(String ip, Integer port) throws InterruptedException {

        //创建两个线程池对象
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        //创建服务器的启动引导对象
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //配置启动引导对象
        serverBootstrap.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new RpcDecoder(RpcRequest.class, new JsonSerializer()));
                        pipeline.addLast(new UserServiceHandler());
                    }
                });


        //绑定端口
        serverBootstrap.bind(8999).sync();
    }

}
