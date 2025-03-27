package com.hexin.demo.cdc;

import com.hexin.demo.util.GsonUtils;
import com.ververica.cdc.connectors.oceanbase.OceanBaseSource;
import com.ververica.cdc.connectors.oceanbase.table.OceanBaseDeserializationSchema;
import com.ververica.cdc.connectors.oceanbase.table.OceanBaseRecord;
import com.ververica.cdc.connectors.oceanbase.table.StartupMode;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class FlinkCdcListener {

    @Resource
    private FlinkCdcConfig flinkCdcConfig;

    @PostConstruct
    public void start() throws Exception {
        // 创建 Flink 执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 配置 OceanBase CDC 源
        DataStream<String> sourceStream = env
                .addSource(OceanBaseSource.<String>builder()
                        .hostname(flinkCdcConfig.getHostname())  // OceanBase 地址
                        .port(2881)           // MySQL 模式的端口
                        .username(flinkCdcConfig.getUsername()) // OceanBase 用户名
                        .password(flinkCdcConfig.getPassword()) // 密码
                        .databaseName(flinkCdcConfig.getDatabase())     // 监听的数据库
                        //.tableList(flinkCdcConfig.getTable()) // 监听的表
                        .tenantName("sys")
                        .startupMode(StartupMode.INITIAL)
                        .compatibleMode("acd")
                        .jdbcDriver("com.mysql.jdbc.Driver")
                        .deserializer(new OceanBaseDeserializationSchema() {
                            @Override
                            public TypeInformation getProducedType() {
                                return TypeInformation.of(new TypeHint<OceanBaseRecord>() {});
                            }

                            @Override
                            public void deserialize(OceanBaseRecord oceanBaseRecord, Collector collector) throws Exception {
                                System.out.println(GsonUtils.toJSONString(oceanBaseRecord)+"ooooooooooo");
                            }
                        }) // 反序列化为 JSON
                        .serverTimeZone("UTC")
                        //.dialect(OceanBaseDialect.MYSQL) // OceanBase MySQL 模式
                        .tableName(flinkCdcConfig.getTable())
                        .build());

        // 处理数据流
        sourceStream.map(data -> {
            System.out.println("收到数据变更：" + data);
            return data;
        });

        // 启动 Flink 任务
        env.execute("Flink CDC Listener for OceanBase");
    }
}