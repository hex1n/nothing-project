package com.bins.springboot.dubbo.consumer.countdownlatch;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author hex1n
 * @date 2021/6/9 20:57
 * @description
 */
@Data
public class Info {

    private Map<String, List<String>> a;
    private Map<String, List<String>> b;
    private Map<String, List<String>> c;
}
