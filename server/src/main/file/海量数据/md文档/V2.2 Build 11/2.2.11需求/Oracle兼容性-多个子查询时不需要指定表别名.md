# 多个子查询时不需要指定表别名

## 功能描述

在Oracle兼容模式下，Vastbase G100在使用多个子查询时可以省略表的别名。

## 语法格式

```sql
SELECT * FROM
(SELECT ... FROM table_name WHERE ...),
(SELECT ... FROM table_name WHERE ...),
(SELECT ... FROM table_name WHERE ...),
...
```

## 参数说明

table_name：子查询中涉及到的表名。每个子句中的表名可以是相同的，也可以是不同的。

## 注意事项

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。
- Vastbase中不支持多个子查询别名相同。

## 示例

1、创建数据库my_test，检查兼容性。

```sql
CREATE DATABASE dbtest DBCOMPATIBILITY 'A';
\c dbtest;
show sql_compatibility;
```

2、创建测试表，并插入数据。

```sql
create table t_test(id int,name varchar);
insert into t_test values(1,'jack');
insert into t_test values(2,'grace');
insert into t_test values(3,'frank');
```

3、进行多个不带表别名的子查询。

```sql
select * from (select id from t_test where id=1),
(select name from t_test where id =2);
```

返回结果如下，表示查询成功。

```sql
 id | name
----+-------
  1 | grace
(1 row)
```

