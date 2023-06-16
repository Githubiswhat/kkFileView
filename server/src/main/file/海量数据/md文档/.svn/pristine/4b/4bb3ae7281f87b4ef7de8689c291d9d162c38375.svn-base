# CONVERT

## 功能描述

Vastbase G100在SQL Server兼容模式下支持CONVERT函数，用于将表达式由一种数据类型转换为另一种数据类型，返回值为函数中指定数据类型。

## 语法格式

```sql
CONVERT(data_type[(length)], expression[,style ])
```

## 参数说明

- **data_type**

  目标数据类型。

- **length**

  指定目标数据类型长度的可选整数，适用于允许用户指定长度的数据类型。

  默认值：30

- **expression**

  有效表达式。

- **style**

  指定 CONVERT函数将如何转换表达式的整数表达式。

  默认值：0

  > <div align="left"><img src="image/img1.png" style="zoom:75%")</div> 
  >
  > 仅支持style值为0。

## 注意事项

- 该功能仅在数据库兼容模式为SQL Server时能够使用（即创建DB时DBCOMPATIBILITY='MSSQL'），在其他数据库兼容模式下不能使用该特性。
- SQL Server时间类型与Vastbase G100不完全一致，该函数在Vastbase G100中使用时间类型timestamp代替datetime类型。

## 示例

**前置步骤：**创建兼容模式为SQL Server的库db_sqlserver，并进入。

```sql
CREATE DATABASE db_sqlserver DBCOMPATIBILITY='MSSQL';
\c db_sqlserver
```

**示例1：**直接调用CONVERT函数，将timestamp类型转换为varchar类型。

```sql
select convert(varchar,getdate());
```

结果返回如下：

```sql
       getdate
---------------------
 Feb 10 2023 02:40PM
(1 row)
```

**示例2：**在函数和存储过程中调用CONVERT函数。

1、创建函数。

```sql
create function fun_1131634(b1 int,b2 out char(8))return char(8)
as
begin
select (convert(char(8),b1) || 'oo') into b2;
return b2;
end;
/
```

2、调用新建函数验证结果。

```sql
select convert(text,fun_1131634(3));
```

结果返回如下：

```sql
 fun_1131634
-------------
 3oo
(1 row)
```

3、创建存储过程。

```sql
create procedure pro_1131634(b1 int,b2 out char(8))
as
begin
select (convert(char(8),b1) || 'oo') into b2;
raise notice '%',b2;
end;
/
```

4、调用存储过程验证结果。

```sql
select convert(text,pro_1131634(6));
```

结果返回如下：

```sql
NOTICE:  6oo
CONTEXT:  referenced column: pro_1131634
 pro_1131634
-------------
 6oo
(1 row)
```

