# CREATE PROCEDURE

放入MySQL兼容性-SQL语法

## 功能描述

创建新的存储过程。在MySQL兼容模式下，支持以下语法：

- SQL DATA Access
  - CONTAINS SQL
  - NO SQL
  - READS SQL DATA
  - MODIFIES SQL DATA
- LANGUAGE SQL
- SQL SECURITY

## 语法格式

```sql
CREATE [ OR REPLACE ] [DEFINER = usr] PROCEDURE procedure_name
    [ ( {[ argmode ] [ argname ] argtype [ { DEFAULT | := | = } expression ]}[,...]) ]
   { IS | AS } [characteristic ...] plsql_body
/
```

其中characteristic语法如下：

```sql
 COMMENT 'string'
 |LANGUAGE SQL
 |[NOT]DETERMINISTIC
 |{CONTAINS SQL|NO SQL|READ SQL DATA|MODIFIES SQL DATA}
 |SQL SECURITY {DEFINER|INVOKER}
```

## 参数说明

- **procedure_name**

  创建的存储过程名称，可以带有模式名。

  取值范围：字符串，要符合标识符的命名规范。

- **LANGUAGE SQL** 

  用以实现存储过程的语言的名称。在存储过程中如果使用LANGUAGE SQL用法，则在plsql_body 部分除了出现SQL语句时能正常执行以外，出现逻辑代码如变量赋值、逻辑控制、异常处理等行为时也可以正常执行。

   与[NOT] DETERMINISTIC 的顺序可以随意排列。

-  **[NOT] DETERMINISTIC** 

  说明存储过程执行的结果是否正确。

  - DETERMINISTIC：表示结果确定。每次执行存储过程时，相同的输入会得到相同的输出。
  - NOT DETERMINISTIC：表示结果不确定，相同的输入可能得到不同的输出。

- **CONTAINS SQL**

  表示存储过程中不包含读或写数据的语句。

- **NO SQL**

  表示存储过程中不包含SQL语句。

- **READ SQL DATA**

  表示存储过程中包含读数据的语句，但不包含写数据的语句。

- **MODIFIES SQL DATA**

  表示存储过程中包含写数据的语句。

- **SQL SECURITY**

  表示存储过程的权限。

  默认值为：SQL SECURITY DEFINER。

  - SECURITY INVOKER：表明该存储过程将带着调用它的用户的权限执行。该参数可省略。
  - SECURITY DEFINER：声明该存储过程将以创建它的用户的权限执行。

- **plsql_body**

  PL/SQL存储过程体。

  > <div align="left"><img src="image/image1.png" style="zoom:25%"></div>
  >
  > 当在存储过程体中进行创建用户等涉及用户密码相关操作时，系统表及csv日志中会记录密码的明文。因此不建议用户在存储过程体中进行涉及用户密码的相关操作。

## 注意事项

- characteristic特性仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 当未指定SQL DATA Access特性时，默认为CONTAINS SQL。
- 指定多个特性时，排列位于后的特性生效。
- 特性语法小写时可识别。
- DEFINER与SQL SECURITY INVOKER同时定义时，SQL SECURITY INVOKER优先级更高，即在执行该存储过程时，使用”调用者“的用户权限判断其是否有访问该存储过程中的具体对象的权限。
- DEFINER与SQL SECURITY DEFINER同时定义时，使用“定义者”的用户权限判断其是否有访问该存储过程中的具体对象的权限。

## 示例

**示例1：**创建含有NO SQL 语法的存储过程。

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
create table test2(id int,age int,name varchar2(16)); 

insert into test2 values(1,22,'小王'); 
insert into test2 values(2,23,'小张'); 
insert into test2 values(3,21,'小弓'); 

select * from test2 order by id;
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

3、创建带有NO SQL语法的存储过程。

```sql
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

```sql
call pro_test2(25,'asdddf'); 
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

**示例2：** 创建带NOT DETERMINISTIC语法的存储过程

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

**示例3：** 创建带LANGUAGE SQL的存储过程。

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

**示例4：**创建带有SQL SECURITY的存储过程。

1、创建兼容MySQL的数据库db_mysql：

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

3、创建两个测试用户a和b。

```sql
CREATE USER a password 'Aa@123456';
CREATE USER b password 'Bb@123456';
```

4、给a授予表security_test的所有权限，授予b查询权限。

```sql
grant all privileges on security_test to a;
grant select  on security_test to b;、
```

5、创建存储过程，定义DEFINER为a 同时使用SQL SECURITY INVOKER。

```sql
CREATE DEFINER=a procedure pro1() SQL SECURITY INVOKER
AS
BEGIN
INSERT INTO security_test values(100,100);
end;
/
```

6、使用用户a调用存储过程。

```sql
\c - a
call pro1();
```

结果返回如下，表示调用成功。

```sql
 pro1
------

(1 row)
```

7、给用户b授予存储过程权限。

```sql
GRANT ALL PRIVILEGES ON PROCEDURE pro1() TO b;
```

8、切换至用户b 调用存储过程。

```sql
\c - b
call pro1();
```

结果返回如下，因b没有表security_test 的写权限，所以执行失败。

```sql
ERROR:  permission denied for relation security_test
DETAIL:  N/A
CONTEXT:  SQL statement "INSERT INTO security_test values(100,100)"
PL/pgSQL function public.pro1() line 3 at SQL statement
```

