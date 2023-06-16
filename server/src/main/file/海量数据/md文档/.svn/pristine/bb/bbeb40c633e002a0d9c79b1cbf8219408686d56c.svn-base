# POSTGRES_FDW

开发者指南-外部数据封装器

## **功能描述**

postgres_fdw功能提供了外部数据封装器postgres_fdw插件，它可以被用来访问存储在外部Vastbase服务器中的数据。

用户可以通过指定参数[remote_kind](#kind)的值来控制远端连接的数据库为Vastbase或是PostgreSQL 11。

## **语法格式**

1、加载postgres_fdw扩展。

```sql
CREATE EXTENSION [ IF NOT EXISTS ] extension_name
  [ WITH ] [ SCHEMA schema_name ]
       [ VERSION version ]
       [ FROM old_version ]
```

2、创建一个新的外部服务器。

```sql
CREATE SERVER server_name
FOREIGN DATA WRAPPER fdw_name
  	OPTIONS ( { option_name ' value ' } [, ...] ) ;
```

3、创建一个用户到一个外部服务器的新映射。

```sql
CREATE USER MAPPING FOR { user_name | USER | CURRENT_USER | PUBLIC }
  SERVER server_name
[ OPTIONS ( option 'value' [ , ... ] ) ]
```

4、创建外表。

```sql
CREATE FOREIGN TABLE [ IF NOT EXISTS ] table_name ( [
column_name type_name [ OPTIONS ( option 'value' [, ... ] ) ] [ COLLATE collation ] [ column_constraint [ ... ] ]
 [, ... ]
] )
 SERVER server_name
[ OPTIONS ( option 'value' [, ... ] ) ]
```

这里column_constraint 可以是:

```sql
[ CONSTRAINT constraint_name ]
{ NOT NULL |
NULL |
DEFAULT default_expr }
```

## **参数说明**

- **server_name**

  server的名称。

  取值范围：长度必须小于等于63。

- **fdw_name**

  指定外部数据封装器的名称。

  取值范围：oracle_fdw，mysql_fdw，postgres_fdw，mot_fdw。

- **CREATE SERVER中OPTIONS参数包括：**
  
  - host（必选）：要连接的主机名，本功能填写远端服务器的IP地址。
  - port（必选）：主机服务器的端口号，本功能填写远端服务器上集群的端口号。
  - dbname（必选）：数据库名，本功能填写远端服务器上的数据库名。
  - <a name=kind></a>remote_kind（非必选）：远程连接类型，可选取值为vastbase（默认）或postgres。若用户指定其为postgres可以连接到远端PostgreSQL 11数据库。如果不指定此参数，默认按照远程连接到Vastbase数据库的逻辑进行处理。
  - hostaddr(非必选)：与之链接的主机的IP地址，是标准的IPv4地址格式，比如，172.28.40.9。如果机器支持IPv6，那么也可以使用IPv6的地址。如果声明了一个非空的字符串，那么使用TCP/IP通讯机制。
  - connect_timeout(非必选)：链接的最大等待时间，以秒计（用十进制整数字符串书写），0或者不声明表示无穷。不建议把链接超时的值设置得小于2秒。
  - options(非必选)：添加命令行选项以在运行时发送到服务器。
  - keepalives(非必选)：控制客户端侧的TCP保持激活是否使用。缺省值是1，意思为打开，但是如果不想要保持激活，你可以更改为0，意思为关闭。通过Unix域套接字做的链接忽略这个参数。
  - keepalives_idle(非必选)：在TCP应该发送一个保持激活的信息给服务器之后，控制不活动的秒数。0值表示使用系统缺省。通过Unix域套接字做的链接或者如果禁用了保持激活则忽略这个参数。
  - keepalives_interval(非必选)：在TCP保持激活信息没有被应该传播的服务器承认之后，控制秒数。0值表示使用系统缺省。通过Unix域套接字做的链接或者如果禁用了保持激活则忽略这个参数。
  - keepalives_count(非必选)：添加命令行选项以在运行时发送到服务器。例如，设置为-c comm_debug_mode=off设置guc参数comm_debug_mode参数的会话的值为off。
  - use_remote_estimate(非必选)：控制postgres_fdw是否发出EXPLAIN命令以获取运行消耗估算。默认值为false。
  - fdw_startup_cost(非必选)：执行一个外表扫描时的启动耗时估算。这个值通常包含建立连接、远端对请求的分析和生成计划的耗时。默认值为100。
  - fdw_typle_cost(非必选)：在远端服务器上对每一个元组进行扫描时的额外消耗。这个值通常表示数据在server间传输的额外消耗。默认值为0.01。
  
- **user_name**

	要映射到外部服务器的一个现有用户的名称。 CURRENT_USER和USER匹配当前用户的名称。 当PUBLIC被指定时，一个所谓的公共映射会被创建，当没有特定用户的映射可用时将会使用它。

- **server_name**

	将为其创建用户映射的现有服务器的名称。

- **CREATE USER MAPPING中OPTIONS参数包括：**
  
  - user(必选)：远端服务器上Vastbase的用户名，属于普通用户。
  - password（必选）：远端服务器上Vastbase用户对应的密码
  
- **table_name**

	外表的表名。取值范围：字符串，要符合标识符的命名规范。

- **column_name**

	外表中的字段名。取值范围：字符串，要符合标识符的命名规范。

- **type_name**

	字段的数据类型。

- **SERVER server_name**

	外表的server名称。

- **OPTIONS 参数包括**
  
  - schema_name（必选）：远端server的schema名称。如果不指定的话，将使用外表自身的schema名称作为远端的schema名称。
  - table_name（必选）：远端server的表名。如果不指定的话，将使用外表自身的表名作为远端的表名。
  - column_name（非必选）：远端server的表的列名。如果不指定的话，将使用外表自身的列名作为远端的的表的列名。

## **注意事项**

- 两个postgres_fdw外表间的SELECT JOIN不支持下推到远端Vastbase执行，会被分成两条SQL语句传递到远端Vastbase执行，然后在本地汇总处理结果。
- 不支持IMPORT FOREIGN SCHEMA语法。
- 不支持对外表进行CREATE TRIGGER操作。
- 若需要Vastbase访问远端PostgreSQL  11的分区表，需要设置GUC参数sql_beta_feature的值为partition_fdw_on。关于此参数的详细说明及配置方法可以参考[sql_beta_feature](其他优化器选项.md/#section29331229203010)。

## **示例**

**示例1：**使用postgres_fdw连接到远程vastbase数据库，并访问外表。

**前置条件：**

在本地服务器和远端服务器创建数据库用户之前，建议关闭远端数据库和本地数据库的强制修改密码功能。（参数默认启用，修改后重启生效。）

```sql
alter system set password_force_alter=off;
```

1、在本地服务器创建用户。

```sql
create user hzy with sysadmin password '123456Aa';
grant all on database vastbase to hzy;
```

2、远端服务器修改配置文件pg_hba.conf的配置参数，新增下列参数。

```shell
host  all   all       0.0.0.0/0         sha256
```

3、远端服务器修改配置文件postgresql.conf中的listen_addresses 参数，值为本地服务器的IP地址。

4、远端服务器中创建用户。

```sql
create user hzy with sysadmin password '123456Aa';
grant all on database vastbase to hzy;
```

5、远端服务器使用hzy用户创建测试表。

```sql
create table student( student_no int4 primary key, student_name varchar(30), age int2 ); 
insert into student values(1,'xiaoming',12); 
```

6、在本地服务器中，使用新建的用户登录系统。

```
\c - hzy
```

> <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
>
> 以下操作均在本地服务器上执行。

7、加载postgres_fdw扩展。

```sql
create extension postgres_fdw;
```

8、创建外部服务器。

```sql
create server server_to_200 foreign data wrapper postgres_fdw options (host '172.16.105.54',port '5432',dbname 'vastbase');
```

9、创建一个用户到外部服务器的映射。

```sql
create user MAPPING FOR hzy SERVER server_to_200 OPTIONS (user 'hzy',password '123456Aa');
```

10、创建外表。

```sql
create foreign table local_foreign_table_student( student_no int4 , student_name varchar(30), age int2) server server_to_200 options(schema_name 'public', table_name 'student');
```

11、访问外表。

```sql
select * from local_foreign_table_student;
```

访问外表成功，返回结果为：

```sql
 student_no | student_name | age
------------+--------------+-----
          1 | xiaoming     |  12
(1 row)
```

12、对外表执行DML操作并再次查看外表。

```sql
insert into local_foreign_table_student values (2,'xiaohong',11);
update local_foreign_table_student set student_name = 'xiaoqing' where student_no = 1;
delete from local_foreign_table_student where age = 11;
select * from local_foreign_table_student ;
```

操作成功，返回结果为：

```sql
 student_no | student_name | age
------------+--------------+-----
          1 | xiaoqing     |  12
(1 row)
```

**示例2：**使用postgres_fdw连接到远程PostgreSQL 11数据库，并执行查询表的操作。

**前置条件：**以下步骤在远端服务器执行。

1、具备远端PostgreSQL 11的数据库环境。

2、在远端数据库中创建数据库用户u1。

```sql
create user u1 password 'Test1234';
```

3、创建并切换至测试数据库dtest下。

```sql
create database dtest;
\c dtest;
```

4、以用户u1的身份在远端测试数据库中创建测试表t1，并插入数据。

```sql
\c - u1
create table t1(id int,name text);
insert into t1 values(101,'zhangsan');
insert into t1 values(102,'lisi');
```

**示例步骤：**以下步骤在本地服务器中执行。

1、创建扩展postgres_fdw。

```sql
create extension postgres_fdw;
```

2、创建DBLINK，指定参数remote_kind为'postgres'。

```sql
create database link link1 connect to u1 identified by 'Test1234' using postgres_fdw(dbname 'dtest', host '127.0.0.1', port '5432', remote_kind 'postgres');
```

3、查询远端PG数据库中的t1表。

```sql
select * from public.t1@link1;
```

返回结果如下：查询到t1中的数据，且数据正确。

```sql
id  |  name 
----+----------
101 | zhangsan
102 | lisi
(2 rows)
```

**示例3：**使用postgres_fdw连接到远程PostgreSQL 11数据库，并查询分区表的数据。

**前置条件：**以下步骤在远端服务器执行。

> <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
>
> 远程连接前请确认远端PostgreSQL数据库已经在配置文件pg_hba.conf中允许其它用户访问此数据库。

1、具备远端PostgreSQL 11的数据库环境。

2、在远端数据库中创建数据库用户u2。

```sql
create user u2 password 'Test1234';
```

3、创建并切换至测试数据库dtest下。

```sql
create database dtest;
\c dtest;
```

4、以用户u2的身份在远端测试数据库中创建分区表。

```sql
\c - u2
create table test_partition(col1 int ,col2 text) partition by list(col1);
create table test_subpartition1 partition of test_partition for values in (1);
create table test_subpartition2 partition of test_partition for values  in (2);
create table test_subpartition3 partition of test_partition for values  in (3);
create table test_subpartition4 partition of test_partition for values  in (4);
create table test_subpartition5 partition of test_partition for values  in (5);
```

5、向分区表test_partition中插入数据。

```
insert into test_partition values (1,'test1');
insert into test_partition values (2,'test2');
insert into test_partition values (3,'test3');
insert into test_partition values (4,'test4');
insert into test_partition values (5,'test5');
```

**示例步骤：**以下步骤在本地服务器中执行。

1、创建扩展postgres_fdw。

```sql
create extension postgres_fdw;
```

2、创建DBLINK，指定参数remote_kind为'postgres'。

```sql
create database link link2 connect to u2 identified by 'Test1234' using postgres_fdw(dbname 'dtest', host '127.0.0.1', port '5432', remote_kind 'postgres');
```

3、开启分区表查询开关。

```sql
set sql_beta_feature=PARTITION_FDW_ON;
```

4、查询远端PG数据库中的分区表。

```sql
select * from public.test_partition@link2;
```

返回结果如下：查询到test_partition表中的数据，且数据正确。

```sql
col1 | col2 
-----+-------
   1 | test1
   2 | test2
   3 | test3
   4 | test4
   5 | test5
(5 rows)
```
