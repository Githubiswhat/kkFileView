# ISNULL

## 功能描述

Vastbase在SQL Server兼容模式下支持ISNULL函数，本函数可以判断表达式的值是否为NULL。当表达式值被判断为NULL时，使用指定的值替换NULL值。

## 语法格式

```sql
ISNULL(check_expression,replacement_value)
```

## 参数说明

- **check_expression**

  被判断的表达式。

  当表达式的值为NULL时，将被指定的值（即replacement_value值）替换。

- **replacement_value**

  指定的替换值。

  当check_expression表达式的值被判断为NULL时，其值被替换为指定的值（replacement_value值）。

## 注意事项

- 本特性仅在数据库兼容模式为SQL Server时能够使用（即创建DB时DBCOMPATIBILITY='MSSQL'），在其他数据库兼容模式下不能使用该特性。
- 替换值replacement_value的类型必须可以隐式转换为表达式check_expression的类型，当无法隐式转换时，函数报错。
- check_expression和replacement_value为必填项，可以填入NULL和空字符串或其他类型值，但不可不填。

## 示例

**前置条件：**创建兼容SQL Server的库db_sqlserver，并进入。

```sql
create database db_sqlserver dbcompatibility  'MSSQL';
\c db_sqlserver
```

**示例1：**select语句中调用ISNULL函数。

1、当判断值不为空时，调用ISNULL函数。

```sql
SELECT ISNULL('Hello','W3Schools.com'::varchar);
```

表达式值不被替换，返回结果为：

```sql
 isnull
--------
 Hello
(1 row)
```

2、当判断值为空时，调用ISNULL函数。

```sql
SELECT ISNULL(null,'W3Schools.com'::varchar);
```

表达式值被指定的值替换，返回结果为：

```sql
 isnull
---------------
 W3Schools.com
(1 row)
```

**示例2：**在查询测试表中数据时调用ISNULL函数。

1、创建测试表并插入数据。

```sql
create table tb_test(id int, name varchar);
insert into tb_test values(1, NULL), (NULL, 'test2'), (3, 'test3');
select * from tb_test;
```

表中记录的内容为：

```sql
 id | name
----+-------
  1 |
    | test2
  3 | test3
(3 rows)
```

2、调用ISNULL函数。

```sql
SELECT ISNULL(id, '101'), ISNULL(name, 'testname') from tb_test order by id;
```

结果显示如下，其中在查询结果中，id字段为空的记录的id值被替换为101；name字段为空的记录的name值被替换为了testname：

```
 isnull |  isnull
--------+----------
      1 | testname
      3 | test3
    101 | test2
(3 rows)
```

**示例3：**当被检查表达式为空字符串时，其值不被替换。

```sql
SELECT ISNULL('','W3Schools.com'::varchar);
```

结果显示如下，表达式不被替换：

```
 isnull
--------

(1 row)
```

