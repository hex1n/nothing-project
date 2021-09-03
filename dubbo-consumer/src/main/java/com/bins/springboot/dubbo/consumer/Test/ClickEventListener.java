package com.bins.springboot.dubbo.consumer.Test;

import java.util.EventListener;

/**
 * @author hex1n
 * @date 2021/3/29 15:55
 * @description
 */
public interface ClickEventListener extends EventListener {
    void clickEvent(ClickEvent clickEvent);
}
