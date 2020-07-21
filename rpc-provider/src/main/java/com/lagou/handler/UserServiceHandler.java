package com.lagou.handler;

import com.lagou.pojo.RpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.BeansException;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
@Component
public class UserServiceHandler extends ChannelInboundHandlerAdapter implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        UserServiceHandler.context = applicationContext;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        RpcRequest rpcRequest = (RpcRequest) msg;
        Object object = handler(rpcRequest);
        ctx.writeAndFlush("success");
    }

    private Object handler(RpcRequest rpcRequest) throws ClassNotFoundException, InvocationTargetException {
        Class<?> aClass = Class.forName(rpcRequest.getClassName());
        Object bean = context.getBean(aClass);
        String methodName = rpcRequest.getMethodName();
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] parameters = rpcRequest.getParameters();

        FastClass fastClass = FastClass.create(aClass);
        FastMethod fastMethod = fastClass.getMethod(methodName, parameterTypes);
        return fastMethod.invoke(bean, parameters);
    }

}
