package com.lagou.service.method;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ProviderMethod {
    private Random random = new Random();
    public void methodA() throws InterruptedException {
        Thread.sleep(random.nextInt(100));
        System.out.println("methodA");
    }

    public void methodB() throws InterruptedException {
        Thread.sleep(random.nextInt(100));
        System.out.println("methodB");
    }

    public void methodC() throws InterruptedException {
        Thread.sleep(random.nextInt(100));
        System.out.println("methodC");
    }
}
