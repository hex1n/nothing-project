package com.hexin.demo.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author hex1n
 */
@Slf4j
public class LikeSystem {
    private final RedissonClient redissonClient;

    public LikeSystem() {
        // 配置 Redisson 连接
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://localhost:6379");

        // 创建 Redisson 客户端
        redissonClient = Redisson.create(config);

        // redisson pipeline
        RBatch batch = redissonClient.createBatch();
        RMapAsync<Object, Object> map = batch.getMap("aa");
        RFuture<Object> aa = map.getAsync("aa");
    }

    public LikeSystem(int dbNumber) {
        // 配置 Redisson 连接  // 指定 Redis 服务器地址 和库
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://localhost:6379")
                .setDatabase(dbNumber);

        // 创建 Redisson 客户端
        redissonClient = Redisson.create(config);
    }

    /**
     * 用户点赞
     */
    public void like(String userId, String objectId) {
        RSet<String> likes = redissonClient.getSet("likes:" + objectId);
        likes.add(userId);
    }

    /**
     * 用户取消点赞
     */
    public void unlike(String userId, String objectId) {
        RSet<String> likes = redissonClient.getSet("likes:" + objectId);
        likes.remove(userId);
    }

    /**
     * 获取点赞数量
     */
    public long getLikeCount(String objectId) {
        RSet<String> likes = redissonClient.getSet("likes:" + objectId);
        return likes.size();
    }

    /**
     * 检查用户是否点赞
     */
    public boolean hasLiked(String userId, String objectId) {
        RSet<String> likes = redissonClient.getSet("likes:" + objectId);
        return likes.contains(userId);
    }

    @SneakyThrows
    public static void main(String[] args) {
        LikeSystem likeSystem = new LikeSystem(2);

        List<String> userList = Arrays.asList("user1", "user2", "user3");
        String subjectId = "post1008610086";
        for (String userId : userList) {
            // 用户点赞
            likeSystem.like(userId, subjectId);
        }


        // 获取点赞数量
        long likeCount = likeSystem.getLikeCount(subjectId);
        System.out.println("Like Count: " + likeCount);

        TimeUnit.SECONDS.sleep(10);
        // 用户取消点赞
        likeSystem.unlike("user1", subjectId);

        long likeCount2 = likeSystem.getLikeCount(subjectId);
        System.out.println("Like Count2: " + likeCount2);

        // 检查用户是否点赞
        boolean hasLiked = likeSystem.hasLiked("user1", subjectId);
        boolean hasLiked2 = likeSystem.hasLiked("user2", subjectId);
        System.out.println("user1 Has Liked: " + hasLiked);
        System.out.println("user2 Has Liked: " + hasLiked2);

        likeSystem.unlike("aaa","bbb");

        // 关闭 Redisson 客户端连接
        likeSystem.redissonClient.shutdown();
    }

}
