# 管道函数pipelined

## 功能描述

Vastbase G100支持自定义管道函数（使用关键字pipelined）时，返回值pipe row支持自定义type类型。

在Oracle兼容模式下，用户创建package时可以使用type…is来定义数据类型，具体请参见：[package中支持type…is语法](../PL/SQL/package中支持type…is语法.md)。

## 语法格式

```sql
pipe row(type)
```

## 参数说明

**type**

自定义类型。

从V2.2 Build 11版本开始支持如下自定义类型：

- type user_type is record(col_name datatype, col2_name datatype) 
- type user_ type is table of datatype
- type user_type1 is record(col_name datatype, col2_name user_type)
- type user_type is record(col_name tablename.column_name%type, col2_name tablename.column_name%type)

## 注意事项

该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建并切换至兼容模式为Oracle的数据库。

```sql
CREATE DATABASE db_oracle DBCOMPATIBILITY 'A';
\c db_oracle
```

**示例1：**包中定义的类型：type user_type is record(col_name datatype, col2_name datatype) 。

包中定义type ... is record 时间类型作为pipe row传入参数。

1、创建包。

```sql
create or replace package pk_1018259_03 as
type record_time is record(type_date date,type_time_no_tz time(3) without time zone,type_time_tz time(3) with time zone,type_timestamp_no_tz timestamp(3) without time zone,type_timestamp_tz timestamp(3) with time zone,type_day_2_second interval day to second(3),type_interval interval SECOND(3));
type record_time_tab is table of record_time;
function func_1018259_03 return record_time_tab pipelined;
end;
/
```

2、创建包体。

```sql
create or replace package body pk_1018259_03 is
function func_1018259_03 return record_time_tab pipelined is record_time_rec_value record_time;
begin
record_time_rec_value.type_date:='16-SEP-2022';
record_time_rec_value.type_time_no_tz:='13:11:00.45632';
record_time_rec_value.type_time_tz:='13:11:00.12332 +07';
record_time_rec_value.type_timestamp_no_tz:='2022-07-18 15:00:17.004775+08';
record_time_rec_value.type_timestamp_tz:='2022-07-18 15:00:17.004775+08';
record_time_rec_value.type_day_2_second:=INTERVAL '36003.0006' SECOND;
record_time_rec_value.type_interval:='36003.0006';
pipe row(record_time_rec_value); ---pipe row中传入自定义type
return;
end;
end pk_1018259_03;
/
```

3、调用包内函数。

```sql
select * from table(pk_1018259_03.func_1018259_03());
```

返回结果为：

```sql
     type_date      | type_time_no_tz |  type_time_tz   |  type_timestamp_no_tz   |     type_timestamp_tz      | type_day_2_second | type_interval
---------------------+-----------------+-----------------+-------------------------+----------------------------+-------------------+---------------
 2022-09-16 00:00:00 | 13:11:00.456    | 13:11:00.123+07 | 2022-07-18 15:00:17.005 | 2022-07-18 15:00:17.005+08 | 10:00:03.001      | 10:00:03.001
(1 row)
```

**示例2：**包中定义的类型：type user_ type is table of datatype。

包中定义type ... is table of 数值类型作为pipe row传入参数。

1、创建package。

```sql
create or replace package pk_1018259_08 as
type int_tab is table of int;
function func_1018259_08(x int) return int_tab pipelined;
end;
/
```

2、创建 package body。

```sql
create or replace package body pk_1018259_08 is
function func_1018259_08(x int) return int_tab pipelined is
begin
for i in 1..x loop
pipe row(i);
end loop;
return;
end;
end pk_1018259_08;
/
```

3、调用包内函数。

```sql
select * from table(pk_1018259_08.func_1018259_08(5));
```

返回结果为：

```sql
 column_value
--------------
            1
            2
            3
            4
            5
(5 rows)
```

**示例3：**包中定义的类型：type user_type1 is record(col_name datatype, col2_name user_type)。

包中定义type ... is record 自定义类型作为pipe row传入参数。

1、创建自定义类型。

```sql
create type ty_1018259_06 is table of nvarchar2(30);
```

2、创建package。

```sql
create or replace package pk_1018259_06 as
type record_customer is record(type_int int,type_customer ty_1018259_06);
type record_customer_tab is table of record_customer;
function func_1018259_06 return record_customer_tab pipelined;
end;
/
```

3、创建 package body。

```sql
create or replace package body pk_1018259_06 is
function func_1018259_06 return record_customer_tab pipelined is record_customer_rec_value record_customer;
begin
record_customer_rec_value.type_int:=1;
record_customer_rec_value.type_customer:=ty_1018259_06('a1','a2');
pipe row(record_customer_rec_value);
return;
end;
end pk_1018259_06;
/
```

4、调用包内函数。

```sql
select * from table(pk_1018259_06.func_1018259_06());
```

返回结果为：

```sql
 type_int | type_customer
----------+---------------
        1 | {a1,a2}
(1 row)
```

**示例4：**包中定义的类型：type user_type is record(col_name tablename.column_name%type, col2_name tablename.column_name%type)。

包中定义type ... is record %type作为pipe row传入参数。

1、创建表。

```sql
create table tab_1018259_07(id int,name varchar2(20));
```

2、创建package。

```sql
create or replace package pk_1018259_07 as
type record_type is record(type_int tab_1018259_07.id%type,type_varchar2 tab_1018259_07.name%type);
type rrecord_type_tab is table of record_type;
function func_1018259_07 return rrecord_type_tab pipelined;
end;
/
```

3、创建 package body。

```sql
create or replace package body pk_1018259_07 is
function func_1018259_07 return rrecord_type_tab pipelined is record_type_rec_value record_type;
begin
record_type_rec_value.type_int:=1;
record_type_rec_value.type_varchar2:='测试abc123';
pipe row(record_type_rec_value);
return;
end;
end pk_1018259_07;
/
```

4、调用包内函数。

```sql
select * from table(pk_1018259_07.func_1018259_07());
```

返回结果为：

```sql
 type_int | type_varchar2
----------+---------------
        1 | 测试abc123
(1 row)
```

