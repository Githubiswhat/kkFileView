# Anomaly detection: 异常检测

## 功能描述

Anomaly detection 异常检测模块主要基于统计方法来实现时序数据来发现数据中存在的可能的异常情况。该模块框架解耦，可以实现不同异常检测算法的灵活替换，而且该模块功能可以根据时序数据的不同特征来自动选择算法，支持异常值检测、阈值检测、箱型图检测、梯度检测、增长率检测、波动率检测和状态转换检测。

## 语法格式

```shell
gs_dbmind component anomaly_detection --help
```

显示如下帮助信息：

```shell
usage: anomaly_detection.py [-h] --action {overview,plot} -c CONF -m METRIC -s
                            START_TIME -e END_TIME [-H HOST] [-a ANOMALY]

Workload Anomaly detection: Anomaly detection of monitored metric.

optional arguments:
  -h, --help            show this help message and exit
  --action {overview,plot}
                        choose a functionality to perform
  -c CONF, --conf CONF  set the directory of configuration files
  -m METRIC, --metric METRIC
                        set the metric name you want to retrieve
  -s START_TIME, --start-time START_TIME
                        set the start time of for retrieving in ms
  -e END_TIME, --end-time END_TIME
                        set the end time of for retrieving in ms
  -H HOST, --host HOST  set a host of the metric, ip only or ip and port.
  -a ANOMALY, --anomaly ANOMALY
                        set a anomaly detector of the metric(increase_rate,
                        level_shift, spike, threshold)

Process finished with exit code 0
```

## 参数说明
<table>
    <tr>
        <th>参数</th>
        <th>参数说明</th>
        <th>取值范围</th>
    </tr>
    <tr>
        <td>-h, --help</td>
        <td>帮助命令。</td>
        <td>-</td>
    </tr>
    <tr>
        <td>--action</td>
        <td>动作参数。</td>
        <td><ur><li>overview：概览</li><li>plot：可视化</li></ur></td>
    </tr>
    <tr>
        <td>-c，--conf</td>
        <td>配置文件目录。</td>
        <td>-</td>
    </tr>
    <tr>
        <td>-m，--metric-name</td>
        <td>指定显示指标名。</td>
        <td>-</td>
    </tr>
    <tr>
        <td>-H, --host</td>
        <td>指定数据来源地址信息，通过地址信息进行过滤。</td>
        <td>ip地址或者ip地址加端口号</td>
    </tr>
    <tr>
        <td>-a, --anomaly</td>
        <td>指定异常检测方式，用于过滤。</td>
        <td>-</td>
    </tr>
    <tr>
        <td>-s, --start-time</td>
        <td>显示开始时间的时间戳，单位毫秒；或日期时间格式为 %Y-%m-%d %H:%M:%S。</td>
        <td>正整数或日期时间格式</td>
    </tr>
    <tr>
        <td>-e, --end-time</td>
        <td>显示结束时间的时间戳，单位毫秒；或日期时间格式为 %Y-%m-%d %H:%M:%S。</td>
        <td>正整数或日期时间格式</td>
    </tr>
</table>

> <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
>
> 在输入anomaly detection的参数时，\--start-time至少要比\--end-time早30秒以上。

## 使用示例

假设指标采集系统运行正常，并且用户已经初始化了配置文件目录confpath，则可以通过下述命令实现本特性的功能。

- 仅启动异常检测功能。

  ```shell
  gs_dbmind service start --conf confpath --only-run anomaly_detection
  ```

- 对于某一指标，在全部节点上，从timestamps1到timestamps2时间段内的数据进行概览。

  ```shell
  gs_dbmind component anomaly_detection --conf confpath --action overview --metric metric_name --start-time timestamps1 --end-time timestamps2
  ```

  例如：

  ```shell
  gs_dbmind component anomaly_detection --conf confpath --action overview  --metric pg_class_relage --start-time '2023-02-21 11:10:00' --end-time '2023-02-21 11:18:00'
  ```

  返回结果如下：

  <div align="left"><img src="image/AI-15.png" style="zoom:100%"></div>

- 对于某一指标，在特定节点上，从timestamps1到timestamps2时间段内的数据进行概览。

  ```shell
  gs_dbmind component anomaly_detection --conf confpath --action overview --metric metric_name --start-time timestamps1 --end-time timestamps2 --host ip_address --anomaly anomaly_type
  ```

  例如：

  ```shell
  gs_dbmind component anomaly_detection --conf confpath --action overview --metric pg_class_relage --start-time '2023-02-21 11:10:00' --end-time '2023-02-21 11:18:00' --host 127.0.0.1:5732
  ```

- 对于某一指标，在全部节点上，从timestamps1到timestamps2时间段内的数据，以特定异常检测方式进行概览。

  ```shell
  gs_dbmind component anomaly_detection --conf confpath --action overview --metric metric_name --start-time timestamps1 --end-time timestamps2 --anomaly anomaly_type
  ```

  例如：

  ```shell
  gs_dbmind component anomaly_detection --conf confpath --action overview --metric pg_class_relage --start-time '2023-02-21 11:10:00' --end-time '2023-02-21 11:18:00' --anomaly  increase_rate
  ```

  返回结果如下：

  <div align="left"><img src="image/AI-17.png" style="zoom:100%"></div>

- 对于某一指标，在特定节点，从timestamps1到timestamps2时间段内的数据，以特定异常检测方式进行概览。

  ```shell
  gs_dbmind component anomaly_detection --conf confpath --action overview --metric metric_name --start-time timestamps1 --end-time timestamps2 --host ip_address --anomaly anomaly_type
  ```

  例如：

  ```shell
  gs_dbmind component anomaly_detection --conf confpath --action overview --metric pg_class_relage --start-time '2023-02-21 11:10:00' --end-time '2023-02-21 11:18:00'  --host 127.0.0.1:5732  --anomaly  increase_rate
  ```

- 对于某一指标，在特定节点，从timestamps1到timestamps2时间段内的数据，以特定异常检测方式进行可视化展示。

  ```shell
  gs_dbmind component anomaly_detection --conf confpath --action plot --metric metric_name --start-time timestamps1 --end-time timestamps2 --host ip_address --anomaly anomaly_type
  ```

  例如：

  ```shell
  gs_dbmind component anomaly_detection --conf confpath --action plot --metric pg_class_relage --start-time '2023-02-21 11:10:00' --end-time '2023-02-21 11:18:00' --host 127.0.0.1:5732  --anomaly  increase_rate
  ```

  返回结果如下：

  <div align="left"><img src="image/AI-18.png" style="zoom:100%"></div>

- 停止已启动的服务。

  ```shell
  gs_dbmind service stop --conf confpath
  ```
