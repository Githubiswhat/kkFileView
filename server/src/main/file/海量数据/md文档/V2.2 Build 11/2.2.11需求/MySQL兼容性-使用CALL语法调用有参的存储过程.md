# CALL

放入MySQL兼容性-SQL语法

## 功能描述

使用CALL命令可以调用已定义的函数和存储过程。在MySQL兼容模式下，针对使用了out、inout 类型参数的存储过程，在使用call语法调用时，允许使用变量对应作为out、inout类型的参数。

## 语法格式

```sql
CALL sp_name([parameter[,...]])
```

## 参数说明

-  **sp_name ** 

  存储过程名称。

  取值范围：字符串，要符合标识符的命名规范。

-  **parameter** 

  参数名称。

## 注意事项

MySQL使用临时变量，可以比较方便的使用@变量名的方式来定义一个临时变量。

## 示例

1、创建兼容MySQL的库my_test ，并进入。

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

2、创建测试表time_table，并插入测试数据。

```sql
create table time_table8(id int,c1 char(8),c3 varchar(16));
insert into time_table8 values(1,'xx','xx');
insert into time_table8 values(2,'yy','yy');
insert into time_table8 values(3,'zz','zz');
insert into time_table8 values(4,'ss','ss');
```

3、创建存储过程。

```sql
CREATE OR REPLACE PROCEDURE pro_call_1093754(n int,a1 in char(8),a2 out int,a3 inout varchar(16))
AS
BEGIN
for i in 1..n loop
a3:=a3||i;
insert into time_table8 values(4+i,a1,a3);
end loop;
select count(*) into a2 from time_table8;
END;
/
```

4、调用存储过程。

```sql
call pro_call_1093754(3,'dd',5,'ll');
```

结果返回如下：

```sql
 a2 |  a3
----+-------
  7 | ll123
(1 row)
```

5、查询表中数据。

```sql
select * from time_table8 order by id;
```

结果返回如下：

```sql
 id |    c1    |  c3
----+----------+-------
  1 | xx       | xx
  2 | yy       | yy
  3 | zz       | zz
  4 | ss       | ss
  5 | dd       | ll1
  6 | dd       | ll12
  7 | dd       | ll123
(7 rows)
```

6、创建存储过程调用，参数赋值。

```sql
create or replace procedure pro1_call_1093754()
as
b1 char(8);
b2 int;
b3 varchar(16);
begin
b1:='oo';
b2:=3;
b3:='pp';
call pro_call_1093754(2,b1,b2,b3);
end;
/
```

7、调用存储过程pro1_call_1093754()。

```sql
call pro1_call_1093754();
```

8、查询表中数据

```sql
select * from time_table8 order by id;
```

结果返回如下，存储过程调用成功。

```sql
 id |    c1    |  c3
----+----------+-------
  1 | xx       | xx
  2 | yy       | yy
  3 | zz       | zz
  4 | ss       | ss
  5 | dd       | ll1
  5 | oo       | pp1
  6 | oo       | pp12
  6 | dd       | ll12
  7 | dd       | ll123
(9 rows)
```

