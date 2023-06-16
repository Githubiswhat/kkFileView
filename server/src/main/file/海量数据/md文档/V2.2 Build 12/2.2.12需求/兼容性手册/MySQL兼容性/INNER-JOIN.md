# INNER JOIN

## 功能描述

在MySQL兼容模式下支持INNER JOIN连接条件使用WHERE子句。

## 语法格式

```sql
SELECT col_name FROM table1 INNER JOIN table2 WHERE condition
```

## 参数说明

- **col_name**

  字段名。

- **table1、table2**

  内连接的表名。

- **condition**

  条件表达式。

## 注意事项

该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 示例

1、创建兼容MySQL的库db_mysql，并进入。

```sql
CREATE DATABASE db_mysql dbcompatibility='B';
\c db_mysql
```

2、创建测试表并插入数据。

```sql
create table test1(id int,name varchar(10),score numeric,date1 date,c1 bytea);
insert into test1 values(1,'aaa',97.1,'1999-12-12','0101');
insert into test1 values(5,'bbb',36.9,'1998-01-12','0110');
insert into test1 values(3,'ccc',89.2,'2200-03-12','0111');
insert into test1 values(7,'uuu',99.9,'1987-01-01','1000');
insert into test1 values(30,'ooo',90.1,'2023-01-30','1001');
insert into test1 values(6,'hhh',60,'2022-12-22','1010');
insert into test1 values(7,'fff',71,'2001-11-23','1011');
insert into test1 values(-1,'yaya',77.7,'2008-09-10','1100');
insert into test1 values(7,'fff',71,'2001-11-23','1011');  
create table test2(id int,name varchar(10),score numeric,date1 date,c1 bytea);
insert into test2 values(1,'aaa',99.1,'1998-12-12','0101');
insert into test2 values(2,'hhh',36.9,'1996-01-12','0110');
insert into test2 values(3,'ddd',89.2,'2000-03-12','0111');
insert into test2 values(7,'uuu',60.9,'1997-01-01','1000');
insert into test2 values(9,'ooo',90.1,'2021-01-30','1001');
insert into test2 values(6,'hhh',90,'2022-10-22','1010');
insert into test2 values(11,'eee',71,'2011-11-20','1011');
insert into test2 values(-1,'yaya',76.7,'2008-09-10','1100');
insert into test2 values(7,'uuu',60.9,'1997-01-01','1000');
```

3、使用where指定连接条件查询数据。

```sql
select * from test1 inner join test2 where test1.id=test2.id and test1.score>60;
```

结果返回如下：

```sql
 id | name | score |   date1    |     c1     | id | name | score |   date1    |     c1
----+------+-------+------------+------------+----+------+-------+------------+------------
  1 | aaa  |  97.1 | 1999-12-12 | \x30313031 |  1 | aaa  |  99.1 | 1998-12-12 | \x30313031
  3 | ccc  |  89.2 | 2200-03-12 | \x30313131 |  3 | ddd  |  89.2 | 2000-03-12 | \x30313131
  7 | fff  |    71 | 2001-11-23 | \x31303131 |  7 | uuu  |  60.9 | 1997-01-01 | \x31303030
  7 | fff  |    71 | 2001-11-23 | \x31303131 |  7 | uuu  |  60.9 | 1997-01-01 | \x31303030
  7 | uuu  |  99.9 | 1987-01-01 | \x31303030 |  7 | uuu  |  60.9 | 1997-01-01 | \x31303030
 -1 | yaya |  77.7 | 2008-09-10 | \x31313030 | -1 | yaya |  76.7 | 2008-09-10 | \x31313030
  7 | fff  |    71 | 2001-11-23 | \x31303131 |  7 | uuu  |  60.9 | 1997-01-01 | \x31303030
  7 | fff  |    71 | 2001-11-23 | \x31303131 |  7 | uuu  |  60.9 | 1997-01-01 | \x31303030
  7 | uuu  |  99.9 | 1987-01-01 | \x31303030 |  7 | uuu  |  60.9 | 1997-01-01 | \x31303030
(9 rows)
```

4、缺省INNER关键字查询数据。

```sql
select * from test1 join test2 where test1.date1=test2.date1;
```

结果返回如下：

```sql
 id | name | score |   date1    |     c1     | id | name | score |   date1    |     c1
----+------+-------+------------+------------+----+------+-------+------------+------------
 -1 | yaya |  77.7 | 2008-09-10 | \x31313030 | -1 | yaya |  76.7 | 2008-09-10 | \x31313030
(1 row)
```

5、查询具体列值。

```sql
select test1.score from test2 join test1 where test2.id=test1.id and test2.name='yaya';
```

结果返回如下：

```sql
 score
-------
  77.7
(1 row)
```

6、使用distinct关键字返回唯一不同的值。

```sql
select distinct test1.id from test1 inner join test2 where test1.id=test2.id;
```

结果返回如下：

```sql
 id
----
  1
  3
  6
  7
 -1
(5 rows)
```

7、使用order by语句。

```sql
select * from test1 inner join test2 where test1.id=test2.id order by 1,2;
```

结果返回如下：

```sql
 id | name | score |   date1    |     c1     | id | name | score |   date1    |     c1
----+------+-------+------------+------------+----+------+-------+------------+------------
 -1 | yaya |  77.7 | 2008-09-10 | \x31313030 | -1 | yaya |  76.7 | 2008-09-10 | \x31313030
  1 | aaa  |  97.1 | 1999-12-12 | \x30313031 |  1 | aaa  |  99.1 | 1998-12-12 | \x30313031
  3 | ccc  |  89.2 | 2200-03-12 | \x30313131 |  3 | ddd  |  89.2 | 2000-03-12 | \x30313131
  6 | hhh  |    60 | 2022-12-22 | \x31303130 |  6 | hhh  |    90 | 2022-10-22 | \x31303130
  7 | fff  |    71 | 2001-11-23 | \x31303131 |  7 | uuu  |  60.9 | 1997-01-01 | \x31303030
  7 | fff  |    71 | 2001-11-23 | \x31303131 |  7 | uuu  |  60.9 | 1997-01-01 | \x31303030
  7 | fff  |    71 | 2001-11-23 | \x31303131 |  7 | uuu  |  60.9 | 1997-01-01 | \x31303030
  7 | fff  |    71 | 2001-11-23 | \x31303131 |  7 | uuu  |  60.9 | 1997-01-01 | \x31303030
  7 | uuu  |  99.9 | 1987-01-01 | \x31303030 |  7 | uuu  |  60.9 | 1997-01-01 | \x31303030
  7 | uuu  |  99.9 | 1987-01-01 | \x31303030 |  7 | uuu  |  60.9 | 1997-01-01 | \x31303030
(10 rows)
```

