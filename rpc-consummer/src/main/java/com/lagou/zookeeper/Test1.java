package com.lagou.zookeeper;

import java.util.Date;
import java.util.Random;

public class Test1 {
    public static void main(String[] args) {
        long time = new Date().getTime();
        System.out.println("time=" + time);
        System.out.println(Long.parseLong(time + "")%2);

    }
}
