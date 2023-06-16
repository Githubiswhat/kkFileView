# 支持update使用where current of

**功能描述**

在PG兼容模式下，Vastbase G100支持update命令使用where current of可选子句。使用此子句可以更新游标对应的字段值。

**语法格式**

```
UPDATE[ONLY]table_name[*][[AS]alias]
 SET{column_name={expression|DEFAULT}|
    ()
    ()
   }[,...]
  [FROM from_item[,...]]
  [WHERE cindition|WHERE CURRENT OF cursor_name]
  [RETURNING *|output_expression[[AS]output_name][,...]]
```

**参数说明**

cursor_name：要在WHERE CURRENT OF条件中使用的游标名。要被更新的是从这个游标中最近取出的行。该游标必须是一个在UPDATE目标表上的非分组查询。

**注意事项**

- 仅在PG兼容模式下有效。
- WHERE CURRENT OF不能和一个布尔条件一起指定。

**示例**

1、创建数据库，检查兼容性。

```
CREATE DATABASE my_test DBCOMPATIBILITY 'PG';
\c my_test
show sql_compatibility;
```

2、创建测试表。

```
create table t1(col1 int,col2 text,col3 varchar,col4 timestamp,col5 boolean);
```

3、创建用户并授权。

```
create user user1 with password 'Bigdata@123';
grant all on t1 to user1;
```

4、插入数据。

```
insert into t1 values(1,'testtest1','test1','20220127',true);
insert into t1 values(2,'testtest2','test2','20220227',false);
insert into t1 values(3,'testtest3','test3','20220327',false);
```

5、切换到用户user1，开始事务。

```
\c - user1;
start transaction;
declare cursor1 cursor for select * from t1 where col1=1;//定义游标
fetch forward 1 from cursor1;//抓取头一行到游标cursor1里
update t1 set col1=0 where current of cursor1;//更新游标中对应行的字段值
select * from t1;//查看结果
close cursor1;//关闭游标并提交事务
end;//结束事务
```

返回结果为：

```
 col1 |   col2    | col3  |        col4         | col5
------+-----------+-------+---------------------+------
    2 | testtest2 | test2 | 2022-02-27 00:00:00 | f
    3 | testtest3 | test3 | 2022-03-27 00:00:00 | f
    0 | testtest1 | test1 | 2022-01-27 00:00:00 | t
(3 rows)
```

col1=1的数据改为col1=0，并插在表尾。