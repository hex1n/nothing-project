package com.hexin.demo.test;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * @Author hex1n
 * @Date 2022/9/15/11:33
 * @Description
 **/
public class BigFileTest {

    public static void main(String[] args) {
        logMemory();


    }

    private static void logMemory() {
        System.out.println("最大内存: " + Runtime.getRuntime()
                .maxMemory() / 1048576 + "Mb");
        System.out.println("总内存:" + Runtime.getRuntime()
                .totalMemory() / 1048576 + "Mb");
        System.out.println("空闲内存:" + +Runtime.getRuntime()
                .freeMemory() / 1048576 + "Mb");
    }

    public static void splitFileAndRead() throws Exception {
        // 先将大文件拆分成小文件
        List<File> fileList = splitLargeFile("temp/test.txt");
        // 创建一个 最大线程数为 10，队列最大数为 100 的线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 60l, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100));
        List<Future> futureList = Lists.newArrayList();
        for (File file : fileList) {
            Future<?> future = threadPoolExecutor.submit(() -> {
                try (Stream inputStream = Files.lines(file.toPath(), StandardCharsets.UTF_8)) {
                    inputStream.forEach(o -> {
                        // 模拟执行业务
                        try {
                            TimeUnit.MILLISECONDS.sleep(10L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            futureList.add(future);
        }
        for (Future future : futureList) {
            // 等待所有任务执行结束
            future.get();
        }
        threadPoolExecutor.shutdown();


    }

    private static List<File> splitLargeFile(String largeFileName) throws IOException {
        /*LineIterator fileContents = FileUtils.lineIterator(new File(largeFileName), StandardCharsets.UTF_8.name());
        List<String> lines = Lists.newArrayList();
        // 文件序号
        int num = 1;
        List<File> files = Lists.newArrayList();
        while (fileContents.hasNext()) {
            String nextLine = fileContents.nextLine();
            lines.add(nextLine);
            // 每个文件 10w 行数据
            if (lines.size() == 100000) {
                createSmallFile(lines, num, files);
                num++;
            }
        }
        // lines 若还有剩余，继续执行结束
        if (!lines.isEmpty()) {
            // 继续执行
            createSmallFile(lines, num, files);
        }*/
        List<File> files = Lists.newArrayList();
        return files;
    }

    private static void createSmallFile(List<String> lines, int num, List<File> files) {

    }
}
