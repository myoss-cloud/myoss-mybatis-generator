/*
 * Copyright 2018-2018 https://github.com/myoss
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package app.myoss.cloud.mybatis.generator.config;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import app.myoss.cloud.mybatis.table.annotation.GenerationType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 配置基类，定义了通用的字段
 *
 * @author Jerry.Chen
 * @since 2018年5月8日 下午10:55:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseConfiguration extends AbstractPropertyHolder {
    /**
     * 数据库中的catalog，用于查询数据库表结构信息
     */
    protected String             catalog;
    /**
     * 生成文件的时候是否使用数据库中的catalog，如果设置为true，将在表名前面加上catalog指定的值
     */
    protected Boolean            useCatalogOnGenerate;
    /**
     * 数据库中的schema，用于查询数据库表结构信息
     */
    protected String             schema;
    /**
     * 生成文件的时候是否使用数据库中的schema，如果设置为true，将在表名前面加上schema指定的值
     */
    protected Boolean            useSchemaOnGenerate;

    /**
     * 所有的列名都增加标识限定符
     */
    protected Boolean            allColumnDelimitingEnabled;
    /**
     * 开始的标识限定符
     */
    protected String             beginningDelimiter = "`";
    /**
     * 结束的标识限定符
     */
    protected String             endingDelimiter    = "`";

    /**
     * 父类package name，可以只设置此字段，默认会分为：entity、mapper、service、web
     */
    protected String             rootPackageName;

    /**
     * 实体类名(entityClass name)
     */
    protected String             entityName;
    /**
     * 移除掉entityClassName的前缀
     */
    protected String             removeEntityClassPrefix;
    /**
     * 移除掉entityClassName的后缀
     */
    protected String             removeEntityClassSuffix;
    /**
     * entityClass的前缀
     */
    protected String             entityClassPrefix;
    /**
     * entityClass的后缀
     */
    protected String             entityClassSuffix;
    /**
     * 实体类package name
     */
    protected String             entityPackageName;
    /**
     * 实体类需要import package names
     */
    protected Set<String>        entityImportPackages;
    /**
     * 实体类的父类class name
     */
    protected String             entitySuperClass;
    /**
     * 实体类的父类class所有字段
     */
    protected Map<String, Field> entitySuperClassFields;
    /**
     * 生成主键id策略类型【sequenceStrategy和sequenceTemplatePath二选一】
     */
    protected GenerationType     sequenceStrategy;
    /**
     * 生成主键id模版路径【sequenceStrategy和sequenceTemplatePath二选一】
     */
    protected String             sequenceTemplatePath;
    /**
     * 实体类文件保存的目录（默认值为：java，会在 {@link Configuration#rootOutputPath} 创建这样的子目录：
     * "entityOutputPath" + "{@link #entityPackageName }
     * 转换为目录"。示例：com/test/user/entity）
     */
    protected String             entityOutputPath;
    /**
     * 实体类模版路径，默认值为：templates/freemarker/entity.ftl
     */
    protected String             entityTemplatePath;
    /**
     * 忽略字段是否在父类class中
     */
    protected Boolean            ignoredInSuperClassField;

    /**
     * DTO类名(dtoClass name)
     */
    protected String             dtoName;
    /**
     * dtoClass的后缀
     */
    protected String             dtoClassSuffix;
    /**
     * DTO类package name
     */
    protected String             dtoPackageName;
    /**
     * DTO类需要import package names
     */
    protected Set<String>        dtoImportPackages;
    /**
     * DTO类文件保存的目录（默认值为：java，会在 {@link Configuration#rootOutputPath} 创建这样的子目录：
     * "dtoOutputPath" + "{@link #dtoPackageName } 转换为目录"。示例：com/test/user/dto）
     */
    protected String             dtoOutputPath;
    /**
     * DTO类模版路径，默认值为：templates/freemarker/dto.ftl
     */
    protected String             dtoTemplatePath;

    /**
     * Converter类名(converterClass name)
     */
    protected String             converterName;
    /**
     * converterClass的后缀
     */
    protected String             converterClassSuffix;
    /**
     * Converter类package name
     */
    protected String             converterPackageName;
    /**
     * Converter类文件保存的目录（默认值为：java，会在 {@link Configuration#rootOutputPath}
     * 创建这样的子目录： "dtoOutputPath" + "{@link #converterPackageName }
     * 转换为目录"。示例：com/test/user/converter）
     */
    protected String             converterOutputPath;
    /**
     * Converter类模版路径，默认值为：templates/freemarker/converter.ftl
     */
    protected String             converterTemplatePath;

    /**
     * Mapper接口名
     * <p>
     * 自定义文件命名，使用 %s 自动填充表实体名，举例：%sMapper，会生成 UserMapper
     * </p>
     */
    protected String             mapperName;
    /**
     * Mapper接口package name
     */
    protected String             mapperPackageName;
    /**
     * Mapper接口需要import package names
     */
    protected Set<String>        mapperImportPackages;
    /**
     * Mapper接口的父类class name
     */
    protected String             mapperSuperClass;
    /**
     * Mapper接口文件保存的目录（默认值为：java，会在 {@link Configuration#rootOutputPath}
     * 创建这样的子目录： "mapperOutputPath" + "{@link #mapperPackageName }
     * 转换为目录"。示例：com/test/user/mapper）
     */
    protected String             mapperOutputPath;
    /**
     * Mapper接口模版路径，默认值为：templates/freemarker/mapper.java.ftl
     */
    protected String             mapperTemplatePath;
    /**
     * Mapper XML文件保存的目录（默认为：resources/mybatis/mapper，会在
     * {@link Configuration#rootOutputPath} 创建这样的子目录： "mapperXMLOutputPath" + "
     * {@link #mapperPackageName }
     * 转换为目录，取父级模块名字"。示例：resources/mybatis/mapper/user）
     */
    protected String             mapperXMLOutputPath;
    /**
     * Mapper XML模版路径，默认值为：templates/freemarker/mapper.xml.ftl
     */
    protected String             mapperXMLTemplatePath;

    /**
     * service接口名
     * <p>
     * 自定义文件命名，使用 %s 自动填充表实体名，举例：%sService，会生成 UserService
     * </p>
     */
    protected String             serviceName;
    /**
     * service接口package name
     */
    protected String             servicePackageName;
    /**
     * service接口需要import package names
     */
    protected Set<String>        serviceImportPackages;
    /**
     * service接口的父类class name
     */
    protected String             serviceSuperClass;
    /**
     * service接口文件保存的目录（默认值为：java，会在 {@link Configuration#rootOutputPath}
     * 创建这样的子目录： "serviceOutputPath" + "{@link #servicePackageName }
     * 转换为目录"。示例：com/test/user/service）
     */
    protected String             serviceOutputPath;
    /**
     * service接口模版路径，默认值为：templates/freemarker/service.java.ftl
     */
    protected String             serviceTemplatePath;

    /**
     * service实现类名
     * <p>
     * 自定义文件命名，使用 %s 自动填充表实体名，举例：%sServiceImpl，会生成 UserServiceImpl
     * </p>
     */
    protected String             serviceImplName;
    /**
     * service实现类package name
     */
    protected String             serviceImplPackageName;
    /**
     * service实现类需要import package names
     */
    protected Set<String>        serviceImplImportPackages;
    /**
     * service实现类的父类class name
     */
    protected String             serviceImplSuperClass;
    /**
     * service实现类文件保存的目录（默认值为：java，会在 {@link Configuration#rootOutputPath}
     * 创建这样的子目录： "serviceImplOutputPath" + "{@link #serviceImplPackageName }
     * 转换为目录"。示例：com/test/user/service/impl）
     */
    protected String             serviceImplOutputPath;
    /**
     * service实现类模版路径，默认值为：templates/freemarker/serviceImpl.java.ftl
     */
    protected String             serviceImplTemplatePath;

    /**
     * web类名
     * <p>
     * 自定义文件命名，使用 %s 自动填充表实体名，举例：%sController，会生成 UserController
     * </p>
     */
    protected String             webName;
    /**
     * web类package name
     */
    protected String             webPackageName;
    /**
     * web类需要import package names
     */
    protected Set<String>        webImportPackages;
    /**
     * web类的父类class name
     */
    protected String             webSuperClass;
    /**
     * web类文件保存的目录（默认值为：java，会在 {@link Configuration#rootOutputPath} 创建这样的子目录：
     * "webOutputPath" + "{@link #webPackageName } 转换为目录"。示例：com/test/user/web）
     */
    protected String             webOutputPath;
    /**
     * web类模版路径，默认值为：templates/freemarker/webRestApi.java.ftl
     */
    protected String             webTemplatePath;
    /**
     * web类对应Http请求的接口名，默认值为：{@link #entityName}
     */
    protected String             webRequestName;

    /**
     * v2 service接口名
     * <p>
     * 自定义文件命名，使用 %s 自动填充表实体名，举例：%sService，会生成 UserService
     * </p>
     */
    protected String             v2ServiceName;
    /**
     * v2 service接口package name
     */
    protected String             v2ServicePackageName;
    /**
     * v2 service接口需要import package names
     */
    protected Set<String>        v2ServiceImportPackages;
    /**
     * v2 service接口的父类class name
     */
    protected String             v2ServiceSuperClass;
    /**
     * v2 service接口文件保存的目录（默认值为：java，会在 {@link Configuration#rootOutputPath}
     * 创建这样的子目录： "v2ServiceOutputPath" + "{@link #v2ServicePackageName }
     * 转换为目录"。示例：com/test/user/service）
     */
    protected String             v2ServiceOutputPath;
    /**
     * v2 service接口模版路径，默认值为：templates/freemarker/v2-service/service.java.ftl
     */
    protected String             v2ServiceTemplatePath;

    /**
     * v2 service实现类名
     * <p>
     * 自定义文件命名，使用 %s 自动填充表实体名，举例：%sServiceImpl，会生成 UserServiceImpl
     * </p>
     */
    protected String             v2ServiceImplName;
    /**
     * v2 service实现类package name
     */
    protected String             v2ServiceImplPackageName;
    /**
     * v2 service实现类需要import package names
     */
    protected Set<String>        v2ServiceImplImportPackages;
    /**
     * v2 service实现类的父类class name
     */
    protected String             v2ServiceImplSuperClass;
    /**
     * v2 service实现类文件保存的目录（默认值为：java，会在 {@link Configuration#rootOutputPath}
     * 创建这样的子目录： "v2ServiceImplOutputPath" + "{@link #v2ServiceImplPackageName }
     * 转换为目录"。示例：com/test/user/service/impl）
     */
    protected String             v2ServiceImplOutputPath;
    /**
     * v2
     * service实现类模版路径，默认值为：templates/freemarker/v2-service/serviceImpl.java.ftl
     */
    protected String             v2ServiceImplTemplatePath;

    /**
     * v2 web类名
     * <p>
     * 自定义文件命名，使用 %s 自动填充表实体名，举例：%sController，会生成 UserController
     * </p>
     */
    protected String             v2WebName;
    /**
     * v2 web类package name
     */
    protected String             v2WebPackageName;
    /**
     * v2 web类需要import package names
     */
    protected Set<String>        v2WebImportPackages;
    /**
     * v2 web类的父类class name
     */
    protected String             v2WebSuperClass;
    /**
     * v2 web类文件保存的目录（默认值为：java，会在 {@link Configuration#rootOutputPath}
     * 创建这样的子目录： "v2WebOutputPath" + "{@link #v2WebPackageName }
     * 转换为目录"。示例：com/test/user/web）
     */
    protected String             v2WebOutputPath;
    /**
     * v2 web类模版路径，默认值为：templates/freemarker/webRestApi.java.ftl
     */
    protected String             v2WebTemplatePath;
    /**
     * v2 web类对应Http请求的接口名，默认值为：{@link #entityName}
     */
    protected String             v2WebRequestName;

    /**
     * 为实体类添加一个import package name
     *
     * @param importPackage import package name
     * @return 当前实例对象
     */
    public BaseConfiguration addEntityImportPackage(String... importPackage) {
        if (this.entityImportPackages == null) {
            this.entityImportPackages = new LinkedHashSet<>();
        }
        this.entityImportPackages.addAll(Arrays.asList(importPackage));
        return this;
    }

    /**
     * 为DTO类添加一个import package name
     *
     * @param importPackage import package name
     * @return 当前实例对象
     */
    public BaseConfiguration addDtoImportPackage(Collection<String> importPackage) {
        if (this.dtoImportPackages == null) {
            this.dtoImportPackages = new LinkedHashSet<>();
        }
        this.dtoImportPackages.addAll(importPackage);
        return this;
    }

    /**
     * 为Mapper接口类添加一个import package name
     *
     * @param importPackage import package name
     * @return 当前实例对象
     */
    public BaseConfiguration addMapperImportPackage(String... importPackage) {
        if (this.mapperImportPackages == null) {
            this.mapperImportPackages = new LinkedHashSet<>();
        }
        this.mapperImportPackages.addAll(Arrays.asList(importPackage));
        return this;
    }

    /**
     * 为Service接口类添加一个import package name
     *
     * @param importPackage import package name
     * @return 当前实例对象
     */
    public BaseConfiguration addServiceImportPackage(String... importPackage) {
        if (this.serviceImportPackages == null) {
            this.serviceImportPackages = new LinkedHashSet<>();
        }
        this.serviceImportPackages.addAll(Arrays.asList(importPackage));
        return this;
    }

    /**
     * 为Service实现类添加一个import package name
     *
     * @param importPackage import package name
     * @return 当前实例对象
     */
    public BaseConfiguration addServiceImplImportPackage(String... importPackage) {
        if (this.serviceImplImportPackages == null) {
            this.serviceImplImportPackages = new LinkedHashSet<>();
        }
        this.serviceImplImportPackages.addAll(Arrays.asList(importPackage));
        return this;
    }

    /**
     * 为Web类添加一个import package name
     *
     * @param importPackage import package name
     * @return 当前实例对象
     */
    public BaseConfiguration addWebImportPackage(String... importPackage) {
        if (this.webImportPackages == null) {
            this.webImportPackages = new LinkedHashSet<>();
        }
        this.webImportPackages.addAll(Arrays.asList(importPackage));
        return this;
    }

    /**
     * 为 v2 Service接口类添加一个import package name
     *
     * @param importPackage import package name
     * @return 当前实例对象
     */
    public BaseConfiguration addV2ServiceImportPackage(String... importPackage) {
        if (this.v2ServiceImportPackages == null) {
            this.v2ServiceImportPackages = new LinkedHashSet<>();
        }
        this.v2ServiceImportPackages.addAll(Arrays.asList(importPackage));
        return this;
    }

    /**
     * 为 v2 Service实现类添加一个import package name
     *
     * @param importPackage import package name
     * @return 当前实例对象
     */
    public BaseConfiguration addV2ServiceImplImportPackage(String... importPackage) {
        if (this.v2ServiceImplImportPackages == null) {
            this.v2ServiceImplImportPackages = new LinkedHashSet<>();
        }
        this.v2ServiceImplImportPackages.addAll(Arrays.asList(importPackage));
        return this;
    }

    /**
     * 为v2 Web类添加一个import package name
     *
     * @param importPackage import package name
     * @return 当前实例对象
     */
    public BaseConfiguration addV2WebImportPackage(String... importPackage) {
        if (this.v2WebImportPackages == null) {
            this.v2WebImportPackages = new LinkedHashSet<>();
        }
        this.v2WebImportPackages.addAll(Arrays.asList(importPackage));
        return this;
    }
}
