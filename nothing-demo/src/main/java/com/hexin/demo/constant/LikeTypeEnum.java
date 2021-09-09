package com.hexin.springboot.dubbo.consumer.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author hex1n
 * @date 2021/4/30 15:18
 * @description
 */
@Getter
public enum LikeTypeEnum {

    BUSINESS_CARD("1", "名片点赞"),
    IMPRESSION_LABEL("2", "客户印象标签"),
    COUNTRYMEN_LABEL("3", "同乡"),
    MATERIAL_VIDEO("4", "素材视频"),
    MORNING_PAPER("5", "早报"),
    MATERIAL_ARTICLES("6", "文章"),
    MORNING_PAPER_COMMENT("7", "早报点评"),
    ;

    private String val;
    private String desc;

    LikeTypeEnum(String val, String desc) {
        this.val = val;
        this.desc = desc;
    }

    public static String getNameByVal(String val) {
        String name = Arrays.stream(LikeTypeEnum.values()).filter(likeTypeEnum -> Objects.equals(val, likeTypeEnum.getVal()))
                .map(LikeTypeEnum::name)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("未点找到该点赞类型配置"));
        return name;
    }
}
