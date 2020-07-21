package com.lagou.serializable;

import java.io.IOException;

public interface Serializer {
    byte[] serialize(Object object) throws IOException;

    <T> T deserialize(Class<?> clazz, byte[] bytes) throws IOException;
}
