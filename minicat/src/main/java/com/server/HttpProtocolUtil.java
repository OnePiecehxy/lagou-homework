package com.server;

public class HttpProtocolUtil {

    /**
     * 为响应码200提供请求头信息
     * @param contentLength
     * @return
     */
    public static String getHttpHeader200(long contentLength){
        return "HTTP/1.1 200 OK \n" +
                "Content-Type: text/html \n" +
                "Content-Length: " + contentLength + " \n" +
                "\r\n";
    }

    /**
     * 为响应码404提供请求头信息
     * @return
     */
    public static String getHttpHeader404(){
        String data = "<h1> 404 NOT FOUND</h1>";
        return "HTTP/1.1 404 NOT Found \n" +
                "Content-Type: text/html;charset=utf-8 \n" +
                "Content-Length: " + data.getBytes().length + "\n" +
                "\r\n" + data;
    }
}
