package com.hexin.demo.executor;

import lombok.Getter;

import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author hexin
 * @date 2024/09/13 16:14
 * @description 自动扩容Queue
 **/
@Getter
public class ResizableLinkedBlockingQueue<E> extends LinkedBlockingQueue<E> {
    private volatile int capacity;

    public ResizableLinkedBlockingQueue(int capacity) {
        super(capacity);
        this.capacity = capacity;
    }

    @Override
    public synchronized boolean offer(E e) {
        return size() < capacity && super.offer(e);
    }

    synchronized void setCapacity(int newCapacity) {
        this.capacity = newCapacity;
        while (size() > newCapacity) {
            try {
                remove();
            } catch (NoSuchElementException ignored) {
                break;
            }
        }
    }

    @Override
    public int remainingCapacity() {
        return capacity - size();
    }

    public int getCapacity() {
        return capacity;
    }

    public double getUsageRatio() {
        return size() / (double) capacity;
    }
}