## 适配ECPG工具

**功能描述**

ECPG工具用于处理C中的嵌入式SQL语句，将包含嵌入式SQL语句的C程序转换成可以被C/C++编译器编译执行的C/C++程序。支持Vastbase SQL语句和ECPG SQL两种嵌入式SQL语句：

- 除部分SQL命令不支持外其余Vastbase SQL命令全部支持。以下为不支持的SQL命令：

  - 匿名块相关，包括开启匿名块和创建包含匿名块的函数。

```
    [DECLARE [declare_statements]] 
    BEGIN
    execution_statements  
    END;
    /
```

  - 不支持创建package。

    ```
    create package XXX
    ```

注：除以上SQL语句之外Vastbase支持的所有SQL语句都可以作为嵌入式SQL语句使用。

- ECPG SQL命令：详细的ECPG SQL语法使用及功能参考《PG11.2手册》（http://www.postgres.cn/docs/11/）。

  ECPG SQL语法列表如下：

  - ALLOCATE DESCRIPTOR ：分配一个SQL描述符区域

  - CONNECT ：建立一个数据库连接

  - DEALLOCATE DESCRIPTOR：释放一个SQL描述符区域

  - DECLARE ：定义一个游标

  - DESCRIBE ：得到有关一个预备语句或结果集的信息

  - DISCONNECT ： 终止一个数据库连接

  - EXECUTE IMMEDIATE ： 动态地准备和执行一个语句

  - GET DESCRIPTOR ： 从一个SQL描述符区域得到信息

  - OPEN ：打开一个动态游标

  - PREPARE ： 准备一个语句用于执行

  - SET AUTOCOMMIT ： 设置当前会话的自动提交行为

  - SET CONNECTION ： 选择一个数据库连接

  - SET DESCRIPTOR ： 在一个SQL描述符区域中设置信息

  - TYPE： 定义一种新数据类型

  - VAR ： 定义一个变量

  - WHENEVER ：指定一个要在一个SQL语句导致发生一个特定类别的情况时要采取的动作

  注：在Vastbase 中，ECPG SQL中的CALL和 IDENTIFIED改名为ECPGCALL和	ECPGIDENTIFIED。

**语法格式**

在C语言中使用嵌入式SQL语句的语法如下：

```
EXEC SQL stmt;
```

**参数说明**

stmt：一条SQL语句。

**使用流程**

1、安装EPCG工具及其依赖库。

2、编写一个C程序，后缀为.pgc，在文件中使用嵌入式SQL语句，例如在prog1.pgc文件中使用SQL命令。

```
EXEC SQL CREATE TABLE t1(id int, info text);  --定义表t1
```

3、使用ECPG工具将pgc文件，例如将prog1.pgc文件编译为prog1.c。

```
ecpg prog1.pgc
```

4、编译可执行程序。

```
cc -I/usr/local/pgsql/include -c prog1.c
cc -o prog1 prog1.o -L/usr/local/pgsql/lib -lecpg
```

5、运行可执行程序。

```
./prog1
```

**示例**

在C程序中通过嵌入式SQL实现数据库连接、创建表、插入、修改、查询、连接断开

```
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

EXEC SQL INCLUDE ../regression;

int main() {
  EXEC SQL BEGIN DECLARE SECTION;
  	int i1[3], i2[3], i3[3], i4;
  EXEC SQL END DECLARE SECTION;

  ECPGdebug(1, stderr);
  EXEC SQL CONNECT TO REGRESSDB1;

  EXEC SQL WHENEVER SQLWARNING SQLPRINT;
  EXEC SQL WHENEVER SQLERROR SQLPRINT;

  EXEC SQL CREATE TABLE insupd_test(a int, b int);

  EXEC SQL INSERT INTO insupd_test (a,b) values (1, 1);
  EXEC SQL INSERT INTO insupd_test (a,b) values (2, 2);
  EXEC SQL INSERT INTO insupd_test (a,b) values (3, 3) returning a into :i4;

  EXEC SQL UPDATE insupd_test set a=a+1 returning a into :i3;
  EXEC SQL UPDATE insupd_test set (a,b)=(5,5) where a = 4;
  EXEC SQL UPDATE insupd_test set a=4 where a=3;;

  EXEC SQL SELECT a,b into :i1,:i2 from insupd_test order by a;

  printf("changes\n%d %d %d %d\n", i3[0], i3[1], i3[2], i4);
  printf("test\na b\n%d %d\n%d %d\n%d %d\n", i1[0], i2[0], i1[1], i2[1], i1[2], i2[2]);

  EXEC SQL DISCONNECT ALL;

  return 0;
}
```
