# **package中支持type...is语法**

**功能描述**

在Oracle兼容模式下，用户创建package时使用type...is来定义数据类型。

定义好type后，在本package或其他package函数中，可以只用这个type类型定义变量；在package的body里可以直接使用这个type来定义变量；在其他包里用packagename.typename的形式使用。

**语法格式**

```
create or replace package package_name as 
type type_name is record...;//record类型
type type_name is table of ...; //嵌套表
type type_name is table of ...index by ...//关联数组
end;
/
```

**参数说明**

- package_name： 包名。

- type_name： 类型名。

**注意事项**

该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

**示例**

**示例1：** 包规格内声明int类型嵌套表。

1、创建测试数据库并检查数据库兼容性。

```
\c vastbase
CREATE DATABASE my_test DBCOMPATIBILITY 'A';
\c my_test
show sql_compatibility;
```

兼容模式为：

```
 sql_compatibility
-------------------
 A
(1 row)
```

2、包规格声明包内公有变量。

```
create or replace package package_test as
type array_integer is table of int;
end;
/
```

3、其他函数中调用int类型嵌套表。

```
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

4、调用函数。

```
call func_Pack_para();
```

函数返回结果：

```
 func_pack_para
----------------
              5
(1 row)
```

**示例2：** 包规格内声明int类型嵌套表关联varchar(20)类型数组。

1、创建测试数据库并检查数据库兼容性。

```
\c vastbase
CREATE DATABASE my_test DBCOMPATIBILITY 'A';
\c my_test
show sql_compatibility;
```

兼容模式为：

```
 sql_compatibility
-------------------
 A
(1 row)
```

2、包中定义变量。

```
set serveroutput on;
create or replace package package_test2 AUTHID DEFINER IS
Type array_list_type IS TABLE OF int INDEX BY varchar2(20);
end;
/
```

3、包外存储过程调用变量。

```
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

```
call proc_Pack_para();
```

函数返回结果：

```
1 一
3 三
2 二
 proc_pack_para
----------------

(1 row)
```



