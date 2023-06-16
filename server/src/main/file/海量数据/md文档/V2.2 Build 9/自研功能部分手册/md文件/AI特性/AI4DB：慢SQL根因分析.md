###### AI4DB：慢SQL根因分析

**功能描述**

慢SQL一直是数据运维中的痛点问题，如何有效诊断慢SQL根因是当前一大难题，工具结合Vastbase自身特点融合了现网DBA慢SQL诊断经验，该工具可以支持慢SQL根因15+，能同时按照可能性大小输出多个根因并提供针对性的建议。

**语法格式**

用户可以通过 –help 选项获得该模式的帮助信息。

```
gs_dbmind component slow_query_diagnosis --help
```

```
usage:  [-h] -c DIRECTORY [--query SLOW_QUERY]
        [--start-time TIMESTAMP_IN_MICROSECONDS]
        [--end-time TIMESTAMP_IN_MICROSECONDS] [--retention-days DAYS]
        {show,clean}

Slow Query Diagnosis: Analyse the root cause of slow query

positional arguments:
  {show,clean}          choose a functionality to perform

optional arguments:
  -h, --help            show this help message and exit
  -c DIRECTORY, --conf DIRECTORY
                        set the directory of configuration files
  --query SLOW_QUERY    set a slow query you want to retrieve
  --start-time TIMESTAMP_IN_MICROSECONDS
                        set the start time of a slow SQL diagnosis result to
                        be retrieved
  --end-time TIMESTAMP_IN_MICROSECONDS
                        set the end time of a slow SQL diagnosis result to be
                        retrieved
  --retention-days DAYS
                        clear historical diagnosis results and set the maximum
                        number of days to retain data
```

**参数说明**

| 参数            | 参数说明                       | 取值范围                      |
| :-------------- | :----------------------------- | :---------------------------- |
| -h, –help       | 帮助命令                       | -                             |
| action          | 动作参数                       | show：结果展示clean：清理结果 |
| -c，–conf       | 配置目录                       | -                             |
| –query          | 慢SQL文本                      | *                             |
| –start-time     | 显示开始时间的时间戳，单位毫秒 | 非负整数                      |
| –end-time       | 显示结束时间的时间戳，单位毫秒 | 非负整数                      |
| –retention-days | 清理天数级结果                 | 非负实数                      |

**注意事项**

- 数据库运行正常。
- 指标采集系统运行正常。
- 已完成confpath目录初始化配置，可参考SERVICE子命令章节中配置初始化目录内容。

**示例**

- 仅启动慢SQL诊断功能（输出Top3根因），启动命令如下（更多用法参考对service子命令的说明）：

  ```
  gs_dbmind service start -c confpath --only-run slow_query_diagnosis
  ```

- 交互式慢SQL诊断，命令如下：

  ```
  gs_dbmind component slow_query_diagnosis show -c confpath --query SQL --start-time timestamps0 --end-time timestamps1
  ```

- 手动清理历史预测结果，命令如下：

  ```
  gs_dbmind component slow_query_diagnosis clean -c confpath --retention-days DAYS
  ```

- 停止已启动的服务，命令如下：

  ```
  gs_dbmind service stop -c confpath
  ```

**常见问题**

- 如果用户对没有执行过的慢SQL执行交互式诊断命令，则无法给出诊断结果。
- exporter指标采集功能没有启动时运行慢SQL诊断功能，此时功能无法正常运行。
- 配置文件中的参数重新设置后，需要重新启动服务进程才能生效。