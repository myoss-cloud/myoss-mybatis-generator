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

import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 生成 Mybatis 文件的时候，忽略哪些字段名，使用正则表达式比较
 *
 * @author Jerry.Chen 2018年5月5日 上午11:04:39
 */
@EqualsAndHashCode(callSuper = true)
@Getter
public class IgnoredColumnPattern extends AbstractPropertyHolder {
    private String  patternRegex;
    private Pattern pattern;

    public IgnoredColumnPattern(String patternRegex) {
        this.patternRegex = patternRegex;
        pattern = Pattern.compile(patternRegex);
    }

    public boolean matches(String columnName) {
        return pattern.matcher(columnName).matches();
    }
}
