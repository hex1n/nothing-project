package com.hexin.demo.test;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author hex1n
 * @date 2021/1/18 15:01
 * @description
 */
@Slf4j
public class RedissonTest {
    public static void main(String[] args) throws InterruptedException {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(1);
        RedissonClient redissonClient = Redisson.create(config);
        RLock lock = redissonClient.getLock("test");
        boolean tryLock = lock.tryLock(1000L,TimeUnit.SECONDS);
        boolean heldByCurrentThread = lock.isHeldByCurrentThread();
        if (tryLock){
            try {
                System.out.println("lock");
            }finally {
                lock.unlock();
            }
        }

        RBlockingQueue<String> testQueue = redissonClient.getBlockingQueue("test");
        RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(testQueue);
        new Thread(() -> {
            for (; ; ) {
                try {
                    String ele = testQueue.peek();
                    if (Objects.equals(ele, "ffffff1")) {
                        testQueue.remove(ele);
                    }
                    System.err.println(testQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        for (int i = 0; i <= 5; i++) {
            delayedQueue.offer("ffffff" + i, 20, TimeUnit.SECONDS);
        }
    }
}
