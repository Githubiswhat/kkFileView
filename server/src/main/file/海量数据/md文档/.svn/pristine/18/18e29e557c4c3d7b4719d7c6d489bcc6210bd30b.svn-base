# DROP NODE GROUP

## 功能描述

删除节点组。

## 注意事项

- DROP NODE GROUP是集群管理工具封装的内部接口，用来实现集群管理。
- 该接口仅对管理员用户开放使用。

## 语法格式

```
DROP NODE GROUP groupname;
    [ DISTRIBUTE FROM src_nodegroup_name ]
    [ TO ELASTIC GROUP ]
```

## 参数说明

- **groupname**

  要删除的节点组名。

  取值范围：已存在的节点组。

- **DISTRIBUTE FROM src_group_name**

  如果被删除的节点组是从src_group_name逻辑集群节点组重分布过来的，删除该节点组需要指定src_group_name，以便将重分布后的节点分布信息同步到src_group_name指定的逻辑集群节点组。该语句仅仅用于扩容重分布，用户不建议直接使用，以免导致数据分布错误和逻辑集群不可用。

- **ELASTIC GROUP**

  

## 示例

```
DROP NODE GROUP group_example;
```

## 相关链接