# EXTRACT

## 功能描述

使用EXTRACT函数从时间类型数据或时间格式的字符串中提取指定部分。

## 语法格式

```sql
EXTRACT(unit FROM date)
```

## 参数说明

- **date**

  时间类型数据或时间格式的字符串，支持text类型作为date位置的参数。当date为NULL时，函数返回NULL。

- **unit**

  指定提取部分，unit值与date格式对应关系如下表所示：
  
  <table>
      <th>unit值</th>
      <th>date格式</th>
      <tr>
      <td>MICROSECOND</td>
          <td>MICROSECONDS</td></tr>
      <tr>
      <td>SECOND</td>
      <td>SECONDS</td></tr>
      <tr>
      <td>MINUTE</td>
      <td>MINUTES</td></tr>
      <tr>
      <td>HOUR</td>
      <td>HOURS</td></tr>
      <tr>
      <td>DAY</td>
      <td>DAYS</td></tr>
      <tr>
      <td>WEEK</td>
      <td>WEEKS</td></tr>
      <tr>
      <td>MONTH</td>
      <td>MONTHS</td></tr>
      <tr>
      <td>QUARTER</td>
      <td>QUARTERS</td></tr>
      <tr>
      <td>YEAR</td>
      <td>YEARS</td></tr>
      <tr>
      <td>SECOND_MICROSECOND</td>
      <td>'SECONDS.MICROSECONDS'</td></tr>
      <tr>
      <td>MINUTE_MICROSECOND</td>
      <td>'MINUTES:SECONDS.MICROSECONDS'</td></tr>
      <tr>
      <td>MINUTE_SECOND</td>
      <td>'MINUTES:SECONDS'</td></tr>
      <tr>
      <td>HOUR_MICROSECOND</td>
      <td>'HOURS:MINUTES:SECONDS.MICROSECONDS'</td></tr>
      <tr>
      <td>HOUR_SECOND</td>
      <td>'HOURS:MINUTES:SECONDS'</td></tr>
      <tr>
      <td>HOUR_MINUTE</td>
      <td>'HOURS:MINUTES'</td></tr>
      <tr>
      <td>DAY_MICROSECOND</td>
          <td>'DAYS HOURS:MINUTES:SECONDS:MICROSECONDS'</td></tr>
      <tr>
      <td>DAY_SECOND</td>
          <td>'DAYS HOURS:MINUTES:SECONDS'</td>
      </tr>
      <tr>
      <td>DAY_MINUTE</td>
      <td>'DAYS HOURS:MINUTES'</td></tr>
      <tr>
      <td>DAY_HOUR</td>
      <td>'DAYS_HOURS'</td></tr>
      <tr>
      <td>YEAR_MONTH</td>
      <td>'YEARS-MONTHS'</td></tr>
  </table>

## 注意事项

无。

## 示例

**示例1：**在日期中提取出年份和月份。

```sql
select extract(year_month from date '2022-11-04');
```

结果返回如下：

```sql
 date_part
-----------
    202211
(1 row)
```

**示例2：**从时间格式text字符串中提取日和小时。

```sql
select extract( day_hour from '2022-11-04 15:30:00');
```

结果返回如下：

```sql
 date_part
-----------
       415
(1 row)
```

