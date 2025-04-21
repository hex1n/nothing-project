package com.hexin.demo.generator;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class TypeHandler {
    private static final Map<String, JavaTypeInfo> TYPE_MAPPING = new ConcurrentHashMap<>();
    
    @Data
    @Builder
    public static class JavaTypeInfo {
        private String javaType;
        private String fullClassName;
        private boolean needImport;
    }
    
    static {
        // 基本类型映射
        registerType("INT", "Integer", "java.lang.Integer", false);
        registerType("BIGINT", "Long", "java.lang.Long", false);
        registerType("DECIMAL", "BigDecimal", "java.math.BigDecimal", true);
        registerType("DATETIME", "LocalDateTime", "java.time.LocalDateTime", true);
        // 更多类型映射...
    }
    
    private static void registerType(String dbType, String javaType, String fullClassName, boolean needImport) {
        TYPE_MAPPING.put(dbType.toUpperCase(), JavaTypeInfo.builder()
            .javaType(javaType)
            .fullClassName(fullClassName)
            .needImport(needImport)
            .build());
    }
    
    public static JavaTypeInfo getJavaTypeInfo(String dbType) {
        return TYPE_MAPPING.getOrDefault(dbType.toUpperCase(), 
            JavaTypeInfo.builder()
                .javaType("String")
                .fullClassName("java.lang.String")
                .needImport(false)
                .build());
    }
}