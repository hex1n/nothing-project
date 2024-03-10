package com.hexin.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.collect.Lists;
import com.hexin.demo.util.GsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author hex1n
 * @Date 2023/8/8/12:45
 * @Description
 **/
@Slf4j
public class EasyExcelListener extends AnalysisEventListener {

    private static final int BATCH_COUNT = 20000;
    private List<Object> dataList = Lists.newArrayList();

    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        dataList.add(o);
        if (dataList.size() >= BATCH_COUNT) {
            saveData();
            dataList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();  // 处理最后一批数据
    }

    private void saveData() {
        // todo
        for (Object o : dataList) {
            log.info("row data:{}", GsonUtils.toJSONString(o));
        }
    }
}
