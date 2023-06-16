替换oracle兼容性->SQL语法中的PIVOT和UNPIVOT

# PIVOT

## 功能描述

PIVOT子句用于将指定字段的字段值由行转换为列。

## 语法格式

```sql
SELECT …
FROM …
PIVOT[XML]
(pivot_clause  --定义需要聚合的列
pivot_for_clause  --定义要分组和透视的列
pivot_in_clause)   --列过滤器
WHERE …
```

## 参数说明

- **pivot_clause**

  定义需要聚合的列，语法如下：

  ```sql
  aggregate-function(<column>) AS <alias>
  ```

  聚合函数必须指定group by子句，但pivot_clause不包含显式group by子句。相反，pivot_clause执行隐式group by。隐式分组基于pivot_clause和 pivot_for_clause中未引用的所有列。

- **pivot_for_clause**

    定义要分组和透视的列，语法如下：

    ```sql
    FOR <pivot-column>
   ```

- **pivot_in_clause**

    定义pivot_for_clause中列的过滤器（即限制结果的值范围）。pivot_in_clause中每个值的聚合将被转置到单独的列中。

    `pivot for in`查询子句支持关键字做为别名，支持的关键字为month、content、percent、password、begin、select、drop和on。语法如下：
  
    ```sql
    IN(<value1> [AS <alias1>], ..., <valuen> [AS <aliasn>])
     ```
  
   > <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
   >
   > pivot_in_clause指定别名有两种方法：`<value1> AS <alias1>`和`<value1> <alias1>`。
   >
   > 其中关键字别名只支持"AS"，普通字符串别名"AS"和""都支持。

## 注意事项

无。

## 示例

1、创建测试表并插入数据。

```sql
create table test_pivot (NAME varchar2(20), test varchar2(20), score number);
insert into test_pivot values('张三','数学',90);
insert into test_pivot values('张三','英语',85);
insert into test_pivot values('李四','数学',90);
insert into test_pivot values('李四','语文',85);
insert into test_pivot values('王五','数学',100);
insert into test_pivot values('王五','英语',95);
insert into test_pivot values('张三','语文',80);
insert into test_pivot values('李四','英语',95);
insert into test_pivot values('王五','语文',90);
```

2、查询表中数据。

```sq;
select * from  test_pivot;
```

返回结果为：

```sql
 name | test | score
------+------+-------
 张三 | 数学 |    90
 张三 | 英语 |    85
 李四 | 数学 |    90
 李四 | 语文 |    85
 王五 | 数学 |   100
 王五 | 英语 |    95
 张三 | 语文 |    80
 李四 | 英语 |    95
 王五 | 语文 |    90
(9 rows)
```

3、执行如下SQL语句将学生姓名由行转列（此时虽然用到了聚合函数sum，由于test字段也一并显示，因此sum函数并没有起到效果，显示依旧是每一个学生的各科具体成绩）。

```sql
select * from test_pivot
pivot
(
sum(score)
for name in('张三','李四','王五')
);
```

返回结果为：

```sql
 test | '张三' | '李四' | '王五'
------+--------+--------+--------
 数学 |     90 |     90 |    100
 英语 |     85 |     95 |     95
 语文 |     80 |     85 |     90
(3 rows)
```

4、统计每一个学生的所有科目总成绩，并将学生姓名字段行转列（test字段没有显示）。

```sql
select * from (select name,score from test_pivot)
pivot
(
sum(score)
for name in ('张三','李四','王五')
);
```

返回结果为：

```sql
 '张三' | '李四' | '王五'
--------+--------+--------
    255 |    270 |    285
(1 row)
```

