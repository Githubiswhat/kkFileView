# DATEDIFF

## 功能描述

Vastbase G100在SQL Server兼容模式下支持DATEDIFF函数，用于计算两个日期之间的时间间隔。函数返回指定startdate和enddate之间所跨的指定datepart边界的计数（作为带符号整数值），返回值类型为integer类型。

## 语法格式

```sql
datediff(depart,startdate,enddate)
```

## 参数说明

- **depart**

  enddate与startdate之间的间隔差值单位。

  取值范围：depart值不能在变量中指定，也不能指定为如`'month'`这样带引号的字符串。DATEDIFF接受所有有效的depart全名或全名缩写，其中在[表1](#table1)中列出了所有有效的depart值。

  **表1** 有效的depart值

  <table>
      <tr>
     		<th>日期部分</th>
      	<th>缩写</th>
          <th>备注</th>
      </tr>
      <tr>
          <td>year</td>
          <td>yy,yyyy</td>
          <td>年</td>
      </tr>
          <tr>
          <td>quater</td>
          <td>qq,q</td>
          <td>季度</td>
      </tr>
          <tr>
          <td>month</td>
          <td>mm,m</td>
              <td>月</td>
      </tr>
          <tr>
          <td>dayofyear</td>
          <td>dy,y</td>
              <td>一年中的第几天</td>
      </tr>
          <tr>
          <td>day</td>
          <td>dd,d</td>
              <td>日</td>
      </tr>
          <tr>
          <td>week</td>
          <td>wk,ww</td>
              <td>一年中的第几周</td>
      </tr>
          <tr>
          <td>weekday</td>
          <td>dw日期部分返回对应于星期中的某天的数,w<br>例如：Sunday=1</td>
              <td>星期几</td>
      </tr>
          <tr>
          <td>hour</td>
          <td>hh</td>
              <td>小时</td>
      </tr>
          <tr>
          <td>minute</td>
          <td>mi,n</td>
              <td>分种</td>
      </tr>
          <tr>
          <td>second</td>
          <td>ss,s</td>
              <td>秒</td>
      </tr>
          <tr>
          <td>millisecond</td>
          <td>ms</td>
              <td>毫秒</td>
      </tr>
          <tr>
          <td>microsecond</td>
          <td>mcs</td>
              <td>微秒</td>
      </tr>
          <tr>
          <td>nanosecond</td>
          <td>ns</td>
              <td>纳秒</td>
      </tr>
  </table>

- **startdate**

  起始日期。

  可以解析为以下之一的表达式：time、timetz、date、timestamp、timestamptz。

- **enddate**

  终止日期。 

  可以解析为以下之一的表达式：time、timetz、date、timestamp、timestamptz。

## 注意事项

- 本特性仅在数据库兼容模式为SQL Server时能够使用（即创建DB时DBCOMPATIBILITY='MSSQL'），在其他数据库兼容模式下不能使用该特性。
- 若返回值超出范围（-2,147,483,648到+2,147,483,647），DATEDIFF函数返回错误。
  - 对于指定depart值为millisecond的DATEDIFF函数，startdate和enddate之间的最大差值为24天20小时31分钟23.647秒。
  - 对于指定depart值为second的DATEDIFF函数，startdate和enddate的最大差值为68年19天3小时14分7秒。
- Vastbase的时间精度只到微秒级别，纳秒部分的值将四舍五入进入微秒部分。

## 示例

**前置条件：**创建兼容SQL Server的库db_sqlserver，并进入。

```sql
create database db_sqlserver dbcompatibility  'MSSQL';
\c db_sqlserver
```

**示例1：**depart输入为year时直接使用datediff函数。

1、查询当enddate比startdate小的情况。

```sql
select datediff(year,'2023-12-31 23:59:59','2023-01-01 00:00:00'); 
```

查询结果为0年：

```sql
 datediff_mssql
----------------
              0
(1 row)
```

2、查询当enddate比start大的情况。

```sql
select datediff(year,'2023-12-31 23:59:59','2024-01-01 00:00:00');
```

查询结果为enddate与startdate差值，即1年：

```sql
 datediff_mssql
----------------
              1
(1 row)
```

**示例2：**在测试表中使用以ss为depart输入的datediff函数。

1、创建测试表，并插入数据。

```sql
CREATE TABLE t_datediff_second(
c1 int,
c2 time,
c3 timetz,
c4 date,
c5 timestamp,
c6 timestamptz
)partition by list(c4)
(partition p1 values('0001-01-01'),
partition p2 values('2023-02-09'),
partition p3 values(default));

INSERT INTO t_datediff_second VALUES(1,'2001-01-01 00:00:00+00'::time,'2001-01-01 00:00:00+00'::timetz,'2001-01-01 00:00:00+00','2001-01-01 00:00:00+00','2001-01-01 00:00:00+00'); 
INSERT INTO t_datediff_second VALUES(2,'2030-12-31 23:59:59+08','2030-12-31 23:59:59+08','2030-12-31 23:59:59+08','2030-12-31 23:59:59+08','2030-12-31 23:59:59+08'); 
INSERT INTO t_datediff_second VALUES(3,'2023-02-09 10:04:00+08','2023-02-09 10:04:00+08','2023-02-09 10:04:00+08','2023-02-09 10:04:00+08','2023-02-09 10:04:00+08'); 
```

2、在SELECT的语句中使用DATEDIFF函数（函数以ss作为depart输入）。

```sql
select
datediff(ss,A.c2,B.c2),datediff(ss,A.c3,B.c3),datediff(ss,A.c4,B.c4),datediff(ss,A.c5,B.c5),datediff(ss,A.c6,B.c6) 
FROM t_datediff_second A LEFT JOIN t_datediff_second B 
ON A.c1=B.c1+1
WHERE B.c1 is not null;
```

查询结果如下：

```sql
datediff_mssql | datediff_mssql | datediff_mssql | datediff_mssql | datediff_mssql
---------------+----------------+----------------+----------------+------------
         50159 |          50159 |      249004800 |      249054959 |      249054959
             1 |         -57599 |     -946598400 |     -946684799 |     -946655999
(2 rows)   
```
