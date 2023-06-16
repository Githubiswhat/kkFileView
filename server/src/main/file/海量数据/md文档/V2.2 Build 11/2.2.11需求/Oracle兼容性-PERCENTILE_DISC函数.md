# PERCENTILE_DISC

## 功能描述

PERCENTILE_DISC函数可以根据输入的百分比与排序规则从集合中返回一个元素。

## 语法格式

```sql
PERCENTILE_DISC(number) WITHIN GROUP(ORDER BY column1 [ DESC | ASC ])[OVER (partition by column2) ][aliasnames] from table;
```

## 参数说明

- **number**

  小数值。

  取值范围：0-1之间（包含0和1）。

- **column1**

  列名，需要排序的列，后面可添加DESC或者ASE指定排序规则。

  默认值：ASE（升序）

- **column2**

  列名，用于分组的列，在每个组中，column2的值完全相同。

- **aliasname**

  select显示结果的别名（可选）。

- **table**

  表名。

## 注意事项

- 使用该语法时可不带over子句，语法如下：

  ```sql
  SELECT PERCENTILE_DISC(number) WITHIN GROUP(ORDER BY column1 [ DESC | ASC ])[aliasnames] from table;
  ```

  该用法只能select一项，即下面语句是无法执行的：

  ```sql
  SELECT customer_id,sum_order,PERCENTILE_DISC(0.4) WITHIN GROUP(ORDER BY sum_orders disc from small_customers;
  ```

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

## 示例

1、创建并切换至兼容模式为Oracle的数据库db_oracle。

```sql
CREATE DATABASE db_oracle dbcompatibility='A';    
\c db_oracle
```

2、创建测试表并插入数据。

```sql
create table ID1096014_tb1 (col1 int,col2 int,col3 int,col4 number,col5 number(5),col6 numeric,col7 numeric(18,9),col8 numeric(38,4),col9 varchar2(10),col10 float);
insert into ID1096014_tb1 values(1,1,1,20,40.5,60,12548.62,8511651551251.6523,'a',1.23);
insert into ID1096014_tb1 values(2,2,2,40,60.5,80,22548.62,9511651551251.6523,'b',2.23);
insert into ID1096014_tb1 values(-10,-10,-10,60,60.5,80,32548.62,7511651551251.6523,'c',3.23);
insert into ID1096014_tb1 values(2,3,3,30,50.5,70,32548.62,3511651551251.6523,'d',4.23);
insert into ID1096014_tb1 values(3,4,4,10,50.5,70,32548.62,4511651551251.6523,'e',5.23);
insert into ID1096014_tb1(col1) values(1);
insert into ID1096014_tb1(col1) values(2);
insert into ID1096014_tb1(col1) values(3);
```

3、查询表中的数据。

```sql
select * from ID1096014_tb1;
```

返回结果为：

```sql
 col1 | col2 | col3 | col4 | col5 | col6 |   col7   |        col8        | col9 | col10
------+------+------+------+------+------+----------+--------------------+------+-------
    1 |    1 |    1 |   20 |   41 |   60 | 12548.62 | 8511651551251.6523 | a    |  1.23
    2 |    2 |    2 |   40 |   61 |   80 | 22548.62 | 9511651551251.6523 | b    |  2.23
  -10 |  -10 |  -10 |   60 |   61 |   80 | 32548.62 | 7511651551251.6523 | c    |  3.23
    2 |    3 |    3 |   30 |   51 |   70 | 32548.62 | 3511651551251.6523 | d    |  4.23
    3 |    4 |    4 |   10 |   51 |   70 | 32548.62 | 4511651551251.6523 | e    |  5.23
    1 |      |      |      |      |      |          |                    |      |
    2 |      |      |      |      |      |          |                    |      |
    3 |      |      |      |      |      |          |                    |      |
(8 rows)
```

4、直接调用percentile_disc函数。

```sql
select percentile_disc(0.5) within group(order by col5) over (partition by col9 ) from ID1096014_tb1;
```

返回结果为：

```sql
 percentile_disc
-----------------
              41
              61
              61
              51
              51



(8 rows)
```

5、delete中使用percentile_disc函数删除包含步骤3返回结果的表数据行。

```sql
delete from ID1096014_tb1 where col5 in (select percentile_disc(0.5) within group(order by col5) over (partition by col9 ) from ID1096014_tb1);
```

6、查询删除后的数据。

```sql
select * from ID1096014_tb1;
```

返回结果为：

```sql
 col1 | col2 | col3 | col4 | col5 | col6 | col7 | col8 | col9 | col10
------+------+------+------+------+------+------+------+------+-------
    1 |      |      |      |      |      |      |      |      |
    2 |      |      |      |      |      |      |      |      |
    3 |      |      |      |      |      |      |      |      |
(3 rows)
```

