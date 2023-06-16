### UTL_URL

**功能描述**

UTL_URL内置包用于对URL进行转义。

通常URL中会包含英文字母、数字和标点符号，这些字符称为非保留字符。URL中的任何其他字符，包括多字节字符和二进制八位字节码，都必须进行转义才能被web服务器或web浏览器准确处理。一些标点符号，例如美元符号 ( $)、问号 (？)、冒号 (:)和等号 ( =)) 被保留为 URL 中的分隔符，它们被称为保留字符，需要从字面上来处理这些字符，而不是将他们视为分隔符，因此这些字符必须进行转义。

- 非保留字符有：
  - A_Z, a_z,0_9
  - 连字符 ( -)、下划线 ( _)、句号 ( .)、感叹号 ( !)、波浪号 ( ~)、星号 ( *)、重音 ( ')、左括号 ( ()、右括号 ( ))

- 保留字符有：分号 ( ;) 斜线 ( /)、问号 ( ?)、冒号 ( :)、at 号 ( @)、和号 ( &)、等号 ( =)、加号 ( +)、美元符号 ( $)、百分号 ( %) 和逗号 ( ) ,)

该内置包包含以下函数：

<table>
<tr>
<th>函数</th>
<th>描述</th>
</tr>
<tr>
<td><a href="#URLESCAPE">ESCAPE</a></td>
<td>对URL进行转义。</td>
</tr>
<tr>
<td><a href="#URLUNESCAPE">UNESCAPE</a></td>
<td>对已转义的URL进行反转义。</td>
</tr>
</table>


**注意事项**

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。
- 目前支持的字符集有gbk，gb2312，utf-8。
- 功能基于python3及插件plpython3u，需要有python3环境（目前测试通过的python版本是python3.7和python3.8），python3 编译时指定--enable-shared。
- 若需要启用tls安全认证，需要安装openssl及openssl-devel，且python3编译时指定。--with-ssl-default-suites=openssl，如安装完在python3命令行执行import ssl成功，则说明安装ssl模块成功。
  例如：./configure --prefix=/usr/local/python3.8 --enable-shared --with-ssl-default-suites=openssl

**示例**

1、安装python3。

2、添加环境变量。

- PYTHONHOME：python3安装路径
- LD_LIBRARY_PATH：增加python3安装路径下的lib目录
- PATH：增加python3安装路径bin目录

3、创建并切换至兼容模式为Oracle的数据库db_oracle。

```
CREATE DATABASE db_oracle dbcompatibility='A';    
\c db_oracle
```

4、创建plpython3u扩展。

```
create extension plpython3u;
```

5、调用utl_url.escape函数对URL进行转义。

```
select utl_url.escape('https://www.baidu.com/参数/=+/',false,'gb2312');
```

6、调用utl_url.unescape函数对URL进行反转义。

```
select utl_url.unescape('https://www.baidu.com/%B2%CE%CA%FD/=+/','gb2312');
```

#### ESCAPE函数<a id="URLESCAPE"></a>

**语法格式**

```
UTL_URL.ESCAPE (
   url                   IN VARCHAR2 CHARACTER SET ANY_CS,
   escape_reserved_chars IN BOOLEAN  DEFAULT  FALSE,
   url_charset           IN VARCHAR2 DEFAULT  utl_http.body_charset)
 RETURN VARCHAR2;
```

**参数说明**

- url：原始URL。
- escape_reserved_chars：是否转义保留字符，true为转义，默认为false。
- url_charset：定需转换的字符设置为目标字符集。默认值是UTL_HTTP包的当前正文字符集，默认ISO-8859-1。


#### UNESCAPE函数<a id="URLUNESCAPE"></a>

**语法格式**

```
UTL_URL.UNESCAPE (
   url            IN VARCHAR2 CHARACTER SET ANY_CS,
   url_charset    IN VARCHAR2 DEFAULT utl_http.body_charset)
 RETURN VARCHAR2;
```

**参数说明**

- url：需要反转义的URL。
- url_charset：定需转换的字符设置为目标字符集。