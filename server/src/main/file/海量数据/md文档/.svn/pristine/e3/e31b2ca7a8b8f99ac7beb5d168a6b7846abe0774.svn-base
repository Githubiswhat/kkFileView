#### ALL_TAB_PARTITIONS

ALL_TAB_PARTITIONS视图提供当前用户可访问数据库中的所有分区的信息。

| 列名                   | 类型                        | 描述                                                         |
| ---------------------- | --------------------------- | ------------------------------------------------------------ |
| table_owner            | varchar(128)                | 分区表的所有者                                               |
| table_name             | varchar(128)                | 分区表的表名                                                 |
| composite              | text                        | 指示表是复合分区 (  YES ) 还是不是 (  NO )                   |
| partition_name         | varchar(128)                | 分区名称                                                     |
| subpartition_count     | bigint                      | 如果是符合分区，分区中的子分区数量                           |
| high_value             | text                        | 对应分区范围最大值                                           |
| high_value_length      | integer                     | 对应分区范围最大值的字节长度                                 |
| partition_position     | numeric                     | 分区在表中的位置                                             |
| tablespace_name        | text                        | 分区所在表空间名                                             |
| pct_free               | numeric                     | 仅为支持兼容性                                               |
| pct_used               | numeric                     | 仅为支持兼容性                                               |
| ini_trans              | numeric                     | 仅为支持兼容性                                               |
| max_trans              | numeric                     | 仅为支持兼容性                                               |
| initial_extent         | numeric                     | 仅为支持兼容性                                               |
| next_extent            | numeric                     | 仅为支持兼容性                                               |
| min_extent             | numeric                     | 仅为支持兼容性                                               |
| max_extent             | numeric                     | 仅为支持兼容性                                               |
| max_size               | numeric                     | 仅为支持兼容性                                               |
| pct_increase           | numeric                     | 仅为支持兼容性                                               |
| freelists              | numeric                     | 仅为支持兼容性                                               |
| freelist_groups        | numeric                     | 仅为支持兼容性                                               |
| logging                | varchar(7)                  | 是否开启表的logging                                          |
| compression            | varchar(8)                  | 分区是否开启压缩                                             |
| compress_for           | varchar(30)                 | 默认压缩方式                                                 |
| num_rows               | numeric                     | 分区中的数据行数                                             |
| blocks                 | double precision            | 分区中数据块数                                               |
| empty_blocks           | numeric                     | 仅为支持兼容性                                               |
| avg_space              | numeric                     | 仅为支持兼容性                                               |
| chain_cnt              | numeric                     | 仅为支持兼容性                                               |
| avg_row_len            | numeric                     | 仅为支持兼容性                                               |
| sample_size            | numeric                     | 仅为支持兼容性                                               |
| last_analyzed          | timestamp without time zone | 仅为支持兼容性                                               |
| buffer_pool            | varchar(7)                  | 仅为支持兼容性                                               |
| flash_cache            | varchar(7)                  | 用于分区块的数据库智能闪存缓存提示                           |
| cell_flash_cache       | varchar(7)                  | 用于分区块的单元闪存缓存提示                                 |
| global_stats           | varchar(3)                  | 仅为支持兼容性                                               |
| user_stats             | varchar(3)                  | 仅为支持兼容性                                               |
| is_nested              | varchar(7)                  | 指示这是否是嵌套表分区 (  YES ) 或不是 (  NO )               |
| parent_table_partition | varchar(7)                  | 父表对应的分区                                               |
| interval               | varchar(3)                  | 指示分区是否在区间分区表的区间部分 (  YES ) 或分区是否在范围部分 (  NO ) |
| segment_created        | varchar(4)                  | 指示简单分区表的分区的实际段创建属性，或复合分区表的子分区的默认值（如果指定），否则 NONE |
| indexing               | varchar(4)                  | 简单分区表的分区的实际索引属性，或复合分区表的子分区的默认值（如果指定），否则为 NONE |
| read_only              | varchar(4)                  | 分区的默认设置：YES(只读)，NO(读/写)，NONE（没有指定默认设置） |
| inmemory               | varchar(4)                  | 是否为此分区启动内存列存储，ENABLED(启动)，DISABLED（未启动） |
| inmemory_priority      | varchar(4)                  | 内存列存储填充的优先级                                       |
| inmemory_distribute    | varchar(4)                  | 内存列存储在RAC环境中的分布方式                              |
| inmemory_compression   | varchar(4)                  | 内存列存储的压缩级别                                         |
| inmemory_duplicate     | varchar(4)                  | oracle RAC中的内存列存储的重复设置                           |
| cellmemory             | varchar(24)                 | 存储单元闪存缓存中的列压缩值                                 |
| inmemory_service       | varchar(12)                 | 如何在各种实例上填充内存列存储                               |
| inmemory_service_name  | varchar(100)                | 填充的内存列存储的服务名称                                   |
