# MySQL兼容性-SYSDATE函数

## 功能描述

SYSDATE函数用于获取当前系统时间。可根据参数设置时间精度。

## 语法格式

```sql
SYSDATE([fsp])
```

## 参数说明

**fsp**

用于表示秒的小数位数。

取值范围：0~6。

## 注意事项

该特性仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤**：创建兼容MySQL的库db_mysql，并进入。

```sql
create database db_mysql dbcompatibility  'B';
\c  db_mysql
```

**示例1：**SELECT直接调用SYSDATE()函数。

```sql
select sysdate(2);
```

结果返回如下：

```sql
        sysdate
------------------------
 2022-11-17 16:54:40.63
(1 row)
```

**示例2：**SQL语句中调用SYSDATE()函数。

1、创建测试表stest并插入数据。

```sql
CREATE TABLE stest(id int ,stime1 timestamp,stime2 char(30));
INSERT INTO stest values(1,sysdate(),sysdate());
INSERT INTO stest values(2,sysdate(2),sysdate(2));
```

2、查询数据。

```sql
SELECT * FROM stest;
```

结果返回如下：

```sql
 id |         stime1         |             stime2
----+------------------------+--------------------------------
  1 | 2022-11-17 17:01:28    | 2022-11-17 17:01:28
  2 | 2022-11-17 17:01:29.36 | 2022-11-17 17:01:29.36
(2 rows)
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>  
>
> SYSDATE()函数返回值支持隐式转换，如stime2所示。