### SYS_GUID

**功能描述**

SYS_GUID可以生成一个全局唯一的字符串数据值。生成的依据主要是时间和随机数，具有全局唯一性。

**语法格式**

```
select * from sys_guid();
```

**注意事项**

无。

**示例**

```
select * from sys_guid();
```

其返回结果如下：

```
               sys_guid               
--------------------------------------
 48a5e783-bd94-4038-9547-0d8611aa8a33
(1 row)
```
