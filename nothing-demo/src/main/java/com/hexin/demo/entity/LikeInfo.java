package com.hexin.demo.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author hex1n
 */
@Data
@Accessors
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LikeInfo implements Serializable {

    private static final long serialVersionUID = 1701599230559L;


    /**
     * 主键
     * id
     * isNullAble:0
     */
    private Long id;

    /**
     * 点赞的用户id
     * isNullAble:0
     */
    private Long likeUserId;

    /**
     * 被点赞的用户id
     * isNullAble:0
     */
    private Long likedUserId;

    /**
     * 点赞类型id
     * isNullAble:0
     */
    private Long likedSubjectId;

    /**
     * 点赞类型
     * isNullAble:0
     */
    private String likedType;

    /**
     * 操作时间
     * isNullAble:0,defaultVal:-1
     */
    private String operationTime;

    /**
     * 删除0 正常 1 删除
     * isNullAble:0,defaultVal:0
     */
    private String delFlag;

    /**
     * 创建人
     * isNullAble:0
     */
    private String createBy;

    /**
     * 创建时间
     * isNullAble:0
     */
    private java.time.LocalDateTime createTime;

    /**
     * 更新人
     * isNullAble:0
     */
    private String updateBy;

    /**
     * 更新时间
     * isNullAble:0,defaultVal:CURRENT_TIMESTAMP
     */
    private java.time.LocalDateTime updateTime;

}
