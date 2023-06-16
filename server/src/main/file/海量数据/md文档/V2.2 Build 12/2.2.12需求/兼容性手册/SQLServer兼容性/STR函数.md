# STR

## 功能描述

Vastbase G100在SQL Server兼容模式下支持STR函数，用于返回数字数据转换来的字符数据，具有指定长度和十进制精度。返回值为varchar类型，转换所得的字符串。

## 语法格式

```sql
STR(float_expression[,length [,decimal]])
```

## 参数说明

- **float_expression**

  带小数点的近似数字，float数据类型表达式。

- **length**

  总长度，包括小数点、符号、数字以及空格。

  默认值：10

- **decimal**

  小数点后的位数。

  默认值：0

## 注意事项

该功能仅在数据库兼容模式为SQL Server时能够使用（即创建DB时DBCOMPATIBILITY='MSSQL'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建兼容模式为SQL Server的库db_sqlserver，并进入。

```sql
CREATE DATABASE db_sqlserver DBCOMPATIBILITY='MSSQL';
\c db_sqlserver
```

**示例1：**直接调用STR函数。

```sql
select str(123.45,6,1);
```

结果返回如下：

```sql
   str
---------
   123.5
(1 row)
```

**示例2：**对行存表使用DML语句调用STR函数。

1、创建测试表并插入数据。

```sql
CREATE table test_tb_1132253(id int, income varchar);
insert into test_tb_1132253 values(1, '12.3462754');
insert into test_tb_1132253 values(2, '222.3462754');
insert into test_tb_1132253 values(3, '333.3462754');
```

2、SELECT语句中调用STR函数处理返回结果。

```sql
select id, STR(income, 6, 2) from test_tb_1132253;
```

结果返回如下：

```sql
 id |   str
----+---------
  1 |   12.35
  2 |  222.35
  3 |  333.35
(3 rows)
```

3、UPDATE语句中调用STR函数处理数据进行更新。

```sql
update test_tb_1132253 set income = STR(777.889741, 10, 4) where id = 2;
select * from test_tb_1132253 order by id;
```

结果返回如下：

```sql
 id |   income
----+-------------
  1 | 12.3462754
  2 |    777.8897
  3 | 333.3462754
(3 rows)
```

需补充delete示例  目前vastbase  DELETE语句中调用STR有问题



