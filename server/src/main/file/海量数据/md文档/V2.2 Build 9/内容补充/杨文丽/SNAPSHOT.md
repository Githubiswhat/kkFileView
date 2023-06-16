# SNAPSHOT<a name="ZH-CN_TOPIC_0000001163839469"></a>

## 功能描述<a name="section1590014447254"></a>

针对多用户情况下，对数据进行统一的版本控制。

## 注意事项<a name="section1427744717250"></a>

-   当快照选用增量存储方式时，各个快照中具有依赖关系。删除快照需要按照依赖顺序进行删除。
-   snapshot特性用于团队不同成员间维护数据，涉及管理员和普通用户之间的数据转写。所以在私有用户、三权分立\(enableSeparationOfDuty=ON\)等状态下，数据库不支持snapshot功能特性。
-   当需要稳定可用的快照用于AI训练等任务时，用户需要将快照发布。
-   与本特性相关的GUC参数有：
    -   [db4ai\_snapshot\_mode](AI特性-GUC.md#db4ai_snapshot_mode)：快照存储模型分为MSS和CSS两种。
    -   [db4ai\_snapshot\_version\_delimiter](AI特性-GUC.md#db4ai_snapshot_version_delimiter)：用于设定版本分隔符，默认为“@”。
    -   [db4ai\_snapshot\_version\_separator](AI特性-GUC.md#db4ai_snapshot_version_separator)：用于设定子版本分隔符，默认为“.”。

## 语法格式<a name="section1452716494253"></a>

- 创建快照。

  可以采用“CREATE SNAPSHOT … AS”以及“CREATE SNAPSHOT … FROM”语句创建数据表快照。

  - CREATE SNAPSHOT AS

    ```
    CREATE SNAPSHOT <qualified_name> [@ <version | ident | sconst>]
        [COMMENT IS <sconst>}
        AS query;
    ```

  - CREATE SNAPSHOT FROM

    ```
    CREATE SNAPSHOT <qualified_name> [@ <version | ident | sconst>]
        FROM @ <version | ident | sconst>
        [COMMENT IS <sconst>}
            USING (
            { INSERT [INTO SNAPSHOT] …
              | UPDATE [SNAPSHOT] [AS <alias>] SET … [FROM …] [WHERE …]
              | DELETE [FROM SNAPSHOT] [AS <alias>] [USING …] [WHERE …]
              | ALTER [SNAPSHOT] { ADD … | DROP … } [, …]
          } [; …]
        );
    ```

- 删除快照。

  PURGE SNAPSHOT

  ```
  PURGE SNAPSHOT <qualified_name> @ <version | ident | sconst>;
  ```

- 快照采样。

  SAMPLE SNAPSHOT

  ```
  SAMPLE SNAPSHOT <qualified_name> @ <version | ident | sconst>
      [STRATIFY BY attr_list]
      { AS <label> AT RATIO <num> [COMMENT IS <comment>] } [, …]
  ```

- 快照发布。

  PUBLISH SNAPSHOT

  ```
  PUBLISH SNAPSHOT <qualified_name> @ <version | ident | sconst>;
  ```

- 快照存档。

  ARCHIVE SNAPSHOT

  ```
  ARCHIVE SNAPSHOT <qualified_name> @ <version | ident | sconst>;
  ```


## 参数说明<a name="section319555514251"></a>

-   qualified\_name

    创建snapshot的名称。

    取值范围：字符串，需要符合标识符命名规则。

-   version

    \(可省略\)snapshot的版本号，当省略设置。系统会自动顺延编号。

    取值范围：字符串，数字编号配合分隔符。

## 示例<a name="section3170957142519"></a>

1、创建测试表。

```
create table t1(id int,name text);
```

2、创建新快照s1。

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

4、删除一个快照。

```
purge snapshot s1@1.0;
```

5、快照采样。

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

6、发布一个快照。

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

7、存档快照。

```
archive snapshot s1@3.0;
```

## 相关链接<a name="section2051314595253"></a>

无。

