package com.bins.springboot.dubbo.consumer.constant;

import lombok.Getter;

/**
 * @author hex1n
 * @date 2021/4/27 16:55
 * @description
 */
@Getter
public enum LikedStatusEnum {

    LIKE(1, "点赞"),
    UNLIKE(-1, "取消点赞/未点赞"),
    ;
    private Integer code;

    private String msg;

    LikedStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
