package com.hexin.demo.listener;


import com.hexin.demo.service.MessageService;
import io.debezium.config.Configuration;
import io.debezium.embedded.Connect;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Slf4j
@Service
public class DebeziumSourceEventListener {

    //This will be used to run the engine asynchronously
    private final Executor executor;

    //DebeziumEngine serves as an easy-to-use wrapper around any Debezium connector
    //private final DebeziumEngine<ChangeEvent<String, String>> debeziumEngine;
    private final DebeziumEngine<ChangeEvent<SourceRecord, SourceRecord>> debeziumEngine;

    //Inject product service
    private final MessageService messageService;


    public DebeziumSourceEventListener(
            Configuration mysqlConnector, MessageService messageService) {
        // Create a new single-threaded executor.
        this.executor = Executors.newSingleThreadExecutor();

        // Create a new DebeziumEngine instance.
        this.debeziumEngine = DebeziumEngine.create(Connect.class).using(mysqlConnector.asProperties()).notifying(new Consumer<ChangeEvent<SourceRecord, SourceRecord>>() {
                                                                                                                      @Override
                                                                                                                      public void accept(ChangeEvent<SourceRecord, SourceRecord> sourceRecordChangeEvent) {
                                                                                                                          System.out.println(sourceRecordChangeEvent);
                                                                                                                      }
                                                                                                                  }
        ).build();

        // Set the product service.
        this.messageService = messageService;
    }


    private void receiveChangeEvent(String value, String name) {
        System.out.println(value + "====" + name);
        return;
        /*if (Objects.nonNull(value)) {
            Map<String, Object> payload = DebeziumUtils.getPayload(value);
            String handleType = DebeziumUtils.getHandleType(payload);
            if (!("NONE".equals(handleType) || "READ".equals(handleType))) {
                ChangeData changeData = DebeziumUtils.getChangeData(payload);
                Map<String, Object> data;
                if ("DELETE".equals(handleType)) {
                    data = changeData.getBefore();
                } else {
                    data = changeData.getAfter();
                }
                Message build = Message.builder()
                        .data(data)
                        .dbType("MySQL")
                        .database(String.valueOf(changeData.getSource().get("db")))
                        .table(String.valueOf(changeData.getSource().get("table")))
                        .handleType(handleType)
                        .build();
                log.info("【Debezium-" + name + "】" + build.toString());
                messageService.sendMessage(build);
            }
        }*/
    }

    @PostConstruct
    private void start() {
        this.executor.execute(debeziumEngine);
    }

    @PreDestroy
    private void stop() throws IOException {
        if (this.debeziumEngine != null) {
            this.debeziumEngine.close();
        }
    }
}