# YEAR

## 功能描述

YEAR函数用于获取给定时间的年。

## 语法格式

```sql
YEAR(time)
```

## 参数说明

time支持的数据类型如下：

- date

- datetime

- timestamp

- timestamptz

- time

- timetz
- timestamp类型的字符串
- timestamptz类型的字符串
- date类型的字符串
- datetime类型的字符串

## 注意事项

- 若函数的参数是time类型时，由于time类型不包含年月日，按照MySQL的处理，返回当前的年份和月份。
- 输入null时，返回值为空。
- 输入的时间格式非法时，报错信息为时间格式语法异常。
- 数据的时间超范围时，报错信息为输入的时间范围异常。
- 具备该函数的访问权限，例如Year(time)函数如果位于pg_catalog模式下，用户需要具备以下权限：
  - pg_catalog模式的usage权限。
  - year(time)函数的excute权限。

## 兼容性

完全兼容。

## 示例

**示例1：**查询当前年份。

```sql
select year(current_date);
```

结果返回如下：

```sql
 year 
------
 2022
(1 row)
```

**示例2：**查询date类型年份。

1、直接调用。

```sql
select year(date '2022-1-23') as result;
```

结果返回如下：

```sql
 result
--------
   2022
(1 row)
```

2、创建测试表并插入测试数据。

```sql
create table test(info date);
insert into test values ('2022-1-1');
```

3、结合表调用year函数。

```sql
select info,year(info) from test;
```

结果返回如下：

```sql
         info          | year
-----------------------+------
 2021-11-1 21:11:23    | 2021
 2021-11-1 21:11:23+08 | 2021
(2 rows)
```

**示例3：**查询date类型的字符串年份。

1、直接调用year函数。

```sql
select year('2012-1-5') as result;
```

2、创建测试表并插入数据。

```sql
create table test(info text);
insert into test values ('2021-11-1');
```

3、结合表调用year函数。

```sql
select info,year(info) from test;
```

结果返回如下：

```sql
   info    | year
-----------+------
 2021-11-1 | 2021
(1 row)
```

