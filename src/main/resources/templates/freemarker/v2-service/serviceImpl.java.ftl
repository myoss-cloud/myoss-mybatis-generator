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
 * This service implement access the database table ${table.tableName}
 * <p>
 * Database Table Remarks:<#if table.remarks??> ${table.remarks}</#if>
 * </p>
 *
 * @author ${configuration.author}
 * @since ${configuration.generateDate}
 */
@Service
public class ${table.v2ServiceImplName} extends ${table.v2ServiceImplSuperClass}<${table.mapperName}, ${table.entityName}> implements ${table.v2ServiceName} {

}
