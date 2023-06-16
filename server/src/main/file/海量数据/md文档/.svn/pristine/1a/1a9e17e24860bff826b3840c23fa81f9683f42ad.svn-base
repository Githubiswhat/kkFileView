# QUARTER函数

## 功能描述

QUARTER函数根据给定的date返回对应的季度值。

## 语法格式

```sql
QUARTER(date)
```

## 参数说明

**date**

描述：日期值。

取值范围：date、datetime、timestamp类型数据；日期格式字符串。

## 注意事项

- 当date为NULL时，返回NULL。
- datetime类型仅在数据库兼容模式为MySQL时支持。

## 示例

**示例1：**date使用字符串格式。

```sql
select quarter('2020-06-30');
```

结果返回如下：

```sql
 quarter
---------
       2
(1 row)
```

**示例2：**date支持时间格式，使用::date转化

```sql
select quarter('2022-04-01 10:00:00'::date);
```

结果返回如下：

```sql
 quarter
---------
       2
(1 row)
```

**示例3：**date支持时间格式，使用::timestamp转化。

```sql
select quarter('2022-01-01 10:00:00'::timestamp);
```

结果返回如下：

```sql
 quarter
---------
       1
(1 row)
```

