# 存储过程和函数中支持CONTAINS SQL等语法

## 功能描述

在Vastbase兼容MySQL类型中在存储过程和函数中支持使用SQL DATA Access特性，其中包括CONTAINS SQL、NO SQL、READ SQL DATA、MODIFIES SQL DATA语法。

## 注意事项

- 本特性仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 当未指定SQL DATA Access特性时，默认为CONTAINS SQL。
- 指定多个特性时，排列位于后的特性生效。
- 特性语法小写时可识别。

## 语法格式

- 创建存储过程

```
CREATE [ OR REPLACE ] PROCEDURE procedure_name
	[ [NOT] DETERMINISTIC]
    [ LANGUAGE SQL ]
    [CONTAINS SQL|NO SQL|READ SQL DATA|MODIFIES SQL DATA]
    [ ... ]
	{ IS | AS } 
	plsql_body 
```

- 创建函数

```
CREATE [ OR REPLACE  ] FUNCTION function_name
    RETURN rettype 
    [ [NOT] DETERMINISTIC]
    [ LANGUAGE SQL ]
    [CONTAINS SQL|NO SQL|READ SQL DATA|MODIFIES SQL DATA]
	[...]
    {IS | AS} 
    plsql_body
/
```

## 参数说明

- **procedure_name**

  创建的存储过程名称，可以带有模式名。

  取值范围：字符串，要符合标识符的命名规范。

- **function_name**

  要创建的函数名称（可以用模式修饰）。

  取值范围：字符串，要符合标识符的命名规范。且最多为63个字符。若超过63个字符，数据库会截断并保留前63个字符当做函数名称。

- **[NOT] DETERMINISTIC**

  说明存储过程执行的结果是否正确。

  与LANGUAGE SQL 的顺序可以随意排列。

- **LANGUAGE SQL** 

  用以实现函数的语言的名称。

   与[NOT] DETERMINISTIC 的顺序可以随意排列。

- **CONTAINS SQL**

  表示存储过程或函数中不包含读或写数据的语句。

- **NO SQL**

  表示存储过程或函数中不包含SQL语句。

- **READ SQL DATA**

  表示存储过程或函数中包含读数据的语句，但不包含写数据的语句。

- **MODIFIES SQL DATA**

  表示存储过程或函数中包含写数据的语句

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

**示例1** 函数中含有CONTAINS SQL语法

1、创建数据库，设置兼容性为MySQL。

```
CREATE DATABASE my_test DBCOMPATIBILITY 'B';
\c my_test
show sql_compatibility;
```

查看兼容性结果为：

```
 sql_compatibility
-------------------
B
(1 row)
```

2、创建测试表，并插入数据，查看表内容。

```
create table test1(id int,age int,name varchar2(16)); 

insert into test1 values(1,22,'小王'); 
insert into test1 values(2,23,'小张'); 
insert into test1 values(3,21,'小弓'); 

select * from test1 order by id;
```

查询结果为：

```
 id | age | name
----+-----+------
  1 |  22 | 小王
  2 |  23 | 小张
  3 |  21 | 小弓
(3 rows)
```

3、创建函数。

```
create function fun_test1(age1 int,name1 varchar2)return int 
CONTAINS SQL 
as 
declare 
a1 int; 
begin 
for i in 4..6 loop 
insert into test1 values(i,age1,name1); 
end loop; 
update test1 set age=23 where id=3; 
delete from test1 where id=2; 
select count(*) into a1 from test1; 
return a1; 
end; 
/ 
```

4、调用函数

```
select fun_test1(25,'asdf'); 
```

结果显示为：

```
 fun_test1
-----------
         5
(1 row)
```

5、查看测试表结果。

```
select * from test1 order by id;
```

结果显示为；

```
 id | age | name
----+-----+------
  1 |  22 | 小王
  3 |  23 | 小弓
  4 |  25 | asdf
  5 |  25 | asdf
  6 |  25 | asdf
(5 rows)
```

**示例2** 存储过程中含有NO SQL语法

1、创建数据库，设置兼容性为MySQL。

```
CREATE DATABASE my_test DBCOMPATIBILITY 'B';
\c my_test
show sql_compatibility;
```

查看兼容性结果为：

```
 sql_compatibility
-------------------
B
(1 row)
```

2、创建测试表，并插入数据，查看表内容。

```
create table test2(id int,age int,name varchar2(16)); 

insert into test2 values(1,22,'小王'); 
insert into test2 values(2,23,'小张'); 
insert into test2 values(3,21,'小弓'); 

select * from test2 order by id;
```

查询结果为：

```
 id | age | name
----+-----+------
  1 |  22 | 小王
  2 |  23 | 小张
  3 |  21 | 小弓
(3 rows)
```

3、创建带有NO SQL语法的存储过程。

```
create procedure pro_test2(age1 int,name1 varchar2) 
NOT DETERMINISTIC 
LANGUAGE SQL 
NO SQL 
as 
a1 int; 
begin 
for i in 7..9 loop 
insert into test2 values(i,age1,name1); 
end loop; 
update test2 set name='小张' where id=3; 
delete from test2 where id=1; 
select count(*) into a1 from test2; 
end; 
/ 
```

4、调用存储过程。

```
call pro_test2(25,'asdddf'); 
```

结果显示为：

```
 pro_test2
-----------

(1 row)
```

5、查看测试表结果。

```
select * from test2 order by id;
```

结果显示为：

```
 id | age |  name
----+-----+--------
  2 |  23 | 小张
  3 |  21 | 小张
  7 |  25 | asdddf
  7 |  25 | asdddf
  8 |  25 | asdddf
  8 |  25 | asdddf
  9 |  25 | asdddf
  9 |  25 | asdddf
(8 rows)
```

