package com.hexin.springboot.dubbo.consumer.Test;

import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author hex1n
 * @date 2021/1/18 15:01
 * @description
 */
public class RedissonTest {
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redissonClient = Redisson.create(config);
        RBlockingQueue<String> testQueue = redissonClient.getBlockingQueue("test");
        RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(testQueue);
        new Thread(()->{
            for (;;){
                try{
                    String ele = testQueue.peek();
                    if (Objects.equals(ele,"ffffff1")){
                        testQueue.remove(ele);
                    }
                    System.err.println(testQueue.take());
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();

        for (int i = 0; i <=5; i++) {
            delayedQueue.offer("ffffff"+i,20, TimeUnit.SECONDS);
        }
        delayedQueue.offer("ffffff0",20, TimeUnit.SECONDS);
    }
}
