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
<#if table.v2WebPackageName??>
package ${table.v2WebPackageName};
</#if>
<#assign v2ServiceNameTmp="${table.v2ServiceName?uncap_first}">
<#assign allMethodEnable="${(table.properties.allMethodEnableInWebFile!false)?string('true','false')}">
<#macro printPostMapping>
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
</#macro>
<#assign allRestControllerEnable="${(table.properties.allRestControllerEnableInWebFile!false)?string('true','false')}">
<#macro printNormalRestControllerImport>
import org.springframework.web.bind.annotation.RestController;
</#macro>

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
<#if allMethodEnable == 'true'><@printPostMapping/></#if>
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
<#if allRestControllerEnable == 'true'><@printNormalRestControllerImport/></#if>

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import app.myoss.cloud.apm.log.method.aspectj.annotation.LogMethodAround;
import app.myoss.cloud.core.lang.dto.Page;
import app.myoss.cloud.core.lang.dto.Result;
<#list table.v2WebImportPackages as packageName>
import ${packageName};
</#list>
<#if table.entityPackageName??>
import ${table.entityPackageName}.${table.entityName};
</#if>
<#if table.dtoPackageName??>
import ${table.dtoPackageName}.${table.dtoName};
</#if>
<#if table.converterPackageName??>
import ${table.converterPackageName}.${table.converterName};
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
@Api(tags = "${table.remarks}")
<#if allRestControllerEnable == 'false'>///</#if>@RestController
@RequestMapping("/${table.v2WebRequestName}")
public class ${table.v2WebName} <#if table.v2WebSuperClass!?length gt 0>extends ${table.v2WebSuperClass} </#if>{
    @Autowired
    private ${table.v2ServiceName} ${v2ServiceNameTmp};

    /**
     * 创建新的记录
     *
     * @param record 待保存的实体对象
     * @param <I> 主键类型
     * @return 主键id
     */
    @ApiOperation("创建新的记录")
    @LogMethodAround
    <#if allMethodEnable == 'false'>// </#if>@PostMapping("/create")
    public <I> Result<I> create(@RequestBody ${table.dtoName} record) {
        ${table.entityName} entity = ${table.converterName}.INSTANCE.dtoToDomain(record);
        I id = ${v2ServiceNameTmp}.create(entity);
        Result<I> result = Result.ok();
        return result.setValue(id);
    }

    /**
     * 根据主键id更新记录（只会更新有值的字段）
     *
     * @param record 待更新的实体对象
     * @return 是否操作成功
     */
    @ApiOperation("根据主键id更新记录")
    @LogMethodAround
    <#if allMethodEnable == 'false'>// </#if>@PostMapping("/updateByPrimaryKey")
    public Result<Boolean> updateByPrimaryKey(@RequestBody ${table.dtoName} record) {
        ${table.entityName} entity = ${table.converterName}.INSTANCE.dtoToDomain(record);
        ${v2ServiceNameTmp}.updateByPrimaryKey(entity);
        return Result.ok(true);
    }

    /**
     * 根据主键id删除记录，如果是审计字段的实体，使用逻辑删除，而不是物理删除
     *
     * @param condition 匹配的条件
     * @return 是否操作成功
     */
    @ApiOperation("根据主键id删除记录")
    @LogMethodAround
    <#if allMethodEnable == 'false'>// </#if>@PostMapping("/deleteByPrimaryKey")
    public Result<Boolean> deleteByPrimaryKey(@RequestBody ${table.dtoName} condition) {
        ${table.entityName} entity = ${table.converterName}.INSTANCE.dtoToDomain(condition);
        ${v2ServiceNameTmp}.deleteByPrimaryKey(entity);
        return Result.ok(true);
    }

    /**
     * 根据主键id查询实体对象
     *
     * @param id 主键id
     * @return 对应的实体对象
     */
    @ApiOperation("根据主键id查询实体对象")
    <#if allMethodEnable == 'false'>// </#if>@GetMapping("/findByPrimaryKey")
    public Result<${table.dtoName}> findByPrimaryKey(@RequestParam("id") Serializable id) {
        ${table.entityName} value = ${v2ServiceNameTmp}.findByPrimaryKey(id);
        ${table.dtoName} dto = ${table.converterName}.INSTANCE.domainToDto(value);
        return Result.ok(dto);
    }

    /**
     * 根据主键id查询实体对象
     *
     * @param condition 主键id
     * @return 对应的实体对象
     */
    @ApiOperation("根据主键id查询实体对象")
    <#if allMethodEnable == 'false'>// </#if>@PostMapping("/findByPrimaryKey")
    public Result<${table.dtoName}> findByPrimaryKey(@RequestBody ${table.dtoName} condition) {
        ${table.entityName} entityCondition = ${table.converterName}.INSTANCE.dtoToDomain(condition);
        ${table.entityName} value = ${v2ServiceNameTmp}.findByPrimaryKey(entityCondition);
        ${table.dtoName} dto = ${table.converterName}.INSTANCE.domainToDto(value);
        return Result.ok(dto);
    }

    /**
     * 根据条件查询匹配的实体对象
     *
     * @param condition 匹配的条件
     * @return 匹配的实体对象
     */
    @ApiOperation("根据条件查询匹配的实体对象")
    <#if allMethodEnable == 'false'>// </#if>@PostMapping("/findList")
    public Result<List<${table.dtoName}>> findList(@RequestBody ${table.dtoName} condition) {
        ${table.entityName} entityCondition = ${table.converterName}.INSTANCE.dtoToDomain(condition);
        List<${table.entityName}> value = ${v2ServiceNameTmp}.findList(entityCondition);
        List<${table.dtoName}> dto = ${table.converterName}.INSTANCE.domainToDto(value);
        return Result.ok(dto);
    }

    /**
     * 根据条件查询匹配的实体对象，并支持字段排序
     *
     * @param condition 匹配的条件和排序字段
     * @return 匹配的实体对象
     */
    @ApiOperation("根据条件查询匹配的实体对象，并支持字段排序")
    <#if allMethodEnable == 'false'>// </#if>@PostMapping("/findListWithSort")
    public Result<List<${table.dtoName}>> findListWithSort(@RequestBody Page<${table.dtoName}> condition) {
        Page<${table.entityName}> pageCondition = ${table.converterName}.INSTANCE.dtoToDomain(condition);
        List<${table.entityName}> value = ${v2ServiceNameTmp}.findListWithSort(pageCondition);
        List<${table.dtoName}> dto = ${table.converterName}.INSTANCE.domainToDto(value);
        return Result.ok(dto);
    }

    /**
     * 根据条件查询匹配的实体对象，并进行分页
     *
     * @param condition 匹配的条件和排序字段
     * @return 匹配的实体对象
     */
    @ApiOperation("根据条件查询匹配的实体对象，并进行分页")
    <#if allMethodEnable == 'false'>// </#if>@PostMapping("/findPage")
    public Page<${table.dtoName}> findPage(@RequestBody Page<${table.dtoName}> condition) {
        Page<${table.entityName}> pageCondition = ${table.converterName}.INSTANCE.dtoToDomain(condition);
        Page<${table.entityName}> pageValue = ${v2ServiceNameTmp}.findPage(pageCondition);
        return ${table.converterName}.INSTANCE.domainToDto(pageValue);
    }
}
