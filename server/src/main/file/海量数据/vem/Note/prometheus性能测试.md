influxdb 

timescaledb

docker 部署测试

存储的性能指标，cpu、磁盘、内存、写入、查询效率，以及最大承载量



- 存储的性能指标有哪一些
  - IOPS
  - 传输速度
  - IO响应时间
- 查询效率怎么表示

![img](https://oscimg.oschina.net/oscnet/1b818cae749c50ded8e38d78f8b8d999759.jpg)

使用 InfluxDB 的 Tagset 数据模型，每个测量数据中具有一个时间戳，以及一组相关的标签（称为“Tagset”）和一组字段（称为“Fieldset”）。Fieldset 表示了实际的测量读取值，而 Tagset 表示了描述测量数据的元数据。





- 最大承载量



- 数据写入性能
- 数据存储空间占用
- 数据读取性能
  - 全表扫描
  - 条件查询
  - 聚合查询





#### 功能对比

TimeScaleDB的功能更完善。TimeScaleDB作为PostgreSQL的扩展，只是在数据存储上利用PostgreSQL的特性做了一些优化，PostgreSQL支持的功能，TimeScaleDB全部支持。而InfluxDB是分析型时序数据库，舍弃了很多交易型数据库必须支持的功能。

增删改：InfluxDB不支持对数据的修改，InfluxDB只支持按tag或时间戳删除。TimeScaleDB都支持。

索引：InfluxDB时间戳和tags带索引，不支持自定义索引。TimeScaleDB更加灵活，可自定义索引

约束：InfluxDB不支持，TimeScaleDB支持

聚合函数：常用的聚合函数两种数据库都支持，TimeScaleDB的函数覆盖面更广一些。





- 表结构

- 测试数据
- 环境搭建 





### influxdb几个关键概念

- ​    measurement，就相当于关系数据库中的table，就是tag，field，time的容器；
- ​    对于influxDb的measurement来说，field是必须的，并且不能根据field来排序；
- ​    Tag是可选的，tag可以用来做索引，tag是以字符串的形式存放的；tag字段一般用于where中限制条件。
- ​    retention policy，保留策略，用于决定要保留多久的数据，保存几个备份，以及集群的策略等；
- ​    series，a series is the collection of data that share a retention policy, measurement, and tag set。







TSDB和其他数据库非常不同的属性包括：**时间戳、数据存储和压缩、数据生命周期管理、数据汇总、处理大量记录的时间序列相关扫描的能力以及时间序列感知查询。**





业务方常见需求

1. 获取最新状态，查询最近的数据(例如传感器最新的状态)

2. 展示区间统计，指定时间范围，查询统计信息，例如平均值，最大值，最小值，计数等。。。

3. 获取异常数据，根据指定条件，筛选异常数据

常见业务场景

1、聚会计算、短期保持高精度（经常需要删除数据）

2.大量的统计查询要求，查询一定时间范围内的计数、最大值、最小值和平均值。

监控软件系统： 虚拟机、容器、服务、应用

监控物理系统： 水文监控、制造业工厂中的设备监控、国家安全相关的数据监控、通讯监控、传感器数据、血糖仪、血压变化、心率等

资产跟踪应用： 汽车、卡车、物理容器、运货托盘

金融交易系统： 传统证券、新兴的加密数字货币

事件应用程序： 跟踪用户、客户的交互数据

商业智能工具： 跟踪关键指标和业务的总体健康情况

在互联网行业中，也有着非常多的时序数据，例如用户访问网站的行为轨迹，应用程序产生的日志数据等等。

一些基本概念(不同的时序数据库称呼略有不同)

Metric:  度量，相当于关系型数据库中的 table。

Data point:  数据点，相当于关系型数据库中的 row。

Timestamp：时间戳，代表数据点产生的时间。

Field:  度量下的不同字段。比如位置这个度量具有经度和纬度两个 field。一般情况下存放的是随时间戳而变化的数据。

Tag:  标签。一般存放的是不随时间戳变化的信息。timestamp 加上所有的 tags 可以视为 table 的 primary key。

例如采集有关风的数据，度量为 Wind，每条数据都有时间戳timestamp，两个字段 field：direction(风向)、speed(风速)，两个tag：sensor(传感器编号)、city(城市)。第一行和第三行，存放的都是 sensor 编号为86F-2RT8的设备，城市是深圳。随着时间的变化，风向和风速发生了改变，风向从56.4变为45.6，风速从2.9变为3.6。





### 测试方法

https://www.yinyubo.cn/www.yinyubo.cn/?p=34

https://github.com/influxdata/influx-stress

https://github.com/influxdata/influxdb-client-java

https://github.com/influxdata/influxdb-java

https://www.codenong.com/cs105394651/





### 数据模型

![img](https://img2018.cnblogs.com/i-beta/915691/202002/915691-20200226220743871-1869783396.png)
![img](https://img2018.cnblogs.com/i-beta/915691/202002/915691-20200226220805989-1736636048.png)

![img](https://img2018.cnblogs.com/i-beta/915691/202002/915691-20200226220814388-1220467188.png)

![img](https://img2018.cnblogs.com/blog/915691/201912/915691-20191219155136323-364381528.png)





database             -------->   database

measurement    -------->   table

points                  -------->   row







### 1、Shard

Shard在InfluxDB中是一个比较重要的概念，它和retention policy（数据保留策略）相关联。

每一个存储策略下会存在许多shard，每一个shard存储一个指定时间段内的数据，并且不重复，

例如：7点-8点的数据落入shard0中，8点-9点的数据则落入shard1中。

每一个 shard 都对应一个底层的 tsm 存储引擎，有独立的 cache、wal、tsm file。

这样做的目的就是为了可以通过时间来快速定位到要查询数据的相关资源，加速查询的过程，并且也让之后的批量删除数据的操作变得非常简单且高效。



数据写入过程：

![img](https://img2018.cnblogs.com/blog/915691/201912/915691-20191219161433046-341675381.png)

**1）Cache：**

cache相当于是LSM Tree中的memtabl。

插入数据时，实际上是同时往cache与wal中写入数据，可以认为cache是wal文件中的数据在内存中的缓存。

当InfluxDB启动时，会遍历所有的wal文件，重新构造cache，这样即使系统出现故障，也不会导致数据的丢失。

cache中的数据并不是无限增长的，有一个maxSize参数用于控制当cache中的数据占用多少内存后就会将数据写入tsm文件。

每当cache中的数据达到阀值后，会将当前的cache进行一次快照，之后清空当前cache中的内容，再创建一个新的wal文件用于写入，

剩下的wal文件最后会被删除，快照中的数据会经过排序写入一个新的tsm文件中。

如果不配置的话，默认上限为25MB。

**2）WAL：**

wal文件的内容与内存中的cache相同，其作用就是为了持久化数据，当系统崩溃后可以通过wal文件恢复还没有写入到tsm文件中的数据。

**3）TSM File：**

单个tsm file大小最大为2GB，用于存放数据。

**4）Compactor：**

compactor组件在后台持续运行，每隔1秒会检查一次是否有需要压缩合并的数据。

主要进行两种操作，一种是cache中的数据大小达到阀值后，进行快照，之后转存到一个新的tsm文件中。

另外一种就是合并当前的tsm文件，将多个小的tsm文件合并成一个，使每一个文件尽量达到单个文件的最大大小，减少文件的数量，并且一些数据的删除操作也是在这个时候完成。







B+ 树是为读而优化的，而 LSM 树是为写而优化的。很多人认为，关系型数据库用了 B+ 树就不太适合再去做时序场景的缺省数据库，因为时序场景 95%-99% 都是“写”的操作，而大多的时序数据库底层都是使用类似 LSM 树的方式。