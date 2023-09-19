package com.hexin.demo.test;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author hex1n
 * @date 2021/3/30 10:12
 * @description
 */
public class SemaphoreDemo {
    private static Semaphore semaphore = new Semaphore(10);

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread((() -> {
                System.out.println(Thread.currentThread().getName() + "--来到停车场");
                if (semaphore.availablePermits() == 0) {
                    System.out.println("车位不足，请赖心等待");
                }
                try {
                    semaphore.acquire(1);
                    System.out.println(Thread.currentThread().getName() + "--成功进入停车场");
                    Thread.sleep(new Random().nextInt(10000));
                    System.out.println(Thread.currentThread().getName() + "--离开停车场");
                    semaphore.release(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            })
            ).start();
        }
    }
}
