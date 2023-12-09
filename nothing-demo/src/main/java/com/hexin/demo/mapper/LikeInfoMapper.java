package com.hexin.demo.mapper;

import com.hexin.demo.entity.LikeInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/**
*  @author author
*/
@Mapper
public interface LikeInfoMapper {

    int insertLikeInfo(LikeInfo likeInfo);

    int updateLikeInfo(LikeInfo likeInfo);

    int update(LikeInfo likeInfo);

    List<LikeInfo> queryLikeInfo(LikeInfo likeInfo);

    LikeInfo queryLikeInfoLimit(LikeInfo likeInfo);

    int deleteLikeInfo(LikeInfo likeInfo);
}