package com.hexin.demo.generator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnInfo {
    private String columnName;
    private String propertyName;
    private String dataType;
    private String javaType;
    private String jdbcType;
    private Integer columnSize;
    private Boolean nullable;
    private String comment;
    private Boolean isPrimaryKey;
}