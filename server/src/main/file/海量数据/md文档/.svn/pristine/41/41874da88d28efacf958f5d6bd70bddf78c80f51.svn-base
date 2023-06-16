##### ROLLBACK

**功能描述**

回滚当前事务并取消当前事务中的所有更新。

在事务运行的过程中发生了某种故障，事务不能继续执行，系统将事务中对数据库的所有已完成的操作全部撤销，数据库状态回到事务开始前。

**语法格式**

```
ROLLBACK [ WORK | TRANSACTION ];
```

**参数说明**

WORK | TRANSACTION：可选关键字。除了增加可读性，没有任何其他作用。

**注意事项**

无。

**示例**

1. 创建测试表test。

```
CREATE TABLE test(id int);
```

2. 开启一个事务 。

```
START TRANSACTION; 
```

3. 插入数据。

```
INSERT INTO test(id) VALUES(1);
```

4. 查询当前数据。

```
select * from test;
```

当结果显示如下信息，则表示插入成功。

```
 id 
----
  1
(1 row)
```

5. 取消所有更改 。

```
ROLLBACK;
```

6. 查询数据进行验证。

```
select * from test;
```

当结果显示如下信息，则表示回滚成功。

```
id 
----
(0 rows)
```

   