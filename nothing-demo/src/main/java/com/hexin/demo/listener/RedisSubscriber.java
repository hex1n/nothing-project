package com.hexin.demo.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

/**
 * @Author hex1n
 * @Date 2022/3/14 14:51
 * @Description
 */
@Component
@Slf4j
public class RedisSubscriber extends MessageListenerAdapter {
    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${server.port}")
    private String port;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();
        Object msg = redisTemplate.getStringSerializer().deserialize(body);
        Object topic = redisTemplate.getStringSerializer().deserialize(channel);
        log.info("RedisSubscriber onMessage port:{} | topic:{},msg:{}", port, JSON.toJSONString(topic), JSON.toJSONString(msg));
    }
}
