# SQL Rewriter: SQL语句改写

## 功能描述

SQL Rewriter是一个SQL改写工具，根据预先设定的规则，将查询语句转换为更为高效或更为规范的形式，使得查询效率得以提升。

>
> <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
>
> - 本功能不适用包含子查询的语句。
> - 本功能只支持SELECT语句和DELETE对整个表格删除的语句。
> - 本功能包含11个改写规则，对不符合改写规则的语句，不会进行处理。
> - 本功能会对原始查询语句和改写后语句进行屏幕输出，不建议对包含涉敏感信息的SQL语句进行改写。
> - union转union all规则避免了去重，从而提升了查询性能，所得结果有可能存在冗余。
> - 语句中如包含order by+ 指定列名或group by+ 指定列名，无法适用SelfJoin规则。
> - SQL改写工具不保证查询语句等价转换，其目的是提升查询语句效率。

## 语法格式

使用SQL Rewriter前，您可以通过以下指令获取帮助：

```
gs_dbmind component sql_rewriter --help
```

显示如下帮助信息：

```sql
usage:  [-h] [--db-host DB_HOST] [--db-user DB_USER] [--schema SCHEMA]
        db_port database file

SQL Rewriter

positional arguments:
  db_port            Port for database
  database           Name for database
  file               File containing SQL statements which need to rewrite

optional arguments:
  -h, --help         show this help message and exit
  --db-host DB_HOST  Host for database
  --db-user DB_USER  Username for database log-in
  --schema SCHEMA    Schema name for the current business data
```

## 参数说明
<table>
    <tr>
        <th>参数</th>
        <th>参数说明</th>
    </tr>
    <tr>
        <td>db_port</td>
        <td>数据库端口号。</td>
    </tr>
    <tr>
        <td>database</td>
        <td>数据库名称。</td>
    </tr>
    <tr>
        <td>file</td>
        <td>包含多个查询语句的文件路径。</td>
    </tr>
    <tr>
        <td>--db-host</td>
        <td>（可选）数据库主机号。</td>
    </tr>
    <tr>
        <td>--db-user</td>
        <td>（可选）数据库用户名。</td>
    </tr>
    <tr>
        <td>--schema</td>
        <td>（可选，模式为public）模式。</td>
    </tr>
</table>

## 示例

1、登录vastbase数据库。

```shell
vsql -r -d vastbase
```

2、创建测试数据。

```sql
CREATE TABLE test1(id int,name varchar);
INSERT INTO test1(id,name) VALUES(1,'zhangsan');
INSERT INTO test1(id,name) VALUES(2,'lisi');
INSERT INTO test1(id,name) VALUES(3,'zhangsan');
INSERT INTO test1(id,name) VALUES(4,'wangwu');
INSERT INTO test1(id,name) VALUES(5,'xiaoming');
INSERT INTO test1(id,name) VALUES(6,'xiaohong');
INSERT INTO test1(id,name) VALUES(7,'LISI');
```

3、退出数据库。

```sql
\q
```

4、创建需要改写的SQL文件queries.sql，写入以下内容。

```shell
SELECT id FROM test1 GROUP BY ID HAVING id=1;
delete from test1; 
delete from test1 where id='1';
```

5、执行如下命令使用SQL Rewriter工具改写SQL。

```shell
gs_dbmind component sql_rewriter 5732 vastbase queries.sql --db-host 127.0.0.1 --db-user vastbase_ai --schema public
```

返回结果为改写后的查询语句，无法改写的语句，显示为空：

<div align="left"><img src="image/AI-19.png" style="zoom:100%"></div>



