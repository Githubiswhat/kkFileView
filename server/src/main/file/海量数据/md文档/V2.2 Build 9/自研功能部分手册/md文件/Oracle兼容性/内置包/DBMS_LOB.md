### DBMS_LOB

**功能描述**

DBMS_LOB用于提供一组对blob（二进制数据类型）、clob（字符数据类型）和bfile数据类型（存储数据库外的大型对象，指向操作系统物理文件）操作的函数或过程。该内置包支持一下函数：

<table>
<tr>
<th>函数</th>
<th>描述</th>
</tr>
<tr>
 <td><a href="#LOBGETLENGTH">GETLENGTH</a></td>
<td>获取blob、clob和bfile数据类型的长度。</td>
</tr>
<tr>
<td><a href="#LOBFILEOPEN">FILEOPEN</a></td>
<td>打开一个bfile文件。</td>
</tr>
<tr>
<td><a href="#LOBREAD">READ</a></td>
<td>从一个bfile文件中读取指定长度的内容。</td>
</tr>
<tr>
<td><a href="#LOBFILECLOSE">FILECLOSE</a></td>
<td>关闭一个bfile文件。</td>
</tr>
</table>


**注意事项**

无。

**示例**

1、操作系统环境下创建文件，并输入数据。

```
 vi /home/vastbase/bfile.data
 cat /home/vastbase/bfile.data
```

文件内容为：

```
张三
李四
```

2、在数据库中执行如下命令创建目录和表。

```
drop directory d_bfile;
drop table t_bfile;
create directory d_bfile as '/home/vastbase';
create table testbfile1(id number,bfile_name bfile);
```

3、调用bfilename函数构造bfile数据并插入到表中。

```
insert into testbfile1 values(1,bfilename('d_bfile', 'bfile.data'));
set serveroutput on;
```

创建函数：

```
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

4、调用函数。

```
select f_read_bfile();
```

返回结果为：

```
file data:
张三
李四

 f_read_bfile
--------------

(1 row)
```

#### GETLENGTH<a id="LOBGETLENGTH"></a>

**语法格式**

BLOB对象类型：由于存储的内容即为二进制数据，直接返回二进制内容的字节数即可，函数原型为：

```
DBMS_LOB.GETLENGTH (
   lob_loc    IN  BLOB) 
  RETURN INTEGER;
```

CLOB对象类型：该对象存储的数据格式为字符数据，返回值为字符数。函数原型为：

```
DBMS_LOB.GETLENGTH (
lob_loc    IN  CLOB) 
RETURN INTEGER; 
```

BFILE对象类型：函数返回bfile数据指向的操作系统物理文件的长度，以字节为单位，bfile文件的长度包含了文件结尾符（eof）。函数原型为：

```
DBMS_LOB.GETLENGTH (
 file_loc   IN  BFILE) 
 RETURN INT8;
```

**参数说明**

file_loc：将要返回其长度的文件位置。

**示例**

示例一：

1、创建测试表并插入数据。

```
create table testclob (data clob);
insert into testclob values('中文测试');
```

2、查询数据长度。

```
select dbms_lob. getlength (data) from testclob;
```

返回结果为：

```
 getlength
-----------
         4
(1 row)
```

示例二：

1、创建测试表并插入数据。

```
create table testblob (data blob);
insert into testblob values(utl_raw.cast_to_raw('中文测试'));
```

2、查询数据长度。

```
select dbms_lob. getlength (data) from testblob;
```

返回结果为：

```
 getlength
-----------
        12
(1 row)
```




#### FILEOPEN<a id="LOBFILEOPEN"></a>

**语法格式**

```
DBMS_LOB.FILEOPEN (
file_loc   IN OUT BFILE, 
open_mode  IN   BINARY_INTEGER ); 
```

**参数说明**

- file_loc：要打开的文件定位。

- open_mode：文件访问模式。

#### READ<a id="LOBREAD"></a>

**语法格式**

```
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

#### FILECLOSE<a id="FILECLOSE"></a>

**语法格式**

```
DBMS_LOB.FILECLOSE (
    file_loc IN OUT BFILE); 
```

**参数说明**

file_loc：要关闭的文件。