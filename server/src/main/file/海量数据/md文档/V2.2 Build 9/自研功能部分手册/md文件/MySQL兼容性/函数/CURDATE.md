#### CURDATE函数

**功能描述**

curdate函数用于获取当前时间，返回年月日。

**语法格式**

```
select curdate()
```

**注意事项**

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

- 输出类型：DATE

- 输出格式：'YYYY-MM-DD'

**兼容性**

完全兼容。

**示例**

1、创建兼容MySQL的库db_mysql，并进入。

```
CREATE DATABASE db_mysql DBCOMPATIBILITY='B';
\c db_mysql
```

2、执行函数验证。

```
select curdate();
```

结果返回如下：

```
  curdate   
------------
 2022-07-25
(1 row)

```

#### 