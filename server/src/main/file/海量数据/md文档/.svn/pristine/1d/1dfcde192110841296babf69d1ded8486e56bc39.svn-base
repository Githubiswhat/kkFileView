# MySQL兼容性-存储过程中允许使用START TRANSACTION

## 功能描述

在MySQL兼容模式下，Vastbase G100支持在存储过程中使用START TRANSACTION语句开启一个事务。

## 语法格式

```sql
CREATE [ OR REPLACE ] PROCEDURE procedure_name
 [ OPTION... ]
 { IS | AS }
 BEGIN
 ...
 START TRANSACTION;
 ...
 COMMIT;
 END;
/
```

## 参数说明

- **OR REPLACE**

  当存在同名的存储过程时，替换原来的定义。

- **procedure_name**

  创建的存储过程名称，可以带有模式名。

  取值范围：字符串，需符合标识符的命名规范。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

- 仅Vastbase G100 2.2 Build 11及以后版本支持此功能。

## 示例

1、创建并切换至兼容模式为B的数据库下。

```sql
CREATE DATABASE db_mysql DBCOMPATIBILITY 'B';
\c db_mysql;
```

2、创建存储过程。（包含START TRANSACTION语句。）

```sql
CREATE OR REPLACE PROCEDURE proc_transaction()
as
a int;
BEGIN
a=1;
start transaction;
raise notice 'test: %',a;
commit;
end;
/
```

3、调用存储过程。

```sql
call proc_transaction();
```

调用成功，返回结果为：

```sql
NOTICE:  test: 1
 proc_transaction
------------------

(1 row)
```

