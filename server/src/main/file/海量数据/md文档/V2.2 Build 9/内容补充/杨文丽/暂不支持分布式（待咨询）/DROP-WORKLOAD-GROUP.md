# DROP WORKLOAD GROUP

## 功能描述

删除一个负载组。

## 注意事项

用户对当前数据库有DROP权限，才可以删除负载组。

## 语法格式

```
DROP WORKLOAD GROUP  [ IF EXISTS ] wg_name;
```

## 参数说明

- **IF EXISTS**

  如果指定的视图不存在，则发出一个notice而不是抛出一个错误。

- **wg_name**

  负载组名称。负载组名称不能和当前数据库里其他负载组重名。

  取值范围：字符串，已有的负载组名称。

## 示例

请参见CREATE WORKLOAD GROUP的[示例](CREATE-WORKLOAD-GROUP.md#example)。

## 相关链接

[CREATE WORKLOAD GROUP](CREATE-WORKLOAD-GROUP.md)，[ALTER WORKLOAD GROUP](ALTER-WORKLOAD-GROUP.md)