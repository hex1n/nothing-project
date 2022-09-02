package com.hexin.demo.listener;

import com.hexin.demo.util.RedisStreamUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;

import java.util.Objects;

/**
 * @Author hex1n @Date 2022/3/15 20:46 @Description
 */
@Slf4j
public class ListenerMessage implements StreamListener<String, MapRecord<String, String, String>> {

  RedisStreamUtil redisStreamUtil;

  public ListenerMessage(RedisStreamUtil redisStreamUtil) {
    this.redisStreamUtil = redisStreamUtil;
  }

  @Override
  public void onMessage(MapRecord<String, String, String> entries) {
    try {
      // check用于验证key和对应消息是否一直
      log.info(
          "stream name :{}, body:{}, check:{}",
          entries.getStream(),
          entries.getValue(),
          (Objects.equals(entries.getStream(), entries.getValue().get("name"))));
      //            redisUtil.delField(entries.getStream(), entries.getId().getValue());
    } catch (Exception e) {
      log.error("error message:{}", e.getMessage());
    }
  }
}
