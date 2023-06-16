### ALL

**功能描述**

ALL函数用于判断数组中所有元素都是否为真（非零或true）。用逗号分隔的方式输入数组表达式的各个分量。

**语法格式**

```
op ALL(ARRAY[expr,])
```

**参数说明**

- op：指定的运算符，诸如‘>’，‘<’，‘=’等等。
- ARRAY：指显示指定数组表达式的关键字，中括号内部可以指定多个表达式。

**示例**

1、创建测试表并插入数据。

```
create table testall(id int,name char(10));
insert into testall values(1,'amy');
insert into testall values(2,'tom');
insert into testall values(3,'jack');
insert into testall values(97,'jane');
insert into testall values(98,'alice');
insert into testall values(99,'taylor');
```

2、使用ALL函数。

```
select * from testall where id  >= all(2,3);
```

返回结果为：

```
 id |    name
----+------------
  3 | jack
 97 | jane
 98 | alice
 99 | taylor
(4 rows)
```
