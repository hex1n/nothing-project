package com.hexin.demo.config;

import com.hexin.demo.entity.DatabaseData;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author hex1n
 */
@Configuration
@ConfigurationProperties(prefix = "debezium")
@Slf4j
public class MysqlDebeziumConfig {

    @Setter
    private DatabaseData database;

    @Bean
    public io.debezium.config.Configuration mysqlConnector() {
        String osName = System.getProperty("os.name");
        String path = "/";
        if (osName.startsWith("Windows")) {
            path = "D:/";
        } else if (osName.startsWith("Linux") | osName.startsWith("Mac OS")) {
            path = "/Users/hex1n/Development/";
        }

        database.setOffsetPath(path + database.getOffsetPath());
        database.setHistoryPath(path + database.getHistoryPath());

        Properties props = new Properties();
        props.setProperty("name", database.getName());
        props.setProperty("connector.class", "io.debezium.connector.mysql.MySqlConnector");
        props.setProperty("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore");
        props.setProperty("offset.storage.file.filename", database.getOffsetPath());
        props.setProperty("offset.flush.interval.ms", "600000");
        props.setProperty("database.hostname", database.getHost());
        props.setProperty("database.port", database.getPort());
        props.setProperty("database.user", database.getUsername());
        props.setProperty("database.password", database.getPassword());
        props.setProperty("database.server.id", database.getServerId());
        props.setProperty("database.server.name", "dlwlrma_connector" + database.getName());
        props.setProperty("database.history",
                "io.debezium.relational.history.FileDatabaseHistory");
        props.setProperty("database.history.file.filename", database.getHistoryPath());
        String tableList = String.join(",", database.getTable());
        props.setProperty("table.include.list", tableList);
        String columnList = String.join(",", database.getColumn());
        props.setProperty("column.include.list", columnList);
        props.setProperty("database.include.list", database.getName());
        props.setProperty("topic.prefix", "cdc");
        props.setProperty("errors.log.include.messages", "true");
        props.setProperty("database.history.skip.unparseable.ddl", "true");
        props.setProperty("converter.schemas.enable", "false"); // don't include schema in message



        return io.debezium.config.Configuration.from(props);
    }

}
