#### V$INSTANCE

V$INSTANCE描述当前系统实例信息。

| 列名             | 类型                     | 描述                       |
| ---------------- | ------------------------ | -------------------------- |
| instance_number  | integer                  | 实例编号                   |
| instance_name    | name                     | 实例名称                   |
| host_name        | text                     | 当前实例所在主机名称       |
| version          | text                     | 数据库版本信息             |
| startup_time     | timestamp with time zone | 数据库启动时间             |
| status           | text                     | 数据库状态                 |
| parallel         | text                     | 指明数据库是否在集群中     |
| thread#          | integer                  | 数据库打开的重做线程数     |
| archiver         | text                     | 自动归档信息               |
| log_switch_wait  | text                     | 记录切换日志的等待事件     |
| logins           | text                     | 指明数据库是否处于受限状态 |
| shutdown_pending | text                     | 是否正在关闭               |
| database_status  | text                     | 当前数据库状态             |
| instance_role    | text                     | 当前实例角色信息           |
| active_state     | text                     | 当前实例的活动状态         |
| blocked          | text                     | 当前实例是否阻塞           |
| edition          | text                     | 版本信息                   |
