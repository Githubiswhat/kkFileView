# DBMS_SESSION

## 功能描述

Vastbase G100在Oracle兼容模式下支持内置包DBMS_SESSION的部分功能，该内置包提供了查询和设置会话相关状态的方法。

Vastbase G100支持该内置包的如下模块：

| 函数                 | 描述                                                |
| -------------------- | --------------------------------------------------- |
| modify_package_state | 用于重置当前会话中所有package的状态，重新初始化包。 |

## 语法格式

**modify_package_state**

```sql
function modify_package_state(
  flag in TINYINT   --1or2
) return bool;
```

## 参数说明

**flag in**

用户指定的入参，类型为TINYINT。当入参为1，2时函数返回为true代表调用成功，其他情况下返回false。

## 注意事项

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

- flag in传入内核中区分不同的逻辑处理，为2时性能更优。


## 示例

1、创建并切换至兼容模式为Oracle的数据库下。

```sql
create  database dbtest  dbcompatibility 'A';
\c dbtest;
```

2、设置serveroutput 为on（允许将dbms_output.put_line的输出信息输出至vsql的命令界面的屏幕上）。

```sql
set serveroutput =on;
```

3、创建包含全局变量v_stack_depth的pkg1。

```sql
create or replace package pkg1 is
v_stack_depth number;
function get_stack_depth return number;
end;
/
```

4、在包体初始化中对全局变量赋值，并打印初始化信息。

```sql
create or replace package body pkg1 is 
function get_stack_depth return number 
is
begin
v_stack_depth := v_stack_depth + 1;
return v_stack_depth;
end;
begin
v_stack_depth := 0;
dbms_output.put_line('初始化pkg1');
end;
/
```

5、连续调用包中函数，函数返回全局变量v_stack_depth的值。

```sql
select pkg1.get_stack_depth from dual;
select pkg1.get_stack_depth from dual;
```

返回结果依次为：

```sql
初始化pkg1
 get_stack_depth
-----------------
               1
(1 row)

 get_stack_depth
-----------------
               2
(1 row)
```

6、重置会话中所有package的初始化值。

```sql
call dbms_session.modify_package_state(2);
```

返回结果为：

```sql
 modify_package_state
----------------------
 t
(1 row)
```

7、检测是否重新初始化。（连续调用包中函数返回v_stack_depth的值。）

```sql
select pkg1.get_stack_depth from dual;
select pkg1.get_stack_depth from dual;
```

返回结果依次为：

```sql
初始化pkg1
 get_stack_depth
-----------------
               1
(1 row)

 get_stack_depth
-----------------
               2
(1 row)
```

成功使用内置包函数DBMS_SESSION.modify_package_state重置函数入参为1。
