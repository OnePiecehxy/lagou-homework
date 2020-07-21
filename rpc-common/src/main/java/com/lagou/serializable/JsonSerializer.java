package com.lagou.serializable;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

public class JsonSerializer implements Serializer {
    public byte[] serialize(Object object) throws IOException {
        byte[] bytes = JSON.toJSONBytes(object);
        return bytes;
    }

    public <T> T deserialize(Class<?> clazz, byte[] bytes) throws IOException {
        Object object = JSON.parseObject(bytes, clazz);
        return (T) object;
    }
}
