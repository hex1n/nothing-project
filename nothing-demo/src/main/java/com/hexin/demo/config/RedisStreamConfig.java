package com.hexin.demo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexin.demo.listener.ListenerMessage;
import com.hexin.demo.listener.RedisStreamMessageListener;
import com.hexin.demo.util.RedisStreamUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.concurrent.Executor;

/**
 * @Author hex1n
 * @Date 2022/3/15 20:44
 * @Description
 */
@Slf4j
//@Configuration
public class RedisStreamConfig {

    private final ListenerMessage streamListener;
    private final RedisStreamUtil redisStreamUtil;
  /*  @Value("${redis-stream.names}")
    private String[] redisStreamNames;
    @Value("${redis-stream.groups}")
    private String[] groups;*/

    @Value("${redis-message.topic.aliMsg:test-topic}")
    private String aliMsgTopic;

    @Resource
    private Executor commentAsync;

    @Autowired
    public RedisStreamConfig(RedisStreamUtil redisStreamUtil) {
        this.redisStreamUtil = redisStreamUtil;
        this.streamListener = new ListenerMessage(redisStreamUtil);
    }

    /**
     * redis消息监听器容器
     * 可以注册多个消息监听器，每个监听器可以检测多个主题
     *
     * @param redisConnectionFactory redis连接工厂
     * @param listenerAdapter        消息监听器
     * @return
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory,
                                                                       MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        // 订阅多个频道
        redisMessageListenerContainer.addMessageListener(listenerAdapter, new PatternTopic(aliMsgTopic));
        //redisMessageListenerContainer.addMessageListener(listenerAdapter, new PatternTopic("xxxxxxx"));

        //序列化对象 发布的时候需要设置序列化；订阅方也需要设置同样的序列化
        Jackson2JsonRedisSerializer seria = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        seria.setObjectMapper(objectMapper);
        redisMessageListenerContainer.setTopicSerializer(seria);
        return redisMessageListenerContainer;
    }

    /**
     * stream监听容器配置
     *
     * @param redisConnectionFactory redis连接工厂
     * @param streamListener         消息监听器，消息的实际消费者
     * @return
     */
    @Bean
    public StreamMessageListenerContainer streamMessageListenerContainer(RedisConnectionFactory redisConnectionFactory, RedisStreamMessageListener streamListener) {
        //构建StreamMessageListenerContainerOptions
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, String>> options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                //超时时间
                .pollTimeout(Duration.ofSeconds(2))
                .batchSize(10)
                .targetType(String.class)
                //执行时用的线程池
                .executor(commentAsync)
                //还可以设置序列化方式，这里不做设置，使用默认的方式
                .build();

        //创建StreamMessageListenerContainer
        StreamMessageListenerContainer<String, ObjectRecord<String, String>> container = StreamMessageListenerContainer
                .create(redisConnectionFactory, options);

        //指定消费最新的消息
        StreamOffset<String> offset = StreamOffset.create(aliMsgTopic, ReadOffset.lastConsumed());

        //创建消费者 指定消费者组和消费者名字（注意，这里使用到用户组时，发送消息时必须有用户组，不然会报错，消息消费不成功）
        Consumer consumer = Consumer.from("group-1", "consumer-1");

        StreamMessageListenerContainer.StreamReadRequest<String> streamReadRequest = StreamMessageListenerContainer.StreamReadRequest.builder(offset)
                .errorHandler((error) -> log.error(error.getMessage()))
                .cancelOnError(e -> false)
                .consumer(consumer)
                //关闭自动ack确认
                .autoAcknowledge(false)
                .build();
        //指定消费者对象
        container.register(streamReadRequest, streamListener);
        container.start();
        return container;
    }

}
