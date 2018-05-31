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

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 生成 Mybatis 文件的时候，忽略哪些字段名，比较字段名是否相等
 *
 * @author Jerry.Chen
 * @since 2018年5月5日 上午11:10:09
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@AllArgsConstructor
public class IgnoredColumn extends AbstractPropertyHolder {
    /**
     * The column name.
     */
    private String columnName;

    public boolean matches(String columnName) {
        return this.columnName.equals(columnName);
    }
}
