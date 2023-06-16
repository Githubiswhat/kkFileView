# JSON_REPLACE

## 功能描述

JSON_REPLACE函数用于替换JSON属性值，返回修改后的JSON值。

## 语法格式

```sql
json_replace(json_doc json, path jsonpath, val [, path jsonpath, val] ...)
```

## 参数说明

- **json_doc**

  被修改的json。

- **path**

  被修改值的路径

- **val**

  更新的新值。

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>  
>
> 根据path使用val值更新json_doc中已存在的值，并返回修改后的json_doc。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 如果任何参数是NULL则返回NULL。当json_doc或path参数不符合对应类型时，或者path中含有*或**通配符时，函数执行报错。
- 该函数参数不定个数，可以传入一个json_doc和多对 (path, val) 组合实际执行时，会从左到右应用 (path, val) 组合，每应用一次就产生一个新的json_doc，下一个 (path, val) 应用在新的json_doc上。
- 如果一个 (path, val) 无法根据path找到要更新的位置，则被跳过。

## 示例

1、创建并进入兼容MySQL的库db_mysql。

```sql
CREATE DATABASE db_mysql DBCOMPATIBILITY 'B';
\c db_mysql 
```

2、调用函数。

```sql
select * from json_replace('{"a": 1, "b": [2, 3]}', '$.a', 10, '$.c', '[true, false]');
```

结果返回如下：

```sql
      json_replace
------------------------
 {"a": 10, "b": [2, 3]}
(1 row)
```

