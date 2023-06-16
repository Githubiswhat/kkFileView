# DATEPART

## 功能描述

Vastbase G100在SQL Server模式下支持DATEPART函数，用于返回表示指定 date 的指定 datepart 的整数，返回值为整数类型。

## 语法格式

```sql
DATEPART(datepart,date)
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
CREATE DATABASE db_sqlserver DBCOMPATIBILITY='MSSQL'；
\c db_sqlserver
```

**示例1：**直接调用DATEPART函数。

```sql
SELECT DATEPART(m,timestamp'2007-10-30 12:15:32.1234567');
```

结果返回如下：

```sql
 datepart
----------
       10
(1 row)
```

**示例2：**date参数为行存表中的列。

1、创建测试表并插入数据。

```sql
create table tb_info(id int,name text,birth date);
insert into tb_info values(1,'lili','1999-11-11');
insert into tb_info values(2,'kiko','2000-2-15');
insert into tb_info values(3,'lala','2001-1-3');
insert into tb_info values(4,'ming','1999-5-7');
create table tb_class(class int,year int);
insert into tb_class values(4,2001);
insert into tb_class values(5,2000);
insert into tb_class values(6,1999);
insert into tb_class values(7,1998);
```

2、查询表中数据。

```sql
select * from tb_info order by id;
```

结果返回如下：

```sql
 id | name |   birth
----+------+------------
  1 | lili | 1999-11-11
  2 | kiko | 2000-02-15
  3 | lala | 2001-01-03
  4 | ming | 1999-05-07
(4 rows)
```

```sql
select * from tb_class order by class;
```

结果返回如下：

```sql
 class | year
-------+------
     4 | 2001
     5 | 2000
     6 | 1999
     7 | 1998
(4 rows)
```

3、调用DATEPART函数查询数据。

```sql
select datepart(yy,birth),sum(datepart(yy,birth))/datepart(yy,birth) count from tb_info having datepart(yy,birth) group by 1 order by 1;
```

结果返回如下：

```sql
 datepart | count
----------+-------
     1999 |     2
     2000 |     1
     2001 |     1
(3 rows)
```

