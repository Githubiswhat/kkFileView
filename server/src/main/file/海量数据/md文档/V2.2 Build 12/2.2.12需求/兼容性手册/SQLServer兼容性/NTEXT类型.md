# NTEXT类型

## 功能描述

Vastbase G100在SQL Server兼容模式下支持ntext类型。ntext类型是用于存储大量Unicode文本数据的可变长度数据类型。

## 注意事项

- 本特性仅在数据库兼容模式为SQL Server时能够使用（即创建DB时DBCOMPATIBILITY='MSSQL'），在其他数据库兼容模式下不能使用该特性。
- ntext类型为text类型的复用，所以没有类型oid，无法直接通过查询内部表获取信息。

## 示例

**前置条件：**创建兼容SQL Server的库db_sqlserver，并进入。

```sql
create database db_sqlserver dbcompatibility 'MSSQL';
\c db_sqlserver
```

**示例1：** 类型强制转换。

1、类型强制转换为ntext类型。

```sql
select 1::ntext;
select 'aa'::char::ntext;
```

结果显示为：

```sql
 text
------
 1
(1 row)

 text
------
 a
(1 row)
```

2、将next类型强制转换为其他。

```sql
select 'AAAAA'::ntext::varchar; 
select 5::ntext::int;
```

结果显示为：

```sql
 varchar
---------
 AAAAA
(1 row)

 int4
------
    5
(1 row)
```

**示例2：**在表中使用ntext类型。

1、创建带ntext类型的表。

```sql
create table tab1(a1 ntext PRIMARY KEY);
insert into tab1 values('2'),('ff'),('!'),('li'),('good'),('yes');
```

2、创建与表1关联的表2。

```sql
create table tab2(a1 ntext not null,a2 ntext unique,a3 ntext PRIMARY KEY,a4 ntext default 'good',a5 ntext check(a5 is not null),a6 ntext REFERENCES tab1(a1));
```

3、使用元命令查看表2信息。

```sql
\d+ tab2
```

表2具体信息如下：

```sql
                          Table "public.tab_1117218"
 Column | Type |      Modifiers       | Storage  | Stats target | Description
--------+------+----------------------+----------+--------------+-------------
 a1     | text | not null             | extended |              |
 a2     | text |                      | extended |              |
 a3     | text | not null             | extended |              |
 a4     | text | default 'good'::text | extended |              |
 a5     | text |                      | extended |              |
 a6     | text |                      | extended |              |
Indexes:
    "tab_1117218_pkey" PRIMARY KEY, btree (a3) TABLESPACE pg_default
    "tab_1117218_a2_key" UNIQUE CONSTRAINT, btree (a2) TABLESPACE pg_default
Check constraints:
    "tab_1117218_a5_check" CHECK (a5 IS NOT NULL)
Foreign-key constraints:
    "tab_1117218_a6_fkey" FOREIGN KEY (a6) REFERENCES tt_1117218(a1)
Has OIDs: no
Options: orientation=row, compression=no, fillfactor=80
```

4、向表2中插入数据并查看记录。

```sql
insert into tab_1117218 values('my','name','cc','is','li','li');
insert into tab_1117218 values('aa','bb','how','are','you','!');
select * from tab2 order by a1; 
```

查询结果为：

```sql
 a1 |  a2  | a3  | a4  | a5  | a6
----+------+-----+-----+-----+----
 aa | bb   | how | are | you | !
 my | name | cc  | is  | li  | li
(2 rows)
```

