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
