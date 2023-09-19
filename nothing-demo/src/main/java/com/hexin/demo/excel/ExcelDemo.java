package com.hexin.demo.excel;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Sheet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author hex1n
 * @Date 2023/8/6/21:34
 * @Description
 **/
@Slf4j
public class ExcelDemo {

    public static String filePath = "/Users/hex1n/Desktop/test.xlsx";

    public static void main(String[] args) {
        //fastexcelCreatExcel();
        fastexcelReadExcel();
        //StopWatch stopWatch = new StopWatch();
        //stopWatch.start();
        //EasyExcelListener easyExcelListener = new EasyExcelListener();
        //EasyExcel.read(filePath, easyExcelListener).sheet().doRead();
        //log.info("time:{}", stopWatch.getTime());
    }


    /**
     * fastexcel
     */
    public static void fastexcelCreatExcel() {
        try (OutputStream os = Files.newOutputStream(Paths.get(filePath))) {

            Workbook wb = new Workbook(os, "DemoExcel", "1.0");
            Worksheet ws = wb.newWorksheet("Sheet 1");

            StopWatch watch = new StopWatch();
            watch.start();

            ws.value(0, 0, "Column 1");
            ws.value(0, 1, "Column 2");
            ws.value(0, 2, "Column 3");
            ws.value(0, 3, "Column 4");
            ws.value(0, 4, "Column 5");
            ws.value(0, 5, "Column 6");
            ws.value(0, 6, "Column 7");
            ws.value(0, 7, "Column 8");
            ws.value(0, 8, "Column 9");
            ws.value(0, 9, "Column 10");

            for (int i = 1; i < 1_000_000; i++) {
                String value = "data-" + i;
                ws.value(i, 0, i + "-Column1");
                ws.value(i, 1, value + "-Column2");
                ws.value(i, 2, value + "-Column3");
                ws.value(i, 3, value + "-Column4");
                ws.value(i, 4, value + "-Column5");
                ws.value(i, 5, value + "-Column6");
                ws.value(i, 6, value + "-Column7");
                ws.value(i, 7, value + "-Column8");
                ws.value(i, 8, value + "-Column9");
                ws.value(i, 9, value + "-Column10");
            }

            wb.finish();
            watch.stop();

            log.info("Processing time ::{}ms ", watch.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * fastexcel
     */
    public static void fastexcelReadExcel() {
         int BATCH_COUNT = 5678;
         AtomicInteger count = new AtomicInteger();
        List<@Nullable Object> rowDataList = Lists.newArrayList();
        try (InputStream is = Files.newInputStream(Paths.get(filePath));
             ReadableWorkbook wb = new ReadableWorkbook(is)) {

            StopWatch watch = new StopWatch();
            watch.start();

            Sheet sheet = wb.getFirstSheet();
            long size = sheet.openStream().count();
            log.info("excel total size:{}",size);
            List<String> headers = Lists.newArrayList();
            sheet.openStream().limit(200000).forEach(r -> {
                count.addAndGet(1);
                int cellCount = r.getCellCount();
                Map<String, String> rowData = Maps.newHashMapWithExpectedSize(cellCount);
                for (int i = 0; i < cellCount; i++) {
                    if (r.getRowNum() == 1) {
                        headers.add(r.getCellRawValue(i).get());
                    }
                    String str = r.getCellAsString(i).orElse(null);
                    rowData.put(headers.get(i), str);
                }
                rowDataList.add(rowData);
                if (rowDataList.size() >= BATCH_COUNT) {
                    log.info("rowDataList size:{}",rowDataList.size());
                    for (Object o : rowDataList) {
                        log.info("row data:{}", JSON.toJSONString(o));
                    }
                    rowDataList.clear();
                }
                else if (count.get()==200000){
                    log.info("last row data size: {},count:{}",rowDataList.size(),count.get());
                    for (Object o : rowDataList) {
                        log.info("last row data:{}", JSON.toJSONString(o));
                    }
                    rowDataList.clear();
                }
            });
            watch.stop();
            log.info("Processing time :: {}ms, count:{}", watch.getTime(),count.get());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
