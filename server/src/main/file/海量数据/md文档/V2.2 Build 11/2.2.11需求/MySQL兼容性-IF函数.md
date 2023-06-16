# MySQL兼容性-IF函数

## 功能描述

判断给定条件是否为TRUE，为TRUE返回一个值，为FALSE返回另一个值。

## 语法格式

```sql
IF(expr1,expr2,expr3)
```

## 参数说明

- **expr1**

  自定义给定条件。

- **expr2**

  当给定条件为TRUE时的返回值，可自定义。

- **expr3**

  当给定条件为FALSE时的返回值，可自定义。

## 注意事项

该特性仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建兼容MySQL的库db_mysql，并进入。

```sql
create database db_mysql dbcompatibility  'B';
\c  db_mysql
```

**示例1：**SELECT 直接调用IF函数。

```sql
SELECT IF( (1 IN (1,2)),'YES','NO');
```

结果返回如下：

```sql
 if
-----
 YES
(1 row)
```

**示例2：**SELECT语句中调用IF函数。

1、创建测试表并插入数据。

```sql
CREATE TABLE users (
id int NOT NULL,
username varchar(255) DEFAULT NULL,
age int DEFAULT NULL,
PRIMARY KEY (id)
) ;
INSERT INTO users VALUES ('1', 'Harry', '18');
INSERT INTO users VALUES ('2', 'Odin', '19');
INSERT INTO users VALUES ('3', 'Jack', '25');
INSERT INTO users VALUES ('4', 'Bobi', '8');
INSERT INTO users VALUES ('5', 'Tom', '27');
INSERT INTO users VALUES ('6', 'Flying', '21');
```

2、调用IF函数进行查询。

```sql
SELECT id, username,IF(age<18,'未成年','成年') AS  是否成年  FROM users;
```

结果返回如下：

```sql
 id | username | 是否成年
----+----------+----------
  1 | Harry    | 成年
  2 | Odin     | 成年
  3 | Jack     | 成年
  4 | Bobi     | 未成年
  5 | Tom      | 成年
  6 | Flying   | 成年
(6 rows)
```

