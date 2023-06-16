# CREATE SECURITY LABEL

## 功能描述

定义一个安全标签。

## 注意事项

无

## 语法格式

```
CREATE SECURITY LABEL label_name 'label_context';
```

## 参数说明

- **label_name**

  安全标签名称，创建时要求不能与已有标签重名。

  取值范围：字符串，要符合标识符的命名规范。

- **label_context**

  标签上下文。

## 示例

1、开启线程池，修改$PGDATA/postgresql.conf文件，并重启数据库。

```
enable_thread_pool = on
```

2、新建函数。

```
create or replace function stat_parallel(out rec boolean, inout statements text) returns record
as $$
begin
    begin
        rec := true;
        execute statements;
        exception when others then rec := false;
     end;
end;
$$ LANGUAGE plpgsql;
```

3、连接到vastbase的数据库连接池2，执行以下操作：

```
\parallel on 2
select stat_parallel('create user user1 with password "user1@abcd";');
```

结果显示为：

```
                      stat_parallel
------------------------------------------------------------------
(t,"create user user1 with password ""user1@abcd"";")
(1 row)
```

4、执行以下操作：

```
select stat_parallel('create user user1 with password "user1@abcd";');
\parallel off
```

显示结果为：

```
                            stat_parallel
-------------------------------------------------------------------------
(f,"create user user1 with password ""user1@abcd"";")
(1 row)
```

5、创建安全标签，并为安全标签指定属性。

```
create security label l1 'L3:G1,G2';
security label on role user1 is 'l1';
```

## 相关链接

