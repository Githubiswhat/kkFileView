### DBMS_STATS

**功能描述**

DBMS_STATS包用于查看和修改收集的所有数据库对象的统计信息，以便能估量统计数据（尤其是针对较大的分区表），从而制定出速度更快的SQL执行计划。该内置包中包含以下函数：

- GATHER_SCHEMA_STATS函数：收集模式中所有对象的统计信息。

**注意事项**

无。

**兼容性**

Vastbase G100 V2.2版本与Oracle 11g R2版本相比，仅支持GATHER_SCHEMA_STATS函数。

#### GATHER_SCHEMA_STATS函数

**功能描述**

该函数用于收集指定模式下的所有对象的统计信息。

**语法格式**

- 第一种：

```
DBMS_STATS.GATHER_SCHEMA_STATS(
ownname		VARCHAR2，
estimate_percent NUMBER   DEFAULT 100,
block_sample BOOLEAN DEFAULT FALSE,
method_opt		VARCHAR2 default 'FOR ALL COLUMNS SIZE AUTO',
degree 			NUMBER DEFAULT NULL,
granularity 		VARCHAR2 DEFAULT 'GLOBAL'
cascade			 BOOLEAN DEFAULT FALSE,
stattab 			VARCHAR2 DEFAULT NULL,
statid 			VARCHAR2 DEFAULT NULL,
options 			VARCHAR2 DEFAULT 'GATHER',
objlist 		OUT ObjectTab, 
statown 		VARCHAR2 DEFAULT NULL,
no_invalidate 	BOOLEAN DEFAULT FALSE,
force 		BOOLEAN DEFAULT FALSE, 
obj_filter list ( ObjectTab DEFAULT NULL);
```

- 第二种：

```
DBMS_STATS.GATHER_SCHEMA_STATS(
ownname		VARCHAR2，
estimate_percent NUMBER   DEFAULT 100,
block_sample BOOLEAN DEFAULT FALSE,
method_opt		VARCHAR2 default 'FOR ALL COLUMNS SIZE AUTO',
degree 			NUMBER DEFAULT NULL,
granularity 		VARCHAR2 DEFAULT 'GLOBAL'
cascade			 BOOLEAN DEFAULT FALSE,
stattab 			VARCHAR2 DEFAULT NULL,
statid 			VARCHAR2 DEFAULT NULL,
options 			VARCHAR2 DEFAULT 'GATHER',
statown 		VARCHAR2 DEFAULT NULL,
no_invalidate 	BOOLEAN DEFAULT FALSE,
force 		BOOLEAN DEFAULT FALSE, 
obj_filter list ( ObjectTab DEFAULT NULL);
```

**参数说明**

- ownname：要分析的schema（NULL表示当前schema）。
- estimate_percent: 确定要采样的行的百分比，有效范围在0.000001~100。
- block_sample：表示是否使用随机块采样代替随机行采样，默认为false。注意：Vastbase 目前无需支持，可忽略该参数。
- method_opt：默认值为：FOR ALL COLUMNS SIZE AUTO（仅支持该选项，其
  他选项不支持）。该选项可接受的语法为：

```
FOR ALL [INDEXED|HIDDEN] COLUMNS [size clause]
```

- size clause := SIZE {integer| REPEAT| AUTO |SKEWONLY}
- Integer：直方图数。必须在1~2048范围内。
- REPEAT：仅在已经具有直方名的列上收集直方名。
- AUTO：数据库根据数据分布和列的工作量确定要收集直方图的列。
- SKEWONLY：数据库根据列的数据分布来确定要收集直方图的列。
- degree：并行度，默认degree值为 NULL，也可以使用常量 AUTO DEGREE设
  置自动并行度（Vastbase 仅支持 NULL，其他选项不支持）。
- granularity：要收集的统计信息的粒度(仅在表已分区时才适用)。取值可以是：'GLOBAL'，'GLOBAL AND PARTITION’，'PARTITION'
- 'GLOBAL'：收集全局统计数据。
- 'GLOBAL AND PARTITION’：收集全局和分区级别的统计数据。
- 'PARTITION'：收集分区级别的统计信息。
- cascade：收集有关索引的统计信息。除了收集表和列统计信息之外，使用此选项
  等效于在模式中的每个索引上运行 GATHER_INDEX_STATS 过程，使用该常量 DBMS_STATS.AUTO_CASCADE 让数据库确定是否要收集索引统计信息。这是默认值（支持该参数传入，但不校验并忽略该参数）。
- stattab：用户统计信息表标识符，用于描述将当前统计信息保存在何处。
- statid：标识符。支持该参数传入，但不校验并忽略该参数。
- options：指定需要收集统计信息的对象。有效值如下：Vastbase 仅支持默认值 GATHER（收集 schema 中所有对象的统计信息），其他选项不支持。
- objlist：过旧或空的对象列表（支持该参数传入，但不校验并忽略该参数）。
- statown： 包含的 schema（支持该参数传入，但不校验并忽略该参数）。
- no_invalidate：是否使相关游标无效，设置为 false 表示使得相关游标无效:设置为 true 则相反（支持该参数传入，但不校验并忽略该参数）。
- force：收集有关对象的统计信息，被锁定的对象也会被统计（支持该参数传入，但不校验并忽略该参数）。
- obj_filter list：设置对象过滤器列表（支持该参数传入，但不校验并忽略该参数）。

**兼容性**

1、granularity参数只兼容部分选项，暂不支持的选项如下（与oracle的差异）：

- 'ALL'：收集所有(子分区，分区和全局)统计信息。
- 'AUTO'：根据分区类型确定粒度，这是默认值。
- 'DEFAULT'：收集全局和分区级别的统计信息。已过时，为向下兼容保留。
- 'SUBPARTITION'：收集子分区级别的统计信息。

2、GATHER参数目前Vastbase G100 Build 9暂不支持的选项如下（与oracle的差异）：

- GATHER AUTO ：自动收集所有必要的统计信息。
- GATHER STALE：通过查询*TAB MODIFICATIONS 视图来收集陈旧对象的统计信息。同样，数据库返回发现已过时的对象的列表。
- GATHER EMPTV：收集有关当前没有统计信息的对象的统计信息。同样。数据库返回发现没有统计信息的对象列表。
- LIST AUTO：返回要使用处理的对象的列表 GATHER AUTO。
- LIST STALE：返回通过查看* TAB MODIFICATIONS 视名确定的陈旧对象的列表。
- LIST EMPTY：返回当前没有统计信息的对象列表。

**示例**

1、创建模式。

```
create schema zmm;
```

2、创建分区表。

```
create table zmm.customer_address
(
    ca_address_sk       integer                  not null   ,
    ca_address_id       character(16) 
)
partition by range (ca_address_sk)
(
        partition p1 values less than(500),
        partition p2 values less than(1000),
        partition p3 values less than(1500),
        partition p4 values less than(2000),
        partition p5 values less than(2500),
        partition p6 values less than(3000),
        partition p7 values less than(4000),
        partition p8 values less than(maxvalue) 
)
enable row movement;
```

3、插入数据。

```
insert into zmm.customer_address(ca_address_sk) values(generate_series(1,5000));
```

4、调用dbms_stats.gather_schema_stats函数收集统计信息。

```
exec dbms_stats.gather_schema_stats(ownname=>'zmm',estimate_percent=>100,block_sample=>false,method_opt=>'for all columns size auto',degree=>DBMS_STATS.AUTO_DEGREE,granularity=>'GLOBAL',cascade=>DBMS_STATS.AUTO_CASCADE,stattab=>'',statid=>'',options=>'GATHER AUTO',no_invalidate=>DBMS_STATS.AUTO_INVALIDATE,force=>false,statown=>'');
```

返回结果如下，则表示函数调用成功：

```
NOTICE:  PL/SQL procedure successfully completed.
 gather_schema_stats
---------------------

(1 row)
```