#### V$OBJECT_USAGE

V$OBJECT_USAGE包含数据库收集的用户索引使用情况的统计信息。

| 列名             | 类型 | 描述             |
| ---------------- | ---- | ---------------- |
| index_name       | name | 索引名称         |
| table_name       | name | 索引所在的表名   |
| monitoring       | text | 该索引是否被监控 |
| used             | text | 该索引是否被使用 |
| start_monitoring | text | 开始监控的时间   |
| end_monitoring   | text | 停止监控的时间   |
