#### DBLINK

**功能描述**

当用户需要跨越本地数据库，访问远程数据库的数据时，可以通过DBLINK像访问本地数据库一样访问远程数据库表中的数据。

**语法格式**

- 打开一个到远程数据库的持久连接。

```
SELECT dblink_connect(dblink_name,connstr);
```

- 关闭一个到远程数据库的持久连接。

```
SELECT dblink_disconnect();
```

- 在远程数据库执行查询。

```
SELECT * FROM dblink(dblink_name, sql_statement);
```

- 在远程数据库执行命令。

```
SELECT dblink_exec(dblink_name, sql_statement);
```

- 返回所有打开的命名dblink连接的名称。

```
SELECT dblink_get_connections();
```

- 发送一个异步查询到远程数据库。

```
SELECT dblink_send_query(dblink_name, sql_statement);
```

- 检查连接是否正在忙于一个异步查询。

```
SELECT dblink_is_busy(dblink_name);
```

- 创建private dblink或public dblink。

```
CREATE [public] DATABASE LINK ${dblink_name} CONNECT TO ${username} IDENTIFIED BY '${password}' USING ${fdw_name} (
url 'jdbc:oracle:thin:@//${ip}:${port}/${dbname}',
jarfile '${driver_path}'
);
```

- 远程Oracle函数和存储过程。

```
${schema_name}.${function_name | procedure_name}@${dblink_name}[(parameter,[...])]
```

**参数说明**

- dblink_name：连接名称，可自定义。
- connstr：连接信息，例如：hostaddr=172.16.103.92 port=6036 dbname=vastbase user=vastbase password='Bigdata@123'
- sql_statement：执行的语句。

- ${schema_name}：目标函数或存储过程所在的模式名（或者package名称）。可不指定，不指定时默认访问database link中Oracle用户对应的模式。

- ${function_name}：函数名称。

- ${procedure_name}：存储过程名称。

- parameter：函数或存储过程中的参数，只考虑传入参数，不考虑out输出参数。

**注意事项**

- 不同的用户可以创建同名的private dblink，且用户只能访问自己创建的DBLINK。

- 同一用户可以创建同名的private dblink和，在访问时，优先访问private dblink。
- 为支持不同用户创建同名dblink，需禁用GRANT FOREIGN SERVER，不再允许将server权限给予其他用户，以防一个用户能访问多个同名server，造成混乱。
- 不支持访问远程Oracle包变量和常量。（注：数据也不支持）

- 不支持将远程函数和存储过程创建为同义词方式使用。

- 创建private dblink或public dblink时，必须提前创建jdbc_fdw，并具备DBLINK相关权限。

**示例**

- 示例1：常用函数操作

1、创建扩展。

```
create extension dblink;
```

2、先执行dblink_connect保持连接。

```
SELECT dblink_connect('mycoon','hostaddr=172.16.103.92 port=6036 dbname=vastbase user=lst password=Bigdata@123');
```

3、执行BEGIN命令。

```
SELECT dblink_exec('mycoon', 'BEGIN');
```

4、执行数据操作。

```
SELECT dblink_exec('mycoon', 'create table people(id int,info varchar(10))');
SELECT dblink_exec('mycoon', 'insert into people values(1,''foo'')');
SELECT dblink_exec('mycoon', 'insert into people values(2,''foo'')');
SELECT dblink_exec('mycoon', 'update people set info=''bar'' where id=1');
```

5、执行事务提交。

```
SELECT dblink_exec('mycoon', 'COMMIT');
```

6、执行查询。

```
select * from dblink('mycoon','select * from people') as testTable (id int,info varchar(10));
```

7、解除连接。

```
SELECT dblink_disconnect('mycoon')
```

- private dblink和public dblink

1、修改postgresql.conf参数，将jdbc_fdw配置到shared_preload_libraries参数中。

```
shared_preload_libraries = 'jdbc_fdw'
```

2、创建插件。

```
create extension jdbc_fdw;
```

3、创建dblink。

（1）创建私有private dblink。

```
create  database  link  oradb1  connect  to  SYSTEM  identified  by  'root'  
using  jdbc_fdw(
url  'jdbc:oracle:thin:@//172.16.103.120:1521/utf8',
jarfile  '/home/vastbase2/ojdbc7.jar'
);
```

（2）创建公共public dblink。

```
vastbase=# create  public  database  link  oradb2  connect  to  SYSTEM  identified  by  'root'  
using  jdbc_fdw(
url  'jdbc:oracle:thin:@//172.16.103.104:1521/orcl',
jarfile  '/home/vastbase2/ojdbc7.jar'
);
```

4、dblink使用查询。

```
select  *  from  fdw_t1@oradb1;
select  *  from  fdw_t1@oradb2;
```

5、ALTER SERVER。

```
ALTER  SERVER  oradb1  VERSION  '8.4';
ALTER  PUBLIC  SERVER  oradb2  VERSION  '8.5';
```

6、删除DBLINK。

```
DROP  DATABASE  LINK  oradb1;
DROP  PUBLIC  DATABASE  LINK  oradb2;
```

- Oracle函数和存储过程

调用函数。

```
select * from test_func@test_dblink(100);
```

调用存储过程。

```
call test_proc@test_dblink(100);
```

