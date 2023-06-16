###  UTL_HTTP

**功能描述**

使用UTL_HTTP包可通过SQL和过程语言调用超文本传输协议HTTP，允许通过HTTP访问internet上的数据。该内置包包含以下函数：

<table>
<tr>
<th>函数</th>
<th>描述</th>
</tr>
<tr>
    <td><a href="#HTTPSET_TRANSFER_TIMEOUT">SET_TRANSFER_TIMEOUT</a></td>
<td>设置UTL_HTTP从WEB服务器或代理服务器读取HTTP响应的超时值。</td>
</tr>
<tr>
<td><a href="#HTTPREQUEST">REQUEST</a></td>
<td>从指定的URL请求检索数据。可直接用于SQL查询。</td>
</tr>
<tr>
    <td><a href="#HTTPBEGIN_REQUEST">BEGIN_REQUEST</a></td>
<td>开始一个新的HTTP请求。</td>
</tr>
<tr>
    <td><a href="#HTTPSET_HEADER">SET_HEADER</a></td>
<td>用于设置http请求头。</td>
</tr>
<tr>
<td><a href="#HTTPGET_RESPONSE">GET_RESPONSE</a></td>
<td>用于读取http响应。</td>
</tr>
<tr>
<td><a href="#HTTPSET_BODY_CHATSET">SET_BODY_CHATSET</a></td>
<td> Content-Type标头中未指定字符集时，设置请求体的字符集。</td>
</tr>
<tr>
<td><a href="#HTTPWRITE_TEXT">WRITE_TEXT</a></td>
<td>在HTTP请求体中写入一些文本数据。</td>
</tr>
<tr>
<td><a href="#HTTPREAD_LINE">READ_LINE</a></td>
<td>以文本形式读取http响应正文，直到到达尾行。</td>
</tr>
<tr>
<td><a href="#HTTPEND_RESPONSE">END_RESPONSE</a></td>
<td>结束HTTP响应，完成HTTP请求和响应。</td>
</tr>
<tr>
    <td><a href="#HTTPEND_REQUEST">END_REQUEST</a></td>
<td>结束HTTP请求。</td>
</tr>
<tr>
<td><a href="#HTTPGET_DETAILED_SQLERRM">GET_DETAILED_SQLERRM</a></td>
<td>检索最后引发的异常的详细SQLERRM。</td>
</tr>
</table>


**类型说明**

**req类型**

**功能描述**

req用于接受http的request请求。

**语法格式**

```
TYPE req RECORD(
url  VARCHAR2(32767),
method  VARCHAR2(64),
http_version   VARCHAR2(64),
);
```

**参数说明**

- url：http请求的URL，在BEGIN_REQUEST进行设置。

- method：请求url的方法，在BEGIN_REQUEST进行设置。

- http_version：发送请求的http协议版本。


**resq类型**

**功能描述**

使用resq的记录表示http的响应。

**语法格式**

```
TYPE resq RECORD(
status_code  PLS_INTEGER,
reason_phrase  VARCHAR2(256),
http_version   VARCHAR2(64),
);
```

**参数说明**

- url：http请求的URL，在BEGIN_REQUEST进行设置。
- method：请求url的方法，在BEGIN_REQUEST进行设置。
- http_version：发送请求的http协议版本。

**使用流程**

1. 定义http.rep和http.resp变量。
2. 设置http超时时间。
3. 进行url编码，设置header，设置body字符集。
4. 获取request请求。
5. 获取response。
6. 异常处理。

**注意事项**

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。
- 使用该功能需要创建plpython3u插件。

**兼容性**

Vastbase G100 V2.2版本与Oracle 11g R2版本相比，仅支持本文档介绍的函数，该内置包的其他函数暂不支持。

**示例**

1、系统已经安装python3环境。 

2、创建并切换至兼容模式为Oracle的数据库db_oracle。

```
CREATE DATABASE db_oracle dbcompatibility='A';    
\c db_oracle
```

3、创建plpython3u插件。

```
create extension plpython3u;
```

4、使用UTL_HTTP包创建函数。

```
CREATE OR REPLACE FUNCTION FN_HTTP_GET (v_url VARCHAR2)
RETURN VARCHAR2
AS
BEGIN
DECLARE
req UTL_HTTP.REQ;
resp UTL_HTTP.RESP;
res record;
value varchar2;
v_text text;
BEGIN
v_text := '';
BEGIN
req := UTL_HTTP.BEGIN_REQUEST( url => v_url, method => 'GET' );
UTL_HTTP.SET_BODY_CHARSET(req, 'UTF-8');
UTL_HTTP.SET_HEADER(req, 'Content-Type', 'text/html');
resp := UTL_HTTP.GET_RESPONSE( req );
LOOP
UTL_HTTP.READ_LINE( resp, value, TRUE );
--value:=res.data;
v_text := v_text || value;
END LOOP;
UTL_HTTP.END_RESPONSE( resp );
EXCEPTION
WHEN "utl_http.end_of_body" THEN
UTL_HTTP.END_RESPONSE( resp );
WHEN OTHERS THEN
RAISE EXCEPTION '(%)', SQLERRM;
UTL_HTTP.END_RESPONSE(resp);
END;
return v_text;
END;
END;
```

5、删除插件。

```
drop extension plpython3u;
```

6、调用函数。

```
select FN_HTTP_GET('http://172.16.19.135/reviewboard/r/ ');
```

7、重新创建拓展。

```
create extension plpython3u;
```

8、调用函数。

```
select FN_HTTP_GET('http://172.16.19.135/reviewboard/r/ ');
```

#### SET_TRANSFER_TIMEOUT<a id="HTTPSET_TRANSFER_TIMEOUT"></a>

**语法格式**

```
UTL_HTTP.SET_TRANSFER_TIMEOUT(timeout);
```

**参数说明**

timeout：网络传输超时值（以秒为单位）。

#### REQUEST<a id="HTTPREQUEST"></a>

**语法格式**

```
UTL_HTTP.REQUEST(url);
```

**参数说明**

url：请求的url地址。

#### BEGIN_REQUEST<a id="HTTPBEGIN_REQUEST"></a>

**语法格式**

```
UTL_HTTP.BEGIN_REQUEST(r);
```

**参数说明**

r：http请求的URL。

#### SET_HEADER<a id="HTTPSET_HEADER"></a>

**语法格式**

```
UTL_HTTP.SET_HEADER(r,name,value);
```

**参数说明**

- r：http请求。
- name：http的header名称。
- value：http的header值。

#### GET_RESPONSE<a id="HTTPGET_RESPONSE"></a>

**语法格式**

```
UTL_HTTP.GET_RESPONSE(r);
```

**参数说明**

r：http响应。

#### SET_BODY_CHATSET<a id="HTTPSET_BODY_CHATSET"></a>

**语法格式**

```
UTL_HTTP.SET_BODY_CHATSET(r,charset);
```

**参数说明**

- r：http响应。

- charset：正文的字符集。

#### WRITE_TEXT<a id="HTTPWRITE_TEXT"></a>

**语法格式**

```
UTL_HTTP.WRITE_TEXT(r,date);
```

**参数说明**

- r：http请求。
- date：需要在HTTP请求body正文中发送的文本数据。。

#### READ_LINE<a id="HTTPREAD_LINE"></a>

**语法格式**

```
UTL_HTTP.READ_LINE(r,date,crlf);
```

**参数说明**

- r：http响应。
- date：http的body文本。
- crlf：是否删除换行符。

#### END_RESPONSE<a id="HTTPEND_RESPONSE"></a>

**语法格式**

```
UTL_HTTP.END_RESPONSE(r);
```

**参数说明**

r：http响应。

#### END_REQUEST<a id="HTTPEND_REQUEST"></a>

**语法格式**

```
UTL_HTTP.END_REQUEST(r);
```

**参数说明**

r：http请求。

#### GET_DETAILED_SQLERRM<a id="HTTPGET_DETAILED_SQLERRM"></a>

**语法格式**

```
UTL_HTTP.GET_DETAILED_SQLERRM 
RETURN VARCHAR2;
```
