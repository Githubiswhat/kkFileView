# ALTER FUNCTION

放入MySQL兼容性-SQL语法

## 功能描述

在MySQL兼容模式下，函数创建成功后可以使用ALTER FUNCTION语法修改[characteristic](#characteristic)部分特性。

## 语法格式

```sql
ALTER FUNCTION func_name [characteristic ...]
```

其中characteristic语法如下：<a id="characteristic">

```sql
COMMENT 'string'
 |LANGUAGE SQL
 |[NOT]DETERMINISTIC
 |{CONTAINS SQL|NO SQL|READ SQL DATA|MODIFIES SQL DATA}
 |SQL SECURITY {DEFINER|INVOKER}
```

## 参数说明

-  **func_name** 

  创建的函数名称，可以带有模式名。

  取值范围：字符串，要符合标识符的命名规范。

-  **LANGUAGE SQL**  

  用以实现函数的语言的名称。在函数中如果使用LANGUAGE SQL用法，则在plsql_body 部分除了出现SQL语句时能正常执行以外，出现逻辑代码如变量赋值、逻辑控制、异常处理等行为时也可以正常执行。

   与[NOT] DETERMINISTIC 的顺序可以随意排列。

-  **[NOT] DETERMINISTIC**  

  说明函数执行的结果是否正确。

  - DETERMINISTIC：表示结果确定。每次执行函数时，相同的输入会得到相同的输出。
  - NOT DETERMINISTIC：表示结果不确定，相同的输入可能得到不同的输出。

-  **CONTAINS SQL** 

  表示函数中不包含读或写数据的语句。

-  **NO SQL** 

  表示函数中不包含SQL语句。

-  **READ SQL DATA** 

  表示函数中包含读数据的语句，但不包含写数据的语句。

-  **MODIFIES SQL DATA** 

  表示函数中包含写数据的语句。

-  **SQL SECURITY** 

  表示函数的权限。

  默认值为：SQL SECURITY DEFINER。

  - SECURITY INVOKER：表明该函数将带着调用它的用户的权限执行。该参数可省略。
  - SECURITY DEFINER：声明该函数将以创建它的用户的权限执行。

## 注意事项

- characteristic特性仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- ALTER 语句可用于更改存储过程的特性，并且可以指定多个更改，但是不支持更改存储过程的参数和主体代码。
- 不支持修改DETERMINISTIC特性。
- MySQL兼容模式下支持使用ALTER修改函数时，函数名不带参数列表。

## 示例

1、创建兼容MySQL的数据库db_mysql。

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

结果返回如下：

```sql
 id | age | name
----+-----+------
  1 |  22 | 小王
  2 |  23 | 小张
  3 |  21 | 小弓
(3 rows)
```

3、创建测试函数。

```sql
create function func_test(id1 int,age1 int,name1 varchar2) return bigint 
NOT DETERMINISTIC
CONTAINS SQL
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

4、修改函数属性。

```sql
ALTER FUNCTION func_test 
LANGUAGE SQL 
NO SQL 
SQL SECURITY INVOKER;
```

结果返回如下，表示修改成功：

```sql
ALTER FUNCTION
```

