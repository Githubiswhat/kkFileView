# vb_dump

## 背景信息

vb_dump是Vastbase用于导出数据库相关信息的工具，用户可以自定义导出一个数据库或其中的对象（模式、表、视图等），回收站对象除外。支持导出的数据库可以是默认数据库postgres，也可以是自定义数据库。

vb_dump工具由安装Vastbase数据库的操作系统用户执行。

vb_dump工具在进行数据导出时，其他用户可以访问Vastbase数据库（读或写）。

vb_dump工具支持导出完整一致的数据。例如，T1时刻启动vb_dump导出A数据库，那么导出数据结果将会是T1时刻A数据库的数据状态，T1时刻之后对A数据库的修改不会被导出。

vb_dump工具在进行数据导出时生成的列不会被转储。

vb_dump支持导出兼容v1版本数据库的文本格式文件。

vb_dump支持将数据库信息导出至纯文本格式的SQL脚本文件或其他归档文件中。

-   纯文本格式的SQL脚本文件：包含将数据库恢复为其保存时的状态所需的SQL语句。通过vsql运行该SQL脚本文件，可以恢复数据库。即使在其他主机和其他数据库产品上，只要对SQL脚本文件稍作修改，也可以用来重建数据库。
-   归档格式文件：包含将数据库恢复为其保存时的状态所需的数据，可以是tar格式、目录归档格式或自定义归档格式，详见[导出文件格式](#导出文件格式)。该导出结果必须与vb_restore配合使用来恢复数据库，vb_restore工具在导入时，系统允许用户选择需要导入的内容，甚至可以在导入之前对等待导入的内容进行排序。

## 导出格式

vb_dump可以创建四种不同的导出文件格式，通过[-F或者\--format=]选项指定，具体如导出文件格式所示。

**表1** 导出文件格式<a id=导出文件格式></a>

<table>
	<tr>
		<th>格式名称</th>
		<th>-F的参数值</th>
		<th>说明</th>
		<th>建议</th>
		<th>对应导入工具</th>
	</tr>
	<tr>
		<td>纯文本格式</td>
		<td>p</td>
		<td>纯文本脚本文件包含SQL语句和命令。命令可以由vsql命令行终端程序执行，用于重新创建数据库对象并加载表数据。</td>
		<td>小型数据库，一般推荐纯文本格式。</td>
		<td>使用vsql工具恢复数据库对象前，可根据需要使用文本编辑器编辑纯文本导出文件</td>
	</tr>
	<tr>
		<td>自定义归档格式</td>
		<td>c</td>
		<td>一种二进制文件。支持从导出文件中恢复所有或所选数据库对象。</td>
		<td>中型或大型数据库，推荐自定义归档格式。</td>
		<td rowspan="3">使用vb_restore可以选择要从自定义归档/目录归档/tar归档导出文件中导入相应的数据库对象。</td>
	</tr>
	<tr>
		<td>目录归档格式</td>
		<td>d</td>
		<td>该格式会创建一个目录，该目录包含两类文件，一类是目录文件，另一类是每个表和blob对象对应的数据文件。</td>
		<td>-</td>
	</tr>
	<tr>
		<td>tar归档格式</td>
		<td>t</td>
		<td>tar归档文件支持从导出文件中恢复所有或所选数据库对象。tar归档格式不支持压缩且对于单独表大小应小于8GB。</td>
		<td>-</td>
	</tr>
</table>

> <div align="left"><img src="image/img1.png" style="zoom:75%")</div> 
>
>可以使用vb_dump程序将文件压缩为目录归档或自定义归档导出文件，减少导出文件的大小。生成目录归档或自定义归档导出文件时，默认进行中等级别的压缩。vb_dump程序无法压缩已归档导出文件。



## 语法格式

```shell
vb_dump [OPTION]... [DBNAME]
```

通用参数包括：

```shell
General options:
  -f, --file=FILENAME                         output file or directory name
  -F, --format=c|d|t|p                        output file format (custom, directory, tar,
                                              plain text (default))
  -j, --jobs=NUM                              use this many parallel jobs to dump
  -v, --verbose                               verbose mode
  -V, --version                               output version information, then exit
  -Z, --compress=0-9                          compression level for compressed formats
  --lock-wait-timeout=TIMEOUT                 fail after waiting TIMEOUT for a table lock
  -?, --help                                  show this help, then exit
```
控制输出内容的参数包括：

```shell
  -a, --data-only                             dump only the data, not the schema
  -b, --blobs                                 include large objects in dump
  -c, --clean                                 clean (drop) database objects before recreating
  -C, --create                                include commands to create database in dump
  -E, --encoding=ENCODING                     dump the data in encoding ENCODING
  -g, --exclude-guc=GUC_PARAM                 do NOT dump the GUC_PARAM set
  -n, --schema=SCHEMA                         dump the named schema(s) only
  -N, --exclude-schema=SCHEMA                 do NOT dump the named schema(s)
  -o, --oids                                  include OIDs in dump
  -O, --no-owner                              skip restoration of object ownership in
                                              plain-text format
  -s, --schema-only                           dump only the schema, no data
  -q, --target=VERSION                        dump data format can compatible Gaussdb version (v1 or ..)
  -S, --sysadmin=NAME                         system admin user name to use in plain-text format
  -t, --table=TABLE                           dump the named table(s) only
  -T, --exclude-table=TABLE                   do NOT dump the named table(s)
  --include-table-file=FileName               dump the named table(s) only
  --exclude-table-file=FileName               do NOT dump the named table(s)
  --pipeline                                  use pipeline to pass the password,
                                              forbidden to use in terminal
  -x, --no-privileges/--no-acl                do not dump privileges (grant/revoke)
  --column-inserts/--attribute-inserts        dump data as INSERT commands with column names
  --disable-dollar-quoting                    disable dollar quoting, use SQL standard quoting
  --disable-triggers                          disable triggers during data-only restore
  --exclude-table-data=TABLE                  do NOT dump data for the named table(s)
  --exclude-with                              do NOT dump WITH() of table(s)
  --inserts                                   dump data as INSERT commands, rather than COPY
  --no-publications                           do not dump publications
  --no-security-labels                        do not dump security label assignments
  --no-synchronized-snapshots                 do not use synchronized snapshots in parallel jobs
  --no-subscriptions                          do not dump subscriptions
  --no-tablespaces                            do not dump tablespace assignments
  --no-unlogged-table-data                    do not dump unlogged table data
  --include-alter-table                       dump the table delete column
  --quote-all-identifiers                     quote all identifiers, even if not key words
  --section=SECTION                           dump named section (pre-data, data, or post-data)
  --serializable-deferrable                   wait until the dump can run without anomalies
  --dont-overwrite-file                       do not overwrite the existing file in case of plain, tar and custom format
  --use-set-session-authorization
                                              use SET SESSION AUTHORIZATION commands instead of
                                              ALTER OWNER commands to set ownership
  --exclude-function                          do not dump function and procedure
  --with-encryption=AES128/SWS-KMS-SM4/TAS-KMS-SM4
                                              dump data is encrypted using AES128 or SWS-KMS-SM4 or TAS-KMS-SM4
  --with-key=KEY                              encryption key, AES128 key must be 16 bytes in length, SWS-KMS-SM4 key must be
                                              greater than 0 in length, the key means index of encryption key if use TAS-KMS-SM4
  --with-salt=RANDVALUES                      used by gs_dumpall, pass rand value array
  --include-extensions                        include extensions in dump
  --binary-upgrade                            for use by upgrade utilities only
  --binary-upgrade-usermap="USER1=USER2"      to be used only by upgrade utility for mapping usernames
  --non-lock-table                            for use by OM tools utilities only
  --include-depend-objs                       dump the object which depends on the input object
  --exclude-self                              do not dump the input object
  --table-conditions="[tablename; whereclause ]limitclause"                                              add table conditions for exporting data
```

连接参数包括：

```shell
Connection options:
  -h, --host=HOSTNAME                         database server host or socket directory
  -p, --port=PORT                             database server port number
  -U, --username=NAME                         connect as specified database user
  -w, --no-password                           never prompt for password
  -W, --password=PASSWORD                     the password of specified database user
  --role=ROLENAME                             do SET ROLE before dump
  --rolepassword=ROLEPASSWORD                 the password for role
```

## 参数说明
**DBNANE**

指定要连接的数据库。“DBNAME”前面不需要加短或长选项。

- 方式一：不需要-d，直接指定“DBNAME”。

  ```shell
  vb_dump -p port_number vastbase -f dump1.sql
  ```

- 方式二：

  ```shell
  export PGDATABASE=vastbase 
  vb_dump -p port_number -f dump1.sql
  ```
     环境变量： PGDATABASE

### 通用参数

- **-f, \--file=FILENAME**

  将输出发送至指定文件或目录。如果省略该参数，则使用标准输出。如果输出格式为(-F c/-F d/-F t)时，必须指定-f参数。如果-f的参数值含有目录，要求当前用户对该目录具有读写权限，并且不能指定已有目录。

- **-F, \--format=c|d|t|p**

  选择输出格式。格式如下：

  - p|plain：输出一个文本SQL脚本文件（默认）。

  - c|custom：输出一个自定义格式的归档，并且以目录形式输出，作为gs_restore输入信息。该格式是最灵活的输出格式，因为能手动选择，而且能在恢复过程中将归档项重新排序。该格式默认状态下会被压缩。

  - d|directory：该格式会创建一个目录，该目录包含两类文件，一类是目录文件，另一类是每个表和blob对象对应的数据文件。
  
  - t|tar：输出一个tar格式的归档形式，作为gs_restore输入信息。tar格式与目录格式兼容；tar格式归档形式在提取过程中会生成一个有效的目录格式归档形式。但是，tar格式不支持压缩且对于单独表有8GB的大小限制。此外，表数据项的相应排序在恢复过程中不能更改。


- **-v, \--verbose**

    指定verbose模式。该选项将导致vb_dump向转储文件输出详细的对象注解和启动/停止次数，向标准错误流输出处理信息。

- **-V, \--version**

    打印vb_dump版本，然后退出。

- **-Z, \--compress=0-9**

  指定使用的压缩比级别。

  取值范围：0-9
  - 0表示无压缩。
  - 1表示压缩比最小，处理速度最快。
  - 9表示压缩比最大，处理速度最慢。

   针对自定义归档格式，该选项指定单个表数据片段的压缩，默认方式是以中等级别进行压缩。tar归档格式和纯文本格式目前不支持压缩。

- **\--lock-wait-timeout=TIMEOUT**

    请勿在转储刚开始时一直等待以获取共享表锁。如果无法在指定时间内锁定某个表，就选择失败。可以以任何符合SET statement_timeout的格式指定超时时间。

- **-?, \--help**

    显示vb_dump命令行参数帮助，然后退出。

### 转储参数

- **-a, \--data-only**

  只输出数据，不输出模式(数据定义)。转储表数据、大对象和序列值。

- **-b, \--blobs**

  该参数为扩展预留接口，不建议使用。

- **-c, \--clean**

  在将创建数据库对象的指令输出到备份文件之前，先将清理（删除）数据库对象的指令输出到备份文件中。（如果目标数据库中没有任何对象，gs_restore工具可能会输出一些提示性的错误信息）该选项只对文本格式有意义。针对归档格式，可以调用gs_restore时指定选项。

- **-C, \--create**

  备份文件以创建数据库和连接到创建的数据库的命令开始。（如果命令脚本是这种方式执行，可以先指定任意数据库用于执行创建数据库的命令，数据不会恢复到指定的数据库中，而是恢复到创建的数据库中。）该选项只对文本格式有意义。针对归档格式，可以在调用gs_restore时指定选项。

- **-E, \--encoding=ENCODING**

  以指定的字符集编码创建转储。默认情况下，以数据库编码创建转储。（得到相同结果的另一个办法是将环境变量“PGCLIENTENCODING”设置为所需的转储编码。）

- **-n, \--schema=SCHEMA**

  只转储与模式名称匹配的模式，此选项包括模式本身和所有它包含的对象。如果该选项没有指定，所有在目标数据库中的非系统模式将会被转储。写入多个-n选项来选择多个模式。此外，根据vsql的d命令所使用的相同规则，模式参数可被理解成一个pattern，所以多个模式也可以通过在该pattern中写入通配符来选择。使用通配符时，注意给pattern打引号，防止shell扩展通配符。

><div align="left"><img src="image/img1.png" style="zoom:75%"></div>
>
>-   当-n已指定时，vb_dump不会转储已选模式所附着的任何其他数据库对象。因此，无法保证某个指定模式的转储结果能够自行成功地储存到一个空数据库中。
>-   当-n指定时，非模式对象不会被转储。

  转储支持多个模式的转储。多次输入-n schemaname转储多个模式。

  例如：

```shell
vb_dump -h host_name -p port_number postgres -f backup/bkp_shl2.sql -n sch1 -n sch2
```

  在上面这个例子中，sch1和sch2会被转储。

- **-N, \--exclude-schema=SCHEMA**

  不转储任何与模式pattern匹配的模式。pattern将参照针对-n的相同规则来理解。可以通过输入多次-N，不转储与任何pattern匹配的模式。当同时输入-n和-N时，会转储与至少一个-n选项匹配、与-N选项不匹配的模式。如果有-N没有-n，则不转储常规转储中与-N匹配的模式。转储过程支持排除多个模式，输入-N exclude schema name排除多个模式。

  例如：
  
  ```shell
  vb_dump -h host_name -p port_number postgres -f backup/bkp_shl2.sql -N sch1 -N sch2
  ```


​       在上面这个例子中，sch1和sch2在转储过程中会被排除。

- **-o, \--oids**

  转储每个表的对象标识符（OIDs），作为表的一部分数据。该选项用于应用以某种方式参照了OID列的情况。如果不是以上这种情况，请勿使用该选项。

- **-O, \--no-owner**

  不输出设置对象的归属这样的命令，以匹配原始数据库。默认情况下，vb_dump会发出ALTER OWNER或SET SESSION AUTHORIZATION语句设置所创建的数据库对象的归属。如果脚本正在运行，该语句不会执行成功，除非是由系统管理员触发（或是拥有脚本中所有对象的同一个用户）。通过指定-O，编写一个任何用户都能存储的脚本，且该脚本会授予该用户拥有所有对象的权限。该选项只对文本格式有意义。针对归档格式，可以在调用vb_restore时指定选项。

- **-s, \--schema-only**<a id="schema-only"></a>

  只转储对象定义（模式），而非数据。

- **-S, \--sysadmin=NAME**

  该参数为扩展预留接口，不建议使用。

- **-t, \--table=TABLE**

  指定转储的表（或视图、或序列、或外表）对象列表，可以使用多个-t选项来选择多个表，也可以使用通配符指定多个表对象。当使用通配符指定多个表对象时，注意给pattern打引号，防止shell扩展通配符。当使用-t时，-n和-N没有任何效应，这是因为由-t选择的表的转储不受那些选项的影响。

><div align="left"><img src="image/img1.png" style="zoom:75%"></div>
>
>-   -t参数选项个数必须小于等于100。
>-   如果-t参数选项个数大于100，建议使用参数\--include-table-file来替换。
>-   当-t已指定时，vb_dump不会转储已选表所附着的任何其他数据库对象。因此，无法保证某个指定表的转储结果能够自行成功地储存到一个空数据库中。
>-   -t tablename只转储在默认搜索路径中可见的表。-t  *.tablename转储数据库下所有模式下的tablename表。-t schema.table转储特定模式中的表。
>-   -t tablename不会导出表上的触发器信息。
>-   对于表名中包含大写字母的表，在使用-t参数指定导出时需对表名添加"来导出。如对于表"abC"，导出需指定-t "abC"；如对于表schema."abC"，导出需指定-t schema."abC"。  

  例如：

```shell
vb_dump -h host_name -p port_number postgres -f backup/bkp_shl2.sql -t schema1.table1 -t schema2.table2
```


  在上面这个例子中，schema1.table1和schema2.table2会被转储。

- **\--include-table-file=FILENAME**

  指定需要dump的表文件。

- **-T, \--exclude-table=TABLE**

  不转储的表（或视图、或序列、或外表）对象列表，可以使用多个-T选项来选择多个表，也可以使用通配符指定多个表对象。当同时输入-t和-T时，会转储在-t列表中，而不在-T列表中的表对象。

  例如：

  ```shell
  vb_dump -h host_name -p port_number postgres -f backup/bkp_shl2.sql -T table1 -T table2
  ```
   在上面这个例子中，table1和table2在转储过程中会被排除。

- **\--exclude-table-file=FILENAME**

  指定不需要dump的表文件。
  
  > <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
  >
  >同\--include-table-file，其内容格式如下：
  >schema1.table1
  >schema2.table2
  >......

- **-x, \--no-privileges|\--no-acl**

  防止转储访问权限（授权/撤销命令）。

- **-q, \--target**

  指定导出兼容其他版本数据库的文本文件，目前支持v1和v5参数。v1参数用于导出v5数据库的数据为兼容v1的文本文件。v5参数用于导出v5数据库的数据为v5格式的文本文件，减少了导入v5时的可能的报错情况。在使用v1参数时，建议和\--exclude-guc="enable_cluster_resize"，\--exclude-function，\--exclude-with等选项共用，否则导入到v1时可能报错。

- **-g, \--exclude-guc**

  该参数为扩展预留接口，不建议使用。

- **\--exclude-function**

  不导出函数和存储过程。

- **\--exclude-with**

  导出的表定义，末尾不添加WITH(orientation=row，compression=on）这样的描述。

- **\--binary-upgrade**

  该参数为扩展预留接口，不建议使用。

- **\--binary-upgrade-usermap="USER1=USER2"**

  该参数为扩展预留接口，不建议使用。

- **\--column-inserts|\--attribute-inserts**

  以INSERT命令带列名（INSERT INTO表（列、…）值…）方式导出数据。这会导致恢复缓慢。但是由于该选项会针对每行生成一个独立分开的命令，所以在重新加载某行时出现的错误只会导致那行丢失，而非整个表内容。

- **\--disable-dollar-quoting**

  该选项将禁止在函数体前使用美元符号$，并强制使用SQL标准字符串语法对其进行引用。

- **\--disable-triggers**

  该参数为扩展预留接口，不建议使用。

- **\--exclude-table-data=TABLE**

  指定不转储任何匹配表pattern的表这方面的数据。依照针对-t的相同规则理解该pattern。可多次输入\--exclude-table-data来排除匹配任何pattern的表。当用户需要特定表的定义但不需要其中的数据时，这个选项很有帮助。排除数据库中所有表的数据，参见[\--schema-only](#schema-only)。

- **\--inserts**

  发出INSERT命令（而非COPY命令）转储数据。这会导致恢复缓慢。但是由于该选项会针对每行生成一个独立分开的命令，所以在重新加载某行时出现的错误只会导致那行丢失，而非整个表内容。注意：如果重排列顺序，可能会导致整个恢复失败。列顺序改变时，\--column-inserts选项不受影响，虽然会更慢。

- **\--no-publications**

  不转储发布。

- **\--no-security-labels**

  该参数为扩展预留接口，不建议使用。

- **\--no-subscriptions**

  不转储订阅。

- **\--no-tablespaces**

  不输出选择表空间的命令。使用该选项，无论默认表空间是哪个，在恢复过程中所有对象都会被创建。该选项只对文本格式有意义。针对归档格式，可以在调用gs_restore时指定选项。

- **\--no-unlogged-table-data**

  该参数为扩展预留接口，不建议使用。

- **\--non-lock-table**

  该参数为扩展预留接口，不建议使用。

- **\--include-alter-table**

  转储表删除列。该选项会记录列的删除。

- **\--quote-all-identifiers**

  强制对所有标识符加引号。为了向后续版本迁移，且其中可能涉及引入额外关键词，在转储相应数据库时该选项会有帮助。

- **\--section=SECTION**

  指定已转储的名称区段（pre-data、data和post-data）。

- **\--serializable-deferrable**

  转储过程中使用可串行化事务，以确保所使用的快照与之后的数据库状态一致；要实现该操作需要在无异常状况的事务流中等待某个点，因为这样才能保证转储成功，避免引起其他事务出现serialization_failure要重新再做。但是该选项对于灾难恢复没有益处。对于在原始数据库进行升级的时候，加载一个数据库的拷贝作为报告或其他只读加载共享的转储是有帮助的。没有这个选项，转储会反映一个与任何事务最终提交的序列化执行不一致的状态。如果当vb_dump启动时，读写事务仍处于非活动状态，即便使用该选项也不会对其产生影响。如果读写事务处于活动状态，转储的开始时间可能会延迟一段不确定的时间。

- **\--use-set-session-authorization**

  输出符合SQL标准的SET SESSION AUTHORIZATION命令而不是ALTER OWNER命令来确定对象所有权。这样令转储更加符合标准，但是如果转储文件中的对象的历史有些问题，那么可能不能正确恢复。并且，使用SET SESSION AUTHORIZATION的转储需要数据库系统管理员的权限才能转储成功，而ALTER OWNER需要的权限则低得多。

- **\--with-encryption=AES128**

  指定转储数据需用AES128进行加密。

- **\--with-key=KEY**

  AES128密钥规则如下：

  -   密钥长度为8~16个字符。
  -   至少包含大写字母（A-Z）、小写字母（a-z）、数字（0-9）、非字母数字字符（限定为~!@#$%^&*()-_=+|[{}];:,<.>/?）四类字符中的三类字符。

><div align="left"><img src="image/img1.png" style="zoom:75%"></div>
>
>-   使用vb_dump工具进行加密导出时，仅支持plain格式导出。通过-F plain导出的数据，需要通过gsql工具进行导入，且如果以加密方式导入，在通过gsql导入时，需要指定\--with-key参数。
>-   不支持加密导出存储过程和函数。

- **\--with-salt=RANDVALUES**

  vb_dumpall使用此参数传递随机值。

- **\--include-extensions**

    在转储中包含扩展。

- **\--include-depend-objs**

    备份结果包含依赖于指定对象的对象信息。该参数需要同-t/\--include-table-file参数关联使用才会生效。

- **\--exclude-self：**

    备份结果不包含指定对象自身的信息。该参数需要同-t/\--include-table-file参数关联使用才会生效。

- **\--pipeline**

  使用管道传输密码，禁止在终端使用。

- **\--dont-overwrite-file**

    文本、tar以及自定义格式情况下会重写现有文件。这对目录格式不适用。

  例如：

  设想这样一种情景，即当前目录下backup.sql已存在。如果在输入命令中输入-f backup.sql选项时，当前目录恰好也生成backup.sql，文件就会被重写。
  
    如果备份文件已存在，且输入\--dont-overwrite-file选项，则会报告附带‘转储文件已经存在’信息的错误。
  
  ```shell
  vb_dump -p port_number postgres -f backup.sql -F plain --dont-overwrite-file
  ```
  
-  **--table-conditions="[tablename; whereclause ]limitclause"**  

    导出格式为纯文本格式时，仅导出部分数据：

  - 导出整个指定库/模式时，使用limit子句限制每张表的导出行数。`limit n`意为导出的每张表仅包含n行copy（insert）数据。
  
  - 单表/多表导出时，使用where子句/limit子句限制表的导出条件或导出行数。参见[示例7](#示例7)。
  
    - whereclause用于指定导出条件，仅导出符合条件的结果集数据。
  
    - limitclause用于限制导出行数，`limit n`意为导出（符合条件的）前n行。
        ><div align="left"><img src="image/img1.png" style="zoom:75%"></div>
        >
        >- 指定tablename时表示单表数据过滤条件，不指定tablename时表示多表过滤条件。
        >- 支持使用多个--table-conditions分别指定每张表的导出条件，
        >- 当指定了单表limit时，不支持再指定多表的limit条件。
        >- 支持的单表条件的筛选关键词包括：where、order、limit、offset、fetch。
        >- 支持的多表条件的筛选关键词包括：limit、offset、fetch。
        >- 当指定了`-o`选项后，对于有OIDS的表，将会忽略\--table-conditions中的条件并导出全部表数据。


><div align="left"><img src="image/img1.png" style="zoom:75%"></div>
>
>- -s \--schema-only和-a \--data-only不能同时使用。
>- -c \--clean和-a \--data-only不能同时使用。
>- \--inserts \--column-inserts和-o \--oids不能同时使用，因为INSERT命令不能设置OIDS。
>- \--role和\--rolepassword必须一起使用。
>- \--binary-upgrade-usermap和\--binary-upgrade必须一起使用。
>- \--include-depend-objs \--exclude-self需要同-t \--include-table-file参数关联使用才会生效。
>- \--exclude-self必须同\--include-depend-objs一起使用。
>

### 连接参数

- -**h, \--host=HOSTNAME**

  指定主机名称。如果数值以斜杠开头，则被用作到Unix域套接字的路径。缺省从PGHOST环境变量中获取（如果已设置），否则，尝试一个Unix域套接字连接。该参数只针对Vastbase外，对Vastbase内本机只能用127.0.0.1。

  例如：主机名

  环境变量：PGHOST

- **-p, \--port=PORT**

  指定主机端口。在开启线程池情况下，建议使用 pooler port，即主机端口+1。

  环境变量：PGPORT

- **-U, \--username=NAME**

  指定所连接主机的用户名。不指定连接主机的用户名时，用户默认系统管理员。

  环境变量：PGUSER

- **-w, \--no-password**

  不出现输入密码提示。如果主机要求密码认证并且密码没有通过其它形式给出，则连接尝试将会失败。 该选项在批量工作和不存在用户输入密码的脚本中很有帮助。

- **-W, \--password=PASSWORD**

  指定用户连接的密码。如果主机的认证策略是trust，则不会对系统管理员进行密码验证，即无需输入-W选项；如果没有-W选项，并且不是系统管理员，“Dump Restore工具”会提示用户输入密码。

- **\--role=ROLENAME**

  指定创建转储使用的角色名。选择该选项，会使vb_dump连接数据库后，发起一个SET ROLE角色名命令。当所授权用户（由-U指定）没有vb_dump要求的权限时，该选项会起到作用，即切换到具备相应权限的角色。某些安装操作规定不允许直接以超系统管理员身份登录，而使用该选项能够在不违反该规定的情况下完成转储。

- **\--rolepassword=ROLEPASSWORD**

  指定角色名的密码。


## 注意事项

- 如果Vastbase有任何本地数据要添加到template1数据库，请谨慎将vb_dump的输出恢复到一个真正的空数据库中，否则可能会因为被添加对象的定义被复制，出现错误。要创建一个无本地添加的空数据库，需从template0而非template1复制，例如：

  ```shell
  CREATE DATABASE foo WITH TEMPLATE template0;
  ```

- 
  tar归档形式的文件大小不得超过8GB（tar文件格式的固有限制）。tar文档整体大小和任何其他输出格式没有限制，操作系统可能对此有要求。


- 由vb_dump生成的转储文件不包含优化程序用来做执行计划决定的统计数据。因此，最好从某转储文件恢复之后运行ANALYZE以确保最佳效果。转储文件不包含任何ALTER DATABASE…SET命令，这些设置由vb_dumpall转储，还有数据库用户和其他完成安装设置。
- 禁止修改-F c/d/t 格式导出的文件和内容，否则可能无法恢复成功。对于-F p 格式导出的文件，如有需要，可根据需要谨慎编辑导出文件。
- 为了保证数据一致性和完整性，vb_dump会对需要转储的表设置共享锁。如果表在别的事务中设置了共享锁，vb_dump会等待锁释放后锁定表。如果无法在指定时间内锁定某个表，转储会失败。用户可以通过指定\--lock-wait-timeout选项，自定义等待锁超时时间。
- 不支持加密导出存储过程和函数。


## 示例

> <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
> - 示例中“Bigdata@123”表示数据库用户密码；
> - “MPPDB_backup.sql”表示导出的文件；
> - “5432”表示数据库服务器端口；
> - “postgres”表示要访问的数据库名。
> - 导出操作时，请确保该目录存在并且当前的操作系统用户对其具有读写权限。  

**示例1：**执行vb_dump，导出postgres数据库全量信息，导出的MPPDB_backup.sql文件格式为纯文本格式。

```shell
vb_dump -U vastbase -W Bigdata@123 -f MPPDB_backup.sql -p 5432 postgres -F p
vb_dump[port='5432'][postgres][2022-08-03 11:45:15]: The total objects number is 440.
vb_dump[port='5432'][postgres][2022-08-03 11:45:15]: [100.00%] 440 objects have been dumped.
vb_dump[port='5432'][postgres][2022-08-03 11:45:15]: dump database postgres successfully
vb_dump[port='5432'][postgres][2022-08-03 11:45:15]: total time: 941  ms
```


使用vsql程序从纯文本导出文件中导入数据。



**示例2：**执行vb_dump，导出postgres数据库全量信息，导出的MPPDB_backup.tar文件格式为tar格式。

```shell
vb_dump -U vastbase -W Bigdata@123 -f MPPDB_backup.tar -p 5432 postgres -F t
vb_dump[port='5432'][postgres][2022-08-03 11:47:37]: The total objects number is 440.
vb_dump[port='5432'][postgres][2022-08-03 11:47:37]: [100.00%] 440 objects have been dumped.
vb_dump[port='5432'][postgres][2022-08-03 11:47:37]: dump database postgres successfully
vb_dump[port='5432'][postgres][2022-08-03 11:47:37]: total time: 896  ms
```



**示例3：**执行vb_dump，导出postgres数据库全量信息，导出的MPPDB_backup.dmp文件格式为自定义归档格式。

```shell
vb_dump -U vastbase -W Bigdata@123 -f MPPDB_backup.dmp -p 5432 postgres -F c
vb_dump[port='5432'][postgres][2022-08-03 11:48:13]: The total objects number is 440.
vb_dump[port='5432'][postgres][2022-08-03 11:48:13]: [100.00%] 440 objects have been dumped.
vb_dump[port='5432'][postgres][2022-08-03 11:48:13]: dump database postgres successfully
vb_dump[port='5432'][postgres][2022-08-03 11:48:13]: total time: 899  ms
```



**示例4：**执行vb_dump，导出postgres数据库全量信息，导出的MPPDB_backup文件格式为目录格式。

```shell
vb_dump -U vastbase -W Bigdata@123 -f MPPDB_backup -p 5432  postgres -F d
vb_dump[port='5432'][postgres][2022-08-03 11:48:51]: The total objects number is 440.
vb_dump[port='5432'][postgres][2022-08-03 11:48:51]: [100.00%] 440 objects have been dumped.
vb_dump[port='5432'][postgres][2022-08-03 11:48:51]: dump database postgres successfully
vb_dump[port='5432'][postgres][2022-08-03 11:48:51]: total time: 893  ms
```



**示例5：**执行vb_dump，导出postgres数据库信息，但不导出MPPDB_temp.sql中指定的表信息。导出的MPPDB_backup.sql文件格式为纯文本格式。

```shell
vb_dump -U vastbase -W Bigdata@123 -p 5432 postgres --exclude-table-file=MPPDB_temp.sql -f backup/MPPDB_backup.sql
vb_dump[port='5432'][postgres][2022-08-03 11:50:50]: The total objects number is 1367.
vb_dump[port='5432'][postgres][2022-08-03 11:50:50]: [100.00%] 1367 objects have been dumped.
vb_dump[port='5432'][postgres][2022-08-03 11:50:50]: dump database postgres successfully
vb_dump[port='5432'][postgres][2022-08-03 11:50:50]: total time: 37017  ms
```



**示例6：**执行vb_dump，仅导出依赖于指定表testtable的视图信息。然后创建新的testtable表，再恢复依赖其上的视图。

前置步骤：进入数据库创建测试表testtable。

```sql
\c postgres
create table testtable(id int);
```

1、备份仅依赖于testtable的视图。

```shell
vb_dump -s -p 5432 postgres -t PUBLIC.testtable --include-depend-objs --exclude-self -f backup/MPPDB_backup.sql -F p
vb_dump[port='5432'][vastbase][2022-08-03 11:58:23]: The total objects number is 432.
vb_dump[port='5432'][vastbase][2022-08-03 11:58:23]: [100.00%] 432 objects have been dumped.
vb_dump[port='5432'][vastbase][2022-08-03 11:58:23]: dump database vastbase successfully
vb_dump[port='5432'][vastbase][2022-08-03 11:58:23]: total time: 1014  ms
```

2、修改testtable名称。

```shell
vsql -p 5432 postgres -r -c "ALTER TABLE PUBLIC.testtable RENAME TO testtable_bak;"
```

3、创建新的testtable表。

```sql
CREATE TABLE PUBLIC.testtable(a int, b int, c int);
```

4、还原依赖于testtable的视图。

```shell
vsql -p 5432 postgres -r -f MPPDB_backup.sql
```



**示例7：**<a name="示例7"></a>使用`--table conditions`限定导出条件。

1、创建测试用数据库dbtest_07。

```sql
create database dbtest_06;
```

2、创建测试表table7。

```sql
create table t_backup1(id int,col text);
```

3、插入测试数据。

```sql
insert into t_backup1 values(1,'张三');
insert into t_backup1 values(2,'李四');
insert into t_backup1 values(3,'王五');
insert into t_backup1 values(4,'赵六');
```

4、使用`--table conditions`限定导出条件，导出数据。

```shell
vb_dump vastbase -t t_backup1 --table-condition="t_backup1; where id > 1 limit 1" -f backup1.sql  #命令1
vb_dump vastbase -t t_backup1 --table-condition="t_backup1; where id > 1" -f backup2.sql  #命令2
vb_dump vastbase -t t_backup1 --table-condition="t_backup1; limit 1" -f backup3.sql  #命令3
```

> <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
> 命令1：导出id大于1的行，仅导出第一条符合要求的记录。
> 命令2：导出id大于1的行，不限制记录条数。
> 命令3：仅导出第一条符合要求的记录。