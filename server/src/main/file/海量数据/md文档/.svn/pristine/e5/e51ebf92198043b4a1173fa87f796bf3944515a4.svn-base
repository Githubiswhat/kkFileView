# JSON_LENGTH

## 功能描述

JSON_LENGTH函数用于获取json的长度。长度计算规则如下：

- 标量(非数组，非复合ison对象) 长度为1。
- 数组的长度是元素个数。
- 复合json对象的长度是对象成员数量。
- 计算时不计算嵌套的对象或数组长度
## 语法格式

```sql
json_length(json_doc json, [, path jsonpath] ...)
```

## 参数说明

- **json_doc**

  待计算长度的json。

- **path**

  查找的路径。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 如果path参数指定了，则计算的是由path指定的部分json的长度。
- 如果任何参数是NULL或者给出的path找不到指定json对象，则返回NULL。当json_doc或path参数不符合对应类型时，或者path中含有*或**通配符时，报错。

## 示例

1、创建并进入兼容MySQL的库db_mysql。	

```sql
CREATE DATABASE db_mysql DBCOMPATIBILITY 'B';
\c db_mysql 
```

2、调用函数。

```sql
 select json_length('[1,2,{"a":3}]');
```

结果返回如下：

```sql
 json_length
-------------
           3
(1 row)
```

