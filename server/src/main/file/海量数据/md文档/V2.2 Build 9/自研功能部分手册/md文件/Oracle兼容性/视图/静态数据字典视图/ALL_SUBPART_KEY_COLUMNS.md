#### ALL_SUBPART_KEY_COLUMNS

ALL_SUBPART_KEY_COLUMNS视图提供当前用户可访问数据库分区表的子分区的关键列的信息。

| 列名            | 类型         | 描述                            |
| --------------- | ------------ | ------------------------------- |
| owner           | text         | 分区表的所有者                  |
| schema_name     | text         | 分区表所属模式名                |
| name            | text         | 字段所在的表名                  |
| object_type     | character(5) | 仅为支持兼容性。该值始终为TABLE |
| column_name     | text         | 分区键的列名                    |
| column_position | integer      | 列所在分区键的位置              |