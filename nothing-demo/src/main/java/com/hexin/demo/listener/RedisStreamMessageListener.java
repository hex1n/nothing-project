package com.hexin.demo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisStreamMessageListener implements StreamListener<String, ObjectRecord<String, String>> {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public void onMessage(ObjectRecord<String, String> message) {

        // 消息ID
        RecordId messageId = message.getId();

        // 消息的key和value
        String stream = message.getStream();
        String string = message.getValue();
        log.info("========stream监听器监听到消息========它们分别是。messageId={}, stream={}, body={}", messageId,
                stream, string);


        // 通过RedisTemplate手动确认消息，确认之后消息会从队列中消失，如果不确认，可能存在重复消费
        Long acknowledge = this.stringRedisTemplate.opsForStream().acknowledge("group-1", message);
        if (acknowledge > 0) {
            System.out.println("acknowledge的值是：" + acknowledge);
        }

    }
}