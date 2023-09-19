package com.hexin.demo.controller;

import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;

public class LikeSystem {
    private final RedissonClient redissonClient;

    public LikeSystem() {
        // 配置 Redisson 连接
        Config config = new Config();
        config.useSingleServer()
              .setAddress("redis://localhost:6379"); // 指定 Redis 服务器地址

        // 创建 Redisson 客户端
        redissonClient = Redisson.create(config);

        // redisson pipeline
        RBatch batch = redissonClient.createBatch();
        RMapAsync<Object, Object> map = batch.getMap("aa");
        RFuture<Object> aa = map.getAsync("aa");
    }

    // 用户点赞
    public void like(String userId, String objectId) {
        RSet<String> likes = redissonClient.getSet("likes:" + objectId);
        likes.add(userId);
    }

    // 用户取消点赞
    public void unlike(String userId, String objectId) {
        RSet<String> likes = redissonClient.getSet("likes:" + objectId);
        likes.remove(userId);
    }

    // 获取点赞数量
    public long getLikeCount(String objectId) {
        RSet<String> likes = redissonClient.getSet("likes:" + objectId);
        return likes.size();
    }

    // 检查用户是否点赞
    public boolean hasLiked(String userId, String objectId) {
        RSet<String> likes = redissonClient.getSet("likes:" + objectId);
        return likes.contains(userId);
    }

    public static void main(String[] args) {
        LikeSystem likeSystem = new LikeSystem();

        String userId = "user1";
        String objectId = "post123";

        // 用户点赞
        likeSystem.like(userId, objectId);

        // 获取点赞数量
        long likeCount = likeSystem.getLikeCount(objectId);
        System.out.println("Like Count: " + likeCount);

        // 用户取消点赞
        // likeSystem.unlike(userId, objectId);

        // 检查用户是否点赞
        boolean hasLiked = likeSystem.hasLiked(userId, objectId);
        System.out.println("Has Liked: " + hasLiked);

        // 关闭 Redisson 客户端连接
        likeSystem.redissonClient.shutdown();
    }
}
