#### PITR指定时间点恢复

**功能描述**

当数据库崩溃或希望回退到数据库之前的某一状态时，Vastbase的即时恢复功能（Point-In-Time Recovery，简称PITR）可以支持恢复到备份归档数据之后的任意时间点。

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>   
>
> - PITR仅支持恢复到物理备份数据之后的某一时间点。
> - 仅主节点可以进行PITR恢复，备机需要进行全量build达成与主机数据同步。

**注意事项**

- 要求具有基于经过物理备份的全量数据文件。
- 要求具有基于已归档的WAL日志文件。

**PITR恢复流程**

1. 将物理备份的文件替换目标数据库目录。
2. 删除数据库目录下pg_xlog/中的所有文件。
3. 将归档的WAL日志文件复制到pg_xlog文件中（此步骤可以省略，通过配置recovery.conf恢复命令文件中的restore_command项替代）。
4. 在数据库目录下创建恢复命令文件recovery.conf，指定数据库恢复的程度。
5. 启动数据库。
6. 连接数据库，查看是否恢复到希望预期的状态。
7. 若已经恢复到预期状态，通过pg_xlog_replay_resume()指令使主节点对外提供服务。

**recovery.conf文件配置**

**归档恢复配置**

- restore_command = string

这个SHELL命令是获取WAL文件系列中已归档的WAL文件。字符串中的任何一个%f是用归档检索中的文件名替换， 并且%p是用服务器上的复制目的地的路径名替换。 任意一个%r是用包含最新可用重启点的文件名替换。

示例：

```
restore_command = 'cp /mnt/server/archivedir/%f %p'
```

- archive_cleanup_command = string

这个选项参数声明一个shell命令。在每次重启时会执行这个shell命令。 archive_cleanup_command为清理备库不需要的归档WAL文件提供一个机制。 任何一个%r由包含最新可用重启点的文件名代替。这是最早的文件，因此必须保留以允许恢复能够重新启动，因此所有早于%r的文件可以安全的移除。

示例：

```
archive_cleanup_command = 'pg_archivecleanup /mnt/server/archivedir %r'
```

需要注意的是，如果多个备服务器从相同的归档路径恢复时， 需要确保在任何一个备服务器在需要之前，不能删除WAL文件。

- recovery_end_command = string

这个参数是可选的，用于声明一个只在恢复完成时执行的SHELL命令。recovery_end_command是为以后的复制或恢复提供一个清理机制。

**恢复目标设置**

- recovery_target_name = string

此参数声明命名还原到一个使用pg_create_restore_point()创建的还原点。

示例:

```
recovery_target_name = 'restore_point_1'
```

- recovery_target_time = timestamp

此参数声明命名还原到一个指定时间戳。

示例：

```
recovery_target_time = '2020-01-01 12:00:00'
```

- recovery_target_xid = string

这个参数声明还原到一个事务ID。

示例：

```
recovery_target_xid = '3000'
```

- recovery_target_lsn = string

这个参数声明还原到日志的指定LSN点。

示例：

```
recovery_target_lsn = '0/0FFFFFF'
```

- recovery_target_inclusive = boolean

声明是否在指定恢复目标（true）之后停止，或在这（false）之前停止。该声明仅支持恢复目标为recovery_target_time、recovery_target_xid和recovery_target_lsn的配置。

示例：

```
recovery_target_inclusive = true
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>   
>
> - recovery_target_name、recovery_target_time、recovery_target_xid、recovery_target_lsn这四个配置项仅同时支持一项。
> - 如果不配置任何恢复目标，或配置目标不存在，则默认恢复到最新的WAL日志点。