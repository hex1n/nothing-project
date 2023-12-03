package com.hexin.demo.like.service;

import com.hexin.demo.ResultBean;
import com.hexin.demo.entity.LikeInfo;

/**
 * @Author hex1n
 * @Date 2023/12/3/20:38
 * @Description
 **/
public interface LikeService {
    ResultBean likeUnLike(String likeRedisKey, LikeInfo likeInfo);
}
