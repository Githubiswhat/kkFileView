### Insert All&First子句语法

**功能描述**

Insert All&First语句允许用户在一条Insert语句中往不同的表同时插入数据，也可以实现有条件的插入。

- Insert All表示所有满足条件的插入语句都会被执行。
- Insert First在执行第一个满足条件的插入语句后就会结束。

**语法格式**

```
Insert [ First | All ] 
[ WHEN { Condition } THEN INTO table [ [ AS ] alias ] Values (v1, ...), ...]
{ Subquery }
```

**参数说明**

WHEN { Condition }：任意一个判断表达式，表达式中可以使用常量或引用查询中的表字段。

**注意事项**

 无。

**示例**

1、创建测试表并插入数据。

```
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

```
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

```
select * from small_orders;
```

返回结果如下，则表示插入数据成功:

```
 order_id | customer_id | order_total
----------+-------------+-------------
        1 |       10000 |           1
        1 |       20000 |           1
        1 |       30000 |           1
(3 rows)
```

```
select * from medium_orders;
```

返回结果如下，则表示插入数据成功:

```
 order_id | customer_id | order_total
----------+-------------+-------------
        1 |      110000 |           1
        1 |      120000 |           1
        1 |      130000 |           1
        1 |      140000 |           1
        1 |      140000 |           1
(5 rows)
```

```
select * from large_orders;
```

返回结果如下，则表示插入数据成功:

```
 order_id | customer_id | order_total
----------+-------------+-------------
        1 |      210000 |           1
        1 |      220000 |           1
        1 |      340000 |           1
(3 rows)

```

```
select * from special_orders;
```

返回结果如下，则表示插入数据成功:

```
 order_id | customer_id | order_total | other
----------+-------------+-------------+-------
(0 rows)
```

4、使用INSERT FIRST向不同表中插入数据。

```
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

```
select * from small_orders;
```

返回结果如下，则表示插入数据成功:

```
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

```
select * from medium_orders;
```

返回结果如下（与第三步查询结果相同时），则表示语句执行成功:

```
 order_id | customer_id | order_total
----------+-------------+-------------
        1 |      110000 |           1
        1 |      120000 |           1
        1 |      130000 |           1
        1 |      140000 |           1
        1 |      140000 |           1
(5 rows)
```

```
select * from large_orders;
```

返回结果如下（与第三步查询结果相同时），则表示语句执行成功:

```
 order_id | customer_id | order_total
----------+-------------+-------------
        1 |      210000 |           1
        1 |      220000 |           1
        1 |      340000 |           1
(3 rows)
```

```
select * from special_orders;
```

返回结果如下（与第三步查询结果相同时），则表示语句执行成功:

```
 order_id | customer_id | order_total | other
----------+-------------+-------------+-------
(0 rows)
```

