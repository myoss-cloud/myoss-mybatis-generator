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
<#if table.v2ServicePackageName??>
package ${table.v2ServicePackageName};
</#if>

<#list table.v2ServiceImportPackages as packageName>
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
public interface ${table.v2ServiceName} extends ${table.v2ServiceSuperClass}<${table.entityName}> {

}
