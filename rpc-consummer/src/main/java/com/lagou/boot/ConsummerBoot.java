package com.lagou.boot;

import com.lagou.client.RPCConsummer;
import com.lagou.pojo.RpcRequest;
import com.lagou.service.common.IUserService;

public class ConsummerBoot {

    public static void main(String[] args) throws InterruptedException {

        IUserService service = (IUserService) RPCConsummer.createProxy(IUserService.class);
        while (true){
            String result = service.sayHello("Cosumer method");
            System.out.println(result);
            Thread.sleep(2000);
        }
    }
}
