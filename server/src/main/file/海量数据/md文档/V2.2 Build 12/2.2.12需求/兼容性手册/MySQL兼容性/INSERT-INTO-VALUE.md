# INSERT INTO...VALUE...

## 功能描述

Vastbase G100在MySQL兼容模式下支持INSERT INTO语法使用value插入数据。

## 语法格式

```sql
INSERT INTO table_name VALUE (expression)[,...];
```

## 参数说明

- **table_name**

  要插入数据的目标表名。

- **expression**

  要插入目标表的有效表达式或值。

## 注意事项

该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建兼容模式为MySQL的库db_mysql，并进入。

```
create  database  db_mysql  dbcompatibility  'B';
\c  db_mysql
```

1、创建测试表。

```sql
create table tb_1117689(col1 int ,col2 int default 3);
```

2、插入数据。

```sql
insert into tb_1117689 value(1,1),(2,2);
```

3、查询数据。

```sql
select * from tb_1117689 order by 1,2;
```

返回结果为：

```sql
 col1 | col2
------+------
    1 |    1
    2 |    2
(2 rows)
```

