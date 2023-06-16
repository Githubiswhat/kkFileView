### SELECT... FOR UPDATE

**功能描述**

SELECT ... FOR UPDATE语句用于锁定满足WHERE查询条件的行，以防止其他用户在您结束事务之前锁定或更新这些行。

**语法格式**

```
SELECT ... FOR UPDATE [ |WAIT integer| NOWAIT]
```

**参数说明**


- FOR UPDATE不跟任何参数：如果当前查询的结果中存在锁，则该查询一直等待（等待期间您无法执行其他操作），直到查询结果中的锁被释放后，立即返回查询结果。
- WAIT integer：如果当前查询的结果中存在锁，且超过integer秒后仍未释放锁，则报错，其中integer为等待超时时间。

  - integer取值范围：正整数。
  - integer取值0：等同NOWAIT。
- NOWAIT：当前查询的结果中存在锁，立即报错。

**注意事项**

- Vastbase G100数据库中可配置参数“update lockwait timeout”，该参数表示对单个锁的超时时间，默认为 120 秒。当select for update wait integer语句因为加锁需要进行等待时，其中integer值为等待所有行级锁的总计时长，因此，可能在总时长还未达指定值前，该语句因为等待单个行级锁超时而退出并解锁。
- Vastbase支持FOR UPDATE后跟表或者视图名，不支持跟列名。
- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

**兼容性**

Vastbase G100 V2.2版本与Oracle 11G R2版本对比，不支持的选项为：[SKIP LOCKED]。

**示例**

1、创建并切换至兼容模式为ORACLE的数据库db_oracle。

```
CREATE DATABASE db_oracle dbcompatibility=’A’;     
\c db_oracle
```

2、创建测试表并插入数据。

```
create table test8(
id number,
orderid number,
productid number,
price number(10,2),
quantity number
);
insert into test8(id,orderid,productid,price,quantity) 
values(1,1,1,6,10),
(2,1,2,4,5),
(3,1,3,10,2),
(4,2,1,3,6),
(5,2,2,4,6);
```

3、在会话1中，在事务中使用for update加锁，暂不提交。

```
begin;
select * from test8 where quantity <6 for update;
id | orderid | productid | price | quantity
--------------------------------------------
2 |        1  |          2   | 4.00 | 5
3 |        1  |          3   | 10.00 | 2
(2rows)
```

4、切换至会话2，使用for update nowait 获取同样数据时，提示获取锁失败。

```
select * from test8 where quantity <6 for update nowait;
```

返回结果如下：

```
ERROR:could not obtain lock on row in relation “test8”
```

5、切换至会话2，使用for update wait 2 获取同样数据，超时（2s）提示获取锁失败。

```
elect * from test8 where quantity <6 for update wait 2;
```

返回结果如下：

```
ERROR:could not obtain lock on row in relation “test8”
```

6、切换至会话2，使用for update wait 600 获取同样数据，n取足够大，阻塞等待会话1事务提交释放锁。

```
select * from test8 where quantity <6 for update wait 600;
```

7、切换至会话1，在等待返回结果的600秒内，提交事务，释放锁。

```
commit;
```

8、切换至会话2，成功返回查询结果。

```
id | orderid | productid | price | quantity
--------------------------------------------
2 |        1  |          2   | 4.00 | 5
3 |        1  |          3   | 10.00 | 2
(2rows)
```
