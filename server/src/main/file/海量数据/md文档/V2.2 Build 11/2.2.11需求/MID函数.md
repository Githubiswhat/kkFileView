# MID函数

## 功能描述

使用MID函数从varbit可变长位串和text类型字符串中提取子串。

## 语法格式

```sql
MID(str, pos, len)
MID(str FROM pos FOR len)
MID(str FOR len FROM pos)
```

## 参数说明

- **str**

  原本的字符串，不允许为空，类型为varbit可变长位串或text字符串，不支持bytea类型。

- **pos**

  int类型，要截取字符串的开始位置。为负数时表示从字符串结尾开始截取，不允许为空。

- **len**

  int类型，要截取的字符串的长度。为空时返回从pos开始后续所有的字符组成的字符串，不允许为负数。

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>    
>
> 函数返回类型为varbit可变长位串或text字符串。位串场景按照位截取，text字符串场景按照字符截取。

## 注意事项

该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建并进入兼容MySQL的库db_mysql。

```sql
CREATE DATABASE db_mysql DBCOMPATIBILITY 'B';
\c db_mysql 
```

**示例1：**提取字符串中从第二个字符开始往后3个字符。

```sql
select mid('string', 2, 3);
```

结果返回如下：

```sql
 mid
-----
 tri
(1 row)
```

**示例2：**使用MID(str FROM pos FOR len)提取字符传中从第二个字符开始往后10个字符。

```sql
select mid('cbwinx!@##;xa' from 2 for 10);
```

结果返回如下：

```sql
    mid
------------
 bwinx!@##;
(1 row)
```

**示例3：**使用MID(str FOR len FROM pos)提取字符传中从第二个字符开始往后10个字符。

```sql
select mid('cbwinx!@##;xa' for 10 from 2);
```

结果返回如下：

```sql
    mid
------------
 bwinx!@##;
(1 row)
```

