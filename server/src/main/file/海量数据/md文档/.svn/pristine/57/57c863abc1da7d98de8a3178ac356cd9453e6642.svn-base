替换PL/pgSQL-TYPE … IS RECORD

# RECORD类型

## 功能描述

RECORD类型（集合类型）将多个基本数据类型变量组合成一个整体，作为一个复合数据类型使用，可以看做是一种用户自定义数据类型。

## 语法格式

```sql
TYPE record_name IS RECORD  (成员名称 数据类型 [NOT NULL] :=[默认值][表达式],..);
```

## 参数说明

- **record_name**

  自定义的数据类型名称。

- **数据类型**

  - 任意Vastbase G100兼容的数据类型
  - 行类型%rowtype
  - 复制类型%type
  - nested record（在record类型中嵌套record类型）

## 注意事项

  无。

## 示例

**示例1：**在函数中定义record类型：数值类型。

1、创建一个函数定义record类型。

```sql
CREATE OR REPLACE FUNCTION func_record() RETURNS void AS $body$
DECLARE
        TYPE test_record IS RECORD(
        col_int int);
    v_record test_record;
BEGIN
    v_record.col_int := 1;
     
  RAISE NOTICE 'col_int:%',v_record.col_int;
END;
$body$ LANGUAGE plpgsql;
```

2、调用函数。

```sql
select func_record();
```

返回结果为：

```sql
NOTICE:  col_int:1
CONTEXT:  referenced column: func_record
 func_record
-------------

(1 row)
```

**示例2：**nested record类型。

1、创建测试表并插入数据。

```sql
CREATE TABLE EMP(EMPNO NUMBER(4) NOT NULL,ENAME VARCHAR2(10),JOB VARCHAR2(9),MGR NUMBER(4),HIREDATE DATE,SAL NUMBER(7, 2),COMM NUMBER(7, 2),DEPTNO NUMBER(2));
INSERT INTO EMP VALUES (7369, 'SMITH', 'CLERK', 7902,TO_DATE('17-DEC-1980', 'DD-MON-YYYY'), 800, NULL, 20);
INSERT INTO EMP VALUES (7499, 'ALLEN', 'SALESMAN', 7698,TO_DATE('20-FEB-1981', 'DD-MON-YYYY'), 1600, 300, 30);
INSERT INTO EMP VALUES (7521, 'WARD', 'SALESMAN', 7698,TO_DATE('22-FEB-1981', 'DD-MON-YYYY'), 1250, 500, 30);

CREATE TABLE DEPT(DEPTNO NUMBER(2),DNAME VARCHAR2(14),LOC VARCHAR2(13));
INSERT INTO DEPT VALUES (10, 'ACCOUNTING', 'NEW YORK');
INSERT INTO DEPT VALUES (20, 'RESEARCH', 'DALLAS');
INSERT INTO DEPT VALUES (30, 'SALES', 'CHICAGO');
INSERT INTO DEPT VALUES (40, 'OPERATIONS', 'BOSTON ');
```

2、定义嵌套的record类型。

```sql
do language plpgsql $$  DECLARE
TYPE dept_type IS RECORD (
deptno    dept.deptno%TYPE := 80,    -- 定义默认值
dname    dept.dname%TYPE ,
loc    dept.loc%TYPE
) ;
TYPE emp_type IS RECORD (
empno    emp.empno%TYPE ,   --nested record
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

```sql
NOTICE:  雇员编号：7369, 姓名：SMITH, ，职位：CLERK, 雇佣日期：1980-12-17, 基本工资：800, 佣金：0
NOTICE:  部门编号：20, 名称：RESEARCH, 位置：DALLAS
ANONYMOUS BLOCK EXECUTE
```

**示例3：**INSERT语句中使用record类型。

1、创建测试表。

```sql
CREATE TABLE t_err (id int,col char(5));
```

2、定义record类型，并在insert语句中使用record类型。

```sql
do language plpgsql $$
DECLARE
TYPE test_record1 IS RECORD(
col_int int := 999,
col_text text := 'test'
);
v1 test_record1;
BEGIN
INSERT INTO t_err VALUES v1;
END;
$$;
```

3、查询表t_err中的数据。

```sql
select * from t_err;
```

返回结果如下，表示insert语句中插入record类型数据成功：

```sql
 id  |  col
-----+-------
 999 | test
(1 row)
```



  