### 字符串CHAR/VARCHAR/VARCHAR2指定BYTE和CHAR关键字

**功能描述**

常见的字符串类型varchar2、char、varchar数据类型的字段长度默认为字节byte，常见写法如varchar(10)。而在UTF-8字符集中，中文汉字一个字符占用了3字节，如果该字段指定限制字符串长度为10，实际上只能插入3个UTF-8汉字字符。

为了解决这种情况，Vastbase G100V2.2版本支持在创建字段的时候显式定义字段的长度单位为byte或者character（即在定义varchar2、char、varchar数据类型时支持指定byte和character关键字）。

- byte为按字节来定义字段长度，是该数据类型的缺省值。
- character为按字符来定义字段长度，可以简写成char。

**语法格式**

```
varchar(n byte)或者varchar(n character)
```

**参数说明**

- n character：表明该字段允许输入n个字符，等于n char。
- n byte：表明该字段允许输入n个字节。

**注意事项**

无。

**示例**

创建测试表。

```
create table test_char(
C1 char(10 char), --10 character ，允许输入最多10个字符，包括英文和中文
C2 char(10 byte), --10 byte，允许输入最多10个字节，能允许输入的字符数需要以字符具体所占字节数为准计算
C3 varchar(10 character), --10 character
C4 varchar(10), --10 byte
C5 char, --不指定限制长度，默认允许输入最多1个字节
C6 varchar, --不指定限制长度，默认不限制输入长度
C7 varchar2(10 character), --10 character
C8 varchar2(10) --10 byte
);
```

返回结果如下，则表示字符串char/varchar/varchar2指定byte和char关键字：

```
CREATE TABLE
```
