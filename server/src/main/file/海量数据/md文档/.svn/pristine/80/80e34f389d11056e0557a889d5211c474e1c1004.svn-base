####  SQLERRM函数

**功能描述**

  sqlerrm函数的主要功能是根据入参错误号匹配RAISE_APPLICATION_ERROR(error_code, message)预定义函数定义的错误信息。

**前提条件**

该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

**语法格式**

```
SQLERRM( error_code number)
```

**参数说明**

error_code：错误编号。

**使用场景**

- 当传入的错误编号在-20000到-20999时候，输出RAISE_APPLICATION_ERROR(error_code,  message)预定义函数的错误信息。

- 传入的错误编号不在-20000到-20999时候，输出Vastbase自身的错误信息。

**示例**

如果是-20000到-20999，那么raise_application_error定义的错误信息，进行输出对应结果。

1、创建兼容Oracle的数据库db_oracle。

```
CREATE DATABASE db_oracle DBCOMPATIBILITY='A';
\c db_oracle
```

2、创建存储过程

```
drop procedure if exists exp_sqlerrm;
set serveroutput on;
create procedure exp_sqlerrm(
t1 in date,
t2 in date
)is
begin
if t1<t2 then
raise_application_error(-20000,'testErr---Date t1 is earlier than t2.');
end if;
end;
/
```

3、调用存储过程，打印预定义的错误信息

```
DECLARE
past_due EXCEPTION;
PRAGMA EXCEPTION_INIT(past_due,-20000);
BEGIN
exp_sqlerrm('2020-01-01',sysdate);
EXCEPTION
WHEN past_due THEN
DBMS_OUTPUT.PUT_LINE(SQLERRM(-20000));
END;
/
```

结果返回如下：

```
testErr---Date t1 is earlier than t2.
```

#### 