（跟研发确认过无兼容性限制）

# 内置跨类型操作符

## 功能描述

当基于表中字段类型bpchar创建btree索引时，由于存在跨bpchar/text类型操作符，bpchar op text形式的过滤条件可以用于索引匹配。

## 注意事项

- 跨类型操作符只对bpchar op text或text op bpchar形式的过滤条件支持匹配索引。

- 由于路径代价原因，explain时不一定能在执行计划中看到索引扫描。如果一定要观测索引扫描，则需要执行如下设置：

  ```sql
  set enable_seqscan to off;
  set enable_bitmapscan to off;
  ```

## 示例

1、创建测试表且主表中包含字段类型为bpchar的列,并插入数据。

```sql
create table test(c1 int,c2 bpchar(60));
insert into test values(generate_series(1,10),current_date);
```

2、基于表中字段类型bpchar创建btree索引。

```sql
create index idx_c2 on test using btree(c2);
```

3、收集与数据库中表内容相关的统计信息。

```sql
analyze test;
```

4、查询表中信息。

```sql
select count(*) from test where c1 in ('1','2','3') ; 
```

结果显示为：

```sql
 count
 --------
   3
(1 row)
```

5、查询执行计划。

```sql
explain select count(*) from test where c1 in ('1','2','3') ;
```

结果显示为：

```sql
                     QUERY PLAN
----------------------------------------------------------
 Aggregate  (cost=1.15..1.16 rows=1 width=8)
   ->  Seq Scan on test  (cost=0.00..1.14 rows=3 width=0)
         Filter: (c1 = ANY ('{1,2,3}'::integer[]))
(3 rows)
```

> <div align="left"><img src="image/image1.png" style="zoom:25%"></div>
>
> 当设置enable_seqscan、enable_bitmapscan参数值为off后，可以观测索引扫描，结果显示为：
>
> ```sql
>                                    QUERY PLAN
> --------------------------------------------------------------------------------
>  Aggregate  (cost=1000000000113.76..1000000000113.77 rows=1 width=8)
>    ->  Seq Scan on test  (cost=10000000000.00..1000000000113.75 rows=3 width=0)
>          Filter: (c1 = ANY ('{1,2,3}'::integer[]))
> (3 rows)
> ```