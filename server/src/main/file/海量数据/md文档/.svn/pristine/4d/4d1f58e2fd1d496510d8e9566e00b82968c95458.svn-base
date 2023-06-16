### CREATE FUNCTION中支持PIPELINED和PIPE ROW语法

**功能描述**

CREATE FUNCTION中支持PIPELINED和PIPE ROW语法，让函数可以返回一个集合，在函数中还可以使用PIPE ROW语句，用来返回集合中的单个元素。

**语法格式**

```
CREATE [ OR REPLACE ] FUNCTION func_name ( ... )
RETURNS rettype PIPELINED
...
```

**参数说明**

rettype：函数返回值的数据类型。

**注意事项**

PIPE ROW必须与PIPELINED语法配套使用。

**示例**

1、创建函数。

```
CREATE OR REPLACE FUNCTION test()
  RETURNS int 
  PIPELINED 
  AS $$
  DECLARE
  result RECORD;
  row RECORD;
  BEGIN
  pipe row(1);
  pipe row(2);
  return;
  END
$$
LANGUAGE plpgsql;
```

2、查询结果。

```
select * from table(test());
```

返回结果如下：

```
 test
------
    1
    2
(2 rows)
```

