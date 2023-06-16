### DBMS_UTILITY

**功能描述**

DBMS_UTILITY包提供了很多不同的子程序工具。该内置包包含以下函数：

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
</table>

**注意事项**

LNAME_ARRAY、UNCL_ARRAY、NAME_ARRAY类型用于配合函数comma_to_table、table_to_comma的出入参。它们对应的类型分别为：

- LANAME_ARRY：由bigint作为索引的varchar（4000）[]或者varchar2（4000）[]的数组。
- NAME_ARRY：由bigint作为索引的varchar（30）[]或者varchar2（30）[]的数组。
- UNCL_ARRY：由bigint作为索引的varchar（227）[]或者varchar2（227）[]的数组。

**兼容性**

Vastbase G100 V2.2版本与Oracle 11g R2版本相比，不支持的函数有：CANONICALIZE、COMPILE_SCHEMA、CREATE_ALTER_TYPE_ERROR_TABLE、CURRENT_INSTANCE Function、DATA_BLOCK_ADDRESS_BLOCK、DATA_BLOCK_ADDRESS_FILE、DB_VERSION、EXPAND_SQL_TEXT、FORMAT_ERROR_BACKTRACE、FORMAT_ERROR_STACK、GET_CPU_TIME、GET_DEPENDENCY、GET_ENDIANNESS、GET_DEPENDENCY、GET_PARAMETER_VALUE、GET_SQL_HASH、GET_TZ_TRANSITIONS、INVALIDATE、IS_BIT_SET、IS_CLUSTER_DATABASE、MAKE_DATA_BLOCK_ADDRESS、NAME_RESOLVE、NAME_TOKENIZE、OLD_CURRENT_SCHEMA、OLD_CURRENT_USER、PORT_STRING、SQLID_TO_SQLHASH、VALIDATE、WAIT_ON_PENDING_DML。

**示例**

- 创建并切换至兼容模式为Oracle的数据库db_oracle。

```
CREATE DATABASE testoracle dbcompatibility='A';    
\c db_oracle
```

- format_call_stack函数exec_ddl_statement函数

```
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

查询test_eds表，返回结果如下，则表示函数调用成功：

```
\dti test_eds
                                       List of relations
 Schema |   Name   | Type  |  Owner   | Table |                    Storage
--------+----------+-------+----------+-------+------------------------------------------------
 public | test_eds | table | vastbase |       | {orientation=row,compression=no,fillfactor=80}
(1 row)


```

- table_to_comma函数和comma_to_table函数
- 设置serveroutput为true。

```
select dbms_output.serveroutput(true);
```

- 使用存储过程调用函数。

```
declare
t_vararray dbms_utility.lname_array;
vc_stringlist varchar2(4000);
n_idx binary_integer;
begin
vc_stringlist := 'lname,ARRAY';
dbms_utility.comma_to_table(vc_stringlist, n_idx, t_vararray);
dbms_output.put_line('Total Num : '||to_char(n_idx));
for i in 1..n_idx loop
dbms_output.put_line(t_vararray[i]);
t_vararray(i) := '['||t_vararray[i]||']';
dbms_output.put_line('<<'||t_vararray[i]);
end loop;
n_idx := n_idx - 1;
dbms_utility.table_to_comma(t_vararray, n_idx, vc_stringlist);
dbms_output.put_line('>>>>>');
dbms_output.put_line(''||vc_stringlist);
end;
/
```

返回结果如下，则表示函数调用成功：

```
Total Num : 2
lname
<<[lname]
ARRAY
<<[ARRAY]
>>>>>
[lname],[ARRAY]

```

- get_time函数

```
CREATE OR REPLACE FUNCTION get_runtime() returns number as $$
DECLARE
  start_time   NUMBER;
  finish_time  NUMBER;
  elapsed_time NUMBER;
BEGIN
  start_time := DBMS_UTILITY.get_time();
  perform pg_sleep(2);
  finish_time := DBMS_UTILITY.get_time();
  elapsed_time := finish_time  - start_time;
  return elapsed_time;
END
$$ language plpgsql;
```

调用get_runtime函数，返回结果如下，则表示调用成功：

```
call get_runtime();
 get_runtime
-------------
         200
(1 row)
```

- get_hash_value函数

```
select dbms_utility.get_hash_value('1242',0,1000);
```

返回结果如下，则表示函数调用成功：

```
 get_hash_value
----------------
            984
(1 row)
```

#### COMMA_TO_TABLE函数<a id="UTLCOMMA_TO_TABLE"></a>

**语法格式**

```
DBMS_UTILITY.COMMA_TO_TABLE(
list in VARCHAR|VARCHAR2,
tablen OUT BIGINT,
tab  OUT varchar[]);
```

**参数说明**

- list：逗号间隔的字符串。
- Tablen：数组类型的数量。
- Tab：数组类型的数值。

**注意事项**

- list必须是费控的逗号间隔符类型。字符串里面双引号中的逗号不极端在内。
- 逗号分割列表中的条目不能包含多字节字符，例如连字符（-）。
- tab里的值应为原始列表中剪裁出来的值，不能做任何转换。


#### TABLE_TO_COMMA函数<a id="UTLTABLE_TO_COMMA"></a>

**语法格式**

```
DBMS_UTILITY.TABLE_TO_COMMA(
tab in varchar[],
tablen OUT BIGINT,
list  OUT VARCHAR|VARCHAR2);
```

**参数说明**

- list：逗号间隔的字符串。
- Tablen：数组类型的数量。
- Tab：数组类型的数值。


#### GET_TIME函数<a id="UTLGET_TIME"></a>

**语法格式**

```
DBMS_UTILITY.GET_TIME
return number;
```

**注意事项**

- 如果需要换算为秒，需要除以100。
- 该函数没有传入参数，返回值为number类型。


#### GET_HASH_VALUE函数<a id="UTLGET_HASH_VALUE"></a>


**语法格式**

```
dbms_utility.get_hash_value(
hash_name varchar,
hash_base integer,
hash_size integer);
```

**参数说明**

- hash_name：需要被转化为hash值的字符串。
- hash_base：hash起始位置。
- hash_size：hash大小，不能为0，若为正值表示从hash起始位置到加上hash
  大小的范围，为负值表示从integer范围排除从hash起始位置减去hash大小绝对值的范围。


####  EXEC_DDL_STATEMENT  函数<a id="UTLEXEC_DDL_STATEMENT"></a>

**语法格式**

```
DBMS_UTILITY.EXEC_DDL_STATEMENT (
   parse_string IN VARCHAR2);
```

**参数说明**

- parse_string：要被执行的DDL语句。

#### FORMAT_CALL_STACK函数<a id="UTLFORMAT_CALL_STACK"></a>

**语法格式**

```
DBMS_UTILITY.FORMAT_CALL_STACK 
  RETURN VARCHAR2;
```
