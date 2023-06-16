## TIMESTAMP数据类型

**功能描述**

该数据类型是DATE数据类型的扩展，它可以存储更加精确的时间值。Vastbase G100支持14位(秒)或者17位（毫秒）数字格式数输入。

**示例**

1、创建测试表并插入数据。

```
create table test_timestamp(a timestamp,b timestamptz);
insert into test_timestamp values('2022-1-12 12:23:23','2022-1-12 12:23:23');
insert into test_timestamp values('20220112122323','20220112122323');
```

2、查询表中数据。

```
select * from test_timestamp ;
```

返回结果如下：

```
          a          |           b            
---------------------+------------------------
 2022-01-12 12:23:23 | 2022-01-12 12:23:23+08
 2022-01-12 12:23:23 | 2022-01-12 12:23:23+08
(2 rows)
```

