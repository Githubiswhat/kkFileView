# DBMS_METADATA

## 功能描述

DBMS_METADATA包提供了一种从数据库字典中检索元数据作为 XML或创建DDL并提交XML 以重新创建对象的方法。该内置包包含以下模块：

<table>
<tr>
<th>函数</th>
<th>子函数</th>
<th>描述</th>
</tr>
<tr>
<td rowspan="5"><a href="#GET_xxx" >GET_xxx</a></td>
<td><a href="#GET_DDL" >GET_DDL</a></td>
<td rowspan="3">函数允许用户通过单个调用获取对象的元数据。</td> 
</tr>
<tr>
<td><a href="#GET_DEPENDENT_DDL">GET_DEPENDENT_DDL</a></td>
</tr>
<tr>
<td><a href="#GET_GRANTED_DDL" >GET_GRANTED_DDL</a></td>
</tr>


## 注意事项

该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。


## 子程序语法格式

### GET_DDL<a id="GET_DDL"></a>

**语法格式**

```sql
DBMS_METADATA.GET_GRANTED_DDL (
object_type     IN VARCHAR2,
grantee         IN VARCHAR2 DEFAULT NULL,
version         IN VARCHAR2 DEFAULT 'COMPATIBLE',
model           IN VARCHAR2 DEFAULT 'ORACLE',
transform       IN VARCHAR2 DEFAULT 'DDL',
object_count    IN NUMBER   DEFAULT 10000)
RETURN CLOB;
```

object_type支持如下类型：

- table：表。不支持列存表，列名限制仅支持 not null 与 default number，仅支持分区表中分区列为数字类型。
- index：索引。
- constraint：主键。仅支持4种：primary key、 unique、 foreign 、 check
- view：视图。
- user：用户。不支持区分user与role。
- role：角色。
- tablespace：表空间。
- materialized_view：物化视图。
- db_link：远程连接定义。
- trigger：触发器。
- sequence：序列。不支持large sequence。
- function：函数。
- package：包。
- procedure：存储过程。
- package_body：包体。

#### 示例

1、创建并切换至兼容模式为Oracle的库。

```sql
create database db_oracle dbcompatibility ‘A’；
\c db_oracle
```

2、创建表空间。

```sql
create tablespace space_1103409 relative location 'tablespace/tablespace_1';
```

3、创建表。

```sql
create table tab1_1103409(id int,c1 char(8)) tablespace space_1103409;
```

4、调用get_ddl。

```sql
select DBMS_METADATA.GET_DDL('TABLE','tab1_1103409','public');
```

返回结果为：

```sql
                         get_ddl
-----------------------------------------------------------------------------------------
 create table "public"."tab1_1103409" ("id" int4, "c1" bpchar(8)) with(orientation=row,compression=no,fillfactor=80) tables
pace "space_1103409"
(1 row)
```

### GET_DEPENDENT_DDL<a id="GET_DEPENDENT_DDL"></a>

**语法格式**

```sql
DBMS_METADATA.GET_DEPENDENT_DDL (
object_type         IN VARCHAR2,
base_object_name    IN VARCHAR2,
base_object_schema  IN VARCHAR2 DEFAULT NULL,
version             IN VARCHAR2 DEFAULT 'COMPATIBLE',
model               IN VARCHAR2 DEFAULT 'ORACLE',
transform           IN VARCHAR2 DEFAULT 'DDL',
object_count        IN NUMBER   DEFAULT 10000)
RETURN CLOB;
```

object_type支持如下类型：

- object_grant：检查赋权情况。当前版本支持对table和sequence赋权情况的检查。
- index：索引。仅限用于表类型。
- constraint：主键。仅限用于表类型。

#### 示例

1、创建并切换至兼容模式为Oracle的库。

```sql
create database db_oracle dbcompatibility ‘A’；
\c db_oracle
```

2、创建表。

```sql
create table tab_1103856(c1 int,c2 int);
```

3、创建用户。

```sql
create user use1_1103856 password 'Aa@123456';
create user use2_1103856 password 'Aa@123456';
create user use3_1103856 password 'Aa@123456';
```

4、将表的权限赋给public。

```sql
grant select,insert,update,delete on table tab_1103856 to public;
```

5、将表的权限赋予用户。

```sql
grant select,insert,update,delete on table tab_1103856 to use1_1103856;
grant truncate,alter,drop,comment,index,vacuum on tab_1103856 to use2_1103856;
grant all on tab_1103856 to use3_1103856 with grant option;
```

6、调用函数。

```
select DBMS_METADATA.GET_DEPENDENT_DDL('OBJECT_GRANT','tab_1103856','public');
```

返回结果为：

```sql
                                  get_dependent_ddl
--------------------------------------------------------------------------------------
 grant insert on table "public"."tab_1103856" to "public"                            +
 grant select on table "public"."tab_1103856" to "public"                            +
 grant update on table "public"."tab_1103856" to "public"                            +
 grant delete on table "public"."tab_1103856" to "public"                            +
 grant insert on table "public"."tab_1103856" to "use1_1103856"                      +
 grant select on table "public"."tab_1103856" to "use1_1103856"                      +
 grant update on table "public"."tab_1103856" to "use1_1103856"                      +
 grant delete on table "public"."tab_1103856" to "use1_1103856"                      +
 grant truncate on table "public"."tab_1103856" to "use2_1103856"                    +
 grant alter on table "public"."tab_1103856" to "use2_1103856"                       +
 grant drop on table "public"."tab_1103856" to "use2_1103856"                        +
 grant comment on table "public"."tab_1103856" to "use2_1103856"                     +
 grant index on table "public"."tab_1103856" to "use2_1103856"                       +
 grant vacuum on table "public"."tab_1103856" to "use2_1103856"                      +
 grant insert on table "public"."tab_1103856" to "use3_1103856" with grant option    +
 grant select on table "public"."tab_1103856" to "use3_1103856" with grant option    +
 grant update on table "public"."tab_1103856" to "use3_1103856" with grant option    +
 grant delete on table "public"."tab_1103856" to "use3_1103856" with grant option    +
 grant truncate on table "public"."tab_1103856" to "use3_1103856" with grant option  +
 grant references on table "public"."tab_1103856" to "use3_1103856" with grant option+
 grant trigger on table "public"."tab_1103856" to "use3_1103856" with grant option   +
 grant alter on table "public"."tab_1103856" to "use3_1103856" with grant option     +
 grant drop on table "public"."tab_1103856" to "use3_1103856" with grant option      +
 grant comment on table "public"."tab_1103856" to "use3_1103856" with grant option   +
 grant index on table "public"."tab_1103856" to "use3_1103856" with grant option     +
 grant vacuum on table "public"."tab_1103856" to "use3_1103856" with grant option
(1 row)
```

### GET_GRANTED_DDL<a id="GET_GRANTED_DDL"></a>

**语法格式**

```sql
DBMS_METADATA.GET_GRANTED_DDL (
object_type     IN VARCHAR2,
grantee         IN VARCHAR2 DEFAULT NULL,
version         IN VARCHAR2 DEFAULT 'COMPATIBLE',
model           IN VARCHAR2 DEFAULT 'ORACLE',
transform       IN VARCHAR2 DEFAULT 'DDL',
object_count    IN NUMBER   DEFAULT 10000)
RETURN CLOB;
```

object_type支持如下类型：

- system_grant：系统权限授予。
- object_grant：对象授予。
- role_grant：角色授予。

#### 示例

1、创建并切换至兼容模式为Oracle的库。

```sql
create database db_oracle dbcompatibility ‘A’；
\c db_oracle
```

2、创建用户。

```sql
create user use_1103894 password 'Aa@123456';
create user use1_1103894 password 'Aa@123456';  
create user use2_1103894 password 'Aa@123456';  
```

3、创建数据库。

```sql
create database db_1103894;
```

4、赋权。

```sql
grant create on database db_1103894 to use_1103894;
grant alter on database db_1103894 to use_1103894;
grant drop on database db_1103894 to use_1103894;
grant connect on database db_1103894 to use_1103894;
grant temporary on database db_1103894 to use_1103894;
grant temp on database db_1103894 to use_1103894;
grant comment on database db_1103894 to use_1103894;
grant drop on database db_1103894 to use1_1103894;
grant connect on database db_1103894 to use1_1103894;
grant temporary on database db_1103894 to use1_1103894;
grant temp on database db_1103894 to use1_1103894;
grant comment on database db_1103894 to use1_1103894;  
grant all on database db_1103894 to use2_1103894 with grant option;
```

5、调用函数（use_1103894）。

```sql
select DBMS_METADATA.GET_GRANTED_DDL('OBJECT_GRANT','use_1103894') from dual;
```

返回结果为：

```sql
                   get_granted_ddl
-----------------------------------------------------
 grant all on database "db_1103894" to "use_1103894"
(1 row)
```

6、调用函数（use1_1103894）。

```sql
select DBMS_METADATA.GET_GRANTED_DDL('OBJECT_GRANT','use1_1103894') from dual;
```

返回结果为：

```sql
                     get_granted_ddl
----------------------------------------------------------
 grant drop on database "db_1103894" to "use1_1103894"   +
 grant comment on database "db_1103894" to "use1_1103894"+
 grant temp on database "db_1103894" to "use1_1103894"   +
 grant connect on database "db_1103894" to "use1_1103894"
(1 row)
```

7、调用函数（use2_1103894）。

```sql
select DBMS_METADATA.GET_GRANTED_DDL('OBJECT_GRANT','use2_1103894') from dual;
```

返回结果为：

```sql
                            get_granted_ddl
------------------------------------------------------------------------
 grant all on database "db_1103894" to "use2_1103894" with grant option
(1 row)
```

## 参数说明

| 参数名称           | 描述                                                         |
| ------------------ | ------------------------------------------------------------ |
| object_type        | 对象类型                                                     |
| name               | 对象名称                                                     |
| schema             | 对象架构，默认为 NULL                                        |
| version            | 要提取的元数据版本，默认为'COMPATIBLE'（此参数无实际效果）   |
| transfomodel       | 要使用的对象模型，默认为'ORACLE'（此参数无实际效果）         |
| rm                 | 输出上的转换名称，GET_XML 默认为 NULL，GET_DDL默认为DDL，GET*SXML 默认为 SXML(如果在 GET_DDL中指定为NULL，则会输出XML 格式，同样，在 GET_XML 中指定为DDL，也会输出为 sql语句格式（此参数无实际效果） |
| base_object_name   | 基础对象名称                                                 |
| base_object_schema | 基础对象架构                                                 |
| grantee            | 授予的用户，默认为当前用户                                   |
| object_count       | 要返回的最大对象数默认 10000（此参数无实际效果，object_count 无限制） |

## 示例

在存储过程中调用内置包。

1、创建并切换至兼容模式为Oracle的数据库db_oracle。

```sql
CREATE DATABASE db_oracle dbcompatibility='A';    
\c db_oracle
```

2、创建表。

```sql
create table tab_1103905(id int,c1 char(8));
```

3、创建用户。

```sql
create user use_1103905 password 'Aa@123456';
```

4、将表的权限赋予用户。

```sql
grant insert,update on table tab_1103905 to use_1103905;
```

5、创建存储过程。

```sql
create or replace procedure pro_1103905(n int)
as
c1 record;
c2 record;
c3 record;
begin
if n>5 then
select DBMS_METADATA.GET_DDL('TABLE','tab_1103905','public') into c1;
select DBMS_METADATA.GET_DEPENDENT_DDL('OBJECT_GRANT','tab_1103905','public') into c2;
select DBMS_METADATA.GET_GRANTED_DDL('OBJECT_GRANT','use_1103905') into c3;
raise notice '%',c1;
raise notice '%',c2;
raise notice '%',c3;
else
raise notice 'No action!';
end if;
end;
/
```

6、调用存储过程，入参为6。

```sql
call pro_1103905(6);
```

返回结果为：

```sql
NOTICE:  ("create table ""public"".""tab_1103905"" (""id"" int4, ""c1"" bpchar(8)) with(orientation=row,compression=no,fillfactor=80) ")
NOTICE:  ("grant insert on table ""public"".""tab_1103905"" to ""use_1103905""
grant update on table ""public"".""tab_1103905"" to ""use_1103905""")
NOTICE:  ("grant insert on table ""public"".""tab_1103905"" to ""use_1103905""
grant update on table ""public"".""tab_1103905"" to ""use_1103905""")
 pro_1103905
-------------

(1 row)
```

7、调用存储过程，入参为3。

```sql
call pro_1103905(3);
```

返回结果为：

```sql
NOTICE:  No action!
 pro_1103905
-------------

(1 row)
```

