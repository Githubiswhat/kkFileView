# ALTER NODE GROUP

## 功能描述

修改一个node group的信息。

## 注意事项

- 只有系统管理员可以修改node group信息。
- 修改node group操作都是系统内部操作，除了SET DEFAULT语法之外，其他操作都需要在维护模式下（调用set xc_maintenance_mode=on;）。
- ALTER NODE GROUP语法仅仅应该在数据库内部使用，使用者不应该手动调用这些SQL语句，否则会导致数据库系统数据不一致。

## 语法格式

```
ALTER NODE GROUP groupname
    | SET DEFAULT
    | RENAME TO new_group_name
    | SET VCGROUP RENAME TO new_group_name
    | SET NOT VCGROUP
    | SET TABLE GROUP new_group_name
    | COPY BUCKETS FROM src_group_name
    | ADD NODE ( nodename [, ... ] )
    | DELETE NODE ( nodename [, ... ] )
    | RESIZE TO dest_group_name
    | SET VCGROUP WITH GROUP new_group_name
```

## 参数说明

- **groupname**

  需要修改的node group名称。

  取值范围：字符串，要符合标识符的命名规范。

- **SET DEFAULT**

  将系统中除了groupname指定的node group之外的其他node group对象的in_redistribution字段设置为'y'。考虑到兼容以前版本，该语法仍然保留，且不需要设置维护模式。

- **RENAME TO new_group_name**

  将groupname指定的node group的名字修改为new_group_name。

- **SET VCGROUP RENAME TO new_group_name**

  将整个物理集群转换为一个逻辑集群，转换后groupname是逻辑集群名称，原物理集群名称修改为new_group_name。

- **SET NOT VCGROUP**

  将所有逻辑集群转换为普通的node group，所有逻辑集群的group_kind从'v' 变成 'n'。

- **SET TABLE GROUP new_group_name**

  将所有CN节点的pgxc_class表中pgroup字段是group_name的记录修改为new_group_name。

- **COPY BUCKETS FROM src_group_name**

  从src_group_name表示的NodeGroup中，将group_members字段和group_buckets字段的内容拷贝到groupname所表示的NodeGroup中。

- **ADD NODE ( nodename [, ... ] )**

  从groupname指定的NodeGroup中增加指定的节点，这些新增节点在pgxc_node系统表中存在。该语句仅仅修改系统表，不会进行实际的节点添加和数据重分布，用户不应该直接调用该SQL语句。

- **DELETE NODE ( nodename [, ... ] )**

  从groupname指定的NodeGroup中，将指定的节点移除，这些被移除的节点仍然存在于pgxc_node系统表中。该语句仅仅修改系统表，不会进行实际的节点移除和数据重分布，用户不应该直接调用该SQL语句。

- RESIZE TO dest_group_name

  设置集群resize操作标志，将groupname所表示的NodeGroup设置为重分布的源NodeGroup，并取消is_installation标志；同时将desst_group_name设置为重分布的目的NodeGroup，并设置is_installation标志。

- **SET VCGROUP WITH GROUP new_group_name**

  将整个物理集群转换为一个逻辑集群，转换后groupname仍是物理集群，new_group_name为转换后的逻辑集群名称。

## 示例



## 相关链接