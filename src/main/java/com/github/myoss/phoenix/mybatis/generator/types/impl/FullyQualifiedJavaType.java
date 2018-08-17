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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

/**
 * Java限定的数据类型
 *
 * @author Jerry.Chen
 * @since 2018年5月14日 下午1:44:32
 */
@Getter
public class FullyQualifiedJavaType implements Comparable<FullyQualifiedJavaType> {

    private static final String           JAVA_LANG                   = "java.lang";

    private static FullyQualifiedJavaType INT_INSTANCE                = null;

    private static FullyQualifiedJavaType STRING_INSTANCE             = null;

    private static FullyQualifiedJavaType BOOLEAN_PRIMITIVE_INSTANCE  = null;

    private static FullyQualifiedJavaType OBJECT_INSTANCE             = null;

    private static FullyQualifiedJavaType DATE_INSTANCE               = null;

    private static FullyQualifiedJavaType CRITERIA_INSTANCE           = null;

    private static FullyQualifiedJavaType GENERATED_CRITERIA_INSTANCE = null;

    /** The short name without any generic arguments. */
    private String                        baseShortName;

    /** The fully qualified name without any generic arguments. */
    private String                        baseQualifiedName;

    private boolean                       explicitlyImported;

    private String                        packageName;

    private boolean                       primitive;

    private boolean                       isArray;

    /**
     * Gets the primitive type wrapper.
     */
    private PrimitiveTypeWrapper          primitiveTypeWrapper;

    private List<FullyQualifiedJavaType>  typeArguments;

    /** the following three values are used for dealing with wildcard types */
    private boolean                       wildcardType;

    private boolean                       boundedWildcard;

    private boolean                       extendsBoundedWildcard;

    /**
     * Use this constructor to construct a generic type with the specified type
     * parameters.
     *
     * @param fullTypeSpecification the full type specification
     */
    public FullyQualifiedJavaType(String fullTypeSpecification) {
        super();
        typeArguments = new ArrayList<>();
        parse(fullTypeSpecification);
    }

    private String getString(String key, String parm1) {
        return String.format(key, parm1);
    }

    /**
     * Returns the fully qualified name - including any generic type parameters.
     *
     * @return Returns the fullyQualifiedName.
     */
    public String getFullyQualifiedName() {
        StringBuilder sb = new StringBuilder();
        if (wildcardType) {
            sb.append('?');
            if (boundedWildcard) {
                if (extendsBoundedWildcard) {
                    sb.append(" extends ");
                } else {
                    sb.append(" super ");
                }

                sb.append(baseQualifiedName);
            }
        } else {
            sb.append(baseQualifiedName);
        }

        if (typeArguments.size() > 0) {
            boolean first = true;
            sb.append('<');
            for (FullyQualifiedJavaType fqjt : typeArguments) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                sb.append(fqjt.getFullyQualifiedName());

            }
            sb.append('>');
        }

        return sb.toString();
    }

    /**
     * Returns a list of Strings that are the fully qualified names of this
     * type, and any generic type argument associated with this type.
     *
     * @return the import list
     */
    public List<String> getImportList() {
        List<String> answer = new ArrayList<String>();
        if (isExplicitlyImported()) {
            int index = baseShortName.indexOf('.');
            if (index == -1) {
                answer.add(calculateActualImport(baseQualifiedName));
            } else {
                // an inner class is specified, only import the top
                // level class
                StringBuilder sb = new StringBuilder();
                sb.append(packageName);
                sb.append('.');
                sb.append(calculateActualImport(baseShortName.substring(0, index)));
                answer.add(sb.toString());
            }
        }

        for (FullyQualifiedJavaType fqjt : typeArguments) {
            answer.addAll(fqjt.getImportList());
        }

        return answer;
    }

    private String calculateActualImport(String name) {
        String answer = name;
        if (this.isArray()) {
            int index = name.indexOf("[");
            if (index != -1) {
                answer = name.substring(0, index);
            }
        }
        return answer;
    }

    /**
     * Gets the short name.
     *
     * @return Returns the shortName - including any type arguments.
     */
    public String getShortName() {
        StringBuilder sb = new StringBuilder();
        if (wildcardType) {
            sb.append('?');
            if (boundedWildcard) {
                if (extendsBoundedWildcard) {
                    sb.append(" extends ");
                } else {
                    sb.append(" super ");
                }

                sb.append(baseShortName);
            }
        } else {
            sb.append(baseShortName);
        }

        if (typeArguments.size() > 0) {
            boolean first = true;
            sb.append('<');
            for (FullyQualifiedJavaType fqjt : typeArguments) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                sb.append(fqjt.getShortName());

            }
            sb.append('>');
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof FullyQualifiedJavaType)) {
            return false;
        }

        FullyQualifiedJavaType other = (FullyQualifiedJavaType) obj;

        return getFullyQualifiedName().equals(other.getFullyQualifiedName());
    }

    @Override
    public int hashCode() {
        return getFullyQualifiedName().hashCode();
    }

    @Override
    public String toString() {
        return getFullyQualifiedName();
    }

    /**
     * 获取 int 类型
     *
     * @return int 类型
     */
    public static FullyQualifiedJavaType getIntInstance() {
        if (INT_INSTANCE == null) {
            INT_INSTANCE = new FullyQualifiedJavaType("int");
        }

        return INT_INSTANCE;
    }

    /**
     * 创建 Map 类型
     *
     * @return Map 类型
     */
    public static FullyQualifiedJavaType getNewMapInstance() {
        // always return a new instance because the type may be parameterized
        return new FullyQualifiedJavaType("java.util.Map");
    }

    /**
     * 创建 List 类型
     *
     * @return List 类型
     */
    public static FullyQualifiedJavaType getNewListInstance() {
        // always return a new instance because the type may be parameterized
        return new FullyQualifiedJavaType("java.util.List");
    }

    /**
     * 创建 HashMap 类型
     *
     * @return HashMap 类型
     */
    public static FullyQualifiedJavaType getNewHashMapInstance() {
        // always return a new instance because the type may be parameterized
        return new FullyQualifiedJavaType("java.util.HashMap");
    }

    /**
     * 创建 ArrayList 类型
     *
     * @return ArrayList 类型
     */
    public static FullyQualifiedJavaType getNewArrayListInstance() {
        // always return a new instance because the type may be parameterized
        return new FullyQualifiedJavaType("java.util.ArrayList");
    }

    /**
     * 创建 Iterator 类型
     *
     * @return Iterator 类型
     */
    public static FullyQualifiedJavaType getNewIteratorInstance() {
        // always return a new instance because the type may be parameterized
        return new FullyQualifiedJavaType("java.util.Iterator");
    }

    /**
     * 获取 String 类型
     *
     * @return String 类型
     */
    public static FullyQualifiedJavaType getStringInstance() {
        if (STRING_INSTANCE == null) {
            STRING_INSTANCE = new FullyQualifiedJavaType("java.lang.String");
        }

        return STRING_INSTANCE;
    }

    /**
     * 获取 boolean 类型
     *
     * @return boolean 类型
     */
    public static FullyQualifiedJavaType getBooleanPrimitiveInstance() {
        if (BOOLEAN_PRIMITIVE_INSTANCE == null) {
            BOOLEAN_PRIMITIVE_INSTANCE = new FullyQualifiedJavaType("boolean");
        }

        return BOOLEAN_PRIMITIVE_INSTANCE;
    }

    /**
     * 获取 Object 类型
     *
     * @return Object 类型
     */
    public static FullyQualifiedJavaType getObjectInstance() {
        if (OBJECT_INSTANCE == null) {
            OBJECT_INSTANCE = new FullyQualifiedJavaType("java.lang.Object");
        }

        return OBJECT_INSTANCE;
    }

    /**
     * 获取 Date 类型
     *
     * @return Date 类型
     */
    public static FullyQualifiedJavaType getDateInstance() {
        if (DATE_INSTANCE == null) {
            DATE_INSTANCE = new FullyQualifiedJavaType("java.util.Date");
        }

        return DATE_INSTANCE;
    }

    @Override
    public int compareTo(FullyQualifiedJavaType other) {
        return getFullyQualifiedName().compareTo(other.getFullyQualifiedName());
    }

    /**
     * add new FullyQualifiedJavaType
     *
     * @param type FullyQualifiedJavaType
     */
    public void addTypeArgument(FullyQualifiedJavaType type) {
        typeArguments.add(type);
    }

    private void parse(String fullTypeSpecification) {
        String spec = fullTypeSpecification.trim();

        if (spec.startsWith("?")) {
            wildcardType = true;
            spec = spec.substring(1).trim();
            if (spec.startsWith("extends ")) {
                boundedWildcard = true;
                extendsBoundedWildcard = true;
                // "extends ".length()
                spec = spec.substring(8);
            } else if (spec.startsWith("super ")) {
                boundedWildcard = true;
                extendsBoundedWildcard = false;
                // "super ".length()
                spec = spec.substring(6);
            } else {
                boundedWildcard = false;
            }
            parse(spec);
        } else {
            int index = fullTypeSpecification.indexOf('<');
            if (index == -1) {
                simpleParse(fullTypeSpecification);
            } else {
                simpleParse(fullTypeSpecification.substring(0, index));
                int endIndex = fullTypeSpecification.lastIndexOf('>');
                if (endIndex == -1) {
                    throw new RuntimeException(getString("Invalid Type Specification: %.", fullTypeSpecification));
                }
                genericParse(fullTypeSpecification.substring(index, endIndex + 1));
            }

            // this is far from a perfect test for detecting arrays, but is close
            // enough for most cases.  It will not detect an improperly specified
            // array type like byte], but it will detect byte[] and byte[   ]
            // which are both valid
            isArray = fullTypeSpecification.endsWith("]");
        }
    }

    private void simpleParse(String typeSpecification) {
        baseQualifiedName = typeSpecification.trim();
        if (baseQualifiedName.contains(".")) {
            packageName = getPackage(baseQualifiedName);
            baseShortName = baseQualifiedName.substring(packageName.length() + 1);
            int index = baseShortName.lastIndexOf('.');
            if (index != -1) {
                baseShortName = baseShortName.substring(index + 1);
            }

            if (JAVA_LANG.equals(packageName)) {
                explicitlyImported = false;
            } else {
                explicitlyImported = true;
            }
        } else {
            baseShortName = baseQualifiedName;
            explicitlyImported = false;
            packageName = "";

            if ("byte".equals(baseQualifiedName)) {
                primitive = true;
                primitiveTypeWrapper = PrimitiveTypeWrapper.getByteInstance();
            } else if ("short".equals(baseQualifiedName)) {
                primitive = true;
                primitiveTypeWrapper = PrimitiveTypeWrapper.getShortInstance();
            } else if ("int".equals(baseQualifiedName)) {
                primitive = true;
                primitiveTypeWrapper = PrimitiveTypeWrapper.getIntegerInstance();
            } else if ("long".equals(baseQualifiedName)) {
                primitive = true;
                primitiveTypeWrapper = PrimitiveTypeWrapper.getLongInstance();
            } else if ("char".equals(baseQualifiedName)) {
                primitive = true;
                primitiveTypeWrapper = PrimitiveTypeWrapper.getCharacterInstance();
            } else if ("float".equals(baseQualifiedName)) {
                primitive = true;
                primitiveTypeWrapper = PrimitiveTypeWrapper.getFloatInstance();
            } else if ("double".equals(baseQualifiedName)) {
                primitive = true;
                primitiveTypeWrapper = PrimitiveTypeWrapper.getDoubleInstance();
            } else if ("boolean".equals(baseQualifiedName)) {
                primitive = true;
                primitiveTypeWrapper = PrimitiveTypeWrapper.getBooleanInstance();
            } else {
                primitive = false;
                primitiveTypeWrapper = null;
            }
        }
    }

    private void genericParse(String genericSpecification) {
        int lastIndex = genericSpecification.lastIndexOf('>');
        if (lastIndex == -1) {
            // shouldn't happen - should be caught already, but just in case...
            throw new RuntimeException(getString("Invalid Type Specification: %.", genericSpecification));
        }
        String argumentString = genericSpecification.substring(1, lastIndex);
        // need to find "," outside of a <> bounds
        StringTokenizer st = new StringTokenizer(argumentString, ",<>", true);
        int openCount = 0;
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if ("<".equals(token)) {
                sb.append(token);
                openCount++;
            } else if (">".equals(token)) {
                sb.append(token);
                openCount--;
            } else if (",".equals(token)) {
                if (openCount == 0) {
                    typeArguments.add(new FullyQualifiedJavaType(sb.toString()));
                    sb.setLength(0);
                } else {
                    sb.append(token);
                }
            } else {
                sb.append(token);
            }
        }

        if (openCount != 0) {
            throw new RuntimeException(getString("Invalid Type Specification: %.", genericSpecification));
        }

        String finalType = sb.toString();
        if (StringUtils.isNotEmpty(finalType)) {
            typeArguments.add(new FullyQualifiedJavaType(finalType));
        }
    }

    /**
     * Returns the package name of a fully qualified type.
     * <p>
     * This method calculates the package as the part of the fully qualified
     * name up to, but not including, the last element. Therefore, it does not
     * support fully qualified inner classes. Not totally fool proof, but
     * correct in most instances.
     *
     * @param baseQualifiedName the base qualified name
     * @return the package
     */
    private static String getPackage(String baseQualifiedName) {
        int index = baseQualifiedName.lastIndexOf('.');
        return baseQualifiedName.substring(0, index);
    }
}
