# ALTER FOREIGN TABLE FOR HDFS

## 功能描述

更改外部表的定义。

## 注意事项

无

## 语法格式

- 修改外部表属主。

  ```
  ALTER FOREIGN TABLE [ IF EXISTS ] tablename
      OWNER TO new_owner;
  ```

- 修改外部表。

  ```
  ALTER FOREIGN TABLE [ IF EXISTS ] tablename
      action [, ... ];
  ```

- 修改外部表约束。

  ```
  ALTER FOREIGN TABLE [ IF EXISTS ] tablename
      ADD [CONSTRAINT constraint_name]
  {PRIMARY KEY | UNIQUE} (column_name)
  [NOT ENFORCED [ENABLE QUERY OPTIMIZATION | DISABLE QUERY OPTIMIZATION] | ENFORCED];
  ```

- 删除外部表约束。

  ```
  ALTER FOREIGN TABLE [ IF EXISTS ] tablename
      DROP CONSTRAINT constraint_name ;
  ```

## 参数说明

- **tablename**

  需要修改的外表名称。

  取值范围：已存在的外部表名。

- **new_owner**

  外部表新属主。

  取值范围：已存在用户。

- **action**

  对外部表的修改操作。

  可选值：

  - ALTER [ COLUMN ] column_name [ SET DATA ] TYPE data_type
  - ALTER [ COLUMN ] column_name { SET | DROP } NOT NULL
  - ALTER [ COLUMN ] column_name SET STATISTICS [PERCENT] integer

- **constraint_name**

  约束名

  取值范围：字符串，要符合标识符的命名规范。

- **PRIMARY KEY | UNIQUE**

  主键|唯一键值

  主键用于唯一表示表中的每一条数据。

  唯一键值用于避免添加重复数据, 保证某一个字段的值不重复。

- **NOT ENFORCED [ENABLE QUERY OPTIMIZATION | DISABLE QUERY OPTIMIZATION] | ENFORCED**

  NOT ENFORCED ：声明字段不做强检验。

  - ENABLE QUERY OPTIMIZATION：启用查询优化。
  - DISABLE QUERY OPTIMIZATION：禁用查询优化。

  ENFORCED：声明强检验。

## 示例

1、创建外部表。

```
CREATE FOREIGN TABLE test1(id int,name varchar(10),date date,time time);
```

2、修改外部表属主。

```
ALTER FOREIGN TABLE test OWNER TO user2;
```

3、修改外部表字段类型。

```
ALTER FOREIGN TABLE test ALTER COLUMN name SET DATA TYPE text;
```

4、修改外部表约束。

```
ALTER FOREIGN TABLE test ADD CONSTRAINT constraint1 PRIMARY KEY (id)
```

5、删除外部表约束。

```
ALTER FOREIGN TABLE test DROP CONSTRAINT constraint1 ;
```

## 相关链接

无