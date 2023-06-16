#### JDBC_FDW

**功能描述**
支持基于JDBC驱动的FDW，可以通过不同数据源的JDBC驱动在外部表访问数据。
**前置条件**
1、安装JDK1.8，并配置好JAVA_HOME环境变量。
2、数据库环境变量LD_LIBRARY_PATH动态库路径加入JAVA_HOME配置。
通过如下命令配置环境变量：

```
x86：export LD_LIBRARY_PATH=$GAUSSHOME/jre/lib/amd64/server/:$LD_LIBRARY_PATH
ARM：export LD_LIBRARY_PATH=$GAUSSHOME/jre/lib/aarch64/server/:$LD_LIBRARY_PATH
```

3、修改数据库配置参数：在shared_preload_libraries参数配置中加上jdbc_fdw，然后重启数据库。

4、驱动包ojdbc7.jar放置到$GAUSSHOME/lib/postgresql/下并执行如下命令授权

```
chmod 777 $GAUSSHOME/lib/postgresql/ojdbc7.jar
```

**示例**
1、加载jdbc_fdww扩展。

```
create extension jdbc_fdw;
```

2、在创建测试表。

```
create table emp_fdw(empno int,ename varchar(30));
insert into emp_fdw values(1,'foo');
insert into emp_fdw values(2,'bar');
```

3、创建连接。

```
create server ora_jdbc foreign data wrapper jdbc_fdw options(
drivername 'oracle.jdbc.driver.OracleDriver',
url 'jdbc:oracle:thin:@//172.16.103.104:1521/orcl',
querytimeout '100',
jarfile '/home/vastbase/bin/ojdbc7.jar',
maxheapsize '200'
);
```

4、新建用户并授权。

```
create user use_ora_jdbc password 'Bigdata@123';
grant usage on foreign server ora_jdbc to use_ora_jdbc;
```

5、创建到oracle的映射。

```
create user mapping for use_ora_jdbc server ora_jdbc options(username 'system',password 'root');
```

6、创建需要访问的oracle中对应表的结构。

```
create foreign table emp_fdw_ora(empno int,ename varchar(30))
server ora_jdbc options(table_name 'EMP_FDW');
```

7、将权限赋予emp_fdw_ora。

```
grant all on emp_fdw_ora to use_ora_jdbc;
```

8、查看外部表。

```
\c - use_ora_jdbc
select * from emp_fdw_ora;
```

当结果显示如下信息，则表示外部表访问成功。

```
id | info
----+-----------
1 | bar
2 | foo
(2 row)
```

