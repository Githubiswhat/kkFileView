# SMALLMONEY类型

## 功能描述

Vastbase G100在SQL Server兼容模式下支持smallmoney类型。smallmoney类型是可以代表货币或货币值的数据类型，其支持范围从-92233720368547758.08 到 +92233720368547758.07。

## 注意事项

- 本特性仅在数据库兼容模式为SQL Server时能够使用（即创建DB时DBCOMPATIBILITY='MSSQL'），在其他数据库兼容模式下不能使用该特性。
- smallmoney相关类型转换说明：
  - 支持int、bigint、numeric类型隐式转换为smallmoney类型。
  - 支持real、float、double precison、varchar、text类型转化为numeric后，再转化为smallmoney类型。
  - date、timestamp、blob等类型无法隐式转化为smallmoney类型。

- 插入或更新smallmoney类型的字段值为空、null时，其在表中被记录为$0.00。

## 示例

1、创建兼容SQL Server的库db_sqlserver，并进入。

```sql
create database db_sqlserver dbcompatibility 'MSSQL';
\c db_sqlserver
```

2、创建含有smallmoney类型的表。

```sql
create table tb_smallmoney(
id int, 
name varchar,
salary smallmoney);
```

3、向表中插入数据，插入后查询表记录。

```sql
insert into  tb_smallmoney values(1, 'xiaoming', 5000.8334);
insert into  tb_smallmoney values(2, 'xiaolan', 123457123.6771);
insert into  tb_smallmoney values(3, 'xiaohong', 789754.9011);
insert into  tb_smallmoney values(4, 'xiaoming', 983.8954);
select * from tb_smallmoney;
```

查询表记录结果如下：

```sql
 id |   name   |     salary
----+----------+-----------------
  1 | xiaoming |       $5,000.83
  2 | xiaolan  | $123,457,123.68
  3 | xiaohong |     $789,754.90
  4 | xiaoming |         $983.90
(4 rows)
```

4、更新表中一条记录，更新后查询表记录。

```sql
update tb_smallmoney set salary = 7890.5645 where id = 3;
select * from tb_smallmoney;
```

查询表记录结果如下：

```sql
 id |   name   |     salary
----+----------+-----------------
  1 | xiaoming |       $5,000.83
  2 | xiaolan  | $123,457,123.68
  4 | xiaoming |         $983.90
  3 | xiaohong |       $7,890.56
(4 rows)
```

5、删除表中一条记录，删除后查询表记录。

```sql
delete from tb_smallmoney where salary = 983.8954::smallmoney;
select * from tb_smallmoney;
```

查询表记录结果如下：

```sql
 id |   name   |     salary
----+----------+-----------------
  1 | xiaoming |       $5,000.83
  2 | xiaolan  | $123,457,123.68
  3 | xiaohong |       $7,890.56
(3 rows)
```

6、向表中插入一条第三个字段为numeric类型的记录，其可以被隐式转换为smallmoney类型。

```sql
insert into tb_smallmoney values(4,'xiaoli',233.669925::numeric);
select * from tb_smallmoney;
```

查询表结果为如下，第三个字段的类型被隐式转换为了smallmoney：

```sql
 id |   name   |     salary
----+----------+-----------------
  1 | xiaoming |       $5,000.83
  2 | xiaolan  | $123,457,123.68
  3 | xiaohong |       $7,890.56
  4 | xiaoli   |         $233.67
(4 rows)
```
