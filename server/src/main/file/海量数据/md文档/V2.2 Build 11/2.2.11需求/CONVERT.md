# CONVERT

## 功能描述

CONVERT函数包含两种转换功能：

- 将值从一种数据类型转换成参数中指定的另一种数据类型。
- 将值从一个字符集转换成指定的字符集。

## 语法格式

```sql
CONVERT(expression,type);
```

## 参数说明

- **expression**

  待转换的表达式，无数据类型限制，但取决于转换的目标类型如果不能正常转换则返回0或NULL。

- **type**

  类型名称，可以是以下类型中的一种：

  - DATE

  - DATETIME

  - TIME

  - DECIMAL

  - CHAR

  - BINARY

  - INT

  - BIGINT


## 注意事项

该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤**：创建兼容MySQL的库db_mysql，并进入。

```sql
create database db_mysql dbcompatibility  'B';
\c  db_mysql
```

**示例1：**将值从一种数据类型转换成参数中指定的另一种数据类型。

```sql
select convert('11.67',BIGINT);
```

结果返回如下：

```sql
 int8
------
   12
(1 row)
```

**示例2：**将值从一个字符集转换成指定的字符集。

```sql
select convert('aaa',CHAR);
```

结果返回如下：

```sql
 bpchar
--------
 aaa
(1 row)
```

