package com.hexin.demo.controller;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.UUID;

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
}
