# CREATE WORKLOAD GROUP

## 功能描述

创建一个负载组，关联已创建的资源池，指定资源池内可并发的作业数量。

## 注意事项

用户对当前数据库有CREATE权限，才可以创建负载组。

## 语法格式

```
CREATE WORKLOAD GROUP wg_name
     [ USING RESOURCE POOL pool_name [ WITH ( ACT_STATEMENTS = counts) ] ];
```

## 参数说明

- **wg_name**

  负载组名称。负载组名称不能和当前数据库里其他负载组重名。

  取值范围：字符串，要符合标识符的命名规范。

- **pool_name**

  资源池名称。

  取值范围：字符串，要符合标识符的命名规范。

- **counts**

  负载组所在资源池内的并发数量。

  取值范围：整型，取值范围为-1 ~ INT_MAX。

## 示例<a name='example'>

1、创建一个默认负载组，其资源池为默认的资源池。

```
CREATE WORKLOAD GROUP wg_name1;
```

2、创建资源池pool1。

```
CREATE RESOURCE POOL pool1;
```

3、创建一个负载组，关联已创建的资源池。

```
CREATE WORKLOAD GROUP wg_name2 USING RESOURCE POOL pool1;
```

4、创建一个负载组，关联已创建的资源池，并设置并发数量为10。

```
CREATE WORKLOAD GROUP wg_name3 USING RESOURCE POOL pool1 WITH (ACT_STATEMENTS=10);
```

5、删除负载组和资源池。

```
DROP WORKLOAD GROUP wg_name1;
DROP WORKLOAD GROUP wg_name2;
DROP WORKLOAD GROUP wg_name3;
DROP RESOURCE POOL pool1;
```



## 相关链接

[ALTER WORKLOAD GROUP](ALTER-WORKLOAD-GROUP.md)，[DROP WORKLOAD GROUP](DROP-WORKLOAD-GROUP.md)

