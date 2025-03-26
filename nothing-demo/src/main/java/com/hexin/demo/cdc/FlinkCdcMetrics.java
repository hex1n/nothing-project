package com.hexin.demo.cdc;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class FlinkCdcMetrics {
    private final MeterRegistry registry;
    private final Counter insertCounter;
    private final Counter updateCounter;
    private final Counter deleteCounter;

    public FlinkCdcMetrics(MeterRegistry registry) {
        this.registry = registry;
        this.insertCounter = registry.counter("cdc.operations", "type", "insert");
        this.updateCounter = registry.counter("cdc.operations", "type", "update");
        this.deleteCounter = registry.counter("cdc.operations", "type", "delete");
    }

    public void incrementInsert() {
        insertCounter.increment();
    }

    public void incrementUpdate() {
        updateCounter.increment();
    }

    public void incrementDelete() {
        deleteCounter.increment();
    }
}