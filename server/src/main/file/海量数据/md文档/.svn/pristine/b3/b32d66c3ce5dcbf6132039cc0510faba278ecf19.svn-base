##### DROP ROLE

**功能描述**

删除指定的角色。

**语法格式**

```
DROP ROLE [ IF EXISTS ] role_name [, ...];
```

**参数说明**

- IF EXISTS：如果指定的角色不存在，则发出一个notice而不是抛出一个错误。

- role_name：要删除的角色名称。

  取值范围：已存在的角色。

**注意事项**

只允许删除同类型管理员属性的用户。

**示例**

1、创建测试用户utest。

```
CREATE ROLE utest IDENTIFIED BY 'Bigdata@123';
```

2、删除测试用户。

```
DROP ROLE utest;
```

