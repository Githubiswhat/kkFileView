## 报告操作



## 采集配置

![image-20220721142835084](C:\Users\windows\AppData\Roaming\Typora\typora-user-images\image-20220721142835084.png)

![image-20220721142106292](C:\Users\windows\AppData\Roaming\Typora\typora-user-images\image-20220721142106292.png)

- GraphCollector                 dao					collectorService.DTO2POJO()
- GraphCollectorReq          dto                    collectorService.POJO2DTO()
- PageCollectoer         ![image-20220721142352278](C:\Users\windows\AppData\Roaming\Typora\typora-user-images\image-20220721142352278.png)



- 













## 备份管理

- 备份任务
- 备份计划
- 数据源数据库
- 数据源角色



## 菜单管理



## 大屏监控

1. TPS：	

   Transactions Per Second，意思是每秒事务数。一个事务是指客户端向服务器发送请求然后服务器做出反应的过程，具体的事务定义，可以是一个接口、多个接口、一个业务流程等等。以单接口定义为事务举例，每个事务包括了如下3个过程：

   （1）向服务器发请求
   （2）服务器自己的内部处理（包含应用服务器、数据库服务器等）
   （3）服务器返回结果给客户端
   如果每秒能够完成 N 次以上3个过程，TPS 就是 N。

   TPS 是软件测试结果的测量单位。我们在进行服务性能压测时，接口层面最常关注的是最大 TPS 以及接口响应时间，个人理解 TPS 可以指一组逻辑相关的请求，而服务整体处理能力取决于处理能力最低模块的TPS值。

2. QPS：

   Queries Per Second，意思是每秒查询率。指一台服务器每秒能够响应的查询次数，用于衡量特定的查询服务器在规定时间内所处理流量多少，主要针对专门用于查询的服务器的性能指标，比如dns，它不包含复杂的业务逻辑处理，比如数据库中的每秒执行查询sql的次数。QPS 只是一个简单查询的统计显然，不能描述增删改等操作，显然它不够全面，所以不建议用 QPS 来描述系统整体的性能；

   QPS 基本类似于 TPS，但是不同的是，对于一个事务访问，会形成一个 “ T ”；但一次 " T " 中，可能产生多次对服务器的请求，服务器对这些请求，就可计入 QPS 之中。

3. 区别：

   （1）如果是对一个查询接口压测，且这个接口内部不会再去请求其它接口，那么 TPS = QPS，否则，TPS ≠ QPS

   （2）如果是容量场景，假设 N 个接口都是查询接口，且这个接口内部不会再去请求其它接口，QPS = N * TPS 

   

## 告警操作

- 告警规则
- ConditionReq   (规则条件列表，用以生成表达式)



## 告警记录



## 恢复管理



## 会话管理

​	通过 dbid 到数据源，然后再查询该数据源的会话



## 集群操作

*LSN* 由 DB2® 产品中的许多组件用来维护数据库一致性和完整性。

ETCD是用于共享配置和服务发现的分布式，一致性的KV存储系统。



## 监控数据源



## 脚本配置模块



## 脚本任务模块



## 卡片操作



## 日志管理



## 审计日记



## 数据源



## 数据源分组



## 数据源业务



## 物理备份集



## 物理备份记录



## 物理备份 



## 系统版本



## 系统模块



## 系统配置



## 巡检任务模块



## 仪表盘模块



## 用户操作接口



## 指标操作





















## 分页操作

```java
PageHelper.start(page,pageSize);
List<Data> list = getFromDatebase();
PageInfo<Data> page_ist = new PageInfo<>(list);
```



## 名词

- HA  Highly Available  是双机集群系统简称





数据采集
Prometheus采用统一的Restful API方式获取数据，具体是调用HTTP GET请求或metrics数据接口获取监控数据。

调用exporter返回的一个监控数据样本：

"# HELP"提供帮助信息

"# TYPE"代表metric类型

```
# HELP http_requests_total The total number of HTTP requests.
# TYPE http_requests_total counter
http_requests_total{method="post",code="200"}	95066363000
http_requests_total{method="post",code="400"}	66363000
```





