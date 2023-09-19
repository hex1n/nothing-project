package com.hexin.demo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Author hex1n
 * @Date 2023/9/19/10:35
 * @Description
 **/
@Component
@Slf4j
public class TestListener {

    @EventListener(classes = ApplicationReadyEvent.class)
    public void test() {
        log.info("ApplicationReadyEvent=========================");
    }


}
