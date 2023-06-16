# SYS_CONTEXT函数

## 功能描述

SYS_CONTEXT函数用于查询当前时刻数据库与操作系统默认命名空间“USERENV”相关的一些参数值。

## 语法格式

```sql
SYS_CONTEXT('namespace', 'parameter' [, length ])
```

## 参数说明

- **namespace**

  命名空间名称。

  目前只支持USERENV命名空间，因此namespace只有一个可用取值USERENV。

- **parameter**

  用户查询的参数。目前支持如下参数：

  | 兼容参数                  | 描述                                                         | 返回结果（示例）              |
  | ------------------------- | ------------------------------------------------------------ | ----------------------------- |
  | ACTION                    | 通过dbms_application_info.set_action可以设置用户行动。       | 用户自定义                    |
  | CLIENT_INFO               | 通过dbms_application_info.set_client_info可以设置用户信息。  | 用户自定义                    |
  | CLIENT_PROGRAM_NAME       | 用于数据库会话的程序名称，Vastbase返回vsql。                 | vsql                          |
  | CURRENT_EDITION_ID        | 当前版本的标识符。                                           | 2.2.11                        |
  | CURRENT_EDITION_NAME      | 当前版本的名字，即vb_version()返回值。                       | 以实际为准，此处不展示        |
  | CURRENT_SCHEMA            | 当前活动默认schema的名称。                                   | "$user",public                |
  | CURRENT_SCHEMAID          | 当前活动默认schema的标识符，可以查询pg_namespace的oid。      | 0                             |
  | CURRENT_USER              | 当前权限处于活动状态的数据库用户的名称。可能会在数据库会话期间发生变化。CURRENT_USER和SESSION_USER的返回值相等。 | vastbase                      |
  | CURRENT_USERID            | 权限当前处于活动状态的数据库用户的标识符。pg_user可查。      | 10                            |
  | DATABASE_ROLE             | 使用SYS_CONTEXT函数和USERENV命名空间的数据库角色，Vastbase数据库中即为当前用户的role。 | vastbase                      |
  | DB_NAME                   | 数据库的名称。                                               | db_oracle                     |
  | DB_UNIQUE_NAME            | 数据库的名称。                                               | db_oracle                     |
  | HOST                      | 客户端已连接到的主机名称。                                   | vdb1                          |
  | IP_ADDRESS                | 服务器IP地址，通过show local_bind_address;可查询。           | 127.0.0.1                     |
  | IS_SUPERUSER              | 当前用户是否为SUPERUSER。                                    | TRUE                          |
  | LANG                      | 当前数据库的语言环境的简短表示，由于Vastbase没有lang相关设置，用数据库LC_COLLATE 与LC_CTYPE 的取值确定lang。 | en_US                         |
  | LANGUAGE                  | 当前数据库的语言环境的详细表示，包括语言信息和字符集信息。由于Vastbase没有lang相关设置，用数据库LC_COLLATE 与LC_CTYPE的取值确定 language。 | en_US.utf8                    |
  | MODULE                    | 通过DBMS_APPLICATION_INFO包或者OCI设置的应用名称（模块）。   | 用户自定义                    |
  | NETWORK_PROTOCOL          | 用于通信的网络协议。输出固定为tcp。                          | tcp                           |
  | NLS_CALENDAR              | 当前会话的当前日历。Vastbase 无此参数，默认为空。            |                               |
  | NLS_CURRENCY              | 当前会话的货币符号(￥)，根据当前语言环境产生输出。           | $                             |
  | NLS_DATE_FORMAT           | 会话的日期格式。                                             | YEAR-MON-DAY                  |
  | NLS_DATE_LANGUAGE         | 用来表示日期的语言。                                         | English                       |
  | NLS_SORT                  | 当前的排序方式，Vastbase中只能为binary，其他排序方法暂不支持。 | BINARY                        |
  | NLS_TERRITORY             | 当前会话的所处地。                                           | CHINA                         |
  | VAST_HOME                 | Vastbase主目录的路径名。                                     | /home/vastbase/local/vastbase |
  | OS_USER                   | 启动数据库会话的客户端进行的操作系统用户名。                 | root                          |
  | PLATFORM_SLASH            | 用作平台文件路径分隔符的斜杠字符。                           | /                             |
  | SERVER_HOST               | 运行实例的数据库主机名。                                     | vdb1                          |
  | SERVICE_NAME              | 给定会话连接到的服务的名称，Vastbase不区分服务，使用数据库名。 | db_oracle                     |
  | SESSION_DEFAULT_COLLATION | 会话的默认排序规则。                                         | en_US.utf8                    |
  | SESSION_EDITION_ID        | 会话版本的标识符。Vastbase中为数字版本号。                   | 2.2.11                        |
  | SESSION_EDITION_NAME      | 会话版本的名称，即vb_version()返回值。                       | 以实际为准，此处不展示        |
  | SESSION_USER              | 会话用户（登录的用户）的名称。这可能会在数据库会话期间发生变化。 | vastbase                      |
  | SESSION_USERID            | 会话用户（登录的用户）的标识符。                             | 10                            |
  | SID                       | 会话ID。                                                     | 0                             |
  | TERMINAL                  | 当前会话的客户端的操作系统标识，返回本地会话的标识符。(与 SERVICE HOST 输出一致)。 | vdb1                          |

- **length**

  可选项，限制返回结果的最大长度。

  类型返回cstring类型，最大长度默认为256位。

## 注意事项

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。
- 目前只支持“USERENV”命名空间，因此namespace只有一个可用取值。
- 不支持用户自建命名空间。

## 示例

**前置步骤：**创建并切换至兼容模式为Oracle的数据库。

```sql
CREATE DATABASE db_oracle DBCOMPATIBILITY 'A';
\c db_oracle
```

**示例1：** 查询ACTION、CLIENT_INFO和MODULE。

1、执行如下SQL语句。

```sql
SELECT SYS_CONTEXT('USERENV', 'ACTION') ACTION ,SYS_CONTEXT('USERENV', 'CLIENT_INFO') client_info,sys_context('userenv','module') module FROM DUAL;
```

返回结果为空，如下所示：

```sql
action | client_info | module
--------+-------------+--------
        |             |
(1 row)
```

2、通过dbms_application_info.set_action改变ACTION、CLIENT_INFO和MODULE的值。

```sql
exec dbms_application_info.set_action('action2');
exec dbms_application_info.set_client_info('client2');
exec dbms_application_info.set_module('module_wang','action_2020');
```

3、再次执行查询。

```sql
SELECT SYS_CONTEXT('USERENV', 'ACTION') ACTION ,SYS_CONTEXT('USERENV', 'CLIENT_INFO') client_info,sys_context('userenv','module') module FROM DUAL;
```

返回结果为：

```sql
   action    | client_info |   module
-------------+-------------+-------------
 action_2020 | client2     | module_wang
(1 row)
```

4、执行如下SQL语句测试length参数。

```sql
SELECT SYS_CONTEXT('USERENV', 'ACTION') ACTION FROM DUAL;
```

返回结果为：

```sql
   action
-------------
 action_2020
(1 row)
```

**示例2：** 查询DB_NAME和DB_UNIQUE_NAME。

1、执行如下SQL语句查询DB_NAME。

```sql
SELECT SYS_CONTEXT('USERENV', 'DB_NAME') DB_NAME; 
```

返回结果为：

```sql
  db_name
-----------
 db_oracle
(1 row)
```

2、执行如下SQL语句查询DB_UNIQUE_NAME。

```sql
SELECT SYS_CONTEXT('USERENV', 'DB_UNIQUE_NAME') DB_NAME; 
```

返回结果为：

```sql
  db_name
-----------
 db_oracle
(1 row)
```

