# JSON_EXTRACT

## 功能描述	

JSON_EXTRACT用于提取指定key的值。

## 语法格式

```sql
json_extract(json_doc json, path jsonpath, [, path jsonpath] ....)
```

## 参数说明

- **json_doc**

  被查找数据的json。

- **path**

  查找的路径。

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>  
>
> 根据path查找json_doc，并返回找到的数据。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 如果任何参数是NULL或者给出的所有path都找不到指定json对象，则返回NULL。
- 当json_doc或path参数不符合对应类型时报错。
- 该函数参数不定个数，可以传入多个path。当找到多个返回值时，返回值会自动转为数组返回。数组元素顺序就是对应path的顺序。

## 示例

1、创建并进入兼容MySQL的库db_mysql。

```sql
CREATE DATABASE db_mysql DBCOMPATIBILITY 'B';
\c db_mysql 
```

2、调用函数。

```sql
select * from json_extract('[10,20,[30,40]]','$[1]');
```

结果返回如下：

```sql
 json_extract
--------------
 20
(1 row)
```

