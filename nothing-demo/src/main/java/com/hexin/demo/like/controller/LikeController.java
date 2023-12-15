package com.hexin.demo.like.controller;

import com.hexin.demo.entity.WebResponse;
import com.hexin.demo.common.BizServiceTemplate;
import com.hexin.demo.entity.LikeInfo;
import com.hexin.demo.entity.LikeInfoVO;
import com.hexin.demo.exception.BizException;
import com.hexin.demo.like.service.LikeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    @Resource
    private BizServiceTemplate bizServiceTemplate;

    @PostMapping("/likeUnLike")
    public WebResponse<LikeInfoVO> likeUnLike(@RequestBody LikeInfo likeInfo) {
        return bizServiceTemplate.process(likeInfo, "likeUnLike", new BizServiceTemplate.ServiceExecutor<LikeInfo, LikeInfoVO>() {
            @Override
            public void paramCheck(LikeInfo param, Map context) {
                checkLikesParam(param);
            }

            @Override
            public LikeInfoVO process(LikeInfo param, Map context) {
                String likeRedisKey = generateRedisKey(likeInfo.getLikedType(), likeInfo.getLikedSubjectId(), likeInfo.getLikedUserId());
                return likeService.likeUnLike(likeRedisKey, likeInfo);
            }
        });
    }

    @GetMapping("/queryLikesInfos")
    public WebResponse queryLikesInfos(@RequestBody LikeInfo likeInfo) {

        checkLikesParam(likeInfo);
        String likeRedisKey = generateRedisKey(likeInfo.getLikedType(), likeInfo.getLikedSubjectId(), likeInfo.getLikedUserId());
        return WebResponse.buildSuccessWithoutData();
    }

    private String generateRedisKey(String likedType, Long likedSubjectId, Long likedUserId) {
        List<String> params = Arrays.asList(likedType, String.valueOf(likedSubjectId), String.valueOf(likedUserId));
        StringBuilder sb = new StringBuilder("likes");
        for (String param : params) {
            sb.append(":").append(param);
        }
        return sb.toString();
    }

    private void checkLikesParam(LikeInfo likeInfo) {
        String likedType = likeInfo.getLikedType();
        Long likeUserId = likeInfo.getLikeUserId();
        Long likedUserId = likeInfo.getLikedUserId();
        Long likedSubjectId = likeInfo.getLikedSubjectId();
        if (StringUtils.isBlank(likedType) || likeUserId == null || likedUserId == null || likedSubjectId == null) {
            throw new BizException("点赞参数校验失败");
        }
    }
}
