#### SELECT INTO OUTFILE语法

**功能描述**

select into outfile将查询到的数据导出到文件。

**语法格式**

```
SELECT ... INTO OUTFILE 'file_name'
[CHARACTER SET charset_name]
[export_options]
```

export_options:

```
[{FIELDS | COLUMNS}
[TERMINATED BY 'string']
[[OPTIONALLY] ENCLOSED BY 'char']
[ESCAPED BY 'char']
]
[LINES
[STARTING BY 'string']
[TERMINATED BY 'string']]
```

**参数说明**

- file_name：自定义文件名

- FILEDS子句：在FIELDS子句中有三个亚子句：TERMINATED BY、 [OPTIONALLY] ENCLOSED BY和ESCAPED BY。如果指定了FIELDS子句，则这三个亚子句中至少要指定一个。
  - TERMINATED BY：用来指定字段值之间的符号。例如："TERMINATED BY ',' "指定了逗号作为两个字段值之间的标志。
  - ENCLOSED BY：用来指定包裹文件中字符值的符号。例如："ENCLOSED BY ' " ' "表示文件中字符值放在双引号之间，若加上关键字OPTIONALLY表示所有的值都放在双引号之间。
  - ESCAPED BY：子句用来指定转义字符。例如："ESCAPED BY '\*' "将"\*"指定为转义字符，取代"\\"，如空格将表示为"*N"。
- LINES子句：在LINES子句中使用TERMINATED BY指定一行结束的标志，如"LINES TERMINATED BY '?' "表示一行以"?"作为结束标志。
- charset_name：字符集名称。
- GUC参数：secure_file_priv该参数为PG_SIGHUP级别，修改后仅在数据库服务reload后的会话中生效
  - secure_file_priv参数指定的导出目录必须是绝对路径且不能为已有文件。
  - secure_file_priv参数设置为空，则变量无效。
  - secure_file_priv参数设置为NULL（默认值），则禁用select into file导出操作。

**注意事项**

- select into outfile依赖SELECT语句IntoClause的相关功能。

- select into outfile依赖COPY到文件时的用户权限要求以及输出文件名要求。

- select into outfile依赖COPY到文件时可选参数功能。
- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 用户必须具有导出文件的写入权限。
- 如果使用了FIELDS子句，则最少要指定分隔符、包裹字符或转义字符中的一项；如果使用了LINES子句则最少要指定行起始字符或结束符中的一项。

**示例**

1、修改参数secure_file_priv为空。

```
alter system set  secure_file_priv='';
select pg_reload_conf();
```

2、创建兼容MySQL的库db_mysql，并进入。

```
create  database  db_mysql  dbcompatibility  'B';
\c  db_mysql
```

3、创建测试表并写入数据。

```
create table test(id1 int, id2 char(10), id3 text);
insert into test values(1,'a','zhangsan');
insert into test values(2,'b','lisi');
insert into test values(3,'c','wangwu'); 
```

 4、用户有文件权限，直接导出并查看。

```
select * into outfile '/home/vastbase/test.txt' fields terminated by ',' optionally enclosed by '"' lines starting by '<<' terminated by '\r\n' from test;
```

查看文件。

```
cat /home/test.txt
<<1,"a     ","zhangsan"
<<2,"b     ","lisi"
<<3,"c     ","wangwu"
```

