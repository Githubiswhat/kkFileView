### UTL_RAW

**功能描述**

UTL_RAW包提供了一系列对raw类型数据进行处理的函数。

**函数说明**

<table>
<tr>
<th>函数</th>
<th>描述</th>
</tr>
<tr>
<td>cast_to_raw</td>
<td>
将输入的varchar2类型的数据转换成raw类型的数据，在此过程中数据并未以任何方式修改，仅仅只是修改了数据类型。
</td>
</tr>
<tr>
<td>cast_to_varchar2</td>
<td>
将输入的raw类型数据转换回varchar2类型。
</td>
</tr>
</table>




**注意事项**

cast_to_varchar2函数的输入字符须为十六进制表示形式的16个字符，否则会返回识别错误。

**示例**

- **cast_to_raw函数**

示例一：

```
select utl_raw.cast_to_raw('test') from dual;
```

返回结果为'test'字符串每个字符的ASCII码的十六进制表示形式拼接而成，结果如下：

```
cast_to_raw
-----------
74657374
(1 row)
```

示例二：

```
select utl_raw.cast_to_raw('测试') from dual;
```

 返回结果为'测试'在utf8编码下中文转换成十六进制的表现形式，如下所示：

```
cast_to_raw
-----------
E6B58BE8AF95
(1 row)
```


- **cast_to_varchar2函数**

示例一：

```
select utl_raw.cast_to_varchar2('74657374') from dual;
```

返回结果为:

```
cast_to_varchar2
----------------
test
(1 row)
```

示例二：

```
select utl_raw.cast_to_varchar2('E6B58BE8AF95') from dual;
```

返回结果为:

```
cast_to_varchar2
----------------
测试
(1 row)
```

注意：cast_to_varchar2函数的输入字符须为十六进制表示形式的16个字符，否则会返回识别错误。

```
select utl_raw.cast_to_varchar2('test') from dual;
ERROR: invalid hexadecimal digit: "t"
LINE 1: select utl_raw.cast_to_varchar2('test') from dual;
∧
CONTEXT: 	referenced column: cast_to_varchar2
```
