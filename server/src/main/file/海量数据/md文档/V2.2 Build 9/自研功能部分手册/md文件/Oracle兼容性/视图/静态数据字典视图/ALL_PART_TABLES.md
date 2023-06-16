#### ALL_PART_TABLES

ALL_PART_TABLES视图提供当前用户可访问数据库中的分区表信息。

| 列名                      | 类型          | 描述                                                         |
| ------------------------- | ------------- | ------------------------------------------------------------ |
| owner                     | text          | 分区表的所有者                                               |
| table_name                | text          | 分区表名                                                     |
| partitioning_type         | text          | 分区类型： range、list、hash                                 |
| subpartitioning_type      | text          | 子分区类型：range、list、hash                                |
| partition_count           | bigint        | 分区表中的分区数                                             |
| def_subpartition_count    | bigint        | 复合分区表中，子分区数                                       |
| partitioning_key_count    | integer       | 分区键的列数                                                 |
| subpartitioning_key_count | integer       | 复合分区表中，子分区中的列数                                 |
| status                    | varchar(8)    | 仅为支持兼容性。该值始终为VALID                              |
| def_tablespace_name       | varchar(30)   | 仅为支持兼容性。该值始终为NULL                               |
| def_pct_free              | numeric       | 仅为支持兼容性。该值始终为NULL                               |
| def_pct_used              | numeric       | 仅为支持兼容性。该值始终为NULL                               |
| def_ini_trans             | numeric       | 仅为支持兼容性。该值始终为NULL                               |
| def_max_trans             | numeric       | 仅为支持兼容性。该值始终为NULL                               |
| def_initial_extent        | varchar(40)   | 仅为支持兼容性。该值始终为NULL                               |
| def_next_extent           | varchar(40)   | 仅为支持兼容性。该值始终为NULL                               |
| def_min_extents           | varchar(40)   | 仅为支持兼容性。该值始终为NULL                               |
| def_max_extents           | varchar(40)   | 仅为支持兼容性。该值始终为NULL                               |
| def_max_size              | varchar(40)   | 仅为支持兼容性。该值始终为NULL                               |
| def_pct_increase          | varchar(40)   | 仅为支持兼容性。该值始终为NULL                               |
| def_freelists             | numeric       | 仅为支持兼容性。该值始终为NULL                               |
| def_freelist_groups       | numeric       | 仅为支持兼容性。该值始终为NULL                               |
| def_logging               | varchar(7)    | 仅为支持兼容性。该值始终为YES                                |
| def_compression           | varchar(8)    | 仅为支持兼容性。该值始终为None                               |
| def_compress_for          | varchar(30)   | 添加分区时使用哪种操作的默认压缩                             |
| def_buffer_pool           | varchar(7)    | 仅为支持兼容性。该值始终为DEFAULT                            |
| def_flash_cache           | varchar(7)    | 仅为支持兼容性。该值始终为NULL                               |
| def_cell_flash_cache      | varchar(7)    | 仅为支持兼容性。该值始终为NULL                               |
| ref_ptn_constraint_name   | varchar(30)   | 仅为支持兼容性。该值始终为NULL                               |
| interval                  | varchar(1000) | 区间值字符串                                                 |
| autolist                  | varchar(1000) | 指示表是否通过自动列表分区 (  YES )分区 (  NO )              |
| interval_subpartition     | varchar(1000) | 子分区间隔值的字符串                                         |
| autolist_subpartition     | varchar(1000) | 指示此子 分区是否使用 (  YES )自动列表分区 ( ) NO            |
| is_nested                 | varchar(1000) | 指示分区表是否为嵌套表 (  YES ) 或不是 (  NO )               |
| def_segment_creation      | varchar(1000) | 指定是否在表级别指定了段创建的默认值                         |
| def_indexing              | varchar(1000) | 指示为表指定的索引属性。                                     |
| def_inmemory              | varchar(1000) | 表示内存列存储是否默认启用（ENABLE）,禁用（DISABLED）,或者未指定 (NONE) 此表中的分区 |
| def_inmemory_priority     | varchar(1000) | 内存列存储填充的默认优先级                                   |
| def_inmemory_distribute   | varchar(1000) | Oracle RAC)环境中默认情况下如何为表的分区分配 内存列存储     |
| def_inmemory_compression  | varchar(1000) | 内存列存储的默认压缩级别                                     |
| def_inmemory_duplicate    | varchar(1000) | Oracle RAC 环境中内存列存储的默认重复设置                    |
| def_read_only             | varchar(1000) | 新分区的默认设置：YES(只读)，NO（读/写）                     |
| def_cellmemory            | varchar(1000) | 显示父表中的新分区将继承的属性的默认值， CELLMEMORY 除非该行为被显式覆盖 |
| def_inmemory_service      | varchar(1000) | 默认情况下如何在各种实例上为表的分区填充内存列存储           |
| def_inmemory_service_name | varchar(1000) | 默认情况下应为表的分区填充内存列存储的服务的服务名称。       |
