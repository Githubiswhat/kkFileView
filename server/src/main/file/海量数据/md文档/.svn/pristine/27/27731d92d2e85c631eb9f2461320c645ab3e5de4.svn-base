# CREATE TABLE支持继承表

## 功能描述

Vastbase在CREATE TABLE语句中增加了对普通行存表的继承功能。新创建的表可以自动继承指定的表的所有列。父表可以是普通表，也可以是外部表。

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>  
>
> 父表上所有检查约束和费控约束都将自动被后代继承，其他类型的约束，比如：唯一约束，主键和外键约束则不会被继承。

## 语法格式

```sql
CREATE TABLE table_name[INHERITS(parent_table)[,...]]
```

## 参数说明

- **table_name**

  表名。

- **parent_table**

  要继承的父表，可以是普通表也可以是外部表。

## 注意事项

- 该功能仅对普通行存表支持，禁止分区表、ustore表、列存表、行压缩表、全局临时表或外部表使用继承功能。
- 不能对有继承关系的父表或者子表的约束做启用或禁用操作。
- 子表不能修改从父表继承的属性和约束。
- 子表存在的情况下，删除父表会报错，但是删除父表时使用cascade则可删除成功。
- 对父表的数据查询、数据修改或者模式修改的命令（SELECT、UPDATE、DELETE、大部分ALTER TABLE的变体，但是INSERT或者ALTER TABLE ...RENAME不在此范围内）默认会将子表包含在内，支持使用ONLY选项来排除子表。

  

## 示例

**前置步骤：**创建并切换至兼容模式为PG的数据库。

```
CREATE DATABASE db_pg DBCOMPATIBILITY 'PG';
\c db_pg
```

1、创建父表和子表。

```sql
create table parent(id int);
create table child1() inherits(parent);
create table child2() inherits(parent);
```

2、插入数据。

```sql
insert into parent values(1);
insert into child1 values(2);
insert into child2 values(3);
```

3、查询父表。

```sql
select * from parent;
```

返回结果为：

```sql
id
---
1
2
3
(3 rows)
```

4、使用ONLY选项查询父表。

```sql
select * from only parent;
```

返回结果为：

```sql
id
---
1
(1 rows)
```

5、给父表添加属性。

```sql
alter table parent add name text;
```

6、查询表结构。

```sql
\d+ parent
\d+ child1
```

7、修改子表中继承的属性。

```sql
alter table child1 alter column name type varchar(10);
```

返回结果报错，表示子表无法修改从父表继承的属性。

8、删除父表。

```sql
drop table parent;
```

返回结果报错，即当任何一个子表存在时，父表都不能被删除，但是可以使用cascade选项删除父表。

9、使用cascade选项删除父表。

```sql
drop table parent cascade;
```

