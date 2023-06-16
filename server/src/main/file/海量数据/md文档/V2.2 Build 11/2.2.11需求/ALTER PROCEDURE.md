# ALTER PROCEDURE

## 功能描述

在MySQL兼容模式下，存储过程创建成功后可以使用ALTER PROCEDURE语法修改[characteristic](#characteristic)部分特性。

## 语法格式

```sql
ALTER PROCEDURE proc_name[characteristic...]
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

- **proc_name**

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

## 注意事项

- characteristic特性仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- ALTER 语句可用于更改存储过程的特性，并且可以指定多个更改，但是不支持更改存储过程的参数和主体代码。
- 不支持修改DETERMINISTIC特性。
- MySQL兼容模式下支持使用ALTER修改存储过程时，存储过程名称不带参数列表。

## 示例

1、创建并进入兼容MySQL的库db_mysql。

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

3、创建测试存储过程。

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

4、修改存储过程特性。

```sql
ALTER PROCEDURE pro_test2 
LANGUAGE SQL
CONTAINS SQL
SQL SECURITY INVOKER;
```

结果返回如下，表示修改成功。

```sql
ALTER PROCEDURE
```

