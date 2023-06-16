# LONG和LONG RAW

## 功能描述

LONG：可变长的字符串数据，具有VARCHAR2的特性，最多存储2GB的字符数据。

LONG RAW：可变长二级制数据，最多2GB的二进制数据。

> <div align="left"><img src="image/img3.png" style="zoom:75%")</div> 
>
> 不建议使用LONG RAW数据类型。

## 注意事项

- 一个表中只能有一个LONG或LONG RAW列。

- 不能创建具有LONG属性的对象类型。

- LONG列不能出现在WHERE子句或完整性约束中（除非它们可以出现在NULL和NOT NULL约束中）。

- LONG列无法创建索引。

- 不能在正则表达式中指定LONG数据。

- 存储的函数不能返回LONG值。

- 在单个SQL语句中，所有LONG列、更新表和锁定表必须位于同一数据库中。

- LONG和LONG RAW列不能在分布式SQL语句中使用，也不能复制。

- 如果一个表同时具有LONG和LOB列，那么在同一SQL语句中，不能将超过4000字节的数据绑定到LONG和LOB列。但是，您可以将超过4000字节的数据绑定到LONG列或LOB列。

- LONG列不能出现在SQL语句的如下部分中：
  - GROUP BY子句、ORDER BY子句或CONNECT BY子句，或在SELECT语句中使用DISTINCT运算符。
  - SELECT语句的UNIQUE运算符。
  - CREATE CLUSTER语句的列列表。
  - CREATE MATERIALIZED VIEW语句的CLUSTER子句。
  - SQL内置函数、表达式或条件。
  - SELECT子查询或由UNION、INTERSECT或MINUS集合运算符组合的查询列表。
  - SELECT语句中包含GROUP BY子句的查询列表。
  - CREATE TABLE…AS SELECT语句的SELECT列表。
  - ALTER TABLE…MOVE语句。
  - INSERT语句子查询中的SELECT列表。

- 触发器可以通过以下方式使用LONG数据类型：

  - 触发器中的SQL语句可以将数据插入LONG列。

  - 如果可以将LONG列中的数据转换为受约束的数据类型（如CHAR和VARCHAR2），则可以在触发器内的SQL语句中引用LONG列。

    > <div align="left"><img src="image/img3.png" style="zoom:75%")</div> 
    >
    > 无法使用LONG数据类型声明触发器中的变量。

- :NEW和:OLD不能与LONG列一起使用。

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建并切换至兼容模式为Oracle的数据库。

```sql
create database db_oracle dbcompatibility 'A';
\c db_oracle;
```

**示例1：**创建含有LONG数据类型的普通表，并进行增删改查。

1、创建包含LONG数据类型的普通表。

```sql
create table t_long(col1 int,col2 long);
```

2、插入数据。

```sql
insert into t_long values(1,'lisa');
insert into t_long values(2,'rose');
```

3、查询表。

```sql
select * from t_long;
```

返回结果为：

```sql
 col1 | col2
------+------
    1 | lisa
    2 | rose
(2 rows)
```

4、删除表中数据。

```sql
delete from t_long where col1=2;
```

5、更新表中数据。

```sql
update t_long set col1=2 where col2='lisa';
```

6、查询表。

```sql
select * from t_long;
```

返回结果为：

```sql
 col1 | col2
------+------
    2 | lisa
(1 row)
```

**示例2：**创建含有LONG RAW数据类型的普通表。

```sql
create table t_longraw(col1 int,col2 long raw);
```

创建成功，返回结果为：

```sql
CREATE TABLE
```

**示例3：**使用utl_raw.cast_to_raw()插入二进制raw数据。

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div> 
>
> UTL_RAW内置包的相关内容请参考[UTL_RAW](内置包/UTL_RAW.md)。

1、清理环境。

```sql
drop table t_longraw;
```

2、创建测试表。

```sql
create table t_longraw (id number, raw_date long raw);
```

3、使用utl_raw.cast_to_raw()插入二进制raw数据。

```sql
insert into t_longraw values(1,utl_raw.cast_to_raw('上岛咖啡sodifnASD'));
insert into t_longraw values(2,utl_raw.cast_to_raw('阿斯顿那是多少'));
```

4、查看表数据。

```sql
select *from t_longraw;
```

返回结果为：

```sql
 id |                  raw_date
----+--------------------------------------------
  1 | E4B88AE5B29BE59296E595A1736F6469666E415344
  2 | E998BFE696AFE9A1BFE982A3E698AFE5A49AE5B091
(2 rows)
```