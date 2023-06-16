#### UTF8MB4编码字符集

**功能描述**

UTF8MB4用来兼容4字节的unicode的字符集。Vastbase G100版本在如下场景中兼容UTF8MB4：

- 初始化数据库，执行initdb命令的场景。

- 创建数据库的语句中。

- 客户端可设置client_encoding 参数为 utf8mb4或utf-8mb4。

**背景信息**

unicode是一种国际组织制定能容纳所有文字和符号的字符编码方案。Unicode用数字0-0x10FFFF来映射这些字符，最多可以容纳1114112个字符，或者说有1114112个码位。码位就是可以分配给字符的数字。

**语法格式**

- 初始化数据库，执行initdb命令的场景。

```
-E | --encoding=UTF8MB4 | UTF-8MB4
```

- 创建数据库的语句中。

```
CREATE DATABASE testdb1 ENCODING 'UTF8MB4 | UTF-8MB4';
```

- 客户端可设置client_encoding 参数为 utf8mb4或utf-8mb4。

```
set client_encoding='UTF8MB4 |UTF-8MB4 ';
```

**兼容性**

完全兼容。

**示例**

- 初始化时指定字符集（操作系统执行）。

```
mkdir ~/test1
vb_initdb -w Aa123456 -D ~/test1 --nodename='vbnode1' --encoding=utf8mb4
```

- 创建数据库时指定字符集。

```
create database dbutf8mb4 with template=template0 encoding='utf8mb4' lc_ctype='C' lc_collate='C';
```

- 客户端设置指定的字符集。

```
set client_encoding='utf8mb4';
```

#### 