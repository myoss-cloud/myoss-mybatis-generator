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

import java.nio.file.Path;
import java.util.Map;

import com.github.myoss.phoenix.mybatis.generator.db.Table;
import com.github.myoss.phoenix.mybatis.generator.template.TemplateEngine;

/**
 * 自定义文件
 *
 * @author Jerry.Chen
 * @since 2018年6月13日 下午1:58:48
 */
public interface ExtendedFile {
    /**
     * 生成自定义文件
     *
     * @param templateEngine 生成文件的模版引擎
     * @param rootOutputPath 生成文件保存的根目录
     * @param table 数据库表的信息
     * @param data 模版配置数据
     */
    void generateFile(TemplateEngine templateEngine, Path rootOutputPath, Table table, Map<String, Object> data);
}
