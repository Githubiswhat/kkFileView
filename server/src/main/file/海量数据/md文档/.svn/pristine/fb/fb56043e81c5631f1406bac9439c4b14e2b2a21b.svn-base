## BINARY_FLOAT数据类型

**功能描述**

binary_float类型是Oracle支持的一种浮点数类型，大小为4字节。本功能是支持Vastbase数据库能够使用binary_float这一类型,并且该数据类型也可以包含在plpgsql中使用。

**示例**

- 表的增删改查支持binary_float数据类型

1、创建测试表并插入数据。

```
CREATE  TABLE test(id int, var binary_float);
INSERT INTO test VALUES(1,1.25),(2,8.234),(3,0.1234);
```

2、查询表的数据。

```
select * from test;
```

返回结果为：

```
 id |  var
----+-------
  1 |  1.25
  2 | 8.234
  3 | .1234
(3 rows)

```

3、更新表中的数据并查询结果。

```
UPDATE test SET var = 7.321 WHERE id = 2;
SELECT * FROM test;
```

返回结果为：

```
 id |  var
----+-------
  1 |  1.25
  3 | .1234
  2 | 7.321
(3 rows)
```

4、更新表中的数据并查询结果。

```
DELETE FROM test WHERE id = 3;
SELECT * FROM test;
```

返回结果为：

```
 id |  var
----+-------
  1 |  1.25
  2 | 7.321
(2 rows)
```

- PL/PGSQL中支持binary_float数据类型

1、创建存储过程。

```
create or replace function test()
return binary_float as 
declare
    a binary_float := 1.23;
    b binary_float :=3.45;
begin
    return a + b;
end;
/
```

2、查询结果。

```
SELECT test();
```

返回结果为：

```
 test
------
 4.68
(1 row)
```
