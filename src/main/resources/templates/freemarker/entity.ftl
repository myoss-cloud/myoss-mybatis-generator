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
<#if table.entityPackageName??>
package ${table.entityPackageName};
</#if>

<#list table.entityImportPackages as packageName>
import ${packageName};
</#list>

import com.github.myoss.phoenix.mybatis.table.annotation.Column;
<#if table.sequenceTemplatePath!?length gt 0>
<#elseif table.autoIncrement || table.sequenceStrategy != null>
import com.github.myoss.phoenix.mybatis.table.annotation.GenerationType;
import com.github.myoss.phoenix.mybatis.table.annotation.SequenceGenerator;
import com.github.myoss.phoenix.mybatis.table.Sequence;
import com.github.myoss.phoenix.mybatis.table.annotation.SequenceKey;
import com.github.myoss.phoenix.mybatis.table.annotation.SelectKey;
</#if>
<#if table.properties.selectKeyOrder?? || table.properties.sequenceKeyOrder??>
import com.github.myoss.phoenix.mybatis.table.annotation.SequenceGenerator.Order;
</#if>
import com.github.myoss.phoenix.mybatis.table.annotation.Table;

import lombok.Data;
import lombok.experimental.Accessors;
<#if table.entitySuperClass!?length gt 0>
import lombok.EqualsAndHashCode;
import lombok.ToString;
</#if>

/**
 * This class corresponds to the database table ${table.tableName}
 * <p>
 * Database Table Remarks: ${table.remarks}
 * </p>
 *
 * @author ${configuration.author}
 * @since ${configuration.generateDate}
 */
<#if table.entitySuperClass!?length gt 0>
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
</#if>
@Accessors(chain = true)
@Data
<#if table.sequenceTemplatePath!?length gt 0>
<#include "${table.sequenceTemplatePath}">
<#elseif table.sequenceStrategy?? && table.sequenceStrategy == "SELECT_KEY">
@SequenceGenerator(strategy = GenerationType.SELECT_KEY, selectKey = @SelectKey(sql = "<#if table.properties.selectKeySelectSql??>${table.properties.selectKeySelectSql}</#if>", keyProperty = { <#list table.primaryKeyColumns as item>"${item.javaProperty}"${item?has_next?then(', ', '')}</#list> }, keyColumn = { <#list table.primaryKeyColumns as item>"${item.columnName}"${item?has_next?then(', ', '')}</#list> }<#if table.properties.selectKeyResultType??>, resultType = ${table.properties.selectKeyResultType}.class</#if><#if table.properties.selectKeyOrder??>, order = ${table.properties.selectKeyOrder}</#if>))
<#elseif table.sequenceStrategy?? && table.sequenceStrategy == "SEQUENCE_KEY">
@SequenceGenerator(strategy = GenerationType.SEQUENCE_KEY, sequenceKey = @SequenceKey(sequenceClass = <#if table.properties.sequenceKeySequenceClass??>${table.properties.sequenceKeySequenceClass}.class<#else>Sequence.class</#if><#if table.properties.sequenceKeySequenceBeanName??>, sequenceBeanName = "${table.properties.sequenceKeySequenceBeanName}"</#if><#if table.properties.sequenceKeySequenceName??>, sequenceName = "${table.properties.sequenceKeySequenceName}"</#if>, keyProperty = { <#list table.primaryKeyColumns as item>"${item.javaProperty}"${item?has_next?then(', ', '')}</#list> }, keyColumn = { <#list table.primaryKeyColumns as item>"${item.columnName}"${item?has_next?then(', ', '')}</#list> }<#if table.properties.sequenceKeyOrder??>, order = ${table.properties.sequenceKeyOrder}</#if>))
<#elseif table.autoIncrement>
@SequenceGenerator(strategy = GenerationType.USE_GENERATED_KEYS)
</#if>
@Table(name = "${table.tableName}"<#if table.catalog!?length gt 0>, catalog = "${table.catalog}"</#if><#if table.schema!?length gt 0>, schema = "${table.schema}"</#if>)
<#if table.entitySuperClass!?length gt 0>
public class ${table.entityName} extends ${table.entitySuperClass}<#if table.properties.usePrimaryKeyJavaTypeForClassGenericTypeInEntityFile?? && (table.primaryKeyColumns?size gt 0)><${table.primaryKeyColumns[0].fullyQualifiedJavaType.shortName}></#if> {
<#else>
public class ${table.entityName} implements Serializable {
</#if>
    private static final long serialVersionUID = 1L;

<#list table.columns as column>
  <#if column.superClassField && !column.autoIncrement>
  <#else>
    /**
     * Database Column Name: ${table.tableName}.${column.columnName}
     * <p>
     * Database Column Remarks: ${column.remarks}
     * </p>
     */
    @Column(name = "${column.columnName}"<#if !column.nullable>, nullable = false</#if>, jdbcTypeName = "${column.jdbcTypeName}"<#if column.primaryKey>, primaryKey = true</#if>)
    private ${column.fullyQualifiedJavaType.shortName} ${column.javaProperty};

  </#if>
</#list>
}