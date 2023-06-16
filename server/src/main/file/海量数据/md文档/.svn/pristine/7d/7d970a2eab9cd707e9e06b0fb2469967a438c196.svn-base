# 存储过程和函数中支持[NOT]DETERMINISTIC语法

## 功能描述

在Vastbase兼容MySQL类型时，支持添加[NOT] DETERMINISTIC用法。

 [NOT] DETERMINISTIC 说明存储过程或函数执行的结果是否正确。

- DETERMINISTIC表示结果确定。每次执行存储过程时，相同的输入会得到相同的输出。
- NOT DETERMINISTIC表示结果不确定，相同的输入可能得到不同的输出。

## 注意事项

该特性仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 语法格式

- 创建存储过程

```sql
CREATE [ OR REPLACE ] PROCEDURE procedure_name
	[ [NOT] DETERMINISTIC]
    [ LANGUAGE SQL ]
    [ ... ]
	{ IS | AS } 
	plsql_body 
```

- 创建函数

```sql
CREATE [ OR REPLACE  ] FUNCTION function_name
    RETURN rettype 
    [ [NOT] DETERMINISTIC]
    [ LANGUAGE SQL ]
	[...]
    {IS | AS} 
    plsql_body
/
```

## 参数说明

-  **procedure_name**

  创建的存储过程名称，可以带有模式名。

  取值范围：字符串，要符合标识符的命名规范。

- **function_name**

  要创建的函数名称（可以用模式修饰）。

  取值范围：字符串，要符合标识符的命名规范。且最多为63个字符。若超过63个字符，数据库会截断并保留前63个字符当做函数名称。

- **LANGUAGE SQL** 

  用以实现函数的语言的名称。

   与[NOT] DETERMINISTIC 的顺序可以随意排列。

- **rettype** 

  函数返回值的数据类型。

  如果存在OUT或INOUT参数，可以省略RETURNS子句。如果存在，该子句必须和输出参数所表示的结果类型一致：如果有多个输出参数，则为RECORD，否则与单个输出参数的类型相同。

  SETOF修饰词表示该函数将返回一个集合，而不是单独一项。

  可以使用%TYPE或%ROWTYPE间接引用类型。详细可参考存储过程章节[定义变量](../开发者指南/定义变量.md)。

- **plsql_body**

  PL/SQL存储过程体。

  > <div align="left"><img src="image/image1.png" style="zoom:25%"></div>
  >
  > 当在存储过程体中进行创建用户等涉及用户密码相关操作时，系统表及csv日志中会记录密码的明文。因此不建议用户在存储过程体中进行涉及用户密码的相关操作。

## 示例

**示例1** 创建带NOT DETERMINISTIC语法的函数

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

2、创建函数。

```sql
create function fun_deter(c1 int,c2 int)return varchar2 
NOT DETERMINISTIC 
as 
a1 varchar2(36); 
begin 
if c1>c2 then 
a1:='c1 > c2'; 
else 
if c1<c2 then 
a1:='c1 < c2'; 
else 
a1:='c1 = c2'; 
end if; 
end if; 
return a1; 
end; 
/ 
```

3、测试函数结果。

```sql
select fun_deter(5,3); 
select fun_deter(2,3); 
select fun_deter(3,3); 
```

结果显示为：

```sql
 fun_deter
-----------
 c1 > c2
(1 row)

 fun_deter
-----------
 c1 < c2
(1 row)

 fun_deter
-----------
 c1 = c2
(1 row)
```

**示例2** 创建带NOT DETERMINISTIC语法的存储过程

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

2、创建存储过程。

```sql
create procedure pro_deter(c1 int,c2 int) 
NOT DETERMINISTIC 
as 
begin 
if c1>c2 then 
raise notice 'c1 > c2'; 
else 
if c1<c2 then 
raise notice 'c1 < c2'; 
else 
raise notice 'c1 = c2'; 
end if; 
end if; 
end; 
/ 
```

3、调用存储过程。

```sql
call pro_deter(5,3); 
call pro_deter(2,3); 
call pro_deter(3,3);
```

返回结果为：

```sql
NOTICE:  c1 > c2
 pro_deter
-----------

(1 row)


NOTICE:  c1 < c2
 pro_deter
-----------

(1 row)


NOTICE:  c1 = c2
 pro_deter
-----------

(1 row)

```

