# CREATE RESOURCE POOL

## 功能描述

创建一个资源池，并指定此资源池相关联的控制组。

## 注意事项

无

## 语法格式

```
CREATE RESOURCE POOL pool_name
    [WITH ({MEM_PERCENT=pct | CONTROL_GROUP="group_name" | 	
    ACTIVE_STATEMENTS=stmt | MAX_DOP = dop | MEMORY_LIMIT='memory_size' | 
    io_limits=io_limits | io_priority='priority' | nodegroup='nodegroup_name' |
    is_foreign = boolean }[, ... ])];
```

## 参数说明

- **pool_name**

  资源池名称。

  资源池名称不能和当前数据库里已有的资源池重名。

  取值范围：字符串，要符合标识符的命名规范。

- **group_name**

  控制组名称。

  - 设置控制组名称时，语法可以使用双引号，也可以使用单引号。
  - group_name对大小写敏感。
  - 不指定group_name时，默认指定的字符串为 "Medium"，代表指定DefaultClass控制组的"Medium" Timeshare控制组。
  - 若数据库管理员指定自定义Class组下的Workload控制组，如control_group的字符串为："class1:workload1"；代表此资源池指定到class1控制组下的workload1控制组。也可同时指定Workload控制组的层次，如control_group的字符串为："class1:workload1:1"。
  - 若数据库用户指定Timeshare控制组代表的字符串，即"Rush"、"High"、"Medium"或"Low"其中一种，如control_group的字符串为"High"；代表资源池指定到DefaultClass控制组下的"High" Timeshare控制组。
  - 多租户场景下，组资源池关联的控制组为Class级别，业务资源池关联Workload控制组。且不允许在各种资源池间相互切换。

  取值范围：字符串，要符合说明中的规则，其指定已创建的控制组。

- **stmt**

  资源池语句执行的最大并发数量。

  取值范围：数值型，-1~INT_MAX。

- **dop**

  资源池最大并发度，语句执行时能够创建的最多线程数量。

  取值范围：数值型，1~INT_MAX

- **memory_size**

  资源池最大使用内存。

  取值范围：字符串，内容范围1KB~2047GB

- **mem_percent**

  资源池可用内存占全部内存或者组用户内存使用的比例。

  在多租户场景下，组用户和业务用户的mem_percent范围1-100，默认为20。

  在普通场景下，普通用户的mem_percent范围为0-100，默认值为0。

  mem_percent和memory_limit同时指定时，只有mem_percent起作用。

- **io_limits**

  资源池每秒可触发IO次数上限。

  对于行存，以万次为单位计数，而列存则以正常次数计数。

- **io_priority**

  IO利用率高达90%时，重消耗IO作业进行IO资源管控时关联的优先级等级。

  包括三档可选：Low、Medium和High。不控制时可设置为None。默认为None。

  io_limits和io_priority的设置都仅对复杂作业有效。包括批量导入（INSERT INTO SELECT, COPY FROM, CREATE TABLE AS等），单DN数据量大约超过500MB的复杂查询和VACUUM FULL等操作。

- **nodegroup**

  在逻辑集群模式下，指定逻辑集群名称。必须是存在的逻辑集群。

  如果逻辑集群名称包含大写字符、特殊符号或以数字开头，SQL语句中对逻辑集群名称需要加双引号。

- **is_foreign**

  在逻辑集群模式下，指定当前资源池用于控制没有关联本逻辑集群的普通用户的资源。这里的逻辑集群是由资源池nodegroup字段指定的。

  - nodegroup必须是存在的逻辑集群，不能是elastic_group和安装的nodegroup (group_version1)。
  - 如果指定了is_foreign为true，则资源池不能再关联用户，即不允许通过CREATE USER ... RESOURCE POOL语句来将该资源池配置给用户。该资源池自动检查用户是否关联到资源池指定的逻辑集群，如果用户没有关联到该逻辑集群，则这些用户在逻辑集群所包含的DN上运行将受到该资源池的资源控制。

## 示例<a name='example'>

- 创建一个默认资源池。其控制组为"DefaultClass"组下属的"Medium" Timeshare Workload控制组。


```
CREATE RESOURCE POOL pool1;
```

- 创建一个资源池，其控制组指定为"class1"组下属的"Low" Timeshare Workload控制组。

```
CREATE RESOURCE POOL pool3 WITH (CONTROL_GROUP="class1:Low");
```

## 相关链接

[ALTER RESOURCE POOL ](ALTER-RESOURCE-POOL.md)，[DROP RESOURCE POOL ](DROP-RESOURCE-POOL.md)

