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
<#if table.mapperPackageName??>
package ${table.mapperPackageName};
</#if>

import org.springframework.stereotype.Repository;
<#list table.mapperImportPackages as packageName>
import ${packageName};
</#list>
<#if table.entityPackageName??>
import ${table.entityPackageName}.${table.entityName};
</#if>

/**
 * This mapper interface access the database table ${table.tableName}
 * <p>
 * Database Table Remarks:<#if table.remarks??> ${table.remarks}</#if>
 * </p>
 *
 * @author ${configuration.author}
 * @since ${configuration.generateDate}
 */
@Repository
public interface ${table.mapperName} extends ${table.mapperSuperClass}<${table.entityName}> {

}
