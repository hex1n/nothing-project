package com.hexin.demo.executor;

import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hex1n
 */
public interface TracerRunnable extends Runnable {
    Logger log = LoggerFactory.getLogger(TracerRunnable.class);

    void doRun();

    @Override
    @Trace
    default void run() {
        try {
            ActiveSpan.tag("operation", "traced-run");
            doRun();
            ActiveSpan.tag("status", "success");
        } catch (Exception e) {
            log.error("Error occurred during traced operation", e);
            ActiveSpan.tag("status", "error");
        }
    }
}