### DBMS_JOB

**功能描述**

DBMS_JOB包用于安排和管理作业队列，通过使用作业，可以使数据库定期执行特定的任务。该内置包中包含以下函数：

<table >
<tr>
<th>函数</th>
<th>描述</th>
</tr>
<tr>
 <td><a href="#SUBMIT">SUBMIT</a></td>
<td>打开一个cursor，并返回该cursor的id。</td>
</tr>
<tr>
<td><a href="#RUN">RUN</a></td>
<td>运行一次指定的任务。</td>
</tr>
<tr>
<td> <a href="#REMOVE">REMOVE</a></td>
<td>移除一个定时任务。</td>
</tr>
<tr>
<td><a href="#BROKEN">BROKEN</a></td>
<td>停止或启动一个定时任务。</td>
</tr>
</table>


**注意事项**

无。

**兼容性**


Vastbase G100 V2.2版本与Oracle 11g R2版本相比，不支持的函数有：CHANGE、INSTANCE、INTERVAL、NEXT_DATE、USER_EXPORT、WHAT。

**示例**

1、设置 enable_prevent_job_task_startup参数为off（即job线程不能启动）。

```
ALTER SYSTEM SET enable_prevent_job_task_startup TO off;
```

2、创建测试表并插入数据。

```
drop table if exists t_sub;
create table t_sub(insert_date timestamp);
```

3、创建插入数据的定时任务。

```
select dbms_job.submit(1,'insert into t_sub values(sysdate);',sysdate,'''1min''::interval');
select * from t_sub;
```

返回结果为：

```
     insert_date
---------------------
 2022-07-06 09:14:39
(1 row)

```

4、等待1分钟之后，查询t_sub。

```
select * from t_sub;
```

返回结果如下，则表示定时任务创建成功：

```
     insert_date
---------------------
 2022-07-06 09:14:39
 2022-07-06 09:15:39
(2 rows)


```

5、运行正在运行的任务。

```
select dbms_job.run(1);
```

6、移除正在运行的任务：

```
select dbms_job.remove(1);
```

#### SUBMIT函数<a id="SUBMIT"></a>

**语法格式**

```
DBMS JOB.SUBMIT (
job      OUT BINARY_INTEGER. 
what     IN VARCHAR2, 
next_date IN DATE DEFAULT sysdate
interval IN VARCHAR2 DEFAULT 'null'
no_parse IN BOOLEAN DEFAULT FALSE,
instance IN BINARY INTEGER DEFAULT any_instance,
force IN BOOLEAN DEFAULT FALSE); 
```

**参数说明**

- job：输出参数，输出正在运行的作业编号。
- what：指定需要运行的存储过程。
- next_date：下一次job 运行的时间，默认为sysdate。
- interval：时间间隔，默认为 null。
- no_parse：标记参数。设置为 true 表示数据库会在首次运行该作业时解析与该作业相关联的过程，默认为 false 。
- instance：提交作业后，指定可以运行该作业的实例(支持该参数传入，但不校验并忽略该参数)。
- force：该参数设置为true 时表示任何正整数都可被接受为作业实例，默认为false则指定实例必须是运行着的(支持该参数传入，但不校验并忽略该参数)。


####  RUN函数<a id="RUN"></a>


**语法格式**

```
DBMS JOB.RUN (
job      IN BINARY_INTEGER. 
force IN BOOLEAN DEFAULT FALSE); 
```

**参数说明**

- job：需要运行的作业编号。
- force：该参数设置为true 时表示任何实例环境与在进程中与运行的作业无关，如果设置为false，则只能在指定实例中运行作业(支持该参数传入，但不校验并忽略该参数)。


####  REMOVE函数<a id="REMOVE"></a>

**语法格式**

```
DBMS JOB.RUN (
job     IN BINARY_INTEGER;
```

**参数说明**

job：需要删除的作业编号。

#### BROKEN函数<a id="BROKEN"></a>


**语法格式**

```
DBMS JOB.RUN (
job     IN BINARY_INTEGER,
Broken  IN BOOLEAN,
next_date IN DATE DEFAULT sysdate);
```

**参数说明**

- job：作业编号。
- broken：boolean类型，设置为true表示停止或者禁用作业任务，false则表示不中断（broken）作业任务。
- next_date：任务作业运行的下一个日期。
