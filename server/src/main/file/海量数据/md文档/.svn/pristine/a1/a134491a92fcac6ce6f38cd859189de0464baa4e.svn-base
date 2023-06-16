# DATENAME

## 功能描述

Vastbase G100在SQL Server兼容模式下支持DATENAME函数，用于返回表示指定 date 的指定 datepart 的字符串，返回值为varchar类型。

## 语法格式

```sql
DATENAME(datepart,date)
```

## 参数说明

- **datepart**

  datepart将返回表示 date 参数的特定部分的整数，支持的datepart参数如下所示：

  <table>
      <th>datepart</th>
      <th>缩写形式</th>
      <th>备注</th>
      <tr>
      <td>year</td>
      <td>yy,yyyy</td>
      <td>年</td></tr>
      <tr>
      <td>quarter</td>
      <td>qq,q</td>
      <td>季度</td></tr>
      <tr>
      <td>month</td>
      <td>mm,m</td>
      <td>月</td></tr>
      <tr>
      <td>dayofyear</td>
      <td>dy,y</td>
      <td>一年中的第几天</td></tr>
      <tr>
      <td>day</td>
      <td>dd,d</td>
      <td>日</td></tr>
      <tr>
      <td>week</td>
      <td>wk,ww</td>
      <td>一年中的第几周</td></tr>
      <tr>
      <td>weekday</td>
      <td>dw,w</td>
      <td>星期几</td></tr>
      <tr>
      <td>hour</td>
      <td>hh</td>
      <td>小时</td></tr>
      <tr>
      <td>minute</td>
      <td>mi,n</td>
      <td>分钟</td></tr>
      <tr>
      <td>second</td>
      <td>ss,s</td>
      <td>秒</td></tr>
      <tr>
      <td>millisecond</td>
      <td>ms</td>
      <td>毫秒</td></tr>
      <tr>
      <td>microsecond</td>
      <td>mcs</td>
      <td>微秒</td></tr>
      <tr>
      <td>nanosecond</td>
      <td>ns</td>
      <td>毫微秒</td></tr>
      <tr>
      <td>tzoffset</td>
      <td>tz</td>
      <td>时区偏移量</td></tr>
      <tr>
      <td>iso_week</td>
      <td>isowk,isoww</td>
      <td>ISO标准周数</td></tr>
  </table>

- **date**

  要返回datepart值的日期、时间或时间戳表达式。日期表达式必须包含datepart类型的值。

  可解析为下列值之一的表达式：
  
  - time
  - timetz
  - date 
  - timestamp 
  - timestamptz

## 注意事项

该功能仅在数据库兼容模式为SQL Server时能够使用（即创建DB时DBCOMPATIBILITY='MSSQL'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建兼容模式为SQL Server的库db_sqlserver，并进入。

```sql
CREATE DATABASE db_sqlserver DBCOMPATIBILITY='MSSQL';
\c db_sqlserver
```

**示例1：**直接调用DATENAME函数。

```sql
SELECT datename(month,getdate());
```

返回结果如下：

```sql
 datename
----------
 February
(1 row)
```

**示例2：**在sql语句中调用DATENAME函数。

1、创建测试表。

```sql
create table test(id int,col1 text,col2 varchar,col3 char(8),col4 varchar2(10));
```

2、在INSERT语句中使用。

```sql
insert into test values(1,datename(yyyy,getdate()),
datename(month,'2022-12-11'),datename(day,'1999-09-18'),datename(week,'2099-12-01'));
insert into test(id) values(2);
```

3、在UPDATE语句中使用。

```sql
update test set col3=datename(second,'1999-09-18 20:59:59') where id=2;
```

4、查询结果。

```sql
SELECT * FROM test;
```

结果返回如下：

```sql
 id | col1 |   col2   |   col3   | col4
----+------+----------+----------+------
  1 | 2023 | December | 18       | 49
  2 |      |          | 59       |
(2 rows)
```

