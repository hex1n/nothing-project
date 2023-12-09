package com.hexin.demo.like.service.impl;

import com.hexin.demo.WebResponse;
import com.hexin.demo.converts.BizConvertsMapper;
import com.hexin.demo.entity.LikeInfo;
import com.hexin.demo.entity.LikeInfoVO;
import com.hexin.demo.event.BizEventPublisher;
import com.hexin.demo.like.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author hex1n
 * @Date 2023/12/3/20:38
 * @Description
 **/
@Service
@Slf4j
public class LikeServiceImpl implements LikeService {

    @Resource
    private RedissonClient redissonClient;


    @Resource
    private BizEventPublisher bizEventPublisher;

    @Override
    public WebResponse<LikeInfoVO> likeUnLike(String likeRedisKey, LikeInfo likeInfo) {

        LikeInfoVO likeInfoVO = BizConvertsMapper.INSTANCE.likeToLikeVO(likeInfo);
        RSet<Object> likesRedis = redissonClient.getSet(likeRedisKey);
        if (likesRedis.add(likeInfo.getLikeUserId())) {
            likeInfoVO.setLikedStatus("1");
        } else {
            likesRedis.remove(likeInfo.getLikeUserId());
            likeInfoVO.setLikedStatus("-1");
        }
        long likeTotalCount = getRedisLikeCount(likeRedisKey);
        log.info("total count:" + likeTotalCount);
        likeInfoVO.setTotalLikes(likeTotalCount);
        bizEventPublisher.publishEvent(likeInfoVO);
        return WebResponse.success(likeInfoVO);
    }


    public long getRedisLikeCount(String likesRedisKey) {
        RSet<String> likes = redissonClient.getSet(likesRedisKey);
        return likes.size();
    }
}
