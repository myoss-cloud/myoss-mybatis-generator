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
<#if table.converterPackageName??>
package ${table.converterPackageName};
</#if>

<#if table.dtoPackageName??>
import ${table.dtoPackageName}.${table.dtoName};
</#if>
<#if table.entityPackageName??>
import ${table.entityPackageName}.${table.entityName};
</#if>

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * {@link ${table.dtoName}}、{@link ${table.entityName}} 转换器
 *
 * @author ${configuration.author}
 * @since ${configuration.generateDate}
 */
@Mapper
public interface ${table.converterName} {
    /**
     * 转换器实例
     */
    ${table.converterName} INSTANCE = Mappers.getMapper(${table.converterName}.class);

    /**
     * 将 domain 转换为 dto
     *
     * @param domain 实体类
     * @return 数据传输对象
     */
    ${table.dtoName} domainToDto(${table.entityName} domain);

    /**
     * 将 domain 转换为 dto
     *
     * @param domainList 实体类
     * @return 数据传输对象
     */
    List<${table.dtoName}> domainToDto(List<${table.entityName}> domainList);

    /**
     * 将 dto 转换为 domain
     *
     * @param dto 数据传输对象
     * @return 实体类
     */
    ${table.entityName} dtoToDomain(${table.dtoName} dto);

    /**
     * 将 dto 转换为 domain
     *
     * @param dtoList 数据传输对象
     * @return 实体类
     */
    List<${table.entityName}> dtoToDomain(List<${table.dtoName}> dtoList);
}