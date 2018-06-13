package com.github.myoss.phoenix.mybatis.generator.config;

import java.nio.file.Path;
import java.util.Map;

import com.github.myoss.phoenix.mybatis.generator.db.Table;
import com.github.myoss.phoenix.mybatis.generator.template.TemplateEngine;

/**
 * 自定义文件
 *
 * @author chenyao
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
