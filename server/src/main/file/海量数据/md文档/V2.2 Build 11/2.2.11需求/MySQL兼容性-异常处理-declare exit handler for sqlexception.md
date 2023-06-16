# MySQL兼容性-异常处理-declare exit handler for sqlexception

## 功能描述

在MySQL兼容模式下，Vastbase G100支持使用declare handler语句来处理函数或存储过程中的异常。

## 语法格式

```sql
DECLARE handler_action HANDLER
	FOR condition_value
	statement
```

## 参数说明

- **handler_action：**出现异常后采取的动作。当前仅支持设为EXIT，表明当前程序终止（即退出当前declare所在的begin...end）。
- **condition_value：**说明handler被何种条件触发。当前仅支持设为SQLEXCEPTION，即用SQLEXCEPTION捕获异常。
- **statement：**当出现异常时，终止当前执行语句，跳转到statement的语句执行。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 仅Vastbase G100 2.2 Build 11及以后版本支持此功能。
- 该语法仅在函数或存储过程中支持。

## 示例

1、创建并切换到兼容模式为B的数据库下。

```sql
create database db_1103118 dbcompatibility 'B';
\c db_1103118
```

2、创建函数，包含异常情况处理。

```sql
create or replace function func(a int,b int) return int
as
begin
declare exit handler for sqlexception
begin
set @info:='error';
raise info '%',@info;
return a;
end;
set @info := 'begin';
raise info '%',@info;
b:=a/b;
set @info := 'end';
raise info '%',@info;
return b;
end;
/
```

3、调用函数。

```sql
call func(15,0);
```

触发异常情况处理，返回结果为：

```sql
INFO：begin
INFO：error
func
-----
15
(1 row) 
```

