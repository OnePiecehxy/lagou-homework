package com.server;

import java.io.IOException;
import java.io.InputStream;

public class Request {
    //请求url
    private String url;
    //请求方式 GET/POST
    private String method;
    private InputStream inputStream;

    public Request() {
    }

    public Request(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        int count = 0;
        while (count == 0){
            count = inputStream.available();
            System.out.println("count=" + count);
        }
        byte[] inputByte = new byte[count];
        inputStream.read(inputByte);
        String inputStr = new String(inputByte);
        //获取请求头信息
        String[] strings = inputStr.split("\\n");
        String firstLineStr = strings[0];
        String[] s = firstLineStr.split(" ");
        this.method = s[0];
        this.url = s[1];
        System.out.println("method=" + method);
        System.out.println("url=" + url);

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
