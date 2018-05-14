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

package com.github.myoss.phoenix.mybatis.generator;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import javax.sql.DataSource;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import com.github.myoss.phoenix.core.exception.BizRuntimeException;
import com.github.myoss.phoenix.core.lang.base.StringUtil;
import com.github.myoss.phoenix.core.lang.bean.BeanUtil;
import com.github.myoss.phoenix.core.lang.io.FileUtil;
import com.github.myoss.phoenix.mybatis.generator.config.ColumnOverride;
import com.github.myoss.phoenix.mybatis.generator.config.Configuration;
import com.github.myoss.phoenix.mybatis.generator.config.TableConfiguration;
import com.github.myoss.phoenix.mybatis.generator.db.Column;
import com.github.myoss.phoenix.mybatis.generator.db.SqlKeyWords;
import com.github.myoss.phoenix.mybatis.generator.db.Table;
import com.github.myoss.phoenix.mybatis.generator.template.TemplateEngine;
import com.github.myoss.phoenix.mybatis.generator.template.impl.FreemarkerTemplateImpl;
import com.github.myoss.phoenix.mybatis.generator.types.JavaTypeResolver;
import com.github.myoss.phoenix.mybatis.generator.types.impl.FullyQualifiedJavaType;
import com.github.myoss.phoenix.mybatis.generator.types.impl.JavaTypeResolverImpl;
import com.github.myoss.phoenix.mybatis.mapper.template.CrudMapper;
import com.github.myoss.phoenix.mybatis.repository.service.CrudService;
import com.github.myoss.phoenix.mybatis.repository.service.impl.BaseCrudServiceImpl;

/**
 * Mybatis 生成文件工具类
 *
 * @author Jerry.Chen 2018年5月4日 下午6:08:00
 */
@Slf4j
@Getter
public class MyBatisGenerator {
    /**
     * 全局配置信息
     */
    private Configuration  configuration;
    /**
     * 生成文件的模版引擎
     */
    @Setter
    private TemplateEngine templateEngine;

    /**
     * 初始化 Mybatis 生成文件工具类
     *
     * @param configuration 全局配置信息
     */
    public MyBatisGenerator(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 生成 Mybatis 文件
     */
    public void generate() {
        initConfiguration();
        List<TableConfiguration> tableConfigurations = configuration.getTableConfigurations();
        List<Table> tables = new ArrayList<>(tableConfigurations.size());
        DataSource dataSource = configuration.getDataSource();
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            for (TableConfiguration tc : tableConfigurations) {
                Table table = initTableInformation(tc, databaseMetaData);
                tables.add(table);
            }
        } catch (SQLException e) {
            throw new BizRuntimeException(e);
        }

        // init template engine
        if (templateEngine == null) {
            templateEngine = new FreemarkerTemplateImpl();
        }
        templateEngine.init(configuration);

        // writer file
        Path rootOutputPath = Paths.get(configuration.getRootOutputPath());
        for (Table table : tables) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("configuration", configuration);
            data.put("table", table);
            // 生成实体类
            generateEntity(rootOutputPath, table, data);
            // 生成 Mapper 接口
            generateMapperInterface(rootOutputPath, table, data);
            // 生成 Mapper XML
            generateMapperXml(rootOutputPath, table, data);
            // 生成 Service 接口
            generateServiceInterface(rootOutputPath, table, data);
            // 生成 Service 实现类
            generateServiceImplInterface(rootOutputPath, table, data);
            // 生成 Web 类
            generateWeb(rootOutputPath, table, data);
        }
    }

    /**
     * 获取文件路径，并创建对应的目录
     *
     * @param rootOutputPath 生成文件保存的根目录
     * @param childOutputPath 文件保存的子目录
     * @param packageName package name
     * @param fileName 文件名
     * @return 文件的完整路径
     */
    public String resolveFilePath(Path rootOutputPath, String childOutputPath, String packageName, String fileName) {
        Path path;
        String tmp = packageName.replace(".", File.separator);
        if (StringUtils.isNotBlank(childOutputPath)) {
            path = rootOutputPath.resolve(childOutputPath + File.separator + tmp);
        } else {
            path = rootOutputPath.resolve(tmp);
        }
        return FileUtil.createDirectories(path).resolve(fileName + ".java").toString();
    }

    /**
     * 生成 Entity 实体类文件
     *
     * @param rootOutputPath 生成文件保存的根目录
     * @param table 数据库表的信息
     * @param data 模版配置数据
     */
    public void generateEntity(Path rootOutputPath, Table table, HashMap<String, Object> data) {
        String filePath = resolveFilePath(rootOutputPath, table.getEntityOutputPath(), table.getEntityPackageName(),
                table.getEntityName());
        templateEngine.writer(table.getEntityTemplatePath(), filePath, data);
    }

    /**
     * 生成 Mapper 接口文件
     *
     * @param rootOutputPath 生成文件保存的根目录
     * @param table 数据库表的信息
     * @param data 模版配置数据
     */
    public void generateMapperInterface(Path rootOutputPath, Table table, HashMap<String, Object> data) {
        String filePath = resolveFilePath(rootOutputPath, table.getMapperOutputPath(), table.getMapperPackageName(),
                table.getMapperName());
        templateEngine.writer(table.getMapperTemplatePath(), filePath, data);
    }

    /**
     * 生成 Mapper XML文件
     *
     * @param rootOutputPath 生成文件保存的根目录
     * @param table 数据库表的信息
     * @param data 模版配置数据
     */
    public void generateMapperXml(Path rootOutputPath, Table table, HashMap<String, Object> data) {
        Path path;
        String mapperXMLOutputPath = table.getMapperXMLOutputPath();
        if (StringUtils.isNotBlank(mapperXMLOutputPath)) {
            path = rootOutputPath.resolve(mapperXMLOutputPath);
            String tmp = StringUtils.substringAfterLast(StringUtils.removeEnd(table.getMapperPackageName(), ".mapper"),
                    ".");
            if (StringUtils.isNotBlank(tmp)) {
                // 增加模块目录
                path = path.resolve(tmp);
            }
        } else {
            path = rootOutputPath.resolve(table.getMapperPackageName().replace(".", File.separator)).resolve("xml");
        }
        String filePath = FileUtil.createDirectories(path).resolve(table.getMapperName() + ".xml").toString();
        templateEngine.writer(table.getMapperXMLTemplatePath(), filePath, data);
    }

    /**
     * 生成 Service 接口文件
     *
     * @param rootOutputPath 生成文件保存的根目录
     * @param table 数据库表的信息
     * @param data 模版配置数据
     */
    public void generateServiceInterface(Path rootOutputPath, Table table, HashMap<String, Object> data) {
        String filePath = resolveFilePath(rootOutputPath, table.getServiceOutputPath(), table.getServicePackageName(),
                table.getServiceName());
        templateEngine.writer(table.getServiceTemplatePath(), filePath, data);
    }

    /**
     * 生成 Service 实现类文件
     *
     * @param rootOutputPath 生成文件保存的根目录
     * @param table 数据库表的信息
     * @param data 模版配置数据
     */
    public void generateServiceImplInterface(Path rootOutputPath, Table table, HashMap<String, Object> data) {
        String filePath = resolveFilePath(rootOutputPath, table.getServiceImplOutputPath(),
                table.getServiceImplPackageName(), table.getServiceImplName());
        templateEngine.writer(table.getServiceImplTemplatePath(), filePath, data);
    }

    /**
     * 生成 Web Controller 文件
     *
     * @param rootOutputPath 生成文件保存的根目录
     * @param table 数据库表的信息
     * @param data 模版配置数据
     */
    public void generateWeb(Path rootOutputPath, Table table, HashMap<String, Object> data) {
        String filePath = resolveFilePath(rootOutputPath, table.getWebOutputPath(), table.getWebPackageName(),
                table.getWebName());
        templateEngine.writer(table.getWebTemplatePath(), filePath, data);
    }

    /**
     * 初始化配置
     */
    public void initConfiguration() {
        Objects.requireNonNull(configuration, "configuration is null");
        Objects.requireNonNull(configuration.getRootOutputPath(), "rootOutputPath is null");
        Objects.requireNonNull(configuration.getDataSource(), "dataSource is null");
        if (StringUtils.isBlank(configuration.getAuthor())) {
            throw new NullPointerException("author is null or empty");
        }
        List<TableConfiguration> tableConfigurations = configuration.getTableConfigurations();
        if (CollectionUtils.isEmpty(tableConfigurations)) {
            throw new NullPointerException("tableConfigurations is null or empty");
        }

        boolean checkDelimiterIsBlank = false;
        if (BooleanUtils.isTrue(configuration.getAllColumnDelimitingEnabled())) {
            for (TableConfiguration tc : tableConfigurations) {
                tc.setAllColumnDelimitingEnabled(true);
            }
            checkDelimiterIsBlank = true;
        }

        ClassLoader classLoader = this.getClass().getClassLoader();
        for (TableConfiguration tc : tableConfigurations) {
            String tableName = tc.getTableName();
            if (StringUtils.isBlank(tableName)) {
                throw new NullPointerException("tableConfiguration.tableName is null or empty");
            }
            BeanUtil.copyProperties(configuration, tc, null, false);

            // 移除掉entityClassName的前缀和后缀
            String entityName = StringUtils.removeEnd(
                    StringUtils.removeStart(StringUtil.toPascalCase(tableName), tc.getRemoveEntityClassPrefix()),
                    tc.getRemoveEntityClassSuffix());
            // 增加entityClassName的前缀和后缀
            if (StringUtils.isNotBlank(tc.getEntityClassPrefix())) {
                entityName = tc.getEntityClassPrefix() + entityName;
            }
            if (StringUtils.isNotBlank(tc.getEntityClassSuffix())) {
                entityName = entityName + tc.getEntityClassSuffix();
            }
            tc.setEntityName(entityName);

            if (BooleanUtils.isTrue(tc.getAllColumnDelimitingEnabled())) {
                checkDelimiterIsBlank = true;
            }

            // 实体类的父类class name
            String entitySuperClass = tc.getEntitySuperClass();
            if (StringUtils.isNotBlank(entitySuperClass)) {
                tc.setEntitySuperClass(StringUtils.substringAfterLast(entitySuperClass, "."));
                tc.addEntityImportPackage(entitySuperClass);
                if (ClassUtils.isPresent(entitySuperClass, classLoader)) {
                    Class<?> clazz = ClassUtils.resolveClassName(entitySuperClass, classLoader);
                    Map<String, Field> fields = getSuperClassFields(clazz);
                    tc.setEntitySuperClassFields(fields);
                } else {
                    log.warn("can not resolve class name [{}], ignored mark column is superClassField",
                            entitySuperClass);
                }
            }
            if (StringUtils.isBlank(tc.getEntitySuperClass())) {
                tc.addEntityImportPackage("java.io.Serializable");
            }

            // Mapper接口属性
            if (StringUtils.equals(configuration.getMapperName(), tc.getMapperName())
                    && StringUtils.isNotBlank(configuration.getMapperName())) {
                // 自定义文件命名，使用 %s 自动填充表实体名，举例：%sMapper，会生成 UserMapper
                tc.setMapperName(String.format(configuration.getMapperName(), tc.getEntityName()));
            } else if (StringUtils.isBlank(tc.getMapperName())) {
                tc.setMapperName(tc.getEntityName() + "Mapper");
            }
            if (StringUtils.isBlank(tc.getMapperSuperClass())) {
                tc.setMapperSuperClass(CrudMapper.class.getName());
            }
            String mapperSuperClass = tc.getMapperSuperClass();
            if (StringUtils.isNotBlank(mapperSuperClass)) {
                tc.setMapperSuperClass(StringUtils.substringAfterLast(mapperSuperClass, "."));
                tc.addMapperImportPackage(mapperSuperClass);
            }

            // Service接口属性
            if (StringUtils.equals(configuration.getServiceName(), tc.getServiceName())
                    && StringUtils.isNotBlank(configuration.getServiceName())) {
                // 自定义文件命名，使用 %s 自动填充表实体名，举例：%sService，会生成 UserService
                tc.setServiceName(String.format(configuration.getServiceName(), tc.getEntityName()));
            } else if (StringUtils.isBlank(tc.getServiceName())) {
                tc.setServiceName(tc.getEntityName() + "Service");
            }
            if (StringUtils.isBlank(tc.getServiceSuperClass())) {
                tc.setServiceSuperClass(CrudService.class.getName());
            }
            String serviceSuperClass = tc.getServiceSuperClass();
            if (StringUtils.isNotBlank(serviceSuperClass)) {
                tc.setServiceSuperClass(StringUtils.substringAfterLast(serviceSuperClass, "."));
                tc.addServiceImportPackage(serviceSuperClass);
            }

            // Service实现类属性
            if (StringUtils.equals(configuration.getServiceImplName(), tc.getServiceImplName())
                    && StringUtils.isNotBlank(configuration.getServiceImplName())) {
                // 自定义文件命名，使用 %s 自动填充表实体名，举例：%sServiceImpl，会生成 UserServiceImpl
                tc.setServiceImplName(String.format(configuration.getServiceImplName(), tc.getEntityName()));
            } else if (StringUtils.isBlank(tc.getServiceImplName())) {
                tc.setServiceImplName(tc.getEntityName() + "ServiceImpl");
            }
            if (StringUtils.isBlank(tc.getServiceImplSuperClass())) {
                tc.setServiceImplSuperClass(BaseCrudServiceImpl.class.getName());
            }
            String serviceImplSuperClass = tc.getServiceImplSuperClass();
            if (StringUtils.isNotBlank(serviceImplSuperClass)) {
                tc.setServiceImplSuperClass(StringUtils.substringAfterLast(serviceImplSuperClass, "."));
                tc.addServiceImplImportPackage(serviceImplSuperClass);
            }

            // Web类属性
            if (StringUtils.equals(configuration.getWebName(), tc.getWebName())
                    && StringUtils.isNotBlank(configuration.getWebName())) {
                // 自定义文件命名，使用 %s 自动填充表实体名，举例：%sController，会生成 UserController
                tc.setWebName(String.format(configuration.getWebName(), tc.getEntityName()));
            } else if (StringUtils.isBlank(tc.getWebName())) {
                tc.setWebName(tc.getEntityName() + "Controller");
            }
            String webSuperClass = tc.getWebSuperClass();
            if (StringUtils.isNotBlank(webSuperClass)) {
                tc.setWebSuperClass(StringUtils.substringAfterLast(webSuperClass, "."));
                tc.addWebImportPackage(webSuperClass);
            }
            if (StringUtils.isBlank(tc.getWebRequestName())) {
                tc.setWebRequestName(StringUtil.toCamelCase(tc.getEntityName()));
            }

            String rootPackageName = StringUtils.defaultIfBlank(tc.getRootPackageName(),
                    configuration.getRootPackageName());
            if (StringUtils.isNotBlank(rootPackageName)) {
                // 父类package name有值，默认会分为：entity、mapper、service、web
                if (StringUtils.isBlank(tc.getEntityPackageName())) {
                    tc.setEntityPackageName(rootPackageName + ".entity");
                }
                if (StringUtils.isBlank(tc.getMapperPackageName())) {
                    tc.setMapperPackageName(rootPackageName + ".mapper");
                }
                if (StringUtils.isBlank(tc.getServicePackageName())) {
                    tc.setServicePackageName(rootPackageName + ".service");
                }
                if (StringUtils.isBlank(tc.getServiceImplPackageName())) {
                    tc.setServiceImplPackageName(rootPackageName + ".service.impl");
                }
                if (StringUtils.isBlank(tc.getWebPackageName())) {
                    tc.setWebPackageName(rootPackageName + ".web");
                }
            }
        }

        if (configuration.getJavaTypeResolver() == null) {
            JavaTypeResolverImpl javaTypeResolver = new JavaTypeResolverImpl();
            javaTypeResolver.addConfigurationProperties(configuration.getProperties());
            configuration.setJavaTypeResolver(javaTypeResolver);
        }
        configuration.setTodayYear(String.valueOf(LocalDate.now().getYear()));
        if (configuration.getGenerateDate() == null) {
            configuration.setGenerateDate(DateFormatUtils.format(new Date(), "yyyy年M月d日 ah:mm:ss"));
        }
        String copyright = configuration.getCopyright();
        if (StringUtils.isNotBlank(copyright)) {
            copyright = StringUtils.replace(copyright, "${todayYear}", configuration.getTodayYear());
            configuration.setCopyright(copyright);
        }
    }

    /**
     * 获取 {@code clazz } Class 中的所有字段，排除 static, transient
     * 字段，包含父类中的字段（重写的字段只会保留一个）
     *
     * @param clazz 反射类
     * @return 所有字段信息
     */
    public Map<String, Field> getSuperClassFields(Class<?> clazz) {
        Class<?> currentClass = Objects.requireNonNull(clazz);
        Map<String, Field> allFiledName = new HashMap<>();
        boolean isSupper = false;
        while (currentClass != null) {
            Field[] declaredFields = currentClass.getDeclaredFields();
            for (Field field : declaredFields) {
                String name = field.getName();
                if (Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers())) {
                    // 过滤字段: static, transient
                    continue;
                }
                if (isSupper && allFiledName.containsKey(name)) {
                    // 过滤子类已经重写的字段
                    continue;
                }
                allFiledName.put(name, field);
            }
            isSupper = true;
            currentClass = currentClass.getSuperclass();
        }
        return allFiledName;
    }

    /**
     * 初始化数据库表和字段信息
     *
     * @param tc 数据库表的配置
     * @param databaseMetaData 数据库元信息
     * @return 数据库表和字段信息
     * @throws SQLException 异常信息
     */
    private Table initTableInformation(TableConfiguration tc, DatabaseMetaData databaseMetaData) throws SQLException {
        JavaTypeResolver javaTypeResolver = configuration.getJavaTypeResolver();
        String localCatalog;
        String localSchema;
        String localTableName;
        if (databaseMetaData.storesLowerCaseIdentifiers()) {
            localCatalog = tc.getCatalog() == null ? null : tc.getCatalog().toLowerCase();
            localSchema = tc.getSchema() == null ? null : tc.getSchema().toLowerCase();
            localTableName = tc.getTableName() == null ? null : tc.getTableName().toLowerCase();
        } else if (databaseMetaData.storesUpperCaseIdentifiers()) {
            localCatalog = tc.getCatalog() == null ? null : tc.getCatalog().toUpperCase();
            localSchema = tc.getSchema() == null ? null : tc.getSchema().toUpperCase();
            localTableName = tc.getTableName() == null ? null : tc.getTableName().toUpperCase();
        } else {
            localCatalog = tc.getCatalog();
            localSchema = tc.getSchema();
            localTableName = tc.getTableName();
        }

        // process table
        Table table = new Table();
        BeanUtil.copyProperties(tc, table, null, false);
        // 复制自定义的所有属性
        table.setProperties(tc.getProperties());
        ResultSet rs = databaseMetaData.getTables(localCatalog, localSchema, localTableName, null);
        if (rs.next()) {
            if (BooleanUtils.isTrue(table.getUseCatalogOnGenerate())) {
                table.setCatalog(rs.getString("TABLE_CAT"));
            }
            if (BooleanUtils.isTrue(table.getUseSchemaOnGenerate())) {
                table.setSchema(rs.getString("TABLE_SCHEM"));
            }
            table.setTableName(rs.getString("TABLE_NAME"));
            table.setRemarks(rs.getString("REMARKS"));
            table.setTableType(rs.getString("TABLE_TYPE"));
            if (StringUtils.isBlank(table.getEntityName())) {
                table.setEntityName(StringUtil.toCamelCase(table.getTableName()));
            }
        }
        closeResultSet(rs);

        // process columns
        rs = databaseMetaData.getColumns(localCatalog, localSchema, localTableName, "%");
        boolean supportsIsAutoIncrement = false;
        boolean supportsIsGeneratedColumn = false;
        ResultSetMetaData rsMetaData = rs.getMetaData();
        int colCount = rsMetaData.getColumnCount();
        for (int i = 1; i <= colCount; i++) {
            if ("IS_AUTOINCREMENT".equals(rsMetaData.getColumnName(i))) {
                supportsIsAutoIncrement = true;
            }
            if ("IS_GENERATEDCOLUMN".equals(rsMetaData.getColumnName(i))) {
                supportsIsGeneratedColumn = true;
            }
        }
        while (rs.next()) {
            String columnName = rs.getString("COLUMN_NAME");
            if (tc.isColumnIgnored(columnName)) {
                // check ignoredColumns
                continue;
            }
            Column column = new Column();
            table.getColumns().add(column);

            column.setJdbcType(rs.getInt("DATA_TYPE"));
            column.setLength(rs.getInt("COLUMN_SIZE"));
            column.setColumnName(columnName);
            column.setNullable(rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable);
            column.setScale(rs.getInt("DECIMAL_DIGITS"));
            column.setRemarks(rs.getString("REMARKS"));
            column.setDefaultValue(rs.getString("COLUMN_DEF"));
            if (supportsIsAutoIncrement) {
                table.setAutoIncrement(true);
                column.setAutoIncrement("YES".equals(rs.getString("IS_AUTOINCREMENT")));
            }
            if (supportsIsGeneratedColumn) {
                column.setGeneratedColumn("YES".equals(rs.getString("IS_GENERATEDCOLUMN")));
            }

            column.setJavaProperty(StringUtil.toCamelCase(columnName));
            column.setFullyQualifiedJavaType(javaTypeResolver.calculateJavaType(column));
            List<String> importList = column.getFullyQualifiedJavaType().getImportList();
            if (!CollectionUtils.isEmpty(importList)) {
                table.getEntityImportPackages().addAll(importList);
            }
            column.setJdbcTypeName(javaTypeResolver.calculateJdbcTypeName(column));
            if (BooleanUtils.isTrue(tc.getAllColumnDelimitingEnabled())) {
                column.setColumnNameDelimited(true);
            } else {
                column.setColumnNameDelimited(SqlKeyWords.containsWord(columnName));
            }
            if (tc.getEntitySuperClassFields() != null
                    && tc.getEntitySuperClassFields().containsKey(column.getJavaProperty())) {
                column.setSuperClassField(true);
            }

            applyColumnOverrides(tc, column);
        }
        closeResultSet(rs);

        // process primary keys
        rs = databaseMetaData.getPrimaryKeys(localCatalog, localSchema, localTableName);
        // keep primary columns in key sequence order
        Map<Short, String> keyColumns = new TreeMap<>();
        while (rs.next()) {
            String columnName = rs.getString("COLUMN_NAME");
            short keySeq = rs.getShort("KEY_SEQ");
            keyColumns.put(keySeq, columnName);
        }
        for (String columnName : keyColumns.values()) {
            table.addPrimaryKeyColumn(columnName);
        }
        closeResultSet(rs);

        table.customize(tc);
        return table;
    }

    private void applyColumnOverrides(TableConfiguration tc, Column column) {
        ColumnOverride columnOverride = tc.getColumnOverride(column.getColumnName());
        if (columnOverride == null) {
            return;
        }
        if (StringUtils.isNotBlank(columnOverride.getJavaProperty())) {
            column.setJavaProperty(columnOverride.getJavaProperty());
        }
        if (StringUtils.isNotBlank(columnOverride.getJavaType())) {
            column.setFullyQualifiedJavaType(new FullyQualifiedJavaType(columnOverride.getJavaType()));
        }
        if (StringUtils.isNotBlank(columnOverride.getJdbcType())) {
            column.setJdbcTypeName(columnOverride.getJdbcType());
        }
        if (StringUtils.isNotBlank(columnOverride.getTypeHandler())) {
            column.setTypeHandler(columnOverride.getTypeHandler());
        }
        if (columnOverride.isColumnNameDelimited()) {
            column.setColumnNameDelimited(true);
        }
        column.setGeneratedAlways(columnOverride.isGeneratedAlways());
        column.setProperties(columnOverride.getProperties());
        columnOverride.customize(tc, column);
    }

    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.warn("closeResultSet failed", e);
            }
        }
    }

}
