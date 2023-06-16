#### ALL_ALL_TABLES

ALL_ALL_TABLES视图提供有关当前用户可访问的表的信息。

| 列名                      | 类型                        | 描述                                                         |
| ------------------------- | --------------------------- | ------------------------------------------------------------ |
| owner                     | name                        | 表的所有者                                                   |
| table_name                | name                        | 表名                                                         |
| tablespace_name           | name                        | 表所在的表空间                                               |
| cluster_name              | varchar(128)                | 表所属的集群名称（如果有）                                   |
| iot_name                  | varchar(128)                | 溢出或映射表条目所属的索引组织表的名称（如果有）             |
| status                    | varchar(8)                  | 表示表的状态是可用（VALID）还是不可以用(UNUSABLE)            |
| pct_free                  | numeric                     | 块中可用空间的最小百分比；分区表为 NULL                      |
| pct_used                  | numeric                     | 块中已用空间的最小百分比；分区表为 NULL                      |
| ini_trans                 | numeric                     | 初始交易数量；分区表为 NULL                                  |
| max_trans                 | numeric                     | 最大交易数量；分区表为 NULL                                  |
| initial_extent            | numeric                     | 初始范围的大小（以字节为单位）；分区表为 NULL                |
| next_extent               | numeric                     | 辅助范围的大小（以字节为单位）；分区表为 NULL                |
| min_extents               | numeric                     | 段中允许的最小范围数；分区表为 NULL                          |
| max_extents               | numeric                     | 段中允许的最大范围数；分区表为 NULL                          |
| pct_increase              | numeric                     | 范围大小的百分比增加；分区表为 NULL                          |
| freelists                 | numeric                     | 分配给段的进程空闲列表数；分区表为 NULL                      |
| freelist_groups           | numeric                     | 分配给段的空闲列表组数                                       |
| logging                   | varchar(7)                  | 是否记录对表的更改                                           |
| backed_up                 | varchar(1)                  | 自上次修改后表是否已备份 ( Y) 或未 ( N)                      |
| num_rows                  | numeric                     | 表中的行数                                                   |
| blocks                    | doubleprecision             | 表中使用的块数                                               |
| empty_blocks              | numeric                     | 表中的空（从未使用）块数                                     |
| avg_space                 | numeric                     | 表中的平均可用空间                                           |
| chain_cnt                 | numeric                     | 表中从一个数据块链接到另一个数据块或已迁移到新块的行数，需要一个链接来保留旧的 rowid。此列仅在您分析表后更新。 |
| avg_row_len               | numeric                     | 平均行长度，包括行开销                                       |
| avg_space_freelist_blocks | numeric                     | freelist 上所有块的平均可用空间                              |
| num_freelist_blocks       | numeric                     | freelist 上的块数                                            |
| degree                    | numeric                     | 每个实例用于扫描表的线程数                                   |
| instances                 | numeric                     | 要扫描表的实例数                                             |
| cache                     | varchar(5)                  | 表是否要缓存在缓冲区缓存中 ( Y) 或不 ( N)                    |
| table_lock                | varchar(8)                  | 指示表锁定是启用 ( ENABLED) 还是禁用 ( DISABLED)             |
| sample_size               | numeric                     | 用于分析表格的样本量                                         |
| last_analyzed             | timestamp without time zone | 用于分析表格的样本量                                         |
| partitioned               | character(3)                | 指示表是否已分区 ( YES) 或未分区 ( NO)                       |
| iot_type                  | varchar(12)                 | 如果表是索引组织表，IOT_TYPE则为IOT、IOT_OVERFLOW或IOT_MAPPING。如果表不是索引组织表，IOT_TYPE则为 NULL。 |
| object_id_type            | varchar(16)                 | USER-DEFINED`指示对象 ID ( OID) 是`SYSTEM GENERATED          |
| table_type_owner          | varchar(128)                | 如果是对象表，则创建表的类型的所有者                         |
| table_type                | varchar(128)                | 如果是对象表，则表的类型                                     |
| temporary                 | text                        | 是否为临时表，(Y）是，(N)否                                  |
| secondary                 | character(1)                | 指示表是否是由`ODCIIndexCreate`Oracle Data Cartridge 的方法创建的包含域索引内容的辅助对象 ( `Y`) 或不是 ( `N`) |
| nested                    | varchar(7)                  | 指示表是否为嵌套表 (YES) 或不是 ( NO)                        |
| buffer_pool               | varchar(7)                  | 用于表块的缓冲池                                             |
| flash_cache               | varchar(7)                  | 用于表块的数据库智能闪存缓存提示(仅限 Solaris 和 Oracle Linux 功能。) |
| cell_flash_cache          | varchar(7)                  | 用于表块的单元闪存缓存提示                                   |
| row_movement              | varchar(8)                  | 果是分区表，则指示行移动是启用 ( `ENABLED`) 还是禁用 ( `DISABLED`) |
| global_stats              | varchar(3)                  | 统计信息被收集或增量维护，将是`YES`，否则它将是NO            |
| user_stats                | varchar(3)                  | 统计信息是否由用户直接输入 (YES) 或不是 ( NO)                |
| duration                  | varchar(15)                 | 临时表的持续时间                                             |
| skip_corrupt              | varchar(8)                  | Oracle 数据库在表和索引扫描期间是否忽略标记为损坏的块 ( `ENABLED`) 或引发错误 ( `DISABLED`)。要启用此功能，请运行该`DBMS_REPAIR.skip_corrupt_blocks`过程。 |
| monitoring                | varchar(3)                  | 表是否具有`MONITORING`属性集 (YES) 或没有 ( NO)              |
| cluster_owner             | varchar(128                 | 表所属集群的所有者（如果有）                                 |
| dependencies              | varchar(8)                  | 行级依赖性跟踪是启用 ( `ENABLED`) 还是禁用 ( `DISABLED`)     |
| compression               | varchar(8)                  | 否启用表压缩 ( `ENABLED`) 或不 ( `DISABLED`)；分区表为 NULL  |
| compress_for              | varchar(30)                 |                                                              |
| dropped                   | varchar                     | 是否已被删除并在回收站中（`YES`）或不在（NO）；分区表为 NULL。此视图不返回已删除的表的名称。 |
| segment_created           | varchar(3)                  | 表段是否已创建 (YES) 或未创建 ( NO)                          |
| inmemory                  | varchar(8)                  | 此表的内存中列存储（IM 列存储）是启用 ( `ENABLED`) 还是禁用 ( `DISABLED`) |
| inmemory_priority         | varchar(8)                  | 将此表填充到内存中列存储（IM 列存储）中的优先级              |
| inmemory_distribute       | varchar(15)                 | 表示表将如何分布在 Oracle Real Application Clusters (Oracle RAC) 环境中的 IM 列存储中 |
| inmemory_compression      | varchar(17)                 | 内存存储的压缩级别                                           |
| inmemory_duplicate        | varchar(13)                 | Oracle RAC 环境中 In-Memory Column Store（IM 列存储）的重复设置 |
| external                  | varchar(3)                  | 表是否为外部表 (YES) 或不是 ( NO)                            |
| cellmemory                | varchar(24)                 | 存储单元闪存缓存中的列压缩值                                 |
| inmemory_service          | varchar(12)                 | 如何在各种实例上填充 IM 列存储                               |
| inmemory_service_name     | varchar(1000)               | 应在其上填充 IM 列存储的服务的服务名称                       |
