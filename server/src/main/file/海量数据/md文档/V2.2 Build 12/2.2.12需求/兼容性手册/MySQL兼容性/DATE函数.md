# DATE

## 功能描述

Vastbase G100在MySQL兼容模式下，支持使用DATE函数提取日期或日期时间表达式的中的日期部分。

如果指定的表达式不是一个合法的日期或者日期时间，DATE函数将返回NULL。如果输入参数为NULL，函数也返回 NULL。

## 语法格式

```sql
DATE(expr)
```

## 参数说明

**expr**

输入的日期表达式，该参数为必填项。

## 注意事项

该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建兼容模式为MySQL的库db_mysql，并进入。

```
create  database  db_mysql  dbcompatibility  'B';
\c  db_mysql
```

**示例1：**直接调用date函数。

```sql
select date('2022-01-01 12:30:00');
```

返回结果为：

```SQL
    date
------------
 2022-01-01
(1 row)
```

**示例2：**在DML语句中使用DATE函数。

1、创建测试表并插入数据。

```sql
create table t_date(id int, col1 date, col2 timestamp with time zone);
insert into t_date values(1,date '2022-01-01',timestamp '2022-01-01 12:30:00.666 +08');
```

2、在INSERT语句中使用DATE函数。

```sql
insert into t_date values(2,date('2022-01-03'),date(timestamp '2022-01-03 12:30:00.666 +08'));
```
3、在UPDATE语句中使用DATE函数。

```sql
update t_date set col1=date('2022-02-01 12:00:00') where id=1;
```

4、查询数据。

```sql
select * from t_date;
```

返回结果为：

```sql
 id |    col1    |            col2
----+------------+----------------------------
  2 | 2022-01-03 | 2022-01-03 00:00:00+08
  1 | 2022-02-01 | 2022-01-01 12:30:00.666+08
(2 rows)
```

