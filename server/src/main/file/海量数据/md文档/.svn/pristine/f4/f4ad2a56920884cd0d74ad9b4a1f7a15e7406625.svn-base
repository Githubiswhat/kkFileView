#### ALL_VIEWS

ALL_VIEWS视图提供当前用户可访问数据库的视图信息。

| 列名                   | 类型          | 描述                                                         |
| ---------------------- | ------------- | ------------------------------------------------------------ |
| owner                  | name          | 视图所有者                                                   |
| view_name              | name          | 视图名称                                                     |
| text_length            | integer       | 视图文本的长度                                               |
| text                   | text          | 查看文本。仅当行源自当前容器时，此列才会返回正确的值。该 BEQUEATH 子句不会作为 TEXT 该视图中列的一部分出现。 |
| text_vc                | varchar(4000) | 查看文本。此列可能会截断视图文本。该 BEQUEATH 子句不会作为 TEXT_VC 该视图中列的一部分出现。 |
| type_text_length       | numeric       | 类型化视图的类型子句的长度                                   |
| type_text              | varchar(4000) | 类型化视图的类型子句                                         |
| oid_text_length        | numeric       | WITH OID 类型化视图的子句 长度                               |
| oid_text               | varchar(4000) | WITH OID 类型化视图的子句                                    |
| view_type_owner        | varchar(128)  | 如果视图是类型化视图，则视图类型的所有者                     |
| view_type              | varchar(128)  | 视图类型（如果视图是类型化视图）                             |
| superview_name         | varchar(128)  | 超级视图的名称                                               |
| editioning_view        | varchar(1)    | 留作将来使用                                                 |
| read_only              | varchar(1)    | 指示视图是否为只读 (  Y ) 或非 (  N )                        |
| container_data         | varchar(3)    | 指示视图是否包含特定于容器的数据                             |
| bequeath               | varchar(12)   | 可能的值： CURRENT_USER : 当视图是 BEQUEATH CURRENT_USER 视图 时  DEFINER : 当视图是 BEQUEATH DEFINER 视图 时 |
| origin_con_id          | varchar(256)  | 数据来源的容器的 ID                                          |
| default_collation      | varchar(100)  | 视图的默认排序规则                                           |
| containers_default     | varchar(3)    | CONTAINERS() 指示默认情况下是否启用视图(  YES ) 或不启用 (  NO ) |
| container_map          | varchar(3)    | 指示是否启用视图以与 container_map 数据库属性 (  YES ) 一起使用 (  NO ) |
| extended_data_link     | varchar(3)    | 指示是否启用视图以从根 (  YES ) 获取扩展数据链接 (  NO )     |
| extended_data_link_map | varchar(3)    | 仅为支持兼容性                                               |