package com.hexin.demo.mapper;

import com.hexin.demo.entity.LikeCount;
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
