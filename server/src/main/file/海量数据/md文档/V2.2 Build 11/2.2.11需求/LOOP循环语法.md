# LOOP循环语法

放入MySQL兼容性-PL/SQL

## **功能描述**

使用LOOP语法控制函数或存储过程中语句进入循环并执行语句。MySQL兼容模式下支持以下功能：

- 支持在循环开始前使用标签名+冒号的语法，使用label标签。
- 支持在循环结束后，使用标签名的语法。
- 支持在循环体内部，通过ITERATE关键字中断本次循环，进入下一次循环。与循环中的continue语法等效。
- ITERATE关键字在使用时不区分大小写。
- 支持在循环体内，使用LEAVE关键字退出循环。

## **语法格式**

```sql
[begin_label:]
LOOP statement_list
END LOOP [end_label]
```

## **参数说明**

- **begin_label**

  开始标签名称。

- **statement_list**

  函数或存储过程中执行的语句。

- **end_label**

  结束标签名称。

## **注意事项**

- label功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- begin_label后面必须跟一个冒号。
- label 必须是字母或者"_"开头，标签中可以含有字母、数字、"\_"，字符数不能超过128。
- label名称不能使用关键字。
- label 标签需要紧贴着"LOOP"关键字，允许换行，但是不允许中间出现别的内容。label目前仅支持与loop语法结合使用。
- begin_label 存在时可以没有 end_label，但是如果存在 end_label，则它必须与 begin_label 一致。

## **示例**

**示例1：**存储过程中使用LOOP。

1、创建数据库，设置兼容性为MySQL。

```sql
CREATE DATABASE my_test DBCOMPATIBILITY 'B';
\c my_test
show sql_compatibility;
```

查看兼容性结果为：

```sql
 sql_compatibility
-------------------

B
(1 row)
```

2、创建测试表。

```sql
create table looptest_tab(a int);
```

3、创建存储过程验证LOOP中使用标签。

```sql
CREATE PROCEDURE loop_proc(a int) 
as 
BEGIN 
label1:
LOOP 
a:=a+1; 
if a<5 then 
insert into looptest_tab values(a);
ITERATE label1; 
END if; 
LEAVE label1; 
END LOOP label1; 
END;
/
```

4、调用存储过程。

```sql
call loop_proc(1);
```

5、查询存储过程执行结果。

```sql
select * from looptest_tab order by a;
```

结果显示如下：

```sql
 a 
---
 2
 3
 4
(3 rows)
```

**示例2：**函数中使用LOOP。

1、创建数据库，设置兼容性为MySQL。

```sql
CREATE DATABASE my_test DBCOMPATIBILITY 'B';
\c my_test
show sql_compatibility;
```

查看兼容性结果为：

```sql
 sql_compatibility
-------------------

B
(1 row)
```

2、创建测试表。

```sql
create table looptest_fun(a int);
```

3、创建函数验证LOOP中使用标签。

```sql
create function loop_auto1(i int)returns int
as
$$
declare
pragma autonomous_transaction;
begin
label1:
loop
i:=i+1;
if i<10 then
insert into looptest_fun values(i);
commit;
iterate label1;
end if;
leave label1;
end loop label1;
return i;
end;
$$
language plpgsql;
```

4、调用函数。

```sql
call loop_auto1(2);
```

5、查询函数执行结果。

```sql
select * from looptest_fun order by a;
```

结果显示如下：

```sql
 a 
---
 3
 4
 5
 6
 7
 8
 9
(7 rows)
```