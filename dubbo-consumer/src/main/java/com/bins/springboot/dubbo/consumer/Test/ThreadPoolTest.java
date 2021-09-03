package com.bins.springboot.dubbo.consumer.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author hex1n
 * @Date 2021/8/19 12:31
 * @Description
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors() * 2,
                1000L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000)
        );
        // 时间戳转秒
        int i = 139085 / 1000;
        System.out.println(i);
    }
}
