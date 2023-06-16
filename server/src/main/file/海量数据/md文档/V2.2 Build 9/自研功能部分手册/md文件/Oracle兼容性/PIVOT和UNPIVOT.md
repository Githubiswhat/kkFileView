### PIVOT和UNPIVOT

**功能描述**

pivot为select语句的子语句，该语句是将行旋转为列的交叉表查询，在旋转过程中聚合函数、数据透视（pivot data）是整个操作的关键。数据透视时，会对数据透视列值列表中的每个项应用聚合运算符。数据透视列不能包含任意表达式。

在pivot（行转列）语法中pivot for in查询子句支持关键字做为别名，支持的关键字为month、content、percent、password、begin、select、drop和on。

unpivot为select语句的子语句，该语句允许将列旋转为行的交叉表查询。

**语法格式**

- pivot

```
语法：
SELECT …
FROM <table-expr>
PIVOT
(
aggregate-function(<column>) AS <alias>
FOR <pivot-column> IN(<value1> [AS <alias1>], ..., <valuen> [AS <aliasn>])
) AS <alias>
WHERE …
子句划分：
SELECT …
FROM …
PIVOT[XML]
(pivot_clause
pivot_for_clause
pivot_in_clause) 
WHERE …
```

- unpivot

```
语法：
SELECT …
FROM <table-expr>
UNPIVOT [INCLUDE|EXCULDE NULLS]
(
unpivot_column1
FOR <unpivot-column2> IN ({(column_name,...)|column_name,}...)
) AS <alias_name>
WHERE …
子句划分：
SELECT …
FROM …
UNPIVOT[INCLUDE|EXCLUDE NULLS]
(unpivot_clause
unpivot_for_clause
unpivot_in_clause) 
WHERE …
```

  **参数说明**

- pivot_clause：定义需要聚合的列（pivot是一个聚合操作），即上述语法(部分聚合函数)：

```
aggregate-function(<column>) AS <alias>
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>
>
> 聚合函数必须指定group by子句，但pivot_clause不包含显式group by子句。相反，pivot_clause执行隐式group by。隐式分组基于pivot_clause和 pivot_for_clause中未引用的所有列。

- pivot_for_clause：定义要分组和透视的列。即上述语法：

```
FOR <pivot-column>
```

- pivot_in_clause：定义pivot_for_clause中列的过滤器（即限制结果的值范围）。pivot_in_clause中每个值的聚合将被转置到单独的列中，并且pivot for in查询子句支持关键字做为别名：即上述语法：

```
 IN(<value1> [AS <alias1>], ..., <valuen> [AS <aliasn>])
```

pivot_in_clause子句中与xml结合使用subquery以及any。


- INCLUDE|EXCLUDE NULLS：用来控制unpivot是否包含null的记录，默认是不包含null的。
- unpivot_clause：行值的列名（即unpivot_in_clause中各列的值），即上述语法：

```
unpivot_column1
```

-  unpivot_for_clause：将原有多个列合并到单个列的列名（即把unpivot_in_clause中列名拼接），即上述语法：

```
FOR unpivot_column2
```

-  unpivot_in_clause：原始表的列名，即上述语法:

```
IN ({(column_name,...)|column_name,}...)
```

**注意事项**

pivot_in_clause：别名有两种方法

```
<value1> AS <alias1>
和
<value1> <alias1>
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>
>
> 其中关键字别名只支持“<value1> AS <alias1>不支持“<value1> <alias1>
>
> 普通字符串别名“<value1> AS <alias1>”和“<value1> <alias1>”都支持。

**示例**

1、创建测试表并插入数据。

```
create table test_pivot (timetest varchar2(20), test varchar2(20), score number, class number);
insert into test_pivot values('张三','month', 78, 1);
insert into test_pivot values('张三','content',87, 2);
insert into test_pivot values('张三','content',90, 1);
insert into test_pivot values('张三','content',60, 2);
insert into test_pivot values('张三','percent',82, 3);
insert into test_pivot values('张三','percent',88, 1);
insert into test_pivot values('张三','percent',89, 2);
insert into test_pivot values('张三','password',90, 2);
insert into test_pivot values('张三','天文',90, 1);
insert into test_pivot values('李四','地理',90, 2);
insert into test_pivot values('李四','month',65, 2);
insert into test_pivot values('李四','month',95, 1);
insert into test_pivot values('李四','month',65, 3);
insert into test_pivot values('李四','content',77, 1);
insert into test_pivot values('李四','percent',65, 2);
insert into test_pivot values('李四','percent',85, 2);
insert into test_pivot values('李四','percent',95, 2);
insert into test_pivot values('李四','password',85, 3);
insert into test_pivot values('张三','Physics',100, 2);
insert into test_pivot values('张三','Math',70, 1);
insert into test_pivot values('李四','Physics',60, 2);
insert into test_pivot values('李四','Chinese',65, 2);
```

2、将行转为列。

```
select *  from test_pivot pivot (avg(score) for test in ('month' as month, 'content' as content, 'percent' as percent,'password' as password, '天文', '地理')) order by class;
```

返回结果为：

```
 timetest | class | month | content |       percent       | password | '天文' | '地理'
----------+-------+-------+---------+---------------------+----------+--------+--------
 李四     |     1 |    95 |      77 |                     |          |        |
 张三     |     1 |    78 |      90 |                  88 |          |     90 |
 张三     |     2 |       |    73.5 |                  89 |       90 |        |
 李四     |     2 |    65 |         | 81.6666666666666667 |          |        |     90
 张三     |     3 |       |         |                  82 |          |        |
 李四     |     3 |    65 |         |                     |       85 |        |
(6 rows)
```

3、创建一个测试表 testunpivot。

```
create table testunpivot(NAME varchar(10),yu int,shu int,ying int,wu int,zong int);
insert into testunpivot values('李四',65,75,85,95,320),('李五',61,71,81,91,324);
```

4、查询表中的数据。

```
 select * from testunpivot;
```

返回结果为：

```
 name | yu | shu | ying | wu | zong
------+----+-----+------+----+------
 李四 | 65 |  75 |   85 | 95 |  320
 李五 | 61 |  71 |   81 | 91 |  324
(2 rows)
```

5、将列转为行。

```
select * from testunpivot unpivot((wen,li) for kemu in((yu,shu),(yu,wu),(ying,shu),(ying,wu)));
```

返回结果为：

```
 name | zong |   kemu   | wen | li
------+------+----------+-----+----
 李四 |  320 | yu_shu   |  65 | 75
 李四 |  320 | yu_wu    |  65 | 95
 李四 |  320 | ying_shu |  85 | 75
 李四 |  320 | ying_wu  |  85 | 95
 李五 |  324 | yu_shu   |  61 | 71
 李五 |  324 | yu_wu    |  61 | 91
 李五 |  324 | ying_shu |  81 | 71
 李五 |  324 | ying_wu  |  81 | 91
(8 rows)
```
