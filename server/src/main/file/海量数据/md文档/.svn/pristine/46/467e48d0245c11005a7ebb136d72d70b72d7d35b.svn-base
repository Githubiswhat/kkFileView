# MySQL兼容性-CREATE TABLE AS兼容IF NOT EXISTS

## 功能描述

在MySQL兼容模式下CREATE TABLE AS支持使用IF NOT EXISTS进行创建。

## 语法格式

```sql
CREATE [[ GLOBAL LOCAL ][TEMPORARY TEMP | UNLOGGED ]TABLE
[IF NOT EXISTS ] table name
[ (column name [, ...]) ]
WITH ({fstorage_parameter = value} [, ... ])]
[ON COMMIT {PRESERVE ROWS | DELETE ROWS | DROP}]
[COMPRESS | NOCOMPRESS]
[TABLESPACE tablespace_name]
AS query
[WITH [NO]DATA];
```

## 参数说明

- **IF NOT EXISTS**

  在CREATE TABLE 时加入IF NOT EXISTS表示如果表存在，则跳过，不做报错处理。如果表不存在，则创建表。

- 其他参数参考[CREATE TABLE AS](../开发者指南/CREATE-TABLE-AS.md)。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 使用IF NOT EXISTS 无法验证现有表的结构与CREATE TABLE语句所指示的结构相同。

## 示例

1、创建并进入兼容MySQL的库db_mysql。

```sql
CREATE DATABASE db_mysql DBCOMPATIBILITY 'B';
\c db_mysql 
```

2、创建测试表并插入数据。

```sql
CREATE TABLE iftest(id int);
INSERT INTO iftest VALUES (1);
INSERT INTO iftest VALUES (2);
INSERT INTO iftest VALUES (3);
```

3、使用if not exists创建表。

```sql
CREATE TABLE IF NOT EXISTS iftest2 AS table iftest;
```

4、查询表iftest2数据。

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

