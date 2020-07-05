package com.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Response {
    private OutputStream outputStream;

    public Response() {
    }

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    //输出指定字符串
    public void output(String content) throws IOException {
        outputStream.write(content.getBytes());
    }
    public void outputHtml(String url) throws IOException {
        //获取静态资源的绝对路径
        String absoluteResource = StaticResourceUtil.getAbsolutePath(url);
        File file = new File(absoluteResource);
        if (file.exists() && file.isFile()){
            //输出静态资源文件
            StaticResourceUtil.outputStaticResource(new FileInputStream(file), outputStream);
        }else {
            //输出404
            output(HttpProtocolUtil.getHttpHeader404());
        }
    }
}
