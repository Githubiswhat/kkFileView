## 支持RAS：对于SIGBUS信号的处理

**术语定义**

SIGBUS信号：

- 硬件故障导致SIGBUS信号。

- Linux平台上运行malloc()，没有足够的RAM会给当前进程发送SIGBUS信号（实测未复现，不同系统可能会不一样）。

- 部分架构上访问数据时有对齐的要求，例如只能从4字节边界上读取一个4字节的数据类型，否则向当前进程发送SIGBUS信号。

- 试图访问一块无文件内容相应的内存区域，比如超过文件尾的内存区域，或者以前有文件内容对应，现在为另一进程截断过的内存区域，产生SIGBUS信号。

SIGBUS信号处理，可以再数据库接受到SIGBUS信号后进行一些处理并将结果记录到日志后再终止数据库运行。

SIGBUS信号记录操作，如果因SIGBUS停库则进行记录SIGBUS信号信息到文件，数据库工具可以查询是否存在SIGBUS错误，当SIGBUS错误修复后需手动清除SIGBUS信号记录文件。

**功能描述**

结合数据库的运行信息处理SIGBUS返回值，将处理结果记录到日志后再中止数据库运行。接收到SIGBUS信号后，SIGBUS信号中signal code值如下：

| si_code       | 原因                                                         |
| ------------- | ------------------------------------------------------------ |
| BUS_ADRALN    | 无效的地址对齐                                               |
| BUS_ADRERR    | 不存在的物理地址                                             |
| BUS_OBJERR    | 特定对象硬件错误                                             |
| BUS_MCEERR_AR | 硬件内存错误导致机器进行检查（hardware memory error consumed on a machine check: action required） |
| BUS_MCEERR_AO | 在进程中检测到硬件内存错误（hardware memory error detected in process but not consumed: action optional） |

**注意事项**

SIGBUS信号处理依赖GUC参数enable_incremental_checkpoint，请确保该参数处于开启状态（默认开启）。