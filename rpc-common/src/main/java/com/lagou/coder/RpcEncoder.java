package com.lagou.coder;

import com.lagou.serializable.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

public class RpcEncoder extends MessageToByteEncoder {
    private Class<?> clazz;
    private Serializer serializer;

    public RpcEncoder(Class<?> clazz, Serializer serializer) {
        this.clazz = clazz;
        this.serializer = serializer;
    }

    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {

        if (clazz != null && clazz.isInstance(o)){
            byte[] bytes = serializer.serialize(o);
            byteBuf.writeInt(bytes.length);
            byteBuf.writeBytes(bytes);
        }
    }
}
