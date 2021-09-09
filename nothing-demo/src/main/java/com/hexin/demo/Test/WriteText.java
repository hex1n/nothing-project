package com.hexin.springboot.dubbo.consumer.Test;

import java.io.*;

/**
 * @Author hex1n
 * @Date 2021/9/8 15:22
 * @Description
 */
public class WriteText {
    public static void writeToText(String inputStr, String path, String fileName) throws IOException {
        // 生成的文件路径
        String generateFilePath = path + fileName + ".txt";
        File file = new File(generateFilePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        // write 解决中文乱码问题
        // FileWriter fw = new FileWriter(file, true);
        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        BufferedWriter bw = new BufferedWriter(fw);
        String[] split = inputStr.split(",");
        for (int i = 0; i < split.length; i++) {
            bw.write(split[i]);
            bw.newLine();
        }
        bw.flush();
        bw.close();
        fw.close();
    }
}
