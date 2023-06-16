# MySQL兼容性-支持date_sub函数

## 功能描述

在MySQL兼容模式下，Vastbase G100支持使用date_sub函数得到某日期减去指定的时间间隔后的日期。

## 语法格式

```sql
date_sub(date,INTERVAL expr type)
```

## 参数说明

- **date：**日期信息，必须是合法的日期表达式。
- **expr：**要减去的时间间隔。
- **type：**时间类型/日期格式，其可选范围如下表：

| 参数取值           | 含义    |
| :----------------- | ------- |
| MICROSECOND        | 毫秒    |
| SECOND             | 秒      |
| MINUTE             | 分      |
| HOUR               | 时      |
| DAY                | 天      |
| WEEK               | 周      |
| MONTH              | 月      |
| QUARTER            | 季度    |
| YEAR               | 年      |
| SECOND_MICROSECOND | 秒_毫秒 |
| MINUTE_MICROSECOND | 分_毫秒 |
| MINUTE_SECOND      | 分_秒   |
| HOUR_MICROSECOND   | 时_毫秒 |
| HOUR_SECOND        | 时_秒   |
| HOUR_MINUTE        | 时_分   |
| DAY_MICROSECOND    | 天_毫秒 |
| DAY_SECOND         | 天_秒   |
| DAY_MINUTE         | 天_分   |
| DAY_HOUR           | 天_时   |
| YEAR_MONTH         | 年_月   |

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 仅Vastbase G100 2.2 Build 11及以后版本支持此功能。
- date的取值必须是合法的日期表达式，其合法性的校验是按照当前Vastbase G100中的合法性校验进行处理。


## 示例

1、创建并切换至兼容模式为B的数据库下。

```sql
create database test_1100752 dbcompatibility 'B';
\c test_1100752;
```

2、date与type的各种不同取值情况下，查询使用date_sub函数得到的结果。

- date参数类型为date，type类型为WEEK。

```sql
select DATE_SUB('2022-5-31',INTERVAL 2 WEEK) result;
```

返回结果为：

```sql
         result
------------------------
 2022-05-17 00:00:00+08
(1 row)
```

- date参数为now()，type类型为day。

```sql
select DATE_SUB(now(),INTERVAL 123 day) result;
```

返回结果为：

```sql
            result
-------------------------------
 2022-07-17 11:46:24.714729+08
(1 row)
```

- date参数为强转timestamp类型，type类型为day。

```sql
select DATE_SUB('2012-02-13'::timestamp,INTERVAL 123 day) result;
```

返回结果为：

```sql
       result
---------------------
 2011-10-13 00:00:00
(1 row)
```

- date参数为timestamptz类型，type类型为YEAR_MONTH。

```sql
select DATE_SUB(timestamptz '2022-5-31',INTERVAL '5 31' YEAR_MONTH)result;
```

返回结果为：

```sql
         result
------------------------
 2014-10-31 00:00:00+08
(1 row)
```

- date参数为datetime字符串，type类型为SECOND。

```sql
select DATE_SUB('20121213121232',INTERVAL 123 SECOND) result;
```

返回结果为：

```sql
         result
------------------------
 2012-12-13 12:10:29+08
(1 row)
```

