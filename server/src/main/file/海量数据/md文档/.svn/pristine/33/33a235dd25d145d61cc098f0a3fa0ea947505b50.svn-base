#### ALL_SEQUENCES

ALL_SEQUENCES视图提供当前用户可访问数据库用户定义的序列信息。

| 列名           | 类型         | 描述                                                         |
| -------------- | ------------ | ------------------------------------------------------------ |
| sequence_owner | name         | 序列的所有者                                                 |
| sequence_name  | name         | 序列名字                                                     |
| min_value      | int16        | 序列的最小值                                                 |
| max_value      | int16        | 序列的最大值                                                 |
| increment_by   | int16        | 序列递增值                                                   |
| cycle_flag     | character(1) | 序列达到最大值是否循环                                       |
| cache_size     | numeric      | 在内存中预分配的序列号数目                                   |
| last_number    | numeric      | 上次保存到磁盘的序列号的值                                   |
| scale_flag     | varchar(1)   | 指示这是否是可伸缩序列 ( Y) 或不是 ( N)                      |
| extend_flag    | varchar(1)   | 指示此可伸缩序列的生成值是否超出MAX_VALUE或MIN_VALUE( Y) 或不 ( N) |
| session_flag   | varchar(1)   | 指示序列值是会话私有 ( Y) 还是不是 ( N)                      |
| keep_value     | varchar(1)   | 指示在失败后重放期间是否保留序列值 ( Y) 或不 ( N)            |

