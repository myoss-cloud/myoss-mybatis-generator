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

import java.sql.Types;

import org.apache.commons.lang3.StringUtils;

import com.github.myoss.phoenix.mybatis.generator.config.AbstractPropertyHolder;
import com.github.myoss.phoenix.mybatis.generator.types.impl.FullyQualifiedJavaType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据库表的字段信息
 *
 * @author Jerry.Chen
 * @since 2018年5月5日 上午12:16:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Column extends AbstractPropertyHolder {
    /**
     * 字段名
     */
    private String                 columnName;
    /**
     * 编码之后的字段名，比如：字段名是关键字、有空格
     */
    private String                 escapedColumnName;

    private int                    jdbcType;

    private String                 jdbcTypeName;

    private boolean                nullable;

    private int                    length;

    private int                    scale;

    private boolean                identity;

    private boolean                sequenceColumn;

    private String                 javaProperty;

    private FullyQualifiedJavaType fullyQualifiedJavaType;

    private String                 tableAlias;

    private String                 typeHandler;

    private boolean                columnNameDelimited;

    /**
     * any database comment associated with this column. May be null
     */
    private String                 remarks;

    private String                 defaultValue;

    /**
     * true if the JDBC driver reports that this column is auto-increment.
     */
    private boolean                autoIncrement;

    /**
     * true if the JDBC driver reports that this column is generated.
     */
    private boolean                generatedColumn;

    /**
     * True if there is a column override that defines this column as GENERATED
     * ALWAYS.
     */
    private boolean                generatedAlways;

    /**
     * 是否为主键
     */
    private boolean                primaryKey;
    /**
     * 字段是否在父类class中
     */
    private boolean                superClassField;
    /**
     * 是否为索引字段
     */
    private boolean                indexColumn;
    /**
     * 索引信息
     */
    private IndexInfo              indexInfo;

    /**
     * Constructs a Column definition. This object holds all the information
     * about a column that is required to generate Java objects and SQL maps;
     */
    public Column() {
        super();
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
        columnNameDelimited = StringUtils.isAlphaSpace(columnName);
    }

    public boolean isBLOBColumn() {
        String typeName = getJdbcTypeName();
        return "BINARY".equals(typeName) || "BLOB".equals(typeName) || "CLOB".equals(typeName)
                || "LONGNVARCHAR".equals(typeName) || "LONGVARBINARY".equals(typeName)
                || "LONGVARCHAR".equals(typeName) || "NCLOB".equals(typeName) || "VARBINARY".equals(typeName);
    }

    public boolean isStringColumn() {
        return fullyQualifiedJavaType.equals(FullyQualifiedJavaType.getStringInstance());
    }

    public boolean isJdbcCharacterColumn() {
        return jdbcType == Types.CHAR || jdbcType == Types.CLOB || jdbcType == Types.LONGVARCHAR
                || jdbcType == Types.VARCHAR || jdbcType == Types.LONGNVARCHAR || jdbcType == Types.NCHAR
                || jdbcType == Types.NCLOB || jdbcType == Types.NVARCHAR;
    }

    public boolean isJDBCDateColumn() {
        return fullyQualifiedJavaType.equals(FullyQualifiedJavaType.getDateInstance())
                && "DATE".equalsIgnoreCase(jdbcTypeName);
    }

    public boolean isJDBCTimeColumn() {
        return fullyQualifiedJavaType.equals(FullyQualifiedJavaType.getDateInstance())
                && "TIME".equalsIgnoreCase(jdbcTypeName);
    }
}
