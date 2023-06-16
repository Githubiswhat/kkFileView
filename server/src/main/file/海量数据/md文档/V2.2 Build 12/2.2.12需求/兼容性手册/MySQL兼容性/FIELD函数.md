# FIELD

## 功能描述

FIELD函数用于查找特定字符串在给出的若干个字符串序列中的位置。

函数返回值如下所示：

- 0：代表待查找字符串不在序列中。
 - 非0值：代表字符串在序列的位置，以1为起始。

## 语法格式

```sql
FIELD(str,str1,str2,str3,...)
```

## 参数说明

- **str**

  待查找字符串。

- **str1,str2,str3,...**

  被查找字符串序列，不定长度。

> <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
>
>  - 如果所有传入参数是字符串类型，则这些参数按照字符串规则进行比较。如果所有传入参数是number类型则按 number类型进行比较。否则将按浮点数类型比较。
>  - 如果 str为 null，则返回 0，因为 null无法和任何值比较。
>

## 注意事项

函数的输入参数个数不能少于2，否则会返回如下错误提示：

```sql
ERROR: Incorrect parameter count in the call to native function 'field'
```

## 示例

**示例1：**直接调用FIELD函数。

```sql
select field(23.17, 3, 23.17899, 23.1777, 23.17);
```

返回结果为：

```SQL
field
------
    4
(1 row)
```

**示例2：**在insert语句中使用FILED函数。

1、创建测试表并插入数据。

```sql
create table tb_1116624(c1 int, c2 varchar(10), c3 text);
insert into tb_1116624 values(1, 'test1', 'test4');
insert into tb_1116624 values(2, 'test2', 'test5');
insert into tb_1116624 values(3, 'test3', 'test3');
insert into tb_1116624 values(4, 'test4', 'test8');
insert into tb_1116624 values(5, 'test5', 'test2');
```

2、创建需要插入数据的表。

```sql
create table test_insert_tb_1116624(c1 int, c2 varchar(10), c3 text);
```

3、insert语句中使用FIELD函数。

```sql\
with select_data_1116624 as(
select * from tb_1116624 order by field(c2, 'test3', 'test1'))
insert into test_insert_tb_1116624 select * from select_data_1116624;
```

4、查看test_insert_tb_1116624表数据。

```sql
select * from test_insert_tb_1116624;
```

返回结果为：

```sql
 c1 |  c2   |  c3
----+-------+-------
  2 | test2 | test5
  4 | test4 | test8
  5 | test5 | test2
  3 | test3 | test3
  1 | test1 | test4
(5 rows)
```

根据结果可知，测试表 test_insert_tb_1116624中插入了经过order by filed排序之后所创建的临时表数据。