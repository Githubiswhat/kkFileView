# ARCHIVE SNAPSHOT

## 功能描述

存档一个快照。

## 注意事项

无

## 语法格式

```
ARCHIVE SNAPSHOT <qualified_name> @ <version | ident | sconst>;
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

2、创建新快照。

```
create snapshot s1@1.0 comment is 'first version' as select * from t1;
```

3、从现有快照创建快照修订。

```
create snapshot s1@3.0 from @1.0 comment is 'inherits from @1.0' using (INSERT VALUES(6,6), (7,7); DELETE WHERE id = 1);
```

返回结果为：

```
 schema |  name
--------+--------
 public | s1@3.0
(1 row)
```

4、发布一个快照。

```
publish snapshot s1@3.0;
```

返回结果为：

```
 schema |  name
--------+--------
 public | s1@3.0
(1 row)
```

5、存档一个快照。

```
archive snapshot s1@3.0;
```

## 相关链接

[SNAPSHOT](SNAPSHOT.md)