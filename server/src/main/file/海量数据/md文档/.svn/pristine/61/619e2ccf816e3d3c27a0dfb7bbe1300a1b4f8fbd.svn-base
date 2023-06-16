# CURRENT_DATE

## 功能描述

Vastbase在MySQL兼容模式下支持使用CURRENT_DATE函数获取当前日期（不包含时分秒）。

## 语法格式

```sql
CURRENT_DATE()
```

## 参数说明

无。

## 注意事项

该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建兼容模式为MySQL的库db_mysql，并进入。

```
create  database  db_mysql  dbcompatibility  'B';
\c  db_mysql
```

**示例1：**直接调用CURRENT_DATE函数。

```sql
select CURRENT_DATE;
```

返回结果为：

```sql
    date
------------
 2023-02-07
(1 row)
```

**示例2：**在DML语句中使用CURRENT_DATE函数。

1、创建测试表并插入数据。

```sql
create table test(id int,col date default current_date());
insert into test values(1,'2022-01-07');
```

2、向test表中插入一条数据。

```sql
insert into test values(2,current_date());
```

3、更新id为1的那条数据。

```sql
update test set col=current_date()-30 where id=1;
```

4、查询数据（假设当前日期为2023-02-07）。

```sql
select * from test order by id;
```

返回结果为：

```sql
 id |    col
----+------------
  1 | 2023-01-08
  2 | 2023-02-07
(2 rows)
```
