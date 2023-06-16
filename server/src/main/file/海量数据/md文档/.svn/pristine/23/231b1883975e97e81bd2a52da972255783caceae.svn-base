

###  ROWIDROCHAR

**功能描述**

该函数用于将rowid类型转换为字符串类型。

**语法格式**

```
rowidtochar(rowid)
```

**参数说明**

rowid：输入的rowid值。

**注意事项**

无。

**示例**

1、创建测试表并插入数据。

```
drop table testrowid;
create table testrowid(c1 int,c2 varchar(20));
insert into testrowid values(1,'Lucy');
insert into testrowid values(2,'Sally');
```

2、调用 rowidtochar函数。

```
select rowidtochar(rowid) from testrowid;
```

返回结果如下：

```
     rowidtochar
----------------------
 hksAAA==AAAAAA==AQA=
 hksAAA==AAAAAA==AgA=
(2 rows)
```
