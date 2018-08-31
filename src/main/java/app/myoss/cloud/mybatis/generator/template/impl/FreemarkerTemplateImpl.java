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

package app.myoss.cloud.mybatis.generator.template.impl;

import static freemarker.template.Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import app.myoss.cloud.core.exception.BizRuntimeException;
import app.myoss.cloud.mybatis.generator.config.Configuration;
import app.myoss.cloud.mybatis.generator.template.TemplateEngine;
import freemarker.template.Template;
import lombok.Getter;

/**
 * Freemarker模版引擎实现类
 *
 * @author Jerry.Chen
 * @since 2018年5月7日 下午11:21:16
 */
public class FreemarkerTemplateImpl implements TemplateEngine {
    @Getter
    private freemarker.template.Configuration configuration;

    @Override
    public void init(Configuration config) {
        this.configuration = new freemarker.template.Configuration(DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        this.configuration.setDefaultEncoding("UTF-8");
        this.configuration.setClassForTemplateLoading(FreemarkerTemplateImpl.class, "/");
    }

    @Override
    public void writer(String templatePath, String outputPath, Map<String, Object> data) {
        try {
            Template template = configuration.getTemplate(templatePath);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(outputPath));
            template.process(data, new OutputStreamWriter(fileOutputStream, "UTF-8"));
            fileOutputStream.close();
        } catch (Exception ex) {
            throw new BizRuntimeException(
                    "process [" + templatePath + "] writer to [" + outputPath + "] failed, \ndata: " + data, ex);
        }
    }

    @Override
    public void writer(String templatePath, Writer output, Map<String, Object> data) {
        try {
            Template template = configuration.getTemplate(templatePath);
            template.process(data, output);
        } catch (Exception ex) {
            throw new BizRuntimeException(
                    "process [" + templatePath + "] writer to [" + output.getClass() + "] failed, \ndata: " + data, ex);
        }
    }
}
