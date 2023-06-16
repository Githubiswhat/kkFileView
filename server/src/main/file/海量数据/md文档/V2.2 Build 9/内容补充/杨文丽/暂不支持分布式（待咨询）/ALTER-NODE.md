#  ALTER NODE

## 功能描述

更改集群节点。

## 注意事项

ALTER NODE是集群管理工具封装的内部接口，用来实现集群管理。该接口不建议用户直接使用，以免对集群状态造成影响。

## 语法格式

```
ALTER NODE nodename WITH
  (
    [ TYPE = nodetype,]
    [ HOST = hostname,]
    [ PORT = portnum,]
    [ HOST1 = 'hostname',]
    [ PORT1 = portnum,]
    [ HOSTPRIMARY [ = boolean ],]
    [ PRIMARY [ = boolean ],]
    [ PREFERRED [ = boolean ],]
    [ SCTP_PORT = portnum,]
    [ CONTROL_PORT = portnum,]
    [ SCTP_PORT1 = portnum,]
    [ CONTROL_PORT1 = portnum, ]
    [ NODEIS_CENTRAL [= boolean], ]
    [ NODEIS_ACTIVE [= boolean] ]
  );
```

> <div align="left"><img src="image/image1.png" style="zoom:75%"></div>
>
> PORT选项指定的端口号为节点间内部通信绑定的端口号，不同于外部客户端连接节点的端口号，可通过pgxc_node表查询。

## 参数说明

请参见的CREATE NODE的[参数说明](CREATE-NODE.md#create-node-canshu)。

## 示例



## 相关链接

[CREATE-NODE](CREATE-NODE.md)，[DROP NODE](DROP-NODE.md)