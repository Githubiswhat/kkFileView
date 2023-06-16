#### ALL_CONSTRAINTS

ALL_CONSTRAINTS视图提供当前用户可访问表上的约束信息。

| 列名                | 类型          | 描述                                                         |
| ------------------- | ------------- | ------------------------------------------------------------ |
| owner               | text          | 约束定义的所有者                                             |
| constraint_name     | varchar(64)   | 约束名字                                                     |
| constraint_type     | text          | c = 检查约束， f = 外键约束， p = 主键约束， u = 唯一约束， t = 约束触发器， x = 排他约束 |
| table_name          | varchar(64)   | 表的名字                                                     |
| search_condition    | text          | 检查约束条件                                                 |
| search_condition_vc | varchar(4000) | 检查约束的搜索条件文本。此列可能会截断搜索条件。             |
| r_owner             | text          | 约束中引用表的所有者                                         |
| r_constraint_name   | text          | 被引用表的唯一约束定义的名称                                 |
| status              | varchar(8)    | 约束的执行状态：ENABLED或者DISABLED                          |
| delete_rule         | text          | 外键删除动作代码： a = 无动作， r = 限制， c = 级联， n = 置空， d = 置为默认值 |
| deferrable          | boolean       | 该约束是否能被延迟                                           |
| deferred            | boolean       | 该约束是否默认被延迟                                         |
| validated           | varchar(13)   | 仅为支持兼容性。                                             |
| generated           | varchar(14)   | 约束的名称是用户生成的 (  USER NAME ) 还是系统生成的 (  GENERATED NAME ) |
| bad                 | varchar(3)    | 此约束是否以不明确的方式 ( ) 指定世纪 BAD (NULL)。           |
| rely                | varchar(4)    | 当 VALIDATED  =  NOT   VALIDATED 时,此列指示是否要在查询重写 时考虑约束；当 VALIDATED  =  VALIDATED ，此列没有意义 |
| last_change         | data          | 上次启用或禁用约束的时间                                     |
| index_owner         | varchar(64)   | 索引的所有者                                                 |
| index_name          | varchar(64)   | 索引的名字                                                   |
| invalid             | varchar(7)    | 指示约束是否无效 (  INVALID ) 或无效 (NULL)                  |
| view_related        | varchar(14)   | 指示约束是否依赖于视图 (  DEPEND ON VIEW ) 或不 (NULL)       |
| orgin_con_id        | varcahr(256)  | 数据来源的容器的 ID                                          |

#### 