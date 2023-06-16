# 支持interval分区表

## **功能描述**

interval分区是range分区的扩展。

在没有自动interval分区特性之前，在创建范围Range类型分区表时，我们通常会定义一个maxvalue分区，将没有落在当前关注范围内的数据，都放到该分区中，以避免插入的元组的分区键值不能映射到任何分区的错误。

然而当业务场景发生变化时，如果没有及时根据数据增长创建新的分区，就可能会导致分区表的数据发生倾斜，大多数数据都放到了这个未进行细分的maxvlaue分区中，这就违反了对表进行分区的初衷，为了加快查询，则需要各个分区的数据量均衡。

自动interval分区可以改善这个问题，对于连续数据类型的Range分区，如果插入的新数据值与当前分区均不匹配，则可以自动创建分区。

## **语法格式**

```sql
CREATE TABLE table_name
(
  ...
)
PARTITION BY RANGE(column1)
INTERVAL (interval_expr) [STORE IN(tablespace_name[,...])]
(
  PARTITION partition_name1 VALUES LESS THAN(literal | MAXVALUE) [TABLESPACE tablespace1],
  PARTITION partition_name2 START(num1) END(num2)
);
```

## **参数说明**

- **PARTITION BY RANGE(column1)**

  指定一个range分区的列。

- **INTERVAL**

  指定分区间隔。

- **interval_expr**

  字符串类型或number类型，表示指定间隔值。

  interval_expr值不可为空或NULL，当为number类型时不可为0、负数，可以为浮点值。

- **[STORE IN(tablespace_name[,...])]**

  指定存放自动创建分区的表空间列表。如果有指定则自动创建的分区从表空间列表中循环选择使用，否则使用分区表默认的表空间。

- **partition_name2**

  分区名称，需符合命名规范。

- **START(num1) END(num2)**

  当使用number类型作为分区键时，分区范围可以使用start(num1) end(num2)指定起始值，其中num1为初始值，end为结束值。

## **注意事项**

无

## **示例**

**示例1** 使用字符串类型作为分区键创建interval分区

1、创建interval分区表。

```sql
CREATE TABLE test1 (logdate date not null)
PARTITION BY RANGE(logdate)
INTERVAL('1 month')
(
        PARTITION test1_p1 VALUES LESS THAN('2020-03-01'),
        PARTITION test1_p2 VALUES LESS THAN('2020-04-01'),
        PARTITION test1_p3 VALUES LESS THAN('2020-05-01')
);
```

2、执行以下语句查询分区情况。

```sql
SELECT relname,parttype,partstrategy,boundaries FROM pg_partition 
WHERE parentid = (SELECT oid FROM pg_class WHERE relname = 'test1')
ORDER BY relname;
```

返回的分区情况如下:

```sql
  relname | parttype | partstrategy |  boundaries
----------+----------+--------------+---------------
 test1    | r        | i            |
 test1_p1 | p        | r            | {2020-03-01}
 test1_p2 | p        | r            | {2020-04-01}
 test1_p3 | p        | r            | {2020-05-01}
(4 rows)
```

**示例2** 使用number类型作为分区间创建interval分区

1、创建interval分区表。

```sql
create table test2 
( 
        id number not null, 
        name nvarchar2(16) 
) 
partition by range(id) 
interval(5000) 
( 
        partition p1 values less than(5000), 
        partition p2 values less than(10000), 
        partition p3 values less than(15000)
); 
```

2、向表中插入数据，并查看表内容。

```sql
insert into test2 values(500,'test1'); 
insert into test2 values(9500,'test2'); 
insert into test2 values(13500,'test3');
insert into test2 values(18000,'test4');
insert into test2 values(30000,'test5');
insert into test2 values(25000,'test6'); 
insert into test2 values(19000,'test7');   
select * from test2 order by id;
```

结果显示为：

```sql
  id   | name
-------+-------
   500 | test1
  9500 | test2
 13500 | test3
 18000 | test4
 19000 | test7
 25000 | test6
 30000 | test5
(7 rows)
```

3、查询分区情况。

```sql
SELECT relname,parttype,partstrategy,boundaries FROM pg_partition 
WHERE parentid = (SELECT oid FROM pg_class WHERE relname = 'test2')
ORDER BY relname;
```

结果显示为：

```sql
 relname | parttype | partstrategy | boundaries
---------+----------+--------------+------------
 p1      | p        | r            | {5000}
 p2      | p        | r            | {10000}
 p3      | p        | r            | {15000}
 sys_p1  | p        | i            | {20000}
 sys_p2  | p        | i            | {35000}
 sys_p3  | p        | i            | {30000}
 test2   | r        | i            |
(7 rows)
```

4、查询分区sys_p2的内容。

```sql
select * from test2 partition(sys_p1);
```

结果显示为：

```sql
  id   | name
-------+-------
 18000 | test4
 19000 | test7
(2 rows)
```

