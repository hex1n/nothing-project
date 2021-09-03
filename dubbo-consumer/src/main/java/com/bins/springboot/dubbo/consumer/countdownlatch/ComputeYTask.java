package com.bins.springboot.dubbo.consumer.countdownlatch;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author hex1n
 * @date 2021/6/7 16:16
 * @description
 */
@AllArgsConstructor
@Data
public class ComputeYTask implements Callable<Integer> {

    private CountDownLatch count;
    private int threadNum;

    @Override
    public Integer call() throws Exception {
        try {
            Thread.sleep(1000);
            System.out.println("计算 Y轴数据 threadNum:"+threadNum);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (count != null) {
                count.countDown();
            }
        }
        return 5000+threadNum;
    }
}
