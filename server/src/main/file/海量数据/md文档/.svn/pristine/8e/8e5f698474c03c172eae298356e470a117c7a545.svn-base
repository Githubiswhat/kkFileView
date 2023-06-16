#### DBA_SEGMENTS

DBA_SEGMENTS描述数据库中的所有段分配的存储。

| 列名                 | 类型             | 描述                                                         |
| -------------------- | ---------------- | ------------------------------------------------------------ |
| owner                | name             | 段的所有者                                                   |
| segment_name         | name             | 段的名称                                                     |
| partition_name       | varchar(128)     | 段所在分区的名称（设置NULL为非分区对象）                     |
| segment_type         | varchar(18)      | 段的类型                                                     |
| segment_subtype      | varchar(10)      | LOB 段的子类型：SECUREFILE、ASSM、MSSM和NULL                 |
| tablespace_name      | name             | 包含段的表空间的名称                                         |
| header_file          | oid              | 包含段头的文件编号                                           |
| header_block         | oid              | 包含段头的块的编号                                           |
| bytes                | bigint           | 段的大小，以字节为单位                                       |
| blocks               | double precision | 段的大小（以 Oracle 块为单位）                               |
| extents              | numeric          | 分配给段的区数                                               |
| initial_extent       | numeric          | 在创建时为段的初始范围请求的大小（以字节为单位）             |
| next_extent          | numeric          | 要分配给该段的下一个扩展区的大小（以字节为单位）             |
| min_extents          | numeric          | 段中允许的最小范围数                                         |
| max_extents          | numeric          | 段中允许的最大范围数                                         |
| max_size             | numeric          | 段中允许的最大块数                                           |
| retention            | varchar(7)       | SECUREFILE段 的保留选项                                      |
| minretention         | numeric          | SECUREFILE分段 的最短保留期限                                |
| pct_increase         | numeric          | 增加下一个要分配的范围大小的百分比                           |
| freelists            | numeric          | 分配给该段的进程空闲列表数                                   |
| freelist_groups      | numeric          | 分配给该段的空闲列表组数                                     |
| relative_fno         | numeric          | 段头的相对文件号                                             |
| buffer_pool          | varchar(7)       | 用于段块的缓冲池：DEFAULT、KEEP、RECYCLE                     |
| flash_cache          | varchar(7)       | 用于段块的数据库智能闪存缓存提示（仅限 Solaris 和 Oracle Linux 功能） |
| cell_flash_cache     | varchar(7)       | 用于段块的单元闪存缓存提示：DEFAULT、KEEP、NONE              |
| inmemory             | varchar(8)       | 此段 的内存中列存储是启用 ( ENABLED) 还是禁用 (DISABLED )    |
| inmemory_priority    | varchar(8)       | 内存列存储填充的优先级                                       |
| inmemory_distribute  | varchar(15)      | 内存列存储在ORACLE RAC环境中的分布方式                       |
| inmemory_duplicate   | varchar(13)      | Oracle RAC 环境中内存列存储的重复设置                        |
| inmemory_compression | varchar(17)      | 内存列存储的压缩级别                                         |
| cellmemory           | varchar(24)      | 存储单元闪存缓存中的列压缩值                                 |

