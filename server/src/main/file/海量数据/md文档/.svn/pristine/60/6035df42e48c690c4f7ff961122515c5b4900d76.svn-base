## 游标（CURSOR）语法

**功能描述**

游标（cursor）是数据库系统在内存中开设的一个数据缓冲区，存放SQL语句的执行结果。

**语法格式**

- Oracle类型和PG类型语法：

```
name [ [ NO ] SCROLL ] CURSOR [ ( arguments ) ] FOR query;
```

- Vastbase类型语法：

```
CURSOR name [ [ NO ] SCROLL ] [ ( arguments ) ] FOR query;
```

**参数说明**

- name：游标变量的名称。

- [NO] SCROLL：如果指定了SCROLL特性，则表示游标的光标可以向后滚动（回滚）。如果使

  用 NO SCROLL则游标不能回滚。如果两个关键字都没有出现，则是否允许反向回迁取决于查询。

- CURSOR：关键字，指明变量是游标类型。

- [ ( arguments ) ]：如果指定了arguments，则是一个逗号分隔的对名称数据类型列表，起到类

似绑定变量作用。

- FOR：关键字，表示后面的语句为游标定义的内容，可以被IS取代。
- query：查询语句，包含各种关联查询和子查询。

**注意事项**

- 该功能仅在数据库兼容模式为Oracle类型和PG类型时能够使用（即创建DB时DBCOMPATIBILITY='A'或者'PG'），其他类型数据库兼容模式保持原有语法不做改动。
- 在Vastbase使用游标（CURSOR）语法时，仅需要在Oracle类型和PG类型语法基础上调换变量名与类型关键字的顺序即可在Vastbase中实现该功能。

**示例**

- Oracle兼容模式的数据库中。

```
CREATE OR REPLACE procedure a()  as
     CURSOR c_id FOR  select * from tt; 
BEGIN
    select 2*3 from dual;
END;
/
```

- PG兼容模式的数据库中。

```
CREATE OR REPLACE procedure a()  as
     CURSOR c_id FOR  select * from tt; 
BEGIN
    select 2*3 from dual;
 END;
/
```
