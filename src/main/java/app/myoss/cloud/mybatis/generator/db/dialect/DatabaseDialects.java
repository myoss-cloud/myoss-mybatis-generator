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

package app.myoss.cloud.mybatis.generator.db.dialect;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import app.myoss.cloud.mybatis.generator.db.dialect.impl.H2Dialect;
import app.myoss.cloud.mybatis.generator.db.dialect.impl.MySqlDialect;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据库方言
 *
 * @author Jerry.Chen
 * @since 2018年5月15日 下午12:17:28
 */
@Getter
@AllArgsConstructor
public enum DatabaseDialects {
    /**
     * DB2
     */
    DB2("DB2", "VALUES IDENTITY_VAL_LOCAL()"),
    /**
     * MySQL
     */
    MYSQL("MySQL", "SELECT LAST_INSERT_ID()"),
    /**
     * SqlServer
     */
    SQLSERVER("SqlServer", "SELECT SCOPE_IDENTITY()"),
    /**
     * Cloudscape
     */
    CLOUDSCAPE("Cloudscape", "VALUES IDENTITY_VAL_LOCAL()"),
    /**
     * Derby
     */
    DERBY("Derby", "VALUES IDENTITY_VAL_LOCAL()"),
    /**
     * HSQLDB
     */
    HSQLDB("HSQLDB", "CALL IDENTITY()"),
    /**
     * SYBASE
     */
    SYBASE("SYBASE", "SELECT @@IDENTITY"),
    /**
     * DB2_MF
     */
    DB2_MF("DB2_MF", "SELECT IDENTITY_VAL_LOCAL() FROM SYSIBM.SYSDUMMY1"),
    /**
     * Informix
     */
    INFORMIX("Informix", "select dbinfo('sqlca.sqlerrd1') from systables where tabid=1"),
    /**
     * H2
     */
    H2("H2", "CALL IDENTITY()");

    /**
     * 数据库名字
     */
    String                                                databaseName;
    /**
     * 数据库检索标识或自动编号值的 sql statement
     */
    String                                                identityRetrievalStatement;
    /**
     * 数据库方言
     */
    private static Map<DatabaseDialects, DatabaseDialect> DATABASE_DIALECT_MAP = new ConcurrentHashMap<>();

    static {
        DATABASE_DIALECT_MAP.put(MYSQL, new MySqlDialect());
        DATABASE_DIALECT_MAP.put(H2, new H2Dialect());
    }

    /**
     * 注册数据库方言实例
     *
     * @param install 数据库类型
     * @param databaseDialect 数据库方言实现对象
     */
    public static void registerDatabaseDialect(DatabaseDialects install, DatabaseDialect databaseDialect) {
        DATABASE_DIALECT_MAP.put(install, databaseDialect);
    }

    /**
     * Gets the database dialect.
     *
     * @param database the database
     * @return the database dialect for the selected database. May return null
     *         if there is no known dialect for the selected db
     */
    public static DatabaseDialects getDatabaseDialect(String database) {
        for (DatabaseDialects item : DatabaseDialects.values()) {
            if (item.getDatabaseName().equalsIgnoreCase(database)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 获取数据库方言
     *
     * @return 数据库方言
     */
    public static Map<DatabaseDialects, DatabaseDialect> getDatabaseDialectMap() {
        return DATABASE_DIALECT_MAP;
    }
}
