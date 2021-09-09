package com.hexin.springboot.dubbo.consumer.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * @author hex1n
 * @date 2021/3/29 15:53
 * @description
 */
public class EventSource {
    private Semaphore semaphore = new Semaphore(1);
    private final List<ClickEventListener> listeners = new ArrayList<>();

    public void addListener(ClickEventListener listener) throws InterruptedException {
        semaphore.acquire(1);
        listeners.add(listener);
        semaphore.release(1);
    }

    public void removeListener(ClickEventListener listener) throws InterruptedException {
        semaphore.acquire(1);
        if (!listeners.isEmpty()) {
            listeners.remove(listener);
        }
        semaphore.release(1);
    }
    protected  void actionPerformed() throws InterruptedException {
        semaphore.acquire(1);
        ClickEvent clickEvent = new ClickEvent(this);
        for (ClickEventListener listener : listeners) {
            listener.clickEvent(clickEvent);
        }
        semaphore.release(1);
    }
}
