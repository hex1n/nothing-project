package com.bins.springboot.dubbo.consumer.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 点赞表 实体类
 *
 * @author
 * @date 2021-04-28
 */
@Data
public class LikeInfo  implements Serializable {

    private static final long serialVersionUID = 292623669848346654L;
    private String id;
    //被点赞的用户id
    private String likedMemberId;
    //点赞的用户id
//    @NotBlank(message = "点赞用户id不能为空", groups = {LikeValid.class})
    private String likedPostId;
    //点赞类型id
    private String likedTypeId;
    //点赞类型 1 名片 2 标签
    private String likedType;
    //点赞状态，0取消，1点赞
    private Integer likedStatus;
    //操作时间
    private String operationTime;
    //删除0 正常 1 删除
    private String delFlag;

    // 点赞总数
    private long likeCount;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String createBy;
    private String updateBy;

}
