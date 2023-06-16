# UTL_ENCODE

## 功能描述

UTL_ENCODE包提供了将原始数据编码转换为标准编码格式的函数，以便数据可以在主机之间传输。

该内置包包含以下函数：

<table>
<tr>
<th>函数</th>
<th>描述</th>
</tr>
<tr>
<td> <a href="#BASE64_DECODE">BASE64_DECODE</a></td>
<td>读取 base 64 编码的输入字符串并将其解码为其原始值RAW。 </td> 
</tr>
<tr>
<td><a href="#MIMEHEADER_ENCODE">MIMEHEADER_ENCODE</a></td>
<td>将字符串编码为 MIME 格式。 </td> 
 </tr>
<tr>
<td><a href="#QUOTED_PRINTABLE_DECODE">QUOTED_PRINTABLE_DECODE</a></td>
<td>读取带引号的可打印格式输入字符串并将其解码为相应的字符串varchar2RAW。 </td> 
 </tr>
<tr>
<td><a href="#TEXT_DECODE">TEXT_DECODE</a></td>
 <td>解码字符集敏感文本字符串。 </td> 
</tr>
<tr>
<td><a href="#UUDECODE">UUDECODE</a></td>
 <td>读取uuencode 格式的输入字符串并将其解码为相应的字符串RAW。 </td> 
</tr>
</table>

## 注意事项

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

- 系统已经安装python3。python3安装方法请参见[安装配置python3环境](安装配置python3环境.md)。

- 系统已经创建plpython3u插件，需设置环境变量PYTHONHOME，包括以下内容（重启数据库生效）：

  - PYTHONHOME：python3安装路径。

  - LD_LIBRARY_PATH：python3安装路径下的lib目录。

  - PATH：python3安装路径bin目录。

    使用如下命令创建插件：

    ```sql
    CREATE EXTENSION plpython3u;
    ```

## 示例

**前置步骤：**创建并切换至兼容模式为Oracle的库。

```sql
create database db_oracle dbcompatibility ‘A’；
\c db_oracle
```

**示例1：**使用函数调用utl_encode内置包函数。

1、系统已经安装python3。

2、创建plpython3u插件。

```sql
CREATE EXTENSION plpython3u;
```

3、创建表以及插入raw数据。

```sql
create table raw_test (raw_date raw(10));
insert into raw_test values (utl_raw.cast_to_raw('051'));
create table raw2_test(text varchar);
insert into raw2_test values('zhangsan');
```

4、调用UUDECODE函数。

```sql
create or replace function func1() return raw
as
t_raw raw(50);
begin
select UTL_ENCODE.UUDECODE(raw_date) from raw_test into t_raw ;
dbms_output.put_line('t_time: '|| t_raw );
return t_raw;
end;
/

select func1 ();
```

返回结果为：

```sql
 func1
-------
  5510
(1 row)
```

5、调用BASE64_DECODE函数。

```sql
create or replace function func2() return raw
as
t_raw raw(50);
begin
select UTL_ENCODE.BASE64_DECODE(raw_date) from raw_test into t_raw ;
dbms_output.put_line('t_time: '|| t_raw );
return t_raw;
end;
/

select func2 ();
```

返回结果为：

```sql
 func2
-------
  D39D
(1 row)
```

6、调用QUOTED_PRINTABLE_DECODE函数。

```sql
create or replace function func3() return raw
as
t_raw raw(50);
begin
select UTL_ENCODE.QUOTED_PRINTABLE_DECODE(raw_date) from raw_test into t_raw ;
dbms_output.put_line('t_time: '|| t_raw );
return t_raw;
end;
/

select func3 ();
```

返回结果为：

```sql
 func3
-------
303531
(1 row)
```

7、调用TEXT_DECODE函数。

```sql
create or replace function func4() return varchar2
as
t_raw varchar2;
begin
select UTL_ENCODE.TEXT_DECODE(text) from raw2_test into t_raw ;
dbms_output.put_line('t_time: '|| t_raw );
return t_raw;
end;
/

select func4 ();
```

返回结果为：

```sql
 func4
-------
zhangsan
(1 row)
```

8、调用MIMEHEADER_ENCODE 函数。

```sql
create or replace function func5() return varchar2
as
t_raw varchar2;
begin
select UTL_ENCODE.MIMEHEADER_ENCODE(text) from raw2_test into t_raw ;
dbms_output.put_line('t_time: '|| t_raw );
return t_raw;
end;
/

select func5 ();
```

返回结果为：

```sql
     func5
------------------
=?UTF8?zhangsan?=
(1 row)
```

## 子程序

### BASE64_DECODE<a id="BASE64_DECODE"></a>

**语法格式**

```sql
UTL_ENCODE.BASE64_DECODE (
   r  IN RAW) 
RETURN RAW;
```

**参数说明**

**r**

base64编码的数据的RAW字符串。

函数返回值为解码后的RAW字符串。


### **MIMEHEADER_ENCODE** <a id="MIMEHEADER_ENCODE"></a>

此函数返回一个”编码单词“作为输出。

输出采用以下形式：

```sql
=?<charset>?<encoding>?<encoded text>?= 
=?ISO-8859-1?Q?Here is some text?= 
```

**语法格式**

```sql
UTL_ENCODE.MIMEHEADER_ENCODE (
   buf            IN  VARCHAR2 CHARACTER SET ANY_CS, 
   encode_charset IN  VARCHAR2 DEFAULT NULL, 
   encoding       IN  PLS_INTEGER DEFAULT NULL) 
 RETURN string VARCHAR2 CHARACTER SET buf%CHARSET;
```

**参数说明**

- **buf**

  待编码文本数据。

- **encode_charset**

  用于编码的字符集，NULL表示使用数据库的字符集。

- **encoding**

  编码格式。

  有效值为：

  - 1：表示base64

  - 2：表示quoted_printable

  默认值：quoted_printable

### QUOTED_PRINTABLE_DECODE<a id="QUOTED_PRINTABLE_DECODE"></a>

**语法格式**

```sql
UTL_ENCODE.QUOTED_PRINTABLE_DECODE (
   r  IN RAW)
RETURN RAW;
```

**参数说明**

**r**

quoted_printable编码的数据的RAW字符串。

### TEXT_DECODE<a id="TEXT_DECODE"></a>

**语法格式**

```sql
UTL_ENCODE.TEXT_DECODE(
   buf            IN  VARCHAR2 CHARACTER SET ANY_CS, 
   encode_charset IN  VARCHAR2 DEFAULT NULL, 
   encoding       IN  PLS_INTEGER DEFAULT NULL) 
 RETURN string VARCHAR2 CHARACTER SET buf%CHARSET;
```

**参数说明**

- **buf**

  已经编码好的文本数据。

- **encode_charset**

  源文本数据字符集。

- **encoding**

  编码格式。

  有效值为：

  - 1：表示base64

  - 2：表示quoted_printable

  默认值：quoted_printable

### UUDECODE<a id="UUDECODE"></a>

**语法格式**

```sql
UTL_ENCODE.UUDECODE (
   r  IN RAW) 
RETURN RAW;
```

**参数说明**

**r**

已经编码好的文本数据。







