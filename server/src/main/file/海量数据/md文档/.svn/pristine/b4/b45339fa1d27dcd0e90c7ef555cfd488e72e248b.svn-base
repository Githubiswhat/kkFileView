# ANONYMOUS BLOCK

## 功能描述

定义一个新的匿名块。

## 注意事项

无

## 语法格式

```
[DECLARE [declare_statements]]
        BEGIN
        execution_staements
        END;
        /
```

## 参数说明

- **declare_statements**

  声明变量，包括变量名和变量类型，如“sales_cnt int”。

- **execution_statements**

  匿名块中要执行的语句。

  取值范围：DML操作(数据操纵操作：select、insert、delete、update)或系统表中已注册的函数名称。

## 示例

1、创建测试表test。

```
CREATE TABLE test(id int);
```

2、 执行匿名块

```
BEGIN
for i in 0..10 LOOP
INSERT INTO test(id) values (i);
END LOOP;
END;
/
```

3、 验证结果

```
select * from test;
```

当结果显示如下信息，则表示匿名块执行完成。

```
 id 
------------------
  0
  1
  2
  3
  4
  5
  6
  7
  8
  9
 10
(11 rows)
```

## 相关链接

[BEGIN](BEGIN.md)