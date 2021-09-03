package com.bins.springboot.dubbo.consumer.mapper;

import com.bins.springboot.dubbo.consumer.entity.LikeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface LikeInfoMapper {

    LikeInfo getLastOperation(@Param("likedMemberId") String likedMemberId, @Param("likedPostId") String likedPostId,
                              @Param("likedType") String likedType, @Param("likedTypeId") String likedTypeId);

//    List<LikeInfoVo> getLikeDetails(@Param("likedType") String likedType, @Param("likedTypeId") String likedTypeId);

//    List<LikeInfoVo> getLikeLabelDetails(@Param("likedMemberId") String likedMemberId, @Param("likedType") String likedType);

//    List<LikeInfo> getIsLike2sbSth(@Param("like") LikeCountDTO likeCount);
}
