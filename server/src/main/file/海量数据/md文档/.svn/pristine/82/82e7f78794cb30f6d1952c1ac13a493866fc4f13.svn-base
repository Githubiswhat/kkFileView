# IP白名单查询与更新函数

## 功能描述

Vastbase G100支持IP白名单查询和更新函数，可以查询和更新pg_hba.conf文件中的配置条目。

- 调用read_hba_config函数，查询pg_hba.conf文件中配置条目信息；
- 调用modify_hba_config函数，增加、删除、修改pg_hba.conf文件中的配置。

## 语法格式

- IP白名单查询函数：

  ```sql
  SELECT * FROM read_hba_config();
  ```

- IP白名单更新函数：

  ```sql
  SELECT * FROM modify_hba_config(1,'new_item'::cstring);
  SELECT * FROM modify_hba_config(2,item_id);
  SELECT * FROM modify_hba_config(3,item_id,'new_item');
  ```

## 参数说明

- **1/2/3**

  操作标识符。

  取值范围：

  - 1，新增操作的标识符，表示使用函数为pg_hba.conf文件中新增配置。
  - 2，删除操作的标识符，表示使用函数删除指定的pg_hba.conf文件配置。
  - 3，修改操作的标识符，表示使用函数修改指定的pg_hba.conf文件配置。

- **new_item**

  当操作标识符为2时，new_item代表pg_hba.conf文件中新增的内容；操作标识符为3时，new_item代表修改后的配置内容。

  new_item格式需符合pg_hba.conf中配置要求，即满足如下形式：

  ```shell
  TYPE   DATABASE       USER       ADDRESS       METHOD
  ```

- **item_id**

  配置条目ID，即配置条目位于pg_hba.conf文件中的行数。

## 注意事项

只有系统管理员才能执行read_hba_config和modify_hba_config函数。

## 示例

1、查询pg_hba.conf文件中配置。

```sql
SELECT * FROM read_hba_config();
```

查询结果为：

```sql
 id | type  | database | user |      ip      | method
----+-------+----------+------+--------------+--------
 89 | local | all      | all  |              | trust
 91 | host  | all      | all  | 127.0.0.1/32 | trust
 93 | host  | all      | all  | ::1/128      | trust
 99 | host  | all      | all  | 0.0.0.0/0    | md5
(4 rows)
```

2、修改pg_hba.conf文件中配置。

```sql
SELECT * FROM modify_hba_config(3,89,'host all all 172.16.105.58/22 md5');
```

修改成功，回显为：

```sql
 modify_hba_config
-------------------
 t
(1 row)
```

3、删除pg_hba.conf文件中配置。

```sql
SELECT * FROM modify_hba_config(2,89);
```

删除成功，回显为：

```sql
 modify_hba_config
-------------------
 t
(1 row)
```

4、新增pg_hba.conf文件中配置并查询。

```sql
SELECT * FROM modify_hba_config(1,'local all all  trust'::cstring);
SELECT * FROM read_hba_config();
```

查询当前配置文件内容：

```sql
 id | type  | database | user |      ip      | method
----+-------+----------+------+--------------+--------
 90 | host  | all      | all  | 127.0.0.1/32 | trust
 92 | host  | all      | all  | ::1/128      | trust
 98 | host  | all      | all  | 0.0.0.0/0    | md5
 99 | local | all      | all  |              | trust
(4 rows)
```

