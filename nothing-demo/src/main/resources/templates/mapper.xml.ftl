<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperPackage}.${className}Mapper">

    <!-- 基础列 -->
    <sql id="Base_Column_List">
        <#list columns as column>${column.columnName}<#if column_has_next>, </#if></#list>
    </sql>

    <!-- 根据ID查询 -->
    <select id="selectById" resultType="${entityPackage}.${className}">
        SELECT
        <include refid="Base_Column_List"/>
        FROM ${tableName}
        WHERE id = <#noparse>#{id}</#noparse>
    </select>

    <!-- 批量查询 -->
    <select id="selectByIds" resultType="${entityPackage}.${className}">
        SELECT
        <include refid="Base_Column_List"/>
        FROM ${tableName}
        WHERE id IN
        <foreach collection="ids" item="id" open="(" separator="," close=")">
            <#noparse>#{id}</#noparse>
        </foreach>
    </select>

    <!-- 条件查询 -->
    <select id="selectByCondition" resultType="${entityPackage}.${className}" parameterType="${entityPackage}.${className}">
        SELECT
        <include refid="Base_Column_List"/>
        FROM ${tableName}
        <where>
            <#list columns as column>
            <if test="${column.propertyName} != null">
                AND ${column.columnName} = <#noparse>#{</#noparse>${column.propertyName}<#noparse>}</#noparse>
            </if>
            </#list>
        </where>
    </select>

    <!-- 单条插入 -->
    <insert id="insert" parameterType="${entityPackage}.${className}"
            useGeneratedKeys="true" keyProperty="id">
        INSERT INTO ${tableName} (
        <include refid="Base_Column_List"/>
        )
        VALUES (
        <#list columns as column>
            <#noparse>#{</#noparse>${column.propertyName}<#noparse>}</#noparse><#if column_has_next>, </#if>
        </#list>
        )
    </insert>

    <!-- 批量插入 -->
    <insert id="batchInsert" parameterType="java.util.List"
            useGeneratedKeys="true" keyProperty="id">
        INSERT INTO ${tableName} (
        <include refid="Base_Column_List"/>
        )
        VALUES
        <foreach collection="list" item="item" separator=",">
        (
        <#list columns as column>
            <#noparse>#{item.</#noparse>${column.propertyName}<#noparse>}</#noparse><#if column_has_next>, </#if>
        </#list>
        )
        </foreach>
    </insert>

    <!-- 批量插入或更新 -->
    <insert id="batchInsertOrUpdate" parameterType="java.util.List">
        INSERT INTO ${tableName} (
        <include refid="Base_Column_List"/>
        )
        VALUES
        <foreach collection="list" item="item" separator=",">
        (
        <#list columns as column>
            <#noparse>#{item.</#noparse>${column.propertyName}<#noparse>}</#noparse><#if column_has_next>, </#if>
        </#list>
        )
        </foreach>
        ON DUPLICATE KEY UPDATE
        <#list columns as column>
            <#if column.columnName != 'id'>
            ${column.columnName} = VALUES(${column.columnName})<#if column_has_next>, </#if>
            </#if>
        </#list>
    </insert>

    <!-- 更新（全字段） -->
    <update id="updateById" parameterType="${entityPackage}.${className}">
        UPDATE ${tableName}
        <set>
            <#list columns as column>
                <#if column.columnName != 'id'>
                ${column.columnName} = <#noparse>#{</#noparse>${column.propertyName}<#noparse>}</#noparse><#if column_has_next>, </#if>
                </#if>
            </#list>
        </set>
        WHERE id = <#noparse>#{id}</#noparse>
    </update>

    <!-- 更新（有选择地更新非空字段） -->
    <update id="updateSelectiveById" parameterType="${entityPackage}.${className}">
        UPDATE ${tableName}
        <set>
            <#list columns as column>
                <#if column.columnName != 'id'>
                <if test="${column.propertyName} != null">
                    ${column.columnName} = <#noparse>#{</#noparse>${column.propertyName}<#noparse>}</#noparse><#if column_has_next>, </#if>
                </if>
                </#if>
            </#list>
        </set>
        WHERE id = <#noparse>#{id}</#noparse>
    </update>

    <!-- 批量更新 -->
    <update id="batchUpdate" parameterType="java.util.List">
        <foreach collection="list" item="item" separator=";">
            UPDATE ${tableName}
            <set>
                <#list columns as column>
                    <#if column.columnName != 'id'>
                    <if test="item.${column.propertyName} != null">
                        ${column.columnName} = <#noparse>#{item.</#noparse>${column.propertyName}<#noparse>}</#noparse><#if column_has_next>, </#if>
                    </if>
                    </#if>
                </#list>
            </set>
            WHERE id = <#noparse>#{item.id}</#noparse>
        </foreach>
    </update>

    <!-- 删除 -->
    <delete id="deleteById">
        DELETE FROM ${tableName}
        WHERE id = <#noparse>#{id}</#noparse>
    </delete>

    <!-- 批量删除 -->
    <delete id="batchDelete">
        DELETE FROM ${tableName}
        WHERE id IN
        <foreach collection="ids" item="id" open="(" separator="," close=")">
            <#noparse>#{id}</#noparse>
        </foreach>
    </delete>

</mapper>