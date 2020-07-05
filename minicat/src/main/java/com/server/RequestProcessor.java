package com.server;

import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

public class RequestProcessor extends Thread {

    private Socket socket;
    private Map<String, HttpServlet> servletMap;

    public RequestProcessor(Socket socket, Map<String, HttpServlet> servletMap) {
        this.socket = socket;
        this.servletMap = servletMap;
    }

    @Override
    public void run() {
        try{
            InputStream inputStream = socket.getInputStream();
            System.out.println("收到请求");
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());
            if (servletMap.get(request.getUrl()) == null){
                response.outputHtml(request.getUrl());
            }else {
                HttpServlet servlet = servletMap.get(request.getUrl());
                servlet.service(request, response);
            }
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
