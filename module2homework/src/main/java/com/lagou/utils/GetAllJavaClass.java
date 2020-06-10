package com.lagou.utils;

import com.lagou.config.ApplicationConfigXmlBuilder;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetAllJavaClass {


    public static List<String> getAllClassName() throws DocumentException {
        String path = GetAllJavaClass.class.getResource("/").getPath();
        InputStream resourceAsStream = Resources.getResourceAsStream("applicationContext.xml");
        String basePackage = ApplicationConfigXmlBuilder.getBasePackage(resourceAsStream);
        basePackage = basePackage.replace(".", "/");
        File file = new File(path + basePackage);
        File[] files = file.listFiles();
        List<String> fileNameList = new ArrayList<>();
        fileNameList = getFile(file, fileNameList);
        List<String> classNameList = new ArrayList<String>();
        int beginIndex = path.length() - 1;
        for (String s : fileNameList) {
            int index = s.lastIndexOf(".class");
            String temp = s.substring(beginIndex, index);
            classNameList.add(temp.replace("\\", "."));
        }
        return classNameList;
    }


    public static List<Class<?>> getAllClass() throws DocumentException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        List<String> allClassName = getAllClassName();
        System.out.println(allClassName.size());
        List<Class<?>> classes = new ArrayList<>();
        for (String className : allClassName) {
            if (className.indexOf("$") > 0 ){
                continue;
            }
            if ("com.lagou.servlet.TransferServlet".equals(className)){
                continue;
            }

            if ("com.lagou.listener.MyApplicationContextListener".equals(className)){
                continue;
            }
            Class<?> aClass = Class.forName(className);
            classes.add(aClass);
        }
        return classes;

    }


    public static List<String> getFile(File file, List<String> list) {
        File[] files = file.listFiles();
        if (files == null) {
            return list;
        }
        for (File file1 : files) {
            if (file1.isDirectory()) {
                getFile(file1, list);

            } else {
                list.add(file1.getPath());
            }
        }
        return list;


    }

    public static void main(String[] args) throws IOException, DocumentException {
        List<String> allClassName = GetAllJavaClass.getAllClassName();
        for (String s : allClassName) {
            System.out.println(s);
        }

    }
}
