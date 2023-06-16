模式(schema)是数据库中的一个概念，**可以将其理解为一个命名空间或者目录**（用\dn查看有哪些模式）。类似于操作系统层次的目录，只不过模式不能嵌套。



一个数据库包含一个或多个命名的模式，模式又包含表。模式还包含其它命名的对象，包括数据类型，函数，以及操作符。同一个对象名可以在不同的模式里使用而不会导致冲突；比如，schema1 和 myschema 都可以包含叫做 mytable 的表。和数据库不同，模式不是严格分离的，一个用户可以访问他所连接的数据库中的任意模式中的对象，只要他有权限。



#### 创建模式

```
create schema demo_schema;
create schema demo_schema AUTHORIZATION hmxz2nn;
```

在指定模式里创建表，如：

```
CREATE TABLE myschema.mytable (
...
);
```

#### 修改模式

```
ALTER SCHEMA name RENAME TO new_name;
ALTER SCHEMA name OWNER TO new_owner;
```

#### 删除模式

```
DROP SCHEMA [ IF EXISTS ] name [, ...] [ CASCADE | RESTRICT ]
//DROP SCHEMA从数据库中删除模式。
```

