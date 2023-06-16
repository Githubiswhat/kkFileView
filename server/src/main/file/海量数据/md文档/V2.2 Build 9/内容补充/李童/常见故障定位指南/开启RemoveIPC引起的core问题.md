# 开启RemoveIPC引起的core问题

## 问题现象

操作系统配置中RemoveIPC参数设置为yes，数据库运行过程中出现宕机，并显示如下日志消息。

```shell
FATAL: semctl(1463124609, 3, SETVAL, 0) failed: Invalid argument
```

## 原因分析

当RemoveIPC参数设置为yes时，操作系统会在对应用户退出时删除IPC资源（共享内存和信号量），从而使得openGauss服务器使用的IPC资源被清理，引发数据库宕机。

## 处理分析

设置RemoveIPC参数为no。设置方法请参考[设置IPC参数](../快速入门/首次需配置设置IPC参数.md)