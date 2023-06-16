# TOP子句

## 功能描述

TOP子句用来限制查询结果集中返回的行数或百分比。在本语法中，SELECT语句可以包含其他子句，如：WHERE，JOIN，HAVING和GROUP BY等。 

## 语法格式

- 限制查询结果行数 

  ```
  SELECT TOP num column_name[,...] FROM object_name [ORDER BY column_name [DESC|ASC]];
  ```

- 限制查询结果百分比

  ```
  SELECT TOP num PERCENT column_name[,...] FROM object_name [ORDER BY column_name [DESC|ASC]];
  ```

- 包含与最后一行中的值也匹配的行

  ```
  SELECT TOP num WITH TIES column_name[,...] FROM object_name order by column_name;
  ```

## 参数说明

- **num**

  指定的返回行数或百分比数。

- **column_name**

  待查询的列名称。

- **object_name**

  待查询的对象名称。

- **DESC|ASC**

  排序规则，与ORDER BY 语句结合使用。
  
  默认按照升序（ASC）对记录进行排序，使用 DESC 关键字可以按照降序对记录进行排序。

## 注意事项

- 本特性仅在数据库兼容模式为SQL Server时能够使用（即创建DB时DBCOMPATIBILITY='MSSQL'），在其他数据库兼容模式下不能使用该特性。

- WITH TIES用于返回更多行，其值与有限结果集合的最后一行匹配。因此可能会导致返回结果行数多于在表达式中指定的行数。
- WITH TIES 无order by子句时会报错。
- PERCENT后只能跟正数。

## 示例

1、创建测试库，并指定兼容性为SQL Server。

```
create database sqlserver dbcompatibility='MSSQL';
\c sqlserver
show sql_compatibility;
```

结果显示为：

```
 sql_compatibility
-------------------
 MSSQL
(1 row)
```

2、创建测试表并插入数据。

```
create table emp(empno int primary key,ename nvarchar(10),sal int);
insert into emp values (7369,'smith',2800);
insert into emp values (7499,'allen',1500);
insert into emp values (7521,'ward',3500);
insert into emp values (7566,'jones',5000);
insert into emp values (7654,'martin',1600);
insert into emp values (7698,'blake',3000);
```

3、查询指定列的前三条数据。

```
select top 3 * from emp order by sal DESC;
```

结果显示为：

```
 empno | ename | sal
-------+-------+------
  7566 | jones | 5000
  7521 | ward  | 3500
  7698 | blake | 3000
(3 rows)
```

4、查询前30%的指定列数据。

```
select top 30 percent empno,ename from emp;
```

结果显示为：

```
 empno | ename
-------+-------
  7369 | smith
  7499 | allen
(2 rows)
```

