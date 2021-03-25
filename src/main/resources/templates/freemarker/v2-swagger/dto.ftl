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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
<#if table.entitySuperClass!?length gt 0>
import lombok.EqualsAndHashCode;
import lombok.ToString;
</#if>

/**
 * <#if table.remarks??> ${table.remarks}</#if>
 *
 * @author ${configuration.author}
 * @since ${configuration.generateDate}
 */
@ApiModel("<#if table.remarks??>${table.remarks}-数据传输对象(DTO)</#if>")
<#if table.entitySuperClass!?length gt 0>
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
</#if>
@Accessors(chain = true)
@Data
<#if table.entitySuperClass!?length gt 0>
public class ${table.dtoName} extends ${table.entitySuperClass}<#if table.properties.usePrimaryKeyJavaTypeForClassGenericTypeInEntityFile?? && (table.primaryKeyColumns?size gt 0)><${table.primaryKeyColumns[0].fullyQualifiedJavaType.shortName}></#if> {
<#else>
public class ${table.dtoName} implements Serializable {
</#if>
    private static final long serialVersionUID = 1L;

<#list table.columns as column>
  <#if column.superClassField && !table.ignoredInSuperClassField>
  <#else>
    /**
     * ${column.remarks}
     */
    @ApiModelProperty("${column.remarks}")
    private ${column.fullyQualifiedJavaType.shortName} ${column.javaProperty};

  </#if>
</#list>
}