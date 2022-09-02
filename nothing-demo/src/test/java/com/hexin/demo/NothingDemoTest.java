package com.hexin.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.StringRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author hex1n
 * @Date 2022/3/18 14:47
 * @Description
 */
@SpringBootTest
public class NothingDemoTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Value("${redis-message.topic.aliMsg:test-topic}")
    private String aliMsgTopic;

    @Test
    public void test1() {
        for (int i = 0; i < 50; i++) {
            redisTemplate.convertAndSend(aliMsgTopic, "主题" + aliMsgTopic + "====》发送消息" + i);
        }
    }

    /**
     * 无用户组和ack确认
     */
    @Test
    public void testStream() {
        Map<String, String> msg = new HashMap<>();
        msg.put("aaa", "消息aaaaaaa===》");
        MapRecord mapRecord = MapRecord.create(aliMsgTopic, msg);
        StringRecord stringRecord = StringRecord.of(mapRecord).withStreamKey(aliMsgTopic);
        redisTemplate.opsForStream().add(stringRecord);
    }

    /**
     * 有用户组，无ack确认
     */
    @Test
    public void testConsumerStream() {
        Map<String, String> msg = new HashMap<>();
        msg.put("aaa", "消息aaaaaaa===》");
        MapRecord mapRecord = MapRecord.create(aliMsgTopic, msg);
        StringRecord stringRecord = StringRecord.of(mapRecord).withStreamKey(aliMsgTopic);
        StreamOperations streamOperations = redisTemplate.opsForStream();



        Map<String, String> msg1 = new HashMap<>();
        msg1.put("aaa1", "消息aaaaaaa11111===》");
        MapRecord mapRecord1 = MapRecord.create(aliMsgTopic, msg1);
        StringRecord stringRecord1 = StringRecord.of(mapRecord1).withStreamKey(aliMsgTopic);


        Map<String, String> msg2 = new HashMap<>();
        msg2.put("aaa2", "消息aaaaaaa222222222===》");
        MapRecord mapRecord2 = MapRecord.create(aliMsgTopic, msg2);
        StringRecord stringRecord2 = StringRecord.of(mapRecord2).withStreamKey(aliMsgTopic);


        //注意，当用户组已经存在时，执行创建同名用户组会报错，测试时调用了销毁用户组方法，但是没有效果，业务中用到用户组的可能性不大，如果真的用到了，建议在程序初始化时创建用户组
//        String group = streamOperations.createGroup(aliMsgTopic, "group-1");
//        org.springframework.data.redis.connection.stream.Consumer consumer=
//                org.springframework.data.redis.connection.stream.Consumer.from(group, "consumer-1");
        streamOperations.add(stringRecord1);
        streamOperations.add(stringRecord);
        streamOperations.add(stringRecord2);
    }

    /**
     * 有用户组，有ack确认，ack确认实际在消费者端做，ack确认必须有用户组存在
     */
    @Test
    public void testACKStream() {
        Map<String, String> msg = new HashMap<>();
        msg.put("aaa", "消息aaaaa333333a===》");
        MapRecord mapRecord = MapRecord.create(aliMsgTopic, msg);
        StringRecord stringRecord = StringRecord.of(mapRecord).withStreamKey(aliMsgTopic);
        StreamOperations streamOperations = redisTemplate.opsForStream();
        //注意，当用户组已经存在时，执行创建同名用户组会报错，测试时调用了销毁用户组方法，但是没有效果，业务中用到用户组的可能性不大，如果真的用到了，建议在程序初始化时创建用户组
//        String group = streamOperations.createGroup(aliMsgTopic, "group-1");
//        org.springframework.data.redis.connection.stream.Consumer consumer=
//                org.springframework.data.redis.connection.stream.Consumer.from(group, "consumer-1");
        streamOperations.add(stringRecord);
    }
}
