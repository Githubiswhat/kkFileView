# GETDATE

## 功能描述

Vastbase在SQL Server兼容模式下支持GETDATE函数，用于返回当前数据库系统的时间戳。函数的返回类型为timestamp类型，不含数据库时区偏移量，其中结果截取到毫秒。

## 语法格式

```sql
getdate()
```

## 注意事项

- 本特性仅在数据库兼容模式为SQL Server时能够使用（即创建DB时DBCOMPATIBILITY='MSSQL'），在其他数据库兼容模式下不能使用该特性。
- 函数返回结果末尾有0时，末尾的0会被省略，因此与SQL Server的结果有差异。

## 示例

**前置条件：** 创建兼容SQL Server的库db_sqlserver，并进入。

```sql
create database db_sqlserver dbcompatibility  'MSSQL';
\c db_sqlserver
```

**示例1：**查询当前数据库的时间戳。

```sql
select getdate();
```

结果显示为：

```sql
	getdate
---------------------
2023-01-18 10:39:25.583
```

**示例2：**在表字段中使用getdate函数。

1、创建测试表，并插入数据。

```sql
create table getdate_test1(id int, mygetdate timestamp default getdate());
insert into getdate_test1(id) values (1);
```

2、查询表数据。

```sql
select * from getdate_test1;
```

查询结果为：

```sql
 id |        mygetdate
----+-------------------------
  1 | 2023-02-07 06:19:31.737
(1 row)
```

**示例3：**在DML语句中使用getdate函数。

1、创建测试表，并向表中插入数据。

```sql
create table getdate_test2(
id int,
mygetdate timestamp
);
insert into getdate_test2 values(1,getdate());
select * from getdate_test2;
```

查看表记录结果为：

```sql
 id |        mygetdate
----+-------------------------
  1 | 2023-02-07 07:26:31.803
(1 row)
```

2、使用getdate函数将表中数据更新为当前系统时间。

```sql
update getdate_test2 set mygetdate=getdate();
select * from getdate_test2;
```

查看表记录结果为：

```sql
 id |        mygetdate
----+-------------------------
  1 | 2023-02-07 07:28:12.723
(1 row)
```

3、筛选表中小于当前系统时间的记录。

```sql
select * from getdate_test2 where mygetdate<getdate();
```

查看表记录结果为：

```sql
 id |        mygetdate
----+-------------------------
  1 | 2023-02-07 07:28:12.723
(1 row)
```
