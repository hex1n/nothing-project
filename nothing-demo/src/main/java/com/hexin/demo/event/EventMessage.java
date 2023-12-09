package com.hexin.demo.event;

import org.springframework.context.ApplicationEvent;

/**
 * @Author hex1n
 * @Date 2023/12/7/22:00
 * @Description
 **/
public class EventMessage<T> extends ApplicationEvent {

    private T payload;

    public EventMessage(Object source, T payload) {
        super(source);
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }
}
