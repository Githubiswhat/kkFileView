# IGNORE|FORCE INDEX语法

## 功能描述

在MySQL兼容模式下，Vastbase G100支持使用IGNORE|FORCE INDEX语法为优化器提供有关如何在查询处理期间选择索引的信息。

- 使用IGNORE INDEX时，表示不强制使用某个索引来获取查询结果。
- 使用FORCE INDEX时，表示强制使用某个索引来获取查询结果。

## 语法格式

```sql
table_name [[AS]alias] [index_hint_list]

index_hint_list:
	index_hint[index_hint]...
	
index_hint:
	USE {INDEX|KEY}
	[FOR {JOIN|ORDER BY|GROUP BY}]([index_list])|{IGNORE|FORCE}{INDEX|KEY}(index_list)

index_list:              
	index_name[,index_name...]
```

## 参数说明

- **table_name**

	查询用的表名称。

- **[AS]alias**

	可选项，表别名。

- **index_hint**

	索引提示。即如何选择索引信息。

- **index_list**

	索引列表。包含当前表的所有索引名（index_name）。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- FORCE语法仅Vastbase G100 2.2 Build 11及以后版本支持使用，IGNORE语法仅Vastbase G100 2.2 Build 12及以后版本支持使用。
- 仅支持在SELECT语句中使用该语法。
- USE INDEX和FORCE INDEX不能同时使用。

## 示例

**前置条件：**创建并切换至兼容模式为B的数据库下。

```sql
CREATE DATABASE db_1097149 DBCOMPATIBILITY 'B';
\c db_mysql
```

**示例1：**使用force index指定索引。

1、创建测试表，并插入数据。

```sql
create table db_1097149_tb (col1 int ,col2 int,col3 int,col4 varchar(10));
insert into db_1097149_tb values(1,1,1,'a');
insert into db_1097149_tb values(1,2,2,'a');
insert into db_1097149_tb values(2,2,2,'a');
insert into db_1097149_tb values(2,2,3,'b');
insert into db_1097149_tb values(2,3,3,'b');
insert into db_1097149_tb values(3,3,4,'b');
insert into db_1097149_tb values(3,3,4,'a');
insert into db_1097149_tb values(3,4,5,'c');
insert into db_1097149_tb values(4,4,5,'c');
insert into db_1097149_tb values(4,null,1,'c');
```

2、创建索引。

```sql
create index index_1097149_1 on db_1097149_tb (col1);
create index index_1097149_2 on db_1097149_tb (col2);
create index index_1097149_3 on db_1097149_tb (col3);
create index index_1097149_4 on db_1097149_tb (col4);
```

3、更新表的统计信息。

```sql
analyze db_1097149_tb;
```

4、使用force key指定索引进行查询。

```sql
select * from db_1097149_tb force key (index_1097149_2) where col2= 3;
select * from db_1097149_tb force key (index_1097149_4) where col2= 3 and col4 = 'a';
select * from db_1097149_tb FORCE key (index_1097149_1) where col2= 3;
```

返回结果为：

```sql
col1 | col2 | col3 | col4
------+------+------+------
    2 |    3 |    3 | b
    3 |    3 |    4 | b
    3 |    3 |    4 | a
(3 rows)

col1 | col2 | col3 | col4
------+------+------+------
    3 |    3 |    4 | a
(1 row)

col1 | col2 | col3 | col4
------+------+------+------
    2 |    3 |    3 | b
    3 |    3 |    4 | b
    3 |    3 |    4 | a
(3 rows)
```

5、查看步骤4中使用的查询执行计划。

```sql
explain (costs off)select * from db_1097149_tb force key (index_1097149_2) where col2= 3;
explain (costs off)select * from db_1097149_tb force key (index_1097149_4) where col2= 3 and col4 = 'a';
explain (costs off) select * from db_1097149_tb force key (index_1097149_1) where col2= 3;
```

返回结果为：

```sql
                    QUERY PLAN
---------------------------------------------------
 Index Scan using index_1097149_2 on db_1097149_tb
   Index Cond: (col2 = 3)
(2 rows)

                    QUERY PLAN
---------------------------------------------------
 Index Scan using index_1097149_4 on db_1097149_tb
   Index Cond: ((col4)::text = 'a'::text)
   Filter: (col2 = 3)
(3 rows)

        QUERY PLAN
---------------------------
 Seq Scan on db_1097149_tb
   Filter: (col2 = 3)
(2 rows)
```

**示例2：**ignore index使用唯一索引。

1、创建测试库并插入数据。

```sql
create table ignore_table (col1 int ,col2 int,col3 int,col4 varchar(10),primary key(col1)); 
insert into ignore_table values(1,1,1,'a'); 
insert into ignore_table values(2,2,2,'a'); 
insert into ignore_table values(3,3,2,'a'); 
insert into ignore_table values(4,4,3,'b'); 
insert into ignore_table values(5,5,3,'b'); 
insert into ignore_table values(6,6,6,'b'); 
insert into ignore_table values(7,7,7,'a'); 
insert into ignore_table values(8,8,8,'c'); 
insert into ignore_table values(9,9,9,'c'); 
insert into ignore_table values(10,null,1,'c'); 
```

2、创建唯一索引。

```sql
create unique index index_1130449 on ignore_table (col2); 
```

3、更新表的统计信息。

```sql
analyze ignore_table; 
```

4、使用ignore index进行查询。

```sql
select * from ignore_table ignore index (index_1130449) where col2= 3; 
select * from ignore_table ignore index (index_1130449) where col2= 3 and col4 = 'a'; 
select * from ignore_table IGNORE INDEX (index_1130449) where col2= 3; 
```

查询结果依次为：

```sql
 col1 | col2 | col3 | col4
------+------+------+------
    3 |    3 |    2 | a
(1 row)

 col1 | col2 | col3 | col4
------+------+------+------
    3 |    3 |    2 | a
(1 row)

 col1 | col2 | col3 | col4
------+------+------+------
    3 |    3 |    2 | a
(1 row)
```

5、查看步骤4中使用的查询执行计划。

```sql
explain (costs off )select * from ignore_table ignore index (index_1130449) where col2= 3; 
explain (costs off )select * from ignore_table ignore index (index_1130449) where col2= 3 and col4 = 'a'; 
explain (costs off) select * from ignore_table IGNORE INDEX (index_1130449) where col2= 3;
```

返回结果为：

```sql
        QUERY PLAN
--------------------------
 Seq Scan on ignore_table
   Filter: (col2 = 3)
(2 rows)

                      QUERY PLAN
-------------------------------------------------------
 Seq Scan on ignore_table
   Filter: ((col2 = 3) AND ((col4)::text = 'a'::text))
(2 rows)

        QUERY PLAN
--------------------------
 Seq Scan on ignore_table
   Filter: (col2 = 3)
```

**示例3：**使用USE INDEX指定索引。

1、创建测试表，并插入数据。

```sql
create table db_1097154_tb (col1 int ,col2 int,col3 int,col4 varchar(10));
insert into db_1097154_tb values(1,1,1,'a');
insert into db_1097154_tb values(1,2,2,'a');
insert into db_1097154_tb values(2,2,2,'a');
insert into db_1097154_tb values(2,2,3,'b');
insert into db_1097154_tb values(2,3,3,'b');
insert into db_1097154_tb values(3,3,4,'b');
insert into db_1097154_tb values(3,3,4,'a');
insert into db_1097154_tb values(3,4,5,'c');
insert into db_1097154_tb values(4,4,5,'c');
insert into db_1097154_tb values(4,null,1,'c');
```

2、创建索引。

```sql
create index index_1097154_1 on db_1097154_tb (col1);
create index index_1097154_2 on db_1097154_tb (col2);
create index index_1097154_3 on db_1097154_tb (col3);
create index index_1097154_4 on db_1097154_tb (col4);
```

3、更新表的统计信息。

```sql
analyze db_1097154_tb;
```

4、使用USE INDEX指定索引进行查询。

```sql
select col2 from db_1097154_tb * use key (index_1097154_2);
select * from db_1097154_tb use key (index_1097154_2) where col2= 3;
select * from db_1097154_tb use key (index_1097154_4) where col2= 3 and col4 = 'a';
select * from db_1097154_tb USE key (index_1097154_2) where col2= 3;
```

返回结果为：

```sql
 col2
------
    1
    2
    2
    2
    3
    3
    3
    4
    4

(10 rows)

 col1 | col2 | col3 | col4
------+------+------+------
    2 |    3 |    3 | b
    3 |    3 |    4 | b
    3 |    3 |    4 | a
(3 rows)

 col1 | col2 | col3 | col4
------+------+------+------
    3 |    3 |    4 | a
(1 row)

 col1 | col2 | col3 | col4
------+------+------+------
    2 |    3 |    3 | b
    3 |    3 |    4 | b
    3 |    3 |    4 | a
(3 rows)
```

5、查看步骤4中使用的查询执行计划。

```sql
explain (costs off)select col2 from db_1097154_tb * use key (index_1097154_2);
explain (costs off)select * from db_1097154_tb use key (index_1097154_2) where col2= 3;
explain (costs off)select * from db_1097154_tb use key (index_1097154_4) where col2= 3 and col4 = 'a';
explain (costs off) select * from db_1097154_tb USE key (index_1097154_2) where col2= 3;
```

返回结果为：

```sql
                      QUERY PLAN
--------------------------------------------------------
 Index Only Scan using index_1097154_2 on db_1097154_tb
(1 row)

                    QUERY PLAN
---------------------------------------------------
 Index Scan using index_1097154_2 on db_1097154_tb
   Index Cond: (col2 = 3)
(2 rows)

                    QUERY PLAN
---------------------------------------------------
 Index Scan using index_1097154_4 on db_1097154_tb
   Index Cond: ((col4)::text = 'a'::text)
   Filter: (col2 = 3)
(3 rows)

                    QUERY PLAN
---------------------------------------------------
 Index Scan using index_1097154_2 on db_1097154_tb
   Index Cond: (col2 = 3)
(2 rows)
```



