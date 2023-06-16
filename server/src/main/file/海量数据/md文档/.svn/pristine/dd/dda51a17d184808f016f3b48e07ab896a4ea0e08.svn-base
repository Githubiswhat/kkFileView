# TRY CATCH

## 功能描述

Vastbase G100在SQL Server兼容模式下，支持使用TRY CATCH捕捉异常。Transact-SQL语句组可以包含在 TRY 块中。 如果 TRY 块内部发生错误，则会将控制传递给 CATCH 块中包含的另一个语句组。

## 语法格式

```sql
BEGIN TRY; --进入TRY块
TRY块SQL
END TRY BEGIN CATCH; --离开TRY块进入CATCH块
CATCH块SQL
END CATCH; --离开CATCH块
```

## 注意事项

- 该功能仅在数据库兼容模式为SQL Server时能够使用（即创建DB时DBCOMPATIBILITY='MSSQL'），在其他数据库兼容模式下不能使用该特性。
- 仅支持按照BEGIN TRY、END TRY BEGIN CATCH、END CATCH 的流程进行，不支持中间插入任何与事务相关的语句（如BEGIN、COMMIT、SAVEPOINT）。
- 不支持嵌套。
- 不支持在存储过程/函数中使用。
- 如果 TRY 块所包含的代码中有错误，则会将控制传递给相关联的 CATCH 块的第一个语句。当 CATCH 块中的代码完成时，会将控制传递给紧跟在 END CATCH 语句之后的语句。
- 由 CATCH 块捕获的错误不会返回到调用应用程序。如果错误消息的任何部分都必须返回到应用程序，则 CATCH 块中的代码必须使用 SELECT 结果集或 RAISERROR 和 PRINT 语句之类的机制执行此操作。

## 示例

1、创建兼容模式为SQL Server的库db_sqlserver，并进入。

```sql
CREATE DATABASE db_sqlserver DBCOMPATIBILITY='MSSQL';
\c db_sqlserver
```

2、创建测试表并插入数据。

```sql
CREATE TABLE products_1132917(
ProductID int,
Name varchar,
Price int,
Quantity int);
INSERT INTO products_1132917 values(101, 'Laptop', 15000, 100);
INSERT INTO products_1132917 values(102, 'Desktop', 20000, 150);
INSERT INTO products_1132917 values(104, 'Mobile', 3000, 200);
INSERT INTO products_1132917 values(105, 'Tablet', 4000, 250);
```

3、在表上创建唯一索引。

```sql
CREATE UNIQUE INDEX idx_1132917 on products_1132917(ProductID);
```

4、使用TRY块中插入失败，进入到CATCH块中执行插入成功。

```sql
BEGIN TRY;
INSERT INTO products_1132917 values(105, 'Airpods', 1000, 300);
END TRY BEGIN CATCH;
INSERT INTO products_1132917 values(107, 'Airpods', 1000, 300);
END CATCH;
```

5、查看表中插入数据。

```sql
SELECT * FROM products_1132917;
```

结果返回如下：

```sql
 productid |  name   | price | quantity
-----------+---------+-------+----------
       101 | Laptop  | 15000 |      100
       102 | Desktop | 20000 |      150
       104 | Mobile  |  3000 |      200
       105 | Tablet  |  4000 |      250
       107 | Airpods |  1000 |      300
(5 rows)
```



