package com.hexin.springboot.dubbo.consumer.service;

import com.hexin.springboot.dubbo.consumer.entity.LikeCount;
import com.hexin.springboot.dubbo.consumer.entity.LikeInfo;

import java.util.List;

/**
 * @Author hex1n
 * @Date 2021/9/2 12:03
 * @Description
 */
public interface LikeInfoService {

    /**
     * 点赞
     *
     * @param likeInfo
     */
    Long saveLiked2Redis(LikeInfo likeInfo);

    /**
     * 取消点赞
     *
     * @param likeInfo
     */
    Long saveUnLike2Redis(LikeInfo likeInfo);

    /**
     * 获取Redis中存储的所有点赞数据
     *
     * @return
     */
    List<LikeInfo> getLikedDataFromRedis();

    /**
     * 获取Redis中存储的所有点赞数量
     *
     * @return
     */
    List<LikeCount> getLikedCountFromRedis();
}
