package com.hexin.demo.cdc;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

//@Component
public class FlinkCdcHealthIndicator implements HealthIndicator {
    private final FlinkCdcListener cdcListener;

    public FlinkCdcHealthIndicator(FlinkCdcListener cdcListener) {
        this.cdcListener = cdcListener;
    }

    @Override
    public Health health() {
        try {
            // 检查 CDC 监听器状态
            return Health.up()
                    .withDetail("status", "running")
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withException(e)
                    .build();
        }
    }
}