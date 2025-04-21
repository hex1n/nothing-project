package com.hexin.demo.generator;

import java.util.List;
import java.util.function.Consumer;

public class BatchUtils {
    /**
     * 将大批量数据分批处理
     * @param list 数据列表
     * @param batchSize 批次大小
     * @param consumer 处理逻辑
     */
    public static <T> void batchProcess(List<T> list, int batchSize, Consumer<List<T>> consumer) {
        if (list == null || list.isEmpty()) {
            return;
        }
        
        int total = list.size();
        int batchCount = (total + batchSize - 1) / batchSize;
        
        for (int i = 0; i < batchCount; i++) {
            int fromIndex = i * batchSize;
            int toIndex = Math.min((i + 1) * batchSize, total);
            List<T> subList = list.subList(fromIndex, toIndex);
            consumer.accept(subList);
        }
    }
}