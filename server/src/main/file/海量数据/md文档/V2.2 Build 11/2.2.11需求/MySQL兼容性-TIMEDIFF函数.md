# MySQL兼容性-TIMEDIFF函数

# 功能描述

TIMEDIFF函数返回两个时间类型之间的差值。

## 语法格式

```sql
TIMEDIFF(expr1,expr2)
```

## 参数说明

**expr**

时间表达式或日期时间表达式。取值为如下数据类型：

- datetime
- time
- timestamp

## 注意事项

- expr1与expr2必须是同一类型。
- expr1或expr2为NULL时，函数返回NULL。
- 当数据类型为datetime时，仅支持在数据库兼容模式为MySQL时使用。

## 示例

**示例1：**参数数据类型为datetime。

1、创建兼容MySQL的库db_mysql，并进入。

```sql
create database db_mysql dbcompatibility  'B';
\c db_mysql
```

2、调用函数。

```sql
select timediff(datetime'2022-11-07 12:00:00',datetime'2022-12-11 13:00:01');
```

结果返回如下：

```sql
  timediff
------------
 -817:00:01
(1 row)
```

**示例2：**参数数据类型为time。

```sql
select timediff(time'12:00:00',time'13:00:02');
```

结果返回如下：

```sql
 timediff
-----------
 -01:00:02
(1 row)
```

**示例3：**参数数据类型为timestamp。

```sql
select timediff(timestamp'2000-01-01 00:00:00',timestamp'2000-01-01 00:00:00.000001');
```

结果返回如下：

```sql
     timediff
------------------
 -00:00:00.000001
(1 row)
```

