#### MySQL_FDW

**功能描述**

支持创建外部数据封装器mysql_fdw，连接MySQL数据库，并能在外部表上进行查询、插入、更新和删除操作。

**编译MySQL_FDW**

1、编译mysql_fdw需要安装MariaDB的开发库和头文件，推荐使用MariaDB的<a href='http://downloads.mariadb.org/mariadb/repositories'>官方源</a>进行安装。

2、配置好源后，使用yum install MariaDB-devel MariaDB-shared安装相关开发库。另外MariaDB-client是MariaDB的客户端工具，也可以根据需要安装，用于连接MariaDB进行测试。

3、安装好开发包后，就可以开始编译mysql_fdw了。编译时需要在执行configure时，加入–enable-mysql-fdw选项。后续按照正常的Vastbase安装方式安装即可。（Vastbase的安装参考章节“字符安装”）

4、编译完成后，编译产物为mysql_fdw.so，位于安装目录的lib/postgresql/ 下。mysql_fdw相关的sql文件和control文件，位于安装目录的share/postgresql/extension/下。

5、如果编译安装时，没有加入–enable-mysql-fdw选项，可以在Vastbase安装完成后，再次编译mysql_fdw，然后手动将编译产物mysql_fdw.so放到对应的安装目录lib/postgresql/，将mysql_fdw–1.0–1.1.sql、mysql_fdw–1.1.sql、mysql_fdw–1.0.sql、mysql_fdw.control放到对应的安装目录share/postgresql/extension/ ，然后重启数据库即可。

**使用MySQL_FDW**

- 使用mysql_fdw需要连接MariaDB或者MySQL Server，MariaDB或MySQL Server请自行安装。
- 加载mysql_fdw扩展。

```
CREATE EXTENSION mysql_fdw;
```

- 创建服务器对象。

```
CREATE SERVER
```

- 创建用户映射。

```
CREATE USER MAPPING
```

- 创建外表：外表的表结构需要与MySQL/MariaDB侧的表结构保持一致。注意MySQL/MariaDB侧的表的第一个字段必须具有唯一性约束（如PRIMARY KEY、UNIQUE等）。

```
CREATE FOREIGN TABLE
```

- 对外表做正常的操作，如INSERT、UPDATE、DELETE、SELECT、EXPLAIN、ANALYZE、COPY等。
- 删除外表。

```
DROP FOREIGN TABLE
```

- 删除用户映射。

```
DROP USER MAPPING
```

- 删除服务器对象：

```
DROP SERVER
```

- 删除扩展。

```
DROP EXTENSION mysql_fdw
```

**注意事项**

- 两个mysql外表间的SELECT JOIN不支持下推到MariaDB/MySQL Server执行，会被分成两条SQL语句传递到MariaDB/MySQL Server执行，然后在Vastbase处汇总处理结果。
- 不支持IMPORT FOREIGN SCHEMA语法。
- 不支持对外表进行CREATE TRIGGER操作。

**示例**

1、创建扩展。

```
create extension mysql_fdw;
```

2、创建FDW Server用于识别外部数据源。

```
create server mysql_fdw_server foreign data wrapper mysql_fdw options(HOST '172.16.103.108',PORT '20001');
```

3、新建用户并授权。

```
create user use_mysql password 'Bigdata@123';
grant usage on foreign server mysql_fdw_server to use_mysql;
```

4、创建USER MAPPING用于映射MySQL数据库用户。

```
create user mapping for use_mysql server mysql_fdw_server options(username 'root',password 'root');
```

5、在MySQL创建表。

```
create table test.emp_fdw(empno int,ename varchar(30));
insert into emp_fdw values(1,'foo');
insert into emp_fdw values(2,'bar');
```

6、在vastbase创建外部表，切换到用户use_mysql。

```
create foreign table emp_fdw_mysql(empno int,ename varchar(30)) server mysql_fdw_server options(DBNAME 'test',table_name 'emp_fdw');
\c - use_mysql
```

7、查询外部表数据。

```
select * from emp_fdw_mysql;
```

