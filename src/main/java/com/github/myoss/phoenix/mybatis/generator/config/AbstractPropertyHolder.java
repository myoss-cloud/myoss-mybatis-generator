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

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.Getter;

/**
 * 拥有 Property 属性字段的基类
 *
 * @author Jerry.Chen
 * @since 2018年5月5日 下午3:43:07
 */
@Data
public abstract class AbstractPropertyHolder {
    /**
     * 自定义属性
     */
    @Getter
    private Map<String, Object> properties = new HashMap<>();

    /**
     * 增加自定义属性
     *
     * @param name 属性名
     * @param value 属性值
     */
    public void addProperty(String name, Object value) {
        properties.put(name, value);
    }

    /**
     * 批量增加自定义属性
     *
     * @param properties 自定义属性集合
     */
    public void setProperties(Map<String, Object> properties) {
        this.properties.putAll(properties);
    }
}
