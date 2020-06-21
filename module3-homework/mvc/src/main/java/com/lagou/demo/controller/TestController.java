package com.lagou.demo.controller;

import com.lagou.mvcframeword.annotation.LaGouController;
import com.lagou.mvcframeword.annotation.LaGouRequestMapping;

@LaGouController
@LaGouRequestMapping("/test")
public class TestController {

    @LaGouRequestMapping("/t1")
    public void test1(){
        System.out.println("类和方法均无@Sercurity注解");
    }
}
