#### YEAR函数

**功能描述**

该功能是为了获取给定时间的年。函数参数time的类型是：date，timestamp，timestamptz，time，timetz等类型。返回值是整型类型。

**语法格式**

```
YEAR(date)
```

**注意事项**

- 若函数的参数是time类型时，由于time类型不包含年月日，按照MySQL的处理，返回当前的年份和月份。

- 输入null时，返回值为空。

- 输入的时间格式非法时，报错信息为时间格式语法异常。

- 数据的时间超范围时，报错信息为输入的时间范围异常。
- 具备该函数的访问权限，例如Year(time)函数如果位于pg_catalog模式下，用户需要具备以下权限：
  - pg_catalog模式的usage权限。
  - Year(time)函数的excute权限。

**兼容性**

完全兼容。

**示例**

```
select year(current_date);
```

结果返回如下：

```
 year 
------
 2022
(1 row)
```

#### 