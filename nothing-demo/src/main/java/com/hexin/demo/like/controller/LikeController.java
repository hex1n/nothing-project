package com.hexin.demo.like.controller;

import com.hexin.demo.ResultBean;
import com.hexin.demo.entity.LikeInfo;
import com.hexin.demo.like.service.LikeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @Author hex1n
 * @Date 2023/12/3/20:37
 * @Description
 **/
@RestController
@RequestMapping("/likes")
public class LikeController {

    @Resource
    private LikeService likeService;

    @PostMapping("/likeUnLike")
    public ResultBean likeUnLike(@RequestBody LikeInfo likeInfo) {
        checkLikesParam(likeInfo);
        String likeRedisKey = generateRedisKey(likeInfo.getLikedType(), likeInfo.getLikedSubjectId(), likeInfo.getLikedUserId());
        return likeService.likeUnLike(likeRedisKey, likeInfo);
    }

    private String generateRedisKey(String likedType, Long likedSubjectId, Long likedUserId) {
        List<String> params = Arrays.asList(likedType, String.valueOf(likedSubjectId), String.valueOf(likedUserId));
        StringBuffer sb = new StringBuffer("likes");
        for (String param : params) {
            sb.append(":").append(param);
        }
        return sb.toString();
    }

    private void checkLikesParam(LikeInfo likeInfo) {
        String likedType = likeInfo.getLikedType();
        String likeUserId = String.valueOf(likeInfo.getLikeUserId());
        String likedUserId = String.valueOf(likeInfo.getLikedUserId());
        String likedSubjectId = String.valueOf(likeInfo.getLikedSubjectId());
        if (StringUtils.isAnyBlank(likedType, likeUserId, likedUserId, likedSubjectId)) {
            throw new IllegalArgumentException("参数校验失败");
        }
        List<String> likeStatus = Arrays.asList("1", "-1");
        if (!likeStatus.contains(likeInfo.getLikedStatus())) {
            throw new IllegalArgumentException("参数校验失败");
        }
    }
}
