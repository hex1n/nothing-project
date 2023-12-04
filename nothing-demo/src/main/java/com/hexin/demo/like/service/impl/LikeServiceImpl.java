package com.hexin.demo.like.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.hexin.demo.ResultBean;
import com.hexin.demo.common.BusinessTransactionTemplate;
import com.hexin.demo.common.TransactionPropagationEnum;
import com.hexin.demo.entity.LikeCount;
import com.hexin.demo.entity.LikeInfo;
import com.hexin.demo.entity.LikeInfoVO;
import com.hexin.demo.like.service.LikeService;
import com.hexin.demo.mapper.LikeCountMapper;
import com.hexin.demo.mapper.LikeInfoMapper;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @Author hex1n
 * @Date 2023/12/3/20:38
 * @Description
 **/
@Service
public class LikeServiceImpl implements LikeService {

    @Resource
    private RedissonClient redissonClient;
    @Resource
    private BusinessTransactionTemplate businessTransactionTemplate;
    @Resource
    private LikeInfoMapper likeInfoMapper;
    @Resource
    private LikeCountMapper likeCountMapper;

    @Override
    public ResultBean likeUnLike(String likeRedisKey, LikeInfo likeInfo) {
        RSet<Object> likesRedis = redissonClient.getSet(likeRedisKey);
        if (Objects.equals(likeInfo.getLikedStatus(), "1")) {
            likesRedis.add(likeInfo.getLikeUserId());
        } else {
            likesRedis.remove(likeInfo.getLikeUserId());
        }
        LikeInfoVO likeInfoVO = new LikeInfoVO();
        long likeTotalCount = getLikeCount(likeRedisKey);
        System.out.println("total count:" + likeTotalCount);

        businessTransactionTemplate.executeTransaction(() -> {
            likeInfo.setCreateBy("admin");
            likeInfo.setUpdateBy("admin");
            likeInfo.setOperationTime(String.valueOf(System.nanoTime()));
            likeInfo.setCreateTime(LocalDateTime.now());

            likeInfoMapper.insertLikeInfo(likeInfo);
            LikeCount likeCountParam = LikeCount.builder()
                    .likedType(likeInfo.getLikedType())
                    .likedUserId(likeInfo.getLikedUserId())
                    .likedSubjectId(likeInfo.getLikedSubjectId())
                    .build();
            List<LikeCount> likeCounts = likeCountMapper.queryLikeCount(likeCountParam);
            if (CollUtil.isEmpty(likeCounts)) {
                LikeCount likeCount = LikeCount.builder()
                        .likedType(likeInfo.getLikedType())
                        .likedUserId(likeInfo.getLikedUserId())
                        .likedSubjectId(likeInfo.getLikedSubjectId())
                        .likeTotal(likeTotalCount)
                        .createBy("admin")
                        .updateBy("admin")
                        .createTime(LocalDateTime.now())
                        .build();
                likeCountMapper.insertLikeCount(likeCount);
            } else {
                LikeCount likeCount = likeCounts.get(0);
                LikeCount likeCountUpdate = new LikeCount();
                likeCountUpdate.setId(likeCount.getId());
                likeCountUpdate.setLikeTotal(likeTotalCount);
                likeCountMapper.updateLikeCount(likeCountUpdate);
            }
            BeanUtils.copyProperties(likeInfo, likeInfoVO);
            likeInfoVO.setTotalLikes(likeTotalCount);

        }, TransactionPropagationEnum.DEFAULT);
        return ResultBean.success(likeInfoVO);
    }

    public long getLikeCount(String likesRedisKey) {
        RSet<String> likes = redissonClient.getSet(likesRedisKey);
        return likes.size();
    }
}
