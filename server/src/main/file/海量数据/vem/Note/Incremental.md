```
rpchandler

IncrementalCollectTask
```



## snapshot_type

- scn																								oracle
- InfoSeriresContans.Time
- lsn  																						      pg
- binlog_position          																mysql





1、先在tb_run_jobinfo 查看任务是否在running

2、修改tb_portal_job 





kafkaOffset								kafka的偏移量

kafkaSubOffset						 一条kafka消息中的偏移量





普通表

分区表



### 名词解释

- HAS_RAN_TASK
- RPC_THREAD_Y
- RPC_THREAD_N
- CDC（Change Data Capture）即数据变更抓取







继承体系

Task   -->  





### 关键数据库表

- run_task
- 





snapshot





RecordReceriver									用来从kafka查数据

addRewordToApply



recordReceive				包装了kafka的comsumer

recoedSender 				包装了kafka的productor













### 采集 

RpcHandler   -->   IncrementalCollectTask   -->   PgIncrementalCollectTask    -->   PgCollectTaskExecutor   -->  PgCollectListener



### 应用

RpcHandler   -->   IncrementalAppleTask   -->  ApplyListener 



实际的采集器					由collectorFactory创建

pgLogicalReplicaCollector

XStreamCollector

mysqlCollector

sqlservercollector

infomixCollector





采集过程

1、由collector 进行采集

2、由recordStream 将数据序列化

3、由recodeSender 将数据写入kafka





应用过程

1、由recordReceive   从kafka读数据			调用addRecordToApply 写到一个  recordList 里面

2、由applytask    从recordList里面读取Record ，然后调用transa.handler 的applyData 去写到数据库里面





在 ApplyTask 的start方法里面，首先从recorfList里面拿出来一个record， 这个record的是自己封装的。

首先，如果拿到的record 为空，先进行一个循环，如果当前有没有提交的事务，当循环次数达到taskconf里面设定好的waitTime次数后，就会进行提交，然后把事务变成空的

然后，如果拿到的record不为空，判断一下当前的record 是否有finish标记， 当获取到finish标记后，如果当前存在未提交的事务，先回滚，然后将完整的事务写到目标库，更新topic再消费

如果没有finish标记，判断是否含有commit标记，如果有，调用commitaction进行提交，

如果都没有，调用transaction.apply(), 把record加到batch里面，然后等后面进行一个提交







### 查看kafka数据

```
./kafka-topics.sh --describe  --topic xxxxx.xx.xxxxx.xxx 

--topic为topic名称


./kafka-console-consumer.sh --bootstrap-server kafka:9092 --topic xxxxx.xx.xxxxx.xxx --from-beginning 

# 若没有任何返回或没有响应，则该topic中没有数据内容；否则就是有数据


kafka-server-start.sh /export/server/kafka_2.12-2.4.1/config/server.properties  >> /dev/null 2>&1 &
```





选择跟缺陷一样的数据库，然后新建作业的时候，不能跟缺陷的作业名字一样，不然会覆盖掉原本缺陷的信息，例如在kafkaoffset中，或者是在incremental_offset表中的原本缺陷的作业信息





日志的路径

```
data/log4engine
```



kafka 的数据

```
data/kafka/log
```



kafka 启动与暂停

```
./kafka-server-start.sh ../config/server.properties  >> /dev/null 2>&1 &
./kafka-server-stop.sh 
```











进行增量迁移的时候，需要在目标库建立一个kafkaoffset表，在源库建立一个incremental_offset表





```
create or replace function creatData2 ( ) returnsboolean As
$BODYs
declare ii integer;begin
II:=l;
FOR ii IN l..1o000 LOOP
ISERD Mno utf8.orders_insert values (11,'363 ,'0',1224560.83','1996-01-02',，'5-1ON'，'Clerk+000095055' , '0', 'nstructions sleet
-一一-工一-.· . .
end loop;
return true;end;
$BODYs
LANGUAGE plpgsql ;
select *from creatData2 o as tab ;
```





```
drop PROCEDURE if exists p_insert ;
DELIMITER $$
CREATE PROCEDURE p_insert 
begin
declare i int default 0;
  while i<10000 do
    insert into ORDER.S _TDNSERTvalues(i,'3685','0',/1224560.3',，1996-1-12',/ 5-10NV', 'Cletk1095055', U','nstrnuctions sleep furiouasly amng ;
    set i=i + l;
end while;
 END$$
DELIMITER $$
call p_insert () ;
select count (*) from ORDERS_INSERT;
```



```
2 分 52 秒    58条每秒
3 分 38 秒 	45条每秒
```







```
18:11:40 开始采集
18:11:42 开始执行JDBC
18:12:01 结束采集
18:12:02 发送到kafka 完成
18:20:14 应用完成
```



```
14:42:52
```



```
kafka 配置在哪里
```





```
11:46:18    开始采集
11:46:48    采集完成


11:51:58    开始应用
12:01:26	应用结束

9分钟28秒


12:10:41    开始采集
12:11:23    采集结束


12:16:13    开始应用
12:24:52    应用结束


8分钟39秒
```









| 采集 | 数量 | 时间 |
| ---- | ---- | ---- |
|      | 1W   | 11s  |
|      |      |      |
|      |      |      |







