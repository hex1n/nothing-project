package com.hexin.demo.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * @Author hex1n
 * @Date 2023/12/7/22:03
 * @Description
 **/
public abstract class AbstractEventMessageListener {

    @Async(value = "eventExecutor")
    @EventListener
    public abstract <T> void onEventMessage(EventMessage<T> eventMessage);
}
