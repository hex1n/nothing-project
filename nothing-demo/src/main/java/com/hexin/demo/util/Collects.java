package com.hexin.demo.util;

import com.google.common.collect.Lists;
import org.springframework.util.Assert;

import java.util.List;
import java.util.function.Consumer;

/**
 * @Author hex1n
 * @Date 2023/12/15/20:46
 * @Description
 **/
public class Collects {
    private Collects() {

    }

    public static <T> int batchProcess(List<T> list, Consumer<List<T>> processor, int batchSize) {
        Assert.notEmpty(list, "processor list cannot be empty");
        if (list.size() >= batchSize) {
            processor.accept(list);
        } else {
            Lists.partition(list, batchSize).forEach(processor);
        }
        return list.size();
    }
}
