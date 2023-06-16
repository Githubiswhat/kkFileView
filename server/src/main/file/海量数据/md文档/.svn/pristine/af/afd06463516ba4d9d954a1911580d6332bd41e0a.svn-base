## DATE数据类型

**功能描述**

date数据类型用于存储日期和时间信息，对于每一个date值：

- 展示格式为：YYYY/MM/DD，也可以是YYYY/MM/DD HH24:MI:SS
- 存储格式只有一种：YYYY/MM/DD HH24:MI:SS
- date类型的值在数据库图形界面中可以有不同的显示格式（具体格式由工具设置决定），但其实际存储格式只是其中一种，当只存储年月日时，date实际存储的是:年月日0时0分0秒，在图形界面中，其展示格式为：YYYY/MM/DD。
- date类型对插入带有毫秒的部分直接去除毫秒，不进行四舍五入。
- 支持date数据类型和to_char字符串的转换。

**示例**

1、创建测试表并插入数据。

```
create table t1(id int,col date,col1 timestamp);
insert into t1 select 1,current_timestamp,current_timestamp from dual;
```

2、将date数据类型转换为to_char类型，并查询结果。

```
select id,to_char(col,'yyyy-mm-dd hh24:mi:ss'),to_char(col1,'yyyy-mm-dd hh24:mi:ss.ff6') from t1;
```

返回结果如下(其中col字段去除毫秒，不会做四舍五入)：

```
 id |       to_char       |          to_char
----+---------------------+----------------------------
  1 | 2022-07-04 17:15:16 | 2022-07-04 17:15:16.174639
(1 row)
```

3、获取时间的年份。

```
select to_char(col,'yyyy') as nowyear from t1;
```

返回结果为当前年份：

```
 nowyear
---------
 2022
(1 row)
```
