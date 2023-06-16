# vb_initdb

## 功能描述

vb_initdb用于初始化数据库，在初始化数据库时，会创建数据库目录、生成系统表、创建默认数据库和模板数据库。

**系统表**

初始化数据库时会生成大量的系统表和视图，其中绝大部分都对任何数据库用户开放查看权限。

> <div align="left"><img src="image/img1.png" style="zoom:75%")</div> 
>
> pg_user_status、pg_auth_history系统表权限只对初始化数据库用户和sysadmin用户开放。

**创建数据库**

- template1：是一个模板数据库，当以后再创建一个新的数据库时，template1数据库里的所有内容都会拷贝到新数据库中。通过vb_initdb的参数可以决定template1数据库的设置。
- template0：是Vastbase提供的最初始的备份数据库，当需要时可用template0作为模板生成“干净”的数据库。
- postgres：是一个提供给用户、工具和第三方应用的缺省数据库。

在安装时，推荐使用-D参数调用vb_initdb初始化数据库。如果由于故障恢复等原因，需要重新初始化一个数据库，可以通过执行vb_initdb来完成。

- 尽管vb_initdb会尝试创建相应的数据目录，但可能没有权限执行此操作，因为要创建目录的父目录通常被root所拥有。如果要创建数据目录，首先用root用户创建一个空数据目录，然后用chown把该目录的所有权交给数据库用户。
- vb_initdb决定template1数据库的设置，而该设置将会成为其他数据库的默认设置。
- vb_initdb初始化数据库的缺省区域和字符集编码。字符集编码、字符编码排序（LC_COLLATE）和字符集类（LC_CTYPE，如大写、小写数字等）可以在创建数据库时独立设置。

## 语法格式

```
  vb_initdb [OPTION]... [DATADIR]

Options:
  -A, --auth=METHOD         default authentication method for local connections
      --auth-host=METHOD    default authentication method for local TCP/IP connections
      --auth-local=METHOD   default authentication method for local-socket connections
  -c, --enable-dcf          enable DCF mode
 [-D, --pgdata=]DATADIR     location for this database cluster
      --nodename=NODENAME   name of single node initialized
  -E, --encoding=ENCODING   set default encoding for new databases
      --locale=LOCALE       set default locale for new databases
      --dbcompatibility=DBCOMPATIBILITY   set default dbcompatibility for new database
      --pad-attribute=PADATTRIBUTE        set default pad attribute for new database
                                          support NO PAD(N, default) and PAD SPACE(S)
      --lc-collate=, --lc-ctype=, --lc-messages=LOCALE
      --lc-monetary=, --lc-numeric=, --lc-time=LOCALE
                            set default locale in the respective category for
                            new databases (default taken from environment)
      --no-locale           equivalent to --locale=C
      --pwfile=FILE         read password for the new system admin from file
  -T, --text-search-config=CFG
                            default text search configuration
  -U, --username=NAME       database system admin name
  -W, --pwprompt            prompt for a password for the new system admin
  -w, --pwpasswd=PASSWD     get password from command line for the new system admin
  -C, --enpwdfiledir=DIR    get encrypted password of AES128 from cipher and rand file
  -X, --xlogdir=XLOGDIR     location for the transaction log directory
  -S, --security            remove normal user's privilege on public schema in security mode
  -g, --xlogpath=XLOGPATH   xlog file path of shared storage

Less commonly used options:
  -d, --debug               generate lots of debugging output
  -L DIRECTORY              where to find the input files
  -n, --noclean             do not clean up after errors
  -s, --show                show internal settings

Other options:
  -H, --host-ip             node_host of Vastbase node initialized
      --keyname=swkms_key_name      the key stored in sw kms to encrypt data of database initialized
      --userpin=username:password the user pin to login sw kms
      --algorithm=ENCRYPTALGORITHM   encrypt algorithm of database initialized
      --audit_encrypt_algorithm=AUDITENCRYPTALGORITHM   encrypt algorithm of audit file
      --audit_master_key=MASTERKEY   master key to encrypt key of audit file
      --audit_encrypt_key=ENCRYPTKEY   encrypt key of audit file
  -V, --version             output version information, then exit
  -?, --help                show this help, then exit
```

## 参数说明

常用参数如下：

- **-?，\--help**

  显示vb_initdb命令的帮助信息，然后退出。

- **-V，\--version**

  输出vb_initdb命令的版本信息，然后退出。

- **-A, \--auth=METHOD**

  指定本地用户连接数据库时的认证方法，即“pg_hba.conf”配置文件中host和local所在行的认证方法。

  取值范围：trust、reject、md5（不安全的算法，为了兼容老版本而存在）、sha256、sm3

  默认值：trust（除非用户对本地用户都是信任的，否则不要使用默认值trust）

  > <div align="left"><img src="image/img2.png" style="zoom:75%")</div> 
  >
  > 若取值为md5，则需手动修改参数文件 postgresql.conf.sample 中的密码存储类型 password_encryption_type 参数的值，修改为0，且放开注释使之生效。vs_initdb工具需同时配合 -W 的使用。

- **\--auth-host=METHOD**

  指定本地用户通过TCP/IP连接数据库时的认证方法，即：“pg_hba.conf”配置文件中host所在行的认证方法（指定此参数则会覆盖-A参数的值）。

  取值范围：trust、reject、md5（不安全的算法，为了兼容老版本而存在）、sha256、sm3

  默认值：trust

- **\--auth-local=METHOD**

  指定本地用户通过Unix域套接字连接数据库时的认证方法，即“pg_hba.conf”配置文件中local所在行的认证方法（指定此参数则会覆盖-A参数的值）。

  取值范围：trust、reject、md5（不安全的算法，为了兼容老版本而存在）、sha256、sm3、peer（仅用于local模式）

  默认值：trust

- **[-D, \--pgdata=]DATADIR**

  指定数据目录的位置。

  取值范围： DATADIR的取值：用户自定义。不能包括“|”, “;”，“&”，“$”，“<”，“>”，“`”，“\\”，“!”这几个字符。

- **\--nodename=NODENAME**

  初始化的节点名称。

  节点的命名需要遵守如下规范：

  - 节点名称必须为小写字母（a-z）、下划线（_）、特殊符号#、数字（0-9）。
  - 节点名称必须以小写字母（a-z）或下划线（_）开头。
  - 节点名称不能为空，且最大的长度为64个字符 。
  
- **-E, \--encoding=ENCODING**

    为新数据库设置编码格式。

    > <div align="left"><img src="image/img2.png" style="zoom:75%")</div> 
    >
    > - 如果使用此参数，需要加上\--locale选项指定支持此编码格式的区域。如果不加\--locale选项，则采用系统默认的区域，如果系统默认区域的编码格式和用此参数指定的编码格式不匹配则会导致数据库初始化失败。
    >
    > - 如果不指定此参数，则使用系统默认区域的编码格式。系统默认区域和编码格式可以使用locale如下命令查看：
    > ```
    > locale|grep LC_CTYPE
    > ```
    >
    > - 不能包括“|”, “;”，“&”，“$”，“<”，“>”，“`”，“\\”，“!”这几个字符。


- **\--locale=LOCALE**

    为新数据库设置缺省的区域（可以用locale -a查看可用的区域），如果不希望指定特定的区域，则可以用C。

    > <div align="left"><img src="image/img2.png" style="zoom:75%")</div> 
    >
    > - 如果用户设置了数据库的编码格式，则用户选择区域的编码格式必须与用户设置的编码格式一致，否则数据库初始化会失败。
    >
    > - 不能包括“|”, “;”，“&”，“$”，“<”，“>”，“`”，“\\”，“!”这几个字符。
    >
    > - 示例：用户要将数据库编码格式初始化为GBK，可以采用如下步骤：
    >
    >   1、查看系统支持gbk编码的区域。
    >
    >   ```
    >   locale -a|grep gbk
    >   zh_CN.gbk
    >   zh_SG.gbk
    >   ```
    >
    >   2、初始化数据库时加入\--locale=zh_CN.gbk选项。

- **\--dbcompatibility=DBCOMPATIBILITY**

    指定兼容的数据库的类型。

    取值范围：取值范围：A、B、C、PG。分别表示兼容Oracle、MySQL、TD和POSTGRES。

- **--pad-attribute=PADATTRIBUTE**<a name="attribute"></a>

    为新数据库设置默认的列校对规则，可选取值包括：

    - N,NO PAD：默认值。把字符串尾端的空格当作一个字符处理，即字符串等值比较不忽略尾端空格。
    - S,PAD SPACE：字符串等值比较忽略尾端空格。
      > <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
      > Vastbase G100 V2.2 Build12版本开始支持在初始化数据库时指定列校对规则。
- **\--no-locale**

    等价于\--locale=C。

- **\--pwfile=FILE**

    vb_initdb从文件中读取数据库超级用户的密码。该文件的第一行被作为密码使用。

    > <div align="left"><img src="image/img2.png" style="zoom:75%")</div> 
    >
    > FILE可以是“相对路径+文件”的形式，也可以是“绝对路径+文件”的形式。相对路径是相对当前路径的。不能包括“|”, “;”，“&”，“$”，“<”，“>”，“`”，“\\”，“!”这几个字符。

- **-T, \--text-search-config=CFG**

    设置缺省的文本搜索方式。此配置项的值不会做正确性校验，配置成功后，有日志记录提醒当前配置项的取值。

    取值范围：english（全文搜索）、simple（普通文本搜索）

    默认值：simple

- **-U, \--username=NAME**

    选择数据库系统管理员的用户名。

    取值范围：正常的数据库用户

    > <div align="left"><img src="image/img2.png" style="zoom:75%")</div> 
    >
    > 不能包括“|”, “;”，“&”，“$”，“<”，“>”，“`”，“\\”，“!”这几个字符。

    默认值：运行vb_initdb的操作系统用户

- **-W, \--pwprompt**

    vb_initdb时强制交互式输入数据库管理员的密码。

- **-w, \--pwpasswd=PASSWD**

    vb_initdb时通过命令行指定的管理员用户的密码，而不是交互式输入。

    > <div align="left"><img src="image/img2.png" style="zoom:75%")</div> 
    >
    > 设置的密码要符合复杂度要求：
    >
    > - 最少包含8个字符；
    > - 不能和用户名和当前密码（ALTER）相同，或和当前密码反序；
    > - 至少包含大写字母（A-Z）、小写字母（a-z）、数字、非字母数字字符（限定为~!@#$%^&*()-_=+\|[{}];:,<.>/?）四类字符中的三类字符。

- **-X, \--xlogdir=XLOGDIR**

    声明事务日志存储的目录(所设置的目录，必须满足运行Vastbase的用户有读写权限)。

    > <div align="left"><img src="image/img2.png" style="zoom:75%")</div> 
    >
    > 只支持绝对路径。不能包括“|”, “;”，“&”，“$”，“<”，“>”，“`”，“\\”，“!”这几个字符。

- **-S, \--security**

    安全方式初始化数据库。以-S方式初始化的数据库后，创建的数据库用户权限受到限制，默认不再具有public schema的使用权限。

## 使用方法

执行如下命令初始化数据库。

```
vb_initdb  -D /home/vastbase/data/vastbase --nodename vastbase -w vbase@123
```

> <div align="left"><img src="image/img1.png" style="zoom:75%")</div> 
>
> ./home/vastbase/data/vastbase为数据库数据目录，可自定义，需存在且在vastbase用户下，需要0700权限。详细请参见【快速入门->非实例化数据库安装->[初始化数据库运行环境](../快速入门/非实例化数据库安装.md#chushihua)。

