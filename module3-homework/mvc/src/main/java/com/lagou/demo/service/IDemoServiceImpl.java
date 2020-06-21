package com.lagou.demo.service;

import com.lagou.mvcframeword.annotation.LaGouService;

@LaGouService("demoService")
public class IDemoServiceImpl implements IDemoService{
    @Override
    public String get() {
        System.out.println("用户正常访问");
        return "用户正常访问";
    }

}
