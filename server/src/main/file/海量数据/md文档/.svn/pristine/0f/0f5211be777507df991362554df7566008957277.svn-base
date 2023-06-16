##### WDR Snapshot 生成性能报告

**功能描述**

基于WDR Snapshot数据表汇总、统计，生成性能报告，默认初始化用户或监控管理员用户可以生成报告。

**操作步骤**

1、执行如下命令新建报告文件。

```
touch  /home/vastbase/wdrTestNode.html
```

2、连接数据库。

3、执行如下命令查询已经生成的快照，以获取快照的snapshot_id。

```
select * from snapshot.snapshot;
```

4、执行如下命令手动创建快照。数据库中只有一个快照或者需要查看在当前时间段数据库的监控数据，可以选择手动执行快照操作，该命令需要用户具有sysadmin权限。（可选）

```
select create_wdr_snapshot();
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>   
>
> 执行“cm_ctl query -Cdvi”，回显中“Central Coordinator State”下显示的信息即为CCN信息。

5、执行如下命令，在本地生成HTML格式的WDR报告。

（1）执行如下命令，设置报告格式。\a: 不显示表行列符号， \t: 不显示列名 ，\o: 指定输出文件。

```
\a      
\t 
\o /home/vastbase/wdrTestNode.html
```

（2）执行如下命令，生成HTML格式的WDR报告。

```
select generate_wdr_report(begin_snap_id Oid, end_snap_id Oid, int report_type, int report_scope, int node_name );
```

示例一：生成集群级别的报告：

```
select generate_wdr_report(1, 2, 'all', 'cluster',null);
```

示例二：生成某个节点的报告：

```
select generate_wdr_report(1, 2, 'all', 'node', pgxc_node_str()::cstring);
```

（3）执行如下命令关闭输出选项及格式化输出命令。

```
\o \a \t 
```

6、在/home/vastbase/下根据需要查看WDR报告。

generate_wdr_report函数参数说明如下表所示

| 参数          | 说明                                                         | 取值范围                                                     |
| :------------ | :----------------------------------------------------------- | :----------------------------------------------------------- |
| begin_snap_id | 查询时间段开始的snapshot的id（表snapshot.snapshot中的snapshot_id）。 | -                                                            |
| end_snap_id   | 查询时间段结束snapshot的id。默认end_snap_id大于begin_snap_id（表snapshot.snapshot中的snapshot_id）。 | -                                                            |
| report_type   | 指定生成report的类型。例如，summary/detail/all。             | summary：汇总数据。detail：明细数据。all：包含summary和detail。 |
| report_scope  | 指定生成report的范围，可以为cluster或者node。                | cluster：数据库级别的信息。node：节点级别的信息。            |
| node_name     | 在report_scope指定为node时，需要把该参数指定为对应节点的名称。（节点名称可以执行select * from pg_node_env;查询）。在report_scope为cluster时，该值可以省略或者指定为空或NULL。 | node：Vastbase中的节点名称。cluster：省略/空/NULL。          |

**注意事项**

- 需进入系统库查看快照信息。

- WDR Snapshot启动（即参数enable_wdr_snapshot为on时），且快照数量大于等于2。

**示例**

1、修改参数。

```
alter system set enable_wdr_snapshot=on;
```

2、创建报告文件。

```
touch  /home/vastbase/wdrTestNode.html
```

3、登录数据库后切换至系统库。

```
\c postgres
```

4、查询已经生成的快照。

```
select * from snapshot.snapshot;
```

结果显示如下：

```
snapshot_id |           start_ts            |            end_ts             
-------------+-------------------------------+-------------------------------
         432 | 2022-07-05 10:34:25.477173+08 | 2022-07-05 10:34:40.441504+08
         435 | 2022-07-05 13:20:23.230771+08 | 2022-07-05 13:20:38.65221+08
```

5、生成格式化性能报告wdrTestNode.html。

```
\a \t \o /home/om/wdrTestNode.html
```

6、查询node节点名称

```
select * from pg_node_env;
```

结果如下所示，node_name 即为节点名称。

```
 node_name |   host    | process | port |          installpath          |           datapath           | log_directory 
-----------+-----------+---------+------+-------------------------------+------------------------------+---------------
 node1     | localhost |  148191 | 5432 | /home/vastbase/local/vastbase | /home/vastbase/data/vastbase | pg_log
 (1 row)
```

7、向性能报告wdrTestNode.html中写入数据。

```
 select generate_wdr_report(432, 435, 'all', 'node', 'node1');
```

8、关闭性能报告wdrTestNode.html。

```
postgres=# \o
```

9、生成格式化性能报告wdrTestCluster.html。

```
postgres=# \o /home/om/wdrTestCluster.html
```

10、向格式化性能报告wdrTestCluster.html中写入数据。

```
select generate_wdr_report(432, 435, 'all', 'cluster');
```

11、关闭性能报告wdrTestCluster.html。

```
postgres=# \o \a \t
```

