package com.hexin.demo.mapper;

import com.hexin.demo.entity.LikeCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
*  @author hex1n
*/
@Mapper
public interface LikeCountMapper {

    int insertLikeCount(LikeCount likeCount);

    int updateLikeCount(LikeCount likeCount);

    int update(LikeCount likeCount);

    List<LikeCount> queryLikeCount(LikeCount likeCount);

    LikeCount queryLikeCountLimit1(LikeCount likeCount);

}