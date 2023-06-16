# CREATE DATABASE LINK

## 功能描述

定义一个新的数据库链接。当用户需要跨越本地数据库，访问远程数据库的数据时，可以通过DBLINK像访问本地数据库一样访问远程数据库表中的数据。

## 注意事项

- 不同的用户可以创建同名的private dblink，且用户只能访问自己创建的DBLINK。
- 同一用户可以创建同名的private dblink和，在访问时，优先访问private dblink。
- 为支持不同用户创建同名dblink，需禁用GRANT FOREIGN SERVER，不再允许将server权限给予其他用户，以防一个用户能访问多个同名server，造成混乱。
- 创建private dblink或public dblink时，必须提前创建jdbc_fdw，并具备DATABASE LINK相关权限。
- 创建数据库链接的帐号必须有CREATE DATABASE LINK或CREATE PUBLIC DATABASE LINK的系统权限。

## 语法格式

```
CREATE [PUBLIC] DATABASE LINK dblink_name
        CONNECT TO username IDENTIFIED BY 'password' [USING fdw_name] (
                jdbc_fdw_option
        );
```

其中jdbc_fdw_option 可以包括：

```
url 'value',
jarfile 'value',
[ querytimeout 'value'] [, ...]
```

## 参数说明

- **dblink_name**

  连接名称，可自定义。

- **fdw_name** 

  指定fdw名，fdw是foreign data wrapper的简称，为外部封装数据，由用户自定义创建。

  fdw_name为fdw的名称，如示例中jdbc_fdw等。

- **url 'value'**

  指定连接库的URL地址。

  value值为连接库的url地址。

- **jarfile 'value'**

  指定驱动包的获取路径。

  value值为驱动包所在的路径。

- **querytimeout 'value'**

  设置查询超时时长。
  
  value值为超时时长。

## 示例<a name='example'>

1、在Oracle数据库创建表。

```
create table emp_fdw(empno int primary key,ename varchar(30));
insert into emp_fdw values(1,'foo');
insert into emp_fdw values(2,'bar');
```

2、在Vastbase数据库需要使用jdbc_fdw的database下创建插件。

```
create extension jdbc_fdw;
```

3、修改vastbase配置参数。修改postgresql.conf参数，将jdbc_fdw配置到shared_preload_libraries参数中。修改后需要重启数据库实例。

```
shared_preload_libraries = 'jdbc_fdw'
```

4、创建dblink。

```
CREATE PUBLIC DATABASE LINK dblink_104 CONNECT TO lst IDENTIFIED BY 'test' USING jdbc_fdw(
url 'jdbc:oracle:thin:@//172.16.103.104:1521/orcl',
jarfile '/home/vastbase_lst/ojdbc7.jar'
);
```

3、创建用户。

```
CREATE USER lst PASSWORD 'Bigdata@123';
```

4、为用户授权。

```
grant usage on foreign data wrapper jdbc_fdw to lst;
grant all on database vastbase to lst;
```

5、切换到用户lst，查询数据。

```
\c - lst
SELECT * from emp_fdw@dblink_104;
```

## 相关链接

[drop database link](drop-database-link.md)

