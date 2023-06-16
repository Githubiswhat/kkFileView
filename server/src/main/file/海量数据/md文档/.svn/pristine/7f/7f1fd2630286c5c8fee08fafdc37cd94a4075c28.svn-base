# DBLINK

## 功能描述

当用户需要跨越本地数据库，访问远程数据库的数据时，可以通过[DBLINK](../../../开发者指南/DBLINK.md)像访问本地数据库一样访问远程数据库表中的数据。在Oracle兼容模式下，Vastbase G100支持使用tablename@dblink_name语法进行表的增删改查功能。

在Vastbase G100 V2.2 Build 12版本中增强DBLINK同义词功能，支持DBLINK与SYNONYM结合访问Oracle包中的函数。

## 语法格式

- 创建DBLINK。

```
CREATE [PUBLIC] DATABASE LINK ${DBLINK_NAME} CONNECT TO ${USERNAME}
IDENTIFIED BY '${PASSWORD}' USING ${FDW_NAME}
({HOST},{PORT},{DBNAME});
```

- 删除DBLINK。

```
DROP [PUBLIC] DATABASE LINK ${DBLINK_NAME}
```

- 通过DBLINK进行远端表的增删改查操作。

```
INSERT INTO [${SCHEMA_NAME}].${TABLE_NAME}@${DBLINK_NAME}	#插入
UPDATE [${SCHEMA_NAME}].${TABLE_NAME}@${DBLINK_NAME} SET...	#更新
DELETE FROM [${SCHEMA_NAME}].${TABLE_NAME}@${DBLINK_NAME} WHERE...	#删除 
SELECT ... FROM [${SCHEMA_NAME}].${TABLE_NAME}@${DBLINK_NAME}	#查询
```

## 参数说明

- ${DBLINK_NAME} ：连接名称，可自定义。
- ${SCHEMA_NAME}：目标函数或存储过程所在的模式名（或者package名称）。可不指定，不指定时默认访问database link中Oracle用户对应的模式。
- ${TABLE_NAME}：远程访问的数据库表名。

## 注意事项

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。
- 创建DBLINK时，必须提前创建[postgres_fdw](../../../开发者指南/PostgreSQL_FDW.md)，并具备DBLINK相关权限。
- 不支持二进制数据类型、二进制大对象类型、xml数据类型。
- 不支持retun为%rowtype行数据类型。
- 由于 Oracle与Vastbase函数的差异性，return值会以最后一个out参数形式存在，直接远程调用函数、存储过程时，数据库会自动补充return参数，不需要用户补充，而使用同义词时，无法确定此时是call调用还是select 调用，无法自动补全return参数，需要用户自行补充，在使用流程章节中将以用例再次详细说明。

## 示例

**前置步骤：**创建postgres_fdw。

1、修改postgresql.conf参数，将jdbc_fdw配置到shared_preload_libraries参数中。

```shell
shared_preload_libraries='jdbc_fdw'
```

2、驱动包ojdbc8.jar放置到$GAUSSHOME/lib/postgresql/下。

```shell
chmod 777 $GAUSSHOME/lib/postgresql/ojdbc8.jar
```

3、重启数据库实例。

4、在需要使用jdbc_fdw的database下执行以下命令。

```shell
DROP EXTENSION jdbc_fdw;
CREATE EXTENSION jdbc_fdw;
```

**示例1：**通过DBLINK访问远端数据库。

1、在远端数据库创建用户testlink_user并授权。

```
create user testlink_user with sysadmin password '123456Aa';
grant all on database vastbase to testlink_user;
```

2、在远端数据库创建表emp_fdw，并插入数据。

```
create table emp_fdw(empno int primary key,ename varchar(30));
insert into emp_fdw values(1,'foo');
insert into emp_fdw values(2,'bar');
```

3、在本地数据库创建用户user_1 并授权。

```
create user user_1 password 'Aa123456';
grant all on database vastbase to user_1;
```

4、关闭远端数据库和本地数据库的强制修改密码功能。

```
alter system set password_force_alter=off;
```

5、在本地数据库，把当前会话里的会话用户标识和当前用户标识都设置为用户user_1 。

```
set session session authorization user_1 password 'Aa123456';
```

6、在本地数据库创建DBLINK。

```
CREATE DATABASE LINK dblink_84 CONNECT TO usr IDENTIFIED BY '123456Aa' USING postgres_fdw(host '172.16.105.57',port '10929',dbname 'vastbase');
```

7、在本地数据库通过dblink_84查询表emp_fdw的数据。

```
select * from emp_fdw@dblink_84;
```

返回结果如下，表示查询到了远端数据库中的emp_fdw表的内容。

```
 empno | ename
-------+-------
     1 | foo
     2 | bar
(2 rows)
```

**示例2：**与同义词结合访问Oracle包中的函数。

1、在Oracle中创建测试数据和包。

```sql
drop table tb_1133599;
create table tb_1133599(id number(10) ,name varchar(10),info varchar2(10));
insert into tb_1133599 values(1,'小明','中国');
insert into tb_1133599 values(2,'vivi','未知');
insert into tb_1133599 values(3,'zuzu','未知');
```

```sql
drop package ora_pkg_1133599;
create or replace package ora_pkg_1133599 as
function ora_pkg_1133599_func1(id1 number,c2 out varchar,c3 out varchar2) return number;
procedure ora_pkg_1133599_proc1(id1 number ,col2 out varchar,col3 out varchar2);
end ora_pkg_1133599;
/
```

```sql
create or replace package body ora_pkg_1133599 as
function ora_pkg_1133599_func1(id1 number,c2 out varchar,c3 out varchar2) return number
as
begin
select name into c2 from tb_1133599 where id=id1;
select info into c3 from tb_1133599 where id=id1;
return id1;
end;
```

```sql
create procedure ora_pkg_1133599_proc1(id1 number ,col2 out varchar,col3 out varchar2)
as
begin
select name into col2 from tb_1133599 where id=id1;
select info into col3 from tb_1133599 where id=id1;
end;
end ora_pkg_1133599;
/
```

2、在Vastbase中创建DBLINK。

```sql
CREATE public DATABASE LINK dblink_104 CONNECT TO SYSTEM IDENTIFIED BY 'root' USING jdbc_fdw(
url 'jdbc:oracle:thin:@//172.16.103.104:1521/orcl',
jarfile '/$GAUSSHOME/lib/postgresql/ojdbc8.jar'
); 
```

3、在Vastbase中创建同义词访问Oracle的包。

```sql
create SYNONYM syn_1133599 for ora_pkg_1133599@dblink_104;
```

4、创建自定义函数。

```sql
create or replace function func_1133599(id int) return varchar as
declare
v_id number;  
v_name varchar;
v_info varchar2(10);
begin
if id=1 then
syn_1133599.ora_pkg_1133599_func1(id,v_name,v_info,v_id);
v_name=v_id||v_name||v_info;
return v_name;  
elsif id=2 then
syn_1133599.ora_pkg_1133599_proc1(2,v_name,v_info);
v_name=v_name||v_info;
return v_name;    
else
return 'null';
end if;  
end;
/
```

5、调用函数。

```sql
call func_1133599(1);
```

结果返回如下：

```sql
 func_1133599
--------------
 1小明中国
(1 row)
```

