# EMPTY_CLOB

## 功能描述

EMPTY_CLOB函数用于构造空的LOB供后续使用流插入。调用函数后将返回空的CLOB。

## 语法格式

```sql
empty_clob()
```

## 参数说明

无。

## 注意事项

无。

## 示例

**示例1：**insert语句中使用empty_clob函数。

1、创建测试表并插入数据。

```sql
create table oracle_empty_clob(a int,b clob);
insert into oracle_empty_clob values(1,empty_clob());
```

2、查看插入结果。

```sql
select * from oracle_empty_clob;
```

返回结果为：

```sql
 a | b
---+---
 1 |
(1 row)
```

**示例2：**在匿名块中使用empty_clob函数。

1、设置serveroutput 为on（允许将dbms_output.put_line的输出信息输出至vsql的命令界面的屏幕上）。 

```sql
set serveroutput on;
```

2、创建匿名块，打印出判断empty_clob函数的返回值是否为空的结果。

```sql
DECLARE
dummy clob;
BEGIN
dummy := empty_clob();
IF dummy = empty_clob() THEN
dbms_output.put_line( 'Dummy is empty' );
ELSE
dbms_output.put_line( 'Dummy is not empty' );
END IF;
END;
/
```

返回结果为：

```sql
Dummy is empty
ANONYMOUS BLOCK EXECUTE
```

**示例3：**在函数中使用empty_clob函数。

1、创建函数。

```sql
CREATE OR REPLACE FUNCTION blob_to_varchar (blob_in IN BLOB)
RETURN VARCHAR2
IS
v_varchar VARCHAR2(4000);
v_start PLS_INTEGER := 1;
v_buffer PLS_INTEGER := 4000;
BEGIN
if DBMS_LOB.GETLENGTH(blob_in) is null then
return empty_clob();
end if;
DBMS_OUTPUT.put_line('TEST:' || CEIL(DBMS_LOB.GETLENGTH(blob_in)));
FOR i IN 1..CEIL(DBMS_LOB.GETLENGTH(blob_in) / v_buffer)
LOOP
v_varchar := UTL_RAW.CAST_TO_VARCHAR2(DBMS_LOB.SUBSTR(blob_in, v_buffer, v_start));
v_start := v_start + v_buffer;
END LOOP;
RETURN v_varchar;
end;
/
```

2、调用函数。

```sql
select blob_to_varchar(utl_raw.cast_to_raw('abcd')) from dual;
select blob_to_varchar(utl_raw.cast_to_raw('测试')) from dual;
select blob_to_varchar(utl_raw.cast_to_raw(' ')) from dual;
select blob_to_varchar(null) from dual;
```

返回结果依次为：

```sql
TEST:4
 blob_to_varchar
-----------------
 abcd
(1 row)

TEST:6
 blob_to_varchar
-----------------
 测试
(1 row)

TEST:1
 blob_to_varchar
-----------------

(1 row)

 blob_to_varchar
-----------------

(1 row)
```

