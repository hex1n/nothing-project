package com.hexin.demo.cdc;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

//@Component
@Slf4j
public class FlinkCdcListener1 {

    @Resource
    private FlinkCdcConfig flinkCdcConfig;

    private StreamExecutionEnvironment env;
    private JobExecutionResult jobExecutionResult;

    @PostConstruct
    public void init() throws Exception {
        // 创建 Flink 执行环境
        env = StreamExecutionEnvironment.getExecutionEnvironment();

        //SourceFunction<String> oceanBaseSource =
        //        OceanBaseSource.<String>builder()
        //                .startupOptions(StartupOptions.initial())
        //                .hostname(flinkCdcConfig.getHostname())
        //                .port(flinkCdcConfig.getPort())
        //                .username(flinkCdcConfig.getUsername())
        //                .password(flinkCdcConfig.getPassword())
        //                .compatibleMode("mysql")
        //                .jdbcDriver("com.mysql.cj.jdbc.Driver")
        //                .tenantName("sys")
        //                .databaseName("^test$")
        //                .tableName("^t_user$")
        //                .logProxyHost("172.18.0.2")
        //                .logProxyPort(2882)
        //                .rsList("127.0.0.1:2882:2881")
        //                .serverTimeZone("+08:00")
        //                .deserializer(new DebeziumDeserializationSchema<String>() {
        //                    @Override
        //                    public void deserialize(SourceRecord sourceRecord, Collector<String> collector) throws Exception {
        //                        System.out.println(GsonUtils.toJSONString(sourceRecord)+"ppppppppppppp");
        //                    }
        //
        //                    @Override
        //                    public TypeInformation<String> getProducedType() {
        //                        return TypeInformation.of(String.class);
        //                    }
        //                })
        //                .build();
        // enable checkpoint
        //env.enableCheckpointing(3000);

        EnvironmentSettings settings = EnvironmentSettings.newInstance().build();
        TableEnvironment tableEnv = TableEnvironment.create(settings);

        String createTableSQL = "CREATE TABLE t_user (" +
                "  id INT NOT NULL," +  // 使用 INT
                "  name STRING," +  // 使用 STRING，去掉 DEFAULT NULL
                "  age INT," +  // 使用 INT，去掉 DEFAULT NULL
                "  PRIMARY KEY (id) NOT ENFORCED" +  // 主键定义
                ") WITH (" +
                "  'connector' = 'oceanbase-cdc'," +
                "  'scan.startup.mode' = 'initial'," +
                "  'username' = 'root'," +
                "  'password' = 'root123'," +
                "  'tenant-name' = 'sys'," +
                "  'database-name' = 'test'," +
                "  'table-name' = 't_user'," +
                "  'hostname' = '127.0.0.1'," +
                "  'port' = '2881'," +
                "  'rootserver-list' = '127.0.0.1:2882:2881'," +
                "  'logproxy.host' = '127.0.0.1'," +
                "  'logproxy.port' = '2983'" +
                ");";


        tableEnv.executeSql(createTableSQL);

        // 从表 t_user 中读取快照数据和 binlog 数据
        String selectSQL = "SELECT * FROM t_user";
        tableEnv.executeSql(selectSQL);
        //env.addSource(oceanBaseSource).print().setParallelism(1);
        //env.execute("Print OceanBase Snapshot + Change Events");


    }
}