#### DBA_ROLE_PRIVS

DBA_ROLE_PRIVS包含当前数据库所有角色的权限信息。

| 列名         | 类型 | 描述                          |
| ------------ | ---- | ----------------------------- |
| grantee      | text | 接受授权的用户或角色名称      |
| granted_role | text | 被授权角色名称                |
| admin_option | text | 权限是否与amin option一起使用 |
| default_role | text | 是否被定义为默认              |
