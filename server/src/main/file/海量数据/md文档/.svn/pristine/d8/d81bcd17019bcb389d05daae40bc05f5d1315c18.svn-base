### 支持oracle的包（Package）和包体（Package body）

**功能描述**

Package是一个模式对象，用于组织过程、函数和变量的一种方式，由包声明和包体组成。

**语法格式**

- 包声明

```
CREATE [OR REPLACE] PACKAGE [schema_name.]<package_name> IS | AS
    declarations;
END;
```

- 包体

```
CREATE [OR REPLACE] PACKAGE BODY [schema_name.]<package_name> IS | AS
    declarations
    implementations;
[BEGIN
EXCEPTION]
END;
```

**参数说明**

- 声明包 
  - OR REPLACE：该参数说明如果创建的包存在,则重新创建包。
  - schema_name：包含此包的模式名称。
  - package_name：包的名称。
  - declarations：可以声明公有变量和公有子程序。
- 包体 
  - declarations：声明私有变量和私有子程序。
  - implementations：定义私有子程序和公有子程序。

**注意事项**

无。

**示例**

- 创建包

```
CREATE OR REPLACE PACKAGE emp_bonus AS
PROCEDURE calc_bonus (d date);
END;
/
```

- 创建包体

```
CREATE OR REPLACE PACKAGE BODY emp_bonus AS
PROCEDURE calc_bonus (date_hired DATE) IS
BEGIN
DBMS_OUTPUT.PUT_LINE
('Employees hired on ' || date_hired || ' get bonus.');
END;
END;
/
```

- 删除包体

```
 DROP PACKAGE BODY emp_bonus;
```