## OCI/OCCI接口

**功能描述**

支持Oracle Call Interface和Oracle C++ Call Interface的函数，具体见下表：

| 函数                   | 描述                                                         |
| :--------------------- | ------------------------------------------------------------ |
| OCILogon               | 创建一个简单的登录会话                                       |
| OCILogoff              | 释放使用OCILogon登录的会话                                   |
| SQ                     | 执行模块实现函数                                             |
| OCIStmtPrepar          | 函数实现准备要执行的SQL或PL / SQL语句                        |
| OCIBindByPos           | 在SQL语句或PL / SQL块中的程序变量和占位符之间创建关联        |
| OCIBindByName          | 函数实现在SQL语句或PL / SQL块中的程序变量和占位符之间创建关联 |
| OCIBindArrayOfStruct   | 函数实现为静态数组绑定设置跳过参数                           |
| OCIDefineByPos         | 函数实现将选择列表中的项目与类型和输出数据缓冲区关联         |
| OCIDefineArrayOfStruct | 指定在多行多列结构读取数组中使用的静态数组定义所必需的其他属性 |
| OCIStmtExecute         | 函数实现将应用程序请求与服务器关联                           |
| OCIStmtFetch           | 函数实现从查询中获取行                                       |
| OCITransStart          | 函数实现设置事务开始                                         |
| OCITransCommit         | 函数实现事务提交                                             |
| OCITransRollback       | 函数实现事务回滚                                             |
| OCIHandleAlloc         | 函数返回指向已分配和初始化的句柄的指针                       |
| OCIHandleFree          | 函数实现该调用显式取消分配句柄                               |
| OCIErrorGet            | 在提供的缓冲区中返回错误消息和Oracle数据库错误代码           |
| OCIDateToText          | 函数实现将日期类型转换为字符串                               |
| OCINumberFromText      | 函数实现将number类型转换为字符类型                           |
| OCINumberFromReal      | 函数实现将浮点类型转化为number类型                           |
| OCINumberfromInt       | 函数实现将int类型转换为number类型                            |
| OCIDateSysDate         | 函数实现获取客户端的当前系统日期和时间                       |
| OCIDateCompare         | 函数实现两个日期进行比较                                     |
| OCINumberCmp           | 函数实现将两个number类型进行比较                             |
| OCINumberAdd           | 函数实现将两个number类型值进行相加                           |
| OCINumberSub           | 函数实现将两个number类型值进行相减                           |
| OCINumberMul           | 函数实现将两个number类型值进行相乘                           |
| OCINumberDiv           | 函数实现将两个number类型值进行相除                           |
| OCIParamGet            | 函数返回由描述句柄或语句句柄中的位置指定的参数的描述符       |

**注意事项**

该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。