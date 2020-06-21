package com.lagou.mvcframeword.servlet;

import com.lagou.mvcframeword.annotation.*;
import com.lagou.mvcframeword.pojo.Handler;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LGDispacherServlet extends HttpServlet {

    private List<String> classNameList = new ArrayList<>();
    private Properties properties = new Properties();
    private Map<String, Object> ioc = new HashMap<>();
//    private Map<String, Handler> handlerMapping = new HashMap<>();
    private List<Handler> handlerMapping = new ArrayList<>();

    private Map<String, Set<String>> urlUserMapping = new HashMap<>();
    @Override
    public void init(ServletConfig config) throws ServletException {
        String contextConfigLoacation = config.getInitParameter("contextConfigLoacation");
        doLoadConfig(contextConfigLoacation);

        doScan(properties.getProperty("scanPackage"));

        doInstance();

        doAutowired();


        initHandlerMapping();
        System.out.println("初始化完成");

        
    }

    private void initHandlerMapping() {
        if (ioc.isEmpty()){
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()){
            Class<?> aClass = entry.getValue().getClass();
            if (!aClass.isAnnotationPresent(LaGouController.class)){
                continue;
            }
            String baseUrl = "";
            boolean isSecurity = false;
            String[] users = null;
            Set<String> userSet = new HashSet<>();
            if (aClass.isAnnotationPresent(LaGouRequestMapping.class)){
                LaGouRequestMapping classAnnotation = aClass.getAnnotation(LaGouRequestMapping.class);
                baseUrl = classAnnotation.value();
                if (aClass.isAnnotationPresent(Security.class)){
                    isSecurity = true;
                    users = aClass.getAnnotation(Security.class).value();
                    for (String user : users) {
                        userSet.add(user);
                    }

                }
            }
            Method[] declaredMethods = aClass.getMethods();
            for (Method declaredMethod : declaredMethods) {
                if (declaredMethod.isAnnotationPresent(LaGouRequestMapping.class)){
                    LaGouRequestMapping annotation = declaredMethod.getAnnotation(LaGouRequestMapping.class);
                    String methodUrl = annotation.value();
                    String url = baseUrl + methodUrl;
                    Handler handler = new Handler(entry.getValue(), declaredMethod, Pattern.compile(url));


                    Parameter[] parameters = declaredMethod.getParameters();
                    for (int i = 0; i < parameters.length; i++) {
                        Parameter parameter = parameters[i];
                        Map<String, Integer> parameterMapping = handler.getParameterMapping();
                        if (parameter.getType() == HttpServletRequest.class || parameter.getType() == HttpServletResponse.class){
                            parameterMapping.put(parameter.getType().getSimpleName(),i);
                        }else{
                            parameterMapping.put(parameter.getName(), i);
                        }

                    }
                    handlerMapping.add(handler);


                    if (declaredMethod.isAnnotationPresent(Security.class)){
                        Set<String> methodUserSet = new HashSet<>();
                        Security securityAnnotation = declaredMethod.getAnnotation(Security.class);
                        String[] value = securityAnnotation.value();
                        if (userSet.size() != 0){
                            for (String s : userSet) {
                                methodUserSet.add(s);
                            }
                        }
                        for (String userName : value) {
                            methodUserSet.add(userName);

                        }
                        urlUserMapping.put(url, methodUserSet);

                    }else if (isSecurity){
                        urlUserMapping.put(url, userSet);
                    }
                }
            }
        }
    }

    private void doAutowired() {
        if (ioc.isEmpty()){
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()){
            Field[] declaredFields = entry.getValue().getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (declaredField.isAnnotationPresent(LaGouAutowired.class)){
                    LaGouAutowired fieldAnnotation = declaredField.getAnnotation(LaGouAutowired.class);
                    String annotationValue = fieldAnnotation.value();
                    String fieldClassSimpleName = declaredField.getType().getSimpleName();
                    Object fieldObj = null;
                    if ("".equals(annotationValue)){
//                        String key = lowerFirst(fieldClassSimpleName);
//                        fieldObj = ioc.get(key);
                        fieldObj = ioc.get(declaredField.getType().getName());
                    }else{
                        fieldObj = ioc.get(annotationValue);
                    }
                    declaredField.setAccessible(true);
                    try {
                        declaredField.set(entry.getValue(), fieldObj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    private void doInstance() {
        if (classNameList.size() == 0) return;
        try{
            for (int i = 0; i < classNameList.size(); i++) {
                String className = classNameList.get(i);
                Class<?> aClass = Class.forName(className);
                if (aClass.isAnnotationPresent(LaGouController.class)){
                    String simpleClassName = lowerFirst(aClass.getSimpleName());
                    ioc.put(simpleClassName, aClass.newInstance());
                }else if(aClass.isAnnotationPresent(LaGouService.class)){
                    LaGouService annotation = aClass.getAnnotation(LaGouService.class);
                    String annotationValue = annotation.value();
                    Object o = aClass.newInstance();
                    if ("".equals(annotationValue)){
                        String simpleClassName = lowerFirst(aClass.getSimpleName());
                        ioc.put(simpleClassName, o);
                    }else {
                        ioc.put(annotationValue, o);
                    }

                    Class<?>[] interfaces = aClass.getInterfaces();
                    for (Class<?> anInterface : interfaces) {
                        String interfaceName = anInterface.getName();
                        ioc.put(interfaceName, o);
                    }
                }else{
                    continue;
                }


            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public String lowerFirst(String str){
        char[] chars = str.toCharArray();
        if (chars[0] >= 'A' && chars[0] <= 'Z'){
            chars[0] = (char) (chars[0] + 32);
        }
        return String.valueOf(chars);
    }

    private void doScan(String scanPackage) {
        String scanPackagePath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + scanPackage.replaceAll("\\.", "/");
        File file = new File(scanPackagePath);
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isDirectory()){
                doScan(scanPackage + "." + file1.getName());
            }else if (file1.getName().endsWith(".class")){
                String fileName = scanPackage + "." + file1.getName().replace(".class", "");
                classNameList.add(fileName);
            }
        }

    }


    private void doLoadConfig(String contextConfigLoacation) {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(contextConfigLoacation);
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        Handler handler = getHandler(req);
        if (handler == null){
            resp.getWriter().print("404 not found");
            return;
        }

        boolean isPermission = queryUserPermissions(req);
        if (!isPermission){
            resp.getWriter().print("该用户无权限访问");
            return;
        }


        Map<String, String[]> parameterMap = req.getParameterMap();
        Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();
        Object[] paramValues = new Object[parameterTypes.length];
        for(Map.Entry<String, String[]> entry : parameterMap.entrySet()){
            String value = StringUtils.join(entry.getValue());
            if (handler.getParameterMapping().containsKey(entry.getKey())){
                Integer index = handler.getParameterMapping().get(entry.getKey());
                paramValues[index] = value;
            }

            int requestIndex = handler.getParameterMapping().get(HttpServletRequest.class.getSimpleName());
            paramValues[requestIndex] = req;

            int responseIndex = handler.getParameterMapping().get(HttpServletResponse.class.getSimpleName());
            paramValues[responseIndex] = resp;
        }
        try {
            handler.getMethod().invoke(handler.getController(), paramValues);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    private boolean queryUserPermissions(HttpServletRequest req) {

        String requestURI = req.getRequestURI();
        Set<String> userSet = urlUserMapping.get(requestURI);
        if (userSet != null){
            String userName = req.getParameter("userName");
            if (StringUtils.isNotBlank(userName) && userSet.contains(userName)) {
                return true;
            }else{
                return false;
            }
        }
        return true;


    }

    private Handler getHandler(HttpServletRequest req) {
        if (handlerMapping.isEmpty()) {
            return null;
        }
        String requestURI = req.getRequestURI();
        for (Handler handler : handlerMapping){
            Matcher matcher = handler.getPattern().matcher(requestURI);
            if (matcher.find()){
                return handler;
            }

        }
        return null;
    }



}
