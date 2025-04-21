<#-- entity.ftl -->
package ${package.entityPackage};

import lombok.Data;
<#if strategyConfig.lombokModel>
    import lombok.AllArgsConstructor;
    import lombok.NoArgsConstructor;
    import lombok.Builder;
</#if>
import java.io.Serializable;

/**
* ${tableName} 实体类
*/
@Data
<#if strategyConfig.lombokModel>
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
</#if>
public class ${className} implements Serializable {

private static final long serialVersionUID = 1L;
<#-- 生成字段 -->
<#list columns as column>
    <#if column.comment?? && column.comment != "">
        /**
        * ${column.comment}
        */
    </#if>
    private ${column.javaType!"String"} ${column.propertyName!"field" + column_index};

</#list>
<#-- 如果没有使用lombok，生成getter和setter方法 -->
<#if !strategyConfig.lombokModel>
    <#list columns as column>
        <#if column.javaType?? && column.propertyName??>
            public ${column.javaType} get${column.propertyName?cap_first}() {
            return ${column.propertyName};
            }

            public void set${column.propertyName?cap_first}(${column.javaType} ${column.propertyName}) {
            this.${column.propertyName} = ${column.propertyName};
            }
        </#if>
    </#list>
</#if>
}