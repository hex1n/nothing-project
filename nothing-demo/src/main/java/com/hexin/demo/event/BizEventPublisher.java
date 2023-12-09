package com.hexin.demo.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author hex1n
 * @Date 2023/12/7/22:06
 * @Description
 **/
@Component
public class BizEventPublisher {

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    public <T> void publishEvent(final T payload) {
        EventMessage<T> eventMessage = new EventMessage<>(this, payload);
        applicationEventPublisher.publishEvent(eventMessage);
    }
}
