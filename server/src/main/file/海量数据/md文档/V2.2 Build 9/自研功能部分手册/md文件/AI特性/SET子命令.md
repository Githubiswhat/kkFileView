###### SET子命令

该命令用于修改配置文件dbmind.conf中的参数值，与用户手动修改配置文件dbmind.conf一般无差异。

**语法格式**

用户可以通过 –help 选项获得该模式的帮助信息。

```
gs_dbmind set --help
```

```
usage:  set [-h] -c DIRECTORY section option target

positional arguments:
  section               which section (case sensitive) to set
  option                which option to set
  target                the parameter target to set

optional arguments:
  -h, --help            show this help message and exit
  -c DIRECTORY, --conf DIRECTORY
                        set the directory of configuration files
```

**参数说明**

| 参数      | 参数说明             |
| :-------- | :------------------- |
| -h, –help | 帮助命令             |
| -c，–conf | 配置文件目录confpath |
| section   | 设置区               |
| option    | 设置项               |
| target    | 设置值               |

**注意事项**

修改密码项时必须通过该命令才可以实现修改，否则通过用户手动修改的配置文件无法实现对明文密码的加密。

**示例**

修改配置目录confpath中的配置文件dbmind.conf中TSDB配置部分，host参数的值，并将其设置为 127.0.0.1。

```
gs_dbmind set TSDB host 127.0.0.1 -c confpath
```

修改密码。

```
gs_dbmind set METADATABASE password xxxxxx -c confpath
```

##### 