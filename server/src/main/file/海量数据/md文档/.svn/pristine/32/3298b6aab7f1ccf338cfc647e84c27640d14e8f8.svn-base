# JSON_ARRAY

## 功能描述

JSON_ARRAY函数用于将入参转换为JSON数组。

## 语法格式

```sql
json_array([val [, val] ....])
```

## 参数说明

**val**

输入参数。入参不定个数，每个参数都会成为输出数组的一个元素。

## 注意事项

该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 示例

1、创建并进入兼容MySQL的库db_mysql。

```sql
CREATE DATABASE db_mysql DBCOMPATIBILITY 'B';
\c db_mysql 
```

2、调用函数。

```sql
SELECT JSON_ARRAY(1,'abc',NULL,TRUE,current_time);
```

结果返回如下：

```sql
                  json_array
----------------------------------------------
 [1, "abc", null, true, "14:57:46.561923+08"]
(1 row)
```

