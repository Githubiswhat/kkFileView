# CREATE SNAPSHOT AS

## 功能描述

根据查询结果创建新快照。

## 注意事项

无

## 语法格式

```
CREATE SNAPSHOT <qualified_name> [@ <version | ident | sconst>]
    [COMMENT IS <sconst>}
    AS query;
```

## 参数说明

- **qualified_name**

  创建snapshot的名称。

  取值范围：字符串，需要符合标识符命名规则。

- **version**

  (可省略)snapshot的版本号，当省略设置。系统会自动顺延编号。

  取值范围：字符串，数字编号配合分隔符。

## 示例

1、创建测试表

```
create table test(id int,name text);
```

2、根据查询结果创建新快照。

```
CREATE SNAPSHOT qn1 as select * from test;
```

返回结果为：

```
 schema |   name
--------+-----------
 public | qn1@1.0.0
(1 row)
```

## 相关链接

[SNAPSHOT](SNAPSHOT.md)