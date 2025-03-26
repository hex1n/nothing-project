package com.hexin.demo.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author hexin
 * @date 2023/12/25 13:33
 * @description
 **/
public class GsonUtils {
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final JsonSerializer<LocalDateTime> DATETIME_SERIALIZER
            = (obj, type, ctx) -> new JsonPrimitive(DATETIME_FORMATTER.format(obj));
    private static final JsonSerializer<LocalDate> DATE_SERIALIZER
            = (obj, type, ctx) -> new JsonPrimitive(DATE_FORMATTER.format(obj));
    private static final JsonSerializer<LocalTime> TIME_SERIALIZER
            = (obj, type, ctx) -> new JsonPrimitive(TIME_FORMATTER.format(obj));

    private static final JsonDeserializer<LocalDateTime> DATETIME_DESERIALIZER
            = (json, type, ctx) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DATETIME_FORMATTER);
    private static final JsonDeserializer<LocalDate> DATE_DESERIALIZER
            = (json, type, ctx) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DATE_FORMATTER);
    private static final JsonDeserializer<LocalTime> TIME_DESERIALIZER
            = (json, type, ctx) -> LocalTime.parse(json.getAsJsonPrimitive().getAsString(), TIME_FORMATTER);

    private static final Gson GSON;

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.disableHtmlEscaping();
        builder.enableComplexMapKeySerialization();
        // builder.excludeFieldsWithoutExposeAnnotation();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        builder.registerTypeAdapter(LocalDateTime.class, DATETIME_SERIALIZER);
        builder.registerTypeAdapter(LocalDate.class, DATE_SERIALIZER);
        builder.registerTypeAdapter(LocalTime.class, TIME_SERIALIZER);
        builder.registerTypeAdapter(LocalDateTime.class, DATETIME_DESERIALIZER);
        builder.registerTypeAdapter(LocalDate.class, DATE_DESERIALIZER);
        builder.registerTypeAdapter(LocalTime.class, TIME_DESERIALIZER);
        GSON = builder.create();
    }

    public static Type makeType(Type rawType, Type... typeArguments) {
        return TypeToken.getParameterized(rawType, typeArguments).getType();
    }

    public static String toString(Object value) {
        if (Objects.isNull(value)) {
            return null;
        }
        if (value instanceof String) {
            return (String) value;
        }
        return toJSONString(value);
    }

    public static String toJSONString(Object value) {
        return GSON.toJson(value);
    }

    public static String toPrettyString(Object value) {
        return GSON.newBuilder().setPrettyPrinting().create().toJson(value);
    }

    public static JsonElement fromJavaObject(Object value) {
        JsonElement result = null;
        if (Objects.nonNull(value) && (value instanceof String)) {
            result = parseObject((String) value);
        } else {
            result = GSON.toJsonTree(value);
        }
        return result;
    }

    @SneakyThrows
    public static JsonElement parseObject(String content) {
        return JsonParser.parseString(content);
    }

    public static JsonElement getJsonElement(JsonObject node, String name) {
        return node.get(name);
    }

    public static JsonElement getJsonElement(JsonArray node, int index) {
        return node.get(index);
    }

    @SneakyThrows
    public static <T> T toObject(JsonElement node, Class<T> clazz) {
        return GSON.fromJson(node, clazz);
    }

    @SneakyThrows
    public static <T> T toObject(JsonElement node, Type type) {
        return GSON.fromJson(node, type);
    }

    public static <T> T toObject(JsonElement node, TypeToken<?> typeToken) {
        return toObject(node, typeToken.getType());
    }

    public static <E> List<E> toList(JsonElement node, Class<E> clazz) {
        return toObject(node, makeType(List.class, clazz));
    }

    public static List<Object> toList(JsonElement node) {
        return toObject(node, new TypeToken<List<Object>>() {
        }.getType());
    }

    public static <V> Map<String, V> toMap(JsonElement node, Class<V> clazz) {
        return toObject(node, makeType(Map.class, String.class, clazz));
    }

    public static Map<String, Object> toMap(JsonElement node) {
        return toObject(node, new TypeToken<Map<String, Object>>() {
        }.getType());
    }

    @SneakyThrows
    public static <T> T toObject(String content, Class<T> clazz) {
        return GSON.fromJson(content, clazz);
    }

    @SneakyThrows
    public static <T> T toObject(String content, Type type) {
        return GSON.fromJson(content, type);
    }

    public static <T> T toObject(String content, TypeToken<?> typeToken) {
        return toObject(content, typeToken.getType());
    }

    public static <E> List<E> toList(String content, Class<E> clazz) {
        return toObject(content, makeType(List.class, clazz));
    }

    public static List<Object> toList(String content) {
        return toObject(content, new TypeToken<List<Object>>() {
        }.getType());
    }

    public static <V> Map<String, V> toMap(String content, Class<V> clazz) {
        return toObject(content, makeType(Map.class, String.class, clazz));
    }

    public static Map<String, Object> toMap(String content) {
        return toObject(content, new TypeToken<Map<String, Object>>() {
        }.getType());
    }


}
