package com.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StaticResourceUtil {

    //获取资源文件的绝对路径
    public static String getAbsolutePath(String path){
        String rootPath = StaticResourceUtil.class.getResource("/").getPath();
        return rootPath.replaceAll("\\\\", "/") + path;
    }


    public static void outputStaticResource(InputStream inputStream, OutputStream outputStream) throws IOException {
        int count = 0;
        while (count == 0){
            count = inputStream.available();
//            System.out.println("count==" + count);
        }
        outputStream.write(HttpProtocolUtil.getHttpHeader200(count).getBytes());
        long writtern = 0;
        int byteLength = 1024;
        int resourceLength = count;
        byte[] bytes = new byte[byteLength];
        while (writtern < resourceLength){

            if (writtern + byteLength > resourceLength){
                byteLength = (int) (resourceLength - writtern);
                bytes = new byte[byteLength];
            }
            inputStream.read(bytes);
            outputStream.write(bytes);
            outputStream.flush();
            writtern = writtern + byteLength;


        }
    }
}
