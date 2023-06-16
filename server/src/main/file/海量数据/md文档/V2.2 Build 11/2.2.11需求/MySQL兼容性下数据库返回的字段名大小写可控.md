# lower_case_column_names

**参数说明**：该参数用于控制数据库返回字段名的大小写敏感性。

**取值范围：**0、1

- 0：开启大小写敏感。
- 1：关闭大小写敏感。

**默认值：**0

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>  
>
> 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

**示例**

1、修改数据库配置文件postgresql.conf中lower_case_column_names参数(0表示开启，1表示关闭，默认为0开启)。

```
lower_case_column_names=0
```

2、重启数据库实例。

3、创建并切换至兼容模式为MySQL的库db_mysql。

```
CREATE DATABASE db_mysql dbcompatibility='B';
\c db_mysql
```

4、创建测试表并插入数据。

```
create table test(id int,Home varchar);
insert into test values(1,2),(3,4);
```

5、查询数据库中字段名称。

```
select * from test;
```

返回结果为：

```
 id | Home
----+------
  1 | 2
  3 | 4
(2 rows)
```

