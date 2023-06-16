###### Prometheus Exporter组件

**功能描述**

Prometheus是业内非常流行的开源监控系统，同时本身也是一款时序数据库。Prometheus的采集端称之为exporter，用来收集被监控模块的指标项。为了与Prometheus平台完成对接，AI工具分别实现了两款exporter，分别是用来采集数据库指标的openGauss-exporter，以及对采集到的指标进行二次加工的reprocessing-exporter。

**语法格式**

用户可以通过 –help 选项获得该模式的帮助信息。

- reprocessing-exporter的使用帮助详情：

```
gs_dbmind component reprocessing_exporter --help
```

```
usage:  [-h] [--disable-https] [--ssl-keyfile SSL_KEYFILE]
        [--ssl-certfile SSL_CERTFILE]
        [--web.listen-address WEB.LISTEN_ADDRESS]
        [--web.listen-port WEB.LISTEN_PORT]
        [--collector.config COLLECTOR.CONFIG] [--log.filepath LOG.FILEPATH]
        [--log.level {debug,info,warn,error,fatal}] [--version]
        prometheus_host prometheus_port

Reprocessing Exporter: A re-processing module for metrics stored in the
Prometheus server.

positional arguments:
  prometheus_host       from which host to pull data
  prometheus_port       the port to connect to the Prometheus host

optional arguments:
  -h, --help            show this help message and exit
  --disable-https       disable Https schema
  --ssl-keyfile SSL_KEYFILE
                        set the path of ssl key file
  --ssl-certfile SSL_CERTFILE
                        set the path of ssl certificate file
  --web.listen-address WEB.LISTEN_ADDRESS
                        address on which to expose metrics and web interface
  --web.listen-port WEB.LISTEN_PORT
                        listen port to expose metrics and web interface
  --collector.config COLLECTOR.CONFIG
                        according to the content of the yaml file for metric
                        collection
  --log.filepath LOG.FILEPATH
                        the path to log
  --log.level {debug,info,warn,error,fatal}
                        only log messages with the given severity or above.
                        Valid levels: [debug, info, warn, error, fatal]
  --version             show program's version number and exit
```

- openGauss-exporter的使用帮助详情：

```
gs_dbmind component opengauss_exporter --help
```

```
usage:  [-h] --url URL [--config CONFIG] [--constant-labels CONSTANT_LABELS]
        [--web.listen-address WEB.LISTEN_ADDRESS]
        [--web.listen-port WEB.LISTEN_PORT]
        [--web.telemetry-path WEB.TELEMETRY_PATH] [--disable-cache]
        [--disable-settings-metrics] [--disable-statement-history-metrics]
        [--disable-https] [--ssl-keyfile SSL_KEYFILE]
        [--ssl-certfile SSL_CERTFILE] [--parallel PARALLEL]
        [--log.filepath LOG.FILEPATH]
        [--log.level {debug,info,warn,error,fatal}] [--version]

openGauss Exporter (DBMind): Monitoring for openGauss.

optional arguments:
  -h, --help            show this help message and exit
  --url URL             openGauss database target url.
  --config CONFIG       path to config dir or file.
  --constant-labels CONSTANT_LABELS
                        a list of label=value separated by comma(,).
  --web.listen-address WEB.LISTEN_ADDRESS
                        address on which to expose metrics and web interface
  --web.listen-port WEB.LISTEN_PORT
                        listen port to expose metrics and web interface
  --web.telemetry-path WEB.TELEMETRY_PATH
                        path under which to expose metrics.
  --disable-cache       force not using cache.
  --disable-settings-metrics
                        not collect pg_settings.yml metrics.
  --disable-statement-history-metrics
                        not collect statement-history metrics (including slow
                        queries).
  --disable-https       disable Https schema
  --ssl-keyfile SSL_KEYFILE
                        set the path of ssl key file
  --ssl-certfile SSL_CERTFILE
                        set the path of ssl certificate file
  --parallel PARALLEL   not collect pg_settings.yml metrics.
  --log.filepath LOG.FILEPATH
                        the path to log
  --log.level {debug,info,warn,error,fatal}
                        only log messages with the given severity or above.
                        Valid levels: [debug, info, warn, error, fatal]
  --version             show program's version number and exit
```

**参数说明**

| 参数                               | 参数说明                                                     | 取值范围                        |
| :--------------------------------- | :----------------------------------------------------------- | :------------------------------ |
| –url                               | openGauss server 的连接地址，例如 postgres://user:pwd@host:port/dbname | -                               |
| –constant-labels                   | 常量标签，将采集到的指标项中强行添加该标签列表               | 1024-65535                      |
| -h, –help                          | 帮助选项                                                     | -                               |
| –disable-https                     | 禁用Https协议                                                | -                               |
| –ssl-keyfile                       | Https协议使用的证书私钥文件路径                              | -                               |
| –ssl-certfile                      | Https协议使用的证书文件路径                                  | -                               |
| –web.listen-address                | 该exporter服务的绑定IP                                       | -                               |
| –web.listen-port                   | 该exporter服务的侦听端口                                     | 1024-65535                      |
| –web.telemetry-path                | 该exporter采集指标的URI地址，默认为 /metrics                 | -                               |
| –config                            | 显性指定的待采集指标配置文件路径                             | -                               |
| –log.filepath                      | 日志文件保存路径，默认保存在当前目录下                       | -                               |
| –log.level                         | 日志文件的打印级别，默认为INFO级别                           | debug, info, warn, error, fatal |
| –version                           | 显示版本信息                                                 | -                               |
| –disable-cache                     | 禁止使用缓存                                                 | -                               |
| –disable-settings-metrics          | 禁止采集pg_settings表的值                                    | -                               |
| –disable-statement-history-metrics | 禁止收集statement_history表中的慢SQL信息                     | -                               |
| –parallel                          | 连接到openGauss的数据库连接池的大小                          | 正整数                          |

**注意事项**

- Prometheus 和exporter部署在内网环境中，不对外部暴露接口，仅供内部监控平台使用。如果用户希望提高安全性，可自行修改Prometheus的TLS配置选项，但仍不建议对外部直接暴露访问接口。
- 提供的exporter组件默认采用Https通信协议，因此需要用户默认提供ssl证书和秘钥文件，并通过–ssl-keyfile以及–ssl-certfile提供。若用户不希望使用Https协议，则可以通过–disable-https选项禁用该模式。
- openGauss-exporter中连接数据库的用户需要monitor admin或以上权限，否则会出现部分指标无法采集的情况。
- openGauss-exporter会从dbe_perf.statement_history中抽样慢SQL信息，dbe_perf.statement_history视图慢SQL记录与GUC参数log_min_duration_statement和track_stmnt_stat_level相关，其中log_min_duration_statement是慢SQL阈值，单位毫秒，具体值由用户设置；track_stmnt_stat_level是SQL记录级别，默认为'OFF,L0'，即只记录慢SQL信息，级别为L0，用户在详细了解参数意义与作用情况下谨慎修改。
- openGauss-exporter采集数据库相关信息，主要包括部分系统表和视图中的数据（具体参见代码中opengauss_exporter中的配置文件），node-exporter采集系统指标信息，主要与系统磁盘、CPU等相关，reprocessing_exporter基于prometheus-server中的某些指标（具体参见代码中reprocessing_exporter中的配置文件）进行二次加工，最终提供加工后的数据供用户使用。
- prometheus-server在拉取exporter数据时有超时机制，超时时间由scrape_timeout（默认10s）控制，因此当exporter采集数据量较大时，用户可根据实际情况增大scrape_timeout以防止超时报错，另外需要注意的是scrape_interval（采集间隔）不能比scrape_timeout小，否则会出现异常。
- 如果数据库时区设置和系统不相同，可能会出现时间相关指标时间与系统时间不一致的情况，因此需要将用户时区与系统保持同步。
- 当使用https通信时，工具会检测证书与密钥文件权限以及证书有效期，如果文件权限大于600则会出现报警，证书有效期小于90天会出现报警。
- 当存在指标重复采集时，openGauss-exporter会出现异常，异常信息会打印到日志中。
- openGauss-exporter的–config、–disable-settings-metrics、–disable-statement-history-metrics三个参数需要注意，其存在以下几种情况：
  - 用户不指定其中任何参数，则工具会同时对yamls目录下的三个配置文件中的指标进行采集。
  - 用户显式指定–config，则工具不会采集yamls目录下default.yml中的指标，而会采集用户指定配置文件中的指标，同时pg_settings.yml和statements.yml正常采集，此时用户需要注意指定的配置文件中的指标和pg_settings.yml、statements.yml中的指标不能存在重复采集的现象。
  - 用户显式指定–disable-settings-metrics，则工具不会采集yamls目录下pg_settings.yml中的指标，用户显式指定–disable-statement-history-metrics，则工具不会采集yamls目录下statements.yml（慢SQL相关）中的指标。


**环境部署**

1、通过命令行参数启动对应的exporter进程，同时在本地机器创建侦听端口号。

2、在Promethues的服务器端修改配置文件prometheus.yml，将启动的exporter信息添加进去，例如：

```
scrape_configs:
  ...
       
    - job_name: 'opengauss_exporter'
      static_configs:
      - targets: ['127.0.0.1:9187']
        ...
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>  
>
> 由于openGauss默认模式下的通信加密协议与PostgreSQL不兼容，故导致通过PyPI源安装的基于PostgreSQL编译的Python驱动psycopg2-binary默认无法连接至openGauss数据库。
> 因此，需要用户自行编译psycopg2或修改GUC参数进行适配。也可通过openGauss官方网站下载基于openGauss编译的psycopg2（官方网站仅提供部分Python版本的编译包，一般为Python3.6版本，需要用户鉴别）。
>
> - 官方openGauss Python驱动下载地址为：
>   https://opengauss.org/zh/download.html
> - 关于Python驱动的适配问题，可参考openGauss官方操作指南：
>   https://mp.weixin.qq.com/s/2TobUQKtw0N9sBpMZJr6zw

**使用指导**

用户可通过gs_dbmind命令启动对应的exporter。下面演示了一个完整的Prometheus监控平台的搭建过程。

1、部署openGauss-exporter：启动openGauss-exporter，采用默认侦听端口号9187，侦听地址为192.168.1.100，不采用https协议，则命令可以为：

```
gs_dbmind component opengauss_exporter --url postgresql://user:password@ip:port/dbname --web.listen-address 192.168.1.100 --disable-https
```

2、部署reprocessing-exporter：启动reprocessing-exporter，采用默认侦听端口号8181，侦听地址为192.168.1.101，Prometheus-server IP与端口号为192.168.1.100:9090，采用https协议，则命令可以为：

```
gs_dbmind component reprocessing_exporter 192.168.1.100 9090 --web.listen-address 192.168.1.101 --ssl-keyfile server.key --ssl-certfile server.crt
```

3、部署node-exporter：一般地，对于Prometheus监控平台都需要部署node-exporter用于监控Linux操作系统，后文提到的部分AI功能也需要依赖node-exporter采集Linux系统指标，故也需要用户来部署；只不过，该node-exporter为Prometheus自带组件，需要用户自行下载安装，下载地址为 https://prometheus.io/download/#node_exporter  ，使用方法详见：https://prometheus.io/docs/guides/node-exporter/#installing-and-running-the-node-exporter  。用户可解压压缩包后直接运行该node-exporter进程，其默认端口号为9100，启动命令行为：

```
./node_exporter 
```

4、配置Prometheus-server，修改配置文件prometheus.yml，添加下述内容：

```
scrape_configs:
 ...
   - job_name: 'opengauss_exporter'
     static_configs:
     - targets: ['192.168.1.100:9187']
   - job_name: 'reprocessing_exporter'
     scheme: https
     tls_config:
         ca_file: xxx.crt
     static_configs:
     - targets: ['127.0.0.1:8181']
   - job_name: 'node_exporter'
     static_configs:
     - targets: ['127.0.0.1:9100']
 ...
```

**常见问题**

- 提示需要用户提供–ssl-keyfile与–ssl-certfile选项：

  上述exporter默认采用Https模式通信，因此需要用户指定证书及其私钥文件的路径。相反，如果用户只想采用Http模式，则需要显性给定–disable-https选项，从而禁用Https协议。

- 提示用户需要输入PEM密码（Enter PEM pass phrase）：

  如果用户采用Https模式，并给定了证书及其秘钥文件的路径，且该秘钥文件是经过加密的，则需要用户输入该加密私钥证书文件的密码。