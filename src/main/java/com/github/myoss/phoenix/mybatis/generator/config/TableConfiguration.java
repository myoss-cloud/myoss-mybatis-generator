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

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 生成 Mybatis 文件的时候，选择哪一张数据库表，数据库表的配置
 *
 * @author Jerry.Chen
 * @since 2018年5月5日 上午10:34:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TableConfiguration extends BaseConfiguration {
    /**
     * 表名称
     */
    private String                      tableName;
    private Boolean                     ignoreQualifiersAtRuntime;
    private Map<String, ColumnOverride> columnOverrideMap;

    /**
     * ignored columns
     */
    private Set<IgnoredColumn>          ignoredColumns;
    /**
     * ignored columns pattern
     */
    private Set<IgnoredColumnPattern>   ignoredColumnPatterns;

    public ColumnOverride getColumnOverride(String columnName) {
        return columnOverrideMap != null ? columnOverrideMap.get(columnName) : null;
    }

    /**
     * add ignored column
     *
     * @param columnName column name
     * @return 当前实例对象
     */
    public TableConfiguration addIgnoredColumn(String... columnName) {
        if (ignoredColumns == null) {
            ignoredColumns = new LinkedHashSet<>();
        }
        for (String s : columnName) {
            IgnoredColumn ignoredColumn = new IgnoredColumn(s);
            ignoredColumns.add(ignoredColumn);
        }
        return this;
    }

    /**
     * add ignored column pattern
     *
     * @param columnPattern column pattern
     * @return 当前实例对象
     */
    public TableConfiguration addIgnoredColumnPattern(String... columnPattern) {
        if (ignoredColumnPatterns == null) {
            ignoredColumnPatterns = new LinkedHashSet<>();
        }
        for (String s : columnPattern) {
            IgnoredColumnPattern ignoredColumnPattern = new IgnoredColumnPattern(s);
            ignoredColumnPatterns.add(ignoredColumnPattern);
        }
        return this;
    }

    /**
     * check the column is ignored column
     *
     * @param columnName column name
     * @return true: is ignored, false: not ignored
     */
    public boolean isColumnIgnored(String columnName) {
        if (ignoredColumns != null) {
            for (IgnoredColumn ignoredColumn : ignoredColumns) {
                if (ignoredColumn.matches(columnName)) {
                    return true;
                }
            }
        }

        if (ignoredColumnPatterns != null) {
            for (IgnoredColumnPattern ignoredColumnPattern : ignoredColumnPatterns) {
                if (ignoredColumnPattern.matches(columnName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
