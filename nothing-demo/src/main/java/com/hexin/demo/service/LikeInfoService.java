package com.hexin.demo.service;

import com.hexin.demo.entity.LikeCount;
import com.hexin.demo.entity.LikeInfo;

import java.util.List;

/**
 * @Author hex1n
 * @Date 2021/9/2 12:03
 * @Description
 */
public interface LikeInfoService {

    /**
     * 点赞/取消点赞
     *
     * @param likeInfo
     */
    LikeInfo saveLiked2Redis(LikeInfo likeInfo);


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
