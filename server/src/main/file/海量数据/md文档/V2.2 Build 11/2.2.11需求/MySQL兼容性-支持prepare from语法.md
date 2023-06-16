# MySQL兼容性-支持prepare from语法

## 功能描述

在MySQL兼容模式下，Vastbase G100支持使用prepare from语法给一个SQL语句赋予一个名字，并在后续的操作中通过引用该名称来实现对SQL语句的执行等操作。prepare from语句创建后可以在视图pg_prepared_statements中查到。

## 语法格式

```sql
PREPARE stmt_name FROM preparable_stmt
```

## 参数说明

- **stmt_name：**SQL语句的名字。
- **preparable_stmt：**可以是一个字符串或一个包含SQL语句的变量。SQL语句需要是一个单独的语句，不能是多条语句的组合。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

- 仅Vastbase G100 2.2 Build 11及以后版本支持此功能。

## 示例

1、创建并切换至兼容模式为B的数据库下。

```sql
CREATE DATABASE date_test_mysql DBCOMPATIBILITY 'B';
\c date_test_mysql
```

2、创建测试表并插入数据。

```sql
CREATE TABLE example(
id  INTEGER NOT NULL,
c2  character(16)
);
insert into example values(generate_series(1,10),'test1');
```

3、用prepare from语法为SQL语句命名，并用EXECUTE执行。

- 命名SELECT语句并执行。

```sql
PREPARE stmt1 FROM 'SELECT * from example limit 3';
EXECUTE stmt1;
```

返回结果为：

```sql
 id |        c2
----+------------------
  1 | test1
  2 | test1
  3 | test1
(3 rows)
```

- 命名INSERT语句，执行该语句并查看插入结果。

```sql
PREPARE stmt2 FROM 'insert into example(id) values(102)';
EXECUTE stmt2;
select * from example where id=102;
```

返回结果为：

```sql
 id  | c2
-----+----
 102 |
(1 row)
```

- 命名UPDATE语句，执行该语句并查看更新结果。

```sql
PREPARE stmt3 FROM  update example set c2='test01' where id=$1;
EXECUTE stmt3(102);
select * from example where id=102;
```

返回结果为：

```sql
 id | c2
----+----
(0 rows)
```

- 命名DELETE语句，执行该语句并查看结果。

```sql
PREPARE stmt4 FROM delete from example where id=1;
EXECUTE stmt4;
select * from example order by id;
```

返回结果为：

```sql
 id  |        c2
-----+------------------
   2 | test1
   3 | test1
   4 | test1
   5 | test1
   6 | test1
   7 | test1
   8 | test1
   9 | test1
  10 | test1
 102 |
(10 rows)
```

4、在视图pg_prepared_statements中查看名称为stmt3的语句。

```sql
select * from pg_prepared_statements where name='stmt3';
```

返回结果为：

```sql
 name  |                            statement                            |         prepare_time          | parameter_types | from_sql
-------+-----------------------------------------------------------------+-------------------------------+-----------------+----------
 stmt3 | PREPARE stmt3 FROM  update example set c2='test01' where id=$1; | 2022-11-16 17:41:40.938751+08 | {integer}       | t
(1 row)
```

