package com.hexin.demo.generator;

import lombok.extern.slf4j.Slf4j;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TypeConversionUtils {
    private static final Map<Integer, String> SQL_TYPE_TO_JAVA_TYPE = new HashMap<>();
    private static final Map<String, String> DB_TYPE_TO_JAVA_TYPE = new HashMap<>();
    
    static {
        // JDBC Types映射
        SQL_TYPE_TO_JAVA_TYPE.put(Types.BIT, "Boolean");
        SQL_TYPE_TO_JAVA_TYPE.put(Types.TINYINT, "Integer");
        SQL_TYPE_TO_JAVA_TYPE.put(Types.SMALLINT, "Integer");
        SQL_TYPE_TO_JAVA_TYPE.put(Types.INTEGER, "Integer");
        SQL_TYPE_TO_JAVA_TYPE.put(Types.BIGINT, "Long");
        SQL_TYPE_TO_JAVA_TYPE.put(Types.FLOAT, "Float");
        SQL_TYPE_TO_JAVA_TYPE.put(Types.REAL, "Float");
        SQL_TYPE_TO_JAVA_TYPE.put(Types.DOUBLE, "Double");
        SQL_TYPE_TO_JAVA_TYPE.put(Types.NUMERIC, "java.math.BigDecimal");
        SQL_TYPE_TO_JAVA_TYPE.put(Types.DECIMAL, "java.math.BigDecimal");
        SQL_TYPE_TO_JAVA_TYPE.put(Types.CHAR, "String");
        SQL_TYPE_TO_JAVA_TYPE.put(Types.VARCHAR, "String");
        SQL_TYPE_TO_JAVA_TYPE.put(Types.LONGVARCHAR, "String");
        SQL_TYPE_TO_JAVA_TYPE.put(Types.DATE, "java.time.LocalDate");
        SQL_TYPE_TO_JAVA_TYPE.put(Types.TIME, "java.time.LocalTime");
        SQL_TYPE_TO_JAVA_TYPE.put(Types.TIMESTAMP, "java.time.LocalDateTime");
        SQL_TYPE_TO_JAVA_TYPE.put(Types.BINARY, "byte[]");
        SQL_TYPE_TO_JAVA_TYPE.put(Types.VARBINARY, "byte[]");
        SQL_TYPE_TO_JAVA_TYPE.put(Types.LONGVARBINARY, "byte[]");
        SQL_TYPE_TO_JAVA_TYPE.put(Types.BLOB, "byte[]");
        SQL_TYPE_TO_JAVA_TYPE.put(Types.CLOB, "String");
        
        // 数据库特定类型映射（MySQL）
        DB_TYPE_TO_JAVA_TYPE.put("INT", "Integer");
        DB_TYPE_TO_JAVA_TYPE.put("INTEGER", "Integer");
        DB_TYPE_TO_JAVA_TYPE.put("TINYINT", "Integer");
        DB_TYPE_TO_JAVA_TYPE.put("SMALLINT", "Integer");
        DB_TYPE_TO_JAVA_TYPE.put("MEDIUMINT", "Integer");
        DB_TYPE_TO_JAVA_TYPE.put("BIGINT", "Long");
        DB_TYPE_TO_JAVA_TYPE.put("INT UNSIGNED", "Long");
        DB_TYPE_TO_JAVA_TYPE.put("INTEGER UNSIGNED", "Long");
        DB_TYPE_TO_JAVA_TYPE.put("TINYINT UNSIGNED", "Integer");
        DB_TYPE_TO_JAVA_TYPE.put("SMALLINT UNSIGNED", "Integer");
        DB_TYPE_TO_JAVA_TYPE.put("MEDIUMINT UNSIGNED", "Integer");
        DB_TYPE_TO_JAVA_TYPE.put("BIGINT UNSIGNED", "java.math.BigInteger");
        DB_TYPE_TO_JAVA_TYPE.put("DECIMAL", "java.math.BigDecimal");
        DB_TYPE_TO_JAVA_TYPE.put("DECIMAL UNSIGNED", "java.math.BigDecimal");
        DB_TYPE_TO_JAVA_TYPE.put("FLOAT", "Float");
        DB_TYPE_TO_JAVA_TYPE.put("DOUBLE", "Double");
        DB_TYPE_TO_JAVA_TYPE.put("BIT", "Boolean");
        DB_TYPE_TO_JAVA_TYPE.put("CHAR", "String");
        DB_TYPE_TO_JAVA_TYPE.put("VARCHAR", "String");
        DB_TYPE_TO_JAVA_TYPE.put("TINYTEXT", "String");
        DB_TYPE_TO_JAVA_TYPE.put("TEXT", "String");
        DB_TYPE_TO_JAVA_TYPE.put("MEDIUMTEXT", "String");
        DB_TYPE_TO_JAVA_TYPE.put("LONGTEXT", "String");
        DB_TYPE_TO_JAVA_TYPE.put("JSON", "String");
        DB_TYPE_TO_JAVA_TYPE.put("ENUM", "String");
        DB_TYPE_TO_JAVA_TYPE.put("SET", "String");
        DB_TYPE_TO_JAVA_TYPE.put("DATE", "java.time.LocalDate");
        DB_TYPE_TO_JAVA_TYPE.put("TIME", "java.time.LocalTime");
        DB_TYPE_TO_JAVA_TYPE.put("DATETIME", "java.time.LocalDateTime");
        DB_TYPE_TO_JAVA_TYPE.put("TIMESTAMP", "java.time.LocalDateTime");
        DB_TYPE_TO_JAVA_TYPE.put("YEAR", "Integer");
        DB_TYPE_TO_JAVA_TYPE.put("BINARY", "byte[]");
        DB_TYPE_TO_JAVA_TYPE.put("VARBINARY", "byte[]");
        DB_TYPE_TO_JAVA_TYPE.put("TINYBLOB", "byte[]");
        DB_TYPE_TO_JAVA_TYPE.put("BLOB", "byte[]");
        DB_TYPE_TO_JAVA_TYPE.put("MEDIUMBLOB", "byte[]");
        DB_TYPE_TO_JAVA_TYPE.put("LONGBLOB", "byte[]");
    }
    
    /**
     * 根据JDBC类型或数据库特定类型获取Java类型
     */
    public static String getJavaType(String dataType) {
        if (dataType == null || dataType.isEmpty()) {
            return "Object";
        }
        
        // 尝试先按数据库特定类型匹配
        String javaType = DB_TYPE_TO_JAVA_TYPE.get(dataType.toUpperCase());
        if (javaType != null) {
            return javaType;
        }
        
        // 尝试按JDBC类型代码匹配
        try {
            int jdbcType = Integer.parseInt(dataType);
            javaType = SQL_TYPE_TO_JAVA_TYPE.get(jdbcType);
            if (javaType != null) {
                return javaType;
            }
        } catch (NumberFormatException e) {
            // 忽略转换异常
        }
        
        // 默认返回Object
        log.warn("未能识别的数据类型: {}, 将使用Object类型", dataType);
        return "Object";
    }
}