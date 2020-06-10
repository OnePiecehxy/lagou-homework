package com.lagou.utils;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogUtils {
    @Pointcut("execution(* com.lagou.service.TransferService.*(..))")
    public void log(){

    }

    @Before("log()")
    public void beforeMethod(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            System.out.println(arg);
        }
        System.out.println("业务逻辑执行前，前置通知");
    }

    @AfterReturning(value="log()",returning = "returnVal")
    public void afterReturnMethod(Object returnVal){
        System.out.println("业务逻辑执行后，后置通知");
    }

    @After("log()")
    public void afterMethod(){
        System.out.println("业务逻辑执行结束，最终通知");
    }
    @AfterThrowing("log()")
    public void exceptionMethod(){
        System.out.println("业务逻辑异常，异常通知");
    }

//    @Around("log()")
    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("业务逻辑执行前，环绕通知");
        Object result = null;
        try{
            proceedingJoinPoint.proceed();
            System.out.println("业务逻辑执行后，环绕通知");
        }catch (Exception e){
            System.out.println("业务逻辑异常，环绕通知");
        }

        return result;
    }
}
