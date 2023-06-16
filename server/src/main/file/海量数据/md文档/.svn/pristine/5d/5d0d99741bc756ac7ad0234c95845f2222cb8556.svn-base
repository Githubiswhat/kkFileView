### TABLE

**功能描述**

table表函数用于查询函数返回的结果集，并将结果集转换为表的形式（就如同查询普通表一样查询返回的结果集）。

**语法格式**

```
SELECT * FROM table(arg);
```

**参数说明**

arg：结果集。



**示例**

1、创建测试表并插入数据。

```
create table testtable(id int);
insert into testtable values(123),(1234);
```

2、使用表函数查询结果。

```
SELECT * FROM table(testtable);
```

返回结果为：

```
id
------
  123
 1234
(2 rows)
```