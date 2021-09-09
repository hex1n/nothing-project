package com.hexin.springboot.dubbo.consumer.mapper;

import com.hexin.springboot.dubbo.consumer.entity.LikeCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface LikeCountMapper {

    Integer getLikeCount(@Param("likeCount") LikeCount likeCount);

   LikeCount selectByParamForUpdate(LikeCount likeCountParam);

    List<LikeCount> getLikeCounts(@Param("likeCount") LikeCount likeCount);

//    List<LikeCount> getByLikedTypeIds(@Param("likeCount") LikeCountDTO likeCount);
}
