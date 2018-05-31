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

package com.github.myoss.phoenix.mybatis.generator.db;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import com.github.myoss.phoenix.mybatis.generator.config.BaseConfiguration;
import com.github.myoss.phoenix.mybatis.generator.config.TableConfiguration;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据库表的信息
 *
 * @author Jerry.Chen
 * @since 2018年5月5日 上午12:16:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Table extends BaseConfiguration {
    /**
     * 数据库表名
     */
    private String       tableName;
    /**
     * 编码之后的数据库表名，比如：表名是关键字、有空格
     */
    private String       escapedTableName;
    /**
     * 所有字段（包含主键字段）
     */
    private List<Column> columns;
    /**
     * 主键字段
     */
    private List<Column> primaryKeyColumns;
    /**
     * Table remarks retrieved from database metadata.
     */
    private String       remarks;
    /**
     * Table type retrieved from database metadata.
     */
    private String       tableType;
    /**
     * 数据库表的主键是否为自动增长
     */
    private boolean      isAutoIncrement;

    public Table() {
        this.columns = new ArrayList<>();
        this.primaryKeyColumns = new ArrayList<>();
        this.entityImportPackages = new LinkedHashSet<>();
        this.mapperImportPackages = new LinkedHashSet<>();
        this.serviceImportPackages = new LinkedHashSet<>();
        this.serviceImplImportPackages = new LinkedHashSet<>();
        this.webImportPackages = new LinkedHashSet<>();
    }

    /**
     * 自定义方法，可以用于初始化数据库字段属性
     *
     * @param tc 数据库表配置
     */
    public void customize(TableConfiguration tc) {
        // do nothing
    }

    public Table addPrimaryKeyColumn(String columnName) {
        // first search base columns
        for (Column column : columns) {
            if (column.getColumnName().equals(columnName)) {
                primaryKeyColumns.add(column);
                column.setPrimaryKey(true);
                break;
            }
        }
        return this;
    }
}
