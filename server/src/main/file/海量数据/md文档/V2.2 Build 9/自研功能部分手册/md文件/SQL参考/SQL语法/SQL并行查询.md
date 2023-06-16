一条查询语句在所涉及的表中的数据量达到阈值时，由规划器生成的计划去执行扫描时性能会比较差。因此需要使用多个worker线程并行各自去执行扫描表操作，在Vastbase G100 V2.2版本中支持[并行顺序扫描](#并行顺序扫描)、[并行嵌套查询](#并行嵌套查询)、[并行哈希连接](#并行哈希连接)、[并行索引扫描](#并行索引扫描)。


### 并行顺序扫描<a id="并行顺序扫描"></a>

**功能描述**

并行顺序扫描就是利用多核CPU的并行处理能力，将一个表的顺序查询语句由多个并行worker线程去执行查询操作。

**注意事项**

- 不支持临时表、外表、分区表、非行存表的并行查询。
- 不支持CET查询，并行join，并行子查询等。

**示例**

1、创建测试表test并插入数据。

```
create table test(id int,name text,age int);
insert into test values(generate_series(1,1000000),'vastbase',34);
```

2、指定分析表test。

```
analyze test;
```

3、设置并行度。

```
set query_dop=5;
```

4、打印并行查询计划。

```
explain select count(*) from test where id<100;
```

返回结果如下，则表示支持该功能：

```
                                      QUERY PLAN                                 
     
---------------------------------------------------------------------------------
-----
 Aggregate  (cost=8100.25..8100.26 rows=1 width=8)
   ->  Streaming(type: LOCAL GATHER dop: 1/5)  (cost=8100.25..8100.26 rows=1 widt
h=8)
         ->  Aggregate  (cost=8100.25..8100.26 rows=1 width=8)
               ->  Seq Scan on test  (cost=0.00..8100.00 rows=100 width=0)
                     Filter: (id < 100)
(5 rows)
```

### 并行索引扫描<a id="并行索引扫描"></a>

**功能描述**

并行索引扫描就是利用多核CPU的并行处理能力，索引查询可以由多个并行worker线程执行。

当一条sql语句的条件子句中涉及的属性是索引时，查询编译器就会生成并行索引扫描路径，根据代价估算得到并行索引扫描计划，执行器在选择扫描计划时，会将索引扫描计划分给多个worker来执行。

**注意事项**

- 不支持临时表、外表、分区表、非行存表的并行查询。
- 不支持CET查询。
- 只支持并行btree索引扫描，bitmap，gin，gist等索引查询不支持。


**示例**

1、创建测试表并插入数据。

```
create table parallel_t2(a int,b int);
insert into parallel_t2 values(generate_series(1,100000), generate_series(1,100000));
```

2、创建btree索引。

```
create index t2_idx on parallel_t2 using btree(a);
```

3、设置扫描开关和并行代价。

```
set enable_seqscan to off;
set enable_bitmapscan to off;
```

4、.打印并行执行计划。

```
explain (costs off) select count(b) from parallel_t2 where a > 5000;
```

返回结果为：

```
                  QUERY PLAN
----------------------------------------------
 Aggregate
   ->  Index Scan using t2_idx on parallel_t2
         Index Cond: (a > 5000)
(3 rows)
```

```
explain (costs off) select count(b) from parallel_t2 where a < 5000;
```

返回结果为：

```
                  QUERY PLAN
----------------------------------------------
 Aggregate
   ->  Index Scan using t2_idx on parallel_t2
         Index Cond: (a < 5000)
(3 rows)

```

5、执行查询。

```
select count(b) from parallel_t2 where a > 5000;
```

返回结果为：

```
 count
-------
 95000
(1 row)
```

```
select count(b) from parallel_t2 where a < 5000;
```

返回结果为：

```
 count
-------
  4999
(1 row)
```

### 并行嵌套查询<a id="并行嵌套查询"></a>

**功能描述**

并行嵌套查询就是利用多核CPU的并行处理能力，将一个多表连接查询语句由多个并行worker线程执行。

**注意事项**

- 不支持临时表、外表、分区表、非行存表的并行查询。
- 不支持CET查询，并行子查询等。

**示例**

1、创建测试表并插入数据。

```
create table test_a (id int, col varchar(20));
create table test_b (id int, col int);
create table t3(id int, id2 int, id3 int);
insert into test_a  values(generate_series(1,200000), 'xiaohong');
insert into test_b  values(generate_series(100,200000), 20);
insert into t3 values(generate_series(1, 200000), generate_series(1, 100), generate_series(1, 10));
```

2、设置并行开关。

```
set enable_nestloop=on;
set enable_mergejoin=off;
set enable_hashjoin=off;
set query_dop=2;
```

3、打印嵌套循环并行查询计划。

```
explain (costs off) 
select col from test_a where id in (select id from test_b where id in (select id from t3));
```

返回结果为：

```
                                    QUERY PLAN
----------------------------------------------------------------------------------
 Streaming(type: LOCAL GATHER dop: 1/2)
   ->  Nested Loop Semi Join
         Join Filter: (test_a.id = test_b.id)
         ->  Streaming(type: LOCAL REDISTRIBUTE dop: 2/2)
               ->  Seq Scan on test_a
         ->  Materialize
               ->  Streaming(type: LOCAL REDISTRIBUTE dop: 2/2)
                     ->  Nested Loop Semi Join
                           Join Filter: (test_b.id = t3.id)
                           ->  Streaming(type: LOCAL REDISTRIBUTE dop: 2/2)
                                 ->  Seq Scan on test_b
                           ->  Materialize
                                 ->  Streaming(type: LOCAL REDISTRIBUTE dop: 2/2)
                                       ->  Seq Scan on t3
(14 rows)
```

### 并行哈希连接<a id="并行哈希连接"></a>

**功能描述**

并行哈希连接是利用多核CPU的并行处理能力，将一个生成哈希连接的执行计划由多个Worker执行。一个表连接语句规划器会生成哈希连接计划，并行哈希连接功能是执行器在执行时，将哈希连接的操作由多个Worker并行执行完成。

**注意事项**

- 不支持临时表、外表、分区表、非行存表的并行查询。
- 不支持CET查询，并行子查询等。

**示例**

1、创建两张表。

```
create table parallel_hashjoin_test_a (id int);
create table parallel_hashjoin_test_b (id int);
```

2、插入数据。

```
insert into parallel_hashjoin_test_a select n from generate_series(1,1000) n;
insert into parallel_hashjoin_test_b select n from generate_series(1,10) n;
```

3、收集两张表的统计信息。

```
analyse parallel_hashjoin_test_a;
analyse parallel_hashjoin_test_b;
```

4、打印两张表连接查询的查询计划。

```
explain (costs off) select * from parallel_hashjoin_test_a left outer join parallel_hashjoin_test_b on parallel_hashjoin_test_a.id = parallel_hashjoin_test_b.id where parallel_hashjoin_test_a.id < 10 order by parallel_hashjoin_test_a.id;
```

返回结果如下，则表示支持该功能：

```
                                   QUERY PLAN
--------------------------------------------------------------------------------
 Sort
   Sort Key: parallel_hashjoin_test_a.id
   ->  Hash Left Join
         Hash Cond: (parallel_hashjoin_test_a.id = parallel_hashjoin_test_b.id)
         ->  Seq Scan on parallel_hashjoin_test_a
               Filter: (id < 10)
         ->  Hash
               ->  Seq Scan on parallel_hashjoin_test_b
(8 rows)
```

5、执行两张表连接查询。

```
select * from parallel_hashjoin_test_a left outer join parallel_hashjoin_test_b on parallel_hashjoin_test_a.id = parallel_hashjoin_test_b.id where parallel_hashjoin_test_a.id < 10 order by parallel_hashjoin_test_a.id;
```

返回结果为:

```
 id | id
----+----
  1 |  1
  2 |  2
  3 |  3
  4 |  4
  5 |  5
  6 |  6
  7 |  7
  8 |  8
  9 |  9
(9 rows)
```

