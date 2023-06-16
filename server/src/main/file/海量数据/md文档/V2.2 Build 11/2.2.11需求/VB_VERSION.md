# vb_version函数返回版本号

## 功能描述

使用vb_version函数查询Vastbase产品版本信息。显示内容包括产品名称、版本号、补丁号、提交号和基于openGauss版本的信息。

## 注意事项

- 本函数只有在Vastbase数据库能正常启动及登录的前提下使用。
- 本函数大写可识别。

## 语法格式

查询语句：

```sql
select vb_version();
```

返回结果格式：

```sql
                			vb_version
--------------------------------------------------------------------
 (Vastbase xxx) compiled at xxx commit xxx last mr     												 									+
  product xxx 																					  										+ 
  version:xxx																					 										+		
  patch:xxx											    										 										+
  commit:xxx										    										  										+
  openGauss version:xxx								    										 										+
  host:xxx
```

## 参数说明

- **product name**

  产品名称

- **version**

  版本号

- **patch**

  补丁号

- **commit**

  提交号

- **openGauss version**

  基于openGauss的版本号

- **host**

  主机环境信息

## 示例

查询产品信息

```sql
select vb_version();
```

返回结果为：

```sql
                             vb_version

--------------------------------------------------------------------
 (Vastbase G100 V2.2 (Build 11) Alpha) compiled at 2022-10-25 14:51:16 commit 99
13 last mr 			 								+
  product name:Vastbase G100
           											+
  version:V2.2 (Build 11) Alpha
            					 					+
  patch:0
            									 	+
  commit:9913	
            		 								+
  openGauss version:3.0.1		
             										+
  host:x86_64-pc-linux-gnu
(1 row)
```