# COUNT_BIG

## 功能描述

Vastbase G100支持COUNT_BIG函数，用于返回表中的非空记录条数，返回值为bigint类型。

## 语法格式

```sql
COUNT_BIG(columnRef)
```

## 参数说明

**columnRef**

列名。

> <div align="left"><img src="image/img1.png" style="zoom:75%")</div>
>
> 可以使用\*或者数据列名作为参数。\*作为参数会返回所有列的数目，列名作为参数则返回该列非空的数目。

## 注意事项

无。

## 示例

1、创建测试表tb1。

```sql
create table tb1(col_int2 int2 primary key,
col_int int,
col_integer integer(5,2),
col_int4 int4,
col_int8 int8
);
```

2、插入测试数据。

```sql
insert into tb1 values(1,10,15.34,17,25);
insert into tb1 values(2,20,null,17,49);
insert into tb1 values(3,40,148.66,17,3);
insert into tb1 values(4,10,57.3,null,1196);
insert into tb1 values(5,10,9,null,2);
```

3、调用count_big函数查询表中行数。

```sql
select count_big(tb1) from tb1;
```

结果返回如下：

```sql
 count_big
-----------
         5
(1 row)
```

4、调用count_big函数查询某列行数。

```sql
select count_big(col_int2) a,count_big(col_int) b1,count_big(distinct col_int) b2,count_big(col_integer) c1,count_big(all col_integer) c2,count_big(col_int4) d1,count_big(all col_int4) d2,count_big(distinct col_int4) d3,count_big(col_int8) e1,count_big(all col_int8) e1,count_big(distinct col_int8) e2 from tb1;
```

结果返回如下：

```sql
 a | b1 | b2 | c1 | c2 | d1 | d2 | d3 | e1 | e1 | e2
---+----+----+----+----+----+----+----+----+----+----
 5 |  5 |  3 |  4 |  4 |  3 |  3 |  1 |  5 |  5 |  5
(1 row)
```



