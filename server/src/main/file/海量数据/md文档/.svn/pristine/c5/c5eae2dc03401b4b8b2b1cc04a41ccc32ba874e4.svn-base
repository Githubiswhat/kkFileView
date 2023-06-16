#### REPLACE INTO语法

**功能描述**

replace是MySQL的一个扩展语法，它的工作原理与insert几乎相同，不同点在于表中存在唯一性约束时，replace into先删除冲突行，再插入新行。

**语法格式**

```
REPLACE  [INTO]  tbl_name  VALUES   …
REPLACE  [INTO]  tbl_name  VALUE  {expr  |  DEFAULT}   …
REPLACE  [INTO]  tbl_name  SET  col_name  =  value  [,  col_name  =  value  ]...
REPLACE  [INTO]  tbl_name  SELECT
```

**注意事项**

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

- 主体（用户）在使用replace语句，必须对操作的表（客体）具有insert和delete权限。

- 不能将要做replace操作的表通过子查询语句的形式，作为replace操作的目标值，例如以下写法是错误的：

```
REPLACE INTO test VALUES (1, 'Old', select aTime from test where id =2；
```

- replace语句是原MySQL数据库中语法，适用于行存表，针对Vastbase的列存表和MOT表是否支持replace语法，Vastbase G100 V2.2版本对该功能不做硬性要求。

- 在MySQL数据库中replace支持使用带有逗号分隔的分区、子分区或两者名称列表的partition子句进行显式分区选择。与insert一样，如果无法将新行插入这些分区或子分区中的任何一个，则replace语句将失败，错误为“ Found a row not matching the given partition set”。由于目前Vastbase无法在insert的时候显式指定分区，故Vastbase G100 V2.2版本对该功能不做硬性要求。当Vastbase实现insert 语法可以显式指定分区的操作后，replace也同步具备了该功能特性。



**示例**

1、创建兼容MySQL的库db_mysql，并进入。

```
CREATE DATABASE db_mysql DBCOMPATIBILITY='B';
\c db_mysql
```

2、创建一张表，并指定主键。

```
CREATE TABLE test (
id int,
id2 int,
name varchar(50) DEFAULT NULL,
PRIMARY KEY(id)
);
```

 3、插入测试数据，并查询结果。

```
replace test  values(1,1000,'test1');
replace test  values(2,2000,'test2');
replace test  values(3,3000,'test3');
replace test  values(4,4000,'test4');
select * from test;
```

结果返回如下：

```
----+------+-------
  1 | 1000 | test1
  2 | 2000 | test2
  3 | 3000 | test3
  4 | 4000 | test4
(4 rows)
```

 4、执行replace 操作的数据，会与主键产生冲突。

```
replace test  values(1,4000,'test5');
```

5、查询ID为1数据是否被替换。

```
select * from test;
```

当结果显示如下信息，则表示替换成功。

```
 id | id2  | name  
----+------+-------
  2 | 2000 | test2
  3 | 3000 | test3
  4 | 4000 | test4
  1 | 4000 | test5
(4 rows)
```

> <div align="left"><img src="image/image2.png" style="zoom:20%")</div>
>
> 如果表包含多个唯一索引，并且新行在不同的唯一索引中重复了不同旧行的值，则replace的单行可能出现替换多个旧行的结果（新插入一行，删除了多行）。

#### 