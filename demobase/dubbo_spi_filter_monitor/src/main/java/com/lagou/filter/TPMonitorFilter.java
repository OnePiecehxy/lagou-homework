package com.lagou.filter;

import com.lagou.bean.MethodInfo;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
@Activate(group = {CommonConstants.CONSUMER})
public class TPMonitorFilter implements Filter,Runnable {
    private ConcurrentHashMap<String, List<MethodInfo>> concurrentHashMap = new ConcurrentHashMap<>();
    public TPMonitorFilter(){
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(this,5,5, TimeUnit.SECONDS);
    }


    @Override
    public void run() {

        for(Map.Entry<String, List<MethodInfo>> entry : concurrentHashMap.entrySet()){

            System.out.println("方法：" + entry.getKey() + ",TP90耗时情况：" + getTP(entry.getValue(), (long) 0.90));
            System.out.println("方法：" + entry.getKey() + ",TP99耗时情况：" + getTP(entry.getValue(), (long) 0.99));
//            int indexTP90 = (int) (newList.size() * 0.90);
//            int indexTP99 = (int) (newList.size() * 0.99);
//            System.out.println("方法：" + entry.getKey() + ",TP90耗时情况：" + newList.get(indexTP90).getConsuming());
//
//            System.out.println("方法：" + entry.getKey() + ",TP99耗时情况：" + newList.get(indexTP99).getConsuming());

        }
    }


    public Long getTP(List<MethodInfo> list, long rate){
        long currentTime = System.currentTimeMillis();
//        List<MethodInfo> list = entry.getValue();
        List<MethodInfo> newList = new ArrayList<>();
        for (MethodInfo methodInfo : list) {
            long endTime = methodInfo.getEndTime();
            if (endTime <= currentTime && endTime >= currentTime - 60000){
                long consumeTime = methodInfo.getConsuming();
                newList.add(methodInfo);
            }
        }

        newList.sort(new Comparator<MethodInfo>() {
            @Override
            public int compare(MethodInfo o1, MethodInfo o2) {
                if (o1.getConsuming() > o2.getConsuming()){
                    return 1;
                }else if (o1.getConsuming() < o2.getConsuming()){
                    return -1;
                }else{
                    return 0;
                }
            }
        });
        int index = (int) (newList.size() * rate);
        return newList.get(index).getConsuming();
    }
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        long startTime = System.currentTimeMillis();
        Result result = invoker.invoke(invocation);
        long endTime = System.currentTimeMillis();
//        System.out.println("endTime=" + endTime);
        long consumeTime = endTime - startTime;
        String methodName = invocation.getMethodName();
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setConsuming(consumeTime);
        methodInfo.setName(invocation.getMethodName());
        methodInfo.setEndTime(endTime);

        if (concurrentHashMap.get(methodName) == null){
            List<MethodInfo> list = new ArrayList<>();
            list.add(methodInfo);
            concurrentHashMap.put(methodName, list);
        }else{
            List<MethodInfo> list = concurrentHashMap.get(methodName);
            list.add(methodInfo);
            concurrentHashMap.put(methodName, list);
        }
        return result;
    }
}
