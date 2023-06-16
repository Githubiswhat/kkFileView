# DATEADD

## 功能描述

Vastbase G100在SQL Server兼容模式下支持DATEADD函数，用于在日期中添加或减去指定的时间间隔。

## 语法格式

```sql
DATEADD(datepart,number,date)
```

## 参数说明

- **datepart**

  指定date中要与number整数值相加的部分，支持的参数如下表所示：

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
  </table>

- **number**

  一个表达式，可解析为 DATEADD 将其与date 的 datepart相加的 int类型整数。

- **date**

  要处理的目标时间值。

  可解析为下列值之一的表达式: 
  
  - time
  - timetz
  - date 
  - timestamp 
  - timestamptz

## 注意事项

- 该功能仅在数据库兼容模式为SQL Server时能够使用（即创建DB时DBCOMPATIBILITY='MSSQL'），在其他数据库兼容模式下不能使用该特性。
- 函数输入有 date ，time，tiemtz，timestamp，timestampz类型，其中有时区信息时其显示结果会有相应的时区信息。

## 示例

**前置步骤：**创建兼容模式为SQL Server的库db_sqlserver，并进入。

```sql
CREATE DATABASE db_sqlserver DBCOMPATIBILITY='MSSQL'；
\c db_sqlserver
```

**示例1：**直接调用DATEADD函数，将指定时间增加1分钟。

```sql
select dateadd(mi,1,timestamp'1997-12-31 23:59:59');
```

结果返回如下：

```sql
      date_add
---------------------
 1998-01-01 00:00:59
(1 row)
```

**示例2：**DATEADD函数与now函数套用，返回当前日期加上10年的日期。

```sql
select dateadd(year,10,now()) y;
```

结果返回如下：

```sql
               y
-------------------------------
 2033-02-03 08:50:43.872425+00
(1 row)
```

**示例3：**参数为行存表中的列。

1、创建测试表并插入数据。

```sql
create table tb_info(id int,name text,birth date,new int);
insert into tb_info values(1,'lili','1995-11-11',4);
insert into tb_info values(2,'kiko','1997-2-15',3);
insert into tb_info values(3,'lala','2000-1-3',1);
insert into tb_info values(4,'ming','2002-5-7',-2);
select * from tb_info order by id;
```

返回结果如下：

```sql
 id | name |   birth    | new
----+------+------------+-----
  1 | lili | 1995-11-11 |   4
  2 | kiko | 1997-02-15 |   3
  3 | lala | 2000-01-03 |   1
  4 | ming | 2002-05-07 |  -2
(4 rows)
```

2、调用DATEADD函数查询数据。

```sql
select dateadd(yy,new,birth),count(dateadd(yy,new,birth)) from tb_info group by 1 order by 1;
```

结果返回如下：

```sql
      date_add       | count
---------------------+-------
 1999-11-11 00:00:00 |     1
 2000-02-15 00:00:00 |     1
 2000-05-07 00:00:00 |     1
 2001-01-03 00:00:00 |     1
(4 rows)
```

