#### ALL_OBJECTS

ALL_OBJECTS视图提供当前用户可访问数据库中所有对象的信息。

| 列名              | 类型                     | 描述                                                         |
| ----------------- | ------------------------ | ------------------------------------------------------------ |
| owner             | name                     | 对象的所有者                                                 |
| object_name       | name                     | 对象名称                                                     |
| subobject_name    | varchar(128)             | 子对象的名称（例如，分区）                                   |
| object_id         | oid                      | 对象的字典对象编号                                           |
| data_object_id    | numeric                  | 包含对象的段的字典对象编号。                                 |
| object_type       | name                     | 对象的类型（例如INDEX，FUNCTION，PROCEDURE，TABLE，INDEX）   |
| created           | timestamp with time zone | 创建对象的时间戳                                             |
| last_ddl_time     | timestamp with time zone | 由 DDL 语句产生的对象和依赖对象的最后修改的时间戳（包括授权和撤销） |
| timestamp         | varchar(19)              | 对象规范的时间戳（字符数据）                                 |
| status            | varchar(7)               | 仅为支持兼容性，对象的状态始终VALID                          |
| temporary         | varchar(1)               | 指示对象是否是临时对象（当前会话只能看到其自身放置在该对象中的数据）（Y）或（N） |
| generated         | varchar(1)               | 指示此对象的名称是否是系统生成的 (  Y ) 或不是 (  N )        |
| secondary         | varchar(1)               | 指示这是否是由 ODCIIndexCreate Oracle Data Cartridge         |
| namespace         | oid                      | 对象的命名空间                                               |
| edition_name      | varchar(128)             | 所在的版本名称                                               |
| sharing           | varchar(13)              | 该字段的值可以是：</br>DATA LINK：对象是数据链接的或数据链接到根中的对象</br>METADATA LINK：对象是元数据链接或元数据链接到根中的对象</br>EXTENED DATA LINK：对象是扩展数据链接或扩展数据链接到根中的对象</br>NONE：以上均不适用 |
| editionable       | varchar(1)               | 该字段的值可以是：</br>Y：标记为 EDITIONABLE 的对象</br>N：标记为NONEEDITIONABLE 的对象</br>NULL：在数据库中不可编辑的对象 |
| oracle_maintained | varchar(1)               | 表示对象是否由 Oracle 提供的脚本（例如 catalog.sql 或 catproc.sql）创建和维护。 |
| application       | varchar(1)               | 对象是否为应用程序公共对象 (  Y ) 或不是 (  N )              |
| default_collation | varchar(100)             | 对象的默认排序规则                                           |
| duplicated        | varchar(1)               | 此对象是否在此分片上重复 (  Y ) 或不 (  N )                  |
| sharded           | varchar(1)               | 此对象是否已分片 (  Y ) 或不 (  N )                          |
| created_appid     | numeric                  | 创建对象的应用程序 ID                                        |
| created_vsnid     | numeric                  | 创建对象的应用程序版本的 ID                                  |
| modified_appid    | numeric                  | 上次修改对象的应用程序 ID                                    |
| modified_vsnid    | numeric                  | 上次修改对象的应用程序 ID                                    |
