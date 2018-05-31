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

package com.github.myoss.phoenix.mybatis.generator.template;

import java.util.Map;

import com.github.myoss.phoenix.mybatis.generator.config.Configuration;

/**
 * 模版引擎接口
 *
 * @author Jerry.Chen
 * @since 2018年5月4日 下午5:58:19
 */
public interface TemplateEngine {

    /**
     * 初始化模版引擎配置
     *
     * @param configuration 配置信息
     */
    void init(Configuration configuration);

    /**
     * 使用模版生成文件
     *
     * @param templatePath 模版路径
     * @param outputPath 生成文件保存目录
     * @param data 模版配置数据
     */
    void writer(String templatePath, String outputPath, Map<String, Object> data);
}
