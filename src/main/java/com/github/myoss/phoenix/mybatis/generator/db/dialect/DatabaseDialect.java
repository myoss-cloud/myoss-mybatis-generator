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

package com.github.myoss.phoenix.mybatis.generator.db.dialect;

import java.util.Objects;

import com.github.myoss.phoenix.mybatis.generator.db.Column;
import com.github.myoss.phoenix.mybatis.generator.db.Table;

/**
 * 数据库方言接口
 *
 * @author Jerry.Chen
 * @since 2018年5月15日 下午1:44:23
 */
public interface DatabaseDialect {
    /**
     * 获取数据库名字
     *
     * @return 数据库名字
     */
    String getDatabaseName();

    /**
     * 数据库检索标识或自动编号值的 sql statement
     *
     * @return sql statement
     */
    default String getSelectInsertId() {
        DatabaseDialects databaseDialect = DatabaseDialects.getDatabaseDialect(getDatabaseName());
        Objects.requireNonNull(databaseDialect);
        return databaseDialect.getIdentityRetrievalStatement();
    };

    /**
     * 可选配置，用于设置 {@link #getEscapedColumnName(Table, Column)}
     *
     * @return 开始的标识限定符
     */
    default String getBeginningDelimiter() {
        return "";
    };

    /**
     * 可选配置，用于设置 {@link #getEscapedColumnName(Table, Column)}
     *
     * @return 结束的标识限定符
     */
    default String getEndingDelimiter() {
        return "";
    };

    /**
     * Gets the escaped column name.
     *
     * @param table the table
     * @param column the introspected column
     * @return the escaped column name
     */
    default String getEscapedColumnName(Table table, Column column) {
        if (column.isColumnNameDelimited()) {
            StringBuilder sb = new StringBuilder();
            if (table.getBeginningDelimiter() != null) {
                sb.append(table.getBeginningDelimiter());
            } else {
                sb.append(getBeginningDelimiter());
            }
            sb.append(column.getColumnName());

            if (table.getEndingDelimiter() != null) {
                sb.append(table.getEndingDelimiter());
            } else {
                sb.append(getEndingDelimiter());
            }
            return sb.toString();
        } else {
            return column.getColumnName();
        }
    }
}
