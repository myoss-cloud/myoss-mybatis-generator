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

package com.github.myoss.phoenix.mybatis.generator.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import com.github.myoss.phoenix.mybatis.generator.db.dialect.DatabaseDialects;
import com.github.myoss.phoenix.mybatis.generator.types.JavaTypeResolver;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 生成 Mybatis 文件的全局配置
 *
 * @author Jerry.Chen
 * @since 2018年5月4日 下午6:12:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Configuration extends BaseConfiguration {
    /**
     * 数据源
     */
    private DataSource               dataSource;
    /**
     * 数据库方言
     */
    private DatabaseDialects         databaseDialects;
    /**
     * 生成文件保存的根目录
     */
    private String                   rootOutputPath;
    /**
     * 为哪些数据库表生成文件，数据库表的配置信息
     */
    private List<TableConfiguration> tableConfigurations;

    /**
     * Java类型解析器
     */
    private JavaTypeResolver         javaTypeResolver;

    /**
     * 父类package name，可以只设置此字段，默认会分为：entity、mapper、service、web
     */
    private String                   rootPackageName;

    /**
     * Mapper接口名
     */
    private String                   mapperName;
    /**
     * Mapper接口package name
     */
    private String                   mapperPackageName;
    /**
     * Mapper接口需要import package names
     */
    private Set<String>              mapperImportPackages;
    /**
     * Mapper接口的父类class name
     */
    private String                   mapperSuperClass;

    /**
     * service类package name
     */
    private String                   servicePackageName;

    /**
     * web类package name
     */
    private String                   webPackageName;

    /**
     * 版权信息注释
     */
    private String                   copyright;
    /**
     * 当前时间的年份，例如：2018
     */
    private String                   todayYear;
    /**
     * 生成时间，默认是当前时间
     */
    private String                   generateDate;
    /**
     * 作者名字
     */
    private String                   author;

    /**
     * 生成 Mybatis 文件的全局配置
     */
    public Configuration() {
        this.useCatalogOnGenerate = false;
        this.useSchemaOnGenerate = false;
        this.ignoredInSuperClassField = false;

        this.entityOutputPath = "java";
        this.entityTemplatePath = "templates/freemarker/entity.ftl";

        this.mapperOutputPath = "java";
        this.mapperTemplatePath = "templates/freemarker/mapper.java.ftl";
        this.mapperXMLOutputPath = "resources/mybatis/mapper";
        this.mapperXMLTemplatePath = "templates/freemarker/mapper.xml.ftl";

        this.serviceOutputPath = "java";
        this.serviceTemplatePath = "templates/freemarker/service.java.ftl";
        this.serviceImplOutputPath = "java";
        this.serviceImplTemplatePath = "templates/freemarker/serviceImpl.java.ftl";

        this.webOutputPath = "java";
        this.webTemplatePath = "templates/freemarker/webRestApi.java.ftl";
    }

    /**
     * 增加数据库表的配置
     *
     * @param tableConfiguration 数据库表的配置
     * @return 当前实例对象
     */
    public Configuration addTableConfiguration(TableConfiguration tableConfiguration) {
        if (this.tableConfigurations == null) {
            this.tableConfigurations = new ArrayList<>();
        }
        this.tableConfigurations.add(tableConfiguration);
        return this;
    }
}
