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

package app.myoss.cloud.mybatis.generator.types.impl;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;

import app.myoss.cloud.mybatis.generator.config.PropertyRegistry;
import app.myoss.cloud.mybatis.generator.db.Column;
import app.myoss.cloud.mybatis.generator.types.JavaTypeResolver;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据库字段转换 Java 类型默认实现
 *
 * @author Jerry.Chen
 * @since 2018年5月5日 上午11:40:12
 */
public class JavaTypeResolverImpl implements JavaTypeResolver {
    protected Map<String, Object>                                    properties;
    protected Map<Integer, JavaTypeResolverImpl.JdbcTypeInformation> typeMap;
    protected boolean                                                forceBigDecimals;
    protected boolean                                                useJSR310Types;
    private static final int                                         TIME_WITH_TIMEZONE      = 2013;
    private static final int                                         TIMESTAMP_WITH_TIMEZONE = 2014;

    /**
     * 数据库字段转换 Java 类型默认实现
     */
    public JavaTypeResolverImpl() {
        super();
        properties = new HashMap<>();
        typeMap = new HashMap<>();

        typeMap.put(Types.ARRAY, new JavaTypeResolverImpl.JdbcTypeInformation("ARRAY",
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.BIGINT, new JavaTypeResolverImpl.JdbcTypeInformation("BIGINT",
                new FullyQualifiedJavaType(Long.class.getName())));
        typeMap.put(Types.BINARY,
                new JavaTypeResolverImpl.JdbcTypeInformation("BINARY", new FullyQualifiedJavaType("byte[]")));
        typeMap.put(Types.BIT, new JavaTypeResolverImpl.JdbcTypeInformation("BIT",
                new FullyQualifiedJavaType(Boolean.class.getName())));
        typeMap.put(Types.BLOB,
                new JavaTypeResolverImpl.JdbcTypeInformation("BLOB", new FullyQualifiedJavaType("byte[]")));
        typeMap.put(Types.BOOLEAN, new JavaTypeResolverImpl.JdbcTypeInformation("BOOLEAN",
                new FullyQualifiedJavaType(Boolean.class.getName())));
        typeMap.put(Types.CHAR, new JavaTypeResolverImpl.JdbcTypeInformation("CHAR",
                new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.CLOB, new JavaTypeResolverImpl.JdbcTypeInformation("CLOB",
                new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.DATALINK, new JavaTypeResolverImpl.JdbcTypeInformation("DATALINK",
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.DATE,
                new JavaTypeResolverImpl.JdbcTypeInformation("DATE", new FullyQualifiedJavaType(Date.class.getName())));
        typeMap.put(Types.DECIMAL, new JavaTypeResolverImpl.JdbcTypeInformation("DECIMAL",
                new FullyQualifiedJavaType(BigDecimal.class.getName())));
        typeMap.put(Types.DISTINCT, new JavaTypeResolverImpl.JdbcTypeInformation("DISTINCT",
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.DOUBLE, new JavaTypeResolverImpl.JdbcTypeInformation("DOUBLE",
                new FullyQualifiedJavaType(Double.class.getName())));
        typeMap.put(Types.FLOAT, new JavaTypeResolverImpl.JdbcTypeInformation("FLOAT",
                new FullyQualifiedJavaType(Double.class.getName())));
        typeMap.put(Types.INTEGER, new JavaTypeResolverImpl.JdbcTypeInformation("INTEGER",
                new FullyQualifiedJavaType(Integer.class.getName())));
        typeMap.put(Types.JAVA_OBJECT, new JavaTypeResolverImpl.JdbcTypeInformation("JAVA_OBJECT",
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.LONGNVARCHAR, new JavaTypeResolverImpl.JdbcTypeInformation("LONGNVARCHAR",
                new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.LONGVARBINARY,
                new JavaTypeResolverImpl.JdbcTypeInformation("LONGVARBINARY", new FullyQualifiedJavaType("byte[]")));
        typeMap.put(Types.LONGVARCHAR, new JavaTypeResolverImpl.JdbcTypeInformation("LONGVARCHAR",
                new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.NCHAR, new JavaTypeResolverImpl.JdbcTypeInformation("NCHAR",
                new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.NCLOB, new JavaTypeResolverImpl.JdbcTypeInformation("NCLOB",
                new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.NVARCHAR, new JavaTypeResolverImpl.JdbcTypeInformation("NVARCHAR",
                new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.NULL, new JavaTypeResolverImpl.JdbcTypeInformation("NULL",
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.NUMERIC, new JavaTypeResolverImpl.JdbcTypeInformation("NUMERIC",
                new FullyQualifiedJavaType(BigDecimal.class.getName())));
        typeMap.put(Types.OTHER, new JavaTypeResolverImpl.JdbcTypeInformation("OTHER",
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.REAL, new JavaTypeResolverImpl.JdbcTypeInformation("REAL",
                new FullyQualifiedJavaType(Float.class.getName())));
        typeMap.put(Types.REF, new JavaTypeResolverImpl.JdbcTypeInformation("REF",
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.SMALLINT, new JavaTypeResolverImpl.JdbcTypeInformation("SMALLINT",
                new FullyQualifiedJavaType(Short.class.getName())));
        typeMap.put(Types.STRUCT, new JavaTypeResolverImpl.JdbcTypeInformation("STRUCT",
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.TIME,
                new JavaTypeResolverImpl.JdbcTypeInformation("TIME", new FullyQualifiedJavaType(Date.class.getName())));
        typeMap.put(Types.TIMESTAMP, new JavaTypeResolverImpl.JdbcTypeInformation("TIMESTAMP",
                new FullyQualifiedJavaType(Date.class.getName())));
        typeMap.put(Types.TINYINT, new JavaTypeResolverImpl.JdbcTypeInformation("TINYINT",
                new FullyQualifiedJavaType(Byte.class.getName())));
        typeMap.put(Types.VARBINARY,
                new JavaTypeResolverImpl.JdbcTypeInformation("VARBINARY", new FullyQualifiedJavaType("byte[]")));
        typeMap.put(Types.VARCHAR, new JavaTypeResolverImpl.JdbcTypeInformation("VARCHAR",
                new FullyQualifiedJavaType(String.class.getName())));
        // JDK 1.8 types
        typeMap.put(TIME_WITH_TIMEZONE, new JavaTypeResolverImpl.JdbcTypeInformation("TIME_WITH_TIMEZONE",
                new FullyQualifiedJavaType("java.time.OffsetTime")));
        typeMap.put(TIMESTAMP_WITH_TIMEZONE, new JavaTypeResolverImpl.JdbcTypeInformation("TIMESTAMP_WITH_TIMEZONE",
                new FullyQualifiedJavaType("java.time.OffsetDateTime")));
    }

    @Override
    public void addConfigurationProperties(Map<String, Object> properties) {
        this.properties.putAll(properties);
        forceBigDecimals = BooleanUtils.toBooleanDefaultIfNull(
                (Boolean) properties.get(PropertyRegistry.TYPE_RESOLVER_FORCE_BIG_DECIMALS), true);
        useJSR310Types = BooleanUtils.toBooleanDefaultIfNull(
                (Boolean) properties.get(PropertyRegistry.TYPE_RESOLVER_USE_JSR310_TYPES), false);
    }

    @Override
    public FullyQualifiedJavaType calculateJavaType(Column column) {
        FullyQualifiedJavaType answer = null;
        JavaTypeResolverImpl.JdbcTypeInformation jdbcTypeInformation = typeMap.get(column.getJdbcType());

        if (jdbcTypeInformation != null) {
            answer = jdbcTypeInformation.getFullyQualifiedJavaType();
            answer = overrideDefaultType(column, answer);
        }

        return answer;
    }

    /**
     * override default type
     *
     * @param column Column
     * @param defaultType FullyQualifiedJavaType
     * @return new FullyQualifiedJavaType
     */
    protected FullyQualifiedJavaType overrideDefaultType(Column column, FullyQualifiedJavaType defaultType) {
        FullyQualifiedJavaType answer = defaultType;

        switch (column.getJdbcType()) {
            case Types.BIT:
                answer = calculateBitReplacement(column, defaultType);
                break;
            case Types.DATE:
                answer = calculateDateType(column, defaultType);
                break;
            case Types.DECIMAL:
            case Types.NUMERIC:
                answer = calculateBigDecimalReplacement(column, defaultType);
                break;
            case Types.TIME:
                answer = calculateTimeType(column, defaultType);
                break;
            case Types.TIMESTAMP:
                answer = calculateTimestampType(column, defaultType);
                break;
            default:
                break;
        }

        return answer;
    }

    /**
     * calculate date type
     *
     * @param column Column
     * @param defaultType FullyQualifiedJavaType
     * @return new FullyQualifiedJavaType
     */
    protected FullyQualifiedJavaType calculateDateType(Column column, FullyQualifiedJavaType defaultType) {
        FullyQualifiedJavaType answer;

        if (useJSR310Types) {
            answer = new FullyQualifiedJavaType("java.time.LocalDate");
        } else {
            answer = defaultType;
        }

        return answer;
    }

    /**
     * calculate time type
     *
     * @param column Column
     * @param defaultType FullyQualifiedJavaType
     * @return new FullyQualifiedJavaType
     */
    protected FullyQualifiedJavaType calculateTimeType(Column column, FullyQualifiedJavaType defaultType) {
        FullyQualifiedJavaType answer;

        if (useJSR310Types) {
            answer = new FullyQualifiedJavaType("java.time.LocalTime");
        } else {
            answer = defaultType;
        }

        return answer;
    }

    /**
     * calculate timestamp type
     *
     * @param column Column
     * @param defaultType FullyQualifiedJavaType
     * @return new FullyQualifiedJavaType
     */
    protected FullyQualifiedJavaType calculateTimestampType(Column column, FullyQualifiedJavaType defaultType) {
        FullyQualifiedJavaType answer;

        if (useJSR310Types) {
            answer = new FullyQualifiedJavaType("java.time.LocalDateTime");
        } else {
            answer = defaultType;
        }

        return answer;
    }

    /**
     * calculate bit type
     *
     * @param column Column
     * @param defaultType FullyQualifiedJavaType
     * @return new FullyQualifiedJavaType
     */
    protected FullyQualifiedJavaType calculateBitReplacement(Column column, FullyQualifiedJavaType defaultType) {
        FullyQualifiedJavaType answer;

        if (column.getLength() > 1) {
            answer = new FullyQualifiedJavaType("byte[]");
        } else {
            answer = defaultType;
        }

        return answer;
    }

    /**
     * calculate BigDecimal type
     *
     * @param column Column
     * @param defaultType FullyQualifiedJavaType
     * @return new FullyQualifiedJavaType
     */
    protected FullyQualifiedJavaType calculateBigDecimalReplacement(Column column, FullyQualifiedJavaType defaultType) {
        FullyQualifiedJavaType answer;

        if (column.getScale() > 0 || column.getLength() > 18 || forceBigDecimals) {
            answer = defaultType;
        } else if (column.getLength() > 9) {
            answer = new FullyQualifiedJavaType(Long.class.getName());
        } else if (column.getLength() > 4) {
            answer = new FullyQualifiedJavaType(Integer.class.getName());
        } else {
            answer = new FullyQualifiedJavaType(Short.class.getName());
        }

        return answer;
    }

    @Override
    public String calculateJdbcTypeName(Column column) {
        String answer = null;
        JavaTypeResolverImpl.JdbcTypeInformation jdbcTypeInformation = typeMap.get(column.getJdbcType());

        if (jdbcTypeInformation != null) {
            answer = jdbcTypeInformation.getJdbcTypeName();
        }

        return answer;
    }

    /**
     * Jdbc Type Information
     */
    @AllArgsConstructor
    public static class JdbcTypeInformation {
        @Getter
        private String                 jdbcTypeName;
        @Getter
        private FullyQualifiedJavaType fullyQualifiedJavaType;
    }
}
