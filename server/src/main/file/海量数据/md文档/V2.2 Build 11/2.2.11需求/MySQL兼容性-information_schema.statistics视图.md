# MySQL兼容性-information_schema.statistics视图

此需求共涉及两篇新增文档，这一篇加在开发者指南->schem->information_schem->statistics



INFORMATION_SCHEMA.STATISTICS视图用于提供对索引相关信息的查询。

| 名称          | 描述                        | 备注                                                   |
| ------------- | --------------------------- | ------------------------------------------------------ |
| table_catalog | 包含索引的表所在的catalog名 | 值为def                                                |
| table_schema  | 包含索引的表所在的schema名  | -                                                      |
| table_name    | 包含索引的表名              | -                                                      |
| non_unique    | 是否为唯一索引              | 0：唯一索引<br>1：非唯一索引                           |
| index_schema  | 索引所在的schema名          | -                                                      |
| index_name    | 索引名                      | -                                                      |
| seq_in_index  | 索引中的序列值              | 从1开始                                                |
| column_name   | 列名                        | -                                                      |
| collation     | 列值的储存方式              | A：升序<br>B：降序<br>NULL：非排序                     |
| cardinality   | 索引中唯一值的估计          | -                                                      |
| sub_part      | 索引的前缀                  | 恒为空                                                 |
| packed        | 索引的填充方式              | 恒为空                                                 |
| nullable      | 是否包含NULL值              | yes：可能包含<br>''：不包含                            |
| index_type    | 索引类型                    | 枚举：BTREE、HASH、FULLTEXT、RTREE                     |
| comment       | 索引的其他信息              | 恒为空                                                 |
| index_comment | 创建索引时的注释信息        | -                                                      |
| is_visible    | 对优化器是否可见            | 恒为空                                                 |
| expression    | 索引表达式                  | 为函数索引时，值为函数表达式<br>非函数索引时，值为NULL |