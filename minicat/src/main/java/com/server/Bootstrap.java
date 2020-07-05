package com.server;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Bootstrap {
    private int port = 8081;
    private String appBase = "/Users/webapps";
    private Map<String, HttpServlet> servletMap = new HashMap<>();

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("=============>>> 欢迎来到minicat");

        int corePoolSize = 10;
        int maximumPoolSize = 30;
        long keepAliveTime = 60;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler
        );
//        loadServlet("");
        loadApplication();

//




//      minicat3.0
        while (true){
            System.out.println("正在监听");
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());
            if (servletMap.get(request.getUrl()) == null){
                response.outputHtml(request.getUrl());
            }else {
                HttpServlet servlet = servletMap.get(request.getUrl());
                servlet.service(request, response);
            }
            socket.close();

        }
       //多线程
//        while (true){
//            System.out.println("正在监听");
//            Socket socket = serverSocket.accept();
//
//            RequestProcessor requestProcessor = new RequestProcessor(socket, servletMap);
//            requestProcessor.start();
//
//        }
//      线程池
//        while (true){
//            System.out.println("=====>>>多线程改造");
//            Socket socket = serverSocket.accept();
//
//            RequestProcessor requestProcessor = new RequestProcessor(socket, servletMap);
//            threadPoolExecutor.execute(requestProcessor);
//        }
    }

    private void loadMyServlet(String applicationName) throws DocumentException, ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        String webConfigPath = appBase + "/" + applicationName + "/web/WEB-INF/web.xml";
        webConfigPath = webConfigPath.substring(1,webConfigPath.length());
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(webConfigPath);
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputStream);
        Element rootElement = document.getRootElement();


        List<Element> servletElements = rootElement.selectNodes("servlet");
        for (int i = 0; i < servletElements.size(); i++) {
            Element element = servletElements.get(i);
            Element servletNameNode = (Element) element.selectSingleNode("servlet-name");
            String servletNameValue = servletNameNode.getStringValue();
            Element servletClassNode = (Element) element.selectSingleNode("servlet-class");
            String servletClassValue = servletClassNode.getStringValue();
            String className = servletClassValue;
            servletClassValue = servletClassValue.replaceAll("\\.", "/");

            Element servletMapping = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletNameValue + "']" );
            Element servletUrlPattern  = (Element) servletMapping.selectSingleNode("url-pattern");

            String urlPattern = servletUrlPattern.getStringValue();
            String urlValue = new String("/" + applicationName + urlPattern);
            MyClassLoader myClassLoader = new MyClassLoader(appBase, applicationName, servletClassValue);
            String[] split = className.split("\\.");
            Class<?> aClass = myClassLoader.findClass(className);

            Class<?> aClass1 = Class.forName(className, true, myClassLoader);
            Object o = aClass1.newInstance();
            servletMap.put(urlValue, (HttpServlet) o);

        }
    }

    private void loadApplication() throws ClassNotFoundException, InstantiationException, DocumentException, IllegalAccessException, IOException {
        String applicationPath = this.getClass().getClassLoader().getResource("").getPath() + appBase;
        applicationPath = applicationPath.replaceAll("\\\\", "/");
        File applicationDirectoryFile = new File(applicationPath);
        File[] applicationFiles = applicationDirectoryFile.listFiles();
        for (File applicationFile : applicationFiles) {
            String name = applicationFile.getName();
            loadMyServlet(applicationFile.getName());
        }
    }

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.start();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
