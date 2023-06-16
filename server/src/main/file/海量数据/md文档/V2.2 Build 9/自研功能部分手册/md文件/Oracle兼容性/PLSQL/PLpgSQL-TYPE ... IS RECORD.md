###  PL/pgSQL-TYPE ... IS RECORD

**功能描述**

Vastbase G100 V2.2在PL/pgSQL-TYPE... IS RECORD(...)中支持嵌套定义和使用RECORD类型。

RECORD类型（记录类型）将多个基本数据类型变量组合成一个整体，作为一个复合数据类型使用，可以看做是一种用户自定义数据类型。

**语法格式**

```
TYPE 类型名称 IS RECORD  (成员名称 数据类型 [NOT NULL] :=[默认值][表达式],..);
```

**参数说明**

数据类型为：

- RECORD类型。
- 任意SQL兼容的数据类型（varchar、varchar2、char、int、number……）。
- 行类型table_name%ROWTYPE。
- 复制类型（Copying Types）variable%TYPE。
- 支持nested record类型，nested record指在record类型中嵌套record类型。

**注意事项**

无。

**示例**

1、预先创建测试表并插入数据。

```
CREATE TABLE EMP
(
EMPNO NUMBER(4) NOT NULL,
ENAME VARCHAR2(10),
JOB VARCHAR2(9),
MGR NUMBER(4),
HIREDATE DATE,
SAL NUMBER(7, 2),
COMM NUMBER(7, 2),
DEPTNO NUMBER(2)
);
INSERT INTO EMP VALUES (7369, 'SMITH', 'CLERK', 7902,TO_DATE('17-DEC-1980', 'DD-MON-YYYY'), 800, NULL, 20);
INSERT INTO EMP VALUES (7499, 'ALLEN', 'SALESMAN', 7698,TO_DATE('20-FEB-1981', 'DD-MON-YYYY'), 1600, 300, 30);
INSERT INTO EMP VALUES (7521, 'WARD', 'SALESMAN', 7698,TO_DATE('22-FEB-1981', 'DD-MON-YYYY'), 1250, 500, 30);

CREATE TABLE DEPT
(    DEPTNO NUMBER(2),
DNAME VARCHAR2(14),
LOC VARCHAR2(13)
);
INSERT INTO DEPT VALUES (10, 'ACCOUNTING', 'NEW YORK');
INSERT INTO DEPT VALUES (20, 'RESEARCH', 'DALLAS');
INSERT INTO DEPT VALUES (30, 'SALES', 'CHICAGO');
INSERT INTO DEPT VALUES (40, 'OPERATIONS', 'BOSTON ');
```

2、定义和使用嵌套的记录类型1。

```
do language plpgsql $$  DECLARE
TYPE dept_type IS RECORD (
deptno    dept.deptno%TYPE := 80,    -- 定义默认值
dname    dept.dname%TYPE ,
loc    dept.loc%TYPE
) ;
TYPE emp_type IS RECORD (
empno    emp.empno%TYPE ,
ename    emp.ename%TYPE ,
job    emp.job%TYPE ,
hiredate    emp.hiredate%TYPE ,
sal    emp.sal%TYPE ,
comm    emp.comm%TYPE ,
dept    dept_type
) ;
v_emp    emp_type ;
BEGIN
SELECT e.empno,e.ename,e.job,e.hiredate,e.sal,e.comm,d.deptno,d.dname,d.loc INTO
v_emp.empno,v_emp.ename,v_emp.job,v_emp.hiredate,v_emp.sal,v_emp.comm,
v_emp.dept.deptno,v_emp.dept.dname,v_emp.dept.loc
FROM emp e, dept d WHERE e.deptno=d.deptno AND e.empno=7369 ;
RAISE notice '雇员编号：%, 姓名：%, ，职位：%, 雇佣日期：%, 基本工资：%, 佣金：%', v_emp.empno, v_emp.ename, v_emp.job, TO_CHAR(v_emp.hiredate,'yyyy-mm-dd'), v_emp.sal, NVL(v_emp.comm,0); 
RAISE notice '部门编号：%, 名称：%, 位置：%', v_emp.dept.deptno, v_emp.dept.dname, v_emp.dept.loc;
END ;
$$;
```

返回结果为：

```
NOTICE:  雇员编号：7369, 姓名：SMITH, ，职位：CLERK, 雇佣日期：1980-12-17, 基本工资：800, 佣金：0
NOTICE:  部门编号：20, 名称：RESEARCH, 位置：DALLAS
ANONYMOUS BLOCK EXECUTE
```

3、定义和使用嵌套的记录类型2。

```
do language plpgsql $$
DECLARE
TYPE dept_type IS RECORD(
deptno dept.deptno%TYPE := 80,
dname dept.dname%TYPE,
loc dept.loc%TYPE
);
TYPE emp_type IS RECORD(
empno emp.empno%TYPE,
ename emp.ename%TYPE,
job emp.job%TYPE,
hiredate emp.hiredate%TYPE,
sal emp.sal%TYPE,
comm emp.comm%TYPE,
dept dept_type
); 
v_emp emp_type;
v_dept dept_type;
BEGIN
v_emp.dept.deptno := 90;
v_emp.dept.dname := 'ACCOUNTING2';
v_emp.dept.loc := 'NEW YORK';
v_dept := v_emp.dept;
RAISE notice '部门编号：%，名称：%，位置：%',v_dept.deptno,v_dept.dname,v_dept.loc;
END;
$$;
```

返回结果为：

```
NOTICE:  部门编号：90，名称：ACCOUNTING2，位置：NEW YORK
ANONYMOUS BLOCK EXECUTE
```
