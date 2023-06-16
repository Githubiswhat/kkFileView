### DBMS_LOCK

**功能描述**
DBMS_LOCK包可以对数据库锁进行管理和控制。

比如某些开发程序，在高并发的情况下，必须控制好并发请求的运行时间和次序，
来保证处理数据的正确性和完整性。对于这种业务需求，可以使用内置包DBMS_LOCK，把需要做并发控制的参数加上锁，实现并发控制。
dbms_lock包提供了多种锁模式，各个模式都有自己的冲突列表，各个锁模式的含义及取值如下表：

<table>
<tr>
<th>名字</th>
<th>描述</th>
<th>值</th>
</tr>
<tr>
<td>nl_mode</td>
<td>NULL</td>
<td>1</td>
</tr>
<tr>
<td>ss_mode</td>
<td>Sub Shared，表示在一个对象的子部分获取共享锁</td>
<td>2</td>
</tr>
<tr>
<td>sx_mode</td>
<td>Sub Exclusive，表示在一个对象的子部分获取独占锁</td>
<td>3</td>
</tr>
<tr>
<td>s_mode</td>
<td>Shared，表示对整个对象获取共享锁</td>
<td>4</td>
<tr>
<td>ssx_mode</td>
<td>Shared Sub Exclusive，表示整个对象已有共享锁，但是还要对某些子部分获取独占锁</td>
<td>5</td>
<tr>
<td>x_mode</td>
<td>Exclusive，表示对整个对象获取独占锁</td>
<td>6</td>
</tr>
</table>


当要获取一个对象的锁时，如果这个对象的锁正被其他会话所持有，那么需要根据该对象的锁正被持有的模式以及当前要获取的锁的模式来判断本次是否能够成功获取，这就是不同锁模式的冲突。不同锁模式的冲突列表如下：

<table>
<tr>
<th>HELD MODE</th>
<th>GET NL</th>
<th>GET SS</th>
<th>GET SX</th>
<th>GET S</th>
<th>GET SSX</th>
<th>GET X</th>
</tr>
<tr>
<td>NL</td>
<td>Success</td>
<td>Success</td>
<td>Success</td>
<td>Success</td>
<td>Success</td>
<td>Success</td>
</tr>
<tr>
<td>SS</td>
<td>Success</td>
<td>Success</td>
<td>Success</td>
<td>Success</td>
<td>Success</td>
<td>Failed</td>
</tr>
<tr>
<td>SX</td>
<td>Success</td>
<td>Success</td>
<td>Success</td>
<td>Success</td>
<td>Failed</td>
<td>Failed</td>
</tr>
<tr>
<td>S</td>
<td>Success</td>
<td>Success</td>
<td>Failed</td>
<td>Success</td>
<td>Failed</td>
<td>Failed</td>
</tr>
<tr>
<td>SSX</td>
<td>Success</td>
<td>Success</td>
<td>Failed</td>
<td>Failed</td>
<td>Failed</td>
<td>Failed</td>
</tr>
<tr>
<td>X</td>
<td>Success</td>
<td>Failed</td>
<td>Failed</td>
<td>Failed</td>
<td>Failed</td>
<td>Failed</td>
</tr>
</table>

该内置包包含以下函数：

<table>
<tr>
<th>函数</th>
<th>描述</th>
</tr>
<tr>
 <td><a href="#ALLOCATE_UNIQUE">ALLOCATE_UNIQUE</a></td>
<td>为指定的锁名分配一个唯一的锁id。</td>
</tr>
<tr>
<td><a href="#CONVERT">CONVERT</a></td>
<td>锁模式的转换。</td>
</tr>
<tr>
<td><a href="#REQUEST">REQUEST</a></td>
<td>请求一个指定模式的锁。</td>
</tr>
<tr>
<td><a href="#RELEASE">RELEASE</a></td>
<td>用来显示释放由REQUEST函数占有的锁。</td>
</tr>
<tr>
<td><a href="#SLEEP">SLEEP</a></td>
<td>指定session睡眠时间。</td>
</tr>
</table>




**注意事项**

无。

**兼容性**

Vastbase G100 V2.2版本与Oracle 11g R2版本相比，不支持的函数有:ALLOCATE_UNIQUE_AUTONOMOUS

**示例**

1. 创建并切换至兼容模式为Oracle的数据库db_oracle。

```
CREATE DATABASE testoracle dbcompatibility='A';    
\c db_oracle
```

2. 创建函数并发控制锁模式nl_mode与sx_mode。

```
create or replace function lock_test(lock_mode int ,lock_expired int ,release_on_commit boolean) returns int as $$ --lock_mode取1到6整数
declare
v_ret int;
v_name varchar(100) :='test';
v_handle varchar(100);
begin
dbms_output.enable();
dbms_output.serveroutput(true);
dbms_output.put_line('----- session start -----');
select dbms_lock.allocate_unique(v_name,lock_expired) into v_handle;
v_ret :=dbms_lock.request(v_handle , lock_mode , lock_expired , release_on_commit); 
if v_ret <> 0 then
--获取锁失败
dbms_output.put_line('---lock request lock failed ---');
return v_ret;
else
dbms_output.put_line('---now request lock success ---');
end if;
--获取锁成功，此处添加请求业务逻辑
dbms_lock.sleep(lock_expired+20);
--执行完成，应该要释放掉锁
if v_ret = 0 then
v_ret := dbms_lock.release(v_handle);
end if;
return v_ret;
end;
$$ language plpgsql;
```

3. 会话1中执行 CALL lock_test(1,2,false)，切换至会话2同时执行CALL lock_test(3,20,false)。

```
 CALL lock_test(1,2,false);
 CALL  lock_test(3,20,false);
```

会话1和会话2可以同时获得锁，同时出现如下执行结果：

```
----- session start -----
---now request lock success ---
 lock_test
-----------
         0
(1 row)

```

4. 会话1执行 CALL  lock_test(1,2,true),切换至会话2同时执行CALL lock_test(3,20,true);

```
CALL  lock_test(1,2,true);
CALL lock_test(3,20,true);
```

会话1和会话2可以同时获得锁，同时出现如下执行结果：

```
----- session start -----
---now request lock success ---
 lock_test
-----------
         0
(1 row)

```

#### ALLOCATE_UNIQUE<a id="ALLPCATE_UNIQUE"></a>

**语法格式**

```
dbms_lock.allocate_unique(
    lockname IN VARCHAR2,
    lockhandle OUT VARCHAR2,
    expiration_secs IN INTEGER DEFAULT 864000
) RETURN INTEGER;
```

**参数说明**

- lockname：产生唯一的LockID。大小不超过128B，大小写敏感。不能以'ORA$'开头，这是为oracle提供的产品保留的。
- lockhandle：返回值，request，convert，release调用。
- expiration_secs：执行'allocate_unique'后，Clean Up的时间间隔。

#### CONVERT<a id="CONVERT"></a>

**语法格式**

```
dbms_lock.convert(
    lockhandle IN VARCHAR2,
    lockmode IN INTEGER,
    timeout IN NUMBER DEFAULT maxwait
) RETURN INTEGER;
```

**参数说明**

- lockname：生成的唯一ID的锁的名称。
- lockhandle：
- expiration_secs：

#### RELEASE<a id="RELEASE"></a>

**语法格式**

```
dbms_lock.release(
    lockhandle IN VARCHAR2
) RETURN INTEGER;

dbms_lock.release(
    id         IN INTEGER
) RETURN INTEGER;
```

**参数说明**

lockhandle，id：用户分配的锁定标识符。


#### REQUEST<a id="REQUEST"></a>

**语法格式**

```
dbms_lock.request(
    lockhandle IN VARCHAR2,
    lockmode IN INTEGER DEFAULT x_mode,
    timeout IN INTEGER DEFAULT maxwait,
    release_on_commit IN BOOLEAN DEFAULT FALSE
) RETURN INTEGER;
```

**参数说明**

- lockhandle，id：用户分配的锁定标识符，allocate_unique取得的handle。
- lockmode：请求的锁的模式。
- timeout：继续尝试授予锁定的秒数，如果在此时间段内无法授予锁，则调用返回值 1 ( timeout)。
- release_on_commit：将此参数设置为TRUE以释放提交或回滚时的锁定。否则，锁会一直持有，直到它被显式释放或会话结束。默认为FALSE。

#### SLEEP<a id="SLEEP"></a>

**语法格式**

```
dbms_lock.sleep(
    seconds IN NUMBER
);
```

**参数说明**

seconds：睡眠时间。