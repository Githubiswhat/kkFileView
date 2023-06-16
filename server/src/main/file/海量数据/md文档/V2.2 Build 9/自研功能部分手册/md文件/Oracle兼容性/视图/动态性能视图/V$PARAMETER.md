#### V$PARAMETER

V$PARAMETER包含当前实例参数的详细信息。

| 列名                  | 类型    | 描述                           |
| --------------------- | ------- | ------------------------------ |
| num                   | bigint  | 参数编号                       |
| name                  | text    | 参数名称                       |
| type                  | integer | 参数类型                       |
| value                 | text    | 当前会话的参数值               |
| display_value         | text    | 参数值的智能显示               |
| isdefault             | boolean | 是否为默认值                   |
| isses_modifiable      | boolean | 是否可以通过alter session修改  |
| issys_modifiable      | boolean | 是否可以通过alter system修改   |
| isinstance_modifiable | boolean | 集群中每个实例参数是否可以不同 |
| ismodified            | boolean | 该参数是否已修改               |
| isadjusted            | boolean | 数据库实例的建议值             |
| isdeprecated          | boolean | 该参数是否已弃用               |
| isbasic               | boolean | 该参数是否为基本参数           |
| description           | text    | 参数描述                       |
| update_comment        | text    | 参数更新的相关注释             |
| hash                  | text    | 参数名的哈希值                 |
