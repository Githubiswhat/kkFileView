# LEN

## 功能描述

Vastbase在SQL Server兼容模式下支持使用LEN函数返回字符串的字符数，不包含尾随空格。

## 语法格式

```sql
LEN(input_string)
```

## 参数说明

**input_string**

描述：一个字符串或字符串表达式，其中包含要计算长度的序列。

取值范围：文字字符串、字符串表达式、字符或二进制数据的列。

## 注意事项

该功能仅在数据库兼容模式为SQL Server时能够使用（即创建DB时DBCOMPATIBILITY='MSSQL'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建兼容模式为SQLServer的库db_sqlserver，并进入。

```sql
CREATE DATABASE db_sqlserver DBCOMPATIBILITY='MSSQL'；
\c db_sqlserver
```

**示例1：**直接调用LEN函数。

```sql
select len('123');
```

结果返回如下：

```sql
 len
-----
   3
(1 row)
```

**示例2：**函数LEN出现在目标列与其他函数套用。

1、创建测试表并插入数据。

```sql
create table tb_1116312(
ID NUMBER(20),
id1 varchar(10),
id2 char(10),
id3 char(30)
) ;

insert into tb_1116312 values(generate_series(1, 1000), generate_series(1, 1000)||'A ', generate_series(1, 1000)||' A ',generate_series(1, 2000)||' AB ');
insert into tb_1116312 values(null);
insert into tb_1116312 values(null,'','','');
```

2、查询列"id3"最大值的长度。

```sql
select len(max(id3)) from tb_1116312 ;
```

结果返回如下：

```sql
 len
-----
   4
(1 row)
```

3、查询列"id3"长度的最大值。

```sql
select max(len(id3)) from tb_1116312 ;
```

结果返回如下：

```sql
 max
-----
   7
(1 row)
```



