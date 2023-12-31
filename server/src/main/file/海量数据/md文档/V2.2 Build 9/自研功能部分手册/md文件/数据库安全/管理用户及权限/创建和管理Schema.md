##### 管理Schema

**功能描述**

Schema又称作模式。通过管理Schema，允许多个用户使用同一数据库而不相互干扰，可以将数据库对象组织成易于管理的逻辑组，同时便于将第三方应用添加到相应的Schema下而不引起冲突。

每个数据库包含一个或多个Schema。数据库中的每个Schema包含表和其他类型的对象。数据库创建初始，默认具有一个名为public的Schema，且所有用户都拥有此Schema的权限。可以通过Schema分组数据库对象。Schema类似于操作系统目录，但Schema不能嵌套。

相同的数据库对象名称可以应用在同一数据库的不同Schema中，而没有冲突。例如，a_schema和b_schema都可以包含名为mytable的表。具有所需权限的用户可以访问数据库的多个Schema中的对象。

在数据库创建用户时，系统会自动帮助用户创建一个同名Schema。

数据库对象是创建在数据库搜索路径中的第一个Schema内的。有关默认情况下的第一个Schema情况及如何变更Schema顺序等更多信息，请参见搜索路径。

**创建、修改和删除Schema**

- 要创建Schema，请参考[CREATE SCHEMA](CREATE SCHEMA)。默认初始用户和系统管理员可以创建Schema，其他用户需要具备数据库的CREATE权限才可以在该数据库中创建Schema，赋权方式请参考章节“GRANT”中将数据库的访问权限赋予指定的用户或角色中的语法。

- 要更改Schema名称或者所有者，请参考[ALTER SCHEMA](ALTER SCHEMA)。Schema所有者可以更改Schema。

- 要删除Schema及其对象，请参考[DROP SCHEMA](DROP SCHEMA)。Schema所有者可以删除Schema。

- 要在Schema内创建表，请以schema_name.table_name格式创建表。不指定schema_name时，对象默认创建到搜索路径中的第一个Schema内。

- 要查看Schema所有者，请对系统表PG_NAMESPACE和PG_USER执行如下关联查询。语句中的schema_name请替换为实际要查找的Schema名称。

```
SELECT s.nspname,u.usename AS nspowner FROM pg_namespace s, pg_user u WHERE nspname='schema_name' AND s.nspowner = u.usesysid;
```

- 要查看所有Schema的列表，请查询PG_NAMESPACE系统表。

```
SELECT * FROM pg_namespace;
```

- 要查看属于某Schema下的表列表，请查询系统视图PG_TABLES。例如，以下查询会返回Schema PG_CATALOG中的表列表。

```
SELECT distinct(tablename),schemaname from pg_tables where schemaname = 'pg_catalog';
```

**搜索路径**

搜索路径定义在search_path参数中，参数取值形式为采用逗号分隔的Schema名称列表。如果创建对象时未指定目标Schema，则将该对象会被添加到搜索路径中列出的第一个Schema中。当不同Schema中存在同名的对象时，查询对象未指定Schema的情况下，将从搜索路径中包含该对象的第一个Schema中返回对象。

- 要查看当前搜索路径，请参考[SHOW](SHOW)。

```
SHOW SEARCH_PATH; 
 search_path 
---------------- 
 "$user",public 
(1 row)
```

search_path参数的默认值为："$user"，public。$user表示与当前会话用户名同名的Schema名，如果这样的模式不存在，$user将被忽略。所以默认情况下，用户连接数据库后，如果数据库下存在同名Schema，则对象会添加到同名Schema下，否则对象被添加到Public Schema下。

- 要更改当前会话的默认Schema，请使用SET命令。

执行如下命令将搜索路径设置为myschema、public，首先搜索myschema。

```
SET SEARCH_PATH TO myschema, public;
```

