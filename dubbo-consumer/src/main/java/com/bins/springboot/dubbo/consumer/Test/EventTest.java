package com.bins.springboot.dubbo.consumer.Test;

/**
 * @author hex1n
 * @date 2021/3/29 16:52
 * @description
 */
public class EventTest {
    public static void main(String[] args) throws InterruptedException {
        EventSource eventSource = new EventSource();
        eventSource.addListener(clickEvent -> System.out.println("tt"));
        eventSource.actionPerformed();
    }
}
