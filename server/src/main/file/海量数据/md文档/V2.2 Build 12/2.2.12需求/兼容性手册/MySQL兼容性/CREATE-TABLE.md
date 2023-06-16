补充跳转链接实际路径。

# CREATE TABLE

## 功能描述

Vastbase G100在MySQL兼容模式下，CREATE TABLE语法支持以下用法：

- 支持在建表时指定存储引擎和字符集。
- 支持在建表时指定表级校对规则。

其他关于CREATE TABLE的用法请参考[CREATE TABLE](../../开发者指南/CREATE-TABLE.md)。

## 语法格式

```sql
CREATE [ [ GLOBAL | LOCAL ] { TEMPORARY | TEMP } | UNLOGGED ] TABLE [ IF NOT EXISTS ] table_name
  ( { column_name data_type [ compress_mode ] [ COLLATE collation ] [ column_constraint [ ... ] ]
    | table_constraint
    | LIKE source_table [ like_option [...] ] }
    [, ... ])
    [ AUTO_INCREMENT [ = ] value ]
    [ INHERITS ( parent_table [, ... ] ) ]
    [ WITH ( {storage_parameter = value} [, ... ] ) ]
    [ ON COMMIT { PRESERVE ROWS | DELETE ROWS | DROP } ]
    [ COMPRESS | NOCOMPRESS ]
    [ TABLESPACE tablespace_name ]
    [ COLLATE [=] collate_name];
	[ ENGINE [=] engine_name ]  
	[ [DEFAULT] { CHARSET | CHARACTER SET } [=] charset_name ];
```

## 参数说明

- **collate_name**

  表的校对规则。可用的规则可以在系统表[PG_COLLATION](PG_COLLATION.md)中查询，默认的排序规则为查询结果中以default开始的行。

- **[DEFAULT] { CHARSET | CHARACTER SET } [=] charset_name**

  该参数用于选择表所使用的字符集。

- **ENGINE [=] engine_name**

  该参数用于指定存储引擎。

  > <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
  >
  > 更多参数说明请参考：[CREATE TABLE ](开发者指南/CREATE-TABLE.md)。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 支持在建表时指定存储引擎和字符集的功能只做纯语法兼容，不实现功能。

## 示例

**前置步骤：**创建兼容模式为MySQL的库db_mysql，并进入。

```sql
create database db_19836 dbcompatibility='B';
\c db_19836;
```

**示例1：**在建表时指定表级校对规则。

1、查看数据库的pad_attribute参数。

```sql
show pad_attribute;
```
返回结果为：

```sql
 pad_attribute
---------------
 NO PAD
(1 row)
```

> <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
>
> Vastbase G100从V2.2 Build 12开始支持[初始化数据库时指定pad_attribute](vb_initdb.md#attribute)以及[创建数据库时指定pad_attribute](CREATE-DATABASE.md)的功能，以此来为新数据库设置默认的列校验属性，可选取值包括：
>
> - N,NO PAD：默认值。把字符串尾端的空格当作一个字符处理，即字符串等值比较不忽略尾端空格。
>
> - S,PAD SPACE：字符串等值比较忽略尾端空格。

2、创建具有各种字符串类型的测试表，并为每一列指定不同的列校对规则，指定表级别COLLATE为"C"。

> <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
> 其中列c2、c4、c6的校对规则为`*-space`，即忽略空格匹配。c7未指定列校对规则，使用建库时的默认校对规则，本示例建库时未指定，默认为NO PAD。

```sql
create table tb_1132414_01(
c1 text COLLATE "C",
c2 character varying(30) COLLATE "C-space",
c3 varchar(30) COLLATE "zh_CN",
c4 varchar2(30) COLLATE "zh_CN-space",
c5 nvarchar2(30) COLLATE "en_US",
c6 clob COLLATE "en_US-space",
c7 varchar(30)) COLLATE "C";
```

3、插入测试数据。（第一次插入的字符串尾端无空格，第二次插入的字符串尾端存在空格。）

```sql
insert into tb_1132414_01 values('qqq', 'cqvjj11', 'h2vu2hb3t', 'f4rh535', 'fr2h5fj3c', 'r2hfhcg', 'r2cgcr');
insert into tb_1132414_01 values('qqq ', 'cqvjj11 ', 'h2vu2hb3t ', 'f4rh535 ', 'fr2h5fj3c ', 'r2hfhcg ', 'r2cgcr ');
```

4、查看是否正确识别空格。

```sql
select c1='qqq', c2='cqvjj11', c3='h2vu2hb3t', c4='f4rh535', c5='fr2h5fj3c', c6='r2hfhcg', c7='r2cgcr' from tb_1132414_01;
```

返回结果如下：对于插入的第二条尾部带空格的数据，c2、c4、c6可以忽略空格，返回true，其他返回false。

```sql
 ?column? | ?column? | ?column? | ?column? | ?column? | ?column? | ?column?
----------+----------+----------+----------+----------+----------+----------
 t        | t        | t        | t        | t        | t        | t
 f        | t        | f        | t        | f        | t        | f
(2 rows)
```

**示例2：**在建表时指定存储引擎和字符集。

1、创建一张测试表设置ENGINE和 CHARSET。

```sql
create table tab_1131500 (id int, val1 int, val2 text) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

2、给表中插入数据。

```sql
insert into tab_1131500 values(1,1,1);
```

3、更新表中数据。

```sql
update tab_1131500 set id =2 where id =1 ;
```

4、查询数据。

```sql
select * from tab_1131500;
```

返回结果为：

```sql
 id | val1 | val2
----+------+------
  2 |    1 | 1
(1 row)
```

