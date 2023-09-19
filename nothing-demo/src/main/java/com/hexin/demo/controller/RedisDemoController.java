package com.hexin.demo.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.hexin.demo.ResultBean;
import com.hexin.demo.util.RedisStreamUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Author hex1n
 * @Date 2021/12/13 14:33
 * @Description
 */
@RestController
@Slf4j
public class RedisDemoController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${redis-stream.names:}")
    private String[] redisStreamNames;

    @Autowired
    private RedisStreamUtil redisStreamUtil;

    /**
     * 排行榜
     */
    @GetMapping("/redisTopN")
    public void redisTopN() {
        String key = "Game name: Keep Running, Alibaba Cloud ";
        redisTemplate.delete(key);
        List<String> playerList = Lists.newArrayList();
        for (int i = 0; i < 20; ++i) {
            //为每个玩家随机生成一个ID
            playerList.add(UUID.randomUUID().toString());
        }
        for (int i = 0; i < playerList.size(); i++) {
            int score = (int) (Math.random() * 5000);
            String member = playerList.get(i);
            log.info("Player ID:{},Player Score:{}", member, score);
            // 将玩家ID和分数添加到对应键的SortedSet中;
            redisTemplate.opsForZSet().add(key, member, score);
        }
        log.info("         :{}", key);
        log.info(" Ranking list of all players");
        Set<ZSetOperations.TypedTuple> scoreList = redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, -1);
        for (ZSetOperations.TypedTuple item : scoreList) {
            System.out.println(
                    "Player ID:" +
                            item.getValue() +
                            ", Player Score:" +
                            Double.valueOf(item.getScore()).intValue()
            );
        }
        //输出打印前五名玩家的信息
        System.out.println();
        System.out.println("       " + key);
        System.out.println("       Top players");
        scoreList = redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, 4);
        for (ZSetOperations.TypedTuple item : scoreList) {
            System.out.println(
                    "Player ID:" +
                            item.getValue() +
                            ", Player Score:" +
                            Double.valueOf(item.getScore()).intValue()
            );
        }
        //输出打印特定玩家列表
        System.out.println();
        System.out.println("         " + key);
        System.out.println(" Players with scores from 1,000 to 2,000");
        //从对应key的SortedSet中获取已经积分在1000至2000的玩家列表
        scoreList = redisTemplate.opsForZSet().rangeByScoreWithScores(key, 1000, 2000);
        for (ZSetOperations.TypedTuple item : scoreList) {
            System.out.println(
                    "Player ID:" +
                            item.getValue() +
                            ", Player Score:" +
                            Double.valueOf(item.getScore()).intValue()
            );
        }

    }

    @GetMapping("getRedisKey")
    public ResultBean getRedisKey() throws IOException {
        HashSet<String> set = Sets.newHashSet();
        HashSet<String> tagIdset = Sets.newHashSet();
        HashOperations hashOperations = redisTemplate.opsForHash();

        int count = 0;
        ScanOptions scanOptions = ScanOptions.scanOptions().match("*").count(2000).build();
        RedisSerializer<String> redisSerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
        Cursor cursor = (Cursor) redisTemplate.executeWithStickyConnection((RedisCallback) redisConnection ->
                new ConvertingCursor<>(redisConnection.scan(scanOptions), redisSerializer::deserialize));
        while (cursor.hasNext() && count <= 2000) {
            String key = String.valueOf(cursor.next());
            Map<String, String> entries = hashOperations.entries(key);
            Set<String> keySet = entries.keySet();
            tagIdset.addAll(keySet);
            String[] keys = key.split("_");
            set.add(keys[0]);
            count++;
        }
        log.info("ssssssssssss:{}", JSON.toJSONString(tagIdset));
        cursor.close();

        //关闭cursor

        return ResultBean.success(set);
    }


    /**
     * redis 发布订阅
     *
     * @return
     */
    @GetMapping("/redisPub")
    public ResultBean redisPub() {
        redisTemplate.convertAndSend("redisPublish", "hello");

        return ResultBean.success();
    }

    @GetMapping("/sendTest/{streamName}")
    public String addStream(@PathVariable String streamName) {
        Map<String, Object> message = new HashMap<>();
        message.put("test", "hello redismq");
        message.put("send time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return redisStreamUtil.addStream(streamName, message).getValue();
    }

    @GetMapping("/getStream")
    public List<MapRecord<String, Object, Object>> getStream(String key) {
        return redisStreamUtil.getAllStream(key);
    }


    @GetMapping("/groupRead")
    public void getStreamByGroup(String key, String groupName, String consumerName) {
        redisStreamUtil.getStreamByGroup(key, groupName, consumerName);
    }

    @GetMapping("/moreTest/{count}")
    public void moreAddTest(@PathVariable("count") Integer count) {
        for (int i = 0; i < count; i++) {
            Map<String, Object> message1 = new HashMap<>();
            message1.put("name", "mystream1");
            message1.put("send time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            Map<String, Object> message2 = new HashMap<>();
            message2.put("name", "mystream2");
            message2.put("send time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            redisStreamUtil.addStream(redisStreamNames[0], message1);
            redisStreamUtil.addStream(redisStreamNames[1], message2);
        }
    }

    @Value("${server.port}")
    private int port;

    private final static String UUID_ = UUID.randomUUID().toString();

    @GetMapping("test")
    public void test() throws UnknownHostException {
        log.info(UUID_ + "---" + port);
    }

    /**
     * feed 流 关注
     *
     * @throws UnknownHostException
     */
    @GetMapping("feedTest")
    public ResultBean feedTest() {
        // 查询所有粉丝
        List<String> follows = Arrays.asList("1", "2", "3");
        for (String follow : follows) {
            // 推送文章id给所有粉丝收件箱
            String key = "feed:" + follow;
            redisTemplate.opsForZSet().add(key, "2024", System.currentTimeMillis());
        }

        /**
         * 滚动分页查询
         * 参数
         * max：第一次查？ 取当前时间戳：上一次查询的最小值
         * min：0
         * offset：第一次查？ 0 ： 上一次查询的结果中，与最小值一样的元素个数
         * count：2 查几条
         */
        String key = "feed:1";
        Set<ZSetOperations.TypedTuple<String>> set = redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, 0, 1662353048583L, 1, 2);
        List<String> ids = Lists.newArrayList();
        long minTime = 0;
        int os = 1;
        for (ZSetOperations.TypedTuple<String> val : set) { // 5 4 4 2 2
            String id = val.getValue();
            long time = val.getScore().longValue();
            ids.add(id);
            if (time == minTime) {
                os++;
            } else {
                minTime = time;
                os = 1;
            }

        }
        return ResultBean.success(ids);
    }

    /**
     * 用户签到--bitmap
     *
     * @return
     */
    @GetMapping("sign")
    public ResultBean sign() {
        // 签到
        /**
         * 获取日期
         * 获取今天是本月的第几天
         */
        LocalDateTime now = LocalDateTime.now();
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = "userId" + keySuffix;
        int dayOfMonth = now.getDayOfMonth();
        redisTemplate.opsForValue().setBit(key, dayOfMonth - 1, true);
        return ResultBean.success();
    }

    /**
     * 用户签到统计--bitmap
     *
     * @return
     */
    @GetMapping("signCount")
    public ResultBean signCount() {
        // 签到
        /**
         * 获取日期
         * 获取今天是本月的第几天
         */
        LocalDateTime now = LocalDateTime.now();
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = "userId" + keySuffix;
        int dayOfMonth = now.getDayOfMonth();
        List<Long> result = redisTemplate.opsForValue().bitField(key,
                BitFieldSubCommands.create()
                        .get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth))
                        .valueAt(0));
        Long num = result.get(0);
        int count = 0;
        // 循环遍历
        while (true) {
            // 让这个数字与1 做与运算，得到数字的最后一个bit位 // 判断这个bit 位是否为0
            if ((num & 1) == 0) {
                // 如果为 0  说明未签到，结束
                break;
            } else {
                // 如果不为0，说明已签到，计数器 +1
                count++;
            }
            // 把数字右移一位，抛弃最后一个bit位，继续下一个bit位。
            num >>= 1;
        }

        return ResultBean.success(count);
    }


    /**
     * 用户签到统计--bitmap
     *
     * @return
     */
    @GetMapping("testHyperLog")
    public ResultBean testHyperLog() {


        return ResultBean.success();
    }


    public static void main(String[] args) {
        //
        HashMap<Object, Object> data = Maps.newHashMap();
        data.put("a", 1);
        data.put("b", 2);
    }
}
