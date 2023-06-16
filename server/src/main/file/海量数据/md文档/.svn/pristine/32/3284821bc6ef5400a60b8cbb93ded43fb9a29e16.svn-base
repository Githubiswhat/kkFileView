### ROWIDTOCHAR和CHARTOROWID

**功能描述**

ROWIDTOCHAR函数，将rowid类型转换为字符串类型。

CHARTOROWID函数，将字符串类型转换为rowid类型。

**语法格式**

```
rowidtochar(rowid)
chartorowid(char)
```

**参数说明**

- rowid：输入的rowid值。

- char：输入的字符串。

**示例**

- **ROWIDTOCHAR函数**

1、创建测试表并插入数据。

```
drop table testrowid;
drop table testrowid1;
create table testrowid(c1 int);
create table testrowid1(c1 varchar(20));
insert into testrowid values(1);
insert into testrowid values(2);
```

2、使用ROWIDTOCHAR函数。

```
select rowidtochar(rowid) from testrowid;
```

返回结果为：

```
     rowidtochar
----------------------
 51IAAA==AAAAAA==AQA=
 51IAAA==AAAAAA==AgA=
(2 rows)
```

- **CHARTOROWID函数**

1、创建测试表并插入数据。

```
DROP TABLE testrowid;
create table testrowid (c1 int,c2 varchar(20));
insert into testrowid values(1,'Lucy');
insert into testrowid values(2,'Sally');
insert into testrowid values(3,'Ben');
insert into testrowid values(4,'Black');
```

2、查询ROWID。

```
select rowid from testrowid order by rowid;
```

返回结果为：

```
        rowid
----------------------
 81IAAA==AAAAAA==AQA=
 81IAAA==AAAAAA==AgA=
 81IAAA==AAAAAA==AwA=
 81IAAA==AAAAAA==BAA=
(4 rows)
```

3、将CHAR数据类型转换为ROWID数据类型(CHARTOROWID函数中单引号内的内容为上一步骤的第二条)。

```
select * from testrowid where rowid = CHARTOROWID('81IAAA==AAAAAA==AgA=');
```

返回结果为：

```
 c1 |  c2
----+-------
  2 | Sally
(1 row)
```
