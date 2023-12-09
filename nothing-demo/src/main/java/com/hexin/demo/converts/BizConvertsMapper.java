package com.hexin.demo.converts;

import com.hexin.demo.entity.LikeInfo;
import com.hexin.demo.entity.LikeInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author hex1n
 * @Date 2023/12/7/21:18
 * @Description
 **/
@Mapper
public interface BizConvertsMapper {
    BizConvertsMapper INSTANCE = Mappers.getMapper(BizConvertsMapper.class);

    LikeInfoVO likeToLikeVO(LikeInfo likeInfo);

    LikeInfo likeVOToLike(LikeInfoVO likeInfoVO);
}
