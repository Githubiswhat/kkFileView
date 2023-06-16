补充UPDATE语法及参数说明中的跳转链接

# UPDATE

## 功能描述

UPDATE语法用于更新表中的数据。Vastbase G100在MySQL兼容模式下，支持多表更新。

其余关于UPDATE的用法请参考[UPDATE](UPDATE.md)。

## 语法格式

```sql
[ WITH [ RECURSIVE ] with_query [, ...] ]
UPDATE [/*+ plan_hint */] table_list
SET {column_name = { expression | DEFAULT } 
    |( column_name [, ...] ) = {( { expression | DEFAULT } [, ...] ) |sub_query }}[, ...]
    [ FROM from_list] [ WHERE condition ];
```

where sub_query can be：

```sql
SELECT [ ALL | DISTINCT [ ON ( expression [, ...] ) ] ]
{ * | {expression [ [ AS ] output_name ]} [, ...] }
[ FROM from_item [, ...] ]
[ WHERE condition ]
[ GROUP BY grouping_element [, ...] ]
[ HAVING condition [, ...] ]
[ ORDER BY {expression [ [ ASC | DESC | USING operator ] | nlssort_expression_clause ] [ NULLS { FIRST | LAST } ]} [, ...] ]
[ LIMIT { [offset,] count | ALL } ]
```

## 参数说明

**table_list**

一个表的表达式列表，与from_list类似，但可以同时声明目标表和关联表，仅在多表更新语法中使用。

其他参数请参考UPDATE语法[参数说明](UPDATE.md#canshushuoming)。

## 注意事项

该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建兼容模式为MySQL的库db_mysql，并进入。

```
create  database  db_mysql  dbcompatibility  'B';
\c  db_mysql
```

1、创建2张测试表并插入数据。

```sql
create table tb_1132824(c1 int,c2 int,c3 date,c4 varchar(20),c5 varchar(20));
insert into tb_1132824 values(1 ,1 ,'1990-12-11','A1','B1');
insert into tb_1132824 values(2 ,2 ,'1990-12-12','A2 ','B2 ');
insert into tb_1132824 values(3 ,3 ,'1990-12-13','A3 ','B3 ');

create table tb_1132824_1 (col1 int,col2 int,col3 date,col4 varchar(20),col5 varchar(20));
insert into tb_1132824_1 values(1 ,1 ,'1990-12-11','A1','B1');
insert into tb_1132824_1 values(2 ,2 ,'1990-12-12','A2 ','B2 ');
insert into tb_1132824_1 values(3 ,3 ,'1990-12-13','A3 ','B3 ');
insert into tb_1132824_1 values(4 ,4 ,'1990-12-14','A4 ','B4 ');
```

2、执行多表更新。

```sql
update tb_1132824 t1,tb_1132824_1 t2 set t1.c2 = 10 ,t2.col3='2023-12-31';
```

3、查询表tb_1132824中数据。

```sql
select * from tb_1132824;
```

返回结果如下所示，c2列内容已经被更新为10：

```sql
 c1 | c2 |     c3     | c4  | c5
----+----+------------+-----+-----
  1 | 10 | 1990-12-11 | A1  | B1
  2 | 10 | 1990-12-12 | A2  | B2
  3 | 10 | 1990-12-13 | A3  | B3
(3 rows)
```
4、查询表tb_1132824_1中数据。

```sql
select * from tb_1132824_1;
```

返回结果如下所示，col3列内容已经被更新为2023-12-31：

```sql
 col1 | col2 |    col3    | col4 | col5
------+------+------------+------+------
    1 |    1 | 2023-12-31 | A1   | B1
    2 |    2 | 2023-12-31 | A2   | B2
    3 |    3 | 2023-12-31 | A3   | B3
    4 |    4 | 2023-12-31 | A4   | B4
(4 rows)
```
5、执行如下语句进行多表更新。

```sql
update tb_1132824,tb_1132824_1 set tb_1132824.c2 = col1+10 ,tb_1132824_1.col3=c3 where c1=col1;
```

6、查询表tb_1132824中数据。

```sql
select * from tb_1132824;
```

返回结果如下所示，c2列的内容已经被更新为col1+10：

```sql
 c1 | c2 |     c3     | c4  | c5
----+----+------------+-----+-----
  1 | 11 | 1990-12-11 | A1  | B1
  2 | 12 | 1990-12-12 | A2  | B2
  3 | 13 | 1990-12-13 | A3  | B3
(3 rows)
```

7、查询表tb_1132824_1中数据。

```sql
select * from tb_1132824_1 order by col1;
```

返回结果如下所示，当c1=col1时，col3列内容已经被更新为表tb_1132824中的对应内容：

```sql
 col1 | col2 |    col3    | col4 | col5
------+------+------------+------+------
    1 |    1 | 1990-12-11 | A1   | B1
    2 |    2 | 1990-12-12 | A2   | B2
    3 |    3 | 1990-12-13 | A3   | B3
    4 |    4 | 2023-12-31 | A4   | B4
(4 rows)
```

