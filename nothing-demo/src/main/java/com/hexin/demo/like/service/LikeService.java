package com.hexin.demo.like.service;

import com.hexin.demo.entity.LikeInfo;
import com.hexin.demo.entity.LikeInfoVO;

/**
 * @Author hex1n
 * @Date 2023/12/3/20:38
 * @Description
 **/
public interface LikeService {
    LikeInfoVO likeUnLike(String likeRedisKey, LikeInfo likeInfo);
}
