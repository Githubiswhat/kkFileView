### GROUP BY支持含有表达式或者函数的列

**功能描述**

在Vastbase G100现有group by功能的基础上增强对Oracle group by子句的兼容。针对含有子查询的分组列，不仅支持普通的列，而且支持含有表达式或者函数的列。

**语法格式**

```
select [grouping_element],
[expression] 
[WHERE condition]
[from table]
group by [grouping_element];
```

**参数说明**

- grouping_element：函数、表达式。

- expression：SQL语句中的子查询。

**注意事项**

该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

**示例**

1、创建并切换至兼容模式为ORACLE的数据库db_oracle。

```
CREATE DATABASE db_oracle dbcompatibility=’A’;     
\c db_oracle
```

2、创建测试表并插入数据。

```
create table test_a(c1 number,c2 varchar2(20));
insert into test_a values(1,'a');
insert into test_a values(2,'a_xxr');
insert into test_a values(3,'a_ytr');
insert into test_a values(4,'b');
insert into test_a values(5,'b_xxr');
insert into test_a values(6,'b_ytr');
create table test_b(c1 number,c2 varchar2(20));
insert into test_b values(11,'a');
insert into test_b values(22,'b');
insert into test_b values(33,'aa');
insert into test_b values(44,'ba');
insert into test_b values(55,'e');
insert into test_b values(66,'f');
```

3、使用group by字句包含函数。

```
SELECT substr(a.c2, 1, 1)
        , (
                SELECT b.c1
                FROM test_b b
                WHERE b.c2 = substr(a.c2, 1, 1)
        )
FROM test_a a
GROUP BY substr(a.c2, 1, 1);
```

返回结果为：

```
 substr | c1
--------+----
 a      | 11
 b      | 22
(2 rows)
```
