#### Oracle_FDW

**功能描述**

支持创建外部数据封装器oracle_fdw，连接Oracle数据库，并能在外部表上进行查询、插入、更新和删除操作。

**编译Oracle_FDW**

编译oracle_fdw需要安装Oracle的开发库和头文件。

1、选择合适的运行环境和版本，下载Basic Package和SDK Package并安装。另外SQLPlus Package是Oracle的客户端工具，也可以根据需要安装，用于连接Oracle Server进行测试。

2、安装好开发包后，就可以开始编译oracle_fdw了。编译时需要在执行configure时，加入 –enable-oracle-fdw选项。后续按照正常的Vstbase安装方式安装即可。（Vastbase的编译参考章节"字符安装"）

3、编译完成后，编译产物为oracle_fdw.so，位于安装目录的 lib/postgresql/下。oracle_fdw相关的sql文件和control文件，位于安装目录的 share/postgresql/extension/下。

4、如果编译安装时，没有加入 –enable-oracle-fdw选项，可以在Vastbase安装完成后，再次编译oracle_fdw，然后手动将编译产物 oracle_fdw.so放到对应的安装目录 lib/postgresql/ ，将 oracle_fdw–1.0–1.1.sql、oracle_fdw–1.1.sql、oracle_fdw.control放到对应的安装目录 share/postgresql/extension/即可。

**使用Oracle_FDW**

- 使用oracle_fdw需要连接Oracle，Oracle server请自行安装。
- 加载oracle_fdw扩展：

```
CREATE EXTENSION oracle_fdw
```

- 创建服务器对象：

```
CREATE SERVER
```

- 创建用户映射：

```
CREATE USER MAPPING
```

- 创建外表： 外表的表结构需要与Oracle数据库中的表结构保持一致。注意Oracle server侧的表的第一个字段必须具有唯一性约束（如PRIMARY KEY、UNIQUE等）。

```
CREATE FOREIGN TABLE
```

- 对外表做正常的操作，如INSERT、UPDATE、DELETE、SELECT、EXPLAIN、ANALYZE、COPY等。
- 删除外表：

```
DROP FOREIGN TABLE
```

- 删除用户映射：

```
DROP USER MAPPING
```

- 删除服务器对象：

```
DROP SERVER
```

- 删除扩展：

```
DROP EXTENSION oracle_fdw
```

**注意事项**

- 两个Oracle外表间的SELECT JOIN不支持下推到Oracle server执行，会被分成两条SQL语句传递到Oracle执行，然后在Vastbase处汇总处理结果。
- 不支持IMPORT FOREIGN SCHEMA语法。
- 不支持对外表进行CREATE TRIGGER操作。

**示例**

1、创建扩展。

```
create extension oracle_fdw;
```

2、创建FDW Server用于识别外部数据源。

```
create server ora_fdw_server foreign data wrapper oracle_fdw
options(dbserver '172.16.103.104:1521/orcl');
```

3、新建用户并授权。

```
create user use_ora password 'Bigdata@123';
grant usage on foreign server ora_fdw_server to use_ora;
```

4、创建USER MAPPING用于映射Oracle数据库用户。

```
create user mapping for use_ora server ora_fdw_server options(user 'system',password 'root');
```

5、在oracle创建表。

```
create table system.emp_fdw(empno int,ename varchar(30));
insert into emp_fdw values(1,'foo');
insert into emp_fdw values(2,'bar');
```

6、在vastbase创建外部表，切换到用户use_ora。

```
create foreign table emp_fdw_ora(empno int,ename varchar(30))
server ora_fdw_server
options(schema 'SYSTEM',table 'EMP_FDW');
\c - use_ora
```

7、插入外部表数据

```
insert into emp_fdw_ora values(3,'bar3');
```

8、查询外部表数据

```
select * from emp_fdw_ora;
```

