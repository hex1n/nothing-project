package com.hexin.springboot.dubbo.consumer.Test;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author hex1n
 * @date 2021/1/18 9:37
 * @description
 */
public class HashMapTest {
    static Map<String, Integer> map = Maps.newHashMapWithExpectedSize(2);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new Run(i));
            thread.start();
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println(map.size());
    }

    static class Run implements Runnable {
        int i;
        public Run(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            for (int j = 0; j < 10; j++) {
                map.put(Thread.currentThread().getName() + " j=" + j, i);
            }
        }
    }
}
