## 新增特性：

- 兼容性：兼容MySQL大部分常用功能和语法

  提供dolphin插件，从关键字、数据类型、常量与宏、函数和操作符、表达式、类型转换、DDL/DML/DCL语法、存储过程/自定义函数、系统视图等方面兼容MySQL。

  Build11版本新增对以下语法点的支持（只列举部分典型语法，详见[MySQL兼容性](../开发者指南/MySQL兼容性.md)）：

  - 数据类型：ENUM、SET、FIXED、LONGBLOB、MEDIUMBLOB、TINYBLOB、MEDIUM INT、DATETIME、YEAR、NVARCHAR以及无符号整数。
  - 操作符：:=、^(异或)、<=>（不等于）、||、&&、regexp、not regexp、rlike、DIV、MOD、XOR、like binary、not like binary。
  - 系统函数：if、ifnull、isnull、strcmp、locate、lcase、ucase、insert、bin、chara、elt、field、find_int_set、hex、space、soundex、length、convert、format、rand、crc32、conv、now、sysdate、current_time、dayofmonth、is_ipv4、inet_aton、inet_ntoa、is_ipv6、inet6_aton、export_set、bit_bool、json_array、json_quote、last_insert_id、group_concat。

- statement_history视图诊断能力增强

  - 备机支持statement_history视图，满足备机慢SQL诊断要求。
  - statement_history增加对waitevents的统计，记录慢SQL执行时等待事件耗时和次数。

- 发布订阅能力增强，发布订阅能力增强。

  - 支持发布端主备切换后订阅关系不断开。
  - 支持同步订阅关系创建前的基础数据。
  - 支持备份恢复后复制槽不丢失，保证发布订阅的连接正常。
  - 支持以二进制格式发送数据。

- 行存表压缩能力增强

  通过对行存数据进行压缩的操作，改变数据页面的存储状态。通过增加一个映射管理层将压缩页面分块落盘。整体过程发生在数据库脏页刷盘过程，对数据库的上层逻辑不影响，对用户不感知。

  满足TPCC测试模型中，压缩率2:1以上，且性能劣化小于5%。

- 细粒度Any权限增强

  - ALTER ANY TYPE、DROP ANY TYPE
  - ALTER ANY SEQUENCE、DROP ANY SEQUENCE、SELECT ANY SEQUENCE
  - ALTER ANY INDEX、DROP ANY INDEX
  - CREATE ANY TRIGGER、ALTER ANY TRIGGER、DROP ANY TRIGGER
  - CREATE ANY SYNONYM、DROP ANY SYNONYM

- DBMind自治运维平台

  构建端到端自治运维平台：新增异常检测能力，完善自监控、自诊断、自调优能力。

  - DBMind服务化：提供简易的部署能力、通过新增cmd exporter扩充采集指标；将原有的openGauss-exporter扩展为Agent, 便于获得即时信息；提供多种形式的功能API，便于与用户已有的运维平台对接和集成。
  - 异常检测：通过对监控到的指标进行分析，可以给出系统异常状态波动告警，包括基于规则的和基于算法的两种模式。其中，基于算法的包括对spike, mean shift等典型异常场景进行分析。

- 智能优化器

  - 实现库内Bayes网络算法并基于此实现智能统计信息以提高多列基数估计准确度，进而提升生成的执行计划质量。
  - 计划自适应选择解决因数据倾斜、索引不准、使用Offset查询等引起的计划跳变难题，性能提升1x以上。

- 基础算子性能提升

  - 新选择率模型典型场景准确率提升1X，性能提升1X。
  - 分区表页面估算优化典型场景性能提升20%。
  - Partition Iterator算子优化典型场景性能提升5%。
  - 函数依赖特性支撑多列查询典型场景准确率提升1X。
  - SeqScan算子优化典型场景性能提升10%。

