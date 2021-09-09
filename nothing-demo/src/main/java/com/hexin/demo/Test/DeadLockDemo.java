package com.hexin.springboot.dubbo.consumer.Test;

/**
 * @author hex1n
 * @date 2021/3/30 11:01
 * @description
 */
public class DeadLockDemo {
    private static String LOCK_A="A";
    private static String LOCK_B="B";

    public static void main(String[] args) {
        new DeadLockDemo().deadLock();
    }

    private void deadLock(){
        Thread t1 = new Thread(() -> {
            synchronized (LOCK_A){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK_B){
                    System.out.println("B");
                }
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (LOCK_B){
                synchronized (LOCK_A){
                    System.out.println("A");
                }
            }
        });
        t1.start();
        t2.start();
    }
}
