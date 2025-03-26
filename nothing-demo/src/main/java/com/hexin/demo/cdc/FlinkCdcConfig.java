package com.hexin.demo.cdc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "flink.cdc")
@Data
public class FlinkCdcConfig {
    private String hostname;
    private Integer port;
    private String username;
    private String password;
    private String database;
    private String table;
    private String serverTimeZone = "Asia/Shanghai";
}