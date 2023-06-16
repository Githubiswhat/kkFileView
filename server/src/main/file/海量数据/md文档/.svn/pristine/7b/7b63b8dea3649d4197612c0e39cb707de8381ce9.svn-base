# 存储过程与函数中支持LANGUAGE SQL语法

## 功能描述

在Vastbase兼容MySQL类型时，在过程和函数中如果使用LANGUAGE SQL用法，则在plsql_body 部分除了出现SQL语句时能正常执行以外，出现逻辑代码如变量赋值、逻辑控制、异常处理等行为时也可以正常执行。

## 注意事项

本特性仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

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

- **[NOT]DETERMINISTIC**

  说明存储过程执行的结果是否正确。

  与LANGUAGE SQL的顺序可以随意排列。

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

**示例1** 函数体内含SQL语句

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

2、创建测试表并插入数据 ，查看表内容。

```sql
create table test1(id int,age int,name varchar2(16)); 

insert into test1 values(1,22,'小王'); 
insert into test1 values(2,23,'小张'); 
insert into test1 values(3,21,'小弓'); 

select * from test1 order by id;
```

结果显示为：

```sql
 id | age | name
----+-----+------
  1 |  22 | 小王
  2 |  23 | 小张
  3 |  21 | 小弓
(3 rows)
```

3、创建带有SQL语句的函数。

```sql
create function func_test1(id1 int,age1 int,name1 varchar2) return bigint
LANGUAGE SQL
AS
a1 int;
begin
insert into test1 values(id1,age1,name1);
update test1 set age=23 where id=3;
delete from test1 where id=2;
select count(*) into a1 from test1;
return a1;
end;
/
```

4、调用函数。

```sql
select func_test1(6,25,'小郝'); 
```

结果显示为：

```sql
 func_test1
------------
          3
(1 row)
```

5、查询测试表内容。

```sql
select * from test1 order by id;
```

结果显示为：

```sql
 id | age | name
----+-----+------
  1 |  22 | 小王
  3 |  23 | 小弓
  6 |  25 | 小郝
(3 rows)
```

**示例2** 存储过程体内含有逻辑控制语句

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

2、创建测试表并插入数据，查看表内容。

```sql
create table test2(id int primary key, 
name1 varchar2(20), 
name2 varchar2(25), 
id1 int);  

INSERT INTO test2 VALUES (1, 'Steven', 'King',30); 
INSERT INTO test2 VALUES (2, 'Neena', 'Kochhar',30); 
INSERT INTO test2 VALUES (3, 'Lex', 'De Haan',20); 
INSERT INTO test2 VALUES (4, 'Alexander', 'Hunold',30); 
INSERT INTO test2 VALUES (5, 'Bruce', 'Ernst',20); 
INSERT INTO test2 VALUES (6, 'David', 'Austin',10); 

select * from test2 order by id;
```

结果显示为：

```sql
 id |   name1   |  name2  | id1
----+-----------+---------+-----
  1 | Steven    | King    |  30
  2 | Neena     | Kochhar |  30
  3 | Lex       | De Haan |  20
  4 | Alexander | Hunold  |  30
  5 | Bruce     | Ernst   |  20
  6 | David     | Austin  |  10
(6 rows)
```

3、创建带有逻辑控制语句的存储过程。

```sql
create procedure pro_test2(n int,col1 in int)
LANGUAGE SQL 
AS 
DECLARE 
a1 int; 
CURSOR cur is select id from test2 where id1=col1 order by 1; 
BEGIN 
open cur; 
loop 
FETCH cur INTO a1; 
exit when cur%NOTFOUND; 
raise notice '%',a1; 
end loop; 
CLOSE cur; 
if n>6 then 
insert into test2 value(n,'kk','ff',20); 
else 
delete from test2 where id=n; 
end if;
end 
/ 
```

4、调用存储过程。

```sql
call pro_test2(7,3);
```

结果显示为：

```sql
 pro_test2
-----------

(1 row)
```

5、查看测试表结果。

```sql
select * from test2 order by id;
```

结果显示为：

```sql
 id |   name1   |  name2  | id1
----+-----------+---------+-----
  1 | Steven    | King    |  30
  2 | Neena     | Kochhar |  30
  3 | Lex       | De Haan |  20
  4 | Alexander | Hunold  |  30
  5 | Bruce     | Ernst   |  20
  6 | David     | Austin  |  10
  7 | kk        | ff      |  20
(7 rows)
```

