# DBMS_LOB

## 功能描述

DBMS_LOB用于提供一组处理BLOB（二进制数据类型）、CLOB（字符数据类型）和[BFILE数据类型](BFILE数据类型.md)（存储数据库外的大型对象，指向操作系统物理文件）的 PL/SQL 包。其中包括常量、函数、存储过程。Vastbase G100对该内置包的支持情况如下：

支持以下常量：

| 常量                                  | 值         |
| :------------------------------------ | :--------- |
| [LOBMAXSIZE](#LOBMAXSIZE)             | 1073741832 |
| [DEFAULT_CSID](#DEFAULT_CSID)         | 0          |
| [DEFAULT_LANG_CTX](#DEFAULT_LANG_CTX) | 0          |

支持以下函数：

| 函数                       | 描述                                                         |
| :------------------------- | :----------------------------------------------------------- |
| [GETLENGTH](#LOBGETLENGTH) | 获取BLOB、CLOB和BFILE数据类型的长度。                        |
| [FILEOPEN](#LOBFILEOPEN)   | 打开一个BFILE文件。                                          |
| [READ](#LOBREAD)           | 从一个BFILE文件中读取指定长度的内容。                        |
| [FILECLOSE](#FILECLOSE)    | 关闭一个BFILE文件。                                          |
| [SUBSTR](#SUBSTR)          | 该函数用于返回 LOB 中从指定位置开始的部分内容，从指定的偏移量开始。 |
| [INSTR](#INSTR)            | 该函数用来返回LOB中指定模式第n次出现的匹配位置，从指定的偏移量开始。 |

支持以下存储过程：

| 存储过程                            | 描述                                                         |
| ----------------------------------- | ------------------------------------------------------------ |
| [OPEN](#OPEN)                       | 根据传入的参数打开对应的LOB对象。                            |
| [COPY](#COPY)                       | 根据传入的参数从指定位置开始将源LOB复制到目标LOB。           |
| [WRITEAPPEND](#WRITEAPPEND)         | 将指定数据添加到一个大对象的末尾。                           |
| [CREATETEMPORARY](#CREATETEMPORARY) | 用来在临时表空间中创建临时BLOB或CLOB。                       |
| [FREETEMPORARY](#FREETEMPORARY)     | 释放临时表空间中的临时BLOB或CLOB。                           |
| [CONVERTTOBBLOB](#CONVERTTOBBLOB)   | 将CLOB从指定偏移开始的amount个字符转换到BLOB中，并返回转换后的偏移量。 |

## 注意事项

该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。



## LOBMAXSIZE<a name=LOBMAXSIZE></a>

**示例**

1、允许将dbms_output.put_line的输出信息输出至vsql的命令界面的屏幕上。

```sql
set serveroutput on;
```

2、打印常量lobmaxsize的值。

```sq
declare 
begin
  dbms_output.put_line(dbms_lob.lobmaxsize);
end;
/
```

返回结果为：

```sql
1073741823
ANONYMOUS BLOCK EXECUTE
```



## DEFAULT_CSID<a name=DEFAULT_CSID></a>

**示例**

1、允许将dbms_output.put_line的输出信息输出至vsql的命令界面的屏幕上。

```sql
set serveroutput on;
```

2、打印常量default_csid的值。

```sql
declare 
begin
  dbms_output.put_line(dbms_lob.default_csid);
end;
/
```

返回结果为：

```sql
0
ANONYMOUS BLOCK EXECUTE
```



## DEFAULT_LANG_CTX<a name=DEFAULT_LANG_CTX></a>

**示例**

1、允许将dbms_output.put_line的输出信息输出至vsql的命令界面的屏幕上。

```sql
set serveroutput on;
```

2、打印常量default_lang_ctx的值。

```sql
declare 
begin
  dbms_output.put_line(dbms_lob.default_lang_ctx);
end;
/
```

返回结果为：

```sql
0
ANONYMOUS BLOCK EXECUTE
```



## GETLENGTH<a name=LOBGETLENGTH></a>

**语法格式**

BLOB对象类型：由于存储的内容即为二进制数据，直接返回二进制内容的字节数即可，函数原型为：

```sql
DBMS_LOB.GETLENGTH (
   lob_loc    IN  BLOB) 
  RETURN INTEGER;
```

CLOB对象类型：该对象存储的数据格式为字符数据，返回值为字符数。函数原型为：

```sql
DBMS_LOB.GETLENGTH (
lob_loc    IN  CLOB) 
RETURN INTEGER; 
```

BFILE对象类型：函数返回bfile数据指向的操作系统物理文件的长度，以字节为单位，bfile文件的长度包含了文件结尾符（eof）。函数原型为：

```sql
DBMS_LOB.GETLENGTH (
 file_loc   IN  BFILE) 
 RETURN INT8;
```

**参数说明**

file_loc：将要返回其长度的文件位置。

**示例**

**示例1：**

1、创建测试表并插入数据。

```sql
create table testclob (data clob);
insert into testclob values('中文测试');
```

2、查询数据长度。

```sql
select dbms_lob. getlength (data) from testclob;
```

返回结果为：

```sql
getlength
-----------
         4
(1 row)
```

**示例2：**

1、创建测试表并插入数据。

```sql
create table testblob (data blob);
insert into testblob values(utl_raw.cast_to_raw('中文测试'));
```

2、查询数据长度。

```sql
select dbms_lob. getlength (data) from testblob;
```

返回结果为：

```sql
getlength
-----------
        12
(1 row)
```



## FILEOPEN<a name=LOBFILEOPEN></a>

**语法格式**

```sql
DBMS_LOB.FILEOPEN (
file_loc   IN OUT BFILE, 
open_mode  IN   BINARY_INTEGER ); 
```

**参数说明**

- file_loc：要打开的文件定位。
- open_mode：文件访问模式。

**示例**<a name=bfile操作></a>

1、操作系统环境下创建文件，输入数据并保存。

```shell
vi /home/vastbase/bfile.data
```

文件内容为：

```
张三
李四
```

2、在数据库中执行如下命令创建目录和表。

```sql
create directory d_bfile as '/home/vastbase';
create table testbfile1(id number,bfile_name bfile);
```

3、调用bfilename函数构造bfile数据并插入到表中。

```sql
insert into testbfile1 values(1,bfilename('d_bfile', 'bfile.data'));
```

4、设置serveroutput 为on（允许将dbms_output.put_line的输出信息输出至vsql的命令界面的屏幕上）。

```
set serveroutput on;
```

5、创建函数：

```sql
create or replace function f_read_bfile() returns void as $$
declare
buff raw;
amount int := 0 ;
offset int :=0;
lob_loc bfile;
filesize int;
begin
select bfile_name into lob_loc from testbfile1 where id=1;
--打开BFILE文件
dbms_lob.fileopen(lob_loc,0);
--获取文件大小
filesize := dbms_lob.getlength(lob_loc);
raise notice 'amount:%',amount;
--读取文件全部内容
amount = filesize;
dbms_lob.read(lob_loc,amount,0,buff);
dbms_output.put_line('file data:');
dbms_output.put_line(utl_raw.cast_to_varchar2(buff));
--关闭bfile文件
dbms_lob.fileclose(lob_loc);
return;
end;
$$ language plpgsql;
```

6、调用函数。

```sql
select f_read_bfile();
```

返回结果为：

```sql
file data:
张三
李四

 f_read_bfile
--------------

(1 row)
```



## READ<a name=LOBREAD></a>

**语法格式**

```sql
DBMS_LOB.READ (
   file_loc   IN             BFILE,
   amount    IN OUT   INTEGER,
   offset    IN              INTEGER,
   buffer    OUT             RAW);
```

**参数说明**

- file_loc：lob要检查的文件。
- amount：读取文件的长度。为了防止打开的文件过大，单次读取的内容超过机器缓冲区导致系统崩溃，规定单次读取的字节数不能大于32767字节，即amount为大于0小于等于32767的正整数。
- offset：用于多次读取文件时设置读取的起始位置，如果offset参数指向bfile文件结尾或超出文件结尾，则将amount参数设置为0，并抛出异常。
- buffer：读取操作的输出缓冲区。

**示例**

参见dbms_lob.fileopen的[示例](#bfile操作)。



## FILECLOSE<a name=FILECLOSE></a>

**语法格式**

```sql
DBMS_LOB.FILECLOSE (
    file_loc IN OUT BFILE); 
```

**参数说明**

file_loc：要关闭的文件。

**示例**

参见dbms_lob.fileopen的[示例](#bfile操作)。



## SUBSTR<a name=SUBSTR></a>

**语法格式**

```sql
DBMS_LOB.SUBSTR (
   lob_loc    IN   BLOB,
   amount     IN   INTEGER := 32767,
   offset     IN   INTEGER := 1)
  RETURN RAW;
  
DBMS_LOB.SUBSTR (
   lob_loc    IN   CLOB   CHARACTER SET ANY_CS,
   amount     IN   INTEGER := 32767,
   offset     IN   INTEGER := 1)
  RETURN VARCHAR2 CHARACTER SET lob_loc%CHARSET;
  
DBMS_LOB.INSTR (
   file_loc   IN   BFILE,
   amount     IN   INTEGER := 32767,
   offset     IN   INTEGER := 1)
  RETURN RAW;
```

**参数说明**

- lob_loc：LOB的locator。
- file_loc：FILE的locator。
- amount：要读取的BLOB的字节数或CLOB的字符数。默认值为32767。
- offset：LOB从1开始的字节数（BLOB）或字符数（CLOB）偏移量。默认值为1。

**返回值**

- 传入BLOB或BFILE类型时返回RAW类型。
- 传入CLOB类型时返回VARCHAR2类型。
- 当任意传入值为NULL，或amount<1、amount>32767、offset<1、offset>LOBMAXSIZE时，返回NULL。

**示例**

调用SUBSTR函数。

```sql
select dbms_lob.substr('ABCD'::clob);
select dbms_lob.substr('ABCD');
select dbms_lob.substr('this is lob function test',1,5) as a1;
select dbms_lob.substr('this is lob function test',length('this is lob function test'),1) as a2;
```

返回结果为：

```sql
substr
--------
 ABCD
(1 row)

substr
--------
 ABCD
(1 row)

 a1
----

(1 row)

            a2
---------------------------
 this is lob function test
(1 row)
```



## INSTR<a name=INSTR></a>

**语法格式**

```sql
DBMS_LOB.INSTR (
   lob_loc    IN   BLOB,
   pattern    IN   RAW,
   offset     IN   INTEGER := 1,
   nth        IN   INTEGER := 1)
  RETURN INTEGER;
  
DBMS_LOB.INSTR (
   lob_loc    IN   CLOB      CHARACTER SET ANY_CS,
   pattern    IN   VARCHAR2  CHARACTER SET lob_loc%CHARSET,
   offset     IN   INTEGER := 1,
   nth        IN   INTEGER := 1)
  RETURN INTEGER;
  
DBMS_LOB.INSTR (
   file_loc   IN   BFILE,
   pattern    IN   RAW,
   offset     IN   INTEGER := 1,
   nth        IN   INTEGER := 1)
  RETURN INTEGER;
```

**参数说明**

- lob_loc：待查找的LOB对象。
- file_loc：待查找的BFILE类型LOB对象。
- pattern：以字节为单位或以字符为单位的模式，用于匹配大对象中的内容。如果lob_loc代表BLOB类型大对象或file_loc代表BFILE类型大对象，那么模式必须是RAW类型。如果lob_loc代表CLOB类型大对象，那么模式必须是VARCHAR2类型。
- offset：参数lob_loc或file_loc代表的大对象中搜索模式时的开始位置。第一个字节或字符是位置1。默认值为1。
- nth：由给定的偏移量指定起始位置，开始搜索指定模式第n次出现时的位置。默认值为1。

**返回值**

匹配模式的起始偏移量，以字节或字符为单位。如果找不到模式，则返回0。

以下几种情况下返回NULL：

- 任何一个或多个IN参数为NULL或无效。
- offset < 1 或者 offset > LOBMAXSIZE。
- nth < 1 或者 nth > LOBMAXSIZE

**示例**

调用instr函数。

```sql
select dbms_lob.instr('blob test'::clob,'test',1,1);
```

返回结果为：

```sql
instr
-------
     6
(1 row)
```



## OPEN<a name=OPEN></a>

**语法格式**

```sql
DBMS_LOB.OPEN (
   lob_loc   IN OUT NOCOPY BLOB,
   open_mode IN            BINARY_INTEGER);
   
DBMS_LOB.OPEN (
   lob_loc   IN OUT NOCOPY CLOB CHARACTER SET ANY_CS,
   open_mode IN            BINARY_INTEGER);
 
DBMS_LOB.OPEN (
   file_loc  IN OUT NOCOPY BFILE,
   open_mode IN            BINARY_INTEGER := file_readonly);
```

**参数说明**

- lob_loc：LOB类型的对象名。
- open_mode：打开模式。对于BLOB，CLOB对象，可以选择LOB_READONLY或者LOB_READWRITE。对于BFILE类型，只能是FILE_READONLY。
	- LOB_READONLY：只读。
	- LOB_READWRITE：可读写。
	- FILE_READONLY：只读。

**示例**

1、创建含blob、clob类型字段的表。

```sql
create table testtable(id int,c_lob clob,b_lob blob);
insert into testtable values(1,'clob测试',utl_raw.cast_to_raw('blob'));
```

2、调用open存储过程。

```sql
set serveroutput on;
declare 
  v_clob clob;
  v_clob2 clob;
  len int;
begin
  select c_lob into v_clob from testtable where id=1;
  dbms_lob.open(v_clob,dbms_lob.lob_readwrite);
  dbms_output.put_line('clob1:'||v_clob);
  dbms_lob.close(v_clob);
end;
/
```

返回结果为：

```sql
clob1:clob测试
ANONYMOUS BLOCK EXECUTE
```



## COPY<a name=COPY></a>

**语法格式**

```sql
DBMS_LOB.COPY (
  dest_lob    IN OUT NOCOPY BLOB,
  src_lob     IN            BLOB,
  amount      IN            INTEGER,
  dest_offset IN            INTEGER := 1,
  src_offset  IN            INTEGER := 1);

DBMS_LOB.COPY ( 
  dest_lob    IN OUT NOCOPY CLOB  CHARACTER SET ANY_CS,
  src_lob     IN            CLOB  CHARACTER SET dest_lob%CHARSET,
  amount      IN            INTEGER,
  dest_offset IN            INTEGER := 1,
  src_offset  IN            INTEGER := 1);
```

**参数说明**

- dest_lob：目标LOB对象。
- src_lob：源LOB对象。
- amount：复制的长度。
- dest_offset：目标LOB中的偏移量。
- src_offset：源LOB中的偏移量。

**示例**

存储过程调用copy函数，输入参数中的dest_lob为clob类型。

1、创建含blob类型字段的表并插入数据。

```sql
drop table if exists testtable_01;
create table testtable_01(id int,c_lob clob);
insert into testtable_01 values(1,'blobtest1测试测试');
insert into testtable_01 values(2,'blobtest2测试测试');
```

2、调用COPY函数。

```sql
set serveroutput on;
DECLARE
vdest_lob CLOB;
vsrc_lob CLOB;
amount NUMBER;
dest_offset NUMBER;
src_offset NUMBER;
BEGIN
SELECT c_lob INTO vdest_lob FROM testtable_01 WHERE id = 1 ;
SELECT c_lob INTO vsrc_lob FROM testtable_01 WHERE id = 2 ;
amount := DBMS_LOB.GETLENGTH(vsrc_lob);
dest_offset := DBMS_LOB.GETLENGTH(vdest_lob) + 1;
src_offset := 1;
DBMS_LOB.COPY(vdest_lob, vsrc_lob, amount, dest_offset, src_offset);
DBMS_OUTPUT.PUT_LINE('拷贝结果为: ' || vdest_lob);
END;
/
```

返回结果为：

```sql
拷贝结果为: blobtest1测试测试blobtest2测试测试
ANONYMOUS BLOCK EXECUTE
```



## WRITEAPPEND<a name=WRITEAPPEND></a>

**语法格式**

```sql
DBMS_LOB.WRITEAPPEND (
   lob_loc IN OUT NOCOPY BLOB, 
   amount  IN            INTEGER, 
   buffer  IN            RAW); 
   
DBMS_LOB.WRITEAPPEND (
   lob_loc IN OUT NOCOPY CLOB CHARACTER SET ANY_CS, 
   amount  IN            INTEGER, 
   buffer  IN            VARCHAR2 CHARACTER SET lob_loc%CHARSET);
```

**参数说明**

- lob_loc：LOB类型的对象名。
- amount：追加的长度。
- buffer：待追加的内容。

**示例**

调用writeappend函数将数据添加对lob的末尾，lob_loc类型为clob。

1、创建含测试表。

```sql
create table bak_dbms_lob(
bak_id number(4),
bak_comment clob
);
```

2、插入测试数据。

```sql
insert into bak_dbms_lob(bak_id,bak_comment) values(1,'a');
insert into bak_dbms_lob(bak_id,bak_comment) values(2,'ab');
insert into bak_dbms_lob(bak_id,bak_comment) values(3,'abcdefgccccccc');
insert into bak_dbms_lob(bak_id,bak_comment) values(4,'a bcdefg');
select * from bak_dbms_lob;
```

返回结果为：

```sql
bak_id |  bak_comment
--------+----------------
      1 | a
      2 | ab
      3 | abcdefgccccccc
      4 | a bcdefg
(4 rows)
```

3、调用调用writeappend函数。

```sql
set serveroutput on;
declare
lob_loc clob;
amount integer:= 18;
buffer_text varchar2(20) := 'added text to clob';
begin
select bak_comment into lob_loc from bak_dbms_lob where bak_id = 2;
dbms_lob.writeappend(lob_loc,amount,buffer_text);
dbms_output.put_line('添加后的字段为：' || lob_loc);
end;
/
```

返回结果为：

```sql
添加后的字段为：abadded text to clob
ANONYMOUS BLOCK EXECUTE
```



## CREATETEMPORARY<a name=CREATETEMPORARY></a>

**语法格式**

```sql
DBMS_LOB.CREATETEMPORARY (
   lob_loc IN OUT NOCOPY BLOB,
   cache   IN            BOOLEAN,
   dur     IN            PLS_INTEGER := DBMS_LOB.SESSION);
  
DBMS_LOB.CREATETEMPORARY (
   lob_loc IN OUT NOCOPY CLOB CHARACTER SET ANY_CS,
   cache   IN            BOOLEAN,
   dur     IN            PLS_INTEGER := 10);
```

**参数说明**

- lob_loc：LOB类型的对象名。
- cache：是否将LOB读取到缓冲区。
- dur：指定何时清除临时LOB（10/ SESSION：会话结束时；12/ CALL：调用结束时）。

**示例**

调用createtemporary存储过程，创建临时clob。

```sql
set serveroutput on;
DECLARE
v_clob CLOB;
v_amount INT;
v_offset INT;
v_char VARCHAR2(100);
BEGIN
v_char := 'Chinese中国人';
v_offset := 1;
v_amount := 8;
dbms_lob.createtemporary(v_clob,TRUE,12);
FOR i IN 1..2 LOOP
dbms_lob.writeappend(v_clob,v_amount,v_char);
dbms_output.put_line(v_clob||' 字符长度：'||dbms_lob.getlength(v_clob));
END LOOP;
END;
/
```

返回结果为：

```sql
Chinese中 字符长度：8
Chinese中Chinese中 字符长度：16
ANONYMOUS BLOCK EXECUTE
```



## FREETEMPORARY<a name=FREETEMPORARY></a>

**语法格式**

```sql
DBMS_LOB.FREETEMPORARY (
   lob_loc  IN OUT  NOCOPY BLOB); 

DBMS_LOB.FREETEMPORARY (
   lob_loc  IN OUT  NOCOPY CLOB CHARACTER SET ANY_CS);
```

**参数说明**

lob_loc：LOB类型的对象名。

**示例**

调用freetemporary存储过程，创建临时clob后释放。

```sql
set serveroutput on;
DECLARE
v_clob CLOB;
v_amount INT;
v_offset INT;
v_char VARCHAR2(100);
BEGIN
v_char := 'Chinese中国人';
v_offset := 1;
v_amount := 8;
dbms_lob.createtemporary(v_clob,TRUE,12);
dbms_lob.writeappend(v_clob,v_amount,v_char);
dbms_output.put_line(v_clob||' 字符长度：'||dbms_lob.getlength(v_clob));
dbms_lob.freetemporary(v_clob);
dbms_output.put_line(' 释放后再输出：'||v_clob);
END;
/
```

返回结果为：

```sql
Chinese中 字符长度：8
释放后再输出：
ANONYMOUS BLOCK EXECUTE
```



## CONVERTTOBBLOB<a name=CONVERTTOBBLOB></a>

**语法格式**

```sql
DBMS_LOB.CONVERTTOBBOLB(
dest_blob  IN OUT  NOCOPY  BLOB,
src_clob   IN      CLOB CHARACTER SET ANY_CS,
amount     IN      INTEGER,
dest_offset IN OUT INTEGER,
src_offset  IN OUT INTEGER,
blob_csid   IN     NUMBER,
lang_context IN OUT INTEGER,
warning     OUT   INTEGER);
```

**参数说明**

- dest_lob IN：表示一个BLOB类型的大对象。
- dest_lob OUT：表示转换后的BLOB类型的大对象。
- src_clob：表示一个CLOB类型的大对象。
- amount：表示在参数src_clob所指定的大对象中要转换的字符数量。
- dest_offset IN：BLOB类型的目标大对象中字节的位置。
- dest_offset OUT：写操作完成后，在BLOB类型大对象中字节的位置。
- src_offset IN：转换操作中，CLOB类型大对象开始的位置。
- src_offset OUT：在转换操作完成后，CLOB类型大对象中字符的位置。
- blob_csid：BLOB类型大对象中的字符集ID。

	> <div align="left"><img src="image/img1.png" style="zoom:75%"></div>
	> 0并不是真实存在的字符集ID，为0时表示使用源CLOB的字符集。暂不支持其它取值。

- lang_context IN：在转换操作中使用的语言环境。默认值为0。
- lang_context OUT：转换后的语言环境。
- warning：如果转换成功，则返回0；如果遇到不可转换的字符，则返回1。

**示例**

1、调用CONVERTTOBBLOB存储过程。

```sql
declare
b blob := utl_raw.cast_to_raw('ABCDEFGH');
c clob := '测试123';
amount INTEGER := 4;
dest_offset INTEGER := 1;
src_offset INTEGER := 2;
csid INTEGER := 0;
lang_ctx INTEGER := 0;
warning INTEGER;
begin
dbms_lob.converttoblob(b,c,amount,dest_offset,src_offset,csid,lang_ctx,warning);
dbms_output.put_line(utl_raw.cast_to_varchar2(b));
dbms_output.put_line(dest_offset||' ' ||src_offset||' '||lang_ctx||' '||warning);
end;
/
```

返回结果为：

```sql
试123GH
7 6 0 0
ANONYMOUS BLOCK EXECUTE
```

