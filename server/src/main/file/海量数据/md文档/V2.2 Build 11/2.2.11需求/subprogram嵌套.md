# subprogram嵌套

## 功能描述

subprogram即子程序，匿名块、函数、存储过程以及包都属于子程序。支持开发和维护具有以下特性的可靠、可重用代码。

- 模块化

  子程序使用戶可以将程序分解为可管理的、定义明确的模块。

- 更简单的应用设计

  在设计应用程序时，用户可以推迟子程序的实现细节，直到用户测试了主程序，然后逐步细化。例如子程序创建期间以NULL语句作为占位符，可以在之后来完善它。

- 可维护性

  用户可以更改子程序的实现细节而不更改其调用程序。

- 可包装性

  子程序可以分组到包中。

- 可重用性

  在许多不同的环境中，任何数量的应用程序都可以使用相同的包子程序或独立子程序。

- 更好的性能

  每个子程序都以可执行的形式编译和存储，可以重复调用。因为存储的子程序在数据库服务器中运行，所以通过网络进行的一次调用就可以启动一项大型作业。这种工作分工减少了网络流里并缩短了响应时间。存储的子程序被缓存并在用户之间共享，从而降低了内存需求和调用开销。

- 嵌套子程序

  在PL/SQL 块内创建的子程序是嵌套子程序，用戶可以同时声明和定义。

## 语法格式

- 用户通过执行sql语句进行subprogram的创建和定义。

  ```
  subprogram_name[ (parm [,parm...])]
  subprogram
  ```

- subprogram中定义嵌套对象语法如下：

  - 函数

    函数名称可以相同，同名函数拥有不同的参数个数或者参数类型或返回值其定义语法如下：

    ```sql
    PROCEDURE procedure_name
        [ ( {[ argname ] [ argmode ] argtype [ { DEFAULT | := | = } expression ]}[,...]) ]
        [
           { IMMUTABLE | STABLE | VOLATILE }
           | { SHIPPABLE | NOT SHIPPABLE }
           | {PACKAGE}
           | [ NOT ] LEAKPROOF
           | { CALLED ON NULL INPUT | RETURNS NULL ON NULL INPUT | STRICT }
           | {[ EXTERNAL ] SECURITY INVOKER | [ EXTERNAL ] SECURITY DEFINER | AUTHID DEFINER | AUTHID CURRENT_USER}
           | COST execution_cost
           | SET configuration_parameter { TO value | = value | FROM CURRENT }
        ][ ... ]
       {
        IS | AS 
       } 
       plsql_body 
    /
    ```

  - 存储过程

    针对存储过程，同名存储拥有不同的参数个数或者参数类型语法如下：

    ```sql
    Procedure procedure_name
     [ ( {[ argname ] [ argmode ] argtype [ { DEFAULT | := | = } expression ]}[,...]) ]
      {
       IS | AS 
      } 
      plsql_body 
    /
    ```

> <div align="left"><img src="image/image1.png" style="zoom:25%"></div>
>
> 其中plsql_body语法与普通的schema下的subprogram的plsql_body语法一致，并支持异常处理。

## 参数说明

- **subprogram_name**

  子程序标题名称。

  子程序以子程序标题开始，该标题指定其名称和(可选)其参数列表。

- **parm [,parm,...]**

  子程序参数列表。

- **subprogram** 

  子程序部分。

  子程序部分是子程序的内容，其包含如下几部分：

  - 声明部分（可选）

    这部分声明和定义局部类型、游标、常量、变量、异常和嵌套子程序。当子程序完成执行时，这些项目将不复存在。声明的局部类型、变量或子程序等的作用域，仅限于当前对象，或者当前对象嵌套的subprogram。

  - 可执行部分（必填）

    这部分包含一个或多个用于赋值、控制执行和操作数据的语句。这部分可以包含对嵌套子程序的调用，其中函数类型嵌套子程序被调用，返回值可以参与表达式运算。

  - 异常处理部分（可选）

    这部分包含处理运行时错误的代码。异常处理代码可以是子程序的异常处理,同样嵌套子程序也可以包含异常处理部分。

- 嵌套对象函数参数说明详见[CREATE FUNCTION](../开发者指南/CREATE-FUNCTION.md)。
- 嵌套对象存储过程参数说明详见[CREATE PROCEDURE](../开发者指南/CREATE-PROCEDURE.md)。

## 注意事项

- 本功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。
- 针对函数类型的子程序，还应该包括如下特性：
  - 函数标题必须包含 RETURN 子句，该子句指定函数返回值的数据类型 (过程标题不能有RETURN子句)。
  - 在函数的可执行部分，每条执行路径都必须指向一个RETURN语句。否则SQL编译器会发出编译时警告(在过程中，RETURN语句是可选的，不推荐使用)。
  - 只有功能标题可以包含DETERMINISTIC选项，表示确定性选项，帮助优化器避免冗余函数调用。

- 嵌套子程序的重载要求sql引擎不仅仅要满足嵌套subprogram功能，而且嵌套的存储过程或函数要能够支持重载功能。

## 示例

1、创建测试表，并插入数据。

```sql
create table employees_subpro 
( employee_id number(6,0), 
first_name varchar2(20), 
last_name varchar2(25), 
email varchar2(25), 
phone_number varchar2(20), 
hire_date date , 
job_id varchar2(10) , 
salary number(8,2), 
commission_pct number(2,2), 
manager_id number(6,0), 
department_id number(4,0)
); 
```

2、向表中插入数据。

```sql
INSERT INTO employees_subpro VALUES ('100', 'Steven', 'King', 'SKING', '515.123.4567', date'1987-06-17', 'AD_PRES', '24000.00', null, null, '90'); 
INSERT INTO employees_subpro VALUES ('200', 'Jennifer', 'Whalen', 'JWHALEN', '515.123.4444', date'1987-09-17', 'AD_ASST', '4400.00', null, '101', '10'); 
INSERT INTO employees_subpro VALUES ('101', 'Neena', 'Kochhar', 'NKOCHHAR', '515.123.4568', date'1989-09-21', 'AD_VP', '17000.00', null, '100', '90'); 
INSERT INTO employees_subpro VALUES ('102', 'Lex', 'De Haan', 'LDEHAAN', '515.123.4569', date'1993-01-13', 'AD_VP', '17000.00', null, '100', '90'); 
INSERT INTO employees_subpro VALUES ('103', 'Alexander', 'Hunold', 'AHUNOLD', '590.423.4567', date'1990-01-03', 'IT_PROG', '9000.00', null, '102', '60'); 
INSERT INTO employees_subpro VALUES ('104', 'Bruce', 'Ernst', 'BERNST', '590.423.4568', date'1991-05-21', 'IT_PROG', '6000.00', null, '103', '60'); 
INSERT INTO employees_subpro VALUES ('105', 'David', 'Austin', 'DAUSTIN', '590.423.4569', date'1997-06-25', 'IT_PROG', '4800.00', null, '103', '60');
```

3、创建带有声明和定义一起的函数的子程序嵌套。

```sql
create or replace function subprogram_f_1(id3 int) return varchar2 
as 
id5 int; 
id4 varchar2(20); 
CURSOR cur IS select employee_id from employees_subpro where department_id= id3 order by 1; 
function p ( id1 int) return varchar2 
IS 
id2 varchar2(20); 
BEGIN 
SELECT first_name INTO id2 FROM employees_subpro where employee_id = id1; 
        return id2; 
END ; 
BEGIN 
open cur; 
LOOP 
FETCH cur INTO id5; 
EXIT WHEN cur%NOTFOUND; 
id4 := p(id5); 
END LOOP; 
CLOSE cur; 
return id4; 
END; 
/ 
```

4、调用函数查询表内容。

```sql
select subprogram_f_1(department_id) from employees_subpro order by 1; 
```

结果显示为：

```sql
 subprogram_f_1
----------------
 David
 David
 David
 Jennifer
 Lex
 Lex
 Lex
(7 rows)
```

