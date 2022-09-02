package com.hexin.demo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author hex1n
 * @Date 2021/10/21 9:35
 * @Description
 */
@Data
public class ResultBean implements Serializable {
    private static final long serialVersionUID = 9218812351323900849L;
    private int code;
    private String message;
    private Object data;
    public ResultBean() {
    }

    public ResultBean(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public ResultBean(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public ResultBean(String message, Object data) {
        this.code = 200;
        this.message = message;
        this.data = data;
    }

    public static ResultBean build(int code, String message, Object data) {
        return new ResultBean(code, message, data);
    }

    public static ResultBean success(Object data) {
        return new ResultBean("success", data);
    }

    public static ResultBean success(String message, Object data) {
        return new ResultBean(message, data);
    }

    public static ResultBean success() {
        return new ResultBean("success", (Object) "");
    }

    public static ResultBean error(Object data) {
        return new ResultBean(500, "error", data);
    }

    public static ResultBean error(String message) {
        return new ResultBean(500, message, (Object) "");
    }

    public static ResultBean error() {
        return new ResultBean(500, "error", (Object) "");
    }

    public static ResultBean error(int code, String message) {
        return new ResultBean(code, message);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        t1.start();

        String name = Thread.currentThread().getState().name();
        System.out.println(name);
        // 打断正在睡眠的线程,其它线程可以使用 interrupt 方法打断正在睡眠的线程，这时 sleep 方法会抛出 InterruptedException
        t1.interrupt();
       /* Thread t2 = new Thread(() -> {
            int n = 0;
            while (true) {
                n++;
                // 调用 yield 会让当前线程从 Running 进入 Runnable 就绪状态，然后调度执行其它线程
                // 通过有无让出代码观察n的变化快慢
                Thread.currentThread().yield();
            }
        }, "t2");
        Thread.sleep(1);
        t2.start();
        System.out.println(Thread.currentThread().getState().name());*/
    }
    }
