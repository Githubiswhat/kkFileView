#### V$DATABASE

V$DATABASE包含当前数据库的基本信息。

| 列名                         | 类型                     | 描述                                                         |
| ---------------------------- | ------------------------ | ------------------------------------------------------------ |
| dbid                         | oid                      | 数据库唯一标识符                                             |
| name                         | name                     | 数据库名称                                                   |
| created                      | timestamp with time zone | 数据库的创建时间                                             |
| resetlogs_change#            | integer                  | 打开的重置日志中的系统更改号                                 |
| resetlogs_time               | timestamp with time zone | 打开的重置日志的时间戳                                       |
| prior_resetlogs_change#      | integer                  | 先前的重置日志中的版本号                                     |
| prior_resetlogs_time         | text                     | 先前重置日志的时间                                           |
| log_mode                     | text                     | 归档日志模式                                                 |
| checkpoint_change#           | integer                  | 上一个scn检查点                                              |
| archive_change#              | integer                  | 数据库强制归档scn                                            |
| controlfile_type             | text                     | 控制文件类型                                                 |
| controlfile_created          | text                     | 控制文件的创建日期                                           |
| controlfile_sequence#        | integer                  | 控制文件序列号由控制文件事务增加                             |
| controlfile_change#          | integer                  | 备份控制文件中的最后一个scn；如果控制文件不是备份，则为null  |
| controlfile_time             | text                     | 备份控制文件中的最后一个时间戳；如果控制文件不是备份，则为null |
| open_resetlogs               | integer                  | 表明下一个打开的数据库是允许还是需要resetlogs选项            |
| version_time                 | text                     | 版本时间                                                     |
| open_mode                    | text                     | 数据库打开模式                                               |
| protection_mode              | text                     | 当前数据库的保护模式                                         |
| protection_level             | text                     | 当前数据库的保护级别                                         |
| remote_archive               | text                     | remote_archive_enable初始化参数的值                          |
| activation#                  | integer                  | 分配给数据库实例的编号                                       |
| switchover#                  | integer                  | 分配给数据库切换的编号                                       |
| database_role                | text                     | 数据库的当前角色                                             |
| archivelog_change#           | integer                  | 归档日志的最高编号                                           |
| archivelog_compression       | text                     | 归档日志的压缩状态                                           |
| switchover_status            | text                     | 表明允许切换                                                 |
| dataguard_broker             | text                     | data guard代理信息                                           |
| guard_status                 | text                     | 数据保护状态                                                 |
| supplemental_log_data_min    | text                     | 确保logminer有足够的信息进行工作                             |
| supplemental_log_data_pk     | text                     | 将所有具有主键的表的键值放入重做日志中                       |
| supplemental_log_data_ui     | text                     | 对于所有拥有唯一键的表，指示修改时放入重做日志中             |
| force_logging                | text                     | 指示数据库当前是否为强制日志模式                             |
| platform_id                  | integer                  | 数据库的平台标示号                                           |
| platform_name                | text                     | 数据库的平台名称                                             |
| recovery_target_incarnation# | integer                  | 该数据库恢复的所有文件的编号                                 |
| last_open_incarnation#       | integer                  | v$database_incarnation成功打开的记录号                       |
| current_scn                  | integer                  | 当前的scn号                                                  |
| flashback_on                 | text                     | 指示闪回功能是否开启                                         |
| supplemental_log_data_fk     | text                     | 对于拥有外键的表，修改时是否将键值放入重做日志中             |
| supplemental_log_data_all    | text                     | 对于所有列，修改时是否全部放入重做日志中                     |
| db_unique_name               | name                     | 数据库的唯一标示名称                                         |
| standby_became_primary_scn   | integer                  | 备机成为主机的scn号                                          |
| fs_failover_status           | text                     | 快速故障转移的状态                                           |
| fs_failover_current_target   | text                     | db_unique_name备用数据库                                     |
| fs_failover_threshold        | integer                  | 尝试使用备用机前的观察时间（单位为秒）                       |
| fs_failover_observer_present | text                     | 指示监控者是否连接到数据库                                   |
| fs_failover_observer_host    | text                     | 当前监控器的主机名                                           |
| controlfile_converted        | text                     | 指示还原期间是否隐式转换控制文件                             |
| primary_db_unique_name       | text                     | 对于任何节点数据库，此参数标明主节点名称                     |
| supplemental_log_data_pl     | text                     | 在支持复制工作时，是否额外在重做日志中记录内部的调用信息     |
| min_required_capture_change# | text                     | required_checkpoint_scn数据库上所有本地捕获过程的最小值      |
