# 处理备机need repair(WAL)状态

## 问题现象

Vastbase 备机出现Standby Need repair(WAL)故障。

## 原因分析

因网络故障、磁盘满等原因造成主备实例连接断开，主备日志不同步，导致数据库在启动时异常。

## 处理方法

通过vb_ctl build -D 命令对故障节点进行重建，具体的操作方法请参见[vb_ctl](../工具参考/vb_ctl.md)