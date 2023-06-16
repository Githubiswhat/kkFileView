# MAKEDATE

## 功能描述

MAKEDATE函数根据给定的年值year和日值dayofyear返回一个日期。可用于计算year年第dayofyear天的日期。

## 语法格式

```sql
MAKEDATE(year,dayofyear)
```

## 参数说明

- **year**

  给定的年份。

- **dayofyear**

  天数值。

## 注意事项

- 天数值dayofyear必须大于0，否则结果为空。

- 如任一参数值为空，结果为空。
- 如任一参数值为小数，根据四舍五入取整。

## 示例

**示例1：**计算2021年第100天的日期。

```sql
select makedate(2021,100);
```

结果返回如下：

```sql
  makedate
------------
 2021-04-10
(1 row)
```

**示例2：**参数值为小数。

```sql
select makedate(2021.6,100.2);
```

结果返回如下：

```sql
  makedate
------------
 2022-04-10
(1 row)
```

**示例3：**天数值大于365。

```sql
select makedate(2021,400);
```

结果返回如下：

```sql
  makedate
------------
 2022-02-04
(1 row)
```

