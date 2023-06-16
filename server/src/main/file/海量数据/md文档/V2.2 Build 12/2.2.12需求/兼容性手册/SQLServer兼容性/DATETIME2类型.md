# DATETIME2类型

## 功能描述

Vastbase G100在SQL Server兼容模式下支持datetime2类型。datetime2类型结合了24小时制时间的日期，精确到小数点后6位，其格式为`yyyy-MM-dd HH:mm:ss.ffffff`。

## 语法格式

```
datetime2[($n)]
```

## 参数说明

**$n**

用户指定的精度值，该参数可选。当未设置时，默认最大精度为6。

取值范围：0~6，其中0和6可取

> <div align="left"><img src="image/img2.png" style="zoom:75%"></div>
>
> - 当精度大于6时，会提醒用户最大值为6，并强制将精度修改为6。
> - 当精度小于0时，会报错。

## 注意事项

- 本特性仅在数据库兼容模式为SQL Server时能够使用（即创建DB时DBCOMPATIBILITY='MSSQL'），在其他数据库兼容模式下不能使用该特性。
- 当插入字段值的精度超过预设的精度时，只保留值到预设精度；当插入值小于预设精度时，以插入值为准。

## 示例

**前置条件：**创建兼容SQL Server的库db_sqlserver，并进入。

```sql
create database db_sqlserver dbcompatibility  'MSSQL';
\c db_sqlserver
```

**示例1：**创建带datetime2的数据类型的测试表，并插入数据。

1、创建带datetime2字段的测试表。

```sql
create table test_1(id int,col1 datetime2);
```

2、向测试表中插入带公元、时区的记录。

```sql
insert into test_1 values(1,'294277-1-9 04:00:49.101010');
insert into test_1 values(2,'4713-12-31 23:59:59.999999 bc');
insert into test_1 values(3,'713-12-31 23:00:00.111 +08');
insert into test_1 values(4,'713-12-31 23:00:00.111 bc +08');
insert into test_1 values(5,'294277-1-9 04:00:49.101010 AD'),(7,null);
```

> <div align="left"><img src="image/img1.png" style="zoom:75%")</div> 
>
> - datetime2类型是在插入数据时，会忽略时区。
> - AD表示公元后，BC表示公元前。当日期不带BC时默认就是公元后，所以在查询记录时，公元后的日期可以不显示AD，公元前的日期必须带有BC。

3、查询插入记录在表中的情况。

```sql
select * from test_1 order by col1;
```

结果展示为：

```sql
 id |             col1
----+-------------------------------
  7 |
  2 | 4713-12-31 23:59:59.999999 BC
  4 | 0713-12-31 23:00:00.111 BC
  3 | 0713-12-31 23:00:00.111
  1 | 294277-01-09 04:00:49.10101
  5 | 294277-01-09 04:00:49.10101
(6 rows)
```

**示例2：**创建带指定精度范围的datetime2类型的表。

1、创建测试表。

```sql
create table test_2(col1 datetime2(3));
```

2、向测试表中插入数据。

```sql
insert into test_2 values('0713-12-31 23:00:00.1');
insert into test_2 values('0713-12-31 23:00:00.111');
insert into test_2 values('0713-12-31 23:00:00.111111');
```

3、查询测试表中的数据。

```sql
select * from test_2;
```

查询结果显示为不同精度的数据插入测试表后，小数点后都保持为三位：

```sql
          col1
-------------------------
 0713-12-31 23:00:00.1
 0713-12-31 23:00:00.111
 0713-12-31 23:00:00.111
```

**示例3：**对datetime2字段指定不同的精度创建表。

1、创建带不同精度的datetime2字段的测试表。

```sql
create table test1(col1 datetime2(0));
create table test2(col1 datetime2(6));
create table test(col1 datetime2(7));
create table test(col1 datetime2(-1));
```

返回结果分别为前两条测试语句成功执行；第三条语句创建表成功，但由于精度超过6提示deduced to 6，即表示创建成功，精度被修改为最高值6；第四条执行失败，因为精度值不能为负数：

```sql
CREATE TABLE

CREATE TABLE

WARNING:  TIMESTAMP(7) precision reduced to maximum allowed, 6
LINE 1: create table test(col1 datetime2(7));
                               ^
WARNING:  TIMESTAMP(7) precision reduced to maximum allowed, 6
CREATE TABLE

ERROR:  syntax error at or near "-"
LINE 1: create table test(col1 datetime2(-1));
                                         ^
```