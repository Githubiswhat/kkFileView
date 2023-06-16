#### DISTINCT列时可以使用非ORDER BY列

**功能描述**

兼容MySQL、MariaDB中distinct语句的目标列中，可以使用order by中未列出的列。

**注意事项**

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。

- 利用GUC参数vastbase_sql_mode，控制该功能。
  - vastbase_sql_mode='ONLY_FULL_GROUP_BY'时，distinct list中不可以使用非分组列。
  - vastbase_sql_mode=''时，distinct list中可以使用非分组列。

**语法格式**

```
SELECT DISTINCT column1, column2
FROM table_name
WHERE [condition]
ORDER BY column3
```

**示例**

1、创建兼容MySQL的库db_mysql，并进入。

```
create  database  db_mysql  dbcompatibility 'B';
\c  db_mysql
```

2、创建测试表并插入数据。

```
create  table  test  (id  varchar(32)  ,names  varchar(32));
insert  into  test  values('1','zhangsan');
insert  into  test  values('12','lisi');
insert  into  test  values('2','wangwu');
insert  into  test  values('2','maliu');
```

 3、设置vastbase_sql_mode。

```
set  vastbase_sql_mode  ='';
show vastbase_sql_mode;
```

结果显示如下： 

```
 vastbase_sql_mode 
-------------------
 
(1 row)
```

4、执行查询。

```
select distinct(id) from test order by (id+0), (names+0);
```

结果显示如下： 

```
id 
----
 1
 2
 12
(3 rows)
```

#### 