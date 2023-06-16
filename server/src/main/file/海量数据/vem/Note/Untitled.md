 ```
 --删除
 drop table if exists metadata.database_version;
 
 --创建表
 create table metadata.database_version(
 id 				serial NOT NULL,
 regex 			varchar(255) NULL,
 description     varchar(50) NULL,
 dbtype			varchar(50)  NULL,
 PRIMARY KEY(id)
 );
 ```



```
--删除
drop table if exists monitor.collector_detail;

--创建表
create table monitor.collector_detail(
id 				serial NOT NULL,
"sql"			varchar NULL,
db_version		_varchar NULL,
PRIMARY KEY(id)
);

--初始化数据
insert into monitor.collector_detail select id, sql  from monitor.collector;
```



```
ALTER TABLE monitor.collector DROP "sql";

ALTER TABLE monitor.collector
	ADD COLUMN detail_id serial null;
	
update monitor.collector set detail_id = id where detail_id != id;

```

```
alter table monitor.metric rename collector_id to detail_id;
```







```
--删除
drop table if exists monitor.collector;

--创建表
create table monitor.collector(
id 				bigserial NOT NULL,
title			varchar(50) NULL,
dbtype			varchar(50) NULL,
remark			text NULL,
"sql"			varchar NULL,
updated_time	timestamp NUL,
"type"			varchar(50) NULL,
data_type		varchar(32) NULL,
collect_interval int4 NULL,
CONSTRAINT collector_pkey PRIMARY KEY(id)
);

```



跟collector相关的表

- dbid_job
- metric