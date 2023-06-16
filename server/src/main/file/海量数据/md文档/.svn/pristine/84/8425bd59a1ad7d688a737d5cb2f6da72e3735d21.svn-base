# MySQL兼容性-支持使用#符号注释

## 功能描述

支持在SQL语句以及PLSQL中使用#符号进行注释。

## 注意事项

仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

## 示例

**前置步骤：**创建兼容MySQL的库db_mysql，并进入。

```sql
create database db_mysql dbcompatibility ‘B’；
\c db_mysql
```

**示例1：**SQL语句中使用#注释。

```sql
select 1#4
;
```

结果返回如下：

```sql
 ?column?
----------
        1
(1 row)
```

**示例2：**存储过程语句中存在#。

1、创建测试表char。

```sql
create table char (a varchar(10));
```

2、创建存储过程。

```sql
CREATE PROCEDURE testpro3() as
begin
insert into char values ('ads#da');
insert into char #values ('asdd')
values ('aaaaaa');
end;
/
```

3、执行存储过程。

```sql
call testpro3();
```

4、查询结果。

```sql
SELECT * FROM char;
```

结果返回如下：

```sql
   a
--------
 ads#da
 aaaaaa
(2 rows)
```

