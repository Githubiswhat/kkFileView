# TABLE类型

## 功能描述

Vastbase G100在SQL Server兼容模式下支持table类型。table类型作为表值参数，以INPUT输入形式传递到存储过程或函数中，不支持作为OUTPUT参数，不能用来定义列的类型。

## 注意事项

- 本特性仅在数据库兼容模式为SQL Server时能够使用（即创建DB时DBCOMPATIBILITY='MSSQL'），在其他数据库兼容模式下不能使用该特性。
- table类型不能用来定义表中列的类型。
- table类型作为表值输入参数传递到存储过程或者函数中，不能执行注入UPDATE、DELETE或INSERT等DML操作；也不能SELECT INTO语句的目标；不支持作为OUTPUT参数。
- 不能使用ALTER TABLE语句来修改表值参数的表结构。
- 在函数或存储过程中使用`$n`引用表值参数需要添加双引号`“”`。

## 示例

**前置条件：**创建兼容SQL Server的库db_sqlserver，并进入。

```sql
create database db_sqlserver dbcompatibility 'MSSQL';
\c db_sqlserver
```

**示例1：**将含有单列数值型内容的table类型插入至行存表中。

1、创建单列table类型。

```sql
create type tp1 as table(id int1); 
```

2、创建插入表，向其中的表2插入两条数据。

```sql
create table tb1(id int); 
create table tb2(id int,date1 date); 
insert into tb2 values (1,'2022-1-11'); 
insert into tb2 values (2,'2022-1-12'); 
```

3、创建存储过程。

```sql
create or replace procedure proc1(v1 tp1) 
as 
begin 
insert into tb1 select * from v1; 
end; 
/ 
```

4、执行存储过程，将从表2中搜索到的id列数据插入至表1中。

```sql
declare 
a tp1; 
begin 
insert into a select id from tb2; 
perform proc1(a); 
end; 
/ 
```

5、查询表记录。

```sql
select * from tb1 order by id; 
select * from tb2 order by id;  
```

表1记录为来自表2的字段id内容，表2记录为初始插入内容，查询结果依次如下：

```sql
 id
----
  1
  2
(2 rows)

 id |   date1
----+------------
  1 | 2022-01-11
  2 | 2022-01-12
(2 rows)
```

**示例2：** 将含多列数值型内容的table类型插入至行存表中。

1、创建多列table类型。

```sql
create type tp2 as table( 
num_1 numeric, 
num_2 number(5), 
num_3 number(3,3), 
num_4 numeric(4,3), 
num_5 decimal(3,3)
); 
```

2、创建插入表。

```sql
create table tb3( 
num_1 numeric, 
num_2 number(5), 
num_3 number(3,3), 
num_4 numeric(4,3), 
num_5 decimal(3,3))WITH (ORIENTATION = COLUMN); 
```

3、创建存储过程，从table类型的数据中筛选出小于num_1小于6的记录，然后将记录插入至表中。

```sql
create or replace procedure proc1(v1 tp2) 
as 
begin 
insert into tb3 select * from v1 where num_1<6; 
end; 
/ 
```

4、先向table类型a中插入多条数据，然后执行存储过程。

```sql
declare 
a tp2; 
begin 
insert into a values (1,1235,0.123,1.57,0.101); 
insert into a values (2.37,11111,0.003,1.999,-0.001); 
insert into a values (3.1144,12345,0.993,1.68,0.001); 
insert into a values (4.9,129.12,0.3,1,0.91); 
insert into a values (5.999,1235.5,0.1234,9.9993,0.1235); 
insert into a values (6.1,123,0,-0.99,0); 
perform proc1(a); 
end; 
/ 
```

5、查看表记录。

```sql
select * from tb3 order by num_1;
```

查询表结果如下，table类型的数据中满足条件的记录被放入表中：

```sql
 num_1  | num_2 | num_3 | num_4 | num_5
--------+-------+-------+-------+-------
      1 |  1235 |  .123 |  1.57 |  .101
   2.37 | 11111 |  .003 | 1.999 | -.001
 3.1144 | 12345 |  .993 |  1.68 |  .001
    4.9 |   129 |    .3 |     1 |   .91
  5.999 |  1236 |  .123 | 9.999 |  .124
(5 rows)
```

