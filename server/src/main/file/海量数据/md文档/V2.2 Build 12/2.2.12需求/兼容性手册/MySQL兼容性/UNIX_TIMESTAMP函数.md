# UNIX_TIMESTAMP

## 功能描述

Vastbase G100在MySQL兼容模式下，支持用户通过unix_timestamp函数获取时间戳。函数的调用有以下两种情况：

- unix_timestamp()不带date参数调用，返回值为一个unix时间戳，代表的是自'1970-01-01 00:00:00'UTC以来的秒数。
- unix_timestamp()使用date参数调用，返回值为自'1970-01-01 00:00:00'UTC以来的秒数。

## 语法格式

```sql
unix_timestamp([date])
```

## 参数说明

**date**

表示时间，为timestamptz字符串。

## 注意事项

该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建并切换至兼容模式为MySQL的数据库db_mysql。

```sql
CREATE DATABASE db_mysql dbcompatibility='B';    
\c db_mysql
```

**示例1：**在系统表中查询函数信息。

```sql
\x    --列式展示查询结果
select * from pg_proc where proname='unix_timestamp';
```

查询结果为：

```sql
-[ RECORD 1 ]-------+---------------------------
proname             | unix_timestamp
pronamespace        | 11
proowner            | 10
prolang             | 12
procost             | 1
prorows             | 0
provariadic         | 0
protransform        | -
proisagg            | f
proiswindow         | f
prosecdef           | f
proleakproof        | f
proisstrict         | t
proretset           | f
provolatile         | v
pronargs            | 0
pronargdefaults     | 0
prorettype          | 23
proargtypes         |
proallargtypes      |
proargmodes         |
proargnames         |
proargdefaults      |
prosrc              | unix_timestamp
probin              |
proconfig           |
proacl              |
prodefaultargpos    |
fencedmode          | f
proshippable        |
propackage          | f
prokind             | f
proargsrc           |
propackageid        | 0
proisprivate        | f
prosrcoffset        | 0
proargtypesext      |
prodefaultargposext |
allargtypes         |
allargtypesext      |
protypeoid          | 0
protypekind         |
isfinal             | f
-[ RECORD 2 ]-------+---------------------------
proname             | unix_timestamp
pronamespace        | 11
proowner            | 10
prolang             | 12
procost             | 1
prorows             | 0
provariadic         | 0
protransform        | -
proisagg            | f
proiswindow         | f
prosecdef           | f
proleakproof        | f
proisstrict         | t
proretset           | f
provolatile         | i
pronargs            | 1
pronargdefaults     | 0
prorettype          | 23
proargtypes         | 1184
proallargtypes      |
proargmodes         |
proargnames         |
proargdefaults      |
prosrc              | unix_timestamp_timestamptz
probin              |
proconfig           |
proacl              |
prodefaultargpos    |
fencedmode          | f
proshippable        |
propackage          | f
prokind             | f
proargsrc           |
propackageid        | 0
proisprivate        | f
prosrcoffset        | 0
proargtypesext      |
prodefaultargposext |
allargtypes         | 1184
allargtypesext      |
protypeoid          | 0
protypekind         |
isfinal             | f
```

**示例2：**将unix_timestamp()值作为字段默认值。

1、创建测试表。

```sql
CREATE TABLE t_unix_timestamp(c1 int4 default unix_timestamp('2023-02-03 10:00:00'));
```

2、向测试表中插入default值，并查询表记录。

```sql
insert into t_unix_timestamp values(default);
select * from t_unix_timestamp;
```

查询结果为：

```sql
     c1
------------
 1675418400
(1 row)
```

