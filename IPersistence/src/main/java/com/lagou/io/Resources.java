package com.lagou.io;

import java.io.InputStream;

public class Resources {
    public static InputStream getResourceAsStream(String filePath){
        InputStream inputStream = Resources.class.getClassLoader().getResourceAsStream(filePath);
        return inputStream;
    }
}
