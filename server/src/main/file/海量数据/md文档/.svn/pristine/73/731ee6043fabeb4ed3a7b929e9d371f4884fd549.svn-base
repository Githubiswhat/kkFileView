# CREATE PACKAGE

## 功能描述

PACKAGE是一个模式对象，用于组织过程、函数和变量的一种方式，由包声明和包体组成。包体相关内容请参考[CREATE PACKAGE BODY](CREATE-PACKAGE-BODY.md)。

Vastbase G100在Oracle兼容模式下，CREATE PACKAGE支持如下功能：

- 支持函数返回嵌套表和关联数组类型。

- 包内支持使用%type定义变量的类型，赋值时包体可自动转换。

- 支持同义词引用对象语法定义。

- 创建package中支持类型调用。

- 创建package时支持使用type…is来定义数据类型。

    定义好type后，在本package或其他package函数中，可以只用这个type类型定义变量；在package的body里可以直接使用这个type来定义变量；在其他包里用packagename.typename的形式使用。

- 支持在package中定义的record类型作为procedure出参。

- 编译控制选项pragma serially_reusable，可以将package设定为连续可复用包，此选项可以使得对应package分配的内存在调用后被释放，复用package时包内变量都被初始化为其默认值。

## 语法格式

- 声明包

  ```sql
  CREATE [OR REPLACE] PACKAGE [schema_name.]package_name IS | AS
      declarations;
  END;
  ```

- 返回嵌套表和关联数组类型。

  ```sql
  create or replace package package_test as
  type table_test_type is table of type1 [index by type2];
  ```

    > <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
    >
    > 关联数组和嵌套表相关内容请参考[关联数组](PLSQL/关联数组.md)和[嵌套表](PLSQL/嵌套表.md)。

- 包内支持使用%type定义变量的类型，赋值时包体可自动转换。

  ```sql
  create or replace package package_name as
  variable_name table_name.column_name%type;
  ```

    > <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
    >
    > %type支持Vastbase支持的类型。

- 支持同义词引用对象语法

  ```sql
  CREATE OR REPLACE PACKAGE package_name AS
     type_name data_type;
  END;
  /
  
  CREATE SYNONYM synonym_name for package_name;
  
  CREATE OR REPLACE PACKAGE package_b AS
     variable_name synonym_name.subtype_name%type;
  END;
  /
  ```

- 支持类型调用

  ```sql
  CREATE OR REPLACE PACKAGE package_name AS
  type_name data_type;
  var1 int;
  END;
  /
  
  CREATE OR REPLACE PACKAGE package_b AS
  variable_name package_name.type_name%type;
  END;
  /
  ```
  
- 支持type…is语法，支持record类型作为procedure出参。

  ```sql
  create or replace package package_name as 
  type type_name is record...;               --record类型
  type type_name is table of ...;            --嵌套表
  type type_name is table of ...index by ... --关联数组
  end;
  /
  ```

- 支持record类型作为procedure出参。 

  > <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
  >
  > - 在package中定义的record类型作为procedure出参的操作步骤为：
  >
  >
  > 1、自定义record类型和这个存储过程都是在一个package的声明里使用的。
  >
  > 2、定义package。
  >
  > 3、定义record类型。
  >
  > 4、定义存储过程，使用前面定义的record类型作为出参。
  >
  > - 示例：
  >
  > ```sql
  > --创建一个package
  > create or replace package pkg_themis_xmloperate as
  > 
  > --定义t_tagValue是以int作为数组标号、数组元素是varchar2(30000)的嵌套表类型
  > type t_tagValue is table of varchar2(30000)index by binary_integer;
  > 
  > --定义类型t_tagclobvalue是以int作为数组标号、数组元素是clob的嵌套表类型
  > type t_tagclobvalue is table of clob index binary_integer;
  > 
  > --定义一个record类型A，该类型里有两种子类型：xmldomnode,boolean
  > type t_approve_info is record(xmldomnode int,is_local boolean);
  > 
  > --定义一个存储过程，其中t_approve_info是一个在上面package里定义好的record类型，在这里被用于出参类型
  > procedure p_get_PD_from_approve_info(
  > p_stackid in number,
  > p_stackdepth in number,
  > P_in_node in xmldom.domnode,
  > p_out_data out t_approve_info,
  > p_case_flow_id in number default null,
  > p_flow_id in number default null);
  > end;
  > /
  > ```
  
- 支持设置编译控制选项pragma serially_reusable。

  ```sql
  CREATE OR REPLACE PACKAGE package_name IS
  	PRAGMA SERIALLY_REUSABLE;
  	...
  END pkg1;
  /
  ```


## 参数说明

- **OR REPLACE**

  该参数说明如果创建的包存在，则重新创建包。

- **schema_name**

  包含此包的模式名称。

- **package_name**

  包的名称。

- **declarations**

  可以声明公有变量和公有子程序。

- **package_test**

  用户定义的包名。

- **table_test_type**

  用户定义的返回类型设置名。

- **type 函数返回类型**

  可指定具体数据类型如int、number等。

- **index by type2** 

  表示函数返回关联数组，其中type2是数组类型，如binary_integer等。

- **variable_name**

  用户定义的变量名称。

- **table_name.column_name** 

  %type参考的类型对应的列名（当调用其他表或包时，用xx.xx指定，如语法中的table_name.column_name)。

- **data_type**

  Vastbase所有的数据类型。

- **%type**

  包内支持使用%type定义变量的类型，赋值时包体可自动转换。

- **type_name** 

  类型名。

- **PRAGMA**

  package编译控制选项标记。

- **SERIALLY_REUSABLE**

  编译控制选项，将package设置为连续可复用包。

## 注意事项

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。
- 用户需要具备创建package的权限。
- %type支持Vastbase支持的类型。
- 编译控制选项pragma serially_reusable语法不区分大小写。

## 示例

**前置条件：**创建并切换至兼容Oracle的库db_oracle，检查数据库兼容模式是否为A。

```sql
CREATE DATABASE db_oracle dbcompatibility='A';
\c db_oracle
show sql_compatibility;
```

**示例1：**创建包和包体。<a id='创建包'></a>

1、创建包。

```sql
create package sp_package is
procedure update_sal(name varchar2,newsal number);
function annual_income(name varchar2) return number;
end;
/
```

2、创建包体。

```sql
create or replace package body sp_package is
procedure update_sal(name varchar2,newsal number)
is
begin
update emp set sal=newsal where ename=name;
end;
function annual_income(name varchar2)
return number is
annual_salary number(12,2);
begin
select sal*12+nvl(comm,0) into annual_salary from emp
where ename=name;
return annual_salary;
end;
end;
/
```

3、删除包体

```sql
DROP PACKAGE BODY sp_package;
```

**示例2：** 包内存储过程返回int类型嵌套表。

1、准备测试数据。

```sql
create or replace package package_test as
type table_test_type is table of int;
function func_Pack_para(tmp1_in int,tmp2_in int) return table_test_type;
end package_test;
/
```

2、package内函数调用。

```sql
create or replace package body package_test is
function func_Pack_para(tmp1_in int, tmp2_in int) return table_test_type IS
re_l table_test_type;
i int;
begin
re_l(1) := tmp1_in;
re_l(2) := tmp2_in;

i := re_l.FIRST;
WHILE i IS NOT NULL LOOP
raise info '%; index:%',re_l(i),i;
i := re_l.NEXT(i);
END LOOP;
return re_l;
end;
end package_test;
/

call package_test.func_Pack_para(1, 159.7);
```

函数返回int类型嵌套表成功：

```sql
INFO:  1; index:1
INFO:  160; index:2
 func_pack_para
----------------
 {1,160}
(1 row)
```

**示例3：**包内函数返回number(10,2)类型嵌套表，关联数组binary_integer。

1、准备测试数据。

```sql
create or replace package package_test as
TYPE table_test_type is table of number(10,2) index by binary_integer;
function func_Pack_para(tmp1_in number,tmp2_in number) return table_test_type;
end package_test;
/
```

2、package内函数调用。

```sql
create or replace package body package_test is
function func_Pack_para(tmp1_in number, tmp2_in number) return table_test_type IS
re_l table_test_type;
i int;
begin
re_l(1) := tmp1_in;
re_l(2) := tmp2_in;

i := re_l.FIRST;
WHILE i IS NOT NULL LOOP
raise info '%; index:%',re_l(i),i;
i := re_l.NEXT(i);
END LOOP;
return re_l;
end;
end package_test;
/

call package_test.func_Pack_para(1.123, 12345678.159);
```

嵌套表关联数组成功：

```sql
INFO:  1.12; index:1
INFO:  12345678.16; index:2
   func_pack_para
--------------------
 {1.12,12345678.16}
(1 row)
```

**示例4：**%type语法。

1、创建包，%type定义变量var2的类型为字符型。

```sql
create or replace package pk_1087209 as
var1 varchar(5);
var2 var1%type;
end;
/
```

2、创建包体，赋值类型为数值型。

```sql
create or replace package body pk_1087209 as
procedure p1 as
begin
var1:='aaa';
var2:=55;
raise info '%', var1;
raise info '%', var2;
end;
end pk_1087209;
/
```

3、调用包体。

```sql
call pk_1087209.p1();
```

返回结果为：

```sql
INFO:  aaa
INFO:  55
 p1
----

(1 row)
```

**示例5：**同义词引用对象。

1、创建普通用户并授权。

```sql
CREATE USER user1_1087651 PASSWORD 'Abc@123456';
grant all on database db_oracle to user1_1087651;
CREATE USER user2_1087651 PASSWORD 'Abc@123456';
grant all on database db_oracle to user2_1087651;
```

2、创建类型。

```sql
create type user1_1087651.ty1_1087651 is(
id int2,
num1 int4,
num2 number(5),
num3 number(3,3),
num4 number(4,1),
num5 numeric,
num6 float4,
num7 float8,
num8 real); 
```

3、创建包user1_1087651.pk1_1087651。

```sql
create or replace package user1_1087651.pk1_1087651 as
var1 ty1_1087651;
var2 int2;
var3 var1.num1%type;
type tp_rec4 is record(id number(5),num2 var1.num3%type);
rec4 tp_rec4;
var5 number(4,1);
type tp_varray6 is varray(3) of var1.num5%type;
varray6 tp_varray6;
type tp_tb7 is table of var1.num7%type index by text;
tb7 tp_tb7;
var8 float4;
var9 real;
end;
/
```

4、创建同义词。

```sql
create synonym user2_1087651.syn_pk2_1087651 for user1_1087651.pk1_1087651;
```

5、创建包user2_1087651.pk3_1087651。

```sql
create or replace package user2_1087651.pk3_1087651 as
var1 syn_pk2_1087651.var1%type;
var2 syn_pk2_1087651.var2%type;
var3 syn_pk2_1087651.var3%type;
rec4 syn_pk2_1087651.rec4%type;
var5 syn_pk2_1087651.var5%type;
varray6 syn_pk2_1087651.varray6%type;
tb7 syn_pk2_1087651.tb7%type;
var8 syn_pk2_1087651.var8%type;
var9 syn_pk2_1087651.var9%type;
end;
/
```

6、创建包体。

```sql
create or replace package body user2_1087651.pk3_1087651 as
procedure p1 as
begin
var1:=(12,16,12345.6,0.123,42.3,61.2,7.889,1.241,5);
var2:=12;
var3:=99;
rec4:=(144,0.9991);
var5:=11.2;
varray6(1):=0.637;
tb7(11):=0.247;
var8:=3.141;
var9:=44;
raise info '%',var1;
raise info '%',var2;
raise info '%',var3;
raise info '%',rec4;
raise info '%',var5;
raise info '%',varray6;
raise info '%',tb7;
raise info '%',var8;
raise info '%',var9;
end;
end pk3_1087651;
/
```

7、调用。

```sql
call user2_1087651.pk3_1087651.p1();   
```

返回结果为：

```sql
INFO:  (12,16,12346,.123,42.3,61.2,7.889,1.241,5)
INFO:  12
INFO:  99
INFO:  (144,.9991)
INFO:  11.2
INFO:  {.637}
INFO:  [11:11]={.247}
INFO:  3.141
INFO:  44
 p1
----

(1 row)
```

**示例6：**package中的类型调用。

1、创建类型。

```sql
create type ty1_1087293 is(
id int2,
num1 int4,
num2 number(5),
num3 number(3,3),
num4 number(4,1),
num5 numeric,
num6 float4,
num7 float8,
num8 real); 
```

2、创建包package pk1_1087293。

```sql
create or replace package pk1_1087293 as
var1 ty1_1087293;
var2 int2;
var3 var1.num1%type;
type tp_rec4 is record(id number(5),num2 var1.num3%type);
rec4 tp_rec4;
var5 number(4,1);
type tp_varray6 is varray(3) of var1.num5%type;
varray6 tp_varray6;
type tp_tb7 is table of var1.num7%type index by text;
tb7 tp_tb7;
var8 float4;
var9 real;
end;
/
```

3、创建包pk2_1087293。

```sql
create or replace package pk2_1087293 as
var1 pk1_1087293.var1%type;
var2 pk1_1087293.var2%type;
var3 pk1_1087293.var3%type;
rec4 pk1_1087293.rec4%type;
var5 pk1_1087293.var5%type;
varray6 pk1_1087293.varray6%type;
tb7 pk1_1087293.tb7%type;
var8 pk1_1087293.var8%type;
var9 pk1_1087293.var9%type;
end;
/
```

4、创建包体。

```sql
create or replace package body pk2_1087293 as
procedure p1 as
begin
var1:=(12,16,12345.6,0.123,42.3,61.2,7.889,1.241,5);
var2:=12;
var3:=99;
rec4:=(144,0.9991);
var5:=11.2;
varray6(1):=0.637;
tb7(11):=0.247;
var8:=3.141;
var9:=44;
raise info '%',var1;
raise info '%',var2;
raise info '%',var3;
raise info '%',rec4;
raise info '%',var5;
raise info '%',varray6;
raise info '%',tb7;
raise info '%',var8;
raise info '%',var9;
end;
end pk2_1087293;
/
```

5、调用。

```sql
call pk2_1087293.p1(); 
```

返回结果为：

```sql
INFO:  (abcde,aAw1,ppp,"oopp,,")
INFO:  (12AB,lmn,opqe4--4,q,pp4477,hh)
INFO:  qqq
INFO:  kkk
INFO:  0OLq
INFO:  (ammm!*…1,1a11,-3.1134)
INFO:  {aaa1}
INFO:  aaa1
INFO:  p
INFO:  {mno,NULL,mmn}
INFO:  aa
 p1
----

(1 row)
```

**示例7：** 包规格内声明int类型嵌套表。

1、包规格声明包内公有变量。

```sql
create or replace package package_test as
type array_integer is table of int;
end;
/
```

2、其他函数中调用int类型嵌套表。

```sql
create or replace function func_Pack_para() return int
as
func_arr_int package_test.array_integer;
i int;
begin
func_arr_int[1] := 1;
for i in 2..10 loop
select i into func_arr_int[i] from dual;
end loop;
return func_arr_int[5];
end;
/
```

3、调用函数。

```sql
call func_Pack_para();
```

函数返回结果：

```sql
 func_pack_para
----------------
              5
(1 row)
```

**示例8：** 包规格内声明int类型嵌套表关联varchar(20)类型数组。

1、设置serveroutput 为on（允许将dbms_output.put_line的输出信息输出至vsql的命令界面的屏幕上）。

```sql
set serveroutput on;
```

2、包中定义变量。

```sql
create or replace package package_test2 AUTHID DEFINER IS
Type array_list_type IS TABLE OF int INDEX BY varchar2(20);
end;
/
```

3、包外存储过程调用变量。

```sql
create or replace PROCEDURE proc_Pack_para AUTHID DEFINER IS 
array_List package_test2.array_list_type;
i varchar2(20);
begin
array_List('一') := 1;
array_List('二') := 2;
array_List('三') := 3;

i := array_List.FIRST;
WHILE i IS NOT NULL LOOP
DBMS_OUTPUT.PUT_LINE (array_List(i) || ' ' || i);
i := array_List.NEXT(i);
END LOOP;
end;
/
```

4、调用存储过程。

```sql
call proc_Pack_para();
call proc_Pack_para();
```

函数返回结果：

```sql
1 一
3 三
2 二
 proc_pack_para
----------------

(1 row)
```

**示例9：**package中定义的record类型作为procedure出参。

1、创建一个包，定义record类型为int。

```sql
CREATE OR REPLACE PACKAGE pkg AS
TYPE rec_type IS RECORD(
col_int int,
col_smallint smallint,
col_integer integer,
col_bigint bigint,
col_int2 int2,
col_int4 int4,
col_int8 int8
);

PROCEDURE print_rec_type(
p_col_int in int,
p_col_smallint in smallint,
p_col_integer in integer,
p_col_bigint in bigint,
p_col_int2 in int2,
p_col_int4 in int4,
p_col_int8 in int8,
rec out rec_type);
END;
/
```
2、包中存储过程作为返回参数。

```sql
CREATE OR REPLACE PACKAGE BODY pkg AS
PROCEDURE print_rec_type(
p_col_int in int,
p_col_smallint in smallint,
p_col_integer in integer,
p_col_bigint in bigint,
p_col_int2 in int2,
p_col_int4 in int4,
p_col_int8 in int8,
rec out rec_type) IS

BEGIN

rec.col_int := p_col_int ;
rec.col_smallint:= p_col_smallint;
rec.col_integer := p_col_integer ;
rec.col_bigint := p_col_bigint ;
rec.col_int2 := p_col_int2 ;
rec.col_int4 := p_col_int4 ;
rec.col_int8 := p_col_int8 ;

END;
END pkg;
/
```

3、PLSQL中调用存储过程。

```sql
set serveroutput on;

DECLARE
rec_p pkg.rec_type;
begin
pkg.print_rec_type(1,11,11,11,34.5,34.5,123,rec_p);

DBMS_OUTPUT.PUT_LINE(rec_p.col_int );
DBMS_OUTPUT.PUT_LINE(rec_p.col_smallint);
DBMS_OUTPUT.PUT_LINE(rec_p.col_integer );
DBMS_OUTPUT.PUT_LINE(rec_p.col_bigint );
DBMS_OUTPUT.PUT_LINE(rec_p.col_int2 );
DBMS_OUTPUT.PUT_LINE(rec_p.col_int4 );
DBMS_OUTPUT.PUT_LINE(rec_p.col_int8 );
end;
/
```
返回结果为：

```sql
1
11
11
11
35
35
123
```

**示例10：**使用编译控制选项pragma serially_reusable控制package连续可复用，复用时包内变量都被初始化为其默认值。<a id='example10'></a>

1、创建带控制选项的测试包。

```sql
CREATE OR REPLACE PACKAGE z_pkg IS 
n NUMBER := 5; 
END z_pkg; 
/ 

CREATE OR REPLACE PACKAGE sr_pkg IS 
PRAGMA SERIALLY_REUSABLE; 
n NUMBER := 5; 
END sr_pkg; 
/ 

BEGIN 
z_pkg.n := 10; 
sr_pkg.n := 10; 
END; 
/ 
```

2、开启参数serveroutput（允许将dbms_output.put_line的输出信息输出至vsql的命令界面的屏幕上），然后调用函数，查看复用package时变量是否初始化。

```sql
set serveroutput on;
BEGIN 
DBMS_OUTPUT.PUT_LINE('z_pkg.n: ' || z_pkg.n); 
DBMS_OUTPUT.PUT_LINE('sr_pkg.n: ' || sr_pkg.n); 
END; 
/ 
```

结果显示为如下，package复用后变量被初始化为默认值：

```sql
z_pkg.n: 10
sr_pkg.n: 5
ANONYMOUS BLOCK EXECUTE
```

