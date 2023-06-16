# DATEDIFF

## 功能描述

Vastbase G100在MySQL兼容模式下支持DATEDIFF函数，用于返回两个日期之间的天数。函数返回`date1 - date2`的计算结果，date1和date2两个参数必须是有效的日期或日期时间值。

如果输入参数传递的是日期时间值，则DATEDIFF函数仅将日期部分用于计算，并忽略时间部分。

## 语法格式

```sql
DATEDIFF(date1,date2)
```

## 参数说明

- **date1**

  输入的日期时间1。

- **date2**

  输入的日期时间2。

## 注意事项

该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建兼容模式为MySQL的库db_mysql，并进入。

```
create  database  db_mysql  dbcompatibility  'B';
\c  db_mysql
```

**示例1：**直接调用DATEDIFF函数。

```sql
select datediff(date'2020-2-29',date'2020-3-29');
```

返回结果分别为：

```SQL
 datediff
----------
      -29
(1 row)
```

**示例2：**在存储过程中使用DATEDIFF函数。

1、设置serveroutput 为on（允许将dbms_output.put_line的输出信息输出至vsql的命令界面的屏幕上）。

```sql
set serveroutput on;
```

2、创建存储过程。

```sql
create or replace procedure p_1131144 (col1 text,col2 text)
as
begin
if datediff(col1,col2) < 0
then DBMS_OUTPUT.PUT_LINE(col2);
ELSIF datediff(col1,col2) = 0
then DBMS_OUTPUT.PUT_LINE('相等');
else DBMS_OUTPUT.PUT_LINE(col1);
end if;
end;
/
```

2、调用存储过程（当两个输入参数日期值相等时）。

```sql
call p_1131144('2020-1-1','2020-01-01');
```

返回结果为：

```sql
相等
p_1131144
---------

(1 row)
```

3、调用存储过程（当输入的第一个日期值小于第二个日期值，返回第二个日期值）。

```sql
call p_1131144('2021-1-1','2022-1-1');
```

返回结果为：

```sql
2022-1-1
 p_1131144
-----------

(1 row)
```

4、调用存储过程（当输入的第一个日期值大于第二个日期值，返回第一个日期值）。

```sql
call p_1131144('2020-1-1','2019-1-1');
```

返回结果为：

```sql
2020-1-1
 p_1131144
-----------

(1 row)
```