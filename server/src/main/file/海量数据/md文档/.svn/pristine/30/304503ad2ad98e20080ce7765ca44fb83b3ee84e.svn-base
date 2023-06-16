#### SELECT语句使用MONTH、CONTENT、PERCENT，PASSWORD关键字做列别名

**功能描述**

select语句中已支持部分关键字作为目标字段的别名，Vastbase G100 V2.2版本增加关键字month、content、percent和password作为目标字段的别名。

**语法格式**

```
SELECT [/*+ plan_hint */] [ ALL | DISTINCT [ ON ( expression [, ...] ) ] ]
{ * | {expression [output_name ]} [, ...] }
[ FROM from_item [, ...] ]
[ WHERE condition ]
...
```

**参数说明**

output_name：用month、content、percent和password关键字给输出字段起别名。

**注意事项**

该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

**示例**

1、创建兼容Oracle的数据库db_oracle。

```
CREATE DATABASE db_oracle DBCOMPATIBILITY='A';
\c db_oracle
```

2、验证功能。

```
select 1  month,2  content,3  percent,4  password from dual;
```

结果返回如下： 

```
month | content | percent | password 
-------+---------+---------+---------
   1 |    2 |    3|    4
(1  rows)
```

####  