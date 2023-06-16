# REPLACE INTO

## **功能描述**

replace是MySQL的一个扩展语法。它的工作原理与insert几乎相同，不同点在于当表中存在唯一性约束时，replace into先删除冲突行，再插入新行。

## **语法格式**

```sql
REPLACE [LOW_PRIORITY | DELAYED] [INTO]  tbl_name  VALUES   …
REPLACE [LOW_PRIORITY | DELAYED] [INTO]  tbl_name  VALUE  {expr  |  DEFAULT}   …
REPLACE [LOW_PRIORITY | DELAYED] [INTO]  tbl_name  SET  col_name  =  value  [,  col_name  =  value  ]...
REPLACE [LOW_PRIORITY | DELAYED] [INTO]  tbl_name col_name  =  value  [,  col_name  =  value  ]... {SELECT ...| TABLE table_name}
```

## 参数说明

**LOW_PRIORITY | DELAYED**

- LOW_PRIORITY  内部优先级控制中的低优先级。

  使用LOW_PRIORITY关键词，则INSERT的执行被延迟，直到没有其它客户端从表中读取为止。会影响DELETE、INSERT、LOAD DATA 、REPLACE和UPDATA语句的执行调度。

- DELAYED 延迟执行操作。
	使用DELAYED关键字时，服务器会把待插入的行放到一个缓冲器中，而发送INSERT DELAYED语句的客户端会继续运行。

- LOW_PRIORITY和DELAYED关键字不可同时出现。

## **注意事项**

- 该特性仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

- 主体（用户）在使用replace语句时，必须对操作的表（客体）具有insert和delete权限。

- replace语句是原MySQL数据库中语法，适用于行存表。

- 在MySQL数据库中replace支持使用带有逗号分隔的分区、子分区或两者名称列表的partition子句进行显式分区选择。与insert一样，如果无法将新行插入这些分区或子分区中的任何一个，则replace语句将失败，错误为

  ```sql
  Found a row not matching the given partition set
  ```

## **示例**

1、创建兼容MySQL的库db_mysql，并进入。

```sql
CREATE DATABASE db_mysql DBCOMPATIBILITY='B';
\c db_mysql
show sql_compatibility;
```

查看兼容性结果为：

```sql
 sql_compatibility
-------------------
B
(1 row)
```

2、创建一张表，并指定主键。

```sql
CREATE TABLE test (
id int,
id2 int,
name varchar(50) DEFAULT NULL,
PRIMARY KEY(id)
);
```

 3、插入测试数据，并查询结果。

```sql
replace test  values(1,1000,'test1');
replace test  values(2,2000,'test2');
replace test  values(3,3000,'test3');
replace test  values(4,4000,'test4');
select * from test order by id;
```

结果返回如下：

```sql
 id | id2  | name
----+------+-------
  1 | 1000 | test1
  2 | 2000 | test2
  3 | 3000 | test3
  4 | 4000 | test4
(4 rows)
```

 4、执行replace 操作的数据，并查询ID为1数据是否被替换。

```sql
replace test values(1,4000,'test5');
select * from test order by id;
```

当结果显示如下信息，则表示替换成功：

```sql
 id | id2  | name
----+------+-------
  1 | 4000 | test5
  2 | 2000 | test2
  3 | 3000 | test3
  4 | 4000 | test4
(4 rows)
```

5、使用LOW_PRIORITY关键字执行replace操作，并查看表内容。

```sql
replace low_priority test value(2,6000,'test0');
select * from test order by id;
```

当结果显示如下信息，则表示替换成功：

```sql
 id | id2  | name
----+------+-------
  1 | 4000 | test5
  2 | 6000 | test0
  3 | 3000 | test3
  4 | 4000 | test4
(4 rows)
```

6、使用关键字DELAYED执行replace操作，并查看表内容。

```sql
replace delayed test value(3,6000,'test2');
select * from test order by id;
```

当结果显示如下信息，则表示替换成功：

```sql
 id | id2  | name
----+------+-------
  1 | 4000 | test5
  2 | 6000 | test0
  3 | 6000 | test2
  4 | 4000 | test4
(4 rows)
```

> <div align="left"><img src="image/image2.png" style="zoom:20%")</div>
>
> 如果表包含多个唯一索引，并且新行在不同的唯一索引中重复了不同旧行的值，则replace的单行可能出现替换多个旧行的结果（新插入一行，删除了多行）。

