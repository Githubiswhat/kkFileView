#### 兼容interval分区表

**功能描述**

interval分区是range分区的扩展。

在没有自动interval分区特性之前，在创建范围Range类型分区表时，我们通常会定义一个maxvalue分区，将没有落在当前关注范围内的数据，都放到该分区中，以避免插入的元组的分区键值不能映射到任何分区的错误。然而当业务场景发生变化时，如果没有及时根据数据增长创建新的分区，就可能会导致分区表的数据发生倾斜，大多数数据都放到了这个未进行细分的maxvlaue分区中，这就违反了我们对表进行分区的初衷，我们希望各个分区的数据量均衡，这样才能加快查询。
自动interval分区可以改善这个问题，对于连续数据类型的Range分区，如果插入的新数据值与当前分区均不匹配，它可以自动创建分区。

**语法格式**

```
CREATE TABLE table_name
(
  ...
)
PARTITION BY RANGE(column1)
INTERVAL (expr) 
(
  PARTITION partition_name1 VALUES LESS THAN(literal | MAXVALUE) [TABLESPACE tablespace1],
  PARTITION partition_name2 VALUES LESS THAN(literal | MAXVALUE) [TABLESPACE tablespace2]
);
```

**参数说明**

- PARTITION BY RANGE(column1)：指定一个range分区的列。
- INTERVAL：指定分区间隔。
- expr：字符串类型，指定时间间隔值。

**注意事项**

无。

**示例**

1、创建interval分区表。

```
CREATE TABLE interval_normal_exchange (logdate date not null)
PARTITION BY RANGE(logdate)
INTERVAL('1 month')
(
        PARTITION interval_normal_exchange_p1 VALUES LESS THAN('2020-03-01'),
        PARTITION interval_normal_exchange_p2 VALUES LESS THAN('2020-04-01'),
        PARTITION interval_normal_exchange_p3 VALUES LESS THAN('2020-05-01')
);
```

2、执行以下语句查询分区情况。

```
SELECT relname,parttype,partstrategy,boundaries FROM pg_partition 
WHERE parentid = (SELECT oid FROM pg_class WHERE relname = 'interval_normal_exchange')
ORDER BY relname;
```

返回的分区情况如下:

```
           relname           | parttype | partstrategy |  boundaries
-----------------------------+----------+--------------+--------------
 interval_normal_exchange    | r        | i            |
 interval_normal_exchange_p1 | p        | r            | {2020-03-01}
 interval_normal_exchange_p2 | p        | r            | {2020-04-01}
 interval_normal_exchange_p3 | p        | r            | {2020-05-01}
(4 rows)
```
