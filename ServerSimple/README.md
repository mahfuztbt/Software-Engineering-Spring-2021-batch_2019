# java-server

## 环境要求

- java 11
- mysql 8
- redis 5.0.12

0. 通过docker创建 mysql和redis
可考虑使用[docker-compose.yml](resources/javaAppContainer/docker-compose.yml)启动mysql和redis

## 数据库初始化(MYSQL)

1. 创建库
```sql
CREATE USER 'appBase'@'%' IDENTIFIED BY 'appBase';
CREATE DATABASE appBase DEFAULT CHARACTER SET utf8mb4;
GRANT ALL PRIVILEGES ON appBase.* TO appBase@'%';
CREATE DATABASE appBase_test DEFAULT CHARACTER SET utf8mb4;
GRANT ALL PRIVILEGES ON appBase_test.* TO appBase@'%';
FLUSH PRIVILEGES;
```
2. 初始化基础表
   2.1 启动 liquibase, liquibase,enable:true
   2.2 通过jpa, 启用 application-base.yml ,generate-ddl:true, ddl-auto:update
   
3. 初始化默认用户
```sql
INSERT INTO t_sys_organization (created_date, last_modified_date, parent_id, name, code, sort, enabled) VALUES (DEFAULT, DEFAULT, DEFAULT, '云南分部', null, null, DEFAULT);
INSERT INTO t_sys_user (created_date, last_modified_date, email, enabled, organization_id, organization_name, sort, password, username, name, title, tags, credentials_non_expired, account_non_locked, account_non_expired, credentials_expired_date, account_expired_date) VALUES (DEFAULT, DEFAULT, null, DEFAULT, 1, '云南分部', null, '{noop}123456', 'admin', '系统管理员', '后台', '管理员', DEFAULT, DEFAULT, DEFAULT, null, null);
```
## 项目结构
1. common 基础架构和常用功能
   1.1 data 抽象dao及符合查询构造器
   1.2 controller,service 类型和控制器和服务基类(目前部分参数需要构造函数注入)
2. sys.controller 登录控制器
   2.1 在 AuthenticationSuccessHandlerImpl 的 sampleLogin 中替换登录以后的返回结构. 
3. education 一个实例包,没有实际功能. 可以删除 
   3.1 目前只有该项目的几个基类支持 jpa 生成. 其他实体只做了 liquibase 配置.

## 开发说明
1. 编码辅助, 大量使用了lombok来辅助get,set 等生成. 需要插件支持.

## 打包
1. 打包 
   1.1 格式矫正, 运行 mvn spring-javaformat:apply 格式化代码. 
   1.2 或者注释 pom.xml, plugins->io.spring.javaformat
   1.3 mvn package

## 代码格式化规范

本项目使用的代码规范根据Spring Framework代码格式约定改造而成。

格式化配置文件格式为eclipse格式，idea导入后使用格式化时可能部分样式和配置文件不一致。故而ide不要求必须导入配置文件

项目中使用maven命令直接格式化代码,

```
mvn spring-javaformat:apply
```

所以只要ide的格式化配置不会给自己开发造成太多障碍即可

原考虑将格式化与git钩子集成，但格式化后需要重新运行git add把格式化后的代码加入暂存区，但这可能导致破坏本不想提交的代码。所以暂时使用手工方式运行格式化

## 配置文件

1.  编译时配置： --spring.profiles.active=local
2.  运行时选择配置： --spring.config.location=/config/application.yml

## 生成离线文档

运行`io.naccoll.boilerplate.DocumentationTest`的测试

```bash
mvn swagger2markup:convertSwagger2markup 
mvn asciidoctor:process-asciidoc 
mvn package
```

最后生成文件位置: 1
`target/generated-docs/index.html`