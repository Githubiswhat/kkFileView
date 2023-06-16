# DROP APP WORKLOAD GROUP MAPPING

## 功能描述

删除一个应用映射组。

## 注意事项

用户对当前数据库有DROP权限，才可以删除应用映射组。

## 语法格式

```
DROP APP WORKLOAD GROUP MAPPING [ IF EXISTS ] app_name;
```

## 参数说明

- **IF EXISTS**

  如果不存在相同名称的应用映射组，不会抛出一个错误，而会发出一个通知，告知应用映射组不存在。

- **app_name**

  通过create app workload group mapping语句创建出的应用映射组名称。

  取值范围：字符串，要符合标识符的命名规范。

## 示例

1、创建一个资源池，其控制组指定为"DefaultClass"组下属的"High" Timeshare Workload控制组。

```
CREATE RESOURCE POOL pool1 WITH (CONTROL_GROUP="High");
```

2、创建一个负载组，关联已创建的资源池。

```
CREATE WORKLOAD GROUP group1 USING RESOURCE POOL pool1;
```

3、创建一个应用映射组，关联已创建的负载组。

```
CREATE APP WORKLOAD GROUP MAPPING app_wg_map1 WITH (WORKLOAD_GPNAME=group1);
```

4、创建一个默认应用映射组，关联默认的负载组。

```
CREATE APP WORKLOAD GROUP MAPPING app_wg_map2;
```

5、删除应用映射组。

```
DROP APP WORKLOAD GROUP MAPPING app_wg_map1;
DROP APP WORKLOAD GROUP MAPPING app_wg_map2;
```

## 相关链接

[CREATE APP WORKLOAD GROUP MAPPING](CREATE-APP-WORKLOAD-GROUP-MAPPING.md)，[ALTER APP WORKLOAD GROUP MAPPING](ALTER-APP-WORKLOAD-GROUP-MAPPING.md)