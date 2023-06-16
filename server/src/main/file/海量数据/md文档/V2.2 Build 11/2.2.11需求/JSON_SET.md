# JSON_SET

## 功能描述

JSON_SET函数用于替换或新增json属性值。

## 语法格式

```sql
json_set(json_doc json, path jsonpath, val [, path jsonpath,val] ....)
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>  
>
> 根据path使用val值插入或更新json_doc，并返回修改后的json_doc。

## 参数说明

- **json doc**

  被修改的json。

- **path**

  被修改值的路径。

- **val**

  插入或更新的新值。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 如果任何参数是NULL或者给出的path找不到指定的待插入或更新的json对象，则返回NULL。
- 当json_doc或path参数不符合对应类型时，或者path中含有*或**通配符时，函数执行报错。
- 该函数参数不定个数，可以传入一个json doc和多对 (path,val) 组合实际执行时，会从左到右调用(path,val) 组合，每调用一次就产生一个新的json_doc，下一个 (path,val) 应用在新的json_doc上。

- 以下两种情况，会发生插入：
  - path指定的是一个对象的属性，对象存在，属性不存在，那么就给对象加上指定属性。
  - path指定的是一个数组的末尾的下一个位置，则把值插入到该位置拓展数组；如果实际是一个对象而不是数组，则会把该对象转为数组再插入新值。

- 既不能更新又不符合插入条件，就跳过该 (path,val)继续执行。

## 示例

1、创建并进入兼容MySQL的库db_mysql。

```sql
CREATE DATABASE db_mysql DBCOMPATIBILITY 'B';
\c db_mysql 
```

2、调用函数。

```sql
select * from json_set('{"a": 1, "b": [2, 3]}', '$.a', 10, '$.c', '[true, false]');
```

结果返回如下：

```sql
                  json_set
----------------------------------------------
 {"a": 10, "b": [2, 3], "c": "[true, false]"}
(1 row)
```

