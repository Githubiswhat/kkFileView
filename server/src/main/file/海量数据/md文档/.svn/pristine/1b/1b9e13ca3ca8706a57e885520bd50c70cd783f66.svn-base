# SUBDATE函数

## 功能描述

SUBDATE函数用于将给定日期减去时间值（作为间隔）。

## 语法格式

```sql
SUBDATE(date,INTERVAL expr unit)
SUBDATE(date,days)
```

## 参数说明

- **date**

  合法的日期表达式。

- **expr**

  要减去的时间间隔值。

- **unit**

  减去的时间间隔单位，取值范围如下表所示：

  <table>
      <th>unit值</th>
      <th>含义</th>
      <tr>
      <td>MICROSECOND</td>
      <td>毫秒</td>
      </tr>
      <tr>
     <td>SECOND</td>
      <td>秒</td></tr>
      <tr>
      <td>MINUTE</td>
      <td>分</td></tr>
      <tr>
      <td>HOUR</td>
      <td>时</td>
      </tr>
      <tr>
      <td>DAY</td>
      <td>天</td></tr>
      <tr>
      <td>WEEK</td>
      <td>周</td></tr>
      <tr>
      <td>MONTH</td>
      <td>月</td></tr>
      <tr>
      <td>QUARTER</td>
      <td>季度</td></tr>
      <tr>
      <td>YEAR</td>
      <td>年</td></tr>
      <tr>
      <td>SECOND_MICROSECOND</td>
      <td>秒_毫秒</td></tr>
      <tr>
      <td>MINUTE_MICROSECOND</td>
      <td>分_毫秒</td></tr>
      <tr>
      <td>MINUTE_SECOND</td>
      <td>分_秒</td></tr>
      <tr>
      <td>HOUR_MICROSECOND</td>
      <td>时_毫秒</td></tr>
      <tr>
      <td>HOUR_MINUTE</td>
      <td>时_分</td></tr>
      <tr>
      <td>DAY_MICROSECOND</td>
      <td>天_毫秒</td></tr>
      <tr>
      <td>DAY_SECOND</td>
      <td>天_秒</td></tr>
      <tr>
      <td>DAY_MINUTE</td>
      <td>天_分</td></tr>
      <tr>
      <td>DAY_HOUR</td>
      <td>天_时</td></tr>
      <tr>
      <td>YEAR_MONTH</td>
      <td>年_月</td></tr>
  </table>

- **days**

  要减去的天数。
  
  取值范围：正数、负数、0、小数。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 当days或expr负数时，返回值增加对应值。
- 当days为小数时，按照四舍五入进行天数的增减。

## 示例

**前置步骤：**创建并进入兼容MySQL的库db_mysql。

```sql
CREATE DATABASE db_mysql DBCOMPATIBILITY 'B';
\c db_mysql 
```

**示例1：**interval为只包含分钟。

```sql
select subdate('2022-5-31 11:12:13.1234567',INTERVAL '11' DAY_MINUTE);
```

结果返回如下：

```sql
            subdate
-------------------------------
 2022-05-31 11:01:13.123457+08
(1 row)
```

**示例2：**interval包含小时、分钟。

```sql
select subdate('2022-5-31 11:12:13.1234567',INTERVAL '11 3' DAY_MINUTE);
```

结果返回如下：

```sql
            subdate
-------------------------------
 2022-05-31 00:09:13.123457+08
(1 row)
```

**示例3：**interval包含日、小时、分钟。

```sql
select subdate('2022-5-31 11:12:13.1234567',INTERVAL '11 3 3' DAY_MINUTE); 
```

结果返回如下：

```sql
            subdate
-------------------------------
 2022-05-20 08:09:13.123457+08
(1 row)
```

**示例4：**expr为负数。

```sql
select subdate('2022-5-31 11:12:13.1234567',INTERVAL '-11 -3 -3' DAY_MINUTE); 
```

结果返回如下：

```sql
            subdate
-------------------------------
 2022-06-11 14:15:13.123457+08
(1 row)
```

**示例5：**使用SUBDATE(date,days)语法，days为小数。

```sql
select subdate(date '2022-5-31', -2.8);
```

结果返回如下：

```sql
  subdate
------------
 2022-06-03
(1 row)
```

