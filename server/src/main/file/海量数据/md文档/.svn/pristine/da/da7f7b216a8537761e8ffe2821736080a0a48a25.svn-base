# DELIMITER自定义分隔符

放入MySQL兼容性-PL/SQL

## 功能描述

MySQL客户端中默认分隔符为“;” ，在PL/SQL编程开发过程中可能存在因使用了“;”导致意外触发分割效果的情况，此时需要提前将分隔符定义为其他符号。

## 语法格式

```sql
DELIMITER [character]
```

## 参数说明

**character**

可指定任意字符，通常选择连续的无意义符号作为自定义分隔符，例如“$$”、“//”等。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

- 创建自定义分隔符时，会以DELIMITER关键字后的第一个连续字符串作为分隔符，例如以下语句中会以“abcd”作为分隔符，而不是“$$”。

  ```sql
  DELIMITER abcd $$
  ```

## 示例

1、创建兼容MySQL的库db_mysql，并进入。

```sql
CREATE DATABASE  db_mysql  DBCOMPATIBILITY ='B';
\c  db_mysql
```

2、设置分隔符为“//”并创建存储过程。

```sql
DELIMITER //
CREATE PROCEDURE testDelimiter() 
as
begin
select 'testDelimiter';
end;
//
```

3、恢复分隔符为“;”。

```sql
DELIMITER ;
```

