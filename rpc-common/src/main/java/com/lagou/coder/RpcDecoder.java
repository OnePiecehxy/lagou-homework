package com.lagou.coder;

import com.lagou.pojo.RpcRequest;
import com.lagou.serializable.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class RpcDecoder extends ByteToMessageDecoder {
    private Class<?> clazz;
    private Serializer serializer;

    public RpcDecoder(Class<?> clazz, Serializer serializer) {
        this.clazz = clazz;
        this.serializer = serializer;
    }

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        Object obj = serializer.deserialize(RpcRequest.class, bytes);
        list.add(obj);

    }
}
