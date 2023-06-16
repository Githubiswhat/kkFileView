# KMS加密

**功能描述**

Vastbase支持使用KMS加密保护数据安全。

- 支持使用gs_encrypt进行KMS加密。
- 支持使用gs_decrypt进行KMS解密。

- vb_dump/vb_dumpall支持使用KMS加密。
- vsql支持使用KMS加密连接。

**前提条件**

KMS相关配置已完成，添加业务用户、密钥、密钥服务。

**注意事项**

KMS模式仅支持SWS-KMS-SM4算法。

**示例**

**前置步骤**

1、初始化配置存储加密数据库实例。

```
vb_initdb -D encdata --keyname=tde_1 --userpin='liuyao:1qaz!QAZ' --algorithm=SWS-KMS-SM4 --nodename=test
```

2、在新实例配置文件 postgresql.conf中配置数据库端口号

```
port=9944
```

3、启动实例。启动过程中，需要输入KMS的业务用户名和口令，格式同样以冒号隔开。

```
vb_ctl - D encdata/ start
```

**示例1：**使用gs_encrypt和gs_decrypt加解密。

1、连接数据库。

```
vsql -d vastbase -p9944 -r
```

2、执行加密语句。

```
select gs_encrypt('abc','tde_1','kms');
```

结果显示如下：

```
         gs_encrypt
------------------------------
BdM5xmJ68pWU9SI7DMPEdsViLQ==
(1 row)
```

3、执行解密语句。

```
select gs_decrypt（'BdM5xmJ68pWU9SI7DMPEdsViLQ==','tde_1','kms');
```

结果显示如下：

```
 gs_decrypt
--------------
 abc
(1 row)
```

**示例2：**vb_dump导出数据使用KMS加密。

1、导出数据。

```
vb_dump vastbase -Fp -p 9944 --with-encryption=SM4 --with-key='tde_1' --with-userpin='liuyao:1qaz!QAZ' -f /home/$username/backup1.sql 
```

2、导入数据。

```
vsql -d back -p 9944 -f /home/$username/backup1.sql --with-decryption=SM4 --with-key='tde_1' --with-userpin='liuyao:1qaz!QAZ'
```

**示例3**：vsql使用KMS连接数据库。

```
vsql -d vastbase -p 9944 -r --with-decryption=SM4 --with-key='test_key2' --with-userpin='caidi:Test1234'
```

