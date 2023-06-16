# ROWID数据类型

## 功能描述

Vastbase数据库的表中的每一行数据都有一个唯一的标识符，或者称为rowid，rowid是一个伪列在Vastbase内部通常就是使用它来访问数据。该值表明了该行在数据库中的物理具体位置。可以在一个查询中使用rowid来表明查询结果中包含该值。

ROWID可以分为物理rowid和逻辑rowid两种。普通的表中的rowid是物理rowid，索引组织表(IOT)的rowid是逻辑rowid。

## 示例

1、创建测试表ridtest。

```
CREATE TABLE ridtest (id int,name varchar(10));
```

2、插入测试数据。

```
INSERT INTO ridtest values(1,'zhangsan');
```

3、查询rowid。

```
SELECT  rowid,id FROM ridtest;
```

返回结果如下：

```
       rowid         | id
----------------------+----
 BWEAAA==AAAAAA==AQA= |  1
(1 row)
```

