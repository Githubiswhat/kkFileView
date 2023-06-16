###  DBMS_ROWID

**功能描述**
该内置包用于我们从PL/SQL和SQL中创建ROWIDs和获取关于ROWIDs的信息。该内置包包含以下函数：

- ROWID_CREATE函数：rowid_create函数用于创建一个rowid。


**语法格式**

```
dbms_rowid.rowid_create(
rowid_type number,
object_number number,
relative_fno number,
block_number number,
row_number number);
```

**参数说明**

- rowid_type：rowid类型（restricted或者extended），目前Vastbase G100的orwid只实现了extended类型，因此该参数目前不生效。
- object_number：数据库对象oid，Vastbase G100中主要是表的oid。不能小于0，不能为小数。
- relative_fno：所在数据库文件编号，Vastbase G100的rowid未实现此功能，因此不生效。
- block_number：数据块编号。不能小于0，不能为小数。
- row_number：块中偏移量。不能小于0，不能为小数。

**兼容性**

Vastbase G100 V2.2版本与Oracle 11g R2版本相比，不支持的函数有：ROWID_BLOCK_NUMBER、 ROWID_INFO、 ROWID_OBJECT、ROWID_RELATIVE_FNO、ROWID_ROW_NUMBER、ROWID_TO_ABSOLUTE_FNO、ROWID_TO_EXTENDED、ROWID_TO_RESTRICTED、ROWID_TYPE、ROWID_VERIFY

**示例**

```
select dbms_rowid.rowid_create(0,1,1,1,1);
```

返回结果为：

```
rowid_create
----------------------
AQAAAA==AQAAAA==AQA=
(1 row)
```

###  