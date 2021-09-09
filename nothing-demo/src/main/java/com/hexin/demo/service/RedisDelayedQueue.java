package com.hexin.springboot.dubbo.consumer.service;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author hex1n
 * @date 2021/3/29 10:24
 * @description
 */
@Service
public class RedisDelayedQueue {

    @Autowired
    private RedissonClient redissonClient;

    public abstract static class TaskEventListener<T>{

        public abstract void invoke(T t);

    }

    public <T> void addQueue(T t , long delay, TimeUnit timeUnit){
        RBlockingQueue<Object> blockingQueue = redissonClient.getBlockingQueue(t.getClass().getName());
        RDelayedQueue<Object> delayedQueue = redissonClient.getDelayedQueue(blockingQueue);
        delayedQueue.offer(t,delay,timeUnit);
        delayedQueue.destroy();
    }

    public <T> void getQueue(Class zClass,TaskEventListener taskEventListener){
        RBlockingQueue<Object> blockingQueue = redissonClient.getBlockingQueue(zClass.getName());
        while (true){
            try{
                T t = (T) blockingQueue.take();
                taskEventListener.invoke(t);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }


}
