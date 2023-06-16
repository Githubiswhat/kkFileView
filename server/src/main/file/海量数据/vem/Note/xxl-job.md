quartz把job和trigger分开，需要自己写代码，不太人性化

xxl-job把scheduler 和 executor 分开，提供了一个调度中心，有一个可视化的界面可以操作



quartz 不能集群，而且与业务耦合了，

xxl-jiob可以集群，本质上就是一个调度中心，可以把executor和业务分开