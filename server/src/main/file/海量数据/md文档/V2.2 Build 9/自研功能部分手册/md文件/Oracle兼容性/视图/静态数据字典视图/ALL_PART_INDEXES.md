#### ALL_PART_INDEXES

ALL_PART_TABLES视图提供当前用户可访问数据库中的分区表上的索引信息。

| 列名                      | 类型          | 描述                                                         |
| ------------------------- | ------------- | ------------------------------------------------------------ |
| owner                     | varchar(128)  | 分区表索引的所有者                                           |
| index_name                | varchar(128)  | 分区表索引的名称                                             |
| table_name                | varchar(128)  | 分区表索引所属的分区表名称                                   |
| partitioning_type         | varchar(9)    | 分区表的分区策略                                             |
| subpartitioning_type      | varchar(9)    | 复合分区分区策略                                             |
| partition_count           | bigint        | 分区表索引的分区个数                                         |
| def_subpartition_count    | bigint        | 复合分区默认的子分区数量（如果指定的话）                     |
| partitioning_key_count    | integer       | 分区表的分区键个数                                           |
| subpartitioning_key_count | integer       | 对于复合分区索引，子分区键中的列数                           |
| locality                  | varchar(6)    | 指示分区索引是本地（LOCAL）还是全局（GLOBAL）                |
| alignment                 | varchar(12)   | 指示分区索引是带前缀 ( PREFIXED) 还是不带前缀 ( NON_PREFIXED) |
| def_tablespace_name       | name          | 分区表索引的表空间名称                                       |
| def_pct_free              | numeric       | 对于本地索引， PCTFREE 添加表分区时使用的默认值              |
| def_ini_trans             | numeric       | 对于本地索引， INITRANS 添加表分区时使用的默认值             |
| def_max_trans             | numeric       | 对于本地索引， MAXTRANS 添加表分区时使用的默认值             |
| def_initial_extent        | varchar(40)   | 对于本地索引，添加表分区或未指定值时使用的默认值（  INITIAL 在 Oracle 块中）DEFAULT INITIAL |
| def_next_extent           | varchar(40)   | 对于本地索引，添加表分区或未指定值时使用的默认值（  NEXT 在 Oracle 块中）DEFAULTNEXT |
| def_min_extents           | varchar(40)   | 对于本地索引，MINEXTENTS添加表分区时使用的默认值，或者DEFAULT如果没有MINEXTENTS指定值 |
| def_max_extents           | varchar(40)   | 对于本地索引， MAXEXTENTS 添加表分区时使用的默认值，或者DEFAULT如果没有 MAXEXTENTS 指定值 |
| def_max_size              | varchar(40)   | 对于本地索引， MAXSIZE 添加表分区时使用的默认值，或者DEFAULT如果没有 MAXSIZE 指定值 |
| def_pct_increase          | varchar(40)   | 对于本地索引， PCTINCREASE 添加表分区时使用的默认值，或者 DEFAULT 如果没有 PCTINCREASE 指定值 |
| def_freelists             | numeric       | 对于本地索引， FREELISTS 添加表分区时使用的默认值            |
| def_freelist_groups       | numeric       | 对于本地索引， FREELIST GROUPS 添加表分区时使用的默认值      |
| def_logging               | varchar(7)    | 对于本地索引，LOGGING添加表分区时要使用的默认属性            |
| def_buffer_pool           | varchar(7)    | 对于本地索引，添加表分区时要使用的默认缓冲池：DEFAULT,KEEP,RECYCLE,NULL |
| def_flash_cache           | varchar(7)    | 对于本地索引，添加表分区时要使用的默认数据库智能闪存缓存提示：DEFAULT,KEEP,NULL |
| def_cell_flash_cache      | varchar(7)    | 对于本地索引，添加表分区时要使用的默认单元闪存缓存提示：DEFAULT,KEEP,NULL |
| def_parameters            | varchar(1000) | 域索引的默认参数字符串                                       |
| INTERVAL                  | varchar(1000) | 区间值字符串                                                 |
| autolist                  | varchar(3)    | 指示本地索引是否通过自动列表分区 (YES)分区 ( NO)             |
| interval_subpartition     | varchar(1000) | 子分区间隔值的字符串                                         |
| autolist_subpartition     | varchar(3)    | 指示本地索引是否通过自动列表分区 (YES)进行子分区 ( NO)       |
