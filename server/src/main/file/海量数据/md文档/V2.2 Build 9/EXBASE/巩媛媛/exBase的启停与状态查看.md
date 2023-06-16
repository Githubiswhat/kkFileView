# exBase的启停与状态查看

1、服务启动，安装时已将服务加入自启项，手工启动命令如下：

- 启动配置库。

```
systemctl start atlasdb
```

- 启动incremental进程。

```
systemctl start incremental
```

- 启动exbase进程。

```
systemctl start exbase
```

2、服务停止。

- 停止exbase进程。

```
systemctl stop exbase
```

- 停止incremental进程。

```
systemctl stop incremental
```

- 停止配置库。

```
systemctl stop atlasdb
```

3、服务重启。

- 重启配置库。

```
systemctl restart atlasdb
```

- 重启incremental进程。

```
systemctl restart incremental
```

- 重启exbase进程。

```
systemctl restart exbase
```

4、查看进程状态。

- 查看exbase进程状态。

```
systemctl status exbase
```

- 查看incremental进程状态。

```
systemctl status incremental
```

- 查看配置库状态。

```
systemctl status atlasdb
```

