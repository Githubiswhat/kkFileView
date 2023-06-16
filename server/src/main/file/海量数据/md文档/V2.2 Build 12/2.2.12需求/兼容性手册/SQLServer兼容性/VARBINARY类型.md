# VARBINARY类型

## 功能描述

Vastbase G100在SQL Server兼容模式下支持VARBINARY类型。varbinary为可变长度二进制字符串类型，存储的是二进制数据。其中包括三种类型定义：

- varbinary，插入值可变长。

- varbinary(n)，插入值的长度为n，数据长度可以在 1到8000之间，其中包括1和8000。

- varbinary(max)，插入值的长度为max，代表长度为2147483647。


## 语法参考

```sql
varbinary
varbinary(n)
varbinary(max)
```

## 参数说明

- **n**

  变长值指定长度。

  取值范围：1~8000

  单位：byte

- **max** 

  最大值。

  代表长度：2147483647。
  
  单位：byte

## 注意事项

本特性仅在数据库兼容模式为SQL Server时能够使用（即创建DB时DBCOMPATIBILITY='MSSQL'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置条件：**创建兼容SQL Server的库db_sqlserver，并进入。

```sql
create database db_sqlserver dbcompatibility  'MSSQL';
\c db_sqlserver
```

**示例1：** 在系统表中查找varbinary类型。

```sql
\x    --列式展示查询结果
select * from pg_type where typname='varbinary';
```

结果显示为：

```sql
-[ RECORD 1 ]--+------------------
typname        | varbinary
typnamespace   | 11
typowner       | 10
typlen         | -1
typbyval       | f
typtype        | b
typcategory    | U
typispreferred | f
typisdefined   | t
typdelim       | ,
typrelid       | 0
typelem        | 0
typarray       | 0
typinput       | varbinaryin
typoutput      | varbinaryout
typreceive     | varbinaryrecv
typsend        | binarysend
typmodin       | varbinarytypmodin
typmodout      | binarytypmodout
typanalyze     | -
typalign       | i
typstorage     | x
typnotnull     | f
typbasetype    | 0
typtypmod      | -1
typndims       | 0
typcollation   | 0
typdefaultbin  |
typdefault     |
typacl         |
```

**示例2：** 创建带varbinary类型的表，并插入数据。

1、创建带varbinary类型的表，并插入数据，可以成功创建并插入数据。

```sql
create table test1(col1 int,col2 varbinary);
insert into test1 values(1,12);
insert into test1 values(3,null);
insert into test1 values(1,'0xFF');
insert into test1 values(1,'0x100');
```

2、查询表记录。

```sql
select * from test1;
```

结果显示为：

```
 col1 | col2
------+------
    1 | 0x0c
    3 |
    1 | 0xff
    1 | 0x01
(4 rows)
```

**示例3：** 创建带varbinary(n)类型的表。

1、创建带varbinary(n)类型的表。

```sql
create table test2(col3 varbinary(0));
create table test3(col3 varbinary(1));
create table test4(col3 varbinary(8000));
create table test5(col3 varbinary(8001));
```

只有当n值符合1~8000范围内才能创建表成功，否则创建表失败。以上四条建表语句的结果显示为test3、test4表创建成功，test2、test5表创建失败：

```sql
ERROR:  varbinary(n): n must be a value from 1 through 8000
CREATE TABLE
CREATE TABLE
ERROR:  varbinary(n): n must be a value from 1 through 8000
```

2、分别向带varbinary(1)、varbinary(8000)字段的表中插入数据。

```sql
insert into test3 values('0x123456');
insert into test4 values('0x123456');
```

以上两条插入语句结果为插入test3失败，插入test4成功：

```sql
ERROR:  value too long for type varbinary(1)
INSERT 0 1
```

3、查询test4表中插入的记录。

```sql
select * from test4;
```

结果显示为：

```sql
   col3
----------
 0x123456
(1 row)
```

**示例4：** 创建带varbinary(max)类型的表。

1、创建带varbinary(max)类型的表。

```sql
create table test6(col4 varbinary(max));
```

2、向带varbinary(max)类型的表插入数据。

```sql
insert into test6 values('\x123456');
select * from test6;
```

查询结果为：

```sql
   col4
----------
 0x123456
(1 row)
```

