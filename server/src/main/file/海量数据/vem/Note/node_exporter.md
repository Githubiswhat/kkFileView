# node-exporter常用指标含义

- CPU使用率

  CPU 除空闲（idle）状态之外的其他所有 CPU 状态的时间总和除以总的 CPU 时间得到的结果

  ```
  (1 - sum(rate(node_cpu_seconds_total{mode="idle"}[1m])) by (instance) / sum(rate(node_cpu_seconds_total[1m])) by (instance) ) * 100
  ```

- 内存监控

  ```
  (1- (node_memory_Buffers_bytes + node_memory_Cached_bytes + node_memory_MemFree_bytes) / node_memory_MemTotal_bytes) * 100
  ```

- 磁盘容量监控

  ```
  (1 - node_filesystem_avail_bytes{fstype=~"ext4|xfs"} / node_filesystem_size_bytes{fstype=~"ext4|xfs"}) * 100
  ```

- 磁盘 IO 监控

  磁盘读 IO （IOPs）

  ```
   sum by (instance) (rate(node_disk_reads_completed_total[5m]))
  ```

  磁盘写 IO  （IOPs）

  ```
  sum by (instance) (rate(node_disk_writes_completed_total[5m]))
  ```

  磁盘读取总数

  ```
  sum (irate(node_disk_bytes_read{instance= "88node" }[1m]))
  ```

  磁盘写入总数

  ```
  sum (irate(node_disk_bytes_written{instance= "88node" }[1m]))
  ```

- 网络 IO 监控

  上行带宽

  ```
  sum by(instance) (irate(node_network_receive_bytes_total{device!~"bond.*?|lo"}[5m]))
  ```

  下行带宽

  ```
  sum by(instance) (irate(node_network_transmit_bytes_total{device!~"bond.*?|lo"}[5m]))
  ```

原文链接：https://blog.csdn.net/qq_34556414/article/details/123443187





# 一、概述

rate(), irate(), increase()这三个函数都是用来描述Counter增长速率的。其不同点的概要介绍如下

1. rate()

计算每一秒增长率(per-second)，是所提供tw内的平均值。举例：

```css
rate(http_requests_total[5m])
```

在5min的tw内，每一秒的平均增长率。它很常用，它产生的是平滑的per-second的 增长率。



2. irate()

它也是per-second增长率，是瞬时增长率。它只使用所提供tw内的最后两个采样，忽略掉前面的所有采样。举例：

```css
irate(http_requests_total[5m])
```

在5min的tw内，只使用最后的两个采样，计算这两个采样点之间的per-second增长率。它反映增长率的瞬时变化，变化较快，它的图像比rate()有更多的尖刺。



3. increase()

除了不是per-second的，它和rate()完全一样(exactly equivalent to rate())。它的返回值的单位是 per-provided-time-window。举例：

```css
increase(http_requests_total[5m])
```

在5min的tw内，所处理的http请求增长了多少个。所以，

increase(foo[5m]) / (5 * 60)  和 rate(foo[5m]) 完全一致。



以上三个函数都要求，在所提供的tw内，至少要有2个采样。如果某个time series，在所提供的tw内少于两个采样，则，该返回结果会直接剔除掉该time series。


给定 tw 和 落在该窗口内的采样，如何来计算增长率 这其实是 权衡和不完美近似的问题(a matter of tradeoffs and imperfect approximations)。

prometheus计算增长率的方式是：给定了tw，且，只能提供 有限个采样的情况下，提供 平均最正确的答案。我们来看prometheus是如何计算的。