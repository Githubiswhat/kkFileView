#### ALL_IND_COLUMNS

ALL_IND_COLUMNS视图提供当前用户可访问索引中包含的列信息。

| 列名               | 类型         | 描述                                                         |
| ------------------ | ------------ | ------------------------------------------------------------ |
| index_owner        | varchar(64)  | 索引列的所有者                                               |
| index_name         | varchar(64)  | 索引名称                                                     |
| table_owner        | varchar(64)  | 表的所有者                                                   |
| table_name         | varchar(64)  | 表名                                                         |
| column_name        | name         | 列名                                                         |
| column_position    | smallint     | 列的编号。一般列从1开始向上编号。系统列则拥有（任意）赋值编号 |
| column_length      | smallint     | 对于一个固定尺寸的类型，typlen是该类型内部表示的字节数。对于一个变长类型，typlen为负值。-1表示一个“varlena”类型（具有长度字），-2表示一个以空值结尾的C字符串 |
| char_length        | numeric      | 列的数据类型如果为char，表示字节数。否则为0                  |
| descend            | character(1) | 默认DESC。指示列是按降序（DESC）还是按升序（ASC）排序        |
| collated_column_id | numeric      | 此列为其提供语言排序的列的内部序列号                         |
