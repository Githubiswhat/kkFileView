# PURGE SNAPSHOT

## 功能描述

删除快照。

## 注意事项

当快照选用增量存储方式时，各个快照中具有依赖关系。删除快照需要按照依赖顺序进行删除。

## 语法格式

```
PURGE SNAPSHOT <qualified_name> @ <version | ident | sconst>;
```

## 参数说明

- **qualified_name**

  创建snapshot的名称。

  取值范围：字符串，需要符合标识符命名规则。

- **version**

  (可省略)snapshot的版本号，当省略设置。系统会自动顺延编号。

  取值范围：字符串，数字编号配合分隔符。

## 示例

1、创建一个快照。

```
create snapshot s1@1.0 comment is 'first version' as select * from t1;
```

2、删除一个快照。

```
purge snapshot s1@1.0;
```

## 相关链接

[SNAPSHOT](SNAPSHOT.md)