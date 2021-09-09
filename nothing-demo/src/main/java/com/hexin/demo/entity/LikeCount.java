package com.hexin.demo.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 点赞统计表 实体类
 *
 * @author
 * @date 2021-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeCount implements Serializable {

    private static final long serialVersionUID = 391878732884195265L;

    private String id;
    //被点赞的用户id
    private String likedMemberId;
    //点赞类型id
    private String likedTypeId;
    //点赞类型 1 名片 2 标签
    private String likedType;
    //点赞总数
    private Integer likeTotal;
    //删除0 正常 1 删除
    private String delFlag;

}
