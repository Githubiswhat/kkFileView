# dblink支持库级别的连接访问

## 功能描述

当用户需要跨越本地数据库，访问远程数据库的数据时，可以通过[DBLINK](../../../开发者指南/DBLINK.md)像访问本地数据库一样访问远程数据库表中的数据。在Oracle兼容模式下，Vastbase G100支持使用tablename@dblink_name语法进行表的增删改查功能。

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
- 暂不支持同义词的DBLINK。

## 示例

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

