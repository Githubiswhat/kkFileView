##### record

**record类型的变量**

创建一个record变量的方式：

定义一个record类型 ，然后使用该类型来声明一个变量。

**语法**

record类型的语法参见下图。

<div align="left"><img src="image/record类型语法.jpg" style="zoom:50%")</div>  

 

对以上语法格式的解释如下：

- record_type：声明的类型名称。

- field：record类型中的成员名称。

- datatype：record类型中成员的类型。

- expression：设置默认值的表达式。

<div align="left"><img src="image/image1.png" style="zoom:25%")</div>    

在Vastbase中：

- record类型变量的赋值支持：
  - 在函数或存储过程的声明阶段，声明一个record类型，并且可以在该类型中定义成员变量。
  - 一个record变量到另一个record变量的赋值。
  - SELECT INTO和FETCH向一个record类型的变量中赋值。
  - 将一个NULL值赋值给一个record变量。

- 不支持INSERT和UPDATE语句使用record变量进行插入数据和更新数据。

- 如果成员有复合类型，在声明阶段不支持指定默认值，该行为同声明阶段的变量一样。

**示例**

1、创建测试表。

```
CREATE TABLE emp_rec(empno numeric(4,0),ename character varying(10), job character varying(9), mgr numeric(4,0),hiredate timestamp(0) without time zone,sal numeric(7,2),comm numeric(7,2),deptno numeric(2,0));
```

2、查看表定义。

```
\d emp_rec 
```

结果返回如下

```
        Table "public.emp_rec" 
 Column  |        Type        | Modifiers  
----------+--------------------------------+----------- 
 empno   | numeric(4,0)          | not null 
 ename   | character varying(10)      |  
 job    | character varying(9)      |  
 mgr    | numeric(4,0)          |  
 hiredate | timestamp(0) without time zone |  
 sal    | numeric(7,2)          |  
 comm   | numeric(7,2)          |  
 deptno  | numeric(2,0)          |  
```

3、演示在存储过程中对数组进行操作。 

```
CREATE OR REPLACE FUNCTION regress_record(p_w VARCHAR2) 
RETURNS 
VARCHAR2  AS $$ 
DECLARE 

type rec_type is record (name  varchar2(100), epno int); -- 声明一个record类型. 
employer rec_type; 

type rec_type1 is record (name  emp_rec.ename%type, epno int not null :=10); --使用%type声明record类型 
employer1 rec_type1; 

  type rec_type2 is record ( 				--声明带有默认值的record类型 
     name varchar2 not null := 'SCOTT',  
     epno int not null :=10); 
  employer2 rec_type2; 
  CURSOR C1 IS  select ename,empno from emp_rec order by 1 limit 1; 

BEGIN 

   employer.name := 'WARD'; 					 --对一个record类型的变量的成员赋值。 
   employer.epno = 18; 
   raise info 'employer name: % , epno:%', employer.name, employer.epno; 
   
   employer1 := employer; 						 --将一个record类型的变量赋值给另一个变量。 
   raise info 'employer1 name: % , epno: %',employer1.name, employer1.epno; 

   employer1 := NULL; 							  --将一个record类型变量赋值为NULL。 
   raise info 'employer1 name: % , epno: %',employer1.name, employer1.epno; 

   raise info 'employer2 name: % ,epno: %', employer2.name, employer2.epno; 	--获取record变量的默认值。

   for employer in select ename,empno from emp_rec order by 1  limit 1  		 --在for循环中使用record变量 
     loop  
       raise info 'employer name: % , epno: %', employer.name, employer.epno; 
     end loop;       

   select ename,empno  into employer2 from emp_rec order by 1 limit 1; 	--在select into 中使用record变量。
   raise info 'employer name: % , epno: %', employer2.name, employer2.epno;       

   OPEN C1; 				--在cursor中使用record变量。 
   FETCH C1 INTO employer2; 
   raise info 'employer name: % , epno: %', employer2.name, employer2.epno; 
   CLOSE C1;     
   RETURN employer.name; 
END; 
$$ 
LANGUAGE plpgsql; 
```

4、调用该存储过程。 

```
CALL regress_record('abc'); 
```

结果显示如下：

```
INFO:  employer name: WARD , epno:18
INFO:  employer1 name: WARD , epno: 18
INFO:  employer1 name: <NULL> , epno: <NULL>
INFO:  employer2 name: SCOTT ,epno: 10
INFO:  employer name: <NULL> , epno: <NULL>
INFO:  employer name: <NULL> , epno: <NULL>

 regress_record 

(1 row)
```

5、删除存储过程。 

```
DROP PROCEDURE regress_record;
```

