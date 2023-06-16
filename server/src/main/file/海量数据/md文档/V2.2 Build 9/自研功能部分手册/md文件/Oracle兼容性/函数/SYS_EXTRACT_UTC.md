### SYS_EXTRACT_UTC

**功能描述**

sys_extract_utc函数将一个带有时区的时间对象，转变成其等效的国际标准时间（格林威治时间）。即把timestamp with time zone类型，转成timestamp without time zone类型。

**语法格式**

```
SYS_EXTRACT_UTC(datetime_with_timezone)
```

**参数说明**

datetime_with_timezone：timestamptz（即timestamp with time zone）类型。

**注意事项**

使用该函数需要具备该函数的访问权限，例如sys_extract_utc函数如果位于pg_catalog模式下，用户需要具备以下权限：

- pg_catalog模式的usage权限

- sys_extract_utc函数的excute权限

**示例**

1、创建测试表。

```
DROP TABLE t_utc;
CREATE TABLE t_utc(c1 timestamptz);
```

2、向表中插入带有时区的数据。

```
insert into t_utc values('2022-02-02 10:00:00 +4:00');
insert into t_utc values('2022-02-03 12:00:00 -2:00');
insert into t_utc values('2010-01-03 11:09:00 +0:00');
```

3、查询数据。

```
select sys_extract_utc(c1) from t_utc; 
```

返回结果为：

```
   sys_extract_utc
---------------------
 2022-02-02 06:00:00
 2022-02-03 14:00:00
 2010-01-03 11:09:00
(3 rows)
```
