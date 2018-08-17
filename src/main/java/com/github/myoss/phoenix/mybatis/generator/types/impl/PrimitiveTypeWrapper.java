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

package com.github.myoss.phoenix.mybatis.generator.types.impl;

import lombok.Getter;

/**
 * Java 中 8 大原始数据类型
 *
 * @author Jerry.Chen
 * @since 2018年5月14日 下午1:46:35
 */
public final class PrimitiveTypeWrapper extends FullyQualifiedJavaType {
    private static PrimitiveTypeWrapper BOOLEAN_INSTANCE;
    private static PrimitiveTypeWrapper BYTE_INSTANCE;
    private static PrimitiveTypeWrapper CHARACTER_INSTANCE;
    private static PrimitiveTypeWrapper DOUBLE_INSTANCE;
    private static PrimitiveTypeWrapper FLOAT_INSTANCE;
    private static PrimitiveTypeWrapper INTEGER_INSTANCE;
    private static PrimitiveTypeWrapper LONG_INSTANCE;
    private static PrimitiveTypeWrapper SHORT_INSTANCE;

    @Getter
    private String                      toPrimitiveMethod;

    /**
     * Use the static getXXXInstance methods to gain access to one of the type
     * wrappers.
     *
     * @param fullyQualifiedName fully qualified name of the wrapper type
     * @param toPrimitiveMethod the method that returns the wrapped primitive
     */
    private PrimitiveTypeWrapper(String fullyQualifiedName, String toPrimitiveMethod) {
        super(fullyQualifiedName);
        this.toPrimitiveMethod = toPrimitiveMethod;
    }

    /**
     * 获取 Boolean 类型
     *
     * @return Boolean 类型
     */
    public static PrimitiveTypeWrapper getBooleanInstance() {
        if (BOOLEAN_INSTANCE == null) {
            BOOLEAN_INSTANCE = new PrimitiveTypeWrapper("java.lang.Boolean", "booleanValue()");
        }
        return BOOLEAN_INSTANCE;
    }

    /**
     * 获取 Byte 类型
     *
     * @return Byte 类型
     */
    public static PrimitiveTypeWrapper getByteInstance() {
        if (BYTE_INSTANCE == null) {
            BYTE_INSTANCE = new PrimitiveTypeWrapper("java.lang.Byte", "byteValue()");
        }
        return BYTE_INSTANCE;
    }

    /**
     * 获取 Character 类型
     *
     * @return Character 类型
     */
    public static PrimitiveTypeWrapper getCharacterInstance() {
        if (CHARACTER_INSTANCE == null) {
            CHARACTER_INSTANCE = new PrimitiveTypeWrapper("java.lang.Character", "charValue()");
        }
        return CHARACTER_INSTANCE;
    }

    /**
     * 获取 Double 类型
     *
     * @return Double 类型
     */
    public static PrimitiveTypeWrapper getDoubleInstance() {
        if (DOUBLE_INSTANCE == null) {
            DOUBLE_INSTANCE = new PrimitiveTypeWrapper("java.lang.Double", "doubleValue()");
        }
        return DOUBLE_INSTANCE;
    }

    /**
     * 获取 Float 类型
     *
     * @return Float 类型
     */
    public static PrimitiveTypeWrapper getFloatInstance() {
        if (FLOAT_INSTANCE == null) {
            FLOAT_INSTANCE = new PrimitiveTypeWrapper("java.lang.Float", "floatValue()");
        }
        return FLOAT_INSTANCE;
    }

    /**
     * 获取 Integer 类型
     *
     * @return Integer 类型
     */
    public static PrimitiveTypeWrapper getIntegerInstance() {
        if (INTEGER_INSTANCE == null) {
            INTEGER_INSTANCE = new PrimitiveTypeWrapper("java.lang.Integer", "intValue()");
        }
        return INTEGER_INSTANCE;
    }

    /**
     * 获取 Long 类型
     *
     * @return Long 类型
     */
    public static PrimitiveTypeWrapper getLongInstance() {
        if (LONG_INSTANCE == null) {
            LONG_INSTANCE = new PrimitiveTypeWrapper("java.lang.Long", "longValue()");
        }
        return LONG_INSTANCE;
    }

    /**
     * 获取 Short 类型
     *
     * @return Short 类型
     */
    public static PrimitiveTypeWrapper getShortInstance() {
        if (SHORT_INSTANCE == null) {
            SHORT_INSTANCE = new PrimitiveTypeWrapper("java.lang.Short", "shortValue()");
        }
        return SHORT_INSTANCE;
    }
}
