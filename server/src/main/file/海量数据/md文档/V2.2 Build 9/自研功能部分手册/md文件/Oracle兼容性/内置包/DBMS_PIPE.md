###  DBMS_PIPE

**功能描述**

dbms_pipe包用于在同一个实例中，通过一个管道来实现会话内或会话之间的消息传递。该内置包包含以下函数：

<table>
<tr>
<th>函数</th>
<th>描述</th>
</tr>
<tr>
    <td> <a href="#PIPECREATE_PIPE">CREATE_PIPE</a></td>
<td>使用指定的名称显示地创建了一个公有或私有的管道。</td>
</tr>
<tr>
<td> <a href="#PIPENEXT_ITEM_TYPE">NEXT_ITEM_TYPE</a></td>
<td>返回消息缓存中下一个数据成员的数据类型。</td>
</tr>
<tr>
<td><a href="#PIPEPACK_MESSAGE">PACK_MESSAGE</a></td>
<td>在本地缓存中构建消息。</td>
</tr>
<tr>
<td> <a href="#PIPEPURGE">PURGE</a></td>
<td>清除指定的管道中的消息。</td>
</tr>
<tr>
<td>  <a href="#PIPERECEIVE_MESSAGE">RECEIVE_MESSAGE</a></td>
<td>删除指定的管道。</td>
</tr>
<tr>
    <td><a href="#PIPEREMOVE_PIPE">REMOVE_PIPE</a></td>
<td>在本地缓存中构建消息。</td>
</tr>
<tr>
<td><a href="#PIPERESET_BUFFER">RESET_BUFFER</a></td>
<td>清除本地缓存中的内容。</td>
</tr>
<tr>
<td><a href="#PIPESEND_MESSAGE">SEND_MESSAGE</a></td>
<td>从会话的本地消息缓冲区中将一条消息发送到指定的管道中。</td>
</tr>
<tr>
<td><a href="#PIPEUNIQUE_SESSION_NAME">UNIQUE_SESSION_NAME</a></td>
<td>返回当前会话的一个唯一名称。</td>
</tr>
<tr>
<td><a href="#PIPEUNPACK_MESSAGE">UNPACK_MESSAGE</a></td>
<td>从本地缓存中获取消息。</td>
</tr>
<tr>
<td><a href="#PIPELIST_PIPES">LIST_PIPES</a></td>
<td>列出所有管道。</td>
</tr>
<tr>
<td>视图DB_PIPES</td>
<td>用于查询数据库中所有的管道。</td>
</tr>
</table>


**注意事项**

该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

**兼容性**

完全兼容。

**示例**

- **CREATE_PIPE**

创建公有管道

```
CREATE FUNCTION test_create_pipe() RETURNS VOID AS
$$
BEGIN
    PERFORM dbms_pipe.create_pipe('message');
END;
$$
LANGUAGE plpgsql;
```

创建私有管道

```
CREATE FUNCTION test_create_pipe() RETURNS VOID AS
$$
BEGIN
    PERFORM dbms_pipe.create_pipe('message', 32, true);
END;
$$
LANGUAGE plpgsql;
```

- **NEXT_ITEM_TYPE**

```
--会话1
CREATE FUNCTION test_next_item_type() RETURNS VOID AS
$$
DECLARE
    v_number NUMBER :=123;
    v_varchar TEXT:= 'Character data';
    status INTEGER;
BEGIN
    dbms_pipe.pack_message(v_number);
    dbms_pipe.pack_message(v_varchar);
    status := dbms_pipe.send_message('datatypes');
    dbms_output.put_line('send_message status:' || status);
END;
$$
LANGUAGE plpgsql;
--会话2
CREATE FUNCTION test_next_item_type() RETURNS VOID AS
$$
DECLARE
    v_number NUMBER;
    v_varchar TEXT;
    status INTEGER;
BEGIN
    status := dbms_pipe.receive_message('datatypes');
    dbms_output.put_line('receive_message status:' || status);
    --number item
    status := dbms_pipe.next_item_type();
    dbms_output.put_line('next_item_type:' || status);
    v_number := dbms_pipe.unpack_message_number();
    dbms_output.put_line('number item:' || v_number);
    --varchar item
    status := dbms_pipe.next_item_type();
    dbms_output.put_line('next_item_type:' || status);
    v_varchar := dbms_pipe.unpack_message_text();
    dbms_output.put_line('varchar item:' || v_varchar);
    --no data
    status := dbms_pipe.next_item_type();
    dbms_output.put_line('next_item_type:' || status);
END;
$$
LANGUAGE plpgsql;
```

- **PACK_MESSAGE**

```
CREATE FUNCTION test_pack_message() RETURNS VOID AS
$$
DECLARE
    v_number NUMBER :=123;
    v_varchar TEXT:= 'Character data';
    status INTEGER;
BEGIN
    dbms_pipe.pack_message(v_number);
    dbms_pipe.pack_message(v_varchar);
    status := dbms_pipe.send_message('datatypes');
    dbms_output.put_line('send_message status:' || status);
END;
$$
LANGUAGE plpgsql;
```

- **PURGE**

```
--会话1
CREATE FUNCTION test_purge() RETURNS VOID AS
$$
DECLARE
    status INTEGER;
BEGIN
    dbms_pipe.pack_message('message 1');
    status := dbms_pipe.send_message('pipe');
    dbms_output.put_line('send_message status:' || status);

    dbms_pipe.pack_message('message 2');
    status := dbms_pipe.send_message('pipe');
    dbms_output.put_line('send_message status:' || status);
END;
$$
LANGUAGE plpgsql;
--会话2
CREATE FUNCTION test_purge() RETURNS VOID AS
$$
DECLARE
    v_item TEXT;
    status INTEGER;
BEGIN
    status := dbms_pipe.receive_message('pipe', 1);
    dbms_output.put_line('receive_message status:' || status);
    v_item := dbms_pipe.unpack_message_text();
    dbms_output.put_line('Item:' || status);
END;
$$
LANGUAGE plpgsql;
--删除管道中的剩余的message 2
SELECT dbms_pipe.purge('pipe');
```

- **RECEIVE_MESSAGE**

```
--会话1
CREATE FUNCTION test_receive_message() RETURNS VOID AS
$$
DECLARE
    status INTEGER;
BEGIN
    dbms_pipe.pack_message('message');
    status := dbms_pipe.send_message('pipe');
    dbms_output.put_line('send_message status:' || status);
END;
$$
LANGUAGE plpgsql;
--会话2
CREATE FUNCTION test_receive_message() RETURNS VOID AS
$$
DECLARE
    v_item TEXT;
    status INTEGER;
BEGIN
    status := dbms_pipe.receive_message('pipe', 1);
    dbms_output.put_line('receive_message status:' || status);
    v_item := dbms_pipe.unpack_message_text();
    dbms_output.put_line('Item:' || status);
END;
$$
LANGUAGE plpgsql;
```

- **REMOVE_PIPE**

```
--会话1
CREATE FUNCTION test_remove_pipe() RETURNS VOID AS
$$
BEGIN
    PERFORM dbms_pipe.create_pipe('pipe');

    dbms_pipe.pack_message('message 1');
    status := dbms_pipe.send_message('pipe');
dbms_output.put_line('send_message status:' || status);

    dbms_pipe.pack_message('message 2');
    status := dbms_pipe.send_message('pipe');
dbms_output.put_line('send_message status:' || status);
END;
$$
LANGUAGE plpgsql;
--会话2
CREATE FUNCTION test_remove_pipe() RETURNS VOID AS
$$
DECLARE
    v_item TEXT;
    status INTEGER;
BEGIN
    status := dbms_pipe.receive_message('pipe', 1);
    dbms_output.put_line('receive_message status:' || status);
    v_item := dbms_pipe.unpack_message_text();
    dbms_output.put_line('Item:' || status);
$$
LANGUAGE plpgsql;
--删除管道，包括剩余的message 2
SELECT dbms_pipe.remove_pipe('pipe');
```

- **RESET_BUFFER**

```
$$
BEGIN
    PERFORM dbms_pipe.create_pipe('pipe');

    dbms_pipe.reset_buffer();

    dbms_pipe.pack_message('message');
    status := dbms_pipe.send_message('pipe');
    dbms_output.put_line('send_message status:' || status);
END;
$$
LANGUAGE plpgsql;
```

- **SEND_MESSAGE**

```
CREATE FUNCTION test_send_message() RETURNS VOID AS
$$
BEGIN
    PERFORM dbms_pipe.create_pipe('pipe');

    dbms_pipe.pack_message('message');
    status := dbms_pipe.send_message('pipe');
    dbms_output.put_line('send_message status:' || status);
END;
$$
LANGUAGE plpgsql;
```

- **UNIQUE_SESSION_NAME**

```
CREATE FUNCTION test_unique_session_name() RETURNS VOID AS
$$
DECLARE
    v_session TEXT;
BEGIN
    v_session := dbms_pipe.unique_session_name();
    dbms_output.put_line('unique_session_name:' || status);
END;
$$
LANGUAGE plpgsql;
```

- **UNPACK_MESSAGE**

```
--会话1
CREATE FUNCTION test_unpack_message() RETURNS VOID AS
$$
BEGIN
    PERFORM dbms_pipe.create_pipe('pipe');

    dbms_pipe.pack_message('message');
    status := dbms_pipe.send_message('pipe');
    dbms_output.put_line('send_message status:' || status);
END;
$$
LANGUAGE plpgsql;
--会话2
CREATE FUNCTION test_unpack_message() RETURNS VOID AS
$$
DECLARE
    v_item TEXT;
    status INTEGER;
BEGIN
    status := dbms_pipe.receive_message('pipe', 1);
    dbms_output.put_line('receive_message status:' || status);
    v_item := dbms_pipe.unpack_message_text();
    dbms_output.put_line('Item:' || status);
END;
$$
LANGUAGE plpgsql;
```

- **LIST_PIPES**

```
CREATE FUNCTION test_list_pipes() RETURNS VOID AS
$$
BEGIN
    PERFORM dbms_pipe.create_pipe('pipe1');

    dbms_pipe.pack_message('message');
    status := dbms_pipe.send_message('pipe2');
    dbms_output.put_line('send_message status:' || status);
END;
$$
LANGUAGE plpgsql;

--查询所有管道信息
SELECT name, items, "limit", private, owner 
FROM dbms_pipe.list_pipes() 
AS (name varchar, items integer, size integer, "limit" integer, private bool, owner varchar);
```

- **视图DB_PIPES**

```
select * from dbms_pipe.db_pipes;
```

#### CREATE_PIPE<a id="PIPECREATE_PIPE"></a>

**语法格式**

```
DBMS_PIPE.CREATE_PIPE (
   pipename     IN VARCHAR2,
   maxpipesize  IN INTEGER DEFAULT 8192,
   private      IN BOOLEAN DEFAULT TRUE)
RETURN INTEGER;
```

**参数说明**

- pipename： 你创建的管道名称。
- maxpipesize：   管道允许的最大大小，以字节为单位。
- private ：使用默认值TRUE创建私有管道。调用时可以隐式创建公共管道SEND_MESSAGE。

#### NEXT_ITEM_TYPE<a id="PIPENEXT_ITEM_TYPE"></a>

**语法格式**

```
DBMS_PIPE.NEXT_ITEM_TYPE 
  RETURN INTEGER;
```

#### PACK_MESSAGE<a id="PIPEPACK_MESSAGE"></a>

**语法格式**

```
DBMS_PIPE.PACK_MESSAGE (
item  IN  VARCHAR2|NCHAR|
NUMBER|DATE|RAW|ROWID);
```

**参数说明**

item：要打包到本地消息缓冲区的项目。

#### PURGE<a id="PIPEPURGE"></a>

**语法格式**

```
DBMS_PIPE.PURGE (
   pipename  IN  VARCHAR2);
```

**参数说明**

pipename： 要删除消息的管道名称。

#### RECEIVE_MESSAGE<a id="PIPERECEIVE_MESSAGE"></a>

**语法格式**

```
DBMS_PIPE.RECEIVE_MESSAGE (
   pipename     IN VARCHAR2,
   timeout      IN INTEGER      DEFAULT maxwait)
RETURN INTEGER;
```

**参数说明**

- pipename： 接收消息的管道名称。
- timeout：等待消息的时间，以秒为单位。

#### UNIQUE_SESSION_NAME<a id="PIPEUNIQUE_SESSION_NAME"></a>

**语法格式**

```
DBMS_PIPE.UNIQUE_SESSION_NAME 
  RETURN VARCHAR2;
```

#### UNPACK_MESSAGE<a id="PIPEUNPACK_MESSAGE"></a>

**语法格式**

```
DBMS_PIPE.UNPACK_MESSAGE (
item  OUT  VARCHAR2|NCHAR|NUMBER|DATE|RAW|ROWID);
```

**参数说明**

item：从本地消息缓冲区接收下一个解包项目的参数

#### LIST_PIPES<a id="PIPELIST_PIPES"></a>

**语法格式**

```
DBMS_PIPE.LIST_PIPES() ;
```
