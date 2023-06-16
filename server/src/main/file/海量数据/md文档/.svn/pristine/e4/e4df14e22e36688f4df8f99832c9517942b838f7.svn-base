放入兼容性手册-MySQL兼容性-函数

# ISNULL

## 功能描述

Vastbase在MySQL兼容模式下，支持ISNULL函数，用于判读输入的参数是否为null，如果输入参数为null返回true，否则返回false。

## 语法格式

```sql
ISNULL(expr)
```

## 参数说明

**expr**

输入表达式。

## 注意事项

该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建兼容模式为MySQL的库db_mysql，并进入。

```sql
create  database  db_mysql  dbcompatibility  'B';
\c  db_mysql
```

**示例1：**直接调用ISNULL函数。

```sql
SELECT ISNULL(1+1);
```

返回结果为：

```sql
 ?column?
----------
 f
(1 row)
```

**示例2：**在存储过程中使用ISNULL函数。

1、创建存储过程，在存储过程中调用ISNULL函数。

```sql
create or replace procedure proc1 as 
c1 varchar;
c2 varchar;
begin
  SELECT ISNULL(NULL)  into c1;
 SELECT ISNULL('a')  into c2;
  raise info '%', c1;
 raise info '%', c2;
end;
/
```

2、调用存储过程。

```sql
call proc1();
```

返回结果为：

```sql
INFO:  true
INFO:  false
 proc1
-------

(1 row)
```





