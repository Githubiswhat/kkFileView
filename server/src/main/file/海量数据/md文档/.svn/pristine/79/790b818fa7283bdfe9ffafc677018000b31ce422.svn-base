##### GOTO语句

**功能描述**

GOTO语句可以实现从GOTO位置到目标语句的无条件跳转。GOTO语句会改变原本的执行逻辑，因此应该慎重使用，或者也可以使用EXCEPTION处理特殊场景。当执行GOTO语句时，目标Label必须是唯一的。

**语法格式**

label declaration ::=

<div align="left"><img src="image/label.jpg" style="zoom:50%")</div>    

goto statement ::=

<div align="left"><img src="image/goto.jpg" style="zoom:50%")</div>     

**注意事项**

- 不支持有多个相同的GOTO labels目标场景，无论是否在同一个block中。

  ```
  BEGIN
    GOTO pos1; 
    <<pos1>>
    SELECT * FROM ...
    <<pos1>>
    UPDATE t1 SET ...
  END;
  /
  ```

- 不支持GOTO跳转到IF语句、CASE语句、LOOP语句中。

  ```
  BEGIN
     GOTO pos1; 
     IF valid THEN
       <<pos1>>
       SELECT * FROM ...
     END IF;
   END;
   /
  ```

- 不支持GOTO语句从一个IF子句跳转到另一个IF子句，或从一个CASE语句的WHEN子句跳转到另一个WHEN子句。

  ```
  BEGIN 
     IF valid THEN
       GOTO pos1;
       SELECT * FROM ...
     ELSE
       <<pos1>>
       UPDATE t1 SET ...
     END IF;
   END;
   /
  ```

- 不支持从外部块跳转到内部的BEGIN-END块。

  ```
  BEGIN
     GOTO pos1;  
     BEGIN
       <<pos1>>
       UPDATE t1 SET ...
     END;
   END;
   /
  ```

- 不支持从异常处理部分跳转到当前的BEGIN-END块。但可以跳转到上层BEGIN-END块。

  ```
  BEGIN
     <<pos1>>
     UPDATE t1 SET ...
     EXCEPTION
       WHEN condition THEN
          GOTO pos1;
   END;
   /
  ```

- 如果从GOTO到一个不包含执行语句的位置，需要添加NULL语句。

  ```
  DECLARE
     done  BOOLEAN;
  BEGIN
     FOR i IN 1..50 LOOP
        IF done THEN
           GOTO end_loop;
        END IF;
        <<end_loop>>  -- not allowed unless an executable statement follows
        NULL; -- add NULL statement to avoid error
     END LOOP;  -- raises an error without the previous NULL
  END;
  /
  ```

**示例**

1、创建测试表test。

```
CREATE TABLE test(id,int);
```

2、创建匿名块进行测试，执行过程将从GOTO语句位置跳转到'pos1'标记位置执行。

```
BEGIN
GOTO pos1;
INSERT INTO test(id) VALUES(1);
<<pos1>>
INSERT INTO test(id) VALUES(2);
END;
/
```

3、验证结果。

```
SELECT * FROM test;
```

当结果返回如下信息，则表示跳转成功。

```
 id 
----
  2
(1 row)
```

