# XFS文件损坏

## 问题现象

在数据库使用过程中，有极小的概率出现XFS文件系统的报错（Input/Output error , structure needs cleaning）。

## 原因分析

此为XFS文件系统问题。

## 处理方法

首先尝试umount/mount对应文件系统，重试看是否可以规避此问题。

如果问题重现，则需要参考文件系统相应的文档请系统管理员对文件系统进行修复，例如xfs_repair。文件系统成功修复后，请使用vb_ctl build命令来修复文件受损的数据节点。