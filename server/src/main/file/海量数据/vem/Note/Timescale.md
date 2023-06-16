# 架构与概念

## 概观

TimescaleDB作为PostgreSQL的扩展实现，这意味着Timescale数据库在整个PostgreSQL实例中运行。该扩展模型允许数据库利用PostgreSQL的许多属性，如可靠性，安全性以及与各种第三方工具的连接性。同时，TimescaleDB通过在PostgreSQL的查询规划器，数据模型和执行引擎中添加钩子，充分利用扩展可用的高度自定义。

从用户的角度来看，TimescaleDB公开了一些看起来像单数表的称为**hypertable的**表，它们实际上是一个抽象或许多单独表的虚拟视图，称为**块**。

![可改变和块](https://assets.iobeam.com/images/docs/illustration-hypertable-chunk.svg)

通过将hypertable的数据划分为一个或多个维度来创建块：所有可编程元素按时间间隔分区，并且可以通过诸如设备ID，位置，用户ID等的关键字进行分区。我们有时将此称为分区横跨“时间和空间”。







**创建数据保留策略**

1. 使用该add_retention_policy()函数对stocks_real_time表进行自动保留策略：

   运行此命令后，所有超过 3 周的数据都将从 中删除stocks_real_time，并创建定期保留策略。不会从您的连续聚合中删除任何数据， stocks_real_time_daily.

   ```
   SELECT add_retention_policy('stocks_real_time', INTERVAL '3 weeks');
   ```

2. 要查看有关您的保留策略验证作业统计信息的信息，请查询 TimescaleDB 信息视图：

   ```
   SELECT * FROM timescaledb_information.jobs;
   ```

   ```
   SELECT * FROM timescaledb_information.job_stats;
   ```

**手动删除旧的超表块**

要一次性手动删除数据，请使用 TimescaleDB 函数 drop_chunks()。

此函数采用与数据保留策略类似的参数。但是，除了让您删除早于特定时间间隔的数据之外，它还允许您删除比特定时间间隔新的数据。这意味着您可以从两端有界的间隔中删除数据。

要删除超过三周的所有数据，请运行：

```
SELECT drop_chunks('stocks_real_time', INTERVAL '3 weeks');
```

要删除超过两周但新于三周的所有数据：

```
SELECT drop_chunks(
  older_than => INTERVAL '2 weeks',
  newer_than => INTERVAL '3 weeks'
)
```