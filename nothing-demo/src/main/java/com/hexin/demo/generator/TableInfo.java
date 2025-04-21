package com.hexin.demo.generator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableInfo {
    private String tableName;
    private String className;
    private String comment;
    private List<ColumnInfo> columns;
    private ColumnInfo primaryKey;
    private List<String> imports;
}