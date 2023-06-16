# SYSDATETIME

## 功能描述

Vastbase在SQL Server兼容模式下支持使用SYSDATETIME函数返回计算机的日期和时间，返回值为timestamp类型。

## 语法格式

```sql
SYSDATETIME()
```

## 注意事项

该功能仅在数据库兼容模式为SQL Server时能够使用（即创建DB时DBCOMPATIBILITY='MSSQL'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建兼容模式为SQLServer的库db_sqlserver，并进入。

```sql
CREATE DATABASE db_sqlserver DBCOMPATIBILITY='MSSQL'；
\c db_sqlserver
```

**示例1：**直接调用SYSDATETIME函数。

```sql
select sysdatetime();
```

返回结果如下：

```sql
        sysdatetime
----------------------------
 2023-02-06 07:44:18.388343
(1 row)
```

**示例2：**SYSDATETIME函数作为关键字在表中调用。

1、创建测试表。

```sql
CREATE TABLE products(
product_id INT PRIMARY KEY,
product_name VARCHAR(255) NOT NULL,
sysdatetime date,
sale_time date,
unit_price DEC(10,2),
discounted_price DEC(10,2),
CHECK(unit_price > 0),
CHECK(discounted_price > 0),
CHECK(sysdatetime < sysdatetime())
);
```

2、调用SYSDATETIME函数插入测试数据。

```sql
insert into products values(1,'page','2022-10-09',sysdatetime(),3.0,2.5);
insert into products values(2,'pen','2022-11-16',sysdatetime(),10.0,8.4);
```

3、查询数据进行验证。

```sql
select * from products;
```

结果返回如下：

```sql
 product_id | product_name | sysdatetime | sale_time  | unit_price | discounted_price
------------+--------------+-------------+------------+------------+------------------
          1 | page         | 2022-10-09  | 2023-02-06 |          3 |              2.5
          2 | pen          | 2022-11-16  | 2023-02-06 |         10 |              8.4
(2 rows)
```

