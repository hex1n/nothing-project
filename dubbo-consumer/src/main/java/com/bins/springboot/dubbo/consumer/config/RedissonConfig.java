package com.bins.springboot.dubbo.consumer.config;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.RedissonNode;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.RedissonNodeConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * com.hfax.ldp.core.config.RedissonConfig <p> Created by nerd on 2017-07-23 15:24.
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.database}")
    private Integer database;

    @Bean
    public Config config() {
        Config config = new Config();
        String address = "redis://" + redisHost + ":" + port;
        String blankPassword = "";
        if (StringUtils.isNotBlank(blankPassword)) {
            config.useSingleServer().setDatabase(database).setAddress(address).setPassword(blankPassword);
        } else {
            config.useSingleServer().setDatabase(database).setAddress(address);
        }
        config.setCodec(new JsonJacksonCodec());
        return config;
    }

    @Bean
    public RedissonClient redissonClient(Config config) {
        return Redisson.create(config);
    }

    @Bean
    public RedissonNode redissonNode(Config config) {
        RedissonNodeConfig nodeConfig = new RedissonNodeConfig(config);
        Map<String, Integer> workers = Maps.newHashMap();

        workers.put(RScheduleExecutorServiceHelper.LOAN_REDIS_QUEUE_KEY, 1);
        workers.put(RScheduleExecutorServiceHelper.LOAN_CONTRACT_REDIS_QUEUE_KEY, 1);
        workers.put(RScheduleExecutorServiceHelper.LOAN_MESSAGE_QUEUE_KEY, 1);
        nodeConfig.setExecutorServiceWorkers(workers);
        RedissonNode node = RedissonNode.create(nodeConfig);
        node.start();
        return node;
    }
}
