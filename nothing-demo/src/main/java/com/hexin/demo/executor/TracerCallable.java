package com.hexin.demo.executor;

import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * @author hex1n
 */
public interface TracerCallable<V> extends Callable<V> {
    Logger log = LoggerFactory.getLogger(TracerCallable.class);

    V doCall() throws Exception;

    @Override
    @Trace
    default V call() throws Exception {
        try {
            ActiveSpan.tag("operation", "traced-call");
            V result = doCall();
            ActiveSpan.tag("status", "success");
            return result;
        } catch (Exception e) {
            ActiveSpan.tag("status", "error");
            log.error("Error occurred during traced operation", e);
            throw e;
        }
    }
}