### RATIO_TO_REPORT

**功能描述**

RATIO_TO_REPORT函数是一个分析函数，主要功能根据over窗口函数的作用区间，求出作用区间中的单个值（expr）在整个区间的总值的比重。
注：如果over（）中为空，就是对整张表的一个分组处理，如果over（coln）中写了分组字段名，就按coln字段分组进行运算。

**语法格式**

```
RATIO_TO_REPORT(expr) OVER([query_partition_clause])
```

**参数说明**

- expr：表达式。
- OVER：对应关键字。
  query_partition_clause：使用query_partition_clause子句定义窗口进行计算。语法格式为partition by col，其中，col为用于分组的列名。如果省略该子句，会计算房前值占所有值的比率。

**注意事项**

无。

**示例**

1、创建测试表并插入数据。

```
create table day_empsalary (
depname varchar,
empno bigint,
salary int,
working_days int
);

insert into day_empsalary values
('develop', 10, 5200, 12),
('sales', 1, 5000, 10),
('personnel', 5, 3500, 11),
('sales', 4, 4800, 5),
('personnel', 2, 3900, 10),
('develop', 7, 4200, 2),
('develop', 9, 4500, 3),
('sales', 3, 4800, 4),
('develop', 8, 6000, 3),
('develop', 11, 5200, 2);
```

2、调用分析函数（省略query_partition_clause子句）

```
select depname, empno, salary, ratio_to_report(salary) over () from day_empsalary order by 1,2;
```

返回结果为:

```
depname  | empno | salary |  ratio_to_report  
-----------+-------+--------+-------------------
develop   |     7 |   4200 |  .089171974522293
develop   |     8 |   6000 |  .127388535031847
develop   |     9 |   4500 | .0955414012738854
develop   |    10 |   5200 |  .110403397027601
develop   |    11 |   5200 |  .110403397027601
personnel |     2 |   3900 | .0828025477707006
personnel |     5 |   3500 | .0743099787685775
sales     |     1 |   5000 |  .106157112526539
sales     |     3 |   4800 |  .101910828025478
sales     |     4 |   4800 |  .101910828025478
(10 rows)
```

3、调用分析函数（不省略query_partition_clause子句）

```
select depname, empno, salary, ratio_to_report(salary) over (partition by depname) from day_empsalary order by 1,2;
```

返回结果为：

```
depname  | empno | salary | ratio_to_report  
-----------+-------+--------+------------------
develop   |     7 |   4200 | .167330677290837
develop   |     8 |   6000 | .239043824701195
develop   |     9 |   4500 | .179282868525896
develop   |    10 |   5200 | .207171314741036
develop   |    11 |   5200 | .207171314741036
personnel |     2 |   3900 | .527027027027027
personnel |     5 |   3500 | .472972972972973
sales     |     1 |   5000 | .342465753424658
sales     |     3 |   4800 | .328767123287671
sales     |     4 |   4800 | .328767123287671
(10 rows)
```
