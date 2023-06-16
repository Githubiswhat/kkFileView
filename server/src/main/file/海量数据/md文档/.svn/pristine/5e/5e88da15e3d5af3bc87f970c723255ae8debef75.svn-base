# JSON_UNQUOTE

## 功能描述

JSON_UNQUOTE函数用于取消引用json值，转换为字符串输出。

## 语法格式

```sql
json_unquote(json_val)
```

## 参数说明

**json_val**

待取消引用的json。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 如果json_val为NULL则返回值也为NULL。
- 如果输入的值被双引号前后包裹但不是一个合法的json字符串值，则报错。

## 示例

1、创建并进入兼容MySQL的库db_mysql。

```sql
CREATE DATABASE db_mysql DBCOMPATIBILITY 'B';
\c db_mysql 
```

2、调用函数

```sql
select * from json_unquote('"abc"');
```

结果返回如下：

```sql
 json_unquote
--------------
 "abc"
(1 row)
```

