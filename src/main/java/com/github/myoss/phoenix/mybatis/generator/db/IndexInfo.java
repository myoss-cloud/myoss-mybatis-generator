package com.github.myoss.phoenix.mybatis.generator.db;

import lombok.Data;

/**
 * 数据库表的索引信息
 *
 * @author Jerry.Chen
 * @since 2018年6月13日 下午3:26:17
 */
@Data
public class IndexInfo {
    private Boolean nonUnique;
    private String  indexName;
    private int     type;
    private int     ordinalPosition;
    private String  columnName;
    private String  ascOrDesc;
}
