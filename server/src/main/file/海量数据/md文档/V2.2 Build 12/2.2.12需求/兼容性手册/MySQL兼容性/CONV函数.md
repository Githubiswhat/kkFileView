# CONV

## 功能描述

CONV函数用于将数值从一种进制转换为另一种进制，函数返回值为目标进制字符串的形式。

## 语法格式

```sql
CONV(N,from_base,to_base)
```

## 参数说明

- **N**

  需要转换进制的数值，可以指定为一个整数或一个字符串。

- **from_base**

  当前进制（2到36或-2到-36之间的数字）。

- **to_base**

  目标进制（2到36或-2到-36之间的数字）。

> <div align="left"><img src="image/img1.png" style="zoom:75%")</div>
>
> - 如果任何参数为null，则返回null。
> - 参数N被当做整数，但它的形式可以是一个整数或一个字符串。最小进制是2，最大进制是36。
> - 如果from_base参数是负数，则输入N会被视为有符号数；如果to_base参数是负数，则输出值被视为有符号数。

## 注意事项

无。

## 示例

**示例1：**直接调用CONV函数将16进制的字符串a转换为2进制的字符串表示。

```sql
SELECT CONV('a',16,2);
```

返回结果为：

```SQL
conv
--------
'1010'
(1 row)
```

**示例2：**CONV函数的from_base参数为负数时，输入的参数N将被视为有符号整数。

```sql
SELECT CONV(-2,-10,-2);
```
返回结果为：

```sql
 conv
------
 -10
(1 row)
```

**示例3：**在函数中使用CONV函数。

1、创建测试函数将输入参数a由16进制转换为2进制。

```sql
create or replace function func_test(a int) return text
is testvalue text;
begin
testvalue :=conv(a,16,2);
return testvalue;
end;
/
```

2、调用func_test。

```sql
call func_test(4);
```

返回结果为：

```sql
 func_test
-----------
 100
(1 row)
```

**示例4：**DML语句中使用CONV函数。

1、创建测试表并插入数据。

```
create table test(id int,col varchar2(30));
insert into test values(1,'60E');
```

2、插入和更新数据。

```sql
insert into test values(2,conv(1438,10,16));
insert into test values(3,conv(1439,10,16));
update test set col=conv(1566,10,16) where id=1;
```

3、查询数据。

```sql
select id,col,conv(col,16,10) from test order by id;
```

返回结果为：

```sql
 id | col | conv
----+-----+------
  1 | 61E | 1566
  2 | 59E | 1438
  3 | 59F | 1439
(3 rows)
```

