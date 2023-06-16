# DBMS_UTILITY

## 功能描述

DBMS_UTILITY包提供了很多不同的子程序工具。该内置包包含以下子程序：

<table >
<tr>
<th>函数</th>
<th>描述</th>
</tr>
<tr>
    <td><a href="#UTLCOMMA_TO_TABLE">COMMA_TO_TABLE</a> </td>
<td>用于将逗号间隔的字符串转化成数组类型。</td>
</tr>
<tr>
    <td><a href="#UTLTABLE_TO_COMMA">TABLE_TO_COMMA</a> </td>
<td>用于将数组类型转换成逗号间隔的字符串。</td>
</tr>
<tr>
    <td><a href="#UTLEXEC_DDL_STATEMENT">EXEC_DDL_STATEMENT</a></td>
<td>用于执行DDL语句。</td>
</tr>
<tr>
<td><a href="#UTLFORMAT_CALL_STACK">FORMAT_CALL_STACK</a></td>
<td>用于格式化调用堆栈。该内置函数返回一个格式化的字符串，它显示了执行调用堆栈直至当前函数调用处的所有过程或者函数的调用顺序。该函数可在存储过程、函数或包中调用，并且以可读格式返回当前调用堆栈。</td>
</tr>
<tr>
    <td><a href="#UTLGET_HASH_VALUE">GET_HASH_VALUE</a>  </td>
<td>用于获得在该范围内字符串的hash值。</td>
</tr>
<tr>
    <td><a href="#UTLGET_TIME">GET_TIME</a> </td>
<td>用于记录当前时刻的时间（单位是百分之一秒）。</td>
</tr>
<tr>
      <td><a href="#ANALYZE_SCHEMA">ANALYZE_SCHEMA</a> </td>
<td>对指定schema中所有的表、列、索引列进行抽样估计并重建统计信息。</td>  
</tr>
</table>

## 注意事项

- LNAME_ARRAY、UNCL_ARRAY、NAME_ARRAY类型可以用来定义变量，存放数组数据，配合函数COMMA_TO_TABLE和TABLE_TO_COMMA的出入参。它们对应的类型分别为：
  - DBMS_UTILITY.LNAME_ARRAY：对应varchar2[4000]。
  - DBMS_UTILITY.NAME_ARRAY：对应varchar2[30]。
  - DBMS_UTILITY.UNCL_ARRAY：对应varchar2[227]。
- 执行 DBMS_UTILITY.ANALYZE_SCHEMA的用户需要具有指定模式的 USAGE 权限以及该模式下的表的 VACUUM 权限。

## 内置包子程序

### COMMA_TO_TABLE<a id="UTLCOMMA_TO_TABLE"></a>

**语法格式**

```sql
DBMS_UTILITY.COMMA_TO_TABLE(
list     in VARCHAR2,
tablen   OUT BINARY_INTEGER,
tab      OUT varchar2[]);
```

**参数说明**

- **list**

  VARCHAR2类型，要分隔的字符串。

- **tablen**

  BINARY_INTEGER类型，分隔后的数组长度。

- **tab**

  VARCHAR2[]类型，分隔后的数组。

**注意事项**

- list必须是非空的逗号间隔符类型。字符串里面双引号中的逗号不计算在内。
- 逗号分割列表中的条目不能包含多字节字符，例如连字符（-）。
- tab里的值应为原始列表中剪裁出来的值，不能做任何转换。

**示例**

直接调用COMMA_TO_TABLE函数将逗号间隔的字符串转化成数组类型。

```sql
select dbms_utility.comma_to_table('a,b,c,d'::text);
```

返回结果为：

```sql
 comma_to_table
-----------------
 (4,"{a,b,c,d}")
(1 row)
```

### TABLE_TO_COMMA<a id="UTLTABLE_TO_COMMA"></a>

**语法格式**

```sql
DBMS_UTILITY.TABLE_TO_COMMA(
tab     in varchar2[],
tablen  OUT BINARY_INTEGER,
list    OUT VARCHAR2);
```

**参数说明**

- **tab**

  VARCHAR2[]类型，输入的数组。

- **tablen**

  BINARY_INTEGER类型，输入的数组长度。

- **list**

  VARCHAR2类型，形成的字符串。

**示例**

直接调用TABLE_TO_COMMA函数将数组类型转换成逗号间隔的字符串。

```sql
select dbms_utility.table_to_comma(array['a','b','c','d']);
```

返回结果为：

```sql
 table_to_comma
----------------
 (4,"a,b,c,d")
(1 row)
```


### GET_TIME<a id="UTLGET_TIME"></a>

**语法格式**

```sql
DBMS_UTILITY.GET_TIME
return number;
```

**注意事项**

- 如果需要换算为秒，需要除以100。
- 该函数没有传入参数，返回值为number类型。

**示例**

1、创建存储过程调用GET_TIME获取当前时间值，用于计算操作的执行之间。

```sql
CREATE OR REPLACE FUNCTION get_runtime() returns number as $$
DECLARE
     start_time   NUMBER;
     finish_time  NUMBER;
     elapsed_time NUMBER;
  BEGIN
     start_time := DBMS_UTILITY.get_time();
     perform pg_sleep(1);
    perform pg_sleep(1);
    perform pg_sleep(1);
    perform pg_sleep(1);
    perform pg_sleep(1);
     finish_time := DBMS_UTILITY.get_time();
     elapsed_time := finish_time  - start_time;
     return elapsed_time;
  END
$$ language plpgsql;
```

2、调用函数。

```sql
select get_runtime();
```

返回结果为：

```sql
 get_runtime
-------------
         501
(1 row)
```

### GET_HASH_VALUE<a id="UTLGET_HASH_VALUE"></a>


**语法格式**

```sql
dbms_utility.get_hash_value(
hash_name varchar,
hash_base integer,
hash_size integer);
```

**参数说明**

- **hash_name**

  需要被转化为hash值的字符串。

- **hash_base**

  hash起始位置。

- **hash_size**

  hash大小，不能为0，若为正值表示从hash起始位置到加上hash大小的范围，为负值表示从integer范围排除从hash起始位置减去hash大小绝对值的范围。

**示例**

直接调用GET_HASH_VALUE函数。

```sql
select dbms_utility.get_hash_value('afeaw',0,1000);
```

返回结果为：

```sql
 get_hash_value
----------------
             84
(1 row)
```

###  EXEC_DDL_STATEMENT <a id="UTLEXEC_DDL_STATEMENT"></a>

**语法格式**

```sql
DBMS_UTILITY.EXEC_DDL_STATEMENT (
   parse_string IN VARCHAR2);
```

**参数说明**

**parse_string**

要被执行的DDL语句。

**示例** <a id="EXEC_DDL_STATEMENT_shili"></a>

1、创建存储过程调用EXEC_DDL_STATEMENT 函数。

```sql
CREATE OR REPLACE FUNCTION checkHexCallStack() returns text 
as $$
DECLARE
stack text;
BEGIN
select * INTO stack from dbms_utility.format_call_stack('o');
return stack;
END;
$$ LANGUAGE plpgsql;
begin
Dbms_utility.exec_ddl_statement('create table test_eds(col int);');
end;
/
```

2、查询test_eds表的属性。

```sql
\dti test_eds
```

返回结果如下，表示函数调用成功：

```sql
                                       List of relations
 Schema |   Name  | Type |  Owner |Table|                    Storage
--------+----------+-------+--------+-------+--------------------------------------------
 public | test_eds | table|vastbase|     | {orientation=row,compression=no,fillfactor=80}
(1 row)
```

### FORMAT_CALL_STACK<a id="UTLFORMAT_CALL_STACK"></a>

**语法格式**

```sql
DBMS_UTILITY.FORMAT_CALL_STACK 
  RETURN VARCHAR2;
```

**示例**

示例请参考[EXEC_DDL_STATEMENT示例](#EXEC_DDL_STATEMENT_shili)。

### ANALYZE_SCHEMA<a id="ANALYZE_SCHEMA"></a>

**语法格式**

```sql
DBMS_UTILITY.ANALYZE_SCHEMA (
   schema             IN  VARCHAR2,
   method             IN  VARCHAR2,
   estimate_rows      IN  NUMBER DEFAULT NULL,
   estimate_percent   IN  NUMBER DEFAULT NULL,
   method_opt         IN  VARCHAR2 DEFAULT NULL);
```

**参数说明**

- **schema**  

  schema名称。

- **method** 
  
  操作。支持的取值如下：
  
  - ESTIMATE：估计。
  
  - COMPUTE：计算。
  - DELETE：清空。
  
> <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
  >
  > 当method为 ESTIMATE时，estimate_rows或estimate_percent中至少一个应该有值。

- **estimate_rows**

  估计统计信息所依据的行数。

- **estimate_percent**

  估计统计信息所依据的行百分比。

- **method_opt**
  
  要分析的对象类型。支持如下取值：
  
  - FOR TABLE
  - FOR ALL [INDEXED] COLUMNS [SIZE n]
  - FOR ALL INDEXES

**示例**

1、创建测试表并插入数据。

```sql
create table t_system_part_01
(c1 integer,
c2 date,
c3 text)
partition by system
(
partition p1,
partition p2,
partition p3
);

insert into t_system_part_01 partition(p1) values(1,'2022-01-01','p1');
insert into t_system_part_01 partition(p2) values(2,'2022-02-01','p2');
insert into t_system_part_01 partition(p3) values(3,'2022-03-01','p3');
```

2、创建全局索引。

```sql
create unique index idx_t_system_part_48 on t_system_part_01(c1) global;
```

3、输出查询t_system_part_01的执行计划。

```sql
explain select * from t_system_part_01;
```

返回结果为：

```sql
                                      QUERY PLAN
---------------------------------------------------------------------------------------
 Partition Iterator  (cost=0.00..21.04 rows=1104 width=44)
   Iterations: 3
   ->  Partitioned Seq Scan on t_system_part_01  (cost=0.00..21.04 rows=1104 width=44)
         Selected Partitions:  1..3
(4 rows)
```

4、使用ANALYZE_SCHEMA存储过程对”public“中所有的表进行抽样估计并重建统计信息。

```sql
exec DBMS_UTILITY.ANALYZE_SCHEMA('public','ESTIMATE',null,90,'for table');
```

返回结果为：

```sql
NOTICE:  PL/SQL procedure successfully completed.
 analyze_schema
----------------

(1 row)
```

5、再次输出查询t_system_part_01的执行计划。

```sql
explain select * from t_system_part_01;
```

返回结果如下，可见抽样估计之后查询计划中的此次扫描的成本，估计行数及大小都发生了变化。

```sql
                                    QUERY PLAN
-----------------------------------------------------------------------------------
 Partition Iterator  (cost=0.00..3.03 rows=3 width=15)
   Iterations: 3
   ->  Partitioned Seq Scan on t_system_part_01  (cost=0.00..3.03 rows=3 width=15)
         Selected Partitions:  1..3
(4 rows)
```

