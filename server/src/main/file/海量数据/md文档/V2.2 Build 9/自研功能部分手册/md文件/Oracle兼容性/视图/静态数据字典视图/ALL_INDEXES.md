#### ALL_INDEXES

ALL_INDEXES视图提供当前用户可访问表上的索引信息。

| 列名                    | 类型          | 描述                                                         |
| ----------------------- | ------------- | ------------------------------------------------------------ |
| owner                   | varchar(64)   | 索引的所有者                                                 |
| index_name              | varchar(64)   | 索引名字                                                     |
| index_type              | varchar(27)   | 索引类型，如btree类型                                        |
| table_owner             | text          | 索引对象的所有者                                             |
| table_name              | varchar(64)   | 索引对象的表名                                               |
| table_type              | varchar(11)   | 表的类型                                                     |
| uniqueness              | text          | 索引是唯一的（UNIQUE）还是非唯一的（NONUNIQUE）              |
| compression             | character(1)  | 是否启用索引压缩                                             |
| prefix_length           | numeric       | /                                                            |
| tablespace_name         | text          | 索引所属的表空间名字                                         |
| ini_trans               | numeric       | 初始交易数量                                                 |
| max_trans               | numeric       | 最大交易数                                                   |
| initial_extent          | numeric       | 初始范围的大小                                               |
| next_extent             | numeric       | 次要扩展区的大小                                             |
| min_extents             | numeric       | 段中允许的最小范围数                                         |
| max_extents             | numeric       | 段中允许的最大范围数                                         |
| pct_increase            | numeric       | 扩展区大小增加的百分比                                       |
| pct_threshold           | numeric       | 每个索引条目允许的块空间的阈值百分比                         |
| include_column          | numeric       | 要包含在索引组织表主键（非溢出）索引中的最后一列的列 ID。此列映射到视图 的 COLUMN_ID 列。 *_TAB_COLUMNS |
| freelists               | numeric       | 分配给该段的进程空闲列表数                                   |
| freelist_groups         | numeric       | 分配给该段的空闲列表组数                                     |
| pct_free                | numeric       | 块中可用空间的最小百分比                                     |
| logging                 | text          | 索引日志是否记录                                             |
| blevel                  | numeric       | B*-树级别（从根块到叶块的索引深度）。深度 0 表示根块和叶块相同。 |
| leaf_blocks             | numeric       | 索引中的叶块数                                               |
| distinct_keys           | numeric       | 不同索引值的数量。对于强制 UNIQUE 和 PRIMARY KEY 约束的索引，此值与表中的行数相同 (  *_TABLES.NUM_ROWS ) |
| avg_leaf_blocks_per_key | numeric       | 索引中每个不同值出现的平均叶块数，四舍五入到最接近的整数。对于强制 UNIQUE 和 PRIMARY KEY 约束的索引，此值始终为 1 。 |
| avg_data_blocks_per_key | numeric       | 表中由索引中的不同值指向的数据块的平均数，舍入到最接近的整数。此统计数据是包含包含索引列的给定值的行的数据块的平均数量。 |
| clustering_factor       | numeric       | 基于索引值的表中行的顺序量。                                 |
| status                  | text          | 索引是否有效                                                 |
| num_rows                | numeric       | 索引中的行数。                                               |
| sample_size             | numeric       | 用于分析指数的样本大小                                       |
| last_analyzed           | date          | 最近分析该指数的日期                                         |
| degree                  | varchar(40)   | 每个实例用于扫描索引的线程数                                 |
| instances               | varchar(40)   | 要扫描的索引的实例数                                         |
| partitioned             | character(3)  | 索引是否已分区                                               |
| temporary               | character(1)  | 索引是否在临时表上                                           |
| generated               | varchar(1)    | 索引的名称是系统生成的 (  Y ) 还是不是 (  N )                |
| secondary               | character(1)  | 仅为支持兼容性。该值始终为N                                  |
| buffer_pool             | varchar(7)    | 用于索引块的缓冲池                                           |
| flash_cache             | varchar(7)    | 用于索引块的数据库智能闪存缓存提示                           |
| cell_flash_cache        | varchar(7)    | 用于索引块的单元闪存缓存提示                                 |
| user_stats              | varchar(3)    | 指示统计信息是否由用户直接输入 (YES) 或不是 ( NO)            |
| duration                | varchar(15)   | 指示临时表的持续时间                                         |
| pct_direct_access       | numeric       | 对于索引组织表上的二级索引， VALID 猜测 行的百分比           |
| ityp_owner              | varchar(128)  | 对于域索引，索引类型的所有者                                 |
| ityp_name               | varchar(128)  | 对于域索引，索引类型的名称                                   |
| parameters              | varchar(1000) | 对于域索引，参数字符串                                       |
| global_stats            | varchar(3)    | GLOBAL_STATS 如果统计信息被收集或增量维护，将 YES 是，否则它将是 NO |
| domidx_status           | varchar(12)   | 域索引的状态：                                               |
| domidx_opstatus         | varchar(6)    | 对域索引的操作状态                                           |
| funcidx_status          | varchar(8)    | 基于函数的索引的状态                                         |
| join_index              | character(3)  | 仅为支持兼容性。该值始终为NO                                 |
| iot_redundant_pkey_elim | character(3)  | 是否从索引组织表的二级索引中消除冗余主键列 (YES) 或不 ( NO)  |
| dropped                 | character(3)  | 仅为支持兼容性。该值始终为NO                                 |
| visibility              | varchar(9)    | 索引是优化器 VISIBLE 还是 INVISIBLE 优化器                   |
| domidx_management       | varchar(14)   | 如果这是域索引，则指示域索引是系统管理的 (  SYSTEM_MANAGED ) 还是用户管理的 (  USER_MANAGED ) |
| segment_created         | varchar(3)    | 指示索引段是否已创建 (YES) 或未 ( NO)                        |
| orphaned_entries        | varchar(3)    | 指示全局索引是否由于在 DROP/TRUNCATE PARTITION 或 MODIFY PARTITION INDEXING OFF 操作期间延迟索引维护而包含陈旧条目 |
| indexing                | varchar(7)    | 指示全局索引是否与基础表分离。                               |
