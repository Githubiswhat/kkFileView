# CREATE TRIGGER

## 功能描述

Vastbase G100在Oracle兼容模式下支持在创建触发器的语句中包含触发器执行的PL/SQL块。即允许DML触发器或边界触发器直接使用一条语句创建触发器，而不需要提前创建触发器函数或过程。

## 语法格式

```sql
CREATE [ OR REPLACE ][ CONSTRAINT ] TRIGGER trigger_name { BEFORE | AFTER | INSTEAD OF } { event [ OR ... ] }
    ON table_name
    [ FROM referenced_table_name ]
    { NOT DEFERRABLE | [ DEFERRABLE ] { INITIALLY IMMEDIATE | INITIALLY DEFERRED } }
    [ FOR [ EACH ] { ROW | STATEMENT } ]
    [ WHEN ( condition ) ]
    [ DECLARE declarations ]
    BEGIN
     statements
    END [ trigger_name ];
```

## 参数说明

- **trigger_name**

  触发器名称。

  取值范围：符合标识符命名规范的字符串，且最大长度不超过63个字符。

- **OR REPLACE**

	支持创建触发器时使用replace，replace仅对同名同表触发器进行替代，同名触发器不同表时不支持替代。

- **statements**

	PL/SQL块的语句部分。可以包含创建触发器的语句。

> <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
>
> 更多参数说明请参考：[CREATE TRIGGER](开发者指南/CREATE-TRIGGER.md)。

## 注意事项

该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

## 示例

1、创建并切换至兼容模式为Oracle的数据库下。

```sql
create database db_1132436 dbcompatibility='A';
\c db_1132436;
```

2、创建测试表。

```sql
create table employees(id int,salary int);
```

3、插入测试数据。

```sql
insert into employees values(1,6000);
```

4、创建DML触发器。

```sql
create or replace trigger t
before insert or update of salary,id 
or delete on employees
begin
case
when inserting then
dbms_output.put_line('inserting');
when updating ('salary') then
dbms_output.put_line('updating salary');
when updating ('id') then
dbms_output.put_line('updating id');
when deleting then
dbms_output.put_line('deleting');
end case;
end;
/
```

5、设置serveroutput 为on（允许将dbms_output.put_line的输出信息输出至vsql的命令界面的屏幕上）。

```sql
set serveroutput on;
```

6、insert触发。

```sql
insert into employees values(2,7000);
```

返回结果为：

```sql
inserting
INSERT 0 1
```

7、update触发。

```sql
update employees set salary= salary+1000 where id=1;
```

返回结果为：

```sql
updating salary
UPDATE 1
```

8、delete触发。

```sql
delete employees where id=2;
```

返回结果为：

```sql
deleting
DELETE 1
```

