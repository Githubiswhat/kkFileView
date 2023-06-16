### VSIZE

**功能描述**

VSIZE函数用于返回表达式的真实存储长度，以字节表示。

**语法格式**

```
vsize(expr)
```

**参数说明**

expr：需要返回存储长度的表达式，如果expr为null，函数返回null。

**示例**

1、创建测试表并插入数据。

```
create table t_col(id int primary key ,x varchar(20) );
insert into t_col values (1,'H');
insert into t_col values (2,'侯');
insert into t_col values (3,'H侯');
insert into t_col values (4,'');
select * from t_col;
```

返回结果为：

```
id |  x
----+-----
  1 | H
  2 | 侯
  3 | H侯
  4 |
(4 rows)
```

2、使用VSIZE函数。

```
select id, x, length(x), vsize(x) from t_col;
```

返回结果如下，则表示支持该函数：

```
id ｜ x   | length | vsize
-------------------------

1 ｜ H    |    1     | 1
2 ｜ 侯    |    1     | 3
3 ｜ H侯   |    2      | 4
4 ｜       |          | 
```
