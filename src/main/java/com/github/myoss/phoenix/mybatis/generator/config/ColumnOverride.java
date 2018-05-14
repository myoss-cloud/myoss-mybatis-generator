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

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.github.myoss.phoenix.mybatis.generator.db.Column;

/**
 * 覆盖字段列属性配置
 *
 * @author Jerry.Chen 2018年5月5日 下午3:33:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ColumnOverride extends AbstractPropertyHolder {
    /** The column name. */
    private String  columnName;

    /** The java property. */
    private String  javaProperty;

    /** The jdbc type. */
    private String  jdbcType;

    /** The java type. */
    private String  javaType;

    /** The type handler. */
    private String  typeHandler;

    /** The is column name delimited. */
    private boolean isColumnNameDelimited;

    /** The configured delimited column name. */
    private String  configuredDelimitedColumnName;

    /**
     * If true, the column is a GENERATED ALWAYS column which means that it
     * should not be used in insert or update statements.
     */
    private boolean isGeneratedAlways;

    /**
     * Instantiates a new column override.
     *
     * @param columnName the column name
     */
    public ColumnOverride(String columnName) {
        super();

        this.columnName = columnName;
    }

    /**
     * 自定义方法，可以用于初始化数据库字段属性
     *
     * @param tc 数据库表配置
     * @param column 数据库列信息
     */
    public void customize(TableConfiguration tc, Column column) {
        // do nothing
    }

}
