# DROP RESOURCE POOL

## 功能描述

删除一个资源池。

## 注意事项

- 多租户场景下，如果删除组资源池，其业务资源池都将被删除。只有不关联用户时，资源池才能被删除。
- 用户对当前数据库有DROP权限，才可以删除资源池。

## 语法格式

```
DROP RESOURCE POOL [ IF EXISTS ] pool_name;
```

## 参数说明

- **IF EXISTS**

如果指定的存储过程不存在，发出一个notice而不是抛出一个错误。

- **pool_name**

已创建过的资源池名称。

取值范围：字符串，要符合标识符的命名规范。

## 示例

请参见CREATE RESOURCE POOL的[示例](CREATE-RESOURCE-POOL.md#example)创建资源池。

删除资源池：

```
DROP RESOURCE POOL pool1;
```

## 相关链接

[ALTER RESOURCE POOL](ALTER-RESOURCE-POOL.md)，[CREATE RESOURCE POOL](CREATE-RESOURCE-POOL.md)