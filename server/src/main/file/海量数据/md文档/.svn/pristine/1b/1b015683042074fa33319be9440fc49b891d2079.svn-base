# MySQL兼容性-存储过程和函数支持声明SQLSTATE、SQLERRM、SQLCODE变量

## 功能描述

在MySQL兼容模式下，Vastbase G100支持在存储过程和函数中自定义声明SQLSTATE、SQLERRM、SQLCODE变量。当出现异常情况（Exception）后，将变量值作为信息输出到屏幕上。

## 语法格式

```sql
CREATE [ OR REPLACE ] { PROCEDURE | FUNCTION } object_name [options]
{ IS | AS } 
DECLARE
SQLCODE data_type := value;
SQLERRM data_type := value;
{ declare_statements }
BEGIN
{ execution_statements }
EXCEPTION
WHEN DIVISION_BY_ZERO THEN
RAISE NOTICE 'SQLSTATE = %,SQLCODE = %，SQLERRM=%',SQLSTATE,SQLCODE,SQLERRM;
END;
/
```

## 参数说明

- **object_name：**创建的存储过程/函数的名字。
- **data_type：**为SQLCODE和SQLERRM声明的数据类型。
- **value：**为SQLCODE和SQLERRM赋的值，其取值可以为任意数据类型。
- **EXCEPTION：**异常处理情况。
- **SQLSTATE：**是一个五字符的数组组成的输出字符串。用来反映调用中出现的问题。
- **SQLCODE：**普通变量，用于反映异常情况时的错误信息，用户可自定义其取值（value）。
- **SQLERRM：**普通变量，用于反映异常情况时的错误信息，用户可自定义其取值（value）。

> <div align="left"><img src="image/image1.png" style="zoom:25%"></div>
>
> - 如果用户没有声明SQLCODE、SQLERRM作为普通变量，那么这两个参数内部都是有值的，其值分别为系统变量returned_sqlstate和message_text的对应值。（系统变量returned_sqlstate和message_text分别反映了数据库操作的返回码和错误信息。）
>
> - 如果用户声明了SQLCODE、SQLERRM作为普通变量，不会影响系统变量returned_sqlstate和message_text的值。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 仅Vastbase G100 V2.2 Build11及以后版本支持此功能。

## 示例

1、创建并切换至兼容模式为MySQL的数据库下。

```sql
CREATE DATABASE db_mysql DBCOMPATIBILITY 'B';
\c db_mysql
```

2、打开serveroutput参数。（使信息从存储过程传输回应用程序，输出在屏幕上。）

```sql
set serveroutput = on;
```

3、创建包含异常情况处理的存储过程testpro。

```sql
create or replace procedure testpro
as
declare
a int;
SQLCODE char := 'T';
SQLERRM char := 'T';
begin
a := 1/0;
EXCEPTION
WHEN DIVISION_BY_ZERO THEN
RAISE NOTICE 'SQLSTATE = %,SQLCODE = %，SQLERRM=%',SQLSTATE,SQLCODE,SQLERRM;
END;
/
```

4、调用存储过程。

```sql
call testpro();
```

返回结果为：

```sql
NOTICE:  SQLSTATE = 22012,SQLCODE = T,SQLERRM = T
 testpro
------------

(1 row)
```

