# 支持在UNION [ALL] 语句中使用ORDER BY 子句

## 功能描述

Vastbase G100在MySQL兼容模式下支持使用UNION [ALL]语句将两个或多个查询结果合并，并通过ORDER BY子句排序。

## 注意事项

该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 示例

1、创建并切换至兼容模式为MySQL的数据库db_mysql。

```sql
CREATE DATABASE db_mysql dbcompatibility='B';    
\c db_mysql
```

2、创建测试表并插入数据。

```sql
CREATE TABLE a(id int,msg text);
CREATE TABLE b(id int,msg text);
INSERT INTO a VALUES(1,'a');
INSERT INTO a VALUES(2,'b');
INSERT INTO b VALUES(1,'a');
INSERT INTO b VALUES(2,'b');
```

3、使用UNION ALL语句返回所有查询结果并使用ORDER BY子句排序。

```sql
SELECT * FROM a UNION ALL SELECT * FROM b ORDER BY id;
```

结果返回如下：

```sql
 id | msg
----+-----
  1 | a
  1 | a
  2 | b
  2 | b
(4 rows)
```

4、使用UNION 语句压缩重复结果返回数据并使用ORDER BY子句排序。

```sql
SELECT * FROM a UNION SELECT * FROM b ORDER BY id;
```

结果返回如下：

```sql
 id | msg
----+-----
  1 | a
  2 | b
(2 rows)
```



