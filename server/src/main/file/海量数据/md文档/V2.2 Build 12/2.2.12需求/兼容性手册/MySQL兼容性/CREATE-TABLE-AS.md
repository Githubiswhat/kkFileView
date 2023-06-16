# CREATE TABLE AS

## 功能描述

CREATE TABLE AS语法可以根据查询结果创建表，并且用来自SELECT命令的结果填充该表。该表的字段和SELECT输出字段的名称及数据类型相关，但用户可以通过明确地给出一个字段名称列表来覆盖SELECT输出字段的名称。

Vastbase G100在MySQL兼容模式下，支持CREATE TABLE AS语法中使用IF NOT EXISTS进行创建表和用户定义表字段的属性。

- 当新增的列与select生成的列重名时，以新增的列为主。
- 当新增的列与新增的列重名时，语法报错。
- 当select生成的列与select生成的列重名时，语法报错。

## 语法格式

```sql
CREATE [ [ GLOBAL | LOCAL ] [ TEMPORARY | TEMP ] |  UNLOGGED ] TABLE [IF NOT EXISTS] table_name
    [ ( { column_name data_type [ compress_mode ] [ COLLATE collation ] [ column_constraint [ ... ] ]
    | table_constraint }
    [, ... ]) ]
    [ WITH ( {storage_parameter = value} [, ... ] ) ]
    [ COMPRESS | NOCOMPRESS ]
    [ TABLESPACE tablespace_name ]
    [ DISTRIBUTE BY { REPLICATION | { [HASH ] ( column_name ) } } ]
    [ TO { GROUP groupname | NODE ( nodename [, ... ] ) } ]
    [ REPLACE | IGNORE ]
    AS query
    [ WITH [ NO ] DATA ];
```

## 参数说明

- **IF NOT EXISTS**

    IF NOT EXISTS表示如果表存在，则跳过，不做报错处理；如果表不存在，则创建表。

- **column_name**

    新表中要创建的字段名。

- **data_type**

    字段的数据类型。

- **compress_mode**

    表字段的压缩选项。本选项指定表字段优先使用的算法。行存表不支持压缩。

    取值范围：DELTA、PREFIX、DICTIONARY、NUMSTR、NOCOMPRESS

- **COLLATE collation**

    COLLATE子句指定列的排序规则（该列必须是可排列的数据类型）。如果没有指定，则使用默认的排序规则。

    排序规则可以使用`select * from pg_collation;`命令从pg_collation系统表中查询，默认的排序规则为查询结果中以default开始的行。

- **column_constraint|table_constraint**

    列约束或表约束的名称。可选的约束子句用于声明约束，新行或者更新的行必须满足这些约束才能成功插入或更新。

    - 列约束column_constraint：作为一个列定义的一部分，仅影响该列。
    - 表约束table_constraint：不和某个列绑在一起，可以作用于多个列。

- **REPLACE | IGNORE **

    当插入数据引起唯一性冲突的处理办法，默认情况为报错。

    IGNORE为忽略，REPLACE为替代。

- 其他参数参考[CREATE TABLE AS](../../../../../VastbaseG100Ver2.2.10/doc/开发者指南/CREATE-TABLE-AS.md)。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 使用IF NOT EXISTS 无法验证现有表的结构与CREATE TABLE语句所指示的结构相同。
- CREATE TABLE AS不支持外键和table like子句。

## 示例

**前提条件：**创建并进入兼容MySQL的库db_mysql。

```sql
CREATE DATABASE db_mysql DBCOMPATIBILITY 'B';
\c db_mysql 
```

**示例1：**使用if not exists创建表。

1、创建测试表并插入数据。

```sql
CREATE TABLE iftest(id int);
INSERT INTO iftest VALUES (1);
INSERT INTO iftest VALUES (2);
INSERT INTO iftest VALUES (3);
```

2、使用if not exists创建表。

```sql
CREATE TABLE IF NOT EXISTS iftest2 AS table iftest;
```

3、查询表iftest2数据。

```sql
SELECT * FROM iftest2;
```

结果返回如下：

```sql
 id
----
  1
  2
  3
(3 rows)
```

**示例2：**创建新表时定义字段属性。

1、创建测试表并插入数据。

```sql
create table tab_test(id int,name char(8)); 
insert into tab_test values(1,'张三'),(2,'李四');
```

2、设置表字段的压缩选项。

```sql
create table tab_test1(name char(8) NOCOMPRESS) as select * from tab_test;
\d+ tab_test1;
```

查看表详细信息如下，字段name的压缩选项被设置为NOCOMPRESS：

```sql
                               Table "public.tab_test1"
 Column |    Type     | Modifiers |Storage | Stats target | Description | Attalias
--------+-------------+-----------+--------+-------------+--------------+-------
 name   |character(8) |           |extended|             |              | name
 id     |integer      |           |plain   |             |              |
Has OIDs: no
Options: orientation=row, compression=no, fillfactor=80
```

3、设置列的排序规则。

```sql
CREATE TABLE tab_test2(a text COLLATE "C",b text COLLATE "en_US",id int
)as select * from tab_test;
\d+ tab_test2;
```

查看表详细信息为如下，a列的排序方式被设置为collate C：

```sql
                                 Table "public.tab_test2"
Column |    Type    |  Modifiers  | Storage | Stats target | Description | Attalias
-------+------------+--------------+----------+--------------+-----------+---------
 a     |text        | collate C    | extended |              |           | a
 b     |text        | collate en_US| extended |              |           | b
 id    |integer     |              | plain    |              |           | id
 name  |character(8)|              | extended |              |           |
Has OIDs: no
Options: orientation=row, compression=no, fillfactor=80
```

4、设置字段约束。

```sql
create table tab_test3(id int primary key,col4 int) as select * from tab_test;
\d+ tab_test3
```

返回信息和查看表详细信息结果为如下，字段id被设置为主键：

```sql
NOTICE:  CREATE TABLE / PRIMARY KEY will create implicit index "tab_test3_pkey" for table "tab_test3"
INSERT 0 2

                               Table "public.tab_test3"
 Column |     Type    | Modifiers | Storage | Stats target| Description | Attalias
--------+-------------+-----------+---------+-------------+-------------+-------
 id     | integer     | not null  | plain   |             |             | id
 col4   | integer     |           | plain   |             |             | col4
 name   | character(8)|           | extended|             |             |
Indexes:
    "tab_test3_pkey" PRIMARY KEY, btree (id) TABLESPACE pg_default
Has OIDs: no
Options: orientation=row, compression=no, fillfactor=80
```

 5、设置表约束。

```sql
create table tab_test4(id int, name char(8), CONSTRAINT t1_pk PRIMARY KEY (id, name)) as select * from tab_test;
\d+ tab_test4
```

成功为tab_test4设置表约束，返回信息和查看表详细信息结果如下：

```sql
NOTICE:  CREATE TABLE / PRIMARY KEY will create implicit index "t1_pk" for table "tab_test4"
INSERT 0 2

                               Table "public.tab_test4"
 Column |    Type    | Modifiers| Storage | Stats target| Description | Attalias
--------+------------+----------+---------+-------------+-------------+-------
 id     |integer     | not null | plain   |             |             | id
 name   |character(8)| not null | extended|             |             | name
Indexes:
    "t1_pk" PRIMARY KEY, btree (id, name) TABLESPACE pg_default
Has OIDs: no
Options: orientation=row, compression=no, fillfactor=80
```

