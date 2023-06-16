# REPLACE

## 功能描述

replace是MySQL的一个扩展语法，它的工作原理与insert几乎相同，不同点在于表中存在唯一性约束时，replace into先删除冲突行，再插入新行。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

- REPLACE的工作方式与INSERT完全相同，但如果表的值与PRIMARY KEY或UNIQUE的新行相同

  索引，则在插入新行之前删除旧行。REPLACE是SQL标准的MySQL扩展。它要么插入，要么删除和插入。对于标准SQL的另一个MySQL扩展插入或更新。

- 除非表具有PRIMARY KEY或UNIQUE索引，否则使用REPLACE语句没有意义。它变得等同于INSERT，因为没有索引可用于确定新行是否复制另一个。
- 要使用REPLACE，必须同时具有对表的INSERT和DELETE权限。

## 语法格式

- 格式1：

```
REPLACE [LOW_PRIORITY | DELAYED]
    [INTO] tbl_name [(col_name,...)]
    {VALUES | VALUE} ({expr},...),(...),...
```

- 格式2：


```
REPLACE [LOW_PRIORITY | DELAYED]
    [INTO] tbl_name
    SET col_name={expr}, ...
```

- 格式3：


```
REPLACE [LOW_PRIORITY | DELAYED]
    [INTO] tbl_name [(col_name,...)]
    SELECT ...
```

## 参数说明

- **LOW_PRIORITY|DELAYED**

  replace的两种模式。

  取值范围：低优先级|延迟

- **tbl_name**

  表名称。

- **col_name**

  列名称。

## 示例

1、创建兼容MySQL的库db_mysql，并进入。

```
CREATE DATABASE db_mysql DBCOMPATIBILITY='B';
\c db_mysql
```

2、创建一张表，并指定主键。

```
CREATE TABLE test (
id int,
id2 int,
name varchar(50) DEFAULT NULL,
PRIMARY KEY(id)
);
```

3、插入测试数据，并查询结果。

```
replace test  values(1,1000,'test1');
replace test  values(2,2000,'test2');
replace test  values(3,3000,'test3');
replace test  values(4,4000,'test4');
select * from test;
```

结果显示为：

```
----+------+-------
  1 | 1000 | test1
  2 | 2000 | test2
  3 | 3000 | test3
  4 | 4000 | test4
(4 rows)
```

4、执行replace 操作的数据，会与主键产生冲突。

```
replace test  values(1,4000,'test5');
```

5、查询ID为1数据是否被替换。

```
select * from test;
```

当结果显示如下信息，则表示替换成功：

```
 id | id2  | name  
----+------+-------
  2 | 2000 | test2
  3 | 3000 | test3
  4 | 4000 | test4
  1 | 4000 | test5
(4 rows)
```

## 相关链接

无