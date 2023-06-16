# LOOP循环语法

**功能描述**

控制函数或存储过程中语句进入循环并执行语句。MySQL兼容模式下支持以下功能：

- 支持在循环开始前使用标签名+冒号的语法，使用label标签。
- 支持在循环结束后，使用标签名的语法。
- 支持在循环体内部，通过ITERATE关键字中断本次循环，进入下一次循环。与在循环中continue等效。
- 支持在循环体内，使用LEAVE关键字退出循环。

**语法格式**

```
[begin_label:]
LOOP statement_list
END LOOP [end_label]
```

**参数说明**

begin_label：开始标签。

statement_list：函数或存储过程中执行的语句。

end_label：结束标签。

**注意事项**

标签功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

**示例**

- 示例一：存储过程中使用LOOP。

1、创建测试表。

```
create table looptest_tab(a int);
```

2、创建存储过程验证LOOP中使用标签。

```
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

3、调用存储过程。

```
call loop_proc(1);
```

4、查询存储过程执行结果。

```
select * from looptest_tab order by a;
```

结果显示如下：

```
 a 
---
 2
 3
 4
(3 rows)
```

- 示例二：函数中使用LOOP。

1、创建测试表。

```
create table looptest_fun(a int);
```

2、创建函数验证LOOP中使用标签。

```
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

3、调用函数。

```
call loop_auto1(2);
```

4、查询函数执行结果。

```
select * from looptest_fun order by a;
```

结果显示如下：

```
 a 
---
 2
 3
 3
 4
 4
 5
 6
 7
 8
 9
(10 rows)
```

