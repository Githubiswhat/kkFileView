# CREATE VIEW语法支持force选项

## 功能描述

CREATE VIEW语法创建视图时不管基表是否存在，使用force view选项都可以成功创建视图，但是只有基表存在时，视图的DML操作才可以正常执行。

## 语法格式

```sql
CREATE [ OR REPLACE ] [ TEMP | TEMPORARY ][NOFORCE | FORCE] VIEW view_name [ ( column_name [, ...] ) ]
    [ WITH ( {view_option_name [= view_option_value]} [, ... ] ) ]
    AS query;
```

## 参数说明

- **OR REPLACE**

  如果视图已存在，则重新定义。

- **TEMP | TEMPORARY**

  创建临时视图。

- **NOFORCE | FORCE**

  决定是否强制创建视图，NOFORCE表示如果基表不存在，则不能创建视图。

  默认值：NOFORCE

- **view_name**

  要创建的视图名称。可以用模式修饰。

  取值范围：字符串，符合标识符命名规范。

- **column_name**

  可选的名称列表，用作视图的字段名。如果没有给出，字段名取自查询中的字段名。

  取值范围：字符串，符合标识符命名规范。

- **view_option_name [= view_option_value]**

  该子句为视图指定一个可选的参数。

  目前view_option_name支持的参数仅有security_barrier，当VIEW试图提供行级安全时，应使用该参数。

  取值范围：boolean类型，TRUE、FALSE

- **query**

  为视图提供行和列的SELECT或VALUES语句。

## 注意事项

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。
- CREATE FORCE VIEW创建视图时，子查询仅支持单表查询，暂不支持多表连接查询。

## 示例

**示例1：**建表后创建视图。

1、创建并切换至兼容Oracle的库db_oracle。

```sql
CREATE DATABASE db_oracle dbcompatibility='A';
\c db_oracle
```

2、创建测试表并插入数据。

```sql
create table test (a int);
insert into test values(1),(3),(5);
```

3、创建普通视图。

```sql
create force view t_view as select * from test;
```

4、查看视图。

```sql
select * from t_view;
```

返回结果为：

```sql
 a
---
 1
 3
 5
(3 rows)
```

5、删除表和视图。

```sql
drop table test cascade;
```

**示例2：**建表前创建视图。

1、创建并切换至兼容Oracle的库db_oracle。

```sql
CREATE DATABASE db_oracle dbcompatibility='A';
\c db_oracle
```

2、建表直接创建视图带有force关键字。

```sql
create force view t_view as select * from test;
```

3、查询视图（由于基表不存在，返回结果报错）。

```sql
select * from t_view;
```

4、在视图上创建物化视图（由于基表不存在，返回结果报错）。

```sql
create materialized view t_mater_view as select * from t_view;
```

5、创建表test。

```sql
create table test (a int);
insert into test values(1),(3),(5);
```

6、查看视图。

```sql
select * from t_view;
```

返回结果为：

```sql
 a
---
 1
 3
 5
(3 rows)
```

7、在视图上创建物化视图。

```sql
create materialized view t_mater_view as select * from t_view;
```

8、查看物化视图。

```sql
select * from t_mater_view;
```

返回结果为：

```sql
 a
---
 1
 3
 5
(3 rows)
```

