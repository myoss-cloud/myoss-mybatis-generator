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

import com.github.myoss.phoenix.mybatis.generator.types.impl.JavaTypeResolverImpl;
import com.github.myoss.phoenix.mybatis.table.annotation.SelectKey;
import com.github.myoss.phoenix.mybatis.table.annotation.SequenceKey;

/**
 * 常量属性
 *
 * @author Jerry.Chen
 * @since 2018年5月14日 下午1:59:09
 */
public class PropertyRegistry {
    /**
     * 默认为： {@link Boolean#TRUE}
     *
     * @see JavaTypeResolverImpl
     */
    public static final String TYPE_RESOLVER_FORCE_BIG_DECIMALS                                = "forceBigDecimals";
    /**
     * 默认为： {@link Boolean#FALSE}
     *
     * @see JavaTypeResolverImpl
     */
    public static final String TYPE_RESOLVER_USE_JSR310_TYPES                                  = "useJSR310Types";

    /**
     * 用于设置 {@link SelectKey#sql()}
     */
    public static final String SELECT_KEY_SELECT_SQL                                           = "selectKeySelectSql";
    /**
     * 用于设置 {@link SelectKey#resultType()}
     */
    public static final String SELECT_KEY_RESULT_TYPE                                          = "selectKeyResultType";
    /**
     * 用于设置 {@link SelectKey#order()}
     */
    public static final String SELECT_KEY_ORDER                                                = "selectKeyOrder";

    /**
     * 用于设置 {@link SequenceKey#sequenceClass()}
     */
    public static final String SEQUENCE_KEY_SEQUENCE_CLASS                                     = "sequenceKeySequenceClass";
    /**
     * 用于设置 {@link SequenceKey#sequenceBeanName()}
     */
    public static final String SEQUENCE_KEY_SEQUENCE_BEAN_NAME                                 = "sequenceKeySequenceBeanName";
    /**
     * 用于设置 {@link SequenceKey#sequenceName()}
     */
    public static final String SEQUENCE_KEY_SEQUENCE_NAME                                      = "sequenceKeySequenceName";
    /**
     * 用于设置 {@link SequenceKey#order()}
     */
    public static final String SEQUENCE_KEY_ORDER                                              = "sequenceKeyOrder";

    /**
     * 用于生成 Entity 实体类文件的时候，是否使用主键字段的 java 类型做为 class 的泛型。
     * <p>
     * 示例：使用 Long 做为 class 的泛型
     * </p>
     *
     * <pre>
     * public class UserHistory extends AuditIdEntity&lt;Long&gt; {
     *
     * }
     * </pre>
     */
    public static final String USE_PRIMARY_KEY_JAVA_TYPE_FOR_CLASS_GENERIC_TYPE_IN_ENTITY_FILE = "usePrimaryKeyJavaTypeForClassGenericTypeInEntityFile";

    /**
     * 用于生成 Web 文件的时候，是否启用方法的 {@code @RequestMapping} 注解，默认是不启用的。如果要启用请设置为:
     * {@link Boolean#TRUE}
     */
    public static final String ALL_METHOD_ENABLE_IN_WEB_FILE                                   = "allMethodEnableInWebFile";
}
