# CHARINDEX

## 功能描述

CHARINDEX函数会在第二个字符表达式中搜索一个字符表达式，返回第一个表达式（如果发现存在）的开始位置。如果没有发现，则返回0，类型为 int。如果 expressionToFind和 expressionToSearch有一个为空串，返回 NULL。

## 语法格式

```sql
CHARINDEX(expressionToFind,expressionToSearch [, start_location])
```

## 参数说明

- **expressionToFind**

  字符表达式，其中包含要查找的序列。

- **expressionToSearch**

  要搜索的字符表达式。

- **start_location**

  表示搜索开始位置的integer 或 bigint表达式。如果start_location 未指定、具有负数值或值为0，搜索将从expressionToSearch 的开头开始。

## 注意事项

无。

## 示例

**示例1：**对字符串使用charindex函数。

```sql
select charindex('is','this is a string');
```

结果返回如下：

```sql
 charindex
-----------
         3
(1 row)
```

**示例2：**指定start_location对字符串使用charindex函数。

```sql
select charindex('is','this is a string',4);
```

结果返回如下：

```sql
 charindex
-----------
         6
(1 row)
```

**示例3：**在表中调用charindex函数，start_location值为表中的列值。

1、创建测试表并插入测试数据。

```sql
CREATE TABLE test_1130762(student_id int, city varchar, address varchar, key int);
insert into test_1130762 values(1, 'beijing', '北京市beijing东城区', 2);
insert into test_1130762 values(2, 'tianjin', '天津tianjin市天津东城区', 4);
insert into test_1130762 values(3, 'xian', '西安市xian雁塔区', 4);
```

2、调用charindex函数查询数据。

```sql
select student_id, CHARINDEX(city, address, key) from test_1130762 order by student_id;
```

结果返回如下：

```sql
 student_id | charindex
------------+-----------
          1 |         4
          2 |         0
          3 |         4
(3 rows)
```

