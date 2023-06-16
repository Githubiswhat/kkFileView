# Oracle兼容性-GROUP_ID函数

## 功能描述

在Orace兼容模式下，Vastbase G100支持使用GROUP_ID函数。该函数可以返回一个整型值，用于标识由GROUP BY子句所返回的唯一重复组。

对于首次出现的分组，GROUP_ID的值为0；对于重复的分组， GROUP_ID的值从1开递增。（例如：有n个重复组，则用0至n-1标识分组。）

## 语法格式

```sql
SELECT grouping_element[,grouping_element],group_id() as group_id
[expression]
FROM table_name
[WHERE condition]
GROUP BY expr;
```

## 参数说明

- **grouping_element：**参与查询的字段名，或者含有函数/表达式的列。

- **expression：**SQL语句中的子查询。
- **table_name：**查询指定的表名。
- **WHERE condition：**WHERE子句指定的查询条件。
- **expr：**查询依据的分组规则，可以是字段名或者含有函数/表达式的列。

## 注意事项

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。
- 该函数仅适用于包含GROUP BY子句的SELECT语句。

## 示例

- **示例1：**group by分组方式为CUBE的查询


> <div align="left"><img src="image/image1.png" style="zoom:25%"></div>
>
> CUBE是自动对group by子句中列出的字段进行分组汇总，结果集将包含维度列中各值的所有可能组合，以及与这些维度值组合相匹配的基础行中的聚合值。它会为每个分组返回一行汇总信息，用户可以使用CUBE来产生交叉表值。比如，在CUBE子句中给出三个表达式（n =3），运算结果为2^n= 2^3 = 8组。以n个表达式的值分组的行称为常规行，其余的行称为超级聚集行。
>

1、创建并切换至兼容模式为A的数据库下。

```sql
CREATE DATABASE db_oracle DBCOMPATIBILITY 'A';
\c db_oracle
```

2、创建测试表并插入数据。

```sql
CREATE TABLE t_groupid(a int,b int);
INSERT INTO t_groupid VALUES(1,1),(1,1),(2,2),(2,2),(3,3),(3,null);
```

3、使用包含GROUP_ID函数的语句进行查询。

```sql
select a,b,group_id() as group_id from t_groupid group by cube((a,b),a);
```

返回结果为：

```sql
 a | b | group_id
---+---+----------
 1 | 1 |        0
 1 | 1 |        1
 1 |   |        0
 2 | 2 |        0
 2 | 2 |        1
 2 |   |        0
 3 | 3 |        0
 3 | 3 |        1
 3 |   |        0
 3 |   |        1
 3 |   |        0
   |   |        0
(12 rows)
```

- **示例2：**grouping_element中包含函数的查询

1、创建并切换至兼容模式为A的数据库下。

```sql
create database db_1103518 dbcompatibility 'B';
\c db_1103518
```

2、创建测试表并插入数据。

```sql
create table t_groupid(id number(10),name varchar2(10),sex varchar2(10),sala number(10));
insert into t_groupid values(1,'w','man',5000);
insert into t_groupid values(1,'w','man',5000);
insert into t_groupid values(1,'w','lady',5000);
insert into t_groupid values(1,'m','lady',5000);
insert into t_groupid values(2,'m','lady',5000);
insert into t_groupid values(3,'f','man',7500);
insert into t_groupid values(4,'f','lady',7500);
```

3、使用包含GROUP_ID函数的语句进行查询。

```sql
select id, name, sex, sum(sala),group_id() from t_groupid group by rollup((id,name), name, sex);
```

返回结果为：

```sql
 id | name | sex  |  sum  | group_id
----+------+------+-------+----------
  1 | m    | lady |  5000 |        0
  1 | m    |      |  5000 |        0
  1 | m    |      |  5000 |        1
  1 | w    | lady |  5000 |        0
  1 | w    | man  | 10000 |        0
  1 | w    |      | 15000 |        0
  1 | w    |      | 15000 |        1
  2 | m    | lady |  5000 |        0
  2 | m    |      |  5000 |        0
  2 | m    |      |  5000 |        1
  3 | f    | man  |  7500 |        0
  3 | f    |      |  7500 |        0
  3 | f    |      |  7500 |        1
  4 | f    | lady |  7500 |        0
  4 | f    |      |  7500 |        0
  4 | f    |      |  7500 |        1
    |      |      | 40000 |        0
(17 rows)
```

