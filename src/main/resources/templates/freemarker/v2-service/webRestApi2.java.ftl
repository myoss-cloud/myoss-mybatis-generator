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

import app.myoss.cloud.apm.log.method.aspectj.annotation.LogMethodAround;
import app.myoss.cloud.core.lang.dto.Page;
import app.myoss.cloud.core.lang.dto.Result;
<#list table.v2WebImportPackages as packageName>
import ${packageName};
</#list>
<#if table.entityPackageName??>
import ${table.entityPackageName}.${table.entityName};
</#if>
<#if table.v2ServicePackageName??>
import ${table.v2ServicePackageName}.${table.v2ServiceName};
</#if>

/**
 * This web rest api access the database table ${table.tableName}
 * <p>
 * Database Table Remarks:<#if table.remarks??> ${table.remarks}</#if>
 * </p>
 *
 * @author ${configuration.author}
 * @since ${configuration.generateDate}
 */
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
    @LogMethodAround
    <#if allMethodEnable == 'false'>// </#if>@PostMapping("/create")
    public <I> Result<I> create(@RequestBody ${table.entityName} record) {
        I id = ${v2ServiceNameTmp}.create(record);
        Result<I> result = new Result<>();
        return result.setValue(id);
    }

    /**
     * 根据主键id更新记录（只会更新有值的字段）
     *
     * @param record 待更新的实体对象
     * @return 是否操作成功
     */
    @LogMethodAround
    <#if allMethodEnable == 'false'>// </#if>@PostMapping("/updateByPrimaryKey")
    public Result<Boolean> updateByPrimaryKey(@RequestBody ${table.entityName} record) {
        ${v2ServiceNameTmp}.updateByPrimaryKey(record);
        return new Result<>(true);
    }

    /**
     * 根据主键id删除记录，如果是审计字段的实体，使用逻辑删除，而不是物理删除
     *
     * @param condition 匹配的条件
     * @return 是否操作成功
     */
    @LogMethodAround
    <#if allMethodEnable == 'false'>// </#if>@PostMapping("/deleteByPrimaryKey")
    public Result<Boolean> deleteByPrimaryKey(@RequestBody ${table.entityName} condition) {
        ${v2ServiceNameTmp}.deleteByPrimaryKey(condition);
        return new Result<>(true);
    }

    /**
     * 根据主键id查询实体对象
     *
     * @param id 主键id
     * @return 对应的实体对象
     */
    <#if allMethodEnable == 'false'>// </#if>@RequestMapping("/findByPrimaryKey")
    public Result<${table.entityName}> findByPrimaryKey(@RequestParam("id") Serializable id) {
        ${table.entityName} value = ${v2ServiceNameTmp}.findByPrimaryKey(id);
        return new Result<>(value);
    }

    /**
     * 根据主键id查询实体对象
     *
     * @param condition 主键id
     * @return 对应的实体对象
     */
    <#if allMethodEnable == 'false'>// </#if>@PostMapping("/findByPrimaryKey")
    public Result<${table.entityName}> findByPrimaryKey(@RequestBody ${table.entityName} condition) {
        ${table.entityName} value =  ${v2ServiceNameTmp}.findByPrimaryKey(condition);
        return new Result<>(value);
    }

    /**
     * 根据条件查询匹配的实体对象
     *
     * @param condition 匹配的条件
     * @return 匹配的实体对象
     */
    <#if allMethodEnable == 'false'>// </#if>@PostMapping("/findList")
    public Result<List<${table.entityName}>> findList(@RequestBody ${table.entityName} condition) {
        List<${table.entityName}> value = ${v2ServiceNameTmp}.findList(condition);
        return new Result<>(value);
    }

    /**
     * 根据条件查询匹配的实体对象，并支持字段排序
     *
     * @param condition 匹配的条件和排序字段
     * @return 匹配的实体对象
     */
    <#if allMethodEnable == 'false'>// </#if>@PostMapping("/findListWithSort")
    public Result<List<${table.entityName}>> findListWithSort(@RequestBody Page<${table.entityName}> condition) {
        List<${table.entityName}> value = ${v2ServiceNameTmp}.findListWithSort(condition);
        return new Result<>(value);
    }

    /**
     * 根据条件查询匹配的实体对象，并进行分页
     *
     * @param condition 匹配的条件和排序字段
     * @return 匹配的实体对象
     */
    <#if allMethodEnable == 'false'>// </#if>@PostMapping("/findPage")
    public Page<${table.entityName}> findPage(@RequestBody Page<${table.entityName}> condition) {
        return ${v2ServiceNameTmp}.findPage(condition);
    }
}
