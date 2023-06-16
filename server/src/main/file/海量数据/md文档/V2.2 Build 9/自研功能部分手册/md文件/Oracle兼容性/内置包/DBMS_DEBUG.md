### DBMS_DEBUG

**功能描述**

DBMS_DEBUG内置包用于实现服务器端调试器，提供了一种调试服务器端PL/SQL程序单元的方法，调试时分为目标会话（the target session）和调试会话（the debug session），通过两个会话配合完成PL/SQL调试工作。

目标会话用来以调试模式运行代码，调试会话用来监督目标会话。在调试会话中通过使用初始化生成的会话id，结合执行响应的调试api接口与目标会话进行通信对PL/SQL程序单元进行调试。该内置包包含以下函数:

<table>
<tr>
<th>函数</th>
<th>描述</th>
</tr>
<tr>
    <td><a href="#INITIALIZE">INITIALIZE</a></td>
<td>初始化目标会话以进行调试。</td>
</tr>
<tr>
<td><a href="#DEBUG_ON">DEBUG_ON</a></td>
<td>使所有PL/SQL都在调试模式下运行。</td>
</tr>
<tr>
<td><a href="#DEBUG_OFF">DEBUG_OFF</a></td>
<td>通知目标会话不应再进行调试。</td>
</tr>
<tr>
<td><a href="#DEBUGEXECUTE">EXECUTE</a></td>
<td>在目标会话中执行一段SQL或PL/SQL。</td>
</tr>
</table>


**注意事项**

- 只有在INITIALIZE操作之后才能进行DEBUG_ON操作，然后才能进行PL/SQL操作。
- 在目标会话中如果有DBMS_DEBUG.DEBUG_ON已生效，再次执行时会提示已开启调试模式。
- 在目标会话中如果有DBMS_DEBUG.DEBUG_OFF已生效，不会进入阻塞状态。

**兼容性**

Vastbase G100 V2.2版本与Oracle 11g R2版本相比，支持的函数有：EXECUTE、INITIALIZE、DEBUG_ON、DEBUG_OFF,其他函数暂不支持。

**示例**

1、创建插件。

```
create extension pldbgapi;
```

2、调用debug_on函数。

```
exec dbms_debug.debug_on();
```

返回结果如下：

```
NOTICE:  Debugger is started successfully, you are SERVER now.
CONTEXT:  SQL statement "CALL dbms_debug.debug_on_inner($1,$2)"
PL/pgSQL function dbms_debug.debug_on(boolean,boolean) line 3 at PERFORM
 debug_on
----------

(1 row)
```

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>
>
> 该函数没有返回值，执行成功时会有Debugger is started successfully, you are Server now.的提示，没有procedure successfully completed的提示，Oracle的procedure successfully completed为执行过程执行成功时的的统一输出结果，这里与Oracle有差异。

3、调用execute函数。

```
select dbms_debug.execute('select * from pg_proc',-1,-1,'sds','sdsad');
```

返回结果为：

```
WARNING:  bind_results Only 0 or 1 is supported
CONTEXT:  referenced column: execute_inner
SQL statement "select dbms_debug.execute_inner($1, $2, $3, $4, $5)"
PL/pgSQL function dbms_debug.execute(varchar,integer,integer,dbms_debug_vc2coll,varchar) line 5 at SQL statement
referenced column: execute
 execute
---------
 t
(1 row)
```

4、调用dbms_debug.debug_off函数。

```
exec dbms_debug.debug_off();
```

####  INITIALIZE函数<a id="INITIALIZE"></a>


**语法格式**

```
DBMS DEBUG.INITIALIZE (
debug_session_id 	IN VARCHAR2    := NULL, 
diagnostics	 IN  BINARY_INTEGER := 0) 
RETURN VARCHAR2;
```

**参数说明**

- debug_session_id：会话ID的名称，如果为 NULL，则生成一个唯一的ID。
- diagnostics：指示是否将诊断信息输出，其中0为默认值，表示不诊断输出：1 为打印诊断信息。

#### DEBUG_ON函数<a id="DEBUG_ON"></a>


**语法格式**

```
DBMS DEBUG.DEBUG_ON (
no_client_side_plsql_engine  BOOLEAN := TRUE.
immediate BOOLEAN := FALSE); 
```

**参数说明**

- no_client_side_plsql_engine：默认值为true，除非从客户端PL/SQL引擎进行调试会话，否则应将其保留为默认值，
- immediate：如果值为TRUE，则解释器将立即将其自身切换到调试模式，而不是在调用期间以常规模式继续运行。


####  DEBUG_OFF函数<a id="DEBUG_OFF"></a>


**语法格式**

```
DBMS DEBUG.DEBUG_OFF;
```

#### EXECUTE函数<a id="DEBUGEXECUTE"></a>

**语法格式**

```
DBMS DEBUG.EXECUTE (
what			 IN VARCHAR2, 
frame# 		IN BINARY_INTEGER, 
bind_results IN BINARY_INTEGER,
results 		IN OUT NOCOPY dbms_debug_vc2coll,
errm 		IN OUT NOCOPY VARCHAR2); 
```

**参数说明**

- what：要执行的sql或pl/sgl 源。
- frame#：执行代码的上下文。目前仅支持-1(全局上下文)。
- bind_results：源是否要绑定到results以便从目标会话返回值：0=否;1=是。
- results：放置结果的集合(如果bind results不为0)，dbms_debug_vc2coll为集合类型，也写为sys.dbms_debug_vc2coll。
- errm：如果发生错误，显示错误信息，否则为null。