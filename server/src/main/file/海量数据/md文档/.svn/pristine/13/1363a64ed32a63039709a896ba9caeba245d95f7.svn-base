# GUC参数log_directory设置不正确引起的core问题

## 问题现象

数据库进程拉起后出现coredump，日志无内容。

## 原因分析

GUC参数log_directory设置的路径不可读取或无访问权限，数据库在启动过程中进行校验失败，通过panic日志退出程序。

## 处理办法

GUC参数log_directory设置为合法路径，具体请参考[log_directory](../开发者指南/记录日志的位置.md)。

