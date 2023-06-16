# DECLARE EXIT HANDLER语句

## 功能描述

Vastbase G100在MySQL兼容模式下支持使用declare handler语句来处理函数或存储过程中的异常。本语法还支持：

- 单次declare handler时可以包含多个condition_value的语法；
- 可以在声明块中多次声明exit handler。

## 语法格式

```sql
DECLARE exit HANDLER
	FOR  condition _value[,condition_value]...
	statement
```

其中confition_value包括

```sql
{
mysql_error_code
|SQLSTATE[VALUE] sqlstate_value
|condition_name
|SQLWARNING
|NOT FOUND
|SQLEXCEPTION
}
```

## 参数说明

- **exit**

  处理类型，表明当程序终止，即退出当前declare所在的begin...end块。

- **condition_value**

  说明handler被何种条件触发。

- **Error_code**

  异常捕获方法，捕获mysql_error_code值。

- **SQLSTATE**

  错误码。

- **condition_name**

  表示异常的名称。

- **SQLWARNING**

  警告。异常捕获方法，使用SQLWARNING捕获异常。

- **NOT FOUND**

  无数据。异常捕获方法，使用NOT FOUND捕获异常。

- **SQLEXCEPTION**

  异常。异常捕获方法，使用SQLEXCEPTION捕获异常。

- **statement**

  当出现异常时，终止当前执行的语句，跳转到statement的语句执行。

## 注意事项

- 该功能仅在数据库兼容模式为MySQL时能够使用（即创建DB时DBCOMPATIBILITY='B'），在其他数据库兼容模式下不能使用该特性。
- 该语法仅在函数或存储过程中支持。
- 同时使用declare handler和exception when异常处理会报错。
- 在Vastbase中将mysql_error_code视为sqlstate。

## 示例

**前置步骤：**创建兼容MySQL的库db_mysql，并进入。

```sql
CREATE DATABASE db_mysql DBCOMPATIBILITY='B';
\c db_mysql
```

**示例1：**在存储过程中定义好触发异常处理语句。

1、创建测试表。

```sql
CREATE TABLE article_testtb(
article_id INT,
tag_id INT,
PRIMARY KEY(article_id,tag_id)
);
```

2、创建声明异常的存储过程。

```sql
CREATE OR REPLACE PROCEDURE insert_article_testtb(IN article_id INT, IN tag_id INT) AS
DECLARE result VARCHAR;
BEGIN

DECLARE EXIT HANDLER FOR sqlexception
begin
RAISE NOTICE 'SQLSTATE = %,SQLCODE = %,SQLERROR=%',SQLSTATE,returned_sqlstate,message_text;
end;

IF article_id <= 10 THEN
-- insert a new record into article_tags
INSERT INTO article_testtb(article_id,tag_id)
VALUES(article_id,tag_id);

-- return tag count for the article
SELECT COUNT(*) FROM article_testtb into result;
RAISE INFO 'count: %', result;

ELSE
CREATE USER pi_user_independent WITH INDEPENDENT IDENTIFIED BY "1234@abe";
END IF;

END;
/
```

3、调用存储过程

（a）正常插入数据。

```sql
CALL insert_article_testtb(1,1);
CALL insert_article_testtb(1,2);
CALL insert_article_testtb(1,3);
```

返回结果为正常：

```sql
INFO:  count: 1
 insert_article_testtb
-----------------------

(1 row)

INFO:  count: 2
 insert_article_testtb
-----------------------

(1 row)

INFO:  count: 3
 insert_article_testtb
-----------------------

(1 row)
```

（b）主键重复，触发异常处理。

```sql
CALL insert_article_testtb(1,3);
```

返回结果提示触发异常处理语句：

```sql
NOTICE:  SQLSTATE = 23505,SQLCODE = 83906754,SQLERROR=duplicate key value violates unique constraint "article_testtb_pkey"
 insert_article_testtb
-----------------------

(1 row)
```

（c）其他异常，直接报错。

```sql
CALL insert_article_testtb(11,11);
```

报错信息为：

```sql
WARNING:  Please carefully use independent user as it need more self-management.
HINT:  Self-management include logical backup, password manage and so on.
CONTEXT:  SQL statement "CREATE USER pi_user_independent WITH INDEPENDENT IDENTIFIED BY "1234@abe""
PL/pgSQL function public.insert_article_testtb(integer,integer) line 20 at SQL statement
 insert_article_testtb
-----------------------

(1 row)
```

**示例2：**同时声明多个mysql_error_code。

1、创建测试表。

```sql
CREATE TABLE article_testtb2(
article_id INT,
tag_id INT,
PRIMARY KEY(article_id,tag_id)
);
```

2、创建声明异常的存储过程。

```sql
CREATE OR REPLACE PROCEDURE test_pro(IN article_id INT, IN tag_id INT) AS
DECLARE result VARCHAR;
BEGIN

--22012分母为0异常
DECLARE EXIT HANDLER FOR 22012
begin
RAISE NOTICE 'SQLSTATE = %,SQLCODE = %,SQLERROR=%',SQLSTATE,returned_sqlstate,message_text;
end;

--23505主键重复的异常
DECLARE EXIT HANDLER FOR 23505
begin
RAISE NOTICE 'SQLSTATE = %,SQLCODE = %,SQLERROR=%',SQLSTATE,returned_sqlstate,message_text;
end;

--用户已存在
DECLARE EXIT HANDLER FOR 42710
begin
RAISE NOTICE 'SQLSTATE = %,SQLCODE = %,SQLERROR=%',SQLSTATE,returned_sqlstate,message_text;
end;

--正常执行的命令
raise info '%', 'begin';

IF article_id < 10 THEN
-- insert a new record into article_tags
INSERT INTO article_testtb2(article_id,tag_id)
VALUES(article_id,tag_id);
ELSEIF article_id < 20 THEN

--22012分母为0异常
result := article_id / tag_id;
RAISE INFO 'result: %', result;
ELSE
create user test_user_1131524 identified by 'Aa123456';
END IF;

--正常执行的命令
raise info '%', 'end';
END;
/
```

3、调用存储过程。

（a）正常调用。

```sql
CALL test_pro(1,1);
```

返回结果为正常：

```sql
INFO:  begin
INFO:  end
 test_pro
----------

(1 row)
```

（b）触发主键重复异常。

```sql
CALL test_pro(1,1);
```

提示异常为：

```sql
INFO:  begin
NOTICE:  SQLSTATE = 23505,SQLCODE = 83906754,SQLERROR=duplicate key value violates unique constraint "article_testtb2_pkey"
 test_pro
----------

(1 row)
```

（c）分母为0触发异常。

```sql
CALL test_pro(11,0);
```

提示异常为：

```sql
INFO:  begin
NOTICE:  SQLSTATE = 22012,SQLCODE = 33816706,SQLERROR=division by zero
 test_pro
----------

(1 row)
```

（d）触发用户重复异常。

```sql
CALL test_pro(21,21);
CALL test_pro(22,22);
```

提示异常为：

```sql
INFO:  begin
INFO:  end
 test_pro
----------

(1 row)

INFO:  begin
NOTICE:  SQLSTATE = 42710,SQLCODE = 290948,SQLERROR=role "test_user_1131524" already exists
 test_pro
----------

(1 row)
```