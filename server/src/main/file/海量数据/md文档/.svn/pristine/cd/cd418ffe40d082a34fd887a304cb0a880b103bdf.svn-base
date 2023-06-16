# CREATE NODE GROUP

## 功能描述

创建一个新的集群节点组。

## 注意事项

- CREATE NODE GROUP是集群管理工具封装的内部接口，用来实现集群管理。
- 该接口仅对管理员用户开放使用。

## 语法格式

```
CREATE NODE GROUP groupname
[WITH ( nodename [, ... ] )]
[bucketcnt bucket_cnt]
[ BUCKETS [ ( bucketnumber [, ... ] ) ] ]
[ VCGROUP ]
[ DISTRIBUTE FROM src_nodegroup_name ]
[groupparent parent_group_name];
```

## 参数说明

- **groupname**

  节点组名称。

  取值范围：字符串，要符合标识符的命名规范。且最大长度不超过63个字符。

  节点组命名支持ASCII字符集上所有字符，但是建议用户按照标识符命名规范命名。

- **nodename**

  节点名称。

  取值范围：字符串，要符合标识符的命名规范。且最大长度不超过63个字符。

- **BUCKETS [ ( bucketnumber [, ... ] ) ]**

  BUCKETS子句是集群管理工具的内部用法，该子句不建议用户直接使用，以免对集群的正常使用造成影响。

- **VCGROUP**

  创建一个逻辑集群类型的节点组。

- DISTRIBUTE FROM src_group_name

  创建一个逻辑集群节点组，用于从src_group_name指定的逻辑集群节点组重分布数据。该子句不建议用户直接使用，以免导致数据分布错误和逻辑集群不可用。

- **groupparent parent_group_name**

  父集群名称。

## 示例

```
create node group group1 with(dn_6001_6002, dn_6003_6004);
```

## 相关链接