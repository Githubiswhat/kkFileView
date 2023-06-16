#### ALL_TAB_SUBPARTITIONS

ALL_TAB_SUBPARTITIONS视图提供当前用户可访问数据库中的所有子分区的信息。

| 列名                  | 类型                        | 描述                                                         |
| --------------------- | --------------------------- | ------------------------------------------------------------ |
| table_owner           | text                        | 子分区表的所有者                                             |
| table_name            | text                        | 表名                                                         |
| partition_name        | text                        | 分区名                                                       |
| subpartition_name     | text                        | 子分区名字                                                   |
| high_value            | text                        | 子分区范围最大值                                             |
| high_value_length     | integer                     | 子分区范围最大值的字节长度                                   |
| partition_position    | numeric                     | 分区在表中的位置                                             |
| subpartition_position | numeric                     | 子分区字段所在表中的位置                                     |
| tablespace_name       | text                        | 子分区所在表空间名                                           |
| pct_free              | numeric                     | 仅为支持兼容性，该值始终为0                                  |
| pct_used              | numeric                     | 仅为支持兼容性，该值始终为0                                  |
| ini_trans             | numeric                     | 仅为支持兼容性，该值始终为0                                  |
| max_trans             | numeric                     | 仅为支持兼容性，该值始终为0                                  |
| initial_extent        | numeric                     | 仅为支持兼容性，该值始终为null                               |
| next_extent           | numeric                     | 仅为支持兼容性，该值始终为null                               |
| min_extent            | numeric                     | 仅为支持兼容性，该值始终为0                                  |
| max_extent            | numeric                     | 仅为支持兼容性，该值始终为0                                  |
| max_size              | numeric                     | 段中允许的最大块数                                           |
| pct_increase          | numeric                     | 仅为支持兼容性，该值始终为0                                  |
| freelists             | numeric                     | 仅为支持兼容性，该值始终为null                               |
| freelist_groups       | numeric                     | 仅为支持兼容性，该值始终为null                               |
| logging               | varchar(7)                  | 仅为支持兼容性，该值始终为YES                                |
| compression           | varchar(8)                  | 仅为支持兼容性，该值始终为NONE                               |
| compress_for          | varchar(30)                 | 操作的默认压缩方式                                           |
| num_rows              | numeric                     | 子分区的数据量                                               |
| blocks                | double precision            | 子分区的数据块数                                             |
| empty_blocks          | numeric                     | 仅为支持兼容性，该值始终为null                               |
| avg_space             | numeric                     | 仅为支持兼容性，该值始终为null                               |
| chain_cnt             | numeric                     | 仅为支持兼容性，该值始终为null                               |
| avg_row_len           | numeric                     | 仅为支持兼容性，该值始终为null                               |
| sample_size           | numeric                     | 仅为支持兼容性，该值始终为null                               |
| last_analyzed         | timestamp without time zone | 仅为支持兼容性，该值始终为null                               |
| buffer_pool           | varchar(7)                  | 仅为支持兼容性，该值始终为null                               |
| flash_cache           | varchar(7)                  | 用于子分区块的数据库智能闪存缓存提示                         |
| cell_flash_cache      | varchar(7)                  | 用于子分区块的单元闪存缓存提示                               |
| global_stats          | varchar(3)                  | 仅为支持兼容性，该值始终为YES                                |
| user_stats            | varchar(3)                  | 仅为支持兼容性，该值始终为NO                                 |
| interval              | varchar(3)                  | 指示分区是否在区间分区表的区间部分 (  YES ) 或分区是否在范围部分 (  NO ) |
| segment_created       | varchar(4)                  | 指示表子分区段是否已创建（ YES ）或未创建（  NO ）； N/A 表示该表没有子分区 |
| indexing              | varchar(4)                  | 指示索引属性。ON(子分区索引开启)，OFF(子分区索引关闭)        |
| read_only             | varchar(4)                  | 指示子分区是只读的还是读/写的：YES(只读)，NO(读/写)          |
| inmemory              | varchar(4)                  | 是否为此子分区启动内存列存储，ENABLED(启动)，DISABLED（未启动） |
| inmemory_priority     | varchar(4)                  | 内存列存储填充的优先级                                       |
| inmemory_distribute   | varchar(4)                  | 内存列存储在oracle RAC中的分布方式                           |
| inmemory_compression  | varchar(4)                  | 内存列存储的压缩级别                                         |
| inmemory_duplicate    | varchar(4)                  | oracle RAC环境中的内存列存储的重复方式设置                   |
| inmemory_service      | varchar(12)                 | 如何在各种实例上填充内存列存储                               |
| inmemory_service_name | varchar(100)                | 内存列存储的服务名称                                         |
| cellmemory            | varchar(24)                 | 存储单元闪存缓存中的列压缩值                                 |
