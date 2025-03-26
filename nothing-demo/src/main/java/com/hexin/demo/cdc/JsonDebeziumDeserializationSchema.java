package com.hexin.demo.cdc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexin.demo.util.GsonUtils;
import com.ververica.cdc.debezium.DebeziumDeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.util.Collector;
import org.apache.kafka.connect.source.SourceRecord;

import java.sql.Struct;
import java.util.HashMap;
import java.util.Map;

public class JsonDebeziumDeserializationSchema implements DebeziumDeserializationSchema<String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void deserialize(SourceRecord record, Collector<String> out) throws Exception {
        //Struct value = (Struct) record.value();
        System.out.println(record.value().toString());
        //Struct source = value.get("source");
        //Struct after = value.getStruct("after");
        //Struct before = value.getStruct("before");
        //
        //Map<String, Object> result = new HashMap<>();
        //result.put("database", source.getString("db"));
        //result.put("table", source.getString("table"));
        //result.put("operation", value.getString("op"));
        //result.put("ts_ms", value.getInt64("ts_ms"));
        //
        //if (before != null) {
        //    result.put("before", structToMap(before));
        //}
        //if (after != null) {
        //    result.put("after", structToMap(after));
        //}
        //
        //out.collect(objectMapper.writeValueAsString(result));
    }

    private Map<String, Object> structToMap(Struct struct) {
        //Map<String, Object> result = new HashMap<>();
        //for (Field field : struct.schema().fields()) {
        //    result.put(field.name(), struct.get(field));
        //}
        System.out.println(GsonUtils.toJSONString(struct));
        return new HashMap<>();
        //return result;
    }

    @Override
    public TypeInformation<String> getProducedType() {
        return TypeInformation.of(String.class);
    }

}