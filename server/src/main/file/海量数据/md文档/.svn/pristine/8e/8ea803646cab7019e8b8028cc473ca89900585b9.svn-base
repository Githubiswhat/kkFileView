#### PostgreSQL_FDW

**功能描述**

postgres_fdw功能提供了外部数据封装器postgres_fdw插件，它可以被用来访问存储在外部Vastbase服务器中的数据。

**语法格式**

1、加载postgres_fdw扩展

```
CREATE EXTENSION [ IF NOT EXISTS ] extension_name
  [ WITH ] [ SCHEMA schema_name ]
       [ VERSION version ]
       [ FROM old_version ]
```

2、创建一个新的外部服务器

```
CREATE SERVER server_name
FOREIGN DATA WRAPPER fdw_name
  	OPTIONS ( { option_name ' value ' } [, ...] ) ;
```

3、创建一个用户到一个外部服务器的新映射

```
CREATE USER MAPPING FOR { user_name | USER | CURRENT_USER | PUBLIC }
  SERVER server_name
[ OPTIONS ( option 'value' [ , ... ] ) ]
```

4、创建外表

```
CREATE FOREIGN TABLE [ IF NOT EXISTS ] table_name ( [
column_name type_name [ OPTIONS ( option 'value' [, ... ] ) ] [ COLLATE collation ] [ column_constraint [ ... ] ]
 [, ... ]
] )
 SERVER server_name
[ OPTIONS ( option 'value' [, ... ] ) ]
```

这里column_constraint 可以是:

```
[ CONSTRAINT constraint_name ]
{ NOT NULL |
NULL |
DEFAULT default_expr }
```

**参数说明**

- server_name：server的名称。取值范围：长度必须小于等于63。

- fdw_name：指定外部数据封装器的名称。取值范围：oracle_fdw，mysql_fdw，postgres_fdw，mot_fdw。

- CREATE SERVER中OPTIONS参数包括：

  - host（必选）：要连接的主机名，本功能填写远端服务器的IP地址。


  - port（必选）：主机服务器的端口号，本功能填写远端服务器上集群的端口号。


  - dbname（必选）：数据库名，本功能填写远端服务器上的数据库名。


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


- user_name：要映射到外部服务器的一个现有用户的名称。 CURRENT_USER和USER匹配当前用户的名称。 当PUBLIC被指定时，一个所谓的公共映射会被创建，当没有特定用户的映射可用时将会使用它。

- server_name：将为其创建用户映射的现有服务器的名称。

- CREATE USER MAPPING中OPTIONS参数包括：

  - user(必选)：远端服务器上Vastbase的用户名，属于普通用户。


  - password（必选）：远端服务器上Vastbase用户对应的密码


- table_name：外表的表名。取值范围：字符串，要符合标识符的命名规范。

- column_name：外表中的字段名。取值范围：字符串，要符合标识符的命名规范。

- type_name：字段的数据类型。

- SERVER server_name：外表的server名称。

- OPTIONS 参数包括：

  - schema_name（必选）：远端server的schema名称。如果不指定的话，将使用外表自身的schema名称作为远端的schema名称。


  - table_name（必选）：远端server的表名。如果不指定的话，将使用外表自身的表名作为远端的表名。


  - column_name（非必选）：远端server的表的列名。如果不指定的话，将使用外表自身的列名作为远端的的表的列名。


**注意事项**

- 两个postgres_fdw外表间的SELECT JOIN不支持下推到远端Vastbase执行，会被分成两条SQL语句传递到远端Vastbase执行，然后在本地汇总处理结果。
- 不支持IMPORT FOREIGN SCHEMA语法。
- 不支持对外表进行CREATE TRIGGER操作。

**示例**

1、在本地服务器创建用户。

```
create user hzy with sysadmin password '123456Aa';
grant all on database postgres to hzy;
```

2、远端服务器修改配置文件pg_hba.conf的配置参数，新增下列参数。

```
host  all   all       0.0.0.0/0         sha256
```

3、远端服务器修改配置文件postgres.conf中的listen_address 参数，值为本地服务器的IP地址。

4、远端服务器中创建用户。

```
create user hzy with sysadmin password '123456Aa';
grant all on database postgres to hzy;
```

5、远端服务器使用hzy用户创建测试表。

```
create table student( student_no int4 primary key, student_name varchar(30), age int2 ); 
insert into student values(1,'xiaoming',12); 
```

6、在本地服务器中，使用新建的用户登录系统。

7、加载postgres_fdw扩展。

```
create extension postgres_fdw ;
```

8、创建外部服务器。

```
create server server_to_200 foreign data wrapper postgres_fdw options (host '172.16.103.82',port '64322',dbname 'postgres');
```

9、创建一个用户到外部服务器的映射。

```
create user MAPPING FOR hzy SERVER server_to_200 OPTIONS (user 'hzy',password '123456Aa');
```

10、创建外表。

```
create foreign table local_foreign_table_student( student_no int4 , student_name varchar(30), age int2) server server_to_200 options(schema_name 'public', table_name 'student');
```

11、访问外表。

```
select * from local_foreign_table_student;
insert into local_foreign_table_student values (2,'xiaohong',11);
update local_foreign_table_student set student_name = 'xiaoqing' where student_no = 1;
delete from local_foreign_table_student where age = 11;
explain select * from local_foreign_table_student ;
```

12、查看外表结构。

```
\d+ local_foreign_table_student;
```