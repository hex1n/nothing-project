package com.hexin.demo.mapper;

import com.hexin.demo.rule.Rule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author hex1n
 * @Date 2024/5/26/00:31
 * @Description
 **/
@Mapper
public interface TRuleMapper {

    @Select("select * from t_rule where business_type=#{businessType} and active=1")
    List<Rule> findByBusinessType(@Param("businessType") String businessType);
}
