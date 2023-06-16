##### DBMIND模式说明

**功能描述**

用户可通过gs_dbmind命令调用AI4DB的全部功能，该命令可实现下列基本功能：

- 服务功能：service子命令，包括创建并初始化配置目录、启动后台服务、关闭后台服务等；
- 调用组件：component子命令，AI4DB功能（如索引推荐、参数调优等）可通过该模式进行即时调用；
- 设置参数：set子命令，通过该命令，可以一键修改配置目录中的配置文件值；当然，用户也可以通过文本编辑器进行手动修改；

**语法格式**

用户可以通过 –help 选项获得模式的帮助信息，例如：

```
gs_dbmind --help
```

```
usage: [-h] [--version] {service,set,component} ...

openGauss DBMind: An autonomous platform for openGauss

optional arguments:
  -h, --help            show this help message and exit
  --version             show program's version number and exit

available subcommands:
  {service,set,component}
                        type '<subcommand> -h' for help on a specific subcommand
    service             send a command to DBMind to change the status of the service
    set                 set a parameter
    component           pass command line arguments to each sub-component.
```

**参数说明**

| 参数      | 参数说明             |
| :-------- | :------------------- |
| -h, –help | 帮助命令             |
| –version  | 版本号               |
| service   | 服务功能相关的子命令 |
| component | 调用组件的子命令     |
| set       | 修改配置文件的子命令 |

###### 