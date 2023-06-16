# TABLE函数

## 功能描述 

TABLE函数用于查询返回的结果集，并将结果集转换为表的形式（就如同查询普通表一样查询返回的结果集）。

## 语法格式


```sql
table(arg);
```

## 参数说明

**arg：**

结果集。

## 注意事项

- table函数从Vastbase G100 V2.2 Build11版本开始入参支持变量，并且函数返回结果支持COLUMN_VALUE列。

  > <div align="left"><img src="image/image1.png" style="zoom:25%")</div>  
  >  COLUMN_VALUE：在 Oracle 中关键字 COLUMN_VALUE 也是数据库为没有列名或属性名的内部嵌套表的标量值生成的名称。在这种情况下，COLUMN_VALUE 不是伪列，而是实际的列名。

- 仅在Oracle兼容模式下table函数的入参可以为嵌套表类型，其他模式下暂不支持该特性。

## 示例

 **示例1** ：调用table函数。

1、创建测试表并插入数据。

```sql
create table testtable(id int);
insert into testtable values(123),(1234);
```

2、使用表函数查询结果。

```sql
SELECT * FROM table(testtable);
```

返回结果为：

```sql
id
------
  123
 1234
(2 rows)
```

 **示例2：** table函数入参使用变量。

1、创建自定义类型。

```
create  type nationalstring is table of varchar(2000);
```

2、创建函数。

```sql
create or replace function teststr return nationalstring is
na nationalstring;
lcn integer(5);
begin
na(1) := 'abc100';
na(2) := 'abc200';
na(3) := 'abc300';
select count(*) into lcn from table(na);
dbms_output.put_line('写入行数'||lcn);
return na;
end;
/ 
```

3、设置serveroutput 为on（允许将dbms_output.put_line的输出信息输出至vsql的命令界面的屏幕上）。

```sql
set serveroutput on;
```

4、调用函数。

```sql
select * from table(teststr());
```

返回结果为：

```sql
 写入行数3
 column_value
--------------
 abc100
 abc200
 abc300
(3 rows)
```

 **示例3：** table入参使用嵌套表（该功能仅在oracle兼容模式下支持）。

1、创建并切换至兼容模式为ORACLE的数据库db_oracle。

```sql
CREATE DATABASE db_oracle dbcompatibility=’B’;     
\c db_oracle
```

2、创建自定义数据类型。

```sql
create or replace type t_test as object(
id integer,
rq date,
mc varchar2(60)
);
```

3、使用步骤2定义好的t_test数据类型创建自定义类型。

```sql
create or replace type t_test_table as table of t_test;
```

4、创建函数。

```sql
create or replace function f_test_array(n in number default null) return t_test_table
as
v_test t_test_table;
begin
for i in 1 .. nvl(n,100) loop
v_test(i) := (i,sysdate,'mc'||i);
end loop;
return v_test;
end;
/
```

5、调用函数。

```sql
select * from table(f_test_array(10));
```

返回结果为：

```
 id |         rq          |  mc
----+---------------------+------
  1 | 2022-11-04 16:11:56 | mc1
  2 | 2022-11-04 16:11:56 | mc2
  3 | 2022-11-04 16:11:56 | mc3
  4 | 2022-11-04 16:11:56 | mc4
  5 | 2022-11-04 16:11:56 | mc5
  6 | 2022-11-04 16:11:56 | mc6
  7 | 2022-11-04 16:11:56 | mc7
  8 | 2022-11-04 16:11:56 | mc8
  9 | 2022-11-04 16:11:56 | mc9
 10 | 2022-11-04 16:11:56 | mc10
(10 rows)
```

 **示例4：** 在with子句中调用table函数，支持column_value列。

1、创建并切换至兼容模式为ORACLE的数据库db_oracle。

```sql
CREATE DATABASE db_oracle dbcompatibility=’B’;     
\c db_oracle
```

2、创建包和包体。

```sql
create or replace package package_test as
TYPE test_Type_List is table of clob INDEX BY varchar2(20);
function func_Pack_para(tmp1_in nvarchar2,tmp2_in nvarchar2) return test_Type_List;
end package_test;
/

create or replace package body package_test is
function func_Pack_para(tmp1_in nvarchar2, tmp2_in nvarchar2) return test_Type_List IS
re_l test_Type_List := test_Type_List();
i int;
begin
re_l('Aa一') := tmp1_in;
re_l('Bb二') := tmp2_in;
re_l('CC') := '333###ccc';
re_l('Bb二') := '22###ccc@_--';
return re_l;
end;
end package_test;
/
```

3、设置serveroutput 为on（允许将dbms_output.put_line的输出信息输出至vsql的命令界面的屏幕上）。

```sql
set serveroutput on;
```

4、调用函数使用column_value列。

```sql
declare
type c_test is ref cursor;
v_cursor2 c_test;
proc_v package_test.test_Type_List:=package_test.func_Pack_para('testTEST测试ちゅうごく~!@#End','222testTEST测试ちゅうごく~!@#End');
v_row2 varchar(400);
begin
open v_cursor2 for
with temp as (select column_value from table(proc_v))
select * from temp;
loop
fetch v_cursor2 into v_row2;
exit when v_cursor2%notfound;
DBMS_OUTPUT.PUT_LINE ('V:'||v_row2);
end loop;
end;
/
```

返回结果为：

```
V:testTEST测试ちゅうごく~!@#End
V:22###ccc@_--
V:333###ccc
ANONYMOUS BLOCK EXECUTE
```



