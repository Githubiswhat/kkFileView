# PG兼容性-copy语法中format值不带引号

## 功能描述

在PG兼容模式下，copy语法中的format值可以不带引号。

## 语法格式

- 从一个文件拷贝至一个表：

  ```sql
  COPY table_name [ ( column_name [, ...] ) ] 
  	FROM { 'filename' | STDIN }
  	WITH (FORMAT binary);
  ```

- 把一个表拷贝到一个文件：

  ```sql
  COPY table_name [ ( column_name [, ...] ) ]
      TO { 'filename' | STDOUT }
      WITH (FORMAT binary);
  
  COPY query
      TO { 'filename' | STDOUT }
      WITH (FORMAT binary);
  ```

> <div align="left"><img src="image/image1.png" style="zoom:25%"></div>
>
> 完整copy语法内容及其参数说明参见[copy语法](copy.md)。

## 参数说明

- **column_name**

  可选的待拷贝字段列表。

  取值范围：如果没有声明字段列表，将使用所有字段。

- **table_name**

  表的名称（可以有模式修饰）。

  取值范围：已存在的表名。

- **STDIN**

  声明输入是来自标准输入。

- **STDOUT**

  声明输出打印到标准输出。

- **FORMAT**

​		数据源文件的格式。

​		取值范围：CSV、TEXT、FIXED、BINARY。

- **binary**

  数据源文件的格式之一，binary形式的选项会使得所有的数据被存储或读作二进制格式而不是文本。 

  比TEXT和CSV格式的要快一些，但是一个BINARY格式文件可移植性比较差。

- **query**

  查询语句。

## 注意事项

导入内容不能超出数值范围。

## 示例

1、创建数据库，并指定兼容模式。

```sql
CREATE DATABASE my_test DBCOMPATIBILITY='PG';
\c my_test
show sql_compatibility;
```

查看兼容性：

```sql
 sql_compatibility
-------------------
 PG
(1 row)
```

2、创建csv文件，添加内容后保存退出。

```sql
vi /tmp/import_test.csv
1,zhangsan,-92254.08,9月花销,2022/09/28,20:50:45
2,lisi,87758.07,8月收入,2022/09/05,14:20:15
3,wangwu,7758.07,8月报销,2022/09/01,16:43:05
```

 3、创建表。

```sql
create table test(
id integer,
name varchar(100),
cash money,
info text,
date date,
time time
);

create table test_bak(
id integer,
name varchar(100),
cash money,
info text,
date date,
time time
);
```

4、从文件导入数据并查询。

```sql
copy test from '/tmp/import_test.csv' DELIMITERS ',';
select * from test;
```

结果显示为：

```sql
 id |   name   |    cash     |  info   |    date    |   time
----+----------+-------------+---------+------------+----------
  1 | zhangsan | -$92,254.08 | 9月花销 | 2022-09-28 | 20:50:45
  2 | lisi     |  $87,758.07 | 8月收入 | 2022-09-05 | 14:20:15
  3 | wangwu   |   $7,758.07 | 8月报销 | 2022-09-01 | 16:43:05
(3 rows)
```

5、binary导出数据到csv文件。

```sql
copy test to '/tmp/test_out.csv' with (format BINARY);
```

结果显示为：

```sql
COPY 3
```

6、binary导入数据并查询。

```sql
copy test_bak from '/tmp/test_out.csv' with (format BINARY);
select * from test_bak;
```

显示结果为：

```sql
 id |   name   |    cash     |  info   |    date    |   time
----+----------+-------------+---------+------------+----------
  1 | zhangsan | -$92,254.08 | 9月花销 | 2022-09-28 | 20:50:45
  2 | lisi     |  $87,758.07 | 8月收入 | 2022-09-05 | 14:20:15
  3 | wangwu   |   $7,758.07 | 8月报销 | 2022-09-01 | 16:43:05
(3 rows)
```

