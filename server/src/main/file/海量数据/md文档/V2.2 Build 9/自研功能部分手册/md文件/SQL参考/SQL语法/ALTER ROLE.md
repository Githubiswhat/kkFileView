##### ALTER ROLE

**功能描述**

修改角色属性。

**语法格式**

- 修改角色的权限。

```
ALTER ROLE role_name [ [ WITH ] option [ ... ] ];
```

其中权限项子句option为：

```
  {CREATEDB | NOCREATEDB}
      | {CREATEROLE | NOCREATEROLE}
      | {INHERIT | NOINHERIT}
      | {AUDITADMIN | NOAUDITADMIN}
      | {SYSADMIN | NOSYSADMIN}
      | {MONADMIN | NOMONADMIN}
      | {OPRADMIN | NOOPRADMIN}
      | {POLADMIN | NOPOLADMIN}
      | {USEFT | NOUSEFT}
      | {LOGIN | NOLOGIN}
      | {REPLICATION | NOREPLICATION}
      | {INDEPENDENT | NOINDEPENDENT}
      | {VCADMIN | NOVCADMIN}
      | {PERSISTENCE | NOPERSISTENCE}
      | CONNECTION LIMIT connlimit
      | [ ENCRYPTED | UNENCRYPTED ] PASSWORD 'password' [EXPIRED]
      | [ ENCRYPTED | UNENCRYPTED ] IDENTIFIED BY 'password' [ REPLACE 'old_password' | EXPIRED ]
      | VALID BEGIN 'timestamp'
      | VALID UNTIL 'timestamp'
      | RESOURCE POOL 'respool'
      | USER GROUP 'groupuser'
      | PERM SPACE 'spacelimit'
      | TEMP SPACE 'tmpspacelimit'
      | SPILL SPACE 'spillspacelimit'
      | NODE GROUP logic_cluster_name
      | ACCOUNT { LOCK | UNLOCK }
      | PGUSER
```

- 修改角色的名称。

```
ALTER ROLE role_name 
    RENAME TO new_name;
```

- 锁定或解锁。

```
ALTER ROLE role_name 
    ACCOUNT { LOCK | UNLOCK };
```

- 设置角色的配置参数。

```
ALTER ROLE role_name [ IN DATABASE database_name ]
  SET configuration_parameter {{ TO | = } { value | DEFAULT } | FROM CURRENT};
```

- 重置角色的配置参数。

```
ALTER ROLE role_name
    [ IN DATABASE database_name ] RESET {configuration_parameter|ALL};
```

**参数说明**

- role_name：现有角色名。

  取值范围：已存在的用户名。

- IN DATABASE database_name：表示修改角色在指定数据库上的参数。

- SET configuration_parameter：设置角色的参数。ALTER ROLE中修改的会话参数只针对指定的角色，且在下一次该角色启动的会话中有效。

  取值范围：configuration_parameter和value的取值请参见[SET](SET.md)

  - DEFAULT：表示清除configuration_parameter参数的值，configuration_parameter参数的值将继承本角色新产生的SESSION的默认值。
  - FROM CURRENT：取当前会话中的值设置为configuration_parameter参数的值。

- RESET configuration_parameter/ALL：清除configuration_parameter参数的值。与SET configuration_parameter TO DEFAULT的效果相同。

  取值范围：ALL表示清除所有参数的值。

- ACCOUNT LOCK | ACCOUNT UNLOCK：

  - ACCOUNT LOCK：锁定帐户，禁止登录数据库。
  - ACCOUNT UNLOCK：解锁帐户，允许登录数据库。

- PGUSER：当前版本不允许修改角色的PGUSER属性。

- PASSWORD/IDENTIFIED BY 'password'：重置或修改用户密码。除了初始用户外其他管理员或普通用户修改自己的密码需要输入正确的旧密码。只有初始用户、系统管理员（sysadmin）或拥有创建用户（CREATEROLE）权限的用户才可以重置普通用户密码，无需输入旧密码。初始用户可以重置系统管理员的密码，系统管理员不允许重置其他系统管理员的密码。

- EXPIRED：设置密码失效。只有初始用户、系统管理员（sysadmin）或拥有创建用户（CREATEROLE）权限的用户才可以设置用户密码失效，其中系统管理员也可以设置自己或其他系统管理员密码失效。不允许设置初始用户密码失效。密码失效的用户可以登录数据库但不能执行查询操作，只有修改密码或由管理员重置密码后才可以恢复正常查询操作。


其他参数请参见“CREATE ROLE”的参数说明部分。

**注意事项**

只允许修改用户的标识，但是不允许修改属性标识。

**示例**

- 修改角色manager的密码为abcd@123。 

```
ALTER ROLE manager IDENTIFIED BY 'abcd@123' REPLACE 'Bigdata@123';
```

- 修改角色manager为系统管理员。 

```
ALTER ROLE manager SYSADMIN; 
```

