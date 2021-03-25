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
<#if table.servicePackageName??>
package ${table.servicePackageName};
</#if>

<#list table.serviceImportPackages as packageName>
import ${packageName};
</#list>
<#if table.entityPackageName??>
import ${table.entityPackageName}.${table.entityName};
</#if>

/**
 * ${table.remarks}
 *
 * @author ${configuration.author}
 * @since ${configuration.generateDate}
 */
public interface ${table.serviceName} extends ${table.serviceSuperClass}<${table.entityName}> {

}
