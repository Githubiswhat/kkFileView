#### YEARWEEK函数

**功能描述**

yearweek函数获取年份和周数。

**语法格式**

```
YEARWEEK(date,mode)
```

**参数说明**

- date：提取年和周的日期或日期时间。

- mode：它指定一周中的哪一天开始。

下表描述了mode参数的工作方式：

| 模式 | 一周的第一天 | 范围 | 初始周计算规则   |
| ---- | ------------ | ---- | ---------------- |
| 0    | Sunday       | 0-53 | 今年有一个星期天 |
| 1    | Monday       | 0-53 | 今年有四天或以上 |
| 2    | Sunday       | 1-53 | 今年有一个星期天 |
| 3    | Monday       | 1-53 | 今年有四天或以上 |
| 4    | Sunday       | 0-53 | 今年有四天或以上 |
| 5    | Monday       | 0-53 | 今年有一个星期一 |
| 6    | Sunday       | 1-53 | 今年有四天或以上 |

**兼容性**

完全兼容。

**注意事项**

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

- 入参需要支持time、timestamp和date等时间类型。

**示例**

创建兼容MySQL的库db_mysql，并进入。

```
CREATE DATABASE db_mysql DBCOMPATIBILITY='B';
\c db_mysql
```

示例1：在14/07/2022使用Year()函数查找当前年和周。

```
SELECT YEARWEEK(NOW()) AS Current_YearWeek;
```

当前年为2022，星期数为28，结果返回如下：

```
 Current_YearWeek 
------------------
           202230
(1 row)
```

示例2：使用YEARWEEK()函数从给定的日期时间查找年份和星期。

```
SELECT YEARWEEK('2018-04-22 08:09:22') AS Year_Week ;
```

年份是2018，星期数为16，结果返回如下：

```
-----------
 Year_Week 
-----------
  201816 
-----------
```

#### 