### ALL语法

**功能描述**

ALL操作符用于将值与子查询返回的值列表的结果集进行比较。

**语法格式**

```
Operator ALL(v1,v2,v3)
Operator ALL(subquery)
```

**参数说明**

Operator：标准的比较运算符（=，<>，=，>，> =，<，或<！=）。

**注意事项**

该语法必须与比较运算符一起使用。

**兼容性**

完全兼容。

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

create table testall1(id int,name char(10));
insert into testall1 values(1,'taylor');
insert into testall1 values(99,'jane');
```

2、使用ALL(v1,v2,v3)语法查询。

```
 select * from testall where id  < all(3,98);
```

当结果返回如下，则表示查询成功：

```
id |    name    
----+------------
1 | amy       
2 | tom       
(2 rows)
```

3、使用ALL(subquery)语法。

```
select * from testall where id = all(select id from testall1 where name='taylor');
```

当结果返回如下，则表示查询成功：

```
 id |    name
----+------------
  1 | amy
(1 row)
```
