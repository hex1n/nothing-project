package com.hexin.demo.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hexin.demo.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author hex1n
 * @Date 2021/9/2 12:03
 * @Description
 */
@RequestMapping("/likeInfo")
@Slf4j
@RestController
public class LikeInoController {

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/test")
    public void test() {
        List<String> data = Lists.newArrayList();
        for (int i = 0; i < 1000000; i++) {
            data.add(String.valueOf(i));
        }
        List<List<String>> lists = Lists.partition(data, 10000);
        for (List<String> list : lists) {
            setRedis(list);
        }

    }

    @PostMapping("/get")
    public ResultBean get() {
        List<Object> fromRedis = getFromRedis();
        return ResultBean.success(fromRedis);
    }

    public void setRedis(List<String> data) {
        redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                for (String datum : data) {
                    Map<byte[], byte[]> map = Maps.newHashMap();
                    map.put(keySerializer.serialize(datum), keySerializer.serialize(Math.random()));
                    redisConnection.hMSet(keySerializer.serialize(datum), map);
                    redisConnection.expire(keySerializer.serialize(datum), 10000L);
                }
                return null;
            }
        });
    }


    public  List<Object> getFromRedis(){
        List<String> keys = Arrays.asList("1","100","10","0","abcds");
        List list = redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                for (String key : keys) {
                    redisConnection.get(key.getBytes(StandardCharsets.UTF_8));
                }
                return null;
            }
        });
        return list;
    }

}
