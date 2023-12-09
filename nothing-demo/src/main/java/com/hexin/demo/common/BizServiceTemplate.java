package com.hexin.demo.common;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author hex1n
 * @Date 2023/12/7/22:50
 * @Description
 **/
@Component
@Slf4j
public class BizServiceTemplate<R, P> {

    public R process(P param, String methodName, ServiceExecutor<R, P> serviceExecutor) {
        String requestMessage = MessageFormat.format("[{0}]--->methodName:[{1}]#requestParam:{2}", this.getClass().getSimpleName(), methodName, param);
        log.info(requestMessage);
        HashMap<@Nullable Object, @Nullable Object> context = Maps.newHashMap();
        serviceExecutor.paramCheck(param, context);
        try {
            return serviceExecutor.process(param, context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public interface ServiceExecutor<R, P> {
        void paramCheck(P param, Map<Object, Object> context);

        R process(P param, Map<Object, Object> context);
    }
}
