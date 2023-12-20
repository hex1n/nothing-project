package com.hexin.demo.common;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import com.hexin.demo.constant.ErrorCode;
import com.hexin.demo.entity.WebResponse;
import com.hexin.demo.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author hex1n
 * @Date 2023/12/7/22:50
 * @Description
 **/
@Component
@Slf4j
public class BizServiceTemplate {

    public <P, R> WebResponse<R> process(P param, String methodName, ServiceExecutor<P, R> serviceExecutor) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        String requestMessage = MessageFormat.format("[{0}]--->methodName:[{1}]#requestParam:{2}", this.getClass().getSimpleName(), methodName, param);
        log.info(requestMessage);
        ConcurrentMap<Object, Object> context = Maps.newConcurrentMap();
        try {
            serviceExecutor.paramCheck(param, context);
            R result = serviceExecutor.process(param, context);
            long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            String msg = MessageFormat.format("业务处理 [{0}]--->methodName:[{1}] 耗时:[{2}ms]", this.getClass().getSimpleName(), methodName, elapsed);
            log.info(msg);
            return WebResponse.buildSuccessWithData(result);
        } catch (BizException bizException) {
            long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            String msg = MessageFormat.format("业务处理异常 [{0}]--->methodName:[{1}] 耗时:[{2}ms]", this.getClass().getSimpleName(), methodName, elapsed);
            log.error(msg, bizException);
            return WebResponse.buildFailResult(bizException.getErrorCode(), bizException.getMessage());
        } catch (Exception e) {
            long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            String msg = MessageFormat.format("系统异常[{0}]--->methodName:[{1}] 耗时:[{2}ms]", this.getClass().getSimpleName(), methodName, elapsed);
            log.error(msg, e);
            return WebResponse.buildFailResult(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getDesc());
        }


    }

    public interface ServiceExecutor<P, R> {
        void paramCheck(P param, Map<Object, Object> context);

        R process(P param, Map<Object, Object> context);
    }
}
