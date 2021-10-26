package com.hexin.demo.Test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author hex1n
 * @Date 2021/8/19 12:31
 * @Description
 */
@Slf4j
public class ThreadPoolTest {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors() * 2,
                1000L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000),
                new DefaultThreadFactory("t1"), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        }
        );
        ArrayList<Object> objects = Lists.newArrayList();
        // 时间戳转秒
        int i = 139085 / 1000;
        System.out.println(i);
        int a = 0;
        Integer b = new Integer(0);
        System.out.println(a == b);

        // 只出现过一次是数字,不开辟新空间
        int[] arr = new int[]{0, 1, 2, 2, 1, 1, 4, 3, 4, 5};
//        Arrays.sort(arr);
        sort(arr);
        log.info(JSON.toJSONString(arr));
        for (int i1 = 0; i1 < arr.length; i1++) {
            if (i1 == arr.length - 1 && arr[i1 - 1] != arr[i1]) {
                System.out.println(arr[i1]);
            }
            if (i1 > 0 && arr[i1 - 1] != arr[i1] && (i1 + 1) < arr.length - 1 && arr[i1] != arr[i1 + 1]) {
                System.out.print(arr[i1]);
            }
            if (i1 == 0 && arr[i1] != arr[i1 + 1]) {
                System.out.print(arr[i1]);
            }
        }
        String str = "asdfghjkl";
        char[] chars = str.toCharArray();
        int ii = 0;
        int jj = chars.length - 1;
        char temp = ' ';
        while (ii < jj) {
            temp = chars[ii];
            chars[ii++] = chars[jj];
            chars[jj--] = temp;
        }
        String s = String.copyValueOf(chars);
        System.out.println(s);

    }

    // 冒泡
    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i -1; j++) {   // 这里说明为什么需要-1
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void sortArr(int[] arr){

    }

}
