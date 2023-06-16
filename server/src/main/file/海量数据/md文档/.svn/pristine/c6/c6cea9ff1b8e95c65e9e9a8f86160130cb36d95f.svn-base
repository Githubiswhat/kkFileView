# MySQL兼容性-rownum(offset)

## 功能描述

在MySQL兼容模式下，Vastbase G100支持用户自定义rownum变量用来识别获取行号。

其实现逻辑为通过赋值语句实现变量值的累加，从而达到递增行号的目的。

## 语法格式

```sql
@rownum 为会话自定义变量
@rownum := 0 确定初始值
@rownum := @rownum +1 变量自增
```

## 参数说明

**rownum**：变量名/变量别名。用户也可以自定义其名称。

## 注意事项

- rownum暂不能充当其它语法成分，仅作为变量/变量别名使用。因此用户也可自定义任意变量名实现此用法。
- 用户自定义变量@xxx不支持在以下情况中使用：
  - 聚合查询中，包括select子句和group by子句中；
  - order by子句中；
  - UNION/INTERSECT/EXCEPTION等复合查询中；

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 仅Vastbase G100 2.2 Build 11及以后版本支持此功能。

## 示例

1、创建并切换至兼容模式为B的数据库下。

```sql
create database testdb_1100915 dbcompatibility 'B';
\c testdb_1100915;
```

2、创建测试表并插入数据。

```sql
CREATE TABLE tb_1101383 (
depname varchar,
empno bigint,
salary int,
enroll_date date
);

INSERT INTO tb_1101383 VALUES
('develop', 10, 5200, '2007-08-01'),
('sales', 1, 5000, '2006-10-01'),
('personnel', 5, 3500, '2007-12-10'),
('sales', 4, 4800, '2007-08-08'),
('personnel', 2, 3900, '2006-12-23'),
('develop', 7, 4200, '2008-01-01');
```

3、自定义用户变量，识别获取行号。

- 自定义变量rownum，令其自增得到行号，并打印结果。

```sql
select @rownum:=@rownum+1 as rownum,b.* from (select @rownum:=0)r,tb_1101383 b;
```

返回结果为：

```sql
 rownum |  depname  | empno | salary | enroll_date
--------+-----------+-------+--------+-------------
      1 | develop   |    10 |   5200 | 2007-08-01
      2 | sales     |     1 |   5000 | 2006-10-01
      3 | personnel |     5 |   3500 | 2007-12-10
      4 | sales     |     4 |   4800 | 2007-08-08
      5 | personnel |     2 |   3900 | 2006-12-23
      6 | develop   |     7 |   4200 | 2008-01-01
(6 rows)
```

- 自定义变量a1，令其自增得到行号，并打印结果。


```sql
select @a1:=@a1+1 as rownum,b.* from (select @a1:=0)r,tb_1101383 b;
```

返回结果为：

```sql
 rownum |  depname  | empno | salary | enroll_date
--------+-----------+-------+--------+-------------
      1 | develop   |    10 |   5200 | 2007-08-01
      2 | sales     |     1 |   5000 | 2006-10-01
      3 | personnel |     5 |   3500 | 2007-12-10
      4 | sales     |     4 |   4800 | 2007-08-08
      5 | personnel |     2 |   3900 | 2006-12-23
      6 | develop   |     7 |   4200 | 2008-01-01
(6 rows)
```

- 自定义变量rownum，令其自增得到行号。根据enroll_date字段进行分组查询，并打印行号小于3的结果（降序排列）。


```sql
select b.enroll_date,b.rownum from (select (@rownum:=@rownum+1) AS rownum,td.* from tb_1101383 td,(select @rownum:=0)r where td.enroll_date<'2008-01-01' and td.depname='sales' order by td.enroll_date desc)b where b.rownum<3;
```

返回结果为：

```sql
 enroll_date | rownum
-------------+--------
 2007-08-08  |      2
 2006-10-01  |      1
(2 rows)
```