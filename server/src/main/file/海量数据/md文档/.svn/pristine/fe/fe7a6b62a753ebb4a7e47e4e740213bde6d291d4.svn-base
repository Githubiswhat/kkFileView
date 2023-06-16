# 高速导入导出transportable

**功能描述**

transportable table功能旨在提供一种通用的快速在线迁移方法，其可以最小支持到表级别的细粒度在线快速物理迁移。

**语法格式**

- 导出

```
$vb_ttdump[]OPTION...
-D
-F
-f
-t
-n
-d
-v
-V
-p
-U
-W
-?,--help
```

- 导入

```
$vb_ttdump[]OPTION...
-D
-f
-t
-n
-d
--remap-schema
--tale-exists-action
-v
-V
-p
-U
-?,--help
```

**参数说明**

- 导出

-D：指定数据文件的输出路径，需要指定具有读写权限的操作系统全路径，并确保存储空间充足。
-F：指定输出格式，-p（原样输出），-t（tar格式输出）。
-f：指定元数据导出文件
-t：指定要导出的表。可以通过schema_name.table_name来指定模式名，多张表可以通过多个-t来指定。
-n：指定要导出的schema。当指定-n参数时，忽略-t参数，此时导出指定schema下的所有表。
-d：指定要导出的database。该参数不与-n，-t冲突，如果没有指定-n，-t参数，则导出整个database；否则按照更细粒度的制定进行导出。
-v：在导出时显示详细信息。
-V：查看该工具软件版本号。
-p：指定登录数据库的端口号。
-U：指定导出所使用的用户，该用户要求为具有管理员权限的用户。
-W：指定-U参数所指定的用户的密码。
-?,--help：显示帮助信息。

- 导入


-D：指定数据文件的输入路径，确保操作用户对该路径具有读写权限。
-f：指定元数据备份文件路径。
-t：指定要导入的表。可以将备份集中的部分指定表进行恢复，多张表可以通过多个-t来指定。
-n：指定要导入的schema。当指定-n参数时，忽略-t参数，此时导入指定schema下的所有表。指定的schema如果在目标库中存在，则恢复到该schema下；如果目标库中不存在该指定schema，则创建schema后进行导入。
-d：指定要导入的database。
--remap-schema：通过old_schema_name:new_schema_name的格式为转储对象重新指定schema。
--table-exists-action：指定导入对象已存在时的处理方式。可选项：skip，replace。默认值为skip，表示跳过该对象不进行导入；replace表示删除之前的对象后导入新对象。
-v：在导入时显示详细信息。
-V：查看该工具软件版本号。
-p：指定登录数据库的端口号。
-U：指定导入所使用的用户，该用户要求为具有管理员权限的用户。
-?,--help：显示帮助信息。

**注意事项**

- 进行该迁移操作需确保源库及目标库软件版本一致。
- 确保待迁移的表上没有外键约束；对于有外键约束的表，需要将关联表同时导出；存在外键约束的情况下，如果不将父表一起进行迁移，则在导入子表时将报错，子表无法导入到目标库中。迁移工具在导出时不会检测外键约束，需要操作者先自行检查。
- 如果压迫迁移金庸的索引，需要操作者先取消其禁用状态。
- 该迁移工具仅支持astore存储引擎下无压缩的行存表，不支持全局分区索引，不支持interval分区，不支持物化视图。
- 该迁移工具不迁移表空间，使用目标数据库的默认表空间。

**示例**

1、创建数据库实例一和实例二。在数据库实例一中，创建源数据库和表。

```
vb_initdb -D /data/vb --nodename=cluster//创建实例二

create database test_db_1087533;
\c test_db_1087533
create table test_table_1087533(id int, name varchar);
```

2、为数据库表中插入数据。

```
insert into test_table_1087533 values(1, 'aaa'), (2, 'bbb');
```

3、在数据库实例二中，创建一个导入的数据库。

```
create database test_restore_db_1087533;
```

4、创建导入导出的文件夹。

```
mkdir -p /tmp/trans_test
```

5、使用系统管理员导出表。

```
vb_ttdump -U vbadmin -W Vbase@admin -D /tmp/trans_test/1087533_bak -F p -f /tmp/trans_test/1087533_bak/1087533.dmp -t test_table_1087533 -d test_db_1087533 -p $PGPORT;
```

6、修改导出文件的权限。

```
chmod -R 777 /tmp/trans_test/1087533_bak
```

7、使用系统管理员导入该表。

```
vb_ttrestore -U vbadmin -W Vbase@admin -D/tmp/trans_test/1087533_bak -f /tmp/trans_test/1087533_bak/1087533.dmp -t test_table_1087533 -d test_restore_db_1087533 -p $PGPORT;
```

8、查看导入结果。

```
\c test_restore_db_1087533
select * from test_table_1087533;
```

返回结果为：

```
id |  name 
----+--------
  1 |  aaa
  2 |  bbb
(2 rows)
```

5、插入数据。

```
insert into test_table_1087533 values(3,'ccc');
```

6、查看表插入结果。

```
select * from test_table_1087533;
```

返回结果为：

```
id |  name 
----+--------
  1 |  aaa
  2 |  bbb
  3 |  ccc
(3 rows)
```