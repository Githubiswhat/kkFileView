### GROUPING_ID和GROUPING

**功能描述**

GROUPING函数用于区分分组后的普通行和聚合行。如果是聚合行，则返回1，反之，则是0。

GROUPING_ID函数相当于将多个GROUPING函数的结果串接成二进制数，返回这个二进制数对应的十进制数。

**语法格式**

```
GROUPING_ID(expr [, expr ]...)
```

**参数说明**

expr：列名。需要在GROUP BY表达式中，该函数返回由1和0组成的位向量对应的数字，指示哪些GROUP BY表达式不包括在当前分组集中。如果对应的expr包含在生成当前结果行的分组集中（常规分组行），则位为0，如果不包含(超聚合行，GROUPBYROLLUP/CUBE等生成）则为1。当有多个expr时，如2个expr，可能的返回值为：00 ->0，01 ->1，10-> 2，11 -> 3。

**注意事项**

- 该函数仅适用于select语句并包含GROUP BY的情况。

- grouping_id的字段，必须是group by包含的字段

**示例**

1、创建测试表并插入数据。

```
create table group_test (group_id int, job varchar2(10), name varchar2(10), salary int);
insert into group_test values (10,'Coding', 'Bruce',1000);
insert into group_test values (10,'Programmer','Clair',1000);
insert into group_test values (10,'Architect', 'Gideon',1000);
insert into group_test values (10,'Director', 'Hill',1000);
insert into group_test values (20,'Coding', 'Jason',2000);
insert into group_test values (20,'Programmer','Joey',2000);
insert into group_test values (20,'Architect', 'Martin',2000);
insert into group_test values (20,'Director', 'Michael',2000);
insert into group_test values (30,'Coding', 'Rebecca',3000);
insert into group_test values (30,'Programmer','Rex',3000);
insert into group_test values (30,'Architect', 'Richard',3000);
insert into group_test values (30,'Director', 'Sabrina',3000);
insert into group_test values (40,'Coding', 'Samuel',4000);
insert into group_test values (40,'Programmer','Susy',4000);
insert into group_test values (40,'Architect', 'Tina',4000);
insert into group_test values (40,'Director', 'Wendy',4000);
```

2、grouping函数：传入两个expr参数，使用GROUP BY ROLLUP生成分组集.

```
select group_id,job,grouping(group_id),grouping(job),grouping(group_id,job),sum(salary) from group_test group by rollup(group_id, job);
```

返回结果为：

```
 group_id |    job     | grouping | grouping | grouping |  sum
----------+------------+----------+----------+----------+-------
       10 | Architect  |        0 |        0 |        0 |  1000
       10 | Coding     |        0 |        0 |        0 |  1000
       10 | Director   |        0 |        0 |        0 |  1000
       10 | Programmer |        0 |        0 |        0 |  1000
       10 |            |        0 |        1 |        1 |  4000
       20 | Architect  |        0 |        0 |        0 |  2000
       20 | Coding     |        0 |        0 |        0 |  2000
       20 | Director   |        0 |        0 |        0 |  2000
       20 | Programmer |        0 |        0 |        0 |  2000
       20 |            |        0 |        1 |        1 |  8000
       30 | Architect  |        0 |        0 |        0 |  3000
       30 | Coding     |        0 |        0 |        0 |  3000
       30 | Director   |        0 |        0 |        0 |  3000
       30 | Programmer |        0 |        0 |        0 |  3000
       30 |            |        0 |        1 |        1 | 12000
       40 | Architect  |        0 |        0 |        0 |  4000
       40 | Coding     |        0 |        0 |        0 |  4000
       40 | Director   |        0 |        0 |        0 |  4000
       40 | Programmer |        0 |        0 |        0 |  4000
       40 |            |        0 |        1 |        1 | 16000
          |            |        1 |        1 |        3 | 40000
(21 rows)
```

3、调用grouping_id函数：grouping_id函数：传入一个expr参数，使用GROUP BY CUBE生成分组集。、

```
select group_id,job,grouping_id(group_id),grouping_id(job),sum(salary) from group_test group by cube(group_id, job);
```

当返回结果如下时，则表示支持grouping_id函数

```
 group_id |    job     | grouping | grouping |  sum
----------+------------+----------+----------+-------
       10 | Architect  |        0 |        0 |  1000
       10 | Coding     |        0 |        0 |  1000
       10 | Director   |        0 |        0 |  1000
       10 | Programmer |        0 |        0 |  1000
       10 |            |        0 |        1 |  4000
       20 | Architect  |        0 |        0 |  2000
       20 | Coding     |        0 |        0 |  2000
       20 | Director   |        0 |        0 |  2000
       20 | Programmer |        0 |        0 |  2000
       20 |            |        0 |        1 |  8000
       30 | Architect  |        0 |        0 |  3000
       30 | Coding     |        0 |        0 |  3000
       30 | Director   |        0 |        0 |  3000
       30 | Programmer |        0 |        0 |  3000
       30 |            |        0 |        1 | 12000
       40 | Architect  |        0 |        0 |  4000
       40 | Coding     |        0 |        0 |  4000
       40 | Director   |        0 |        0 |  4000
       40 | Programmer |        0 |        0 |  4000
       40 |            |        0 |        1 | 16000
          |            |        1 |        1 | 40000
          | Architect  |        1 |        0 | 10000
          | Coding     |        1 |        0 | 10000
          | Director   |        1 |        0 | 10000
          | Programmer |        1 |        0 | 10000
(25 rows)
```
