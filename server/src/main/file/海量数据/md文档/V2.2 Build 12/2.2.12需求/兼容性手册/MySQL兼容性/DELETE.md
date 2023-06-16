补充DELETE语法及参数说明中的跳转链接

# DELETE

## 功能描述

DELETE语法用于从指定的表里删除满足WHERE子句的行。如果WHERE子句不存在，将删除表中所有行，结果只保留表结构。

Vastbase G100在MySQL兼容模式下，支持多表删除。

其余关于DELETE的用法请参考[DELETE](DELETE.md)。

## 语法格式

```sql
[ WITH [ RECURSIVE ] with_query [, ...] ]
DELETE [/*+ plan_hint */] [FROM] 
    {[ ONLY ] table_name [ * ] [ [ [partition_clause]  [ [ AS ] alias ] ] | [ [ [ AS ] alias ] [partitions_clause] ] ]} [, ...]
    [ USING using_list ]
    [ WHERE condition | WHERE CURRENT OF cursor_name ];
```

或

```sql
[ WITH [ RECURSIVE ] with_query [, ...] ]
DELETE [/*+ plan_hint */]
    {[ ONLY ] table_name [ * ] [ [ [partition_clause]  [ [ AS ] alias ] ] | [ [ [ AS ] alias ] [partitions_clause] ] ]} [, ...]
    [ FROM using_list ]
    [ WHERE condition | WHERE CURRENT OF cursor_name ];
```

## 参数说明

**using_list**

using子句。using_list指定关联表的集合时可以同时出现目标表，并且可以定义表的别名并在目标表中使用。其他模式下则目标表不可重复出现在using_list中。

其他参数请参考DELETE语法[参数说明](DELETE.md#canshushuoming)。

## 注意事项

该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建兼容模式为MySQL的库db_mysql，并进入。

```
create  database  db_mysql  dbcompatibility  'B';
\c  db_mysql
```

1、创建测试表并插入数据。

```sql
create table tb_1132876(c1 int,c2 int,c3 date,c4 varchar(20),c5 varchar(20));
insert into tb_1132876 values(1 ,1 ,'1990-12-11','A1','B1');
insert into tb_1132876 values(2 ,2 ,'1990-12-12','A2 ','B2 ');

create table tb_1132876_1 (col1 int,col2 int,col3 date,col4 varchar(20),col5 varchar(20));
insert into tb_1132876_1 values(1 ,1 ,'1990-12-11','A1','B1');
insert into tb_1132876_1 values(2 ,2 ,'1990-12-12','A2 ','B2 ');
insert into tb_1132876_1 values(3 ,3 ,'1990-12-13','A3 ','B3 ');
insert into tb_1132876_1 values(4 ,4 ,'1990-12-14','A4 ','B4 ');
```

2、执行多表删除，使用如下命令删除表tb_1132876中从c1=2或者c2=2的数据，并且删除表tb_1132876_1表中全部数据。

```sql
delete from tb_1132876 t1,tb_1132876_1 t2 where t1.c1 in (select col1 from tb_1132876_1 where col2 = 2) or c2 = 2 ;
```

3、查询tb_1132876表中数据。

```sql
select * from tb_1132876;
```

返回结果为：

```sql
c1 | c2 |     c3     | c4 | c5
----+----+------------+----+----
  1 |  1 | 1990-12-11 | A1 | B1
(1 row)
```

4、查询tb_1132876_1表中数据。

```sql
select * from tb_1132876_1;
```

返回结果为：

```sql
c1 | c2 |     c3     | c4 | c5
----+----+------------+----+----
(0 row)
```

5、使用如下命令可删除两张表中的全部数据。

```sql
delete from tb_1132876 t1,tb_1132876_1 t2;
```

