# CREATE DATABASE

## 功能描述

Vastbase G100在MySQL兼容模式下支持在CREATE DATABASE时通过PAD_ATTRIBUTE参数指定字符串等值比较时是否忽略字符串尾端的空格匹配。

## 语法格式

```sql
CREATE DATABASE [ IF NOT EXISTS ] database_name
    [ [ WITH ] {[ OWNER [=] user_name ]|
           [ TEMPLATE [=] template ]|
           [ ENCODING [=] encoding ]|
           [ LC_COLLATE [=] lc_collate ]|
           [ LC_CTYPE [=] lc_ctype ]|
           [ DBCOMPATIBILITY [=] compatibility_type ]|
           [ TABLESPACE [=] tablespace_name ]|
           [ CONNECTION LIMIT [=] connlimit ]}[...] ]|
           [ PAD_ATTRIBUTE [=] pad_attribute_type ];
```

## 参数说明

- **PAD_ATTRIBUTE [=] pad_attribute_type**<a name="attribute"></a>

    为新数据库设置默认的列校对规则。可选取值包括：
    
    - N,NO PAD：默认值。把字符串尾端的空格当作一个字符处理，即字符串等值比较不忽略尾端空格。
    
    - S,PAD SPACE：字符串等值比较忽略尾端空格。

> <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
> 
> 更多参数说明请参考：[CREATE DATABASE](CREATE-DATABASE.md)。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 在其它兼容模式的数据库下，可以使用PAD SPACE版本的COLLATION，但不会生效；即字符串等值比较时都不会忽略尾端的空格。（例如：其它兼容模式下，指定COLLATION为C和C-space是等效的，字符串等值比较时都不会忽略尾端的空格。）

## 示例

1、创建兼容模式为MySQL的数据库，同时指定PAD ATTRIBUTE为PAD SPACE。

```sql
create database db_1132613 dbcompatibility 'B' PAD_ATTRIBUTE 'S';
```

2、切换至上一步创建的数据库db_1132613下。

```sql
\c db_1132613
```

3、查看当前数据库的PAD ATTRIBUTE参数。

```sql
show pad_attribute;
```

返回结果为：

```sql
 pad_attribute
---------------
 PAD SPACE
(1 row)
```

4、创建具有各种字符串类型的测试表，并为每一列设置不同的列校对规则。（其中列c2、c4、c6的校对规则为`*-space`，即忽略空格匹配，c7未指定列校对规则，使用建库时指定的默认校对规则。）

```sql
create table tb_1132417(
c1 text COLLATE "C",
c2 character varying(30) COLLATE "C-space",
c3 varchar(30) COLLATE "zh_CN",
c4 varchar2(30) COLLATE "zh_CN-space",
c5 nvarchar2(30) COLLATE "en_US",
c6 clob COLLATE "en_US-space",
c7 varchar(30));
```

5、插入测试数据。（插入的第二条测试数据尾部均带有空格。）

```sql
insert into tb_1132417 values('qqq', 'cqvjj11', 'h2vu2hb3t', 'f4rh535', 'fr2h5fj3c', 'r2hfhcg', 'r2cgcr');
insert into tb_1132417 values('qqq ', 'cqvjj11 ', 'h2vu2hb3t ', 'f4rh535 ', 'fr2h5fj3c ', 'r2hfhcg ', 'r2cgcr ');
```

6、查看是否正确识别空格。

```sql
select c1='qqq', c2='cqvjj11', c3='h2vu2hb3t', c4='f4rh535', c5='fr2h5fj3c', c6='r2hfhcg', c7='r2cgcr' from tb_1132417;
```

返回结果如下：对于插入的第二条尾部带空格的数据，c2、c4、c6、c7可以忽略空格，返回true，其他列不能忽略空格，返回false。

```sql
 ?column? | ?column? | ?column? | ?column? | ?column? | ?column? | ?column?
----------+----------+----------+----------+----------+----------+----------
 t        | t        | t        | t        | t        | t        | t
 f        | t        | f        | t        | f        | t        | t
(2 rows)
```

