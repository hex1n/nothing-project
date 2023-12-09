package com.hexin.demo.like.controller;

import com.hexin.demo.ResultBean;
import com.hexin.demo.WebResponse;
import com.hexin.demo.common.BizServiceTemplate;
import com.hexin.demo.entity.LikeInfo;
import com.hexin.demo.entity.LikeInfoVO;
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
        return (WebResponse<LikeInfoVO>) bizServiceTemplate.process(likeInfo, "likeUnLike", new BizServiceTemplate.ServiceExecutor<WebResponse<LikeInfoVO>, LikeInfo>() {
            @Override
            public void paramCheck(LikeInfo param, Map context) {
                checkLikesParam(param);
            }

            @Override
            public WebResponse<LikeInfoVO> process(LikeInfo param, Map context) {
                String likeRedisKey = generateRedisKey(likeInfo.getLikedType(), likeInfo.getLikedSubjectId(), likeInfo.getLikedUserId());
                return likeService.likeUnLike(likeRedisKey, likeInfo);
            }
        });
    }

    @GetMapping("/queryLikesInfos")
    public ResultBean queryLikesInfos(@RequestBody LikeInfo likeInfo) {

        checkLikesParam(likeInfo);
        String likeRedisKey = generateRedisKey(likeInfo.getLikedType(), likeInfo.getLikedSubjectId(), likeInfo.getLikedUserId());
        //return likeService.likeUnLike(likeRedisKey, likeInfo);
        return null;
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
        String likeUserId = String.valueOf(likeInfo.getLikeUserId());
        String likedUserId = String.valueOf(likeInfo.getLikedUserId());
        String likedSubjectId = String.valueOf(likeInfo.getLikedSubjectId());
        if (StringUtils.isAnyBlank(likedType, likeUserId, likedUserId, likedSubjectId)) {
            throw new IllegalArgumentException("参数校验失败");
        }
    }
}
