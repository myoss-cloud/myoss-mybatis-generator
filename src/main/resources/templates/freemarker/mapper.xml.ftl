<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<#if table.mapperPackageName??>
<mapper namespace="${table.mapperPackageName}.${table.mapperName}">
<#else>
<mapper namespace="${table.mapperName}">
</#if>

    <!-- 通用查询映射结果 -->
  <#if table.entityPackageName??>
    <resultMap id="BaseResultMap" type="${table.entityPackageName}.${table.entityName}">
  <#else>
    <resultMap id="BaseResultMap" type="${table.entityName}">
  </#if>
  <#list table.columns as column>
    <#if column.primaryKey>
        <#-- 主键字段 -->
        <id column="${column.columnName}" jdbcType="${column.jdbcTypeName}" property="${column.javaProperty}" />
    </#if>
  </#list>
  <#list table.columns as column>
    <#if !column.primaryKey && column.superClassField>
        <#-- 公共字段 -->
        <result column="${column.columnName}" jdbcType="${column.jdbcTypeName}" property="${column.javaProperty}" />
    </#if>
  </#list>
  <#list table.columns as column>
    <#if !column.primaryKey && !column.superClassField>
        <#-- 其它字段 -->
        <result column="${column.columnName}" jdbcType="${column.jdbcTypeName}" property="${column.javaProperty}" />
    </#if>
  </#list>
    </resultMap>

    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
     <#list table.columns as column>${column.columnName}${column?has_next?then(', ', '')}</#list>
    </sql>

</mapper>
