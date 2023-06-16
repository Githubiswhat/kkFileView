# GROUP_CONCAT函数

**功能描述**

group_concat函数用于将group by 产生的同一个分组中的值连接起来。

**语法格式**

```
GROUP_CONCAT([DISTINCT] expr [,expr ...]             
	[ORDER BY {unsigned_integer | col_name | expr}                 
		[ASC | DESC] [,col_name ...]]             
	[SEPARATOR str_val])
```

**参数说明**

- expr：需要组合的表达式。
- unsigned_integer：无符号整数。
- col_name：列名。
- str_val：分隔符。

**注意事项**

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 需具备该函数访问权限。例如group_concat函数如果位于pg_catalog模式下，用户首先需要具备pg_catalog模式的usage权限，然后具备group_concat函数的execute权限。
- 如果分组中的值为null，则返回值为null。

**兼容性**

由于vastbase中没有参数group_concat_max_len，根据客户现场使用情况，返回值类型为text或blob类型，返回值类型取决于输入参数是字符还是二进制。

**示例**

1、创建兼容MySQL的库db_mysql，并进入。

```
CREATE DATABASE  db_mysql  DBCOMPATIBILITY=  'B';
\c  db_mysql
```

2、创建测试表并插入测试数据。

```
create table contest(id1 int,id2 bigint,id3 smallint,id4 tinyint,id5 integer,id6 serial primary key);
insert into contest values(45,87948,55,48,789,2489);
insert into contest values(78,87948,55,48,789,25499);
insert into contest values(88,87948,55,48,789,54899);
```

3、查询验证。

```
select group_concat(id1,id2,id3,id4,id5,id6 order by id1) from contest group by id1;
```

结果显示如下：

```
    group_concat     
---------------------
 458794855487892489
 7887948554878925499
 8887948554878954899
(3 rows)
```

