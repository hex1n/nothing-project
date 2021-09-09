package com.hexin.springboot.dubbo.consumer.controller;

import com.alibaba.fastjson.JSON;
import com.hexin.springboot.dubbo.consumer.constant.LikedStatusEnum;
import com.hexin.springboot.dubbo.consumer.entity.LikeCount;
import com.hexin.springboot.dubbo.consumer.entity.LikeInfo;
import com.hexin.springboot.dubbo.consumer.service.LikeInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
    private LikeInfoService likeInfoService;

    @PostMapping("/like")
    public Long like(@RequestBody LikeInfo likeInfo) {
        log.info("like likeInfo:{}", JSON.toJSONString(likeInfo));
        if (!Objects.equals(likeInfo.getLikedStatus(), LikedStatusEnum.LIKE.getCode())) {
            throw new IllegalArgumentException("取消点赞参数不合法");
        }
        Long likeCount = likeInfoService.saveLiked2Redis(likeInfo);
        return likeCount;
    }

    @PostMapping("/unLike")
    public Long unLike(@RequestBody LikeInfo likeInfo) {
        log.info("unLike likeInfo:{}", JSON.toJSONString(likeInfo));
        if (!Objects.equals(likeInfo.getLikedStatus(), LikedStatusEnum.UNLIKE.getCode())) {
            throw new IllegalArgumentException("点赞参数不合法");
        }
        Long likeCount = likeInfoService.saveUnLike2Redis(likeInfo);
        return likeCount;
    }

    @GetMapping("/getLikedDataFromRedis")
    public List<LikeInfo> getLikedDataFromRedis() {
        List<LikeInfo> likeInfoList = likeInfoService.getLikedDataFromRedis();
        return likeInfoList;
    }

    @GetMapping("/getLikedCountFromRedis")
    public List<LikeCount> getLikedCountFromRedis() {
        List<LikeCount> likeCounts = likeInfoService.getLikedCountFromRedis();
        return likeCounts;
    }

}
