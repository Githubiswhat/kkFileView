# DECLARE CONDITION

## 功能描述

在MySQL兼容模式下支持使用DECLARE ... CONDITION语句声明一个命名的错误条件，可以为错误号或者 SOLSTATE重命名，在异常处理中可以使用声明的 CONDITION。

## 语法格式

```sql
DECLARE condition_name CONDITION FOR condition_value
```

**condition_value:**

```sql
{
    mysql_error_code
  | SQLSTATE [VALUE] sqlstate_value
}
```

## 参数说明

- **condition_name**

  条件名称。

- **condition_value**

  指示要与条件名称相关联的特定条件或条件类别，支持以下形式：

  - mysql_error_code：表示 MySQL 错误代码的整数文字，不支持使用MySQL错误代码0，因为这表示成功而不是错误条件。
  - SQLSTATE [VALUE]：一个 5 字符的字符串文字，指示 SQLSTATE 值。例如：SQLSTATE '22012'。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- MySQL中 mysql_error_code 与错误信息一一对应，在vastbase 中没有与之对应的关系，只做语法支持。
- 支持在触发器和匿名块中使用。

## 示例

**前置步骤**

1、创建兼容MySQL的库db_mysql，并进入。

```sql
CREATE DATABASE db_mysql dbcompatibility='B';
\c db_mysql
```

2、设置serveroutput为on

```sql
set serveroutput = on;
```

**示例1：**存储过程begin内部声明处理程序，handler方法调用。

1、创建存储过程，begin内部定义condition处理程序。

```sql
create or replace procedure testpro() as
declare
a int;
begin
declare t_condition condition for SQLSTATE '22012' ;
declare exit handler for t_condition
begin
RAISE NOTICE 'SQLSTATE = %,SQLCODE = %,SQLERROR=%',SQLSTATE,returned_sqlstate,message_text;
end;
a = 1/0;
end;
/
```

2、调用存储过程

```sql
call testpro();
```

结果返回如下：

```sql
NOTICE:  SQLSTATE = 22012,SQLCODE = 33816706,SQLERROR=division by zero
 testpro
---------

(1 row)

```

**示例2：**存储过程begin内部声明处理程序，exception方法调用。

1、创建测试表。

```sql
create table test(id int,info text);
```

2、创建存储过程。

```sql
create or replace procedure testpro() as
declare
a int;
begin
declare t_condition condition for SQLSTATE '22012' ;
a = 1/0;
EXCEPTION
WHEN t_condition THEN
begin
insert into test values (1,'success');
RAISE NOTICE 'SQLSTATE = %,SQLCODE = %，SQLERRM = %',SQLSTATE,SQLCODE,SQLERRM;
end;
end;
/
```

3、调用存储过程。

```sql
call testpro();
```

结果返回如下：

```sql
NOTICE:  SQLSTATE = 22012,SQLCODE = 33816706，SQLERRM = division by zero
 testpro
---------

(1 row)
```

4、查询数据。

```sql
select * from test;
```

结果返回如下：

```sql
 id |  info
----+---------
  1 | success
(1 row)
```



