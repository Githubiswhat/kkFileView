# REF CURSOR支持ROWTYPE和RECORD类型

## 功能描述

定义REF CURSOR时可以指定return type，return type的类型支持%rowtype类型和record类型。

## 注意事项

该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建并切换至兼容模式为Oracle的数据库db_oracle。

```sql
CREATE DATABASE db_oracle dbcompatibility='A';    
\c db_oracle
```

 **示例1：** 在存储过程中使用游标返回%rowtype table类型。

1、创建测试表并插入数据。

```sql
create table user_table
(
user_id number(10,0),
ca_card VARCHAR(60),
dept_id number,
is_del number
);
insert into user_table(user_id,ca_card,dept_id,is_del) values(1,'card1',101,1);
insert into user_table(user_id,ca_card,dept_id,is_del) values(2,'card2',102,0);
insert into user_table(user_id,ca_card,dept_id,is_del) values(3,'card3',103,1);
```

2、创建存储过程。

```sql
create or replace procedure test_return_cursor()
as
type ref_cur_type is ref cursor return user_table%rowtype;
my_cur ref_cur_type;
var user_table%rowtype;
begin
open my_cur for select * from user_table;
loop
fetch my_cur into var;
EXIT WHEN my_cur %NOTFOUND;
raise notice '%',var;
end loop;
close my_cur;
end;
/
```

3、调用存储过程。

```sql
call test_return_cursor();
```

返回结果为：

```sql
NOTICE:  (1,card1,101,1)
NOTICE:  (2,card2,102,0)
NOTICE:  (3,card3,103,1)
 test_return_cursor
--------------------

(1 row)
```

**示例2：**  在存储过程中使用record嵌套表。

1、创建自定义类型。

```sql
create or replace type nationalstringarray is table of nvarchar2(2000);
create or replace type key_value_array_t is object (key varchar2(1024),value_array nationalstringarray);
create or replace type key_value_array_tab_t is table of key_value_array_t ;
```

2、创建包和包体。

```sql
create or replace package pk4 is
type t_record is record (key varchar2(100), na_val nationalstringarray);
type t_record_cur is ref cursor return t_record ;
function key_vatt return key_value_array_tab_t ;
procedure test(i integer);
end pk4;
/

create or replace package body pk4 is
function key_vatt return key_value_array_tab_t is
vaary nationalstringarray := nationalstringarray();
kvat key_value_array_t;
vatt key_value_array_tab_t :=key_value_array_tab_t();
begin
vaary.extend();
vaary(1) := 'abc100';
vaary.extend();
vaary(2) := 'abc200';
vaary.extend();
vaary(3) := 'abc300';
kvat :=key_value_array_t('keys_string_1',vaary);
vatt.extend();
vatt(1) := kvat;
vaary.extend();
vaary(4) := 'ABC100';
vaary.extend();
vaary(5) := 'ABC200';
vaary.extend();
vaary(6) := 'ABC300';
kvat :=key_value_array_t('keys_string_2',vaary);
vatt.extend();
vatt(2) := kvat;
return vatt;
end ;
procedure test(i integer) as
t1 t_record;
v_cur t_record_cur;
begin
open v_cur for select * from table(key_vatt()) ;
loop
Fetch v_cur InTo t1;
Exit When v_cur%NotFound;
raise info '%',t1;
end loop;
close v_cur;
end;
end pk4;
/
```

3、创建存储过程并使用record嵌套表。

```sql
create or replace procedure test_return_cursor()
as
type ty_record is record (key varchar2(100), na_val nationalstringarray);
type ref_cur_type is ref cursor return ty_record;
my_cur ref_cur_type;
var ty_record;
begin
open my_cur for select * from table(pk4.key_vatt());
loop
fetch my_cur into var;
EXIT WHEN my_cur %NOTFOUND;
raise notice '%',var;
end loop;
close my_cur;
end;
/
```

4、调用存储过程。

```sql
call test_return_cursor();
```

返回结果为：

```sql
NOTICE:  (keys_string_1,"{abc100,abc200,abc300}")
NOTICE:  (keys_string_2,"{abc100,abc200,abc300,ABC100,ABC200,ABC300}")
 test_return_cursor
--------------------

(1 row)
```





