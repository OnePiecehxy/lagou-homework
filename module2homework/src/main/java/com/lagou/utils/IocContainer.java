package com.lagou.utils;

import com.lagou.annotation.Autowired;
import com.lagou.annotation.Repository;
import com.lagou.annotation.Service;
import com.lagou.annotation.Transactional;
import com.lagou.factory.ProxyFactory;
import com.lagou.service.TransferService;
import org.dom4j.DocumentException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class IocContainer {

    public static Map<String, Object> objectMap = new HashMap<>();
    public static Map<String, String> idClassMap = new HashMap<>();

    public static Object getObject(String id){
        Set<String> keys = idClassMap.keySet();
        if (keys.contains(id)){
            String classId = idClassMap.get(id);
            return objectMap.get(classId);
        }else{
            return objectMap.get(id);
        }

    }
    public void getIocContainer() throws ClassNotFoundException, InstantiationException, DocumentException, IllegalAccessException {
        List<Class<?>> allClass = GetAllJavaClass.getAllClass();
        List<Class<?>> classes = new ArrayList<>();
        for (int i = 0; i < allClass.size(); i++) {
            Class<?> aClass = allClass.get(i);
            if(aClass.isInterface()){
                continue;
            }
            Service annotation = aClass.getAnnotation(Service.class);
            Repository repositoryAnnotation = aClass.getAnnotation(Repository.class);

            boolean flag1 = annotation != null && "com.lagou.annotation.Service".equals(annotation.annotationType().getName());
            boolean flag2 = repositoryAnnotation != null && "com.lagou.annotation.Repository".equals(repositoryAnnotation.annotationType().getName());

            String objectKeyId = getKey(aClass.getName());
            if (flag1){
                Object o = aClass.newInstance();
                if ("".equals(annotation.value())){
                    objectMap.put(objectKeyId, o);
                }else{
                    idClassMap.put(annotation.value(), objectKeyId);
                    objectMap.put(objectKeyId, o);
                }
                classes.add(aClass);
            }

            if (flag2){
                Object o = aClass.newInstance();
                if ("".equals(repositoryAnnotation.value())){
                    objectMap.put(objectKeyId, o);
                }else{
                    idClassMap.put(repositoryAnnotation.value(), objectKeyId);
                    objectMap.put(objectKeyId, o);
                }
                classes.add(aClass);
            }

        }



        for (int i = 0; i < allClass.size(); i++) {
            Class<?> aClass = allClass.get(i);
            String classKey = getKey(aClass.getName());
            Object classObject = objectMap.get(classKey);
            Field[] fields = aClass.getDeclaredFields();
            for (int j = 0; j < fields.length; j++) {
                Annotation[] declaredAnnotations = fields[j].getDeclaredAnnotations();
                boolean autoDefect = false;
                for (Annotation declaredAnnotation : declaredAnnotations) {
                    String annotationName = declaredAnnotation.annotationType().getName();
                    if ("com.lagou.annotation.Autowired".equals(annotationName)){
                        autoDefect = true;
                    }
                }
                if (autoDefect){
                    String fieldClassType = fields[j].getType().getName();
                    Class<?> fieldClass = Class.forName(fieldClassType);
                    if (fieldClass.isInterface()){
                        String implementsClassName = searchImplementsClass(fieldClass, classes);
                        String implementsClassId = getKey(implementsClassName);
                        Object implementObject = objectMap.get(implementsClassId);
                        fields[j].setAccessible(true);
                        fields[j].set(classObject, implementObject);
                    }else {
                        String fieldClassName = fieldClass.getName();
                        String fieldClassKey = getKey(fieldClassName);
                        Object fieldObject = objectMap.get(fieldClassKey);
                        fields[j].setAccessible(true);
                        fields[j].set(classObject, fieldObject);
                    }

                }
            }
            objectMap.put(classKey, classObject);
        }
        for (Class<?> aClass : allClass) {
            if(aClass.isInterface()){
                continue;
            }
            Transactional transactionalAnnotation = aClass.getAnnotation(Transactional.class);
            Service annotation = aClass.getAnnotation(Service.class);
            boolean flag1 = annotation != null && "com.lagou.annotation.Service".equals(annotation.annotationType().getName());
            boolean flag3 = transactionalAnnotation != null && "com.lagou.annotation.Transactional".equals(transactionalAnnotation.annotationType().getName());
            if (flag1 && flag3){
                String serviceKeyId = getKey(aClass.getName());
                Object proxyObject = objectMap.get(serviceKeyId);
                ProxyFactory proxyFactory = new ProxyFactory();
                Object cGlibProxy = proxyFactory.getCGlibProxy(proxyObject);
                objectMap.put(serviceKeyId, cGlibProxy);
            }
        }
        System.out.println("容器初始化完毕");
    }

    public String searchImplementsClass(Class<?> clazz, List<Class<?>> classes){
        String fieldClassName = clazz.getName();
        for (Class<?> aClass : classes) {
            Class<?>[] interfaces = aClass.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                if (fieldClassName.equals(anInterface.getName())){
                    return aClass.getName();
                }
            }
        }

        return "";
    }

    public String getKey(String fullPath){
        String className = getClassName(fullPath);
        String s = lowerCase(className);
        return s;
    }

    public String getClassName(String fullPath){
        int index = fullPath.lastIndexOf(".");

        return fullPath.substring(index + 1, fullPath.length());
    }

    public String lowerCase(String str){
        String firstChar = str.charAt(0) + "";
        return firstChar.toLowerCase() + str.substring(1, str.length());
    }

    public static void main(String[] args) throws Exception {
        IocContainer iocContainer = new IocContainer();
        iocContainer.getIocContainer();
//        TransferService transferServiceImpl = (TransferService) objectMap.get("transferServiceImpl");
        TransferService transferServiceImpl = (TransferService) IocContainer.getObject("transferService");
        transferServiceImpl.transfer("6029621011001", "6029621011000", 100);
//        System.out.println("service==" + objectMap.get("transferServiceImpl"));
    }
}
