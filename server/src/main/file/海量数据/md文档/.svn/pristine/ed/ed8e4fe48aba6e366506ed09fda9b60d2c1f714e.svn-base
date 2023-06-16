### UTL_SMTP

**功能描述**

该内置包提供了发送邮件的功能。该内置包包含以下函数：

<table>
<tr>
<th>
函数
</td>
<th>
描述
</td>
</tr>
<tr>
<td>
<a href="#open_connection">OPNE_CONNECTION</a>
</td>
<td>
与SMTP服务器建立连接。
</td>
</tr>
<tr>
<td>
<a href="#auth">AUTH</a>
</td>
<td>
向SMTP服务器发送AUTH命令进行身份验证。
</tr>
<tr>
<td>
<a href="#helo">HELO</a>
</td>
<td>
使用helo命令与SMTP服务器进行初始化握手。
</td>
</tr>
<tr>
<td>
<a href="#mail">MAIL</a>
</td>
<td>
设置与SMTP服务器发送的邮箱地址。
</td>
</tr>
<tr>
<td>
<a href="#rcpt">RCPT</a>
</td>
<td>
指定接收邮箱地址。
</td>
</tr>
<tr>
<td>
<a href="#open_data">OPNE_DATA</a>
</td>
<td>
发送DATA命令，后面调用write_data写入要发送的文本信息。
</td>
</tr>
<tr>
<td>
<a href="#write_data">WRITE_DATA</a>
</td>
<td>
发送邮件文本内容，重复调用可以将数据附加到内容中。
</td>
</tr>
<tr>
<td>
<a href="#close_data">CLOSE_DATA</a>
</td>
<td>
结束发送电子邮件。
</td>
</tr>
<tr>
<td>
<a href="#quit">QUIT</a>
</td>
<td>
断开与SMTP服务器的连接。
</td>
</tr>
</table>

**注意事项**

- Vastbase G100 V2.2是基于plpython3u实现以上函数及存储过程，完成发送邮件的功能。

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

**兼容性**

Vastbase G100 V2.2版本与Oracle 11g R2版本相比，不支持的函数有：command、command_replies、date、ehlo、help、noop、starttls、vrfy、write_raw_data。

**使用流程**

1、安装python3。

由于该功能基于python3(python3.8)的环境，且带有libpython3.8.so库文件（编译时指定--enable-shared可生成）。

若需要启用tls安全认证，需要安装openssl及openssl-devel，且python3编译时指定--with-ssl-default-suites=openssl，如安装完在python3命令行执行import ssl成功，则说明安装ssl模块成功。

注：麒麟系统import ssl如出现symbol SSLv3_method not defined的错误，是openssl的版本不支持SSLv3。可在python3源码修改Mudule/_ssl.c中的SSLv3_method()为DTLS_method()，保存后重新编译python3即可。

2、添加如下环境变量。

- PYTHONHOME：python3安装路径
- LD_LIBRARY_PATH：增加python3安装路径下的lib目录
- PATH：增加python3安装路径bin目录

3、创建plython3u扩展。

```
--切换到目标DB
\c targetDB
--创建plpython3u扩展
vastbase=# create extension plpython3u;
--查看到内置包相关函数信息
\df utl_smtp.*
```

4、调用函数发送邮件。

```
--创建发送邮件函数：（v_sender填写企业邮箱发送邮箱地址，v_passwd填写邮箱密码）
CREATE OR REPLACE FUNCTION send_mail(recipient varchar, subject varchar, message varchar) returns void as
$$
declare
v_mailhost varchar := '119.147.3.176';
v_sender varchar := 'xxxx@vastdata.com.cn';
v_passwd varchar := 'xxxx';
v_conn utl_smtp.connection;
v_msg varchar;
crlf varchar(2) := CHR(10);
v_ret varchar;
err_context text;
begin
v_conn := utl_smtp.open_connection(v_mailhost, 587, 1000, true);
perform utl_smtp.auth(v_conn, v_sender, v_passwd, 'CRAM-MD5 PLAIN LOGIN');
perform utl_smtp.helo(v_conn, v_mailhost);
perform utl_smtp.mail(v_conn, v_sender);
perform utl_smtp.rcpt(v_conn, recipient);
v_msg := 'From: ' || v_sender || crlf ||
'To: ' || recipient || crlf ||
'Subject: ' || subject || crlf ||
crlf || message;
raise notice '%', v_msg;
perform utl_smtp.open_data(v_conn);
perform utl_smtp.write_data(v_conn, v_msg);
perform utl_smtp.close_data(v_conn);
perform utl_smtp.quit(v_conn);
exception
when others then
GET STACKED DIAGNOSTICS err_context = PG_EXCEPTION_CONTEXT;
RAISE INFO 'Error Name:%',SQLERRM;
RAISE INFO 'Error State:%', SQLSTATE;
RAISE INFO 'Error Context:%', err_context;
end;
$$ LANGUAGE plpgsql;

--调用send_mail函数发送邮件：（第一个参数填写接收邮箱地址）
select send_mail('xxx@qq.com', 'utl_smtp测试', '您好，这是测试邮件！');
```

#### open_connection函数<a id="open_connection"></a>

**语法格式**

```
UTL_SMTP.OPEN_CONNECTION (
   host                            IN  VARCHAR2, 
   port                            IN  PLS_INTEGER DEFAULT 25, 
   c                               OUT connection, 
   tx_timeout                      IN  PLS_INTEGER DEFAULT NULL,
   wallet_path                     IN  VARCHAR2 DEFAULT NULL,
   wallet_password                 IN  VARCHAR2 DEFAULT NULL, 
   secure_connection_before_smtp   IN  BOOLEAN DEFAULT FALSE,
   secure_host                     IN  VARCHAR2 DEFAULT NULL)
 RETURN connection; 
```

**参数说明**

- host：SMTP服务器地址。

- port：SMTP服务器端口，默认25。

- c：SMTP连接。

- tx_timeout：UTL_SMTP包在连接中的读或写操作超时等待的时间（秒），默认为NULL。

- wallet_path：指定客户端钱包，默认为NULL（接口预留，暂不实现）。

- wallet_password：打开钱包的密码，默认为NULL（接口预留，暂不实现）。

- secure_connection_before_smtp：是否在smtp通信之间建立SSL/TLS的安全连接，默认为false。

- secure_host：使用安全连接的证书域名，默认为NULL（接口预留，暂不实现）

#### auth函数<a id="auth"></a>

**语法格式**

```
UTL_SMTP.AUTH (
   c          IN OUT NOCOPY connection,
   username   IN            VARCHAR2,
   password   IN            VARCHAR2,
   schemes    IN            VARCHAR2 DEFAULT NON_CLEARTEXT_PASSWORD_SCHEMES);
```

**参数说明**

- c：open_connection返回的smtp连接。
- username：发送邮件的用户名
- password：发送邮件用户的密码
- schemas：身份验证方案，默认为CRAM-MD5

#### helo函数<a id="helo"></a>

**语法格式**

```
UTL_SMTP.HELO (
   c       IN OUT NOCOPY   connection, 
   domain  IN              VARCHAR2);
```

**参数说明**

- c：open_connection返回的smtp连接。
- domain：发送邮件的域名或者主机地址。

#### mail函数<a id="mail"></a>

**语法格式**

```
UTL_SMTP.MAIL (
   c           IN OUT NOCOPY   connection, 
   sender      IN              VARCHAR2, 
   parameters  IN              VARCHAR2 DEFAULT NULL);
```

**参数说明**

- c：open_connection返回的smtp连接。
- sender：发送邮件的邮箱地址
- parameters：定义邮箱命令的附加参数，默认为NULL（接口预留，暂不实现）

#### rcpt函数<a id="rcpt"></a>

**语法格式**

```
UTL_SMTP.RCPT (
   c           IN OUT NOCOPY     connection,
   recipient   IN                VARCHAR2,
   parameters  IN                VARCHAR2 DEFAULT NULL);
```

**参数说明**

- c：open_connection返回的smtp连接。
- recipient：接收电子邮件的地址。
- parameters：定义邮箱命令的附加参数，默认为NULL（接口预留，暂不实现）。


#### open_data函数<a id="open_data"></a>

**语法格式**

```
UTL_SMTP.OPEN_DATA (
   c     IN OUT NOCOPY connection);
```

**参数说明**

c：open_connection返回的smtp连接。

#### write_data函数<a id="write_data"></a>


**语法格式**

```
UTL_SMTP.WRITE_DATA (
   c     IN OUT NOCOPY connection, 
   data  IN VARCHAR2 CHARACTER SET ANY_CS);
```

**参数说明**

- c：open_connection返回的smtp连接。
- data：要发送消息文本，包括标题，采用RFC822格式。

#### quit函数<a id="quit"></a>

**语法格式**

```
UTL_SMTP.QUIT (
   c  IN OUT NOCOPY connection);
```

**参数说明**

c：open_connection返回的smtp连接。

#### close_data函数<a id="close_data"></a>

**语法格式**

```
UTL_SMTP.CLOSE_DATA (
   c     IN OUT NOCOPY connection);
```

**参数说明**
c：open_connection返回的smtp连接。
