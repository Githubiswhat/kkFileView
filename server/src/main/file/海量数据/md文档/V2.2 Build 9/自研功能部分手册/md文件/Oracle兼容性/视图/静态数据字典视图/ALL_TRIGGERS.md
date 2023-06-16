#### ALL_TRIGGERS

ALL_TRIGGERS视图提供当前用户可访问数据库表上的触发器信息。

| 列名              | 类型          | 描述                                                         |
| ----------------- | ------------- | ------------------------------------------------------------ |
| owner             | text          | 触发器的所有者                                               |
| trigger_name      | text          | 触发器名称                                                   |
| trigger_type      | text          | 触发类型:before/after                                        |
| triggering_event  | text          | 触发事件:insert/update/delete/truncate                       |
| table_owner       | text          | 表的所有者                                                   |
| base_object_type  | text          | 触发对象：表                                                 |
| table_name        | text          | 表名                                                         |
| column_name       | varchar(7)    | 嵌套表列的名称（如果是嵌套表触发器），否则为 NULL            |
| referencing_names | text          | 仅为支持兼容性                                               |
| when_clause       | varchar(4000) | 仅为支持兼容性                                               |
| status            | text          | 指示触发器是启用 (  ENABLED ) 还是禁用 (  DISABLED )；禁用的触发器不会触发 |
| description       | text          | 仅为支持兼容性                                               |
| action_type       | varchar(11)   | 触发体的动作类型：（CALL，PL/SQL）                           |
| trigger_body      | text          | 触发器定义                                                   |
| crossedition      | varchar(7)    | 跨版触发器类型（FORWARD,）                                   |
| before_statement  | varchar(3)    | 触发器是否有 BEFORE STATEMENT 部分 (  YES ) 或没有 (  NO )   |
| before_row        | varchar(3)    | 触发器是否有 BEFORE EACH ROW 部分 (  YES ) 或没有 (  NO )    |
| after_row         | varchar(3)    | 触发器是否有 AFTER EACH ROW 部分 (  YES ) 或没有 (  NO )     |
| after_statement   | varchar(3)    | 触发器是否有 AFTER STATEMENT 部分 (  YES ) 或没有 (  NO )    |
| instead_of_row    | varchar(3)    | 触发器是否有 INSTEAD OF 部分 (  YES ) 或没有 (  NO )         |
| fire_once         | varchar(3)    | 触发器是否仅对进行更改的用户进程触发 (  YES ) 或者触发器是否也将针对复制应用或 SQL 应用进程触发 (  NO ) |
| apply_server_only | varchar(3)    | 触发器是否仅针对复制应用或 SQL 应用进程 (  YES ) 或不 (  NO ) 触发。如果设置为 YES ，则 的设置 FIRE_ONCE 无关紧要。 |
