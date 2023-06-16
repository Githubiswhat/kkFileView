# ACLITEM权限类型

## 功能描述

Vastbase中对象的访问权限使用aclitem类型进行记录。

## 语法格式

```
rolename=privileges/rolegrant
```

## 参数说明

- rolename：被授权者。

- privileges：授予的权限，权限列表如下所示：
  - r：SELECT
  - w：UPDATE
  - a：INSERT
  - d：DELETE
  - D：TRUNCATE
  - x：REFERENCES
  - t：TRIGGER
  - X：EXECUTE
  - U：USAGE
  - C：CREATE
  - c：CONNECT
  - T：TEMPORARY
  - arwdDxt：ALL PRIVILEGES
  - *：授予优先权选项

- rolegrant：授权者。

## 示例

系统表中记录了对象的访问权限，如表1所示。

表1系统表中的ACL列

<table>
    <tr>
    <th>系统表</th>
    <th>字段名</th>
    </tr>
    <tr>
    <td>pg_database</td>
    <td>datacl</td>
    </tr>
    <tr>
    <td>pg_tablespace</td>
    <td>spcacl</td>
    </tr>
    <tr>
    <td>pg_class</td>
    <td>relacl</td>
    </tr>
    <tr>
    <td>pg_attribute</td>
    <td>attacl</td>
    </tr>
    <tr>
    <td>pg_type</td>
    <td>typacl</td>
    </tr>
    <tr>
    <td>pg_proc</td>
    <td>proacl</td>
    </tr>
    <tr>
    <td>pg_language</td>
    <td>lanacl</td>
    </tr>
</table>

查询pg_database中记录的aclitem类型字段

```
select datname,datacl from pg_database;
```

结果显示如下：

```
  datname  |               datacl
-----------+-------------------------------------
 template1 | {=c/vastbase,vastbase=CTc/vastbase}
 db_oracle |
 my_test   |
 template0 | {=c/vastbase,vastbase=CTc/vastbase}
 vastbase  |
 postgres  |
(6 rows)
```

