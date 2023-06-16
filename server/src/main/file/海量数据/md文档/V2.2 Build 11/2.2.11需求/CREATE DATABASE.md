# CREATE DATABASE

## 功能描述

该语法用于创建一个新的数据库。缺省情况下新数据库将通过复制标准系统数据库template0来创建，且仅支持使用template0来创建。

## 注意事项

- 只有拥有CREATEDB权限的用户才可以创建新数据库，系统管理员默认拥有此权限。
- 不能在事务块中执行创建数据库语句。
- 在创建数据库过程中，出现类似“Permission denied”的错误提示，可能是由于文件系统上数据目录的权限不足。出现类似“No space left on device”的错误提示，可能是由于磁盘满引起的。
- 事务中不支持创建database。
- 当新建数据库Encoding、LC-Collate 或LC_Ctype与模板数据库（SQL_ASCII）不匹配（为'GBK' /'UTF8'/'LATIN1'）时，必须指定template [=] template0。

## 语法格式

```sql
CREATE DATABASE database_name
    [ [ WITH ] {[ OWNER [=] user_name ]|
           [ TEMPLATE [=] template ]|
           [ ENCODING [=] encoding ]|
           [ LC_COLLATE [=] lc_collate ]|
           [ LC_CTYPE [=] lc_ctype ]|
           [ DBCOMPATIBILITY [=] compatibility_type ]|
           [ TABLESPACE [=] tablespace_name ]|
           [ CONNECTION LIMIT [=] connlimit ]}[...] ];
```

## 参数说明

- **database_name**

  数据库名称。

  取值范围：字符串，要符合标识符的命名规范。

  > <div align="left"><img src="image/image2.png" style="zoom:25%")</div>  
  >
  > Vastbase G100 V2.2 Build11版本开始支持在创建数据库的时候使用中文的数据库名。

- **OWNER [ = ] user_name**

  数据库所有者。

  取值范围：已存在的用户名。

  默认值：当前用户。

- **TEMPLATE [ = ] template**

  模板名。即从哪个模板创建新数据库。Vastbase采用从模板数据库复制的方式来创建新的数据库。初始时，Vastbase包含两个模板数据库template0、template1，以及一个默认的用户数据库postgres。

  取值范围：仅template0。

- **ENCODING [ = ] encoding**

  指定数据库使用的字符编码，可以是字符串（如'SQL_ASCII'）、整数编号。

  不指定时，默认使用模版数据库的编码。模板数据库template0和template1的编码默认与操作系统环境相关。template1不允许修改字符编码，因此若要变更编码，请使用template0创建数据库。

  常用取值：GBK、UTF8、Latin1。

  **表1**   Vastbase字符集

  | **名称**       | **描述**                      | **语言**               | **是否服务器端？** | **ICU?** | **字节/字符** | **别名**                              |
  | :------------- | :---------------------------- | :--------------------- | :----------------- | :------- | :------------ | :------------------------------------ |
  | BIG5           | Big Five                      | 繁体中文               | 否                 | 否       | 1-2           | WIN950，Windows950                    |
  | EUC_CN         | 扩展UNIX编码-中国             | 简体中文               | 是                 | 是       | 1-3           | -                                     |
  | EUC_JP         | 扩展UNIX编码-日本             | 日文                   | 是                 | 是       | 1-3           | -                                     |
  | EUC_JIS_2004   | 扩展UNIX编码-日本, JIS X 0213 | 日文                   | 是                 | 否       | 1-3           | -                                     |
  | EUC_KR         | 扩展UNIX编码-韩国             | 韩文                   | 是                 | 是       | 1-3           | -                                     |
  | EUC_TW         | 扩展UNIX编码-台湾             | 繁体中文，台湾话       | 是                 | 是       | 1-3           | -                                     |
  | GB18030        | 国家标准                      | 中文                   | 是                 | 否       | 1-4           | -                                     |
  | GBK            | 扩展国家标准                  | 简体中文               | 是                 | 否       | 1-2           | WIN936，Windows936                    |
  | ISO_8859_5     | ISO 8859-5, ECMA 113          | 拉丁语/西里尔语        | 是                 | 是       | 1             | -                                     |
  | ISO_8859_6     | ISO 8859-6, ECMA 114          | 拉丁语/阿拉伯语        | 是                 | 是       | 1             | -                                     |
  | ISO_8859_7     | ISO 8859-7, ECMA 118          | 拉丁语/希腊语          | 是                 | 是       | 1             | -                                     |
  | ISO_8859_8     | ISO 8859-8, ECMA 121          | 拉丁语/希伯来语        | 是                 | 是       | 1             | -                                     |
  | JOHAB          | JOHAB                         | 韩语                   | 否                 | 否       | 1-3           | -                                     |
  | KOI8R          | KOI8-R                        | 西里尔语（俄语）       | 是                 | 是       | 1             | KOI8                                  |
  | KOI8U          | KOI8-U                        | 西里尔语（乌克兰语）   | 是                 | 是       | 1             | -                                     |
  | LATIN1         | ISO 8859-1, ECMA 94           | 西欧                   | 是                 | 是       | 1             | ISO88591                              |
  | LATIN2         | ISO 8859-2, ECMA 94           | 中欧                   | 是                 | 是       | 1             | ISO88592                              |
  | LATIN3         | ISO 8859-3, ECMA 94           | 南欧                   | 是                 | 是       | 1             | ISO88593                              |
  | LATIN4         | ISO 8859-4, ECMA 94           | 北欧                   | 是                 | 是       | 1             | ISO88594                              |
  | LATIN5         | ISO 8859-9, ECMA 128          | 土耳其语               | 是                 | 是       | 1             | ISO88599                              |
  | LATIN6         | ISO 8859-10, ECMA 144         | 日耳曼语               | 是                 | 是       | 1             | ISO885910                             |
  | LATIN7         | ISO 8859-13                   | 波罗的海               | 是                 | 是       | 1             | ISO885913                             |
  | LATIN8         | ISO 8859-14                   | 凯尔特语               | 是                 | 是       | 1             | ISO885914                             |
  | LATIN9         | ISO 8859-15                   | 带欧罗巴和口音的LATIN1 | 是                 | 是       | 1             | ISO885915                             |
  | LATIN10        | ISO 8859-16, ASRO SR 14111    | 罗马尼亚语             | 是                 | 否       | 1             | ISO885916                             |
  | MULE_INTERNAL  | Mule内部编码                  | 多语种编辑器           | 是                 | 否       | 1-4           | -                                     |
  | SJIS           | Shift JIS                     | 日语                   | 否                 | 否       | 1-2           | Mskanji，ShiftJIS，WIN932，Windows932 |
  | SHIFT_JIS_2004 | Shift JIS, JIS X 0213         | 日语                   | 否                 | 否       | 1-2           | -                                     |
  | SQL_ASCII      | 未指定（见文本）              | 任意                   | 是                 | 否       | 1             | -                                     |
  | UHC            | 统一韩语编码                  | 韩语                   | 否                 | 否       | 1-2           | WIN949，Windows949                    |
  | UTF8           | Unicode, 8-bit                | 所有                   | 是                 | 是       | 1-4           | Unicode                               |
  | WIN866         | Windows CP866                 | 西里尔语               | 是                 | 是       | 1             | ALT                                   |
  | WIN874         | Windows CP874                 | 泰语                   | 是                 | 否       | 1             | -                                     |
  | WIN1250        | Windows CP1250                | 中欧                   | 是                 | 是       | 1             | -                                     |
  | WIN1251        | Windows CP1251                | 西里尔语               | 是                 | 是       | 1             | WIN                                   |
  | WIN1252        | Windows CP1252                | 西欧                   | 是                 | 是       | 1             | -                                     |
  | WIN1253        | Windows CP1253                | 希腊语                 | 是                 | 是       | 1             | -                                     |
  | WIN1254        | Windows CP1254                | 土耳其语               | 是                 | 是       | 1             | -                                     |
  | WIN1255        | Windows CP1255                | 希伯来语               | 是                 | 是       | 1             | -                                     |
  | WIN1256        | Windows CP1256                | 阿拉伯语               | 是                 | 是       | 1             | -                                     |
  | WIN1257        | Windows CP1257                | 波罗的海               | 是                 | 是       | 1             | -                                     |
  | WIN1258        | Windows CP1258                | 越南语                 | 是                 | 是       | 1             | ABC, TCVN，TCVN5712，VSCII            |

  > <div align="left"><img src="image/image3.png" style="zoom:25%")</div>  
  >
  > 并非所有的客户端API都支持上面列出的字符集。
  >
  >  SQL_ASCII设置与其他设置表现得相当不同。如果服务器字符集是SQL_ASCII，服务器把字节值0-127根据 ASCII标准解释，而字节值128-255则当作无法解析的字符。如果设置为SQL_ASCII，就不会有编码转换。因此，这个设置基本不是用来声明所使用的指定编码， 因为这个声明会忽略编码。在大多数情况下，如果你使用了任何非ASCII数据，那么使用 SQL_ASCII设置都是不明智的，因为Vastbase将无法帮助你转换或者校验非ASCII字符。
  >
  > <div align="left"><img src="image/image2.png" style="zoom:25%")</div>  
  >
  > - 指定新的数据库字符集编码必须与所选择的本地环境中（LC_COLLATE和LC_CTYPE）的设置兼容。
  > - 当指定的字符编码集为GBK时，部分中文生僻字无法直接作为对象名。这是因为GBK第二个字节的编码范围在0x40-0x7E之间时，字节编码与ASCII字符@A-Z[\]^\_`a-z{|}重叠。其中@[]^_{|}是数据库中的操作符，直接作为对象名时，会语法报错。例如“侤”字，GBK16进制编码为0x8240，第二个字节为0x40，与ASCII“@”符号编码相同，因此无法直接作为对象名使用。如果确实要使用，可以在创建和访问对象时，通过增加双引号来规避这个问题。
  > - 若客户端编码为A，服务器端编码为B，则需要满足数据库中存在编码格式A与B的转换，例如：若服务器端编码为gb18030，由于当前数据库不支持gb18030与gbk的相互转换，所以此时设置客户端编码格式为gbk时，会报错“Conversion between GB18030 and GBK is not supported.”。数据库能够支持的所有的编码格式转换详见系统表pg_conversion。

- **LC_COLLATE [ = ] lc_collate**

  指定新数据库使用的字符集。例如，通过lc_collate = 'zh_CN.gbk'设定该参数。

  该参数的使用会影响到对字符串的排序顺序（如使用ORDER BY执行，以及在文本列上使用索引的顺序）。默认是使用模板数据库的排序顺序。

  取值范围：有效的排序类型。

- **LC_CTYPE [ = ] lc_ctype**

  指定新数据库使用的字符分类。例如，通过lc_ctype = 'zh_CN.gbk'设定该参数。该参数的使用会影响到字符的分类，如大写、小写和数字。默认是使用模板数据库的字符分类。

  取值范围：有效的字符分类。

- **DBCOMPATIBILITY [ = ] compatibility_type**

  指定兼容的数据库的类型。

  取值范围：A、B、C、PG。分别表示兼容O、MY、TD和POSTGRES。

  默认值：A

  > <div align="left"><img src="image/image1.png" style="zoom:25%")</div>  
  >
  > - A兼容性下，数据库将空字符串作为NULL处理，数据类型DATE会被替换为TIMESTAMP(0) WITHOUT TIME ZONE。
  > - 将字符串转换成整数类型时，如果输入不合法，B兼容性会将输入转换为0，而其它兼容性则会报错。
  > - PG兼容性下，CHAR和VARCHAR以字符为计数单位，其它兼容性以字节为计数单位。例如，对于UTF-8字符集，CHAR(3)在PG兼容性下能存放3个中文字符，而在其它兼容性下只能存放1个中文字符。

- **TABLESPACE [ = ] tablespace_name**

  指定数据库对应的表空间。

  取值范围：已存在表空间名。

- **CONNECTION LIMIT [ = ] connlimit**

  数据库可以接受的并发连接数。

  取值范围：>=-1的整数。

  默认值：-1，表示没有限制。
  
  > <div align="left"><img src="image/image2.png" style="zoom:25%")</div>  
  >
  > - 系统管理员不受此参数的限制。
  > - connlimit数据库主节点单独统计，Vastbase整体的连接数 = connlimit * 当前正常数据库主节点个数。

**有关字符编码的一些限制**

- 若区域设置为C（或POSIX），则允许所有的编码类型，但是对于其他的区域设置，字符编码必须和区域设置相同。
- 若字符编码方式是SQL_ASCII，并且修改者为管理员用户时，则字符编码可以和区域设置不相同。
- 编码和区域设置必须匹配模板数据库，除了将template0当作模板。 因为其他数据库可能会包含不匹配指定编码的数据，或者可能包含排序顺序受LC_COLLATE和LC_CTYPE影响的索引。复制这些数据会导致在新数据库中的索引失效。template0是不包含任何会受到影响的数据或者索引。

## 示例

**示例1：**创建数据库。

1、创建jim和tom用户。

```sql
CREATE USER jim PASSWORD 'Aa@12345';
CREATE USER tom PASSWORD 'Aa@12345';
```

2、创建一个"UTF-8"编码的数据库music。

```sql
CREATE DATABASE music ENCODING 'UTF-8' template = template0;
```

3、创建数据库music2，并指定所有者为jim。

```sql
CREATE DATABASE music2 OWNER jim;
```

4、用模板template0创建数据库music3，并指定所有者为jim。

```sql
CREATE DATABASE music3 OWNER jim TEMPLATE template0;
```

5、设置music数据库的连接数为10。

```sql
ALTER DATABASE music CONNECTION LIMIT= 10;
```

6、将music名称改为music4。

```sql
ALTER DATABASE music RENAME TO music4;
```

7、将数据库music2的所属者改为tom。

```sql
ALTER DATABASE music2 OWNER TO tom;
```

8、设置music3的表空间为PG_DEFAULT。

```sql
ALTER DATABASE music3 SET TABLESPACE PG_DEFAULT;
```

9、关闭在数据库music3上缺省的索引扫描。

```sql
 ALTER DATABASE music3 SET enable_indexscan TO off;
```

10、重置enable_indexscan参数。

```sql
ALTER DATABASE music3 RESET enable_indexscan;
```

11、删除数据库和用户。

```sql
DROP DATABASE music2;
DROP DATABASE music3;
DROP DATABASE music4;
DROP USER jim;
DROP USER tom;
```

**示例2：**创建中文名称的数据库。

1、创建数据库。

```sql
create database 数据库;
```

2、创建英文名称的数据库。

```sql
create database db_1096053;
```

3、将英文数据库名称修改为中文。

```sql
alter database db_1096053 rename to 新数据库;
```

4、查询数据库。

```sql
\l
```

返回结果为：

```sql
                                   List of databases
   Name    |  Owner   | Encoding |  Collate   |   Ctype    |     Access privileges
-----------+----------+----------+------------+------------+----------------------------
 postgres  | vastbase | UTF8     | en_US.utf8 | en_US.utf8 |
 template0 | vastbase | UTF8     | en_US.utf8 | en_US.utf8 | =c/vastbase               +
           |          |          |            |            | vastbase=CTc/vastbase
 template1 | vastbase | UTF8     | en_US.utf8 | en_US.utf8 | =c/vastbase               +
           |          |          |            |            | vastbase=CTc/vastbase

 数据库    | vastbase | UTF8     | en_US.utf8 | en_US.utf8 |
 新数据库  | vastbase | UTF8     | en_US.utf8 | en_US.utf8 |
(5 rows)
```

