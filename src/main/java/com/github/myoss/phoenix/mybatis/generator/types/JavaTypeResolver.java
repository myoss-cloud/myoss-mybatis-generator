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

package com.github.myoss.phoenix.mybatis.generator.types;

import java.util.Map;

import com.github.myoss.phoenix.mybatis.generator.db.Column;
import com.github.myoss.phoenix.mybatis.generator.types.impl.FullyQualifiedJavaType;

/**
 * 数据库字段转换 Java 类型接口
 *
 * @author Jerry.Chen
 * @since 2018年5月5日 上午11:38:48
 */
public interface JavaTypeResolver {
    /**
     * Adds properties for this instance from any properties configured in the
     * JavaTypeResolverConfiguration.
     * <p>
     * This method will be called before any of the get methods.
     *
     * @param properties All properties from the configuration
     */
    void addConfigurationProperties(Map<String, Object> properties);

    /**
     * Calculates and returns the Java type that should be associated with this
     * column based on the jdbc type, length, and scale of the column.
     *
     * @param column the column whose Java type needs to be calculated
     * @return the calculated type, or null if an unsupported data type. If null
     *         is returned, we will set the type to Object and issue a warning
     *         unless the column is ignored or otherwise overridden
     */
    FullyQualifiedJavaType calculateJavaType(Column column);

    /**
     * Calculates and returns the JDBC type name that should be associated with
     * this column based on the jdbc type, length, and scale of the column.
     *
     * @param column the column whose Java type needs to be calculated
     * @return the calculated type name, or null if an unsupported data type. If
     *         null is returned, we will set the type to OTHER and issue a
     *         warning unless the column is ignored or otherwise overridden
     */
    String calculateJdbcTypeName(Column column);
}
