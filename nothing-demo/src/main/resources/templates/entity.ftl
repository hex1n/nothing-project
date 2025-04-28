package ${entityPackage};

<#if strategyConfig.lombokModel>
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
</#if>
<#if imports??>
<#list imports as import>
import ${import};
</#list>
</#if>

/**
 * ${tableName}<#if comment??> - ${comment}</#if>
 *
 * @author generator
 */
<#if strategyConfig.lombokModel>
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
</#if>
public class ${className} {
<#list columns as column>
    /**
     * ${column.columnName}<#if column.comment??> - ${column.comment}</#if>
     */
    private ${column.javaType} ${column.propertyName};
    
</#list>
<#if !strategyConfig.lombokModel>
    <#list columns as column>
    public ${column.javaType} get${column.propertyName?cap_first}() {
        return ${column.propertyName};
    }

    public void set${column.propertyName?cap_first}(${column.javaType} ${column.propertyName}) {
        this.${column.propertyName} = ${column.propertyName};
    }
    
    </#list>
</#if>
}