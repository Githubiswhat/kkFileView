### EXCEPTION_INIT预定义异常处理

**功能描述**

使用exception_init可以将自定义异常变量名称预自定义的异常的错误代码关联。

**语法格式**

```
PRAGMA EXCEPTION_INIT (exception_name,error_code)
```

**参数说明**

- exception_name：自定义的异常变量名。

- error_code：自定义错误编码。

**注意事项**

无。

**示例**

1、创建测试表。

```
create table t2(id int not null,name varchar2(50));
```

2、声明预定义的异常处理。

```
declare
e_missingnull exception;
PRAGMA EXCEPTION_INIT(e_missingnull ,-1400);
begin
insert into t2(id) values(null);
exception
when e_missingnull then
dbms_output.put_line(SQLERRM);
rollback;
end;
/
```

返回结果为：

```
ERROR:  null value in column "id" violates not-null constraint
DETAIL:  Failing row contains (null, null).
CONTEXT:  SQL statement "insert into t2(id) values(null)"
PL/pgSQL function inline_code_block line 5 at SQL statement
```
