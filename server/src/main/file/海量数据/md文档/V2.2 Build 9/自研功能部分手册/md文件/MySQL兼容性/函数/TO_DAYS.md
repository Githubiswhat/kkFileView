#### TO_DAYS函数

**功能描述**

to_days()函数用于给定一个日期，返回一个天数（自0001-01-01以来的天数）。

**语法格式**

```
TO_DAYS(date)
```

**注意事项**

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

- to_days函数的入参需要支持time，timestamp，date等时间类型。

- to_days函数的返回值类型是整型。

- 如果输入参数是timestamp或其他包含日期和时间的类型，返回的是整数，并且对结果进行向下取整。

- Vastbase定义0001-01-01为366（MySQL定义0000-01-01为1），所以两者计算结果一致。

**示例**

1、创建兼容MySQL的库db_mysql，并进入。

```
CREATE DATABASE db_mysql DBCOMPATIBILITY='B';
\c db_mysql
```

2、执行函数验证。

```
select to_days('2010-01-01 12:00:00');
```

结果返回如下：

```
 to_days 
---------
  734138
(1 row)
```

#### 