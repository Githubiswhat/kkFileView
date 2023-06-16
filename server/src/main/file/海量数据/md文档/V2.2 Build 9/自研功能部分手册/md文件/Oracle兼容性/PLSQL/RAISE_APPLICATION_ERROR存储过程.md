###  RAISE_APPLICATION_ERROR存储过程

**功能描述**

该存储过程用于声明自定义异常编号和错误信息。

**使用流程**

1、RAISE_APPLICATION_ERROR定义异常编号和错误信息。
2、通过exception_init关联异常编号。
3、捕获异常变量。

**语法格式**

```
RAISE_APPLICATION_ERROR(error_code,message[,{TURE | FALSE}]);
```

**参数说明**

- error_code：用户自定义异常变量编号，范围是-20000~20999。
- message：错误信息，允许最长2k的字符串。
- [{TURE | FALSE}]：默认值是TRUE，输出自定义错误信息。当设置为FALSE，输出系统错误信息。

**注意事项**

无。

**示例**

1、声明自定义异常编号和错误信息。

```
create or replace procedure account_status(
due_date DATE,
today DATE
)authid definer
is
begin
if due_date < today then
RAISE_APPLICATION_ERROR(-20000,'Account past due.');
end if ;
end;
/
```

2、调用存储过程。

```
declare
past_due exception;
PRAGMA EXCEPTION_INIT(past_due,-20000);
begin
account_status('2021-10-22','2021-11-22');
exception
when past_due then
dbms_output.put_line(SQLERRM);
end;
/
```

返回结果为：

```
Account past due.
ANONYMOUS BLOCK EXECUTE
```
