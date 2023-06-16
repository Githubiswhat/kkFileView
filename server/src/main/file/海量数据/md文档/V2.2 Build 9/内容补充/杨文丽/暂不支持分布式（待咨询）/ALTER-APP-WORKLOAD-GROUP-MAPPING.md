# ALTER APP WORKLOAD GROUP MAPPING

## 功能描述

修改应用程序组与组映射的关系。

## 注意事项

只有用户对当前数据库有ALTER权限，才可以对应用映射组进行修改。

## 语法格式

```
ALTER APP WORKLOAD GROUP MAPPING app_name
    WITH ( WORKLOAD_GPNAME = wg_name );
```

## 参数说明

- app_name

  应用映射组名称。应用映射组名称不能和当前数据库里其他应用映射组重名。

  取值范围：字符串，要符合标识符的命名规范。

- **wg_name**

  负载组名称。

  取值范围：字符串，已创建的负载组。

## 示例

1、创建一个资源池，其控制组指定为"DefaultClass"组下属的"High" Timeshare Workload控制组。

```
CREATE RESOURCE POOL pool1 WITH (CONTROL_GROUP="High");
```

2、创建一个负载组，关联已创建的资源池。

```
CREATE WORKLOAD GROUP wg_hr1 USING RESOURCE POOL pool1;
```

3、创建一个默认应用映射组，关联默认的负载组。

```
CREATE APP WORKLOAD GROUP MAPPING app_wg_map1;
```

4、更新一个应用映射组关联的负载组名称。

```
ALTER APP WORKLOAD GROUP MAPPING app_wg_map1 WITH(WORKLOAD_GPNAME=wg_hr1);
```

## 相关链接

[CREATE APP WORKLOAD GROUP MAPPING](CREATE-APP-WORKLOAD-GROUP-MAPPING.md)，[DROP APP WORKLOAD GROUP MAPPING](DROP-APP-WORKLOAD-GROUP-MAPPING.md)