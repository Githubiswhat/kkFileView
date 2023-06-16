# 无关联条件JOIN

## 功能描述

Vastbase G100在MySQL兼容模式下，支持JOIN不加关联条件，返回结果为笛卡尔积的形式。

## 注意事项

该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建兼容模式为MySQL的库db_mysql，并进入。

```
create  database  db_mysql  dbcompatibility  'B';
\c  db_mysql
```

1、创建两张测试表并插入数据。

```sql
create table student(sid int, name varchar(30), class_id int);
create table class (cid int, class_name varchar(30));
insert into student values(1,'a1',100);
insert into student values(2,'a2',101);
insert into class values(100,'class1');
insert into class values(101,'class2');
insert into class values(102,'class3');
```

2、有关联条件JOIN。

```sql
select * from student inner join class ;
```

返回结果为：

```sql
 sid | name | class_id | cid | class_name
-----+------+----------+-----+------------
   1 | a1   |      100 | 100 | class1
   2 | a2   |      101 | 100 | class1
   1 | a1   |      100 | 101 | class2
   2 | a2   |      101 | 101 | class2
   1 | a1   |      100 | 102 | class3
   2 | a2   |      101 | 102 | class3
(6 rows)
```

3、Vastbase在MySQL兼容模式下支持无关联条件的JOIN，也就是说下面语句等价于步骤2，返回结果也与步骤2相同。

```sql
select * from student,class ;
```

