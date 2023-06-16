# JSON_VALID

## 功能描述

Vastbase在MySQL兼容模式下，支持JSON_VALID函数，用于判读输入值是否为合法的JSON数据。如果输入值是合法的JSON数据则返回1，否则返回0。

## 语法格式

```sql
JSON_VALID(val)
```

## 参数说明

**val**

输入值。

> <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
>
>  当输入值为null时，返回null。
>

## 注意事项

该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建并切换至兼容模式为MySQL的数据库db_mysql。

```sql
CREATE DATABASE db_mysql dbcompatibility='B';    
\c db_mysql
```

**示例1：**直接调用函数。

```sql
select json_valid('"sss"');
```

返回结果分别为：

```SQL
json_valid
------------
          1
(1 row)
```

**示例2：**在DML中使用JSON_VALID函数。

1、创建表。

```sql
create table tab_1116530(id int,a int,c text default json_valid('9'));
```

2、插入数据。

```sql
insert into tab_1116530 values(8,json_valid('1'),'"a"');
insert into tab_1116530 values('2',json_valid('1'),'"s"');
insert into tab_1116530 values('2',9);
```

3、查看数据。

```sql
select * from tab_1116530;
```

返回结果为：

```sql
 id | a |  c
----+---+-----
  8 | 1 | "a"
  2 | 1 | "s"
  2 | 9 | 1
(3 rows)
```

4、在select语句中使用JSON_VALID函数。

```sql
select id,json_valid(id) from tab_1116530;
```

返回结果为：

```sql
 id | json_valid
----+------------
  8 |          0
  2 |          0
  2 |          0
(3 rows)
```

5、更新并查看数据。

```sql
update tab_1116530 set id=3 where json_valid(id)=0;
select * from tab_1116530;
```

返回结果为：

```
 id | a |  c
----+---+-----
  3 | 1 | "a"
  3 | 1 | "s"
  3 | 9 | 1
(3 rows)
```

6、删除并查看数据。

```sql
delete from tab_1116530 where json_valid(id)=0;
select * from tab_1116530;
```

返回结果为：

```sql
 id | a | c
----+---+---

(0 rows)
```