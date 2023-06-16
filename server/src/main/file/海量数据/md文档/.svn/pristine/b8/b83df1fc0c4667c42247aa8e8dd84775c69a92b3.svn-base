### MONTHS_BETWEEN

**功能描述**

该函数用于返回两个日期之间的月份数。

**语法格式**

```
MONTHS_BETWEEN(date1, date2)
```

**参数说明**

date1/date2：指定时间值。取值范围是date或TIMESTAMP WITH TIME ZONE

**注意事项**

- 如果date1比date2晚，则返回结果为正数，反之则为负数，即返回结果为date1 减去 date2 的差值。
- 如果date1和date2都为其所在月份的第几天，或者都为某个月份的最后一天，那么返回值为一个整数。否则，返回的是一个浮点数，整数部分为两个时间差中完整月份的差数，小数部分则为剩余时间差与一个月（31天）的比例值。
- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

**示例**

```
select months_between('2020-05-20'::date,'2020-03-20'::date);
```


返回结果为：

```
 months_between 
----------------
            2
(1 row)
```
