# JSON_CONTAINS

## 功能描述

JSON_CONTAINS函数用于判断属性值是否存在。

根据target json是否包含candidate json返回1或0，返回1时表示属性值存在，返回0时表示不存在。

## 语法格式

```sql
json_contains(target json, candidate json [, path jsonpath])
```

## 参数说明

- **target** 

  被查找的json。

- **candidate**

  查找的json。

- **path**

  负责指定target中特定的查找路径，默认为空。

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>  
>
> 当path参数给出时，则要求target json中由path指定路径来判断是否包含，而不是整个target。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

- 如果任何参数是NULL或者path没有指向target json的任何一部分，则返回NULL。
- 当json_doc或path参数不符合对应类型时，或者path中含有*或**通配符时，函数执行报错。
- 标量json（非数组json元素，非复合json对象）当且仅当它们是可以比较且比较结果为相等时是包含的；只有相同JSON TYPE的标量可以比较 （INTERGER和DECIMAL例外，它们可以互相比较）。
- 一个candidate json数组被包含在target json数组是指：当且仅当每一个candidate元素被包含在target的某些元素中。
- 一个candidate非json数组 被包含在target json数组是指：当且仅当该candidate被包含在target数组的某些元素中。
- 一个candidate对象被包含在一个target对象是指：当且仅当对于candidate每个键，在target中都有相同名字的键，并且两个键所对应的值满足candidate值被包含在target值中。

## 示例

1、创建并进入兼容MySQL的库db_mysql。

```sql
CREATE DATABASE db_mysql DBCOMPATIBILITY 'B';
\c db_mysql 
```

2、调用函数。

```sql
select * from json_contains('{"a":1,"b":2,"c":{"d":4}}','1','$.a');
```

结果返回如下：

```sql
 json_contains
---------------
             1
(1 row)
```

