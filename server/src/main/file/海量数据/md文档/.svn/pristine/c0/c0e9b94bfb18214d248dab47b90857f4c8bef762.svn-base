### DBMS_APPLICATION_INFO

**功能描述**

DBMS_APPLICATION_INFO通过视图v\$SESSION来跟踪脚本运行情况的能力，该内置包允许应用程序在v$session上设置如下三个字段的值：

- client_info：客户端信息。
- Module：主程序名称，如包的名称。
- Action：程序包中的过程名。

以上三个字段的内容可以在视图V$SESSION中查看。该功能主要用于对各模块的性能跟踪和调试，系统管理员可按模块跟踪性能，还可以使用此信息来跟踪模块的资源使用情况。该内置包包含以下函数：

<table>
<tr>
<th>
函数
</th>
<th>
描述
</th>
</tr>
<tr>
<td>
READ_CLIENT_INFO
</td>
<td>
读取当前会话通过SET_CLIENT_INFO过程的最后一个信息值，即最后一次存储的客户端信息
</td>
</tr>
<tr>
<td>
READ_MODULE
</td>
<td>
读取当前会话的模块和操作字段信息。
</td>
</tr>
<tr>
<td>
SET_ACTION
</td>
<td>
用于设置当前会话下的当前模块下的操作名称。
</td>
</tr>
<tr>
<td>
SET_CLIENT_INFO
</td>
<td>
用于设置当前会话有关客户端应用程序的其他信息。
</td>
</tr>
<tr>
<td>
SET_MODUL
</td>
<td>
用于设置当前模块的名称。
</td>
</tr>
</table>


**注意事项**

该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

**兼容性**

Vastbase G100 V2.2版本与Oracle 11g R2版本相比，不支持的函数有：SET_SESSION_LONGOPS。


**示例**

1、创建并切换至兼容模式为Oracle的数据库db_oracle。

```
CREATE DATABASE db_oracle dbcompatibility='A';    
\c db_oracle
```

2、创建函数。

```
CREATE OR REPLACE FUNCTION test_dbms_application_info RETURNS  VOID AS 
$$
DECLARE
result1 text:='test';
v_module text;
v_action text;
BEGIN
    dbms_application_info.set_client_info('client A');
    select * into result1 from dbms_application_info.read_client_info();
    dbms_application_info.set_module('module A','action A');
    dbms_application_info.set_action('action B');
    select * into v_module,v_action from dbms_application_info.read_module();
    raise notice 'result_read_client_info is %',result1;
    raise notice 'result_read_module is % and %',v_module,v_action;
END;
$$
LANGUAGE plpgsql;
```

3、调用函数。

```
select test_dbms_application_info();
```

返回结果为：

```
NOTICE:  result_read_client_info is client A
CONTEXT:  referenced column: test_dbms_application_info
NOTICE:  result_read_module is module A and action B
CONTEXT:  referenced column: test_dbms_application_info
 test_dbms_application_info
----------------------------

(1 row)
```

