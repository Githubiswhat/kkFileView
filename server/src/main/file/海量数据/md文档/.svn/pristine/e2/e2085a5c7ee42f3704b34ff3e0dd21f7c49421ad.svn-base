# MySQL兼容性-支持反引号

## 功能描述

在MySQL兼容模式下支持使用反引号解释为标识符的功能。遇到反引号的时候，就会将反引号中的内容解释为标识符，同时关键字也会作为标识符返回。

## 注意事项

该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 示例

1、创建兼容MySQL的库db_mysql，并进入。

```sql
create database db_mysql dbcompatibility  'B';
\c db_mysql
```

2、创建测试表。

```sql
CREATE TABLE test(`all` int,`analyse` int);
```

3、查看表属性。

```sql
\d test;
```

结果返回如下：

```sql
           Table "public.test"
 Column |  Type   | Modifiers | Attalias
--------+---------+-----------+----------
 all      | integer |           | all
 analyse  | integer |           | analyse

```

