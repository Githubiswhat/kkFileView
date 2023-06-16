# Insert语句支持使用set赋值

**功能描述**

使用INSERT语句插入数据时支持使用SET进行赋值。

**语法格式**

```
INSERT [INTO] tbl_name SET assignment_list
```

**参数说明**

tbl_name：表名。

assignment_list：赋值语句。

**注意事项**

该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

**兼容性**

- 不支持向指定的分区插入数据。
- 不支持插入的行/列取别名。
- 不支持调度优先级。

**示例**

1、创建兼容MySQL的库db_mysql，并进入。

```
CREATE DATABASE  db_mysql  DBCOMPATIBILITY=  'B';
\c  db_mysql
```

2、创建测试表。

```
create table setTest(id int primary key,name varchar(128)not null,addr varchar(128)default 'ABC');
```

3、使用SET插入数据。

```
insert into setTest set id=1,name='张三',addr='和平路1号';
insert into setTest set id=2,name='李四',addr=default;
```

4、查询插入结果。

```
select * from setTest;
```

结果返回如下：

```
 id | name |   addr    
----+------+-----------
  1 | 张三 | 和平路1号
  2 | 李四 | ABC
(2 rows)
```

