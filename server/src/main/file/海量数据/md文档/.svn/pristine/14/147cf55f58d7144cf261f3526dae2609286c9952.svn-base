### DBMS_ALERT

**功能说明**

DBMS_ALERT用于生成并传递数据库警报信息，是一种在数据库内部和应用程序间通信的一种方式。

DBMS_ALERT产生的警报是基于事务的，这意味着如果产生警报的事务未被提交，则等待该警报的session会话将不会收到该警报。一个正在等待警报的session将在数据库中被阻塞。该内置包包含以下函数：

<table>
<tr>
<th>函数</th>
<th>描述</th>
</tr>
<tr>
 <td><a href="#ALTERREGISTER">REGISTER</a></td>
<td>注册当前会话接受指定的警报。</td>
</tr>
<tr>
<td><a href="#ALTERREMOVE">REMOVE</a></td>
<td>去除对指定警报的注册。</td>
</tr>
<tr>
<td><a href="#ALTERREMOVEALL">REMOVEALL</a></td>
<td>去除对所有警报的注册。</td>
</tr>
<tr>
<td><a href="#ALTERSIGNAL">SIGNAL</a></td>
<td>为指定警报发出信号。</td>
</tr>
<tr>
<td><a href="#ALTERWAITANY">WAITANY</a></td>
<td>等待任何已注册的警报出现。</td>
</tr>
<tr>
<td><a href="#ALTERWAITONE">WAITONE</a></td>
<td>等待指定的警报出现。</td>
</tr>
</table>


**注意事项**

无。

**兼容性**

完全兼容。

**示例**

1、去除alert1警报的注册。

```
select DBMS_ALERT.REMOVE('alert1');
```

2、会话1:接受进程(客户端接收)。

```
select DBMS_ALERT.REGISTER('alert1');
```

3、切换至会话2，发送进程（服务器端发出）。

```
BEGIN;
select DBMS_ALERT.SIGNAL('alert1', 'hello,this is sending process!');
commit;
```

4、切换至会话1:接受进程(客户端接收)。

```
select DBMS_ALERT.WAITONE('alert1',100);
```

返回结果为:

```
"hello,this is sending process!",0
```

#### REGISTER<a id="ALTERREGISTER"></a>

**语法格式**

```
DBMS_ALERT.REGISTER (
   name      IN  VARCHAR2);
```

**参数说明**

name：text类型，指定的警报的名字。

#### REMOVE<a id="ALTERREGISTER"></a>

**语法格式**

```
DBMS_ALERT.REMOVE (
   name  IN  VARCHAR2);
```

**参数说明**

name：text类型，将要被移除的警报的名字（大小写不敏感）。

#### REMOVEALL<a id="ALTERREMOVEALL"></a>

**语法格式**

```
DBMS_ALERT.REMOVEALL;
```

#### SIGNAL<a id="ALTERSIGNAL"></a>

**语法格式**

```
DBMS_ALERT.SIGNAL (
   name     IN  VARCHAR2,
   message  IN  VARCHAR2);
```

**参数说明**

- name：text类型，指定的警报的名字。
- message：text类型，本次警报携带的消息字符串，将被传递给执行wait的会话。

#### WAITANY<a id="ALTERWAITANY"></a>

**语法格式**

```
DBMS_ALERT.WAITANY (
   name      OUT  VARCHAR2,
   message   OUT  VARCHAR2,
   status    OUT  INTEGER,
   timeout   IN   NUMBER DEFAULT MAXWAIT);
```

**参数说明**

- name：text类型，等待的警报的名字。
- message：text类型，收到的警报携带的消息字符串。如果收到的警报在WAITANY之前被SIGNAL产生了多次，那么收到的message消息字符串是最近的一次SIGNAL产生的，之前的SIGNAL产生的message消息将被忽略。。
- status：integer类型，0表示由收到警报返回，1表示由于超时返回。
- timeout：警报等待的最长时间。缺省默认值为86400000（1000天）。

#### WAITONE<a id="ALTERWAITONE"></a>

**语法格式**

```
DBMS_ALERT.WAITONE (
   name      IN   VARCHAR2,
   message   OUT  VARCHAR2,
   status    OUT  INTEGER,
   timeout   IN   NUMBER DEFAULT MAXWAIT);
```

**参数说明**

- name：text类型，等待的指定的警报的名字。
- message：text类型，收到的警报携带的消息字符串。如果收到的警报在WAITANY之前被SIGNAL产生了多次，那么收到的message消息字符串是最近的一次SIGNAL产生的，之前的SIGNAL产生的message消息将被忽略。。
- status：integer类型，，0表示由收到警报返回，1表示由于超时返回。
- timeout：警报等待的最长时间。缺省默认值为86400000（1000天）。