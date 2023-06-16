#### SHOW CREATE TABLE 语法

**功能描述**

显示用于创建指定表的CREATE TABLE语句。

**语法格式**

```
SHOW CREATE TABLE [schema_name.]table_name;
```

**参数说明**

- schema_name：模式的名称。

- table_name ：指定表的名称。

**注意事项**

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

- 不支持显示分区表分区信息。

- 不支持临时表、外部表。

- 不支持显示表空间信息。

**兼容性**

完全兼容。

**示例**

1、创建兼容MySQL的库db_mysql，并进入。

```
CREATE DATABASE db_mysql dbcompatibility='B';
\c db_mysql
```

2、创建测试表test_myssql。

```
CREATE TABLE test_mysql(c1 int);
```

3、验证结果。

```
SHOW CREATE TABLE public.test_mysql;
```

当结果显示如下信息，则表示验证完成。

```
    Table    |         Create Table
-------------------+----------------------------------------------
 public.test_mysql |  CREATE TABLE test_mysql(c1 integer(32)  );
(1 row)
```

