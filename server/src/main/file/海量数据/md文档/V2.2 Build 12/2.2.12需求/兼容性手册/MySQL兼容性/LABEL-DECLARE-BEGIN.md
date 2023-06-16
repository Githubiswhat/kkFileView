# LABEL: [DECLARE]BEGIN

## 功能描述

Vastbase G100在MySQL兼容模式下，支持`BEGIN...END`代码块的label标签可以通过":"的形式设置。

## 语法格式

```sql
[BEGIN_LABEL:]
[DECLARE] BEGIN statement_list
END [END_LABEL]
```

## 参数说明

- **BEGIN_LABEL**

  开始标签名称。

- **statement_list**

  代码块。

- **END_LABEL**

  结束标签名称。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 支持在代码块内，使用LEAVE 、EXIT 关键字退出代码块。
- 可有相同标签名的代码块嵌套，`EXIT LABEL`名退出时退出的是其外层最近的代码块。
- 代码块标签必须放在 DECLARE 的前面。
- "LABEL:"与 BEGIN 或者 DECLARE 之间要有空格。

## 示例

**前置步骤：**创建并切换至兼容模式为MySQL的数据库db_mysql。

```sql
CREATE DATABASE db_mysql dbcompatibility='B';    
\c db_mysql
```

**示例1：**在自定义函数中使用。

1、创建函数funct。

```sql
create or replace function funct(v1 int,v2 int) return int as
begin
a3:
declare con int:=10;
begin
con=v1+v2+con;
return con;
end;
end;
/
```

2、调用函数。

```sql
call funct(10,-2);
```

结果返回如下：

```sql
 funct
-------
    18
(1 row)
```

**示例2：**在存储过程中使用。

1、创建存储过程proc3。

```sql
create or replace procedure proc3(v1 int,v2 int, v3 out int) as
begin
a3:
declare con int:=10;
begin
raise info 'v1+v2和的10倍为';
v3=(v1+v2)*con;
end;
end;
/
```

2、调用存储过程。

```sql
call proc3(1,2,'');
```

结果返回如下：

```sql
INFO:  v1+v2和的10倍为
 v3
----
 30
(1 row)
```

**示例3：**在匿名块中使用。

创建匿名块。

```sql
begin
标签: declare
a text:='text:匿名块';
begin
raise info '%', a;
end;
end;
/
```

结果返回如下：

```sql
INFO:  text:匿名块
ANONYMOUS BLOCK EXECUTE
```

