#### FIND_IN_SET()函数

**功能描述**

find_in_set(str,strlist)函数可以查询参数1（str）在参数2（strlist）根据'，'分隔列表中第一次出现的位置。

**语法格式**

```
FIND_IN_SET(str,strlist)
```

**参数说明**

- str：要查询的字符串。

- strlist：字段名参数以“,”分隔如(1,2,6,8,10,22)。

- 查询字段（strlist）中包含（str）的结果，返回结果为null或记录。

**注意事项**

该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。



**示例**

1、创建兼容MySQL的库db_mysql，并进入。

```
CREATE DATABASE db_mysql DBCOMPATIBILITY='B';
\c db_mysql
```

2、执行函数验证。

```
select find_in_set('','1,,2');
```

结果返回如下：

```
 find_in_set 
-------------
           2
(1 row)
```

#### 