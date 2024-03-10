package com.hexin.demo.util;


import com.hexin.demo.entity.ChangeData;

import java.util.Map;
import java.util.Objects;

/**
 * @author hex1n
 */
public class DebeziumUtils {

    public static Map<String, Object> getPayload(String value) {
        Map<String, Object> map = GsonUtils.toMap(value);
        return GsonUtils.toMap(GsonUtils.toJSONString(map.get("payload")));
    }

    public static String getHandleType(Map<String, Object> payload) {
        String op = GsonUtils.toJSONString(payload.get("op"));
        if (Objects.nonNull(op)) {
            switch (op) {
                case "r":
                    return "READ";
                case "c":
                    return "CREATE";
                case "u":
                    return "UPDATE";
                case "d":
                    return "DELETE";
                default:
                    return "NONE";
            }
        } else {
            return "NONE";
        }

    }

    public static ChangeData getChangeData(Map<String, Object> payload) {
        return ChangeData.builder()
                .after(GsonUtils.toMap(GsonUtils.toJSONString(payload.get("after"))))
                .source(GsonUtils.toMap(GsonUtils.toJSONString(payload.get("source"))))
                .before(GsonUtils.toMap(GsonUtils.toJSONString(payload.get("before"))))
                .build();
    }


}