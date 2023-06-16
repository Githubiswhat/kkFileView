# Insert All&First子句语法

## **功能描述**

Insert All&First语句允许用户在一条Insert语句中往不同的表针对指定字段同时插入数据，也可以实现有条件的插入。

- Insert All表示所有满足条件的插入语句都会被执行。
- Insert First在执行第一个满足条件的插入语句后就会结束。

## **语法格式**

```sql
Insert [ First | All ] 
[ WHEN { Condition } THEN INTO table [ [ AS ] alias ] [(column_name[,...])] Values (v1, ...), ...]
{ Subquery }
```

## **参数说明**

- **WHEN { Condition }**

  任意一个判断表达式。。

  表达式中可以使用常量或引用查询中的表字段。

- **alias**

  表的别名。

- **column_name[,...]**

  指定字段的名称序列。

- **Subquery**

  子查询语句。

## **注意事项**

- 指定字段功能即column_name选项仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。
- 指定字段功能暂不支持视图操作、自增列和with语法。

## **示例**

**示例1** 使用insert all往不同的表批量指定字段插入数据。

1、创建数据库，设置兼容性为Oracle。

```sql
CREATE DATABASE my_test DBCOMPATIBILITY='A';
\c my_test
show sql_compatibility;
```

查看兼容性结果为：

```sql
 sql_compatibility
-------------------
 A
(1 row)
```

2、创建测试表1、表2。

```sql
create table test1
(id int,
sign_in date,
name varchar2(128),
last_id int,
last_name varchar2(128)
);

create table test2
(id int,
sign_in date,
name varchar2(128),
last_id int,
last_name varchar2(128)
);
```

3、指定字段批量插入数据。

```sql
INSERT ALL INTO test1 as TODO (id,sign_in,name,last_id,last_name)
VALUES (1,sysdate,'a',3,'c')
INTO test1 (id,sign_in,name)
VALUES (2,sysdate,'b')
INTO test2 (id,sign_in,name)
VALUES (1,sysdate,'a')
INTO test2 (id,sign_in,name,last_id,last_name)
VALUES (1,sysdate,'a',2,'b')
select 1 from dual;
```

4、查看插入数据结果。

```sql
select * from test1;
select * from test2;
```

显示结果为：

```sql
 id |    sign_in    | name | last_id | last_name
----+---------------+------+---------+-----------
  1 | 2022-10-14 14:23:31 | a    |       3 | c
  2 | 2022-10-14 14:23:31 | b    |         |
(2 rows)

 id |    sign_in    | name | last_id | last_name
----+---------------+------+---------+-----------
  1 | 2022-10-14 14:23:31 | a    |         |
  1 | 2022-10-14 14:23:31 | a    |       2 | b
(2 rows)
```

**示例2 ** 实现有条件的往不同的表批量插入数据。

1、创建测试表并插入数据。

```sql
CREATE TABLE small_orders 
  ( order_id       NUMERIC   NOT NULL,
    customer_id    NUMERIC    NOT NULL,
    order_total    NUMERIC );
CREATE TABLE medium_orders AS SELECT * FROM small_orders;
CREATE TABLE large_orders AS SELECT * FROM small_orders;
CREATE TABLE special_orders 
  (  order_id       NUMERIC  NOT NULL,
     customer_id    NUMERIC  NOT NULL,
     order_total    NUMERIC,
     other          NUMERIC
   );

CREATE TABLE orders 
(   order_id       NUMERIC NOT NULL,
    customer_id    NUMERIC NOT NULL,
    order_total    NUMERIC ,
    other          NUMERIC );

INSERT INTO orders SELECT 1, 1, 10000, 1 ;
INSERT INTO orders SELECT 1, 1, 20000, 1 ; 
INSERT INTO orders SELECT 1, 1, 30000, 1 ;
INSERT INTO orders SELECT 1, 1, 210000, 1 ;
INSERT INTO orders SELECT 1, 1, 220000, 1 ;
INSERT INTO orders SELECT 1, 1, 110000, 1 ;
INSERT INTO orders SELECT 1, 1, 120000, 1 ;
INSERT INTO orders SELECT 1, 1, 130000, 1 ;
INSERT INTO orders SELECT 1, 1, 140000, 1 ;
INSERT INTO orders SELECT 1, 1, 140000, 1 ;
INSERT INTO orders SELECT 1, 1, 340000, 1 ;
```

2、使用INSERT ALL向不同表中插入数据。

```sql
INSERT ALL
WHEN ottl < 100000 THEN
INTO small_orders
VALUES(oid, ottl, cid)
WHEN ottl > 100000 and ottl < 200000 THEN
INTO medium_orders
VALUES(oid, ottl, cid)
WHEN ottl > 200000 THEN
into large_orders
VALUES(oid, ottl, cid)
SELECT o.order_id oid, o.customer_id cid, o.order_total ottl,
o.other
FROM orders o;
```

3、查询INSERT ALL结果。

```sql
select * from small_orders;
```

返回结果如下，则表示插入数据成功:

```sql
 order_id | customer_id | order_total
----------+-------------+-------------
        1 |       10000 |           1
        1 |       20000 |           1
        1 |       30000 |           1
(3 rows)
```

```sql
select * from medium_orders;
```

返回结果如下，则表示插入数据成功:

```sql
 order_id | customer_id | order_total
----------+-------------+-------------
        1 |      110000 |           1
        1 |      120000 |           1
        1 |      130000 |           1
        1 |      140000 |           1
        1 |      140000 |           1
(5 rows)
```

```sql
select * from large_orders;
```

返回结果如下，则表示插入数据成功:

```sql
 order_id | customer_id | order_total
----------+-------------+-------------
        1 |      210000 |           1
        1 |      220000 |           1
        1 |      340000 |           1
(3 rows)
```

```sql
select * from special_orders;
```

返回结果如下，则表示插入数据成功:

```sql
 order_id | customer_id | order_total | other
----------+-------------+-------------+-------
(0 rows)
```

4、使用INSERT FIRST向不同表中插入数据。

```sql
INSERT First
WHEN ottl > 10000 THEN
INTO small_orders
VALUES(oid, ottl, cid)
INTO small_orders
VALUES(oid, ottl, cid)
INTO small_orders
VALUES(oid, ottl, cid)
WHEN ottl > 100000 THEN
INTO medium_orders
VALUES(oid, ottl, cid)
WHEN ottl > 200000 THEN
into large_orders
VALUES(oid, ottl, cid)
SELECT o.order_id oid, o.customer_id cid, o.order_total ottl,
o.other
FROM orders o;
```

5、查询INSERT First结果。

```sql
select * from small_orders;
```

返回结果如下，则表示插入数据成功:

```sql
 order_id | customer_id | order_total
----------+-------------+-------------
        1 |       10000 |           1
        1 |       20000 |           1
        1 |       30000 |           1
        1 |       20000 |           1
        1 |       20000 |           1
        1 |       20000 |           1
        1 |       30000 |           1
        1 |       30000 |           1
        1 |       30000 |           1
        1 |      210000 |           1
        1 |      210000 |           1
        1 |      210000 |           1
        1 |      220000 |           1
        1 |      220000 |           1
        1 |      220000 |           1
        1 |      110000 |           1
        1 |      110000 |           1
        1 |      110000 |           1
        1 |      120000 |           1
        1 |      120000 |           1
        1 |      120000 |           1
        1 |      130000 |           1
        1 |      130000 |           1
        1 |      130000 |           1
        1 |      140000 |           1
        1 |      140000 |           1
        1 |      140000 |           1
        1 |      140000 |           1
        1 |      140000 |           1
        1 |      140000 |           1
        1 |      340000 |           1
        1 |      340000 |           1
        1 |      340000 |           1
(33 rows)
```

```sql
select * from medium_orders;
```

返回结果如下（与第三步查询结果相同时），则表示语句执行成功：

```sql
 order_id | customer_id | order_total
----------+-------------+-------------
        1 |      110000 |           1
        1 |      120000 |           1
        1 |      130000 |           1
        1 |      140000 |           1
        1 |      140000 |           1
(5 rows)
```

```sql
select * from large_orders;
```

返回结果如下（与第三步查询结果相同时），则表示语句执行成功：

```sql
 order_id | customer_id | order_total
----------+-------------+-------------
        1 |      210000 |           1
        1 |      220000 |           1
        1 |      340000 |           1
(3 rows)
```

```sql
select * from special_orders;
```

返回结果如下（与第三步查询结果相同时），则表示语句执行成功：

```sql
 order_id | customer_id | order_total | other
----------+-------------+-------------+-------
(0 rows)
```

