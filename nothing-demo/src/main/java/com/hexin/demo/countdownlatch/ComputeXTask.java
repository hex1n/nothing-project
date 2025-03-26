package com.hexin.demo.countdownlatch;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author hex1n
 * @date 2021/6/7 16:16
 * @description
 */
@AllArgsConstructor
@Data
public class ComputeXTask implements Callable<Integer> {

    private CountDownLatch count;
    private int threadNum;
    private Info info;


    @Override
    public Integer call() throws Exception {
        try {
            Map<String, List<String>> a = info.getA();
            Map<String, List<String>> b = info.getB();
            Map<String, List<String>> c = info.getC();
            System.out.println(a);
            System.out.println(b);
            System.out.println(c);
            System.out.println("计算 X 轴数据");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (count != null) {
                count.countDown();
            }
        }
        return 4000+threadNum;
    }
}
