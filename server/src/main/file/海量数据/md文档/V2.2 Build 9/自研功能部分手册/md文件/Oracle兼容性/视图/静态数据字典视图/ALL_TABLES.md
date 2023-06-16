#### ALL_TABLES

ALL_TABLES视图提供当前用户可访问数据库用户定义表的信息。

| 列名                      | 类型                        | 描述                                                         |
| ------------------------- | --------------------------- | ------------------------------------------------------------ |
| owner                     | text                        | 表的所有者                                                   |
| table_name                | text                        | 表名                                                         |
| tablespace_name           | text                        | 表所在的表空间名称；分区表，临时表和索引组织表为NULL         |
| cluster_name              | varchar(128)                | 表所属的集群名称（如果有）                                   |
| iot_name                  | varchar(128)                | 溢出或映射表条目所属的索引组织表的名称（如果有）。如果IOT_TYPE列不为 NULL，则该列包含基表名称。 |
| status                    | varchar(5)                  | 如果之前DROP TABLE操作失败，则表示该表是不可用的（UNUSABLE）或者有效（VALID） |
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
| freelist_groups           | numeric                     | 分配给段的空闲列表组数；分区表为 NULL                        |
| logging                   | varchar(7)                  | 是否记录对表的更改；分区表为 NULL：                          |
| backed_up                 | varchar(1)                  | 自上次修改后表是否已备份 ( Y) 或未 ( N)                      |
| num_rows                  | numeric                     | 表中的行数                                                   |
| blocks                    | double precision            | 表中使用的数据块数                                           |
| empty_blocks              | numeric                     | 表中空（从未使用过）数据块的数量。                           |
| avg_space                 | numeric                     | 分配给表的数据块中的平均可用空间量（以字节为单位）           |
| chain_cnt                 | numeric                     | 表中从一个数据块链接到另一个数据块或已迁移到新块的行数，需要链接以保留旧 ROWID |
| avg_row_len               | numeric                     | 表中行的平均长度（以字节为单位）                             |
| avg_space_freelist_blocks | numeric                     | freelist 上所有块的平均可用空间                              |
| num_freelist_blocks       | numeric                     | freelist 上的块数                                            |
| degree                    | numeric                     | 每个实例用于扫描表的线程数，或DEFAULT                        |
| instances                 | numeric                     | 要扫描表的实例数，或DEFAULT                                  |
| cache                     | varchar(5)                  | 是否要缓存在缓冲区缓存中 (  Y ) 或不 (  N )                  |
| table_lock                | varchar(8)                  | 表锁定是启用 (  ENABLED) 还是禁用 ( DISABLED)                |
| sample_size               | numeric                     | 用于分析表格的样本量                                         |
| last_analyzed             | timestamp without time zone | 最近分析表格的日期                                           |
| partitioned               | character(3)                | 指示表是否已分区 (  YES ) 或未分区 (  NO )                   |
| iot_type                  | varchar(12)                 | 如果表是索引组织表， IOT_TYPE 则为 IOT 、 IOT_OVERFLOW 或 IOT_MAPPING 。如果表不是索引组织表， IOT_TYPE 则为 NULL。 |
| temporary                 | character(1)                | 是否为临时表: 是(Y)、否(N)                                   |
| secondary                 | character(1)                | 该表是否是由  ODCIIndexCreate Oracle Data Cartridge (  Y ) 的方法创建的辅助对象 (  N ) |
| nested                    | varchar(3)                  | 表是否为嵌套表 (  YES ) 或不是 (  NO )                       |
| buffer_pool               | varchar(7)                  | 表的缓冲池                                                   |
| flash_cache               | varchar(7)                  | 表块的数据库智能闪存缓存提示（仅限 Solaris 和 Oracle Linux 功能。） |
| cell_flash_cache          | varchar(7)                  | 表块的单元闪存缓存提示                                       |
| row_movement              | varchar(8)                  | 分区行移动是启用 (  ENABLED ) 还是禁用 (  DISABLED )         |
| global_stats              | varchar(3)                  | GLOBAL_STATS 如果统计信息被收集或增量维护，将 YES 是，否则它将是 NO |
| user_stats                | varchar(3)                  | 统计信息是否由用户直接输入 (  YES ) 或不是 (  NO )           |
| duration                  | varchar(15)                 | 指示临时表的持续时间： SYS$SESSION - 在会话期间保留行  SYS$TRANSACTION - 行被删除后 COMMIT 。Null - 永久表 |
| skip_corrupt              | varchar(8)                  | 指示 Oracle 数据库在表和索引扫描期间是否忽略标记为损坏的块 (  ENABLED ) 或引发错误 (  DISABLED )。要启用此功能，请运行 DBMS_REPAIR .  SKIP_CORRUPT_BLOCKS 程序。 |
| monitoring                | varchar(3)                  | 此列已过时                                                   |
| cluster_owner             | varchar(128)                | 表所属集群的所有者（如果有）                                 |
| dependencies              | varchar(8)                  | 指示行级依赖性跟踪是启用 (  ENABLED ) 还是禁用 (  DISABLED ) |
| compression               | varchar(8)                  | 指示是否启用表压缩 (  ENABLED ) 或不 (  DISABLED )；分区表为 NULL |
| compress_for              | varchar(30)                 | 默认压缩方式                                                 |
| dropped                   | varchar                     | 指示表是否已被删除并在回收站中（ YES ）或不在（ NO ）；分区表为 NULL。此视图不返回已删除的表的名称。 |
| read_only                 | varchar(3)                  | 表段是否 READ-ONLY 存在。可能的值：YES(表段是READ_ONLY)；NO(表段不是READ_ONLY)；N/A(不适用。此值出现在分区表中，其中没有与逻辑表对象相关的段。)； |
| segment_created           | varchar(3)                  | 指示是否创建表段。可能的值：YES(表段已创建)；NO(表段未创建)；N/A(不适用。此值出现在分区表中，其中没有与逻辑表对象相关的段。)； |
| result_cache              | varchar(7)                  | 表的结果缓存模式注释                                         |
| clustering                | varchar(3)                  | 表是否有属性聚类子句（ YES ）或没有（ NO ）                  |
| activity_tracking         | varchar(23)                 | 是否在表上启用热图跟踪                                       |
| dml_timestamp             | varchar(25)                 | 自动数据优化的修改时间、创建时间或两者                       |
| has_identity              | varchar(3)                  | 表是否有标识列 (  YES ) 或没有 (  NO )                       |
| container_data            | varchar(3)                  | 表是否包含特定于容器的数据                                   |
| inmemory                  | varchar(8)                  | 是否为此子分区启动内存列存储，ENABLED(启动)，DISABLED（未启动 |
| inmemory_priority         | varchar(8)                  | 内存列存储填充的优先级                                       |
| inmemory_distribute       | varchar(15)                 | 内存列存储在oracle RAC中的分布方式                           |
| inmemory_compression      | varchar(17)                 | 内存列存储的压缩级别                                         |
| inmemory_duplicate        | varchar(13)                 | oracle RAC中的内存列存储的重复设置                           |
| default_collation         | varchar(100)                | 表的默认排序规则                                             |
| duplicated                | varchar(1)                  | 表是否在此分片上重复 (  Y ) 或不 (  N )                      |
| sharded                   | varchar(1)                  | 表是分片 (  Y ) 还是不分片 (  N )                            |
| external                  | varchar(3)                  | 表是否为外部表 (  YES ) 或不是 (  NO )                       |
| cellmemory                | varchar(24)                 | 存储单元闪存缓存中的列压缩值                                 |
| containers_default        | varchar(3)                  | 默认情况下是否启用表 CONTAINERS() (  YES ) 或不启用 (  NO )  |
| container_map             | varchar(3)                  | 指示是否启用表以与 container_map 数据库属性 (  YES ) 一起使用 (  NO ) |
| extended_data_link        | varchar(3)                  | 指示是否启用表以从根 (  YES ) 获取扩展数据链接 (  NO )       |
| extended_data_link_map    | varchar(3)                  | 仅为支持兼容性                                               |
| inmemory_service          | varchar(12)                 | 如何在各种实例上填充内存列存储                               |
| inmemory_service_name     | varchar(1000)               | 内存列存储的服务名称                                         |
| container_map_object      | varchar(3)                  | 指示是否将表用作 container_map 数据库属性的值 (  YES ) 或不 (  NO ) |

