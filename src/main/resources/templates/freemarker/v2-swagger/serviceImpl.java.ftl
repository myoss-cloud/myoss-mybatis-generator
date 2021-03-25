<#if configuration.copyright!?length gt 0>
/*
    <#list configuration.copyright?split("\n") as item>
        <#if item!?length gt 0>
 * ${item}
        <#else>
 *
        </#if>
    </#list>
 */

</#if>
<#if table.v2ServiceImplPackageName??>
package ${table.v2ServiceImplPackageName};
</#if>

import java.util.Map;

import org.apache.ibatis.mapping.SqlCommandType;
import org.springframework.stereotype.Service;
<#list table.v2ServiceImplImportPackages as packageName>
import ${packageName};
</#list>
<#if table.entityPackageName??>
import ${table.entityPackageName}.${table.entityName};
</#if>
<#if table.mapperPackageName??>
import ${table.mapperPackageName}.${table.mapperName};
</#if>
<#if table.v2ServicePackageName??>
import ${table.v2ServicePackageName}.${table.v2ServiceName};
</#if>

/**
 * ${table.remarks}
 *
 * @author ${configuration.author}
 * @since ${configuration.generateDate}
 */
@Service
public class ${table.v2ServiceImplName} extends ${table.v2ServiceImplSuperClass}<${table.mapperName}, ${table.entityName}> implements ${table.v2ServiceName} {
    @Override
    protected void checkCommonQueryConditionIsAllNull(SqlCommandType sqlCommandType, ${table.entityName} condition,
                                                      Map<String, Object> extraCondition) {
    }
}
