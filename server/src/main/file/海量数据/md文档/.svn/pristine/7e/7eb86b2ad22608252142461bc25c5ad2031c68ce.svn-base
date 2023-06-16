# 高速导入导出transportable

此文档需替换掉原：管理员指南->高速导入导出transportable

## 功能描述

transportable table功能旨在提供一种通用的快速在线迁移方法，其可以最小支持到分区级别的细粒度在线快速物理迁移。

## 语法格式

- 导出

```sql
  vb_ttdump [OPTION]...

General options:
  -d, --database=DBNAME                       database name to dump
  -D, --data-directory=DIR                    directory for output data file
  -f, --metadata-file=FILENAME                metadata file name
  -F, --format=p|t                            output file format (original, tar)
  -n, --schema=SCHEMA                         schema name to dump
  -t, --export-tables=TABLE                   table name(s) to dump
  -P, --export-table.partitions=TABLE.PARTITION  table.partition name(s) to dump
  -v, --verbose                               verbose mode
  -V, --version                               output version information, then exit
  -?, --help                                  show this help, then exit

Connection options:
  -h, --host=HOSTNAME                         database server host or socket directory
  -p, --port=PORT                             database server port number
  -U, --username=NAME                         connect as specified database user
  -w, --no-password                           never prompt for password
  -W, --password=PASSWORD                     the password of specified database user
```

- 导入

```sql
  vb_ttrestore [OPTION]...

General options:
  -d, --database=DBNAME                       database name to restore
  -D, --data-directory=DIR                    directory for input data file
  -f, --metadata-file=FILENAME                metadata file name
  -n, --schema=SCHEMA                         schema name to restore
  -t, --import-tables=TABLE                   table name(s) to restore
  -P, --import-tables.partitions=TABLE        table.partition name(s) to restore
  -v, --verbose                               verbose mode
  -V, --version                               output version information, then exit
  --remap-schema=OLDSCHEMA:NEWSCHEMA          remap old schema to new one
  --table-exists-action=skip|replace          action when table exists (skip, replace)
  -?, --help                                  show this help, then exit

Connection options:
  -h, --host=HOSTNAME                         database server host or socket directory
  -p, --port=PORT                             database server port number
  -U, --username=NAME                         connect as specified database user
  -w, --no-password                           never prompt for password
  -W, --password=PASSWORD                     the password of specified database user
```

## 参数说明

- **vb_ttdump导出**
  - -D：指定数据文件的输出路径，需要指定具有读写权限的操作系统全路径，并确保存储空间充足。

  - -F：指定输出格式，-p（原样输出），-t（tar格式输出）。

  - -f：指定元数据导出文件。

  - -t：指定要导出的表。可以通过schema_name.table_name来指定模式名，多张表可以通过多个-t来指定。指定-t即为导出整张表。当-t与-P中指定的表名有冲突时，既会导出整张表，也会导出分区。最终表现形式为导出了整张表。

  - -P：指定要导出的分区。可以将schema_name.table_name当作表名来指定模式名，输入格式可以为schema_name.table_name.partname或者table_name.partname。多个分区可以通过多个-P来指定。

  - -n：指定要导出的schema。当指定-n参数时，忽略-t与-P参数，此时导出指定schema下的所有表。

  - -d：指定要导出的database。该参数不与-n，-t冲突，如果没有指定-n，-t参数，则导出整个database；否则按照更细粒度的指定进行导出。不指定时默认数据库为vastbase数据库。

  - -v：在导出时显示详细信息。

  - -V：查看该工具软件版本号。

  - -p：指定登录数据库的端口号。

  - -U：指定导出所使用的用户，该用户要求为具有管理员权限的用户。

  - -W：指定-U参数所指定的用户的密码。

  - -w：不出现输入密码提示。如果主机要求密码认证并且密码没有通过其它形式给出，则连接尝试将会失败。 

  - -?,--help：显示帮助信息。

- **vb_ttrestore导入**
  - -D：指定数据文件的输入路径，确保操作用户对该路径具有读写权限。
  - -f：指定元数据备份文件路径。
  - -t：指定要导入的表。可以将备份集中的部分指定表进行恢复，多张表可以通过多个-t来指定。指定-t即为导入整张表，需要导入分区的表不需要使用-t标识。当-t与-P中指定的表名有冲突时，既会导入分区，也会导入整张表，最终表现形式为导入了整表。
  - -P：指定要导入的分区。可以将schema_name.table_name当作表名来指定模式名，输入格式可以为schema_name.table_name.partname或者table_name.partname。多个分区可以通过多个-P来指定。
  - -n：指定要导入的schema。当指定-n参数时，忽略-t与-P参数，此时导入指定schema下的所有表。指定的schema如果在目标库中存在，则恢复到该schema下；如果目标库中不存在该指定schema，则创建schema后进行导入。
  - -d：指定要导入的database，不指定时默认数据库为vastbase数据库。
  - --remap-schema：通过old_schema_name:new_schema_name的格式为转储对象重新指定schema。
  - --table-exists-action：指定导入对象已存在时的处理方式。可选项：skip，replace。默认值为replace，表示删除之前的对象后导入新对象。skip表示跳过该对象不进行导入。
  - -v：在导入时显示详细信息。
  - -V：查看该工具软件版本号。
  - -p：指定登录数据库的端口号。
  - -U：指定导入所使用的用户，该用户要求为具有管理员权限的用户。
  - -W：指定-U参数所指定的用户的密码。
  - -w：不出现输入密码提示。如果主机要求密码认证并且密码没有通过其它形式给出，则连接尝试将会失败。 
  - -?,--help：显示帮助信息。

## 注意事项

- 进行该迁移操作需确保源库及目标库软件版本一致。

- 确保源库及目标库的编码一致。

- 需确保待迁移的表上没有外键约束；对于有外键约束的表，需要将关联表同时导出；存在外键约束的情况下，如果不将父表一起进行迁移，则在导入子表时将报错，子表无法导入到目标库中。迁移工具在导出时不会检测外键约束，需要操作者先自行检查。

- 禁用状态的索引不导出，如果要迁移禁用的索引，需要操作者先取消其禁用状态。

- 该迁移工具仅支持astore存储引擎下无压缩的行存表，不支持全局分区索引，不支持system分区，不支持interval分区，不支持物化视图。

- 该迁移工具不迁移表空间，使用目标数据库的默认表空间。

- 该迁移工具不支持列备注的迁移。

- 在动态加载前，需要事先确认表结构是否支持恢复。分别对源表和目标表进行查询，方式如下：

  ```sql
  select oid from pg_class where relname='relname';
  select * from pg_attribute where attrelid = oid and attnum>0 order by attnum;
  ```


## 示例

- **示例1：**表级导入导出

1、在数据库vastbase中创建测试表test_1021，并插入数据。

```sql
create table test_1021(id int,name varchar);
insert into test_1021 values(1, 'aaa'), (2, 'bbb');
```

2、创建一个导入的目标数据库transtest。

```sql
create database transtest;
```

3、创建备份文件路径。

```shell
mkdir -p /tmp/bak/
```

4、使用系统管理员导出表。

```shell
vb_ttdump -U vbadmin -W 'Vbase@admin' -D /tmp/bak -F p -f /tmp/bak/1021.dmp -t test_1021 -d vastbase -p $PGPORT;
```

5、修改导出文件的权限。

```shell
chmod -R 777 /tmp/bak
```

6、使用系统管理员将表test_1021导入至目标数据库transtest下。

```shell
vb_ttrestore -U vbadmin -W 'Vbase@admin' -D /tmp/bak/ -f /tmp/bak/1021.dmp -t test_1021 -d transtest -p $PGPORT;
```

7、切换至数据库transtest下，查看导入结果。

```sql
\c transtest
select * from test_1021;
```

返回结果为：

```sql
id |  name 
----+--------
  1 |  aaa
  2 |  bbb
(2 rows)
```

- **示例2：**分区级导入导出

1、在数据库vastbase中创建range分区表test_db_1103598，并插入数据。

```sql
CREATE TABLE t_range_1103598(
c1 integer,
c2 varchar)
PARTITION BY RANGE (c1)
(
PARTITION p1 VALUES LESS THAN(5),
PARTITION p2 VALUES LESS THAN(10),
PARTITION p3 VALUES LESS THAN(15));

insert into t_range_1103598 values(1,'test1');
insert into t_range_1103598 values(6,'test2');
insert into t_range_1103598 values(11,'test3');
```

2、创建一个导入的目标数据库test_restore_db。

```sql
create database test_restore_db;
```

3、创建备份文件路径。

```shell
mkdir -p /tmp/trans_test
```

4、使用系统管理员导出一个分区。

```shell
vb_ttdump -U vbadmin -W 'Vbase@admin' -D /tmp/trans_test -F p -f  /tmp/trans_test/1103598.dmp -P t_range_1103598.p1 -d vastbase -p $PGPORT;
```

5、修改导出文件的权限。

```shell
chmod -R 777 /tmp/trans_test
```

6、使用系统管理员将表导入至目标数据库test_restore_db下。

```shell
vb_ttrestore -U vbadmin -W 'Vbase@admin' -D /tmp/trans_test -f /tmp/trans_test/1103598.dmp -P t_range_1103598.p1 -d test_restore_db -p $PGPORT;
```

7、切换至数据库test_restore_db下，验证导入结果。

```sql
\c test_restore_db
```

（1）向各分区插入数据。

```sql
insert into t_range_1103598 values(2,'test4');
insert into t_range_1103598 values(7,'test4');
insert into t_range_1103598 values(12,'test4');
```

（2）查询该表中各分区的数据。

```sql
select * from t_range_1103598 partition(p1);
select * from t_range_1103598 partition(p2);
select * from t_range_1103598 partition(p3);
```

返回结果为：

```sql
 c1 |  c2             --分区p1
----+-------
  1 | test1
  2 | test4
(2 rows)

 c1 |  c2             --分区p2
----+-------
  7 | test4
(1 row)

 c1 |  c2             --分区p3
----+-------
 12 | test4
(1 row)
```

由此可见，分区表t_range_1103598以及原p1分区中的数据已成功导入至数据库test_restore_db中。
