ssh 连接库用     jsch   这一部分还没有实现

register  这一部分用了netty

spring-context 依赖的导入         是一个IOC容器     之前没用过这种

数据库连接池用    hikari 

TaskInfo这个地方总有一点别扭





任务的版本号的作用是什么

现在是collector像register发heatbeat之后，register给collector回复一个任务的版本号

然后，调度任务与这个版本号的关系是什么？





任务列表保存在collector的本地文件

这个任务列表里面存的是什么信息？  数据源？









![image-20220729093916829](C:\Users\windows\AppData\Roaming\Typora\typora-user-images\image-20220729093916829.png)

更新到哪一个数据库？





taskmanager     databasemanager      schedule     metricpool   