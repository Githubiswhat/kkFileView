# CHANGE COLUMN

## 功能描述

Vastbase G100在MySQL兼容模式下支持使用CHANGE COLUMN语法修改表列的名字和类型。

## 语法格式

```sql
ALTER TABLE table_name change col1 col2 datatype;
```

## 参数说明

- **table_name**

  表名。

- **col1**

  被修改的表列名。
  
- **col2**

  修改之后的表列名，col1和col2可以相同也可以不同。

- **datatype**

  修改后的表列的数据类型。

## 注意事项

该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建兼容模式为MySQL的库db_mysql，并进入。

```sql
create  database  db_mysql  dbcompatibility  'B';
\c  db_mysql
```

1、创建测试表。

```sql
create table t (col1 int);
```

2、修改表的列名和数据类型。

```sql
alter table t change col1 col2 varchar(100);
```

3、查询修改后的表结构。

```sql
\d t
```

返回结果如下，表示修改表列成功：

```sql
               Table "public.t"
 Column |     Type     | Modifiers | Attalias
--------+--------------+-----------+----------
 col2   | varchar(100) |           | col1
```







