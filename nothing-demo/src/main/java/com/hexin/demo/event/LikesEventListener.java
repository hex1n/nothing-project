package com.hexin.demo.event;

import cn.hutool.core.collection.CollUtil;
import com.hexin.demo.common.BizTemplateCallback;
import com.hexin.demo.common.BusinessTransactionTemplate;
import com.hexin.demo.common.TransactionPropagationEnum;
import com.hexin.demo.converts.BizConvertsMapper;
import com.hexin.demo.entity.LikeCount;
import com.hexin.demo.entity.LikeInfo;
import com.hexin.demo.entity.LikeInfoVO;
import com.hexin.demo.mapper.LikeCountMapper;
import com.hexin.demo.mapper.LikeInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @Author hex1n
 * @Date 2023/12/7/22:05
 * @Description
 **/
@Component
@Slf4j
public class LikesEventListener extends AbstractEventMessageListener {


    @Resource
    private BusinessTransactionTemplate businessTransactionTemplate;
    @Resource
    private LikeInfoMapper likeInfoMapper;
    @Resource
    private LikeCountMapper likeCountMapper;

    @Override
    public <T> void onEventMessage(EventMessage<T> eventMessage) {
        log.info(this.getClass().getSimpleName() + "-" + Thread.currentThread().getName() + "======onMessage=====");

        LikeInfoVO likeInfoVO = (LikeInfoVO) eventMessage.getPayload();

        Long likeTotalCount = likeInfoVO.getTotalLikes();
        LikeInfo likeInfo = BizConvertsMapper.INSTANCE.likeVOToLike(likeInfoVO);
        List<LikeCount> likeCounts = getLikeCounts(likeInfo);
        businessTransactionTemplate.executeTransaction(new BizTemplateCallback() {
            @Override
            protected void process() {
                if (Objects.equals("1",likeInfoVO.getLikedStatus())){
                    likeInfo.setCreateBy("admin");
                    likeInfo.setUpdateBy("admin");
                    likeInfo.setOperationTime(String.valueOf(System.nanoTime()));
                    likeInfo.setCreateTime(LocalDateTime.now());
                    likeInfoMapper.insertLikeInfo(likeInfo);
                }else {
                    likeInfoMapper.deleteLikeInfo(likeInfo);
                }
                if (CollUtil.isEmpty(likeCounts)) {
                    LikeCount likeCount = buildLikeCount(likeInfo, likeTotalCount);
                    likeCountMapper.insertLikeCount(likeCount);
                } else {
                    LikeCount likeCount = likeCounts.get(0);
                    LikeCount likeCountUpdate = new LikeCount();
                    likeCountUpdate.setId(likeCount.getId());
                    likeCountUpdate.setLikeTotal(likeTotalCount);
                    likeCountMapper.updateLikeCount(likeCountUpdate);
                }
            }
        }, TransactionPropagationEnum.DEFAULT);
    }

    private List<LikeCount> getLikeCounts(LikeInfo likeInfo) {
        LikeCount likeCountParam = LikeCount.builder()
                .likedType(likeInfo.getLikedType())
                .likedUserId(likeInfo.getLikedUserId())
                .likedSubjectId(likeInfo.getLikedSubjectId())
                .build();
        return likeCountMapper.queryLikeCount(likeCountParam);
    }

    private LikeCount buildLikeCount(LikeInfo likeInfo, Long likeTotalCount) {
        return LikeCount.builder()
                .likedType(likeInfo.getLikedType())
                .likedUserId(likeInfo.getLikedUserId())
                .likedSubjectId(likeInfo.getLikedSubjectId())
                .likeTotal(likeTotalCount)
                .createBy("admin")
                .updateBy("admin")
                .createTime(LocalDateTime.now())
                .build();
    }
}
