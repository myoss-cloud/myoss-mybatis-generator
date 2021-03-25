# 生成的文件目录介绍

将生成的 `xxx.zip` 包解压缩之后，第一层目录结构介绍：

```
➜  2021-01-01_200059_735 tree -L 1
.
├── java
├── java-swagger
├── java-v1
└── mybatis
```

1. java: 这里是不包含 `Swagger` 注解的 CRUD 代码。
2. java-swagger: 这里是包含 `Swagger` 注解的 CRUD 代码。`Controller` 接收 `DTO` 对象，将 `DTO` 对象转换为 `Entity`，请求 `Service`，将 `Service` 返回的 `Entity` 再转换为 `DTO`，返回给客户端。
3. java-v1: 这里的 `Controller/Service` 代码可以用来覆盖 `java` 目录中的代码，`Service` 的返回值由 `Result` 进行封装，主要是这一点差别。
4. mybatis: 这里是 `MyBatis` 的 `XML` 代码，自定义的 `SQL` 语句放在 XML 里统一维护，不用去 `JAVA` 代码中扣 `SQL` 语句的逻辑。
