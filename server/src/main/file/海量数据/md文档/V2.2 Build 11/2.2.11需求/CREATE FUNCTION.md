# CREATE FUNCTION

放入MySQL兼容性-SQL语法

## 功能描述

创建新的函数。在MySQL兼容模式下，支持以下语法：

- SQL DATA Access
  - CONTAINS SQL
  - NO SQL
  - READS SQL DATA
  - MODIFIES SQL DATA

- LANGUAGE SQL
- SQL SECURITY

## 语法格式

```sql
CREATE [ OR REPLACE  ] [DEFINER = usr] FUNCTION function_name
    ( [  { argname [ argmode  ] argtype [  { DEFAULT  | :=  | =  } expression  ]}  [, ...]  ] )
    [ RETURNS rettype [ DETERMINISTIC | PIPELINED ]
        | RETURNS TABLE (  { column_name column_type  }  [, ...] )]
    LANGUAGE lang_name
    [
        {IMMUTABLE  | STABLE  | VOLATILE}
        | {SHIPPABLE | NOT SHIPPABLE}
        | [ NOT  ] LEAKPROOF
        | WINDOW
        | {CALLED ON NULL INPUT | RETURNS NULL ON NULL INPUT | STRICT}
        | {[ EXTERNAL  ] SECURITY INVOKER  | [ EXTERNAL  ] SECURITY DEFINER | AUTHID DEFINER  | AUTHID CURRENT_USER}
        | SQL SECURITY { DEFAULT | INVOKER }
        | {FENCED | NOT FENCED}
        | {PACKAGE}
        | COST execution_cost
        | ROWS result_rows
        | SET configuration_parameter { {TO | =} value | FROM CURRENT }
        | [NOT] DETERMINISTIC
        | { CONTAINS SQL | NO SQL | READS SQL DATA | MODIFIES SQL DATA }
    ] [...]
    {
        AS 'definition'
        | AS 'obj_file', 'link_symbol'
    }

```

```sql
CREATE [ OR REPLACE  ] [DEFINER = usr] FUNCTION function_name
    ( [  { argname [ argmode  ] argtype [  { DEFAULT | := | =  } expression  ] }  [, ...]  ] )
    RETURN rettype [ DETERMINISTIC | PIPELINED ]
    [
        {IMMUTABLE  | STABLE  | VOLATILE }
        | {SHIPPABLE | NOT SHIPPABLE}
        | {PACKAGE}
        | [ NOT  ] LEAKPROOF
        | {CALLED ON NULL INPUT  | RETURNS NULL ON NULL INPUT  | STRICT }
        | {[ EXTERNAL  ] SECURITY INVOKER  | [ EXTERNAL  ] SECURITY DEFINER | | AUTHID DEFINER  | AUTHID CURRENT_USER}
        | SQL SECURITY { DEFAULT | INVOKER }
        | COST execution_cost
        | ROWS result_rows
        | SET configuration_parameter { {TO | =} value | FROM CURRENT }
        | LANGUAGE SQL
        | [NOT] DETERMINISTIC
        | { CONTAINS SQL | NO SQL | READS SQL DATA | MODIFIES SQL DATA }
     ][...]
     {
        IS | AS
     } plsql_body
/
```

## 参数说明

- **function_name**

  要创建的函数名称（可以用模式修饰）。

  取值范围：字符串，要符合标识符的命名规范。且最多为63个字符。若超过63个字符，数据库会截断并保留前63个字符当做函数名称。

- **argname**

  函数参数的名称。

  取值范围：字符串，要符合标识符的命名规范。且最多为63个字符。若超过63个字符，数据库会截断并保留前63个字符当做函数参数名称。

- **argmode**

   函数参数的模式。

   取值范围：IN、OUT、INOUT或VARIADIC。缺省值是IN。并且OUT和INOUT模式的参数不能用在RETURNS TABLE的函数定义中。

   > <div align="left"><img src="image/image1.png" style="zoom:25%")</div>   
   >
   >  VARIADIC用于声明数组类型的参数。

-  **argtype**

   函数参数的类型。可以使用%TYPE或%ROWTYPE间接引用变量或表的类型，详细可参考函数章节[定义变量](../开发者指南/定义变量.md)。

-  **expression**

   参数的默认表达式。

-  **rettype**

   函数返回值的数据类型。

   如果存在OUT或INOUT参数，可以省略RETURNS子句。如果存在，该子句必须和输出参数所表示的结果类型一致；如果有多个输出参数，则为RECORD，否则与单个输出参数的类型相同。

   SETOF修饰词表示该函数将返回一个集合，而不是单独一项。

   与argtype相同，同样可以使用%TYPE或%ROWTYPE间接引用类型。

-  **column_name**

   字段名称。

-  **column_type**

   字段类型。

-  **definition**

   一个定义函数的字符串常量，含义取决于语言。它可以是一个内部函数名称、一个指向某个目标文件的路径、一个SQL查询、一个过程语言文本。

-  **DETERMINISTIC**

   SQL语法兼容接口，未实现功能，不推荐使用。

-  **LANGUAGE lang_name**

   用以实现函数的语言的名称。可以是SQL、internal或者是用户定义的过程语言名称。为了保证向下兼容，该名称可以用单引号包围。若采用单引号，则引号内必须为大写。

-  **WINDOW**

   表示该函数是窗口函数。替换函数定义时不能改变WINDOW属性。

   > <div align="left"><img src="image/image2.png" style="zoom:20%")</div>    
   >
   >  自定义窗口函数只支持LANGUAGE是internal，并且引用的内部函数必须是窗口函数。

-  **IMMUTABLE**

   表示该函数在给出同样的参数值时总是返回同样的结果。

-  **STABLE**

   表示该函数不能修改数据库，对相同参数值，在同一次表扫描里，该函数的返回值不变，但是返回值可能在不同SQL语句之间变化。

-  **VOLATILE**

   表示该函数值可以在一次表扫描内改变，因此不会做任何优化。

-  **SHIPPABLE**|**NOT SHIPPABLE**

   表示该函数是否可以下推执行。预留接口，不推荐使用。

-  **FENCED**|**NOT FENCED**

   声明用户定义的C函数是在保护模式还是非保护模式下执行。预留接口，不推荐使用。

-  **PACKAGE**

   表示该函数是否支持重载。PostgreSQL风格的函数本身就支持重载，此参数主要是针对其它风格的函数。

   - 不允许package函数和非package函数重载或者替换。
   - package函数不支持VARIADIC类型的参数。
   - 不允许修改函数的package属性。

-  **LEAKPROOF**

   指出该函数的参数只包括返回值。LEAKPROOF只能由系统管理员设置。

-  **CALLED ON NULL INPUT**

   表明该函数的某些参数是NULL的时候可以按照正常的方式调用。该参数可以省略。

-  **RETURNS NULL ON NULL INPUT**

   **STRICT**

   STRICT用于指定如果函数的某个参数是NULL，此函数总是返回NULL。如果声明了这个参数，当有NULL值参数时该函数不会被执行；而只是自动返回一个NULL结果。

   RETURNS NULL ON NULL INPUT和STRICT的功能相同。

-  **EXTERNAL**

   目的是和SQL兼容，是可选的，这个特性适合于所有函数，而不仅是外部函数。

-  **SECURITY INVOKER**

   **AUTHID CURRENT_USER**

   表明该函数将带着调用它的用户的权限执行。该参数可以省略。

   SECURITY INVOKER和AUTHID CURRENT_USER的功能相同。

-  **SECURITY DEFINER**

   **AUTHID DEFINER**

   声明该函数将以创建它的用户的权限执行。

   AUTHID DEFINER和SECURITY DEFINER的功能相同。

-  **COST execution_cost**

   用来估计函数的执行成本。

   execution_cost以cpu_operator_cost为单位。

   取值范围：正数

-  **ROWS result_rows**

   当函数返回值是一个集合时，估计函数返回的行数。

   取值范围：正数，默认值是1000行。

-  **configuration_parameter**

   - **value**

     把指定的数据库会话参数值设置为给定的值。如果value是DEFAULT或者RESET，则在新的会话中使用系统的缺省设置。OFF关闭设置。

     取值范围：字符串

     - DEFAULT
     - OFF
     - RESET

     指定默认值。

   - **from current**

     取当前会话中的值设置为configuration_parameter的值。

- **LANGUAGE SQL** 

  用以实现函数的语言的名称。在函数中如果使用LANGUAGE SQL用法，则在plsql_body 部分除了出现SQL语句时能正常执行以外，出现逻辑代码如变量赋值、逻辑控制、异常处理等行为时也可以正常执行。

   与[NOT] DETERMINISTIC 的顺序可以随意排列。

-  **[NOT] DETERMINISTIC** 

  说明函数执行的结果是否正确。

  - DETERMINISTIC：表示结果确定。每次执行函数时，相同的输入会得到相同的输出。
  - NOT DETERMINISTIC：表示结果不确定，相同的输入可能得到不同的输出。

- **CONTAINS SQL**

  表示函数中不包含读或写数据的语句。

- **NO SQL**

  表示函数中不包含SQL语句。

- **READ SQL DATA**

  表示函数中包含读数据的语句，但不包含写数据的语句。

- **MODIFIES SQL DATA**

  表示函数中包含写数据的语句。

- **SQL SECURITY**

  表示函数的权限。

  默认值为：SQL SECURITY DEFINER。

  - SECURITY INVOKER：表明该函数将带着调用它的用户的权限执行。该参数可省略。
  - SECURITY DEFINER：声明该函数将以创建它的用户的权限执行。

- **plsql_body**

  PL/SQL存储过程体。

  > <div align="left"><img src="image/image1.png" style="zoom:25%"></div>
  >
  > 当在函数体中进行创建用户等涉及用户密码相关操作时，系统表及csv日志中会记录密码的明文。因此不建议用户在函数体中进行涉及用户密码的相关操作。

## 注意事项

- 功能描述中的特性仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 当未指定SQL DATA Access特性时，默认为CONTAINS SQL。
- 指定多个特性时，排列位于后的特性生效。
- 特性语法小写时可识别。
- DEFINER与SQL SECURITY INVOKER同时定义时，SQL SECURITY INVOKER优先级更高，即在执行该函数时，使用”调用者“的用户权限判断其是否有访问该函数中的具体对象的权限。
- DEFINER与SQL SECURITY DEFINER同时定义时，使用“定义者”的用户权限判断其是否有访问该函数中的具体对象的权限。

## 示例

**示例1：**创建含有NO SQL 语法的函数。

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

2、创建测试表，并插入数据，查看表内容。

```sql
create table test1(id int,age int,name varchar2(16)); 

insert into test1 values(1,22,'小王'); 
insert into test1 values(2,23,'小张'); 
insert into test1 values(3,21,'小弓'); 

select * from test1 order by id;
```

查询结果为：

```sql
 id | age | name
----+-----+------
  1 |  22 | 小王
  2 |  23 | 小张
  3 |  21 | 小弓
(3 rows)
```

3、创建带有NO SQL语法的函数。

```sql
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

4、调用函数。

```sql
select fun_test1(25,'asdf'); 
```

结果显示为：

```sql
 fun_test1
-----------
         5
(1 row)
```

5、查看测试表结果。

```sql
select * from test1 order by id;
```

结果显示为：

```sql
 id | age | name
----+-----+------
  1 |  22 | 小王
  3 |  23 | 小弓
  4 |  25 | asdf
  5 |  25 | asdf
  6 |  25 | asdf
(5 rows)
```

**示例2：** 创建带NOT DETERMINISTIC语法的函数

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

3、调用函数。

```sql
select fun_deter(5,3); 
select fun_deter(2,3); 
select fun_deter(3,3); 
```

返回结果为：

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

**示例3：** 创建带LANGUAGE SQL的函数。

1、创建兼容MySQL的数据库my_test。

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

5、查看测试表结果。

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

**示例4：**创建带有SQL SECURITY的函数。

1、创建兼容MySQL的数据库my_test；

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

2、创建测试表security_test。

```sql
CREATE TABLE security_test(id int DEFAULT NULL ,id1 int DEFAULT NULL);
```

3、创建测试用户b。

```sql
CREATE USER b password 'Bb@123456';
```

4、给b授予表security_test的所有权限。

```sql
grant all privileges on security_test to b;
```

5、创建函数，定义DEFINER为b 同时使用SQL SECURITY INVOKER。

```sql
create definer=b function fun_test1(n int,id1 int) return int
SQL SECURITY INVOKER
as
c1 int;
c2 int;
begin
for i in 1..n loop
c2:='111' || i;
insert into security_test values(i,c2);
end loop;
update security_test set id='100' where id=id1;
delete from security_test where id=1;
select count(*) into c1 from security_test;
return c1;
end;
/
```

6、使用用户b调用函数。

```sql
\c - b
SELECT fun_test1(5,3);
```

结果返回如下，表示调用成功

```sql
 fun_test1
-----------
         4
(1 row)
```

