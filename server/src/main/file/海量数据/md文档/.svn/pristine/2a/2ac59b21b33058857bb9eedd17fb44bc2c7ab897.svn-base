#### ALL_TAB_COLUMNS

ALL_TAB_COLUMNS视图提供当前用户可访问表和视图中所有列的信息。

| 列名                 | 类型                        | 描述                                                         |
| -------------------- | --------------------------- | ------------------------------------------------------------ |
| owner                | varchar(64)                 | 字段所在表或视图的所有者                                     |
| table_name           | varchar(64)                 | 表名                                                         |
| column_name          | varchar(64)                 | 列名                                                         |
| data_type            | varchar(128)                | 列的数据类型                                                 |
| data_type_mod        | varchar(3)                  | 列的数据类型修饰符                                           |
| data_type_owner      | varchar(64)                 | 列数据类型的所有者                                           |
| data_length          | integer                     | 列的长度（以字节为单位）                                     |
| data_precision       | integer                     | 数据类型的精度                                               |
| data_scale           | integer                     | 数字小数点右边精度                                           |
| nullable             | bpchar                      | 列值是否允许为空                                             |
| column_id            | integer                     | 列在表中的顺序编号                                           |
| default_length       | numeric                     | 列的默认值的长度                                             |
| data_default         | text                        | 列的默认值                                                   |
| num_distinct         | numeric                     | 列中不同值的数量。保留此列是为了与 Oracle7 向后兼容。此信息现在在 {TAB\|PART}_COL_STATISTICS 视图中。 |
| low_value            | raw                         | 列中的低值。保留此列是为了与 Oracle7 向后兼容。此信息现在在 {TAB\|PART}_COL_STATISTICS 视图中。 |
| high_value           | raw                         | 列中的高值。保留此列是为了与 Oracle7 向后兼容。此信息现在在 {TAB\|PART}_COL_STATISTICS 视图中。 |
| density              | numeric                     | /                                                            |
| num_nulls            | numeric                     | 列中的 NULL 数                                               |
| num_buckets          | numeric                     | 列的直方图中的桶数                                           |
| last_analyzed        | timestamp without time zone | 最近分析此列的日期                                           |
| sample_size          | numeric                     | 用于分析此列的样本量                                         |
| character_set_name   | varchar(44)                 | 字符集名称                                                   |
| char_col_decl_length | numeric                     | 字符类型列的声明长度                                         |
| global_stats         | varchar(3)                  | GLOBAL_STATS 如果统计信息被收集或增量维护，将 YES 是，否则它将是 NO |
| user_stats           | varchar(3)                  | 指示统计信息是否由用户直接输入 (  YES ) 或不是 (  NO )       |
| avg_col_len          | numeric                     | 列的平均长度（以字节为单位）                                 |
| char_length          | numeric                     | 以字符显示列的长度。                                         |
| char_used            | varchar(1)                  | 指示该列使用 BYTE 长度语义 (  B ) 或 CHAR 长度语义 (  C )，或者数据类型是否不是以下任何一种 (NULL)：CHAR,VARCHAR2,NCGAR,NVACHAR2 |
| v80_fmt_image        | varchar(3)                  | 指示列数据是否为 8.0 版图像格式 (  YES ) 或不是 (  NO )      |
| data_upgraded        | varchar(3)                  | 指示列数据是否已升级为最新类型版本格式（ YES ）或未升级（ NO ） |
| histogram            | varchar(15)                 | 直方图类型                                                   |
| default_on_null      | varchar(3)                  | 指示列是否具有 DEFAULT ON NULL 语义 (  YES ) 或没有 (  NO )  |
| identity_column      | varchar(3)                  | 指示这是否是标识列 (  YES ) 或不是 (  NO )                   |
| evaluation_edition   | varchar(128)                | 解析表达式列中引用的编辑对象的版本名称                       |
| unusable_before      | varchar(128)                | 列可用的最旧版本的名称                                       |
| unusable_beginning   | varchar(128)                | 列永久不可用的最旧版本的名称                                 |
| collation            | varchar(100)                | 列的排序规则。仅适用于具有字符数据类型的列。                 |