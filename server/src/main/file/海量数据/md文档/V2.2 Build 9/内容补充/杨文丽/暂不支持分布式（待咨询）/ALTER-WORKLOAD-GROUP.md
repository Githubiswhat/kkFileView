# ALTER WORKLOAD GROUP

## 功能描述

修改一个负载组，设置并发数量。

## 注意事项

用户对当前数据库有ALTER权限，才可以修改负载组。

## 语法格式

```
ALTER WORKLOAD GROUP wg_name
     USING RESOURCE POOL pool_name [ WITH ( ACT_STATEMENTS = count ) ];
```

## 参数说明

- **wg_name**

  负载组名称。负载组名称不能和当前数据库里其他负载组重名。

  取值范围：字符串，要符合标识符的命名规范。

- **pool_name**

  资源池名称。

  取值范围：字符串，已创建的资源池。

- **counts**

  负载组所在资源池内的并发数量。

  取值范围：整型，取值范围为-1 ~ INT_MAX。

## 示例

1、创建资源池pool1。

```
CREATE RESOURCE POOL pool1;
```

2、创建负载组group1。

```
CREATE WORKLOAD GROUP group1;
```

3、更新一个负载组group1的并发数量为10。其关联的资源池为pool1。

```
ALTER WORKLOAD GROUP group1 USING RESOURCE POOL pool1 WITH (ACT_STATEMENTS=10);
```

4、删除负载组group1和资源池pool1。

```
DROP WORKLOAD GROUP group1;
DROP RESOURCE POOL pool1;
```

## 相关链接

[CREATE WORKLOAD GROUP](CREATE-WORKLOAD-GROUP.md)，[DROP WORKLOAD GROUP](DROP-WORKLOAD-GROUP.md)