# OUTPUT

## 功能描述

Vastbase G100在SQL Server兼容模式下支持OUTPUT语法，返回受SQL语句影响的各行中的信息，包括`INSERT/UPDATE/DELETE OUTPUT`和`SELECT OUTPUT INTO`。

## 注意事项

本特性仅在数据库兼容模式为SQL Server时能够使用（即创建DB时DBCOMPATIBILITY='MSSQL'），在其他数据库兼容模式下不能使用该特性。

## 示例

1、创建兼容SQL Server的库db_sqlserver，并进入。

```sql
create database db_sqlserver dbcompatibility 'MSSQL';
\c db_sqlserver
```

2、创建测试表并插入测试数据。

```sql
CREATE TABLE t1(id int,name text,city text);
INSERT INTO t1 VALUES(1,'aaa','guangzhou');
INSERT INTO t1 VALUES(2,'bbb','beijing');
INSERT INTO t1 VALUES(3,'ccc','nanjing');
```

3、使用`INSERT OUTPUT`语句插入数据。

```sql
INSERT INTO t1 VALUES(4,'ddd','xian') output id,name;
```

结果返回如下：

```sql
 id | name
----+------
  4 | ddd
(1 row)

INSERT 0 1
```

4、使用`UPDATE OUTPUT`语句修改数据。

```sql
UPDATE t1 SET name='eee' WHERE id=4 OUTPUT id,name;
```

结果返回如下：

```sql
 id | name
----+------
  4 | eee
(1 row)

UPDATE 1
```

5、使用`DELETE OUTPUT`语句删除数据。

```sql
DELETE FROM t1 WHERE id=4 OUTPUT id,name;
```

结果返回如下：

```sql
 id | name
----+------
  4 | eee
(1 row)

DELETE 1
```

6、使用`SELECT OUTPUT INTO`创建新表t2。

```sql
SELECT id,name OUTPUT into table t2 FROM t1;
```

结果返回如下：

```sql
 id | name
----+------
  1 | aaa
  2 | bbb
  3 | ccc
(3 rows)

INSERT 0 3
```

7、查询t2表中数据

```sql
SELECT * FROM t2;
```

结果返回如下：

```sql
 id | name
----+------
  1 | aaa
  2 | bbb
  3 | ccc
(3 rows)
```

