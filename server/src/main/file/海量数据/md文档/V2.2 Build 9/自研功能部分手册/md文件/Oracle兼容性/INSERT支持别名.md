### INSERT支持别名

**功能描述**

支持在INSERT语句中对表名使用别名。

**语法格式**

```
INSERT INTO table_name t1(t1.col1,t1.col2);
```

**参数说明**

- table_name：表名。
- t1：表的别名。
- col1：表的列1。
- col2：表的列2。

**注意事项**

无。

**兼容性**

完全兼容。

**示例**

1、创建测试表并插入数据。

```
create table table1 (col1 int,col2 int);
create table table2 (col1 int,col2 int);
insert into table2 values(1,2);
insert into table2 values(2,3);
insert into table1  t1(t1.col1,t1.col2) select  t2.col1,t2.col2 from table2 t2;
```

2、查询测试表table1。

```
select * from table1;
```

返回结果如下：

```
 col1 | col2
------+------
    1 |    2
    2 |    3
(2 rows)
```
