# LOCATE

## 功能描述

LOCATE函数用于查找子字符串在原字符串中的位置。

函数返回值如下所示：

- 0：子字符串不在原字符串中。
 - 非0值：子字符串在原字符串的位置，以1为起始。

如果输入值存在null时，则函数返回null。

## 语法格式

```sql
LOCATE(substr,str [,pos])
```

## 参数说明

- **substr：**

    子字符串。

- **str**

    原字符串。

- **pos**

    原字符串开始查找的位置，以1为起始，当pos参数给出时，则从pos位置开始。如果pos值小于1，则结果返回0。当pos有小数时，pos取值为整数值加1，例如`locate('a','nbabaaa',4.5)`，表示从第5个字符开始搜索字符a在'nbabaaa'中的位置。


## 注意事项

无。

## 示例

**示例1：**直接调用LOCATE函数。

- 指定pos参数。

  ```sql
  --正整数
  select locate('a','nbanaa',4);
  --负数
  select locate('a','nba',-4);
  --小数
  select locate('a','nbabaaa',4.5);
  ```

  返回结果分别为：

  ```sql
   locate
  --------
        5
  (1 row)
  ```

  ```sql
   locate
  --------
        0
  (1 row)
  ```

  ```sql
   locate
  --------
        5
  (1 row)
  ```

- 不指定pos参数。

  ```sql
  select locate('ABC','KJABCFG');
  ```

  返回结果为：

  ```sql
   locate
  --------
        3
  (1 row)
  ```

**示例2：**在函数中使用LOCATE函数。

1、创建函数。

```sql
create or replace function fun_1116772(a in char(16),b in char(16),n out int)return int
as
begin
select locate(a,b) into n;
return n;
end;
/
```

2、调用函数pro_1116772。

```sql
select fun_1116772('ABV','HBABV');
```

返回结果为：

```sql
 fun_1116772
-------------
           3
(1 row)
```

