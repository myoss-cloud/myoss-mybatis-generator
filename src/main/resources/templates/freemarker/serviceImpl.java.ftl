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
<#if table.serviceImplPackageName??>
package ${table.serviceImplPackageName};
</#if>

import org.springframework.stereotype.Service;
<#list table.serviceImplImportPackages as packageName>
import ${packageName};
</#list>
<#if table.entityPackageName??>
import ${table.entityPackageName}.${table.entityName};
</#if>
<#if table.mapperPackageName??>
import ${table.mapperPackageName}.${table.mapperName};
</#if>
<#if table.servicePackageName??>
import ${table.servicePackageName}.${table.serviceName};
</#if>

/**
 * This service implement access the database table ${table.tableName}
 * <p>
 * Database Table Remarks: ${table.remarks}
 * </p>
 *
 * @author ${configuration.author}
 * @since ${configuration.generateDate}
 */
@Service
public class ${table.serviceImplName} extends ${table.serviceImplSuperClass}<${table.mapperName}, ${table.entityName}> implements ${table.serviceName} {

}
