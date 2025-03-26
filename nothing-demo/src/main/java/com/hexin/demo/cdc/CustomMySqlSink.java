package com.hexin.demo.cdc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

@Slf4j
public class CustomMySqlSink implements SinkFunction<String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void invoke(String value, Context context) {
        try {
            JsonNode jsonNode = objectMapper.readTree(value);
            String operation = jsonNode.get("operation").asText();
            String database = jsonNode.get("database").asText();
            String table = jsonNode.get("table").asText();
            System.out.println(operation + " " + database + " " + table);
            switch (operation) {
                case "c": // 插入
                    handleInsert(jsonNode);
                    break;
                case "u": // 更新
                    handleUpdate(jsonNode);
                    break;
                case "d": // 删除
                    handleDelete(jsonNode);
                    break;
            }
        } catch (Exception e) {
            log.error("Error processing CDC event: {}", value, e);
        }
    }

    private void handleInsert(JsonNode jsonNode) {
        JsonNode after = jsonNode.get("after");
        log.info("Insert: {}", after);
        // 处理插入逻辑
    }

    private void handleUpdate(JsonNode jsonNode) {
        JsonNode before = jsonNode.get("before");
        JsonNode after = jsonNode.get("after");
        log.info("Update from {} to {}", before, after);
        // 处理更新逻辑
    }

    private void handleDelete(JsonNode jsonNode) {
        JsonNode before = jsonNode.get("before");
        log.info("Delete: {}", before);
        // 处理删除逻辑
    }
}