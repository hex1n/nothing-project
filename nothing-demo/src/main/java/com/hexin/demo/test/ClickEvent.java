package com.hexin.demo.test;

import java.util.EventObject;

/**
 * @author hex1n
 * @date 2021/3/29 15:52
 * @description
 */
public class ClickEvent extends EventObject {

    private EventSource eventSource;
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ClickEvent(Object source) {
        super(source);
        this.eventSource = (EventSource) source;
    }
}
