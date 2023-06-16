##  Quartz 

### 1.简介

​		OpenSymphony提供的强大的一个开源的任务调度框架

​		三个核心概念

- 调度器(scheduler)
- 任务(job)
- 触发器(trigger)



​	job 是一个接口，有一个execute的函数，需要执行的任务写在这个函数这里

​	trigger用来规定任务触发的条件

​	scheduler 用来绑定 job 和 trigger

​	

​		一些其他概念

- jobDetail    
- jobBuilder
- triggerBuilder



jobDetail  用来存储 job 以及和 这个job相关的一些信息

jobBuilder 提供一系列好用的接口来创建jobDetail

triggerBuilder 提供一系列好用的接口来创建trigger





一些思考：

一个任务执行，那么会有两个结果，一个成功，一个失败，那么如何知道是什么结果？

调度的结果是什么？

如何调度的，这个过程是什么样子的？

触发除了时间还有其他类型么？

架构是什么样子的？



设计模式

- buidler模式
- factory模式
- 组件模式
- 链式编程



cron表达式

1. 有七个值，第七个是可选的，也就是平时看到六个
2. 秒  分  时  日  月  周  年





