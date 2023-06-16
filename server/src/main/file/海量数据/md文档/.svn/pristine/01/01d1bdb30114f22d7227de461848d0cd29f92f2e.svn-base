# 字符串连接符+null

**功能描述**

字符串连接符+null的执行结果对标PG执行结果。

**语法格式**

```
select type '123'||'null';
```

**参数说明**

- type ：数据类型，指定后接内容的数据类型，如int、number等。

- '123'  ：由type指定类型的内容，由用户需求和数据类型决定内容。


**注意事项**

type指定内容和null必须由单引号' '包围，否则执行结果为空或出错。错误示范如：select int '123'||null; 或 select int 123||'null';

**示例**

**示例1** ：PG兼容模式下查看执行结果。

1、创建数据库，确定兼容性：

```
CREATE DATABASE db1 DBCOMPATIBILITY='PG';
\c db1
```

2、执行命令查看int数据类型+null结合的执行结果。

```
select int '123'||'null';
```

执行结果：

```
 ?column?
----------
 123null
(1 row)
```

**示例2** ：其他兼容模式下查看执行结果。

1、创建数据库，确定兼容性。

```
CREATE DATABASE db1 DBCOMPATIBILITY='A';
\c db1
```

2、执行命令查看int数据类型+null结合的执行结果。

```
select int '123'||'null';
```

执行结果：

```
 ?column?
----------
 123null
(1 row)
```

