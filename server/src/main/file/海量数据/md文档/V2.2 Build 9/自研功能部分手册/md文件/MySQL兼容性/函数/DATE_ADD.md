#### DATE_ADD函数

**功能描述**

DATE_ADD() 函数向日期添加指定的时间间隔。

**语法格式**

```
DATE_ADD(date_input,INTERVAL expr unit)
```

**参数说明**

- date_input ：合法的日期表达式，用来指定起始时间，增加支持四种数据类型：time、timetz、timestamp和timestamptz。

- expr ：表达式，用来指定从起始日期添加或减去的时间间隔值。对于负值的时间间隔，它可以以一个'-'开头。

- unit：表示加上或减去的时间类型，比如年，月，日，周等，可参考下表type值。

以下表显示了type 和expr 参数的关系：

| type 值            | 预期的 expr 格式             |
| ------------------ | ---------------------------- |
| MICROSECOND        | MICROSECONDS                 |
| SECOND             | SECONDS                      |
| MINUTE             | MINUTES                      |
| HOUR               | HOURS                        |
| DAY                | DAYS                         |
| WEEK               | WEEKS                        |
| MONTH              | MONTHS                       |
| QUARTER            | QUARTERS                     |
| YEAR               | YEARS                        |
| SECOND_MICROSECOND | 'SECONDS.MICROSECONDS'       |
| MINUTE_MICROSECOND | 'MINUTES.MICROSECONDS'       |
| MINUTE_SECOND      | 'MINUTES:SECONDS'            |
| HOUR_MICROSECOND   | 'HOURS.MICROSECONDS'         |
| HOUR_SECOND        | 'HOURS:MINUTES:SECONDS'      |
| HOUR_MINUTE        | 'HOURS:MINUTES'              |
| DAY_MICROSECOND    | 'DAYS.MICROSECONDS'          |
| DAY_SECOND         | 'DAYS HOURS:MINUTES:SECONDS' |
| DAY_MINUTE         | 'DAYS HOURS:MINUTES'         |
| DAY_HOUR           | 'DAYS HOURS'                 |
| YEAR_MONTH         | 'YEARS-MONTHS'               |

**注意事项**

该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

**兼容性**

完全兼容。

**示例**

1、创建兼容MySQL的库db_mysql，并进入。

```
CREATE DATABASE db_mysql DBCOMPATIBILITY='B';
\c db_mysql
```

2、调用函数。

```
SELECT DATE_ADD('2021-08-13',interval 1 DAY);
```

当结果显示如下信息，则表示验证完成。

```
       date_add        
-----------------------
 2021-08-14 00:00:00+08
(1 row)
```

