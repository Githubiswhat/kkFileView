# MySQL兼容性-lower_case_table_names参数控制对象名大小写

## 功能描述

在MySQL兼容模式下，Vastbase G100支持通过lower_case_table_names参数来控制表名的大小写敏感特性。该参数值设为0表示大小写敏感（默认），参数值设为1表示大小写不敏感。

## 语法格式

- 创建数据库时指定参数lower_case_table_names的值：


```sql
CREATE DATABASE database_name DBCOMPATIBILITY 'B' lower_case_table_names=X;
```

- 查看当前数据库下参数lower_case_table_names的值：


```sql
show lower_case_table_names;
```

## 参数说明

- **database_name：**创建的数据库名字。
- **X：**参数lower_case_table_names的取值，可选0或1。含义如下：

| 取值 | 意义                                                         |
| ---- | ------------------------------------------------------------ |
| 0    | 对象名使用创建时的大小写样式保存，在比较判断时使用大小写敏感的方式。 |
| 1    | 对象名统一使用小写样式保存，在比较判断时使用大小写不敏感的方式。 |

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

- 仅Vastbase G100 2.2 Build 11及以后版本支持此功能。
- 创建数据库后，用户无法修改此参数。

## 示例

- **示例1：**大小写不敏感

1、创建兼容模式为MySQL的数据库test_mysql，并将其设置为大小写不敏感。

```sql
CREATE DATABASE test_mysql DBCOMPATIBILITY 'B' lower_case_table_names=1;
```

2、切换到数据库test_mysql下，查看当前数据库下参数lower_case_table_names的值。

```sql
\c test_mysql
show lower_case_table_names;
```

返回结果为：

```sql
 lower_case_table_names
------------------------
 1
(1 row)
```

3、在test_mysql02库中创建表table1。

```sql
CREATE TABLE table1
( 
  id    INTEGER, 
 name   varchar(20)
);  
```

4、向表TABLE1中插入数据。

```sql
INSERT INTO TABLE1(id,name) VALUES(1, 'jack');
```

5、查看表TABLE1内容。

```sql
select * from TABLE1;
```

返回结果如下，表示数据插入成功，该数据库大小写不敏感。

```sql
 id | name
----+------
  1 | jack
(1 row)
```

- **示例2：**大小写敏感

1、创建兼容模式为MySQL的数据库test_mysql2，并将其设置为大小写敏感。

```sql
CREATE DATABASE test_mysql2 DBCOMPATIBILITY 'B' lower_case_table_names=0;
```

2、切换到数据库test_mysql2下，创建测试表table2。

```sql
\c test_mysql2

CREATE TABLE table2
( 
  id   INTEGER, 
 name  varchar(20)
);
```

3、向表TABLE2中插入数据。

```sql
INSERT INTO TABLE2(id,name) VALUES (2, 'grace');
```

插入失败，返回结果如下。

```sql
ERROR:  relation "TABLE2" does not exist on node1
```

