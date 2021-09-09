package com.hexin.springboot.dubbo.consumer.constant;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @Author hex1n
 * @Date 2021/9/2 12:59
 * @Description
 */
public class RedisKeyUtils {

    public static final String DELIMITER = ":";
    public static String LIKE_INFO_KEY = "LIKE_INFO";
    public static String LIKE_INFO_COUNT_KEY = "LIKE_INFO_COUNT";

    public static String getLikeKey(String likedMemberId, String likedPostId, String likedType, String likedTypeId) {
        List<String> params = Arrays.asList(likedMemberId, likedPostId, likedType, likedTypeId);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            if (StringUtils.isBlank(params.get(i))) {
                continue;
            }
            sb.append(params.get(i));
            if (i < params.size() - 1) {
                sb.append(DELIMITER);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String likeOrDisLikeKey = getLikeKey("1", "2", "5", "4");
        System.out.println(likeOrDisLikeKey);
    }
}
