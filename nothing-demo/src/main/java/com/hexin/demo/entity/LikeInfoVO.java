package com.hexin.demo.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @Author hex1n
 * @Date 2023/12/3/22:23
 * @Description
 **/
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class LikeInfoVO extends LikeInfo implements Serializable {

    private Long totalLikes;
}
