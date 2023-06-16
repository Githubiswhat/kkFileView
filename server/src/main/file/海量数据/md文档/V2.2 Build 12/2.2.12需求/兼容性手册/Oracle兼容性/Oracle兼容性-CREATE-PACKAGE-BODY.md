放入兼容性手册->Oracle兼容性->SQL语法

# CREATE PACKAGE BODY

## 功能描述

PACKAGE是一个模式对象，用于组织过程、函数和变量的一种方式，PACKAGE由两部分组成：包（PACKAGE）和包体（PACKAGE BODY）组成。

包头是包的说明部分，是对外的操作接口，对应用是可见的；包体是包的代码和实现部分，对应用来说是不可见的。

Vastbase G100在Oracle兼容性模式下，CREATE PACKAGE支持编译控制选项pragma serially_reusable，可以将package设定为连续可复用包，此选项可以使得对应package分配的内存在调用后被释放，复用package时包内变量都被初始化为其默认值。

当包体存在初始化块时，Vastbase G100支持不同包体的初始化块/包体函数进行相互调用。

## 语法格式

```sql
CREATE [OR REPLACE] PACKAGE BODY [schema_name.]package_name IS | AS
    declarations
    implementations;
    PRAGMA SERIALLY_REUSABLE;
[BEGIN EXCEPTION]
END;
```

## 参数说明

- **schema_name**

  已经存在模式名称。

- **package_name**

  自定义包体名称.

- **declarations**

  声明私有变量和私有子程序。

- **implementations**

  定义私有子程序和公有子程序。

- **PRAGMA**

  package编译控制选项标记。

- **SERIALLY_REUSABLE**

  编译控制选项，将package设定为连续可复用包。

## 注意事项

该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

## 示例

- CREATE PACKAGE BODY示例请参考CREATE PACKAGE[示例1](CREATE-PACKAGE.md#创建包)。

- 带有编译选项pragma serially_reusable的CREATE PACKAGE BODY示例请参考CREATE PACKAGE[示例10](CREATE-PACKAGE.md#example10)。

**前置条件：**创建并切换至兼容模式为Oracle的数据库db_oracle。

```sql
CREATE DATABASE db_oracle dbcompatibility='A';    
\c db_oracle
```

**示例1：**package在初始块中互相调用变量。

1、创建包pkg1。

```sql
CREATE OR REPLACE PACKAGE pkg1 is
--创建共有函数，可以被调用
function pkg1_fun(i int) return int; 
--公有变量
id1 int:= 1;  
end pkg1;
/
```

2、创建包pkg2。

```sql
CREATE OR REPLACE PACKAGE pkg2 is
function pkg2_fun(i number) return number;
id2 int:= 1; 
end pkg2;
/
```

3、给pkg1包创建包体实现pkg1_fun函数，调用pkg2包中定义的变量id2。

```sql
CREATE OR REPLACE PACKAGE BODY pkg1 is
function pkg1_fun(i int) return int is
res int;
begin  
res := pkg2.id2;  --调用pkg2.id2的值赋予res
return res;
end;
end pkg1;
/
```

4、给pkg2包创建包体实现pkg2_fun函数。

```sql
CREATE OR REPLACE PACKAGE BODY pkg2 is
function pkg2_fun(i number) return number is
res int;
begin
res := pkg1.id1; --调用pkg1.id1的值赋予res
return res;
end;
begin
id2 := 20;  --给id2赋值20
end pkg2;
/
```

5、调用包函数可见pkg1_fun函数调用了变量id2。

```sql
select pkg1.pkg1_fun(1) from dual;
```

返回结果为：

```sql
pkg1_fun
--------
      20
(1 row)
```

**示例2：**package包函数互相调用包变量，初始化块互相调用包函数。

1、创建包pkg1。

```sql
CREATE OR REPLACE PACKAGE pkg1 is
function pkg1_fun1(i int) return int;
function pkg1_fun2(i int) return int;
id1 int:= 1;
end pkg1;
/
```

2、创建包pkg2。

```sql
CREATE OR REPLACE PACKAGE pkg2 is
function pkg2_fun1(i number) return number;
function pkg2_fun2(i numeric) return numeric;
id2 int:= 2;
end pkg2;
/
```

3、创建包体pkg1，调用包pkg2中定义的函数pkg2_fun2。

```sql
CREATE OR REPLACE PACKAGE BODY pkg1 is
function pkg1_fun1(i int) return int is 
res int;
begin
res := pkg2.id2;
return res;
end;
function pkg1_fun2(i int) return int is
res int;
begin
res := i;
return res;
end;
begin
id1 := pkg2.pkg2_fun2(5);  --调用包pkg2中定义的函数pkg2_fun2
end pkg1;
/
```

4、创建包体pkg2，调用包pkg1中定义的函数pkg1_fun2。

```sql
CREATE OR REPLACE PACKAGE BODY pkg2 is
function pkg2_fun1(i number) return number is
res int;
begin
res := pkg1.id1;
return res;
end;
function pkg2_fun2(i numeric) return numeric is
res int;
begin
res := i;
return res;
end;
begin
id2 := pkg1.pkg1_fun2(10);  --调用包pkg1中定义的函数pkg1_fun2
end pkg2;
/
```

5、调用包函数。

```sql
select pkg1.pkg1_fun1(100) from dual;
select pkg1.pkg1_fun2(100) from dual;
select pkg2.pkg2_fun1(100) from dual;
select pkg2.pkg2_fun2(100) from dual;
```

返回结果分别为：

```sql
pkg1_fun1
--------
      10
(1 row)

pkg1_fun2
--------
     100
(1 row)

pkg2_fun1
--------
       5
(1 row)

pkg2_fun2
--------
     100
(1 row)
```