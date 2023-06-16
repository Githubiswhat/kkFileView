# CREATE VIEW 创建只读视图

## 功能描述

Vastbase G100在Oracle兼容模式下，支持在创建视图时使用with read only选项标记为只读视图：视图仅支持查询操作，不允许修改原表。

> <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
>
> - 禁止通过视图插入、更新、删除原表。
> - 插入、更新、删除原表数据时，视图内容同步更新。

## 语法格式

```sql
CREATE [ OR REPLACE ] [DEFINER = user] [ TEMP | TEMPORARY ] [ FORCE | NOFORCE ] VIEW view_name [ ( column_name [, ...] ) ]
    [ WITH ( {view_option_name [= view_option_value]} [, ... ] ) ]
    AS query [ WITH READ ONLY ];
```

## 参数说明

- **OR REPLACE**

    当CREATE VIEW语句中存在OR REPLACE时，表示若以前存在该视图就进行替换，但新查询不能改变原查询的列定义，包括顺序、列名、数据类型、类型精度等，只可在列表末尾添加其他的列。

- **DEFINER = user**

    指定user作为视图的属主。

- **TEMP | TEMPORARY**

    创建临时视图。

- **FORCE | NOFORCE**

    强制创建视图。正常情况下，如果基表不存在，创建视图就会失败。指定force选项可以强制创建视图。

    如果仅在存在基表且包含视图的模式的所有者对基表具有特权的情况下才希望创建视图，则指定NOFORCE。NOFORCE是默认值。

- **view_name**

    要创建的视图名称。可以用模式修饰。

    取值范围：字符串，符合标识符命名规范。

- **column_name**

    可选的名称列表，用作视图的字段名。如果没有给出，字段名取自查询中的字段名。

    取值范围：字符串，符合标识符命名规范。

- **view_option_name [= view_option_value]**

    该子句为视图指定一个可选的参数。

    目前view_option_name支持的参数仅有security_barrier，当VIEW试图提供行级安全时，应使用该参数。

    取值范围：Boolean类型，TRUE、FALSE

- **query**

    为视图提供行和列的SELECT或VALUES语句。

- **with read only**

    指定此选项将被标记为只读视图。

## 注意事项

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。
- 物化视图不支持with read only选项。
- 只读视图同步更新对原表数据的修改，不包括原表的表结构变更。

## 示例

**前置条件：**创建并切换至兼容模式为Oracle的数据库oracle_db下。

```sql
create database oracle_db dbcompatibility 'A';
\c oracle_db;
```

**示例1：**创建只读视图，无法修改原表。

1、创建测试表并插入数据。

```sql
create table test(id int,name text);
insert into test values(1,'aaa'),(2,'bbb'),(3,null),(null,'bbb');
```

2、创建只读视图。

```sql
create view v1 as select * from test with read only;
```

3、查看该视图。

```
select * from v1;
```

返回结果为：

```sql
 id | name
----+------
  1 | aaa
  2 | bbb
  3 |
    | bbb
(4 rows)
```

4、更新视图。

```sql
update v1 set name='ccc' where id=3;
```

更新失败，无法通过只读视图更新原表。

```sql
ERROR:  cannot update view "v1"
DETAIL:  Views with read only are not automatically updatable.
```

**示例2：**创建只读视图，更新原表时，视图内容同步更新。

1、创建测试表并插入数据。

```sql
create table class(id int,name text);
insert into class values(1,'aaa'),(2,'bbb'),(3,null),(null,'bbb');
```

2、创建只读视图。

```sql
create view v2 as select * from class with read only;
```

3、查看视图内容。

```sql
select * from v2;
```

返回结果为：

```sql
 id | name
----+------
  1 | aaa
  2 | bbb
  3 |
    | bbb
(4 rows)
```

4、为原表添加一个字段。

```sql
alter table class add column c1 text;
```

5、插入一条测试数据。

```sql
insert into class values(4,'tom','ttt');
```

6、再次查看视图。

```sql
select * from v2;
```

返回结果如下：视图已更新，可以看到新增的一行数据，但不会显示新增的字段。

```sql
 id | name
----+------
  1 | aaa
  2 | bbb
  3 |
    | bbb
  4 | tom
(5 rows)
```

