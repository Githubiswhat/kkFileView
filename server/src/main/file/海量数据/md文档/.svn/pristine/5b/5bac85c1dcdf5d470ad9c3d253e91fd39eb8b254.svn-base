#### DBA_USERS

DBA_USERS视图提供所有用户的数据库的用户信息。

| 列名                        | 类型                        | 描述                                                         |
| --------------------------- | --------------------------- | ------------------------------------------------------------ |
| username                    | varchar(64)                 | 用户名                                                       |
| user_id                     | oid                         | 用户id                                                       |
| password                    | varchar(30)                 | 密码                                                         |
| account_status              | varchar(32)                 | 用户状态                                                     |
| lock_date                   | timestamp without time zone | 如果账户状态是lock，显示被锁日期                             |
| expiry_date                 | timestamp without time zone | 过期时间                                                     |
| default_tablespace          | varchar(64)                 | 数据的默认表空间                                             |
| temporary_tablespace        | varchar(30)                 | 临时表的默认表空间的名称或表空间组的名称                     |
| local_temp_tablespace       | varchar(30)                 | 用户的默认本地临时表空间                                     |
| created                     | timestamp without time zone | 仅为支持兼容性。该值始终为NULL                               |
| profile                     | varchar(30)                 | Profile名                                                    |
| initial_rsrc_consumer_group | varchar(30)                 | 仅为支持兼容性。该值始终为NULL                               |
| external_name               | varchar(4000)               | 仅为支持兼容性。该值始终为NULL                               |
| password_versions           | varchar(12)                 | 显示帐户存在的密码哈希（也称为“验证器”）的版本列表。         |
| editions_enabled            | varchar(1)                  | 指示是否已为相应用户启用版本 ( Y) 或未启用 ( N)              |
| authentication_type         | varchar(8)                  | 表示用户的认证机制                                           |
| proxy_only_connect          | varchar(1)                  | 指示用户是否可以直接连接 (N) 或该帐户是否只能由对此帐户具有代理权限的用户代理 (Y)（即，由已被授予此帐户“连接通过”权限的用户 |
| common                      | varchar(3)                  | 指示给定用户是否是普通用户：YES(普通用户)，NO(本地用户)      |
| last_login                  | timestamp with time zone    | 上次用户登录时间                                             |
| oracle_maintained           | varchar(1)                  | 表示用户是否由 Oracle 提供的脚本（例如 catalog.sql 或 catproc.sql）创建和维护 |
| inherited                   | varchar(3)                  | 指示用户定义是否继承自另一个容器 ( YES) 或不是 ( N)          |
| default_collation           | varchar(100)                | 用户架构的默认排序规则                                       |
| implicit                    | varchar(3)                  | 指示此用户是否是由隐式应用程序创建的普通用户 ( YES) 或不是 ( NO) |
| all_shard                   | varchar(3)                  | 在分片数据库中，此列中的值指示用户是否是在启用分片 DDL 的情况下创建的 |
