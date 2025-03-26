package com.hexin.demo.cdc;

import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
@Slf4j
public class FlinkCdcListener {

    @Resource
    private FlinkCdcConfig flinkCdcConfig;

    private StreamExecutionEnvironment env;
    private JobExecutionResult jobExecutionResult;

    @PostConstruct
    public void init() throws Exception {
        // 创建 Flink 执行环境
        env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 配置 MySQL CDC Source
        MySqlSource<String> mySqlSource = MySqlSource.<String>builder()
            .hostname(flinkCdcConfig.getHostname())
            .port(flinkCdcConfig.getPort())
            .databaseList(flinkCdcConfig.getDatabase())
            .tableList(flinkCdcConfig.getDatabase() + "." + flinkCdcConfig.getTable())
            .username(flinkCdcConfig.getUsername())
            .password(flinkCdcConfig.getPassword())
            .serverTimeZone(flinkCdcConfig.getServerTimeZone())
            .deserializer(new JsonDebeziumDeserializationSchema()) // 自定义反序列化
            .build();

        // Instead of env.addSource(mySqlSource), use:
        log.info("MySQL CDC source configured successfully");  // 添加源配置成功日志

        env.fromSource(mySqlSource, WatermarkStrategy.noWatermarks(), "MySQL Source")
                .setParallelism(1)
                .map(value -> {
                    log.info("Received CDC event: {}", value);  // 添加数据接收日志
                    return value;
                })
                .addSink(new CustomMySqlSink());

        log.info("Starting Flink CDC job...");  // 添加启动日志

        // 创建一个新的线程来执行 Flink 作业
        Thread flinkJobThread = new Thread(() -> {
            try {
                env.fromSource(mySqlSource, WatermarkStrategy.noWatermarks(), "MySQL Source")
                        .setParallelism(1)
                        .map(value -> {
                            log.info("Received CDC event: {}", value);
                            return value;
                        })
                        .addSink(new CustomMySqlSink());

                log.info("Executing Flink CDC job...");
                jobExecutionResult = env.execute("MySQL CDC Job");
                log.info("Flink CDC job executed successfully: {}", jobExecutionResult.getJobID());
            } catch (Exception e) {
                log.error("Error executing Flink CDC job", e);
                throw new RuntimeException("Failed to execute Flink job", e);
            }
        }, "flink-cdc-thread");

        // 设置为守护线程
        flinkJobThread.setDaemon(true);
        // 启动线程
        flinkJobThread.start();

        log.info("Flink CDC thread started");

    }

    public boolean isJobRunning() {
        return jobExecutionResult != null && jobExecutionResult.getJobID() != null;
    }
}