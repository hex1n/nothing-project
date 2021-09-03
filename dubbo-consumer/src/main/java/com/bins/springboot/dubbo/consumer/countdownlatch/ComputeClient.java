package com.bins.springboot.dubbo.consumer.countdownlatch;

import com.google.common.collect.Maps;
import org.springframework.scheduling.annotation.Async;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author hex1n
 * @date 2021/6/7 16:21
 * @description
 */
@Async
public class ComputeClient {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long startTime = System.currentTimeMillis();
        CountDownLatch count = new CountDownLatch(10);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Info info = new Info();
        Map<String, List<String>> a = Maps.newHashMap();
        Map<String, List<String>> b = Maps.newHashMap();
        Map<String, List<String>> c = Maps.newHashMap();
        a.put("1", Arrays.asList("1", "2", "3"));
        b.put("2", Arrays.asList("4", "5", "6"));
        c.put("3", Arrays.asList("7", "8", "9"));
        info.setA(a);
        info.setB(b);
        info.setC(c);
        HashMap<Integer, Integer> map = Maps.newHashMap();
        for (int i = 0; i < 10; i++) {
            Future<Integer> s1 = executor.submit(new ComputeXTask(count, i, info));
            Future<Integer> s2 = executor.submit(new ComputeYTask(count, i));
            Integer i1 = s1.get();
            Integer i2 = s2.get();
            map.put(i, i1 + i2);
        }
        count.await();
        int sum = map.values().stream().mapToInt(value -> value).sum();
        System.out.println("over，计算完成时间 :" + (System.currentTimeMillis() - startTime) + "  结果: " + sum);


        executor.shutdown();
    }
}
