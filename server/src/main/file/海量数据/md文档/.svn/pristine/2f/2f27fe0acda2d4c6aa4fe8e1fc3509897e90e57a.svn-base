# date类型控制参数

**功能描述**

date类型控制参数vb_date_type用来控制date类型的底层存储类型。

**语法格式**

- ORACLE兼容模式下，支持通过alter命令设置date类型控制参数vb_date_type，默认值为1。

  ```
  alter system set vb_date_type = [1/2]; 
  ```

- ORACLE兼容模式下，通过修改postgresql.conf设置date类型控制参数

  ```
  vi $PGDATA/postgresql.conf
  //设置参数:
  vb_date_type = [1/2];
  ```

**参数说明**

- vb_date_type = 1时 date底层存储为oradate。
- vb_date_type = 2时 date底层存储为timestamp。

**注意事项**

该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。


**示例**

- 示例1： oracle兼容下设置date类型控制参数。

1、创建数据库，设置兼容模式为oracle。

```
CREATE DATABASE date_test_oracle DBCOMPATIBILITY 'A';
```

2、配置date类型控制参数vb_date_type。

```
alter system set vb_date_type = 1;
```

3、重启数据库。

```
vb_ctl restart
```

4、查看date兼容效果。

```
select '2022-08-19'::date;
```

查询结果显示为：

```
       oradate
---------------------
 2022-08-19 00:00:00
(1 row)
```

- 示例2：ORACLE兼容模式下，通过修改postgresql.conf设置date类型控制参数。

1、创建数据库。

```
CREATE DATABASE date_test_postgresqlconf DBCOMPATIBILITY 'A';
```

2、配置date类型控制参数。

```
vi $PGDATA/postgresql.conf
//设置参数:
vb_date_type = 2;
```

3、重启数据库。

```
vb_ctl restart
```

4、查看date兼容效果。

```
\c date_test_postgresqlconf
select '2022-08-19'::date;
```

查询结果显示为：

```
      timestamp
---------------------
 2022-08-19 00:00:00
(1 row)
```

