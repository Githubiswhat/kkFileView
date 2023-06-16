# SAMPLE SNAPSHOT

## 功能描述

快照采样。

## 注意事项

- snapshot特性用于团队不同成员间维护数据，涉及管理员和普通用户之间的数据转写。所以在私有用户、三权分立(enableSeparationOfDuty=ON)等状态下，数据库不支持snapshot功能特性。
- snapshot特性GUC参数db4ai_snapshot_mode，快照存储模型分为MSS和CSS两种；GUC参数db4ai_snapshot_version_delimiter，用于设定版本分隔符，默认为“@”;GUC参数db4ai_snapshot_version_separator，用于设定子版本分隔符，默认为“.”。

## 语法格式

```
SAMPLE SNAPSHOT <qualified_name> @ <version | ident | sconst>
    [STRATIFY BY attr_list]
    { AS <label> AT RATIO <num> [COMMENT IS <comment>] } [, …]
```

## 参数说明

- qualified_name

  创建snapshot的名称。

  取值范围：字符串，需要符合标识符命名规则。

- version

  (可省略)snapshot的版本号，当省略设置。系统会自动顺延编号。

  取值范围：字符串，数字编号配合分隔符。

## 示例

1、创建测试表。

```
create table test(id int,name text);
```

2、创建快照。

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

4、快照采样。

```
 sample snapshot s1@3.0 stratify by id as nick at ratio .5;
```

返回结果为：

```
 schema |    name
--------+------------
 public | s1nick@3.0
(1 row)
```

## 相关链接

[SNAPSHOT](SNAPSHOT.md)
