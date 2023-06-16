# SQL_VARIANT类型

## 功能描述

Vastbase G100在SQL Server兼容模式下支持sql_variant类型，sql_variant类型可以保存非用户定义类型（除特殊说明的类型）的值，并保留原类型信息，可以用在列、参数、变量和函数返回值中。

## 注意事项

- 本特性仅在数据库兼容模式为SQL Server时能够使用（即创建DB时DBCOMPATIBILITY='MSSQL'），在其他数据库兼容模式下不能使用该特性。
- 基础类型的二进制长度必须 <= 8000字节。
- 本类型支持在表、视图、匿名块、存储过程和自定义函数中使用。
- sql_variant不支持的类型有：text、ntext、image、varchar(max)、nvarchar(max)、varbinary(max)、xml、timestamp和用户自定义类型。

## 示例

> <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
>
> sql_variant类型会自动补齐8000字节，所以实际查询结果中带大量补齐内容。为方面用户查看示例结果，本文档中删去了自动补齐的部分。

**前置条件：**创建兼容SQL Server的库db_mysql，并进入。

```sql
create database db_sqlserver dbcompatibility  'MSSQL';
\c db_sqlserver
```

**示例1：**在系统表中查找sql_variant类型。

```sql
\x    --列式展示查询结果
select * from pg_type where typname='sql_variant';
```

结果显示为：

```sql
-[ RECORD 1 ]--+----------------
typname        | sql_variant
typnamespace   | 11
typowner       | 10
typlen         | -1
typbyval       | f
typtype        | b
typcategory    | U
typispreferred | f
typisdefined   | t
typdelim       | ,
typrelid       | 0
typelem        | 0
typarray       | 0
typinput       | sql_variantin
typoutput      | sql_variantout
typreceive     | sql_variantrecv
typsend        | sql_variantsend
typmodin       | -
typmodout      | -
typanalyze     | -
typalign       | i
typstorage     | x
typnotnull     | f
typbasetype    | 0
typtypmod      | -1
typndims       | 0
typcollation   | 100
typdefaultbin  |
typdefault     |
typacl         |
```

**示例2：**字符类型强制转换为sql_variant类型。

```sql
select 'aa'::char::sql_variant;
select '圈圈圆圆圈圈天天'::char(20)::sql_variant;
select 'aaaaaaaa'::text::sql_variant;
```

结果除了text类型转换失败，其余均成功。其中第二条查询语句中超出基础类型规定长度的内容被截断，合规长度内容被转为sql_variant类型，其结果展示为：

```sql
sql_variant
---------------
 a
(1 row)

sql_variant
---------------
 圈圈圆圆圈圈
(1 row)

ERROR:  cannot cast type text to sql_variant
CONTEXT:  referenced column: sql_variant
```

**示例3：**sql_variant对基础类型二进制长度要求<=8000。

1、基础类型二进制长度小于等于8000。

```sql
select '我的'::varchar(7999)::sql_variant;
select '你的'::varchar(8000)::sql_variant;
```

结果显示为：

```sql
sql_variant
---------------
 我的
(1 row)

sql_variant
---------------
 你的
(1 row)
```

2、基础类型二进制长度大于8000。

```sql
select repeat('qqqqqqqq',1001)::varchar(8001)::sql_variant;
```

结果显示为超出范围，类型转换会失败：

```sql
ERROR:  value of basic type must be a binary length <= 8000 byte
CONTEXT:  referenced column: repeat
```

