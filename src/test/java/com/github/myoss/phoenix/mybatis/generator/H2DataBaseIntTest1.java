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

package com.github.myoss.phoenix.mybatis.generator;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.myoss.phoenix.mybatis.generator.config.Configuration;
import com.github.myoss.phoenix.mybatis.generator.config.PropertyRegistry;
import com.github.myoss.phoenix.mybatis.generator.config.TableConfiguration;
import com.github.myoss.phoenix.mybatis.table.annotation.GenerationType;
import com.github.myoss.phoenix.mybatis.table.annotation.SequenceGenerator.Order;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 使用h2内存数据库进行集成单元测试
 *
 * @author Jerry.Chen 2018年5月10日 下午11:19:19
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { DataSourceAutoConfiguration.class, JdbcTemplateAutoConfiguration.class })
public class H2DataBaseIntTest1 {
    @Autowired
    private HikariDataSource dataSource;

    @Test
    public void generateTest1() {
        String rootOutputPath = Paths.get(this.getClass().getResource("/").getPath()).getParent()
                .resolve("generated-sources" + File.separator + "generateTest1").toString();

        List<TableConfiguration> tableConfigurations = new ArrayList<>();
        Configuration configuration = new Configuration();
        configuration.setDataSource(dataSource);
        configuration.setTableConfigurations(tableConfigurations);
        configuration.setAuthor("jerry");
        configuration.setCopyright("Copyright 2018-${todayYear} https://github.com/myoss\n" + "\n"
                + "Licensed under the Apache License, Version 2.0 (the \"License\");\n"
                + "you may not use this file except in compliance with the License.\n"
                + "You may obtain a copy of the License at\n" + "\n"
                + "    http://www.apache.org/licenses/LICENSE-2.0\n" + "\n"
                + "Unless required by applicable law or agreed to in writing, software\n"
                + "distributed under the License is distributed on an \"AS IS\" BASIS,\n"
                + "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n"
                + "See the License for the specific language governing permissions and\n"
                + "limitations under the License.\n");
        configuration.setRemoveEntityClassPrefix("TSys");
        configuration.setRootPackageName("com.github.myoss.phoenix.mybatis.test.integration.h2");
        configuration.setRootOutputPath(rootOutputPath);
        configuration.setGenerateDate("2018年5月11日 上午10:41:47");
        configuration.addProperty(PropertyRegistry.ALL_METHOD_ENABLE_IN_WEB_FILE, true);

        TableConfiguration tableConfiguration = new TableConfiguration();
        tableConfiguration.setTableName("t_sys_user");
        tableConfigurations.add(tableConfiguration);

        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration);
        myBatisGenerator.generate();
    }

    @Test
    public void generateTest2() {
        String rootOutputPath = Paths.get(this.getClass().getResource("/").getPath()).getParent()
                .resolve("generated-sources" + File.separator + "generateTest2").toString();

        List<TableConfiguration> tableConfigurations = new ArrayList<>();
        Configuration configuration = new Configuration();
        configuration.setDataSource(dataSource);
        configuration.setTableConfigurations(tableConfigurations);
        configuration.setAuthor("jerry");
        configuration.setCopyright("Copyright 2018-${todayYear} https://github.com/myoss\n" + "\n"
                + "Licensed under the Apache License, Version 2.0 (the \"License\");\n"
                + "you may not use this file except in compliance with the License.\n"
                + "You may obtain a copy of the License at\n" + "\n"
                + "    http://www.apache.org/licenses/LICENSE-2.0\n" + "\n"
                + "Unless required by applicable law or agreed to in writing, software\n"
                + "distributed under the License is distributed on an \"AS IS\" BASIS,\n"
                + "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n"
                + "See the License for the specific language governing permissions and\n"
                + "limitations under the License.\n");
        configuration.setEntitySuperClass("com.github.myoss.phoenix.mybatis.repository.entity.AuditIdEntity");
        configuration.setRemoveEntityClassPrefix("TSys");
        configuration.setRootPackageName("com.github.myoss.phoenix.mybatis.test.integration.h2");
        configuration.setRootOutputPath(rootOutputPath);
        configuration.setGenerateDate("2018年5月14日 下午3:39:43");
        configuration.addProperty(PropertyRegistry.ALL_METHOD_ENABLE_IN_WEB_FILE, false);
        configuration.addProperty(PropertyRegistry.SELECT_KEY_SELECT_SQL,
                "select ifnull(max(`id`) ,1) from t_sys_user_history");
        configuration.addProperty(PropertyRegistry.SELECT_KEY_RESULT_TYPE, "Long");
        configuration.addProperty(PropertyRegistry.SELECT_KEY_ORDER,
                Order.class.getSimpleName() + "." + Order.BEFORE.name());
        configuration.addProperty(PropertyRegistry.USE_PRIMARY_KEY_JAVA_TYPE_FOR_CLASS_GENERIC_TYPE_IN_ENTITY_FILE,
                true);
        configuration.setSequenceStrategy(GenerationType.SELECT_KEY);

        TableConfiguration tableConfiguration = new TableConfiguration();
        tableConfiguration.setTableName("t_sys_user_history");
        tableConfigurations.add(tableConfiguration);

        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration);
        myBatisGenerator.generate();
    }

    @Test
    public void generateTest3() {
        String rootOutputPath = Paths.get(this.getClass().getResource("/").getPath()).getParent()
                .resolve("generated-sources" + File.separator + "generateTest3").toString();

        List<TableConfiguration> tableConfigurations = new ArrayList<>();
        Configuration configuration = new Configuration();
        configuration.setDataSource(dataSource);
        configuration.setTableConfigurations(tableConfigurations);
        configuration.setAuthor("jerry");
        configuration.setCopyright("Copyright 2018-${todayYear} https://github.com/myoss\n" + "\n"
                + "Licensed under the Apache License, Version 2.0 (the \"License\");\n"
                + "you may not use this file except in compliance with the License.\n"
                + "You may obtain a copy of the License at\n" + "\n"
                + "    http://www.apache.org/licenses/LICENSE-2.0\n" + "\n"
                + "Unless required by applicable law or agreed to in writing, software\n"
                + "distributed under the License is distributed on an \"AS IS\" BASIS,\n"
                + "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n"
                + "See the License for the specific language governing permissions and\n"
                + "limitations under the License.\n");
        configuration.setEntitySuperClass("com.github.myoss.phoenix.mybatis.repository.entity.AuditIdEntity");
        configuration.setRemoveEntityClassPrefix("TSys");
        configuration.setRootPackageName("com.github.myoss.phoenix.mybatis.test.integration.h2");
        configuration.setRootOutputPath(rootOutputPath);
        configuration.setGenerateDate("2018年5月14日 下午3:39:43");
        configuration.addProperty(PropertyRegistry.ALL_METHOD_ENABLE_IN_WEB_FILE, true);
        configuration.addProperty(PropertyRegistry.SEQUENCE_KEY_SEQUENCE_CLASS, "SequenceCustomizer");
        configuration.addProperty(PropertyRegistry.SEQUENCE_KEY_SEQUENCE_NAME, "sequenceUserLog");
        configuration.addProperty(PropertyRegistry.SEQUENCE_KEY_SEQUENCE_BEAN_NAME, "seqUserLog");
        configuration.addProperty(PropertyRegistry.SEQUENCE_KEY_ORDER,
                Order.class.getSimpleName() + "." + Order.BEFORE.name());
        configuration.addProperty(PropertyRegistry.USE_PRIMARY_KEY_JAVA_TYPE_FOR_CLASS_GENERIC_TYPE_IN_ENTITY_FILE,
                true);
        configuration.setSequenceStrategy(GenerationType.SEQUENCE_KEY);
        configuration
                .addEntityImportPackage("com.github.myoss.phoenix.mybatis.test.integration.h2.H2DataBaseIntTest.SequenceCustomizer");
        //        configuration.setSequenceTemplatePath("*/templates/freemarker/copyright.ftl");

        TableConfiguration tableConfiguration = new TableConfiguration();
        tableConfiguration.setTableName("t_sys_user_log");
        tableConfigurations.add(tableConfiguration);

        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration);
        myBatisGenerator.generate();
    }
}
