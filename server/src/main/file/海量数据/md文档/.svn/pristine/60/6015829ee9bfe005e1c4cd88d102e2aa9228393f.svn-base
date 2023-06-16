###  DBMS_PROFILER

**功能描述**

DBMS_PROFILER提供了一个接口，用于分析现有的PL/pgSQL应用程序并确定性能瓶颈。该内置包可以收集性能分析数据，用来提高性能或确定PL/pgSQL应用程序的代码覆盖率。

应用程序开发人员可以使用代码覆盖率数据来集中精力进行增量测试。使用该内置包的接口，可以为在会话中执行的所有命名库单元（package body、function、procedure、trigger、anonymous block）生成分析信息。该信息包括执行每行的总次数、执行该行所花费的总时间以及该行的特定执行所花费的最短和最长时间。分析信息存储在数据库表中，可查询数据、构建自定义报告（摘要报告、最热的行、代码覆盖率数据等）以及分析数据。该内置包包含以下函数：

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
START_PROFILER
</td>
<td>
在用户会话中启动事件探查器数据收集。启动一次数据收集就要START_PROFILER，中间可以暂停、继续、刷新数据。如果一直不STOP_PROFILER，收集器就一直在收集	plpgsql执行的性能数据；如果不执行START_PROFILER就执行STOP_PROFILER、	暂停、继续、刷新数据也不会出错，但没有实际数据收集。
</td>
</tr>
<tr>
<td>
STOP_PROFILER
</td>
<td>
停止用户会话中的探查器数据收集。此功能将会刷新会话中已收集的数据。
</td>
</tr>
<tr>
<td>
PAUSE_PROFILER
</td>
<td>
暂停用户会话中的探查器数据收集。
</td>
</tr>
<tr>
<td>
RESUME_PROFILER
</td>
<td>
恢复暂停状态中的探查器数据收集。
</td>
</tr>
<tr>
<td>
FLUSH_DATA
</td>
<td>
刷新在用户会话中收集的探查器数据。
</td>
</tr>
<tr>
<td>
ROLLUP_UNIT
</td>
<td>
计算对应runid和unit所花费的时间，并将总花费的时间刷新到PLSQL_PROFILER_UNITS表中的total_time字段中。
</td>
</tr>
<tr>
<td>
ROLLUP_RUN
</td>
<td>
统计指定runid收集器下所有unit所花费的时间，并将花费时间刷新到	PLSQL_PROFILER_UNITS表中的total_time字段中。
</td>
</tr>
</table>

**注意事项**

- 使用DBMS_PROFILER内置包前，需要先加载安装目录/share/postgresql目录下的proload.sql文件。加载命令如下（该步骤需要在目标库内部执行）：

```
\i ./local/vastbase/share/postgresql/proload.sql;
```

如果该命令执行报错，可以找到preload.sql文件手动执行preload.sql文件中命令创建收集器。收集器相关表说明如下：

<table>
<tr>
<th>
收集器相关表名
</td>
<th>
描述
</td>
</tr>
<tr>
<td>
plsql_profiler_runs
</td>
<td>
记录运行中的收集器信息。
</td>
</tr>
<tr>
<td>
plsql_profiler_units
</td>
<td>
有关运行中每个库单元的信息。
</td>
</tr>
<tr>
<td>
plsql_profiler_data
</td>
<td>
收集器收集的详细数据。
</td>
</tr>
<tr>
<td>
plsql_profiler_runnumber序列
</td>
<td>
生成唯一运行编号。
</td>
</tr>
</table>


- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

- 可以通过profiler.sql工具产生HTML格式的性能报表，具体使用方法见示例。

**兼容性**

完全兼容。

**示例**

1、创建并切换至兼容模式为Oracle的数据库db_oracle。

```
CREATE DATABASE db_oracle dbcompatibility='A';    
\c db_oracle
```

2、创建测试表。

```
create table tab_test(col int);
```

3、创建要统计PL/pgSQL性能的存储过程sp_test。

```
create or replace procedure sp_test
as
begin
for i in 1..100
loop
insert into tab_test values(1);
end loop;
end; 
/
```

4、创建要统计PL/pgSQL性能的存储过程sp_test2。

```
create or replace procedure sp_test2
as
begin
for i in 1..10000
loop
  insert into tab_test values(i);
end loop;
end;
/
```

5、统计sp_test的执行性能。

```
select dbms_profiler.start_profiler('sp');
```

6、调用存储过程sp_test,sp_test2

```
call sp_test();
select dbms_profiler.flush_data();
select dbms_profiler.pause_profiler();

call sp_test2();
select dbms_profiler.resume_profiler();
select dbms_profiler.stop_profiler();

select dbms_profiler.rollup_unit(1,1);
select dbms_profiler.rollup_run(1);
```

7. 查询结果。

```
select * from plsql_profiler_runs;
```

返回结果为：

```
runid | related_run | run_owner |          run_date          | run_comment | run_total_time | run_system_info | run_comment1 | spare1 
-------+-------------+-----------+----------------------------+-------------+----------------+-----------------+--------------+--------
     1 |           0 | vastbase  | 2022-06-28 01:58:56.578502 | sp          |           6668 |                 |              | 
(1 row)

select * from plsql_profiler_units;
 runid | unit_number | unit_type | unit_owner | unit_name |       unit_timestamp       | total_time | spare1 | spare2 
-------+-------------+-----------+------------+-----------+----------------------------+------------+--------+--------
     1 |           1 | PROCEDURE | vastbase   | sp_test   | 2022-06-28 01:59:02.830512 |       3334 |        |       
(1 row)

select * from plsql_profiler_data;
 runid | unit_number | line# | total_occur | total_time | min_time | max_time | spare1 | spare2 | spare3 | spare4 
-------+-------------+-------+-------------+------------+----------+----------+--------+--------+--------+--------
     1 |           1 |     4 |           1 |       1730 |     1730 |     1730 |        |        |        |       
     1 |           1 |     6 |         100 |       1604 |        9 |       26 |        |        |        |       
(2 rows)
```
