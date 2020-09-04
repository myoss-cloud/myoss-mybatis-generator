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
<#if table.dtoPackageName??>
package ${table.dtoPackageName};
</#if>

import java.io.Serializable;
<#if table.dtoImportPackages??>
<#list table.dtoImportPackages as packageName>
import ${packageName};
</#list>
</#if>

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 数据传输对象(DTO):<#if table.remarks??> ${table.remarks}</#if>
 *
 * @author ${configuration.author}
 * @since ${configuration.generateDate}
 */
@Accessors(chain = true)
@Data
public class ${table.dtoName} implements Serializable {
    private static final long serialVersionUID = 1L;

<#list table.columns as column>
  <#if column.superClassField && !table.ignoredInSuperClassField>
  <#else>
    /**
     * ${column.remarks}
     */
    private ${column.fullyQualifiedJavaType.shortName} ${column.javaProperty};

  </#if>
</#list>
}