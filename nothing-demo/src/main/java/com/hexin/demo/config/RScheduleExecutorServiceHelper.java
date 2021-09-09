package com.hexin.demo.config;

import com.google.common.collect.Maps;
import org.redisson.api.RScheduledExecutorService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author nerd
 * @date 2017-08-02 14:49
 */
@Component
public class RScheduleExecutorServiceHelper {
    public static final String LOAN_REDIS_QUEUE_KEY = "loan_queue_jobs";
    public static final String LOAN_CONTRACT_REDIS_QUEUE_KEY = "loan_contract_queue_jobs";
    public static final String LOAN_MESSAGE_QUEUE_KEY = "loan_message_queue_jobs";
    private static RScheduledExecutorService service = null;
    private static RedissonClient client;

    private static Map<String, RScheduledExecutorService> serviceMap = Maps.newConcurrentMap();

    /**
     * 获取 redisson 分布式调度任务服务
     */
    public static RScheduledExecutorService getService() {
        return getService(LOAN_REDIS_QUEUE_KEY);
    }

    public static RScheduledExecutorService getService(String queueName) {
        if (serviceMap.containsKey(queueName)) {
            return serviceMap.get(queueName);
        }
        RScheduledExecutorService service = client.getExecutorService(queueName);
        serviceMap.putIfAbsent(queueName, service);
        return service;
    }

    @Autowired
    public void setRedissonClient(RedissonClient redissonClient) {
        client = redissonClient;
    }
}
