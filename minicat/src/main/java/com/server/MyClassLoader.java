package com.server;

import java.io.*;
import java.net.URLClassLoader;

public class MyClassLoader extends ClassLoader {
    private String appBase = "/Users/webapps";
    private String applicationName;
    private String servletRelativePath;

    public MyClassLoader(String appBase, String applicationName, String servletRelativePath) {
        this.appBase = appBase;
        this.applicationName = applicationName;
        this.servletRelativePath = servletRelativePath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        Class clazz = null;
        try {
            String rootPath = this.getClass().getClassLoader().getResource("").getPath();
            byte[] classData = getClassData(rootPath + appBase + "/" + applicationName + "/" + servletRelativePath + ".class");
            clazz = defineClass(name, classData, 0 , classData.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return clazz.getClass();
    }

//    private Object difineObject(byte[] classData) throws IOException, ClassNotFoundException {
////        URLClassLoader urlClassLoader = new URLClassLoader()
//        defineClass()
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(classData);
//        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
//        Object obj = objectInputStream.readObject();
//        return obj;
//    }

    private byte[] getClassData(String path) throws IOException {
        File file  = new File(path);
        if (file.exists()){
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int size = 0;
            while ((size = in.read(bytes)) != -1){
                out.write(bytes, 0, size);
            }
            in.close();
            out.flush();
            out.close();
            return out.toByteArray();
        }

        return null;
    }


}
