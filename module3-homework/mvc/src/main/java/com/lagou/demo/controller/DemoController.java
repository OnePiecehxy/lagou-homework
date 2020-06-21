package com.lagou.demo.controller;

import com.lagou.demo.service.IDemoService;
import com.lagou.mvcframeword.annotation.LaGouAutowired;
import com.lagou.mvcframeword.annotation.LaGouController;
import com.lagou.mvcframeword.annotation.LaGouRequestMapping;
import com.lagou.mvcframeword.annotation.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@LaGouController
@LaGouRequestMapping("/demo")
@Security("zhangsan")
public class DemoController {

    @LaGouAutowired
    private IDemoService iDemoService;

    @LaGouRequestMapping("/query")
    @Security("lisi")
    public String query(HttpServletRequest request, HttpServletResponse response, String name){
        System.out.println("类和方法都有@Security注解");
        return iDemoService.get();
    }

    @LaGouRequestMapping("/query2")
    public void test2(){
        System.out.println("类上有@Security注解，方法上没有");
    }
}
