# Software-Engineering-Spring-2021-batch_2019
Course Selection System




# Java-Server

## Environmental Rquirements

- java 11
- mysql 8
- redis 5.0.12

0. Create mysql and redis through docker
Consider using [docker-compose.yml](resources/javaAppContainer/docker-compose.yml)Start mysql and redis

## Database initialization(MYSQL)

1. Create library
```sql
CREATE USER 'appBase'@'%' IDENTIFIED BY 'appBase';
CREATE DATABASE appBase DEFAULT CHARACTER SET utf8mb4;
GRANT ALL PRIVILEGES ON appBase.* TO appBase@'%';
CREATE DATABASE appBase_test DEFAULT CHARACTER SET utf8mb4;
GRANT ALL PRIVILEGES ON appBase_test.* TO appBase@'%';
FLUSH PRIVILEGES;
```
2. Initialize the base table
   2.1 start up liquibase, liquibase,enable:true
   2.2 using jpa, enable application-base.yml ,generate-ddl:true, ddl-auto:update
   
3. Initialize the default user
```sql
INSERT INTO t_sys_organization (created_date, last_modified_date, parent_id, name, code, sort, enabled) VALUES (DEFAULT, DEFAULT, DEFAULT, '云南分部', null, null, DEFAULT);
INSERT INTO t_sys_user (created_date, last_modified_date, email, enabled, organization_id, organization_name, sort, password, username, name, title, tags, credentials_non_expired, account_non_locked, account_non_expired, credentials_expired_date, account_expired_date) VALUES (DEFAULT, DEFAULT, null, DEFAULT, 1, '云南分部', null, '{noop}123456', 'admin', '系统管理员', '后台', '管理员', DEFAULT, DEFAULT, DEFAULT, null, null);
```
## Project structure
1. common Infrastructure and common Functions
   1.1 data - Abstract dao and conforming query builder
   1.2 controller,service -Type and controller and service base class (currently some parameters require constructor injection
2. sys.controller -Log in to the controller
   2.1 in AuthenticationSuccessHandlerImpl's  sampleLogin Replace the return structure after login in.
3. education -An example package, no actual function. Can be deleted 
   3.1 Currently only a few base classes of the project support jpa generation. Other entities only do liquibase configuration.

## Development Instructions
1. Coding assistance, lombok is used extensively to assist the generation of get, set, etc. Plug-in support is required.

## Pack
1. pack 
   1.1 Format correction, run mvn spring-javaformat:apply to format the code. 
   1.2 Or comment pom.xml, plugins->io.spring.javaformat
   1.3 mvn package

## Code formatting specifications

The code specifications used in this project are modified according to the Spring Framework code format conventions.

The formatting configuration file format is eclipse format. After the idea is imported, some styles may be inconsistent with the configuration file when formatting is used. Therefore, IDE does not require that the configuration file must be imported

Use the maven command in the project to directly format the code,

```
mvn spring-javaformat:apply
```

So as long as the formatting configuration of the ide does not cause too many obstacles to your own development, you can

Originally considered to integrate formatting with git hooks, but after formatting, you need to re-run git add to add the formatted code to the temporary storage area, but this may cause damage to the code that you don't want to submit. So temporarily run the formatting manually

## Configuration file

1.  Compile-time configuration： --spring.profiles.active=local
2.  Select configuration at runtime： --spring.config.location=/config/application.yml

## Generate offline documents

run`io.naccoll.boilerplate.DocumentationTest`Test

```bash
mvn swagger2markup:convertSwagger2markup 
mvn asciidoctor:process-asciidoc 
mvn package
```

Last generated file location: 1
`target/generated-docs/index.html`
