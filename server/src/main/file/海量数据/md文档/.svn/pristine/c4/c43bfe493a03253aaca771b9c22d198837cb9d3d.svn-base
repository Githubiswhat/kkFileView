##### DDL语法一览表

**功能描述**

DDL（Data Definition Language数据定义语言），用于定义或修改数据库中的对象。如：表、索引、视图等。

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>    
>
> Vastbase不支持数据库主节点不完整时进行DDL操作。例如：Vastbase中有1个数据库主节点故障时执行新建数据库、表等操作都会失败。

**定义客户端加密主密钥**

客户端加密主密钥主要用于密态数据库特性，用来加密列加密密钥(cek)。客户端加密主密钥定义主要包括创建客户端加密主密钥以及删除客户端加密主密钥。所涉及的SQL语句，请参考下表：

客户端加密主密钥定义相关SQL

| 功能                 | 相关SQL                  |
| :------------------- | :----------------------- |
| 创建客户端加密主密钥 | [CREATE CLIENT MASTER KEY](CREATE CLIENT MASTER KEY.md) |
| 删除客户端加密主密钥 | [DROP CLIENT MASTER KEY](DROP CLIENT MASTER KEY.md)   |

**定义列加密密钥**

列加密密钥主要用于密态数据库特性中，用来加密数据。列加密密钥定义主要包括创建列加密密钥以及删除列加密密钥。所涉及的SQL语句，请参考下表：

列加密密钥定义相关SQL

| 功能           | 相关SQL                      |
| :------------- | :--------------------------- |
| 创建列加密密钥 | [CREATE COLUMN ENCRYPTION KEY](CREATE COLUMN ENCRYPTION KEY.md) |
| 删列加密密钥   | [DROP COLUMN ENCRYPTION KEY](DROP COLUMN ENCRYPTION KEY.md)   |

**定义数据库**

数据库是组织、存储和管理数据的仓库，而数据库定义主要包括：创建数据库、修改数据库属性，以及删除数据库。所涉及的SQL语句，请参考下表：

数据库定义相关SQL

| 功能           | 相关SQL         |
| -------------- | --------------- |
| 创建数据库     | [CREATE DATABASE](CREATE DATABASE.md) |
| 修改数据库属性 | [ALTER DATABASE](ALTER DATABASE.md) |
| 删除数据库     | [DROP DATABASE](DROP DATABASE.md)   |

 

**定义模式**

模式是一组数据库对象的集合，主要用于控制对数据库对象的访问。所涉及的SQL语句，请参考下表：

模式定义相关SQL

| 功能         | 相关SQL       |
| ------------ | ------------- |
| 创建模式     | [CREATE SCHEMA](CREATE SCHEMA.md) |
| 修改模式属性 | [ALTER SCHEMA](ALTER SCHEMA.md)  |
| 删除模式     | [DROP SCHEMA](DROP SCHEMA.md)   |

 

**定义表空间**

表空间用于管理数据对象，与磁盘上的一个目录对应。所涉及的SQL语句，请参考下表：

表空间定义相关SQL

| 功能           | 相关SQL           |
| -------------- | ----------------- |
| 创建表空间     | [CREATE TABLESPACE](CREATE TABLESPACE.md) |
| 修改表空间属性 | [ALTER TABLESPACE](ALTER TABLESPACE.md)  |
| 删除表空间     | [DROP TABLESPACE](DROP TABLESPACE.md)   |

 

**定义表**

表是数据库中的一种特殊数据结构，用于存储数据对象以及对象之间的关系。所涉及的SQL语句，请参考下表：

表定义相关SQL

| 功能       | 相关SQL      |
| ---------- | ------------ |
| 创建表     | [CREATE TABLE](CREATE TABLE.md) |
| 修改表属性 | [ALTER TABLE](ALTER TABLE.md)  |
| 删除表     | [DROP TABLE](DROP TABLE.md)   |

 

**定义分区表**

分区表是一种逻辑表，数据是由普通表存储的，主要用于提升查询性能。所涉及的SQL语句，请参考下表：

分区表定义相关SQL

| 功能           | 相关SQL                |
| -------------- | ---------------------- |
| 创建分区表     | [CREATE TABLE PARTITION](CREATE TABLE PARTITION.md) |
| 创建分区       | [ALTER TABLE PARTITION](ALTER TABLE PARTITION.md)  |
| 修改分区表属性 | [ALTER TABLE PARTITION](ALTER TABLE PARTITION.md)  |
| 删除分区       | [ALTER TABLE PARTITION](ALTER TABLE PARTITION.md)  |
| 删除分区表     | [DROP TABLE](DROP TABLE.md)             |

 

**定义索引**

索引是对数据库表中一列或多列的值进行排序的一种结构，使用索引可快速访问数据库表中的特定信息。所涉及的SQL语句，请参考下表：

索引定义相关SQL

| 功能         | 相关SQL      |
| ------------ | ------------ |
| 创建索引     | [CREATE INDEX](CREATE INDEX.md) |
| 修改索引属性 | [ALTER INDEX](ALTER INDEX.md)  |
| 删除索引     | [DROP INDEX](DROP INDEX.md)   |
| 重建索引     | [REINDEX](REINDEX.md)      |

 

**定义存储过程**

存储过程是一组为了完成特定功能的SQL语句集，经编译后存储在数据库中，用户通过指定存储过程的名称并给出参数（如果该存储过程带有参数）来执行它。所涉及的SQL语句，请参考下表：

存储过程定义相关SQL

| 功能         | 相关SQL          |
| ------------ | ---------------- |
| 创建存储过程 | [CREATE PROCEDURE](CREATE PROCEDURE.md) |
| 删除存储过程 | [DROP PROCEDURE](DROP PROCEDURE.md)   |

 

**定义函数**

在Vastbase中，它和存储过程类似，也是一组SQL语句集，使用上没有差别。所涉及的SQL语句，请参考下表：

函数定义相关SQL

| 功能         | 相关SQL         |
| ------------ | --------------- |
| 创建函数     | [CREATE FUNCTION](CREATE FUNCTION.md) |
| 修改函数属性 | [ALTER FUNCTION](ALTER FUNCTION.md)  |
| 删除函数     | [DROP FUNCTION](DROP FUNCTION.md)   |

 

**定义视图**

视图是从一个或几个基本表中导出的虚表，可用于控制用户对数据访问，请参考下表：

视图定义相关SQL

| 功能     | 相关SQL     |
| -------- | ----------- |
| 创建视图 | [CREATE VIEW](CREATE VIEW.md) |
| 删除视图 | [DROP VIEW](DROP VIEW.md)  |

 **定义包**

包是模块化的思想，由包头（package specification）和包体(package body)组成，用来分类管理存储过程和函数，类似于Java、C++等语言中的类，请参考下表：

包定义相关SQL

| 功能       | 相关SQL        |
| :--------- | :------------- |
| 创建包     | [CREATE PACKAGE](CREATE PACKAGE.md) |
| 删除包     | [DROP PACKAGE](DROP PACKAGE.md)   |
| 修改包属性 | [ALTER PACKAGE](ALTER PACKAGE.md)  |



**定义游标**

为了处理SQL语句，存储过程进程分配一段内存区域来保存上下文联系。游标是指向上下文区域的句柄或指针。借助游标，存储过程可以控制上下文区域的变化，请参考下表：

游标定义相关SQL

| 功能             | 相关SQL |
| ---------------- | ------- |
| 创建游标         | [CURSOR](CURSOR.md)  |
| 移动游标         | [MOVE](MOVE.md)    |
| 从游标中提取数据 | [FETCH](FETCH.md)   |
| 关闭游标         | [CLOSE](CLOSE.md)   |

 **定义聚合函数**

聚合函数定义相关SQL

| 功能                 | 相关SQL          |
| :------------------- | :--------------- |
| 创建一个新的聚合函数 | [CREATE AGGREGATE](CREATE AGGREGATE.md) |
| 修改聚合函数         | [ALTER AGGREGATE](ALTER AGGREGATE)  |
| 删除聚合函数         | [DROP AGGREGATE](DROP AGGREGATE.md)   |

**定义数据类型转换**

数据类型定义相关SQL

| 功能                               | 相关SQL     |
| :--------------------------------- | :---------- |
| 创建一个新的用户自定义数据类型转换 | [CREATE CAST](CREATE CAST.md) |
| 删除用户自定义数据类型转换         | [DROP CAST](DROP CAST.md)   |

**定义插件扩展**

插件扩展定义相关SQL

| 功能                 | 相关SQL          |
| :------------------- | :--------------- |
| 创建一个新的插件扩展 | [CREATE EXTENSION](CREATE EXTENSION.md) |
| 修改插件扩展         | [ALTER EXTENSION](ALTER EXTENSION.md)  |
| 删除插件扩展         | [DROP EXTENSION](DROP EXTENSION.md)   |

**定义操作符**

操作符定义相关SQL

| 功能               | 相关SQL         |
| :----------------- | :-------------- |
| 创建一个新的操作符 | [CREATE OPERATOR](CREATE OPERATOR.md) |
| 修改操作符         | [ALTER OPERATOR](ALTER OPERATOR.md)  |
| 删除操作符         | [DROP OPERATOR](DROP OPERATOR.md)   |

**定义过程语言**

过程语言定义相关SQL

| 功能                 | 相关SQL         |
| :------------------- | :-------------- |
| 创建一个新的过程语言 | [CREATE LANGUAGE](CREATE LANGUAGE.md) |
| 修改过程语言         | [ALTER LANGUAGE](ALTER LANGUAGE.md)  |
| 删除过程语言         | [DROP LANGUAGE](DROP LANGUAGE.md)   |

**定义数据类型**

数据类型定义相关SQL

| 功能                 | 相关SQL     |
| :------------------- | :---------- |
| 创建一个新的数据类型 | [CREATE TYPE](CREATE TYPE.md) |
| 修改数据类型         | [ALTER TYPE](ALTER TYPE.md)  |
| 删除数据类型         | [DROP TYPE](DROP TYPE.md)   |
