### DBMS_OUTPUT

**功能描述**

DBMS_OUTPUT内置包用于提供在存储过程或包内对外发送信息的功能，该内置包包含以下函数：

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
enable
</td>
<td>
用来使dbms_output生效。
</td>
</tr>
<tr>
<td>
disable
</td>
<td>
用来使dbms_output失效。
</td>
</tr>
<tr>
<td>
put
</td>
<td>
将数据写入到缓存中，等到put_line/new_line时一起输出。
</td>
</tr>
<tr>
<td>
put_line
</td>
<td>
将数据写入到缓存中，并将缓存中的数据一起输出。
</td>
</tr>
<tr>
<td>
new_line
</td>
<td>
向put写入的行末尾添加终结符，如果缓存中有数据将其输出。
</td>
</tr>
<tr>
<td>
get_line
</td>
<td>
从缓存中读取一行数据。
</td>
</tr>
<tr>
<td>
get_lines
</td>
<td>
从缓存中读取多行数据。
</td>
</tr>
</table>

说明：serveroutput 为on 时允许将dbms_output.put_line的输出信息输出至vsql的命令界面的屏幕上。使用以下语句设置参数：

```
SET serveroutput =on 
```

serveroutput默认值是off  可以通过配置.psqlrc文件设置缺省值。

**注意事项**

该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。


**示例**

1、创建并切换至兼容模式为ORACLE的数据库db_oracle。

```
CREATE DATABASE db_oracle dbcompatibility=’B’;     
\c db_oracle
```

2、创建测试表并插入数据。

```
CREATE TABLE emp (
 empno NUMBER(4) ,
 ename VARCHAR2(10),
 job VARCHAR2(9),
 mgr NUMBER(4),
 hiredate DATE,
 sal NUMBER(7,2) CONSTRAINT emp_sal_ck CHECK (sal > 0),
 comm NUMBER(7,2));
INSERT INTO emp VALUES (7369,'SMITH','CLERK',7902,'17-DEC-80',800,NULL);
INSERT INTO emp VALUES (7499,'ALLEN','SALESMAN',7698,'20-FEB-81',1600,300);
INSERT INTO emp VALUES (7521,'WARD','SALESMAN',7698,'22-FEB-81',1250,500);
INSERT INTO emp VALUES (7566,'JONES','MANAGER',7839,'02-APR-81',2975,NULL);
INSERT INTO emp VALUES (7654,'MARTIN','SALESMAN',7698,'28-SEP-81',1250,1400);
INSERT INTO emp VALUES (7698,'BLAKE','MANAGER',7839,'01-MAY-81',2850,NULL);
INSERT INTO emp VALUES (7782,'CLARK','MANAGER',7839,'09-JUN-81',2450,NULL);
INSERT INTO emp VALUES (7788,'SCOTT','ANALYST',7566,'19-APR-87',3000,NULL);
INSERT INTO emp VALUES (7839,'KING','PRESIDENT',NULL,'17-NOV-81',5000,NULL);
INSERT INTO emp VALUES (7844,'TURNER','SALESMAN',7698,'08-SEP-81',1500,0);
INSERT INTO emp VALUES (7876,'ADAMS','CLERK',7788,'23-MAY-87',1100,NULL);
INSERT INTO emp VALUES (7900,'JAMES','CLERK',7698,'03-DEC-81',950,NULL);
INSERT INTO emp VALUES (7902,'FORD','ANALYST',7566,'03-DEC-81',3000,NULL);
INSERT INTO emp VALUES (7934,'MILLER','CLERK',7782,'23-JAN-82',1300,NULL);
```

3、创建并调用存储过程。

```
CREATE OR REPLACE PROCEDURE list_emp()
 IS
 v_empno NUMBER(4);
 v_ename VARCHAR2(10);
 cursor emp_cur  is  SELECT empno, ename FROM emp ORDER BY empno;
BEGIN
 OPEN emp_cur;
 DBMS_OUTPUT.PUT_LINE('EMPNO ENAME');
 DBMS_OUTPUT.PUT_LINE('----- -------');
 LOOP
 FETCH emp_cur INTO v_empno, v_ename;
 EXIT WHEN emp_cur%NOTFOUND;
 DBMS_OUTPUT.PUT_LINE(v_empno || ' ' || v_ename);
 END LOOP;
 CLOSE emp_cur;
END;
/

vastbase=# call list_emp();
```

返回结果为：

```
EMPNO ENAME
----- -------
7369 SMITH
7499 ALLEN
7521 WARD
7566 JONES
7654 MARTIN
7698 BLAKE
7782 CLARK
7788 SCOTT
7839 KING
7844 TURNER
7876 ADAMS
7900 JAMES
7902 FORD
7934 MILLER
 list_emp
----------

(1 row)
```