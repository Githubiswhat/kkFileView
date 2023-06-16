# IFNULL

## 功能描述

IFNULL(expr1,expr2)函数用于判断函数中expr1是否为NULL，如果为NULL则返回expr2的值，如果不为NULL则返回expr1的值。

## 语法格式

```sql
IFNULL(expr1,expr2)
```

## 参数说明

**expr**

自定义参数值。	

## 注意事项

该特性仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建兼容MySQL的库db_mysql，并进入。

```sql
create database db_mysql dbcompatibility  'B';
\c db_mysql
```

**示例1：**执行SQL语句验证expr1为null和不为NULL返回值。

```sql
select ifnull ( '2022-01-30', date '2022-01-31') as a, ifnull (null, date '2022-01-31') as b;
```

结果返回如下：

```sql
     a      |     b
------------+------------
 2022-01-30 | 2022-01-31
(1 row)
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>  
>
> 当expr1不为NULL时返回expr1的值2022-01-30，当expr1为NULL时，返回expr2的值 2022-01-31

**示例2：**在SQL语句中调用IFNULL函数。

1、创建测试表并插入数据

```sql
create table contacts(name varchar(20),bizphone text,homephone text);
insert into contacts values('John','(541) 754-3009',null);
insert into contacts values('Cindy',null,'(541) 754-3010');
insert into contacts values('Sue','(541) 754-3012','(541) 754-3013');
insert into contacts values('Lily',null,null);
```

2、SELECT语句中调用IFNULL函数。

```sql
select name,IFNULL(bizphone,homephone) phone from contacts;
```

结果返回如下：

```sql
 name  |     phone
-------+----------------
 John  | (541) 754-3009
 Cindy | (541) 754-3010
 Sue   | (541) 754-3012
 Lily  |
(4 rows)
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>  
>
> - John的bziphone不为NULL，homephone为NULL，返回bziphone值。
> - Cindy的bziphone为NULL，homephone不为NULL，返回homephone值。
> - Sue的bziphone和homephone都不为NULL，返回bziphone值。
> - Lily的bziphone和homephone都为NULL，返回为空。