# CREATE TYPE

211版本中该内容已经包含Oracle兼容性SQL语法中的：CREATE TYPE语句支持or replace和{is | as} object子句

使用该文档替换掉即可。

## 功能描述

Vastbase G100支持使用CREATE TYPE创建抽象数据类型，并支持使用create type body实现在create type中声明的方法。

对于创建的抽象对象的应用，支持创建对象表，即表的列属性是对象数据类型(抽象数据类型)。对象表分为行对象表和列对象表。

- 行对象表是指直接基于对象类型所建立的表，一个对象表的列代表了对象数据类型最顶的所有属性，即对象类型是子类型时，表的列就是包含了父类型和子类型的所有属性，每一个行记录就是一个对象数据类型的实例。
- 列对象表是指一张表中既有列是标量类型，也有列是对象数据类型。

create type还支持table集合和varray集合，语法如下：

```sql
create or replace type typname is/as VARRAY (size_limt) | table of datatype;
```


## 语法格式

### CREATE TYPE

定义抽象数据类型

```sql
CREATE [OR REPLACE] TYPE typname [FORCE] AS|IS OBJECT ({atrribute datatype [,]}{,element_spec})[NOT] {[FINAL|INSTANTIABLE]}};
```

定义子类型

```sql
CREATE [OR REPLACE] TYPE typname, [FORCE] UNDER supertype ({atrribute datatype [,] }{,element_spec})[NOT]{[FINAL|INSTANTIABLET]}};
```

### CREATE TYPE BODY

定义抽象数据类型体

```sql
CREATE [OR REPLACE] TYPE BODY typname {is|as} {proc_decl_in_type|func_decl_in_type|constructor_declaration|map_order_func_declaration} END；
```

proc_decl_in_type：创建抽象类型时声明的存储过程，声明语法如下：

```sql
PROCEDURE procname {[parameter_list]}{IS|AS} 存储过程体;
```

func_decl_in_type：创建抽象类型时声明的函数，声明语法如下：

```sql
FUNCTION funcname{[parameter_list]} RETURN datatype {IS|AS} 函数体;
```

constructor_declaration：构造函数，声明语法如下：

```sql
[FINAL] [INSTANTTABLE]CONSTRUCTOR FUNCTION datatype [([ SELF IN OUT datatype,] parameter datatype [,parameter datatype ]...)]RETURN SELF AS RESULT {IS|AS} 函数体;
```

map_order_func_declaration：构造函数，声明语法如下：

```sql
{MAP|ORDER}MEMBER FUNCTION funcname {[(parameter_list)]} RETURN datatype {IS|AS} 函数体;
```

### 创建对象表

```sql
CREATE TABLE tablename OF object_type;
```

## 参数说明

- **typname**

  抽象类型名称。

- **tablename**

  表名称。

- **object_type**

  创建表使用的类型名称，类型已存在。

- **create or replace**

  如果没有type则新建一个，如果已经存在，则删除后重新创建。

- **{is | as} object**

  表示创建对象类型的自定义type 。

- **atrribute**

	抽象数据类型中的成员变量名，抽象数据类型中至少包含一个属性，且属性必须唯一，不能与抽象数据类型中其他属性或者方法重名，子类型属性也不能与父类型属性或者方法重名。

- **datatype**

  处理抽象数据类型的相关方法。

  抽象数据类型的方法总共有三类：子程序方法、构造函数、排序函数。方法的声明语法如下：

  ```sql
  [{NOT]{[OVERRIDING]|FINAL|INSTANTIABLE]}] {subprogram_spec |constructor_spec|map_order_function_spec}
  ```

  - **[{NOT]{[OVERRIDING]|FINAL|INSTANTIABLE]}]** 

    继承子句。表示父类型与子类型关系的子句。

    - OVERRIDING：当子类型中定义一个与父类型中同名的member方法时，将覆盖父类型的方法。

      默认值为：NOT OVERRIDING，暂不支持该选项。

    - FINAL ：指定该类型中的这个方法不能被该类型的任何子类型方法覆盖。

      默认值：NOT FINAL。

    - INSTANTIABLE：指定抽象类型中该方法是否可以被实例化。

      默认值：所有的方法都可以被实例化，暂不支持该选项。

  - **subprogram_spec**

    子程序方法声明：子程序分为member方法和static方法，声明语法如下：

    ```sql
    [MEMBER|STATIC] function_spec|procedure_spec;
    ```

    - member方法：访问抽象数类型的实例中的数据类型的方法，该类型的方法有一个隐形的参数SELF，代表的是调用该方法的抽象数据类型的实例，member方法无论是否显示定义第一个参数为SELF，它的一个参数都是SELF，调用方式为：object_expression.method()，即抽象数据类型的实例.方法()。

      > <div align="left"><img src="image/image1.png" style="zoom:25%")</div>
      >
      > - 成员方法表示对象实例级别的数据调用。
      > - 成员方法的 self是内置参数，表示调用当前实例的对象。
      > - 成员方法的 self可以显示声明，但不是必须的。
      > - type中定义的对象，在成员方法中可以隐式 self方式的进行访问。

    - static 方法：与抽象数据类型相关的方法，static 方法没有隐形参数 SELF，因此在方法实现中不能使用抽象数据类型中访问特定实例的属性，调用方式为typname.method ()，即抽象数据类型.方法()。

      > <div align="left"><img src="image/image1.png" style="zoom:25%")</div>
      >
      > - 用于访问对象类型，能够在对象类型上执行全局操作，而不需要访问特定对象实例的数据，所以 static方法没有 self参数。
      > - static方法只能由对象类型调用，不能由对象实例调用。
      
    - function_spec：函数体。

    - procedure_spec：存储过程体。

  - **constructor_spec**

    构造函数声明。由于抽象数据类型不像标量数据类型一样可以直接赋值，因此需要使用构造函数对其中的属性进行赋值。声明方式如下:

    ```sql
    [FINAL][INSTANTIABLE]CONSTRUCTOR FUNCTION datatype[([ SELF IN OUT datatype,] parameter datatype [, parameter datatype ]... )] RETURN SELF AS RESULT;
    ```

    > <div align="left"><img src="image/img3.png" style="zoom:75%")</div>  
    >
    > - 该方法是用户自定义的构造函数，该类方法有一个隐形的参数 SELF(无论是否显示指定，这个参数都存在)，代表的是当前正在调用的抽象数据类型的实例，返回值是该抽象数据类型的实例。用户可以为一个抽象数据类型创建多个构造函数，只要这些构造函数的参数在参数顺序，个数，参数类型上不同即可。默认的构造函数需要传入全部属性的值。
    >
    > - INSTANTIABLE选项暂不支持。

  - **map_order_function_spec**

    排序函数是指定抽象数据类型的比较方法和比较规则，声明方法如下：

    ```sql
    {MAP|ORDER}MEMBER function_spec
    ```

    - MAP函数：按照用户自定义的比较方法和排序规则将多个对象实例排序。

      > <div align="left"><img src="image/image1.png" style="zoom:25%")</div>  
      >
      > - map 成员方法的 self可以显示声明，但不是必须的。
      >
      > - map成员方法在一个 type中只允许定义一个。
      > - map成员方法可以将单个实例的对象转化为标量数据类型(如CHAR或REAL)的值，具有预定义顺序的作用，方便进行比较。
      > - map成员方法只能定义 self参数，方法不能定义为其他任何参数。

    - ORDER函数：按照用户自定义的比较方法和规则将两个抽象数据类型对象进行比较大小。

      > <div align="left"><img src="image/image1.png" style="zoom:25%")</div>
      >
      > - order 成员方法和 map 成员方法不能同时在一个 type 中定义。
      > - order 成员方法在一个type 中只允许定义一个。
      > - order 成员方法必须定义一个 object type的输入参数，并且函数返回值类型必须为 integer数据类型。
      > - order 成员方法是比较两个 type实例的对象大小。

    当用户定义一个类型时，这两种函数只能2选1。

- **[NOT]{[FINAL|INSTANTIABLET]}**

  表示该类型是否可以继承。

  - [NOT] FINAL：表示该类型是否可以创建子类型。

    默认值：FINAL，不允许该类型创建子类型。

  - [NOT] INSTANTIABLET：表示此对象是否有任何实例，当前版本暂不支持该选项。

## **注意事项**

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。
- type 的继承，如果 type对象中有成员函数，成员函数暂不支持 overriding关键字修饰。

## 示例

**前置步骤：**创建并切换至兼容模式为Oracle的数据库db_oracle。

```sql
CREATE DATABASE db_oracle dbcompatibility='A';    
\c db_oracle
```

**示例1：**使用 create or replace type 定义抽象数据类型。

```sql
create or replace  type addresstype as object
(
province varchar(20),
city varchar(30),
street varchar(40)
);
```

**示例2：**继承子句。

1、创建父类型，使用not final选项。

```sql
create or replace  type parenttype as object
(
province varchar(20),
city varchar(30),
street varchar(40)
) not final;
```

2、创建子类型。

```sql
create or replace  type childrentype under parenttype
(
home varchar(20),
position varchar(30)
);
```

**示例3：**使用抽象数据类型创建表并插入数据。

1、创建抽象父类型。

```sql
create or replace  type parenttype as object
(
province varchar(20),
city varchar(30),
street varchar(40)
)not final;
```

2、创建抽象子类型。

```sql
create or replace  type childrentype under parenttype
(
home varchar(20),
position varchar(30)
);
```

3、创建表。

```sql
create table addresstable (id int,address childrentype);
```

4、插入数据。

```sql
insert into addresstable values(1,childrentype('广西省','桂林市','全州县','才湾镇','才湾村'));
```

5、查询数据。

```sql
select * from addresstable;
```

返回结果为：

```sql
 id |               address
----+--------------------------------------
  1 | (广西省,桂林市,全州县,才湾镇,才湾村)
(1 row)
```

**示例4：**使用type创建table集合类型。

1、创建抽象类型。

```sql
create type typa as object(a int,name varchar2(10),
constructor function typa(m_a int) return self as result
);
```

2、创建对象表并插入数据。

```sql
create table object_table of typa;
insert into object_table values(1,'nanjing');
```

3、查询对象表中数据。

```sql
select * from object_table;
```

返回结果为：

```sql
 a |  name
---+---------
 1 | nanjing
(1 row)
```

**示例5：**创建order成员方法。

```sql
create or replace type emp_type4 as object(
name varchar2(10),
birthdate date,
order member function compare(emp4 emp_type4) return int
);
```

返回结果如下，表示创建成功。

```sql
CREATE TYPE
```

**示例6：**使用type创建varray集合。

> <div align="left"><img src="image/image1.png" style="zoom:25%")</div>
>
> varry是变长数组类型，用于处理pl/sql集合的数据类型。

1、基于type创建varray集合。

```sql
create type phone_typ as object(
country_code varchar2(2),
area_code varchar2(3),
ph_number varchar2(7)
);
```

2、基于已创建的varray集合再次创建varray集合。

```sql
create type phone_varray_typ as varray(5) of phone_typ;
```

3、创建测试表。

```sql
create table dept_phone_list(
dept_no number(5),
phone_list phone_varray_typ
);
```

4、基于type的varray集合，使用构造函数方式插入数据。

```sql
insert into dept_phone_list values(
100,phone_varray_typ(phone_typ('01','650','5550123'),phone_typ('01','650','5550148'),phone_typ('01','650','5550192'))
);
```

5、查询数据。

```sql
select * from dept_phone_list;
```

返回结果为：

```sql
 dept_no |                         phone_list
---------+------------------------------------------------------------
     100 | {"(01,650,5550123)","(01,650,5550148)","(01,650,5550192)"}
(1 row)
```

