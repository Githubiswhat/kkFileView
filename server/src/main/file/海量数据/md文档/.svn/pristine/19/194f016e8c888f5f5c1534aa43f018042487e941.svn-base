#### Bulkload出错清理

**功能描述**

vb_bulkload就是批量加载工具，可以加载表数据，批量加载可以提升加载的效率和性能。

在使用vb_bulkload工具导入出错之后，本次导入的数据都要进行回滚，所以需要将本次导入数据清理，防止这些数据被访问，造成查询、执行计划不准确等问题。

**语法格式**

```
vb_bulkload -r options
```

options支持列表如下：

| **Options选项**                  | **说明**                                        |
| -------------------------------- | ----------------------------------------------- |
| -o "DIRS=val"--option="DIRS=val" | val可指定含.loadstatus为后缀的文件目录。        |
| -d dbname--dbname dbname         | 要连接的数据库的名称。                          |
| -h host--host host               | 正在运行服务器的计算机的主机名。                |
| -p port--port port               | 服务器侦听连接的TCP端口。                       |
| -U username--username username   | 连接的用户名。                                  |
| -W--password                     | 强制vb_bulkload在连接到数据库之前提示输入密码。 |
| -e--echo                         | 显示发送到服务端的命令。                        |
| --help                           | 显示帮助信息。                                  |
| --version                        | 显示版本信息。                                  |

**注意事项**

- 使用此功能需安装pg_bulkload插件。

- 实现vb_bulkload导入时，将在导入报错时清理本次导入数据，需要指定-o “ERROR_RECOVERY=YES”,该参数默认值为NO。

- vb_bulkload不支持ustore表格式。

**示例：**

1、在操作系统执行定义脚本文件bk1oadx4_one：

```
wanghp@localhost hztestj$ more bkloadx4_one 
path='/data3/wanghp/loaddata'
logfile='/home/wanghp/bkload_log.log'
bk_load(){

pg_bulkload -o "INFILE=$path/g2021-${1}*" -O $3 -l $logfile -o "SET_COMMITTED=YES" -O "WRITER=VASTLOADER" -O "NREADERS=$2" -O "TYPE=CSV" -O "DELIMITER=," -O "FILTER=DATETIME_FORMAT(1.YYYYMMDDHH24MISS)" -p5417 -d $4 -u wanghp 

}

date

bk 1oad $1 $2 $3 $4
```

2、执行一次错误的语法：

```
sh bkloadx4_one 01-01 16 bkld_t2 vastbase > x.log 
```

3、此时由于01-01没有用引号包裹起来，会引起sh报错：

```
NOTICE: Last successful login info login time:2022-03-25 01:52:43 application name:gs_clean Ip address:::1 method:Trust
Failed o times since last successful login
NOTICE: BULK LOAD START
ERROR:query failed: ERROR: invalid input syntax for type numeric:"01-01"
```

4、随后再进行一次正确的导入操作：

```
sh bkloadx4_one '01-01'  16 bkld_t2 vastbase > x.log 
```

