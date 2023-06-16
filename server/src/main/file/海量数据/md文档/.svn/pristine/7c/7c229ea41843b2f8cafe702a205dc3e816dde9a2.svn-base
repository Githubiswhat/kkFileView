#### ALL_IND_PARTITIONS

ALL_IND_PARTITIONS视图提供当前用户可访问数据库中的索引分区的信息。

| 列名                    | 类型                        | 描述                                                         |
| ----------------------- | --------------------------- | ------------------------------------------------------------ |
| index_owner             | varchar(128)                | 索引分区所属分区表索引的所有者的名称                         |
| index_name              | varchar(128)                | 索引分区所属分区表索引的名称                                 |
| composite               | varchar(3)                  | 索引分区的名称                                               |
| partition_name          | varchar(128)                | 分区名称                                                     |
| subpartition_count      | bigint                      | 如果是复合分区表上的本地索引，则分区中的子分区数             |
| high_value              | text                        | 索引分区所对应分区的上边界                                   |
| high_value_length       | integer                     | 分区界限值表达式的长度                                       |
| partition_position      | numeric                     | 分区在索引中的位置                                           |
| status                  | varchar(8)                  | 指示索引分区是否可用 (  USABLE ) 或不可用 (  UNUSABLE )      |
| tablespace_name         | name                        | 索引分区的表空间名称                                         |
| pct_free                | numeric                     | 块中可用空间的最小百分比                                     |
| ini_trans               | numeric                     | 初始交易数量                                                 |
| max_trans               | numeric                     | 最大交易数                                                   |
| initial_extent          | numeric                     | 初始范围的大小（以字节为单位）                               |
| next_extent             | numeric                     | 扩展区的大小（以字节为单位）                                 |
| min_extent              | numeric                     | 段中允许的最小范围数                                         |
| max_extent              | numeric                     | 段中允许的最大范围数                                         |
| max_size                | numeric                     | 段中允许的最大块数                                           |
| pct_increase            | numeric                     | 扩展区大小增加的百分比                                       |
| freelists               | numeric                     | 此段中分配的进程空闲列表数                                   |
| freelist_groups         | numeric                     | 段中分配的进程空闲列表组数                                   |
| logging                 | varchar(7)                  | 是否记录对索引的更改                                         |
| compression             | varchar(8)                  | 用于分区的压缩类型                                           |
| blevel                  | numeric                     | B*-树级别（从根块到叶块的索引深度）。深度 0 表示根块和叶块相同。 |
| leaf_blocks             | numeric                     | 索引分区中的叶块数                                           |
| distinct_keys           | numeric                     | 索引分区中不同键的数量                                       |
| avg_leaf_blocks_per_key | numeric                     | 索引中每个不同值出现的平均叶块数，四舍五入到最接近的整数。对于强制 UNIQUE 和 PRIMARY KEY 约束的索引，此值始终为 1 。 |
| avg_data_blocks_per_key | numeric                     | 表中由索引中的不同值指向的数据块的平均数，舍入到最接近的整数。此统计数据是包含包含索引列的给定值的行的数据块的平均数量。 |
| clustering_factor       | numeric                     | 基于索引值的表中行的顺序量                                   |
| num_rows                | numeric                     | 返回的行数                                                   |
| sample_size             | numeric                     | 用于分析此分区的样本大小                                     |
| last_analyzed           | timestamp without time zone | 最近分析此分区的日期                                         |
| buffer_pool             | varchar(7)                  | 分区的实际缓冲池                                             |
| flash_cache             | varchar(7)                  | 用于分区块的数据库智能闪存缓存提示                           |
| cell_flash_cache        | varchar(7)                  | 用于分区块的单元闪存缓存提示                                 |
| user_stats              | varchar(3)                  | 指示统计信息是否由用户直接输入 (  YES ) 或不是 (  NO )       |
| pct_direct_access       | numeric                     | 如果索引组织表上的二级索引，则 VALID 猜测 行的百分比         |
| global_stats            | varchar(3)                  | GLOBAL_STATS表示是否已收集统计信息，或者是否已从子分区汇总统计信息 或尚未收集统计信息 |
| domidx_opstatus         | varchar(6)                  | 索引的操作状态                                               |
| parameters              | varchar(1000)               | 对于域索引，参数字符串                                       |
| interval                | varchar(3)                  | 指示分区是否在区间分区表的区间部分 (  YES ) 或分区是否在范围部分 (  NO ) |
| segment_created         | varchar(3)                  | 指示索引分区段是否已创建（ YES ）或未创建（  NO ）； N/A 表示这个索引是子分区的，在分区级别不存在段 |
| orphaned_entries        | varchar(3)                  | 指示全局索引是否由于在 DROP/TRUNCATE PARTITION 或 MODIFY PARTITION INDEXING OFF 操作期间延迟索引维护而包含陈旧条目。 |
