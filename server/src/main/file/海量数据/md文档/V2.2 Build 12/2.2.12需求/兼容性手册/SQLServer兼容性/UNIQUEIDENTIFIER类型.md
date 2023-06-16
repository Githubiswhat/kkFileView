# UNIQUEIDENTIFIER类型

## 功能描述

Vastbase G100在SQL Server兼容模式下，支持uniqueidentifier类型。

uniqueidentifier类型是32位的十六进制数字和四个连字符”-“的排列，形如xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx。其中，每个 x 都是 0-9 或 a-f 范围内的十六进制数字。

uniqueidentifier列可用于确保表的多个副本中唯一标识行。

## 注意事项

- 本特性仅在数据库兼容模式为SQL Server时能够使用（即创建DB时DBCOMPATIBILITY='MSSQL'），在其他数据库兼容模式下不能使用该特性。
- 对于长度超过36的字符串转化为uniqueidentifier类型时，Vastbase数据库会报错。
- uniqueidentifier数据类型不支持转换为Vastbase不兼容的SQL Server数据类型。

## 示例

**前置条件：**创建兼容SQL Server的库db_sqlserver，并进入。

```sql
create database db_sqlserver dbcompatibility 'MSSQL';
\c db_sqlserver
```

**示例：**创建带有索引的行存表。

1、创建测试表和创建普通索引和唯一索引。

```sql
create table tb1(col1 uniqueidentifier); 
create index idx1 on tb1(col1); 
```

2、向测试表中插入数据，并查看记录。

```sql
insert into tb1 values('0E984725-C51C-4BF4-9960-E1C80E27ABA0'),('00000000-0000-0000-0000-000000000000'),('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'); 
select * from tb1 order by col1; 
```

结果展示为：

```sql
                 col1
--------------------------------------
 00000000-0000-0000-0000-000000000000
 0e984725-c51c-4bf4-9960-e1c80e27aba0
 aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa
(3 rows)
```

3、修改测试表字段名字，并查看记录。

```sql
alter table tb1 rename col1 to col_new; 
select * from tb1 order by col_new; 
```

结果展示为：

```sql
               col_new
--------------------------------------
 00000000-0000-0000-0000-000000000000
 0e984725-c51c-4bf4-9960-e1c80e27aba0
 aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa
(3 rows)
```

4、更新表中数据，并查看记录。

```sql
update tb1 set col_new='12345678-1234-1234-1234-012345678912' where col_new='0E984725-C51C-4BF4-9960-E1C80E27ABA0'; 
select * from tb1 order by col_new; 
```

结果展示为：

```sql
               col_new
--------------------------------------
 00000000-0000-0000-0000-000000000000
 12345678-1234-1234-1234-012345678912
 aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa
(3 rows)
```

5、删除表中数据，并查看记录。

```sql
delete from tb1 where col_new='00000000-0000-0000-0000-000000000000'; 
select * from tb1 order by col_new; 
```

结果展示为：

```sql
               col_new
--------------------------------------
 12345678-1234-1234-1234-012345678912
 aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa
(2 rows)
```

