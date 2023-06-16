# PG兼容性-支持表增加自增列功能

## **功能描述**

对已存在的表，支持在修改时对表增加自增列。

## **语法格式**

```sql
ALTER TABLE test ADD COLUMN column_id serial|bigserial|smallserial;
```

## **参数说明**

**column_id：**自增列名。

## **注意事项**

- 该功能仅在数据库兼容模式为PostgreSQL时能够使用（即创建DB时DBCOMPATIBILITY='PG'），在其他数据库兼容模式下不能使用该特性。
- 增加一列自增列的前提是表存在。
- 一次修改操作能增加一列自增列。
- 本功能不支持在列存表、临时表中修改。

## **示例**

1、创建数据库，设置兼容性为PostgreSQL。

```sql
CREATE DATABASE my_test DBCOMPATIBILITY='PG';
\c my_test
show sql_compatibility;
```

查看兼容性结果为：

```sql
 sql_compatibility
-------------------
 PG
(1 row)
```

2、创建表。

```sql
CREATE TABLE test(a int);
```

3、修改表增加一列自增列。

```sql
ALTER TABLE test ADD COLUMN id1 serial;
```

4、往表中插入数据后查看表内容。

```sql
insert into test values(1);
insert into test values(0);
insert into test values(2);
select * from test;
```

返回结果为：

```sql
 a | id1
---+-----
 1 |   1
 0 |   2
 2 |   3
(3 rows)
```

