# Oracle兼容性-查询函数返回结果集字段带参数列表

## 功能描述

该功能实现了用户在执行函数查询时，返回结果集的列名为函数名（参数），即返回结果的字段名称与查询语句中一致。

## 语法格式

无。

## 注意事项

- 使用该功能需要将参数func_colname_with_args设置为on（该参数默认为off）。
- 该功能在数据库兼容模式为Oracle和MySQL时能够使用（即创建DB时DBCOMPATIBILITY='A'或者'B'），在其他数据库兼容模式下不能使用该特性。

## 示例

1、创建并切换至兼容模式为A的数据库。

```sql
CREATE DATABASE db_oracle DBCOMPATIBILITY 'A';
\c db_oracle
```

2、设置func_colname_with_args参数。

```sql
set func_colname_with_args=on;
```

3、执行函数查询。

```sql
select to_char(123) from dual;
```

返回结果为：

```sql
 to_char(123)
------------
 123
(1 row)
```

