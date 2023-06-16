# CREATE NODE

## 功能描述

创建一个新群集节点。

## 注意事项

CREATE NODE是集群管理工具封装的内部接口，用来实现集群管理。该接口不建议用户直接使用，以免对集群状态造成影响。

## 语法格式

```
CREATE NODE nodename WITH
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



## 参数说明<a name='create-node-canshu'>

- **nodename**

  节点名称。

  取值范围：字符串，要符合标识符的命名规范。

- **TYPE = nodetype**

  指定节点的类型。

  取值范围：

  - 'coordinator'
  - 'datanode'

- **HOST = hostname**

  指定节点对应的主机名称或者IP地址。

- **PORT = portnum**

  指定节点绑定的主机端口号。

- **HOST1 = hostname**

  指定节点对应的备机名称或者IP地址。

- **PORT1 = portnum**

  指定节点绑定的备机端口号。

- **HOSTPRIMARY**

- **PRIMARY = boolean**

  声明该节点是否为主节点。主节点允许做读写操作，否则只允许读操作。

  取值范围：

  - true
  - false（默认值）

- **PREFERRED = boolean**

  声明该节点是否为读操作的首选节点。

  取值范围：

  - true
  - false（默认值）

- **SCTP_PORT = portnum**

  主机TCP代理通信库或SCTP通信库使用的数据传输通道监听端口，使用TCP或SCTP协议监听连接。

- **CONTROL_PORT = portnum**

  主机TCP代理通信库或SCTP通信库使用的控制传输通道监听端口，使用TCP协议监听连接。

- **SCTP_PORT1 = portnum**

  备机TCP代理通信库或SCTP通信库使用的数据传输通道监听端口，使用TCP或SCTP协议监听连接。

- **CONTROL_PORT 1= portnum**

  备机TCP代理通信库或SCTP通信库使用的控制传输通道监听端口，使用TCP协议监听连接。

- **[ NODEIS_CENTRAL [= boolean], ]**

- **[ NODEIS_ACTIVE [= boolean] ]**

  

## 示例

1、创建一个新群集节点。

```
CREATE NODE nodeq WITH type-'datanode' HOST = '172.16.105.57' PORT=5432;
```



## 相关链接

[ALTER NODE](ALTER-NODE.md)，[DROP NODE](DROP-NODE.md)