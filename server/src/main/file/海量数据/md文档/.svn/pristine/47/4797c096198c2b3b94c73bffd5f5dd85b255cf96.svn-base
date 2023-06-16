### DBMS_CRYPTO

**功能描述**

DBMS_CRYPTO是一个PL/SQL包，提供用于加密和解密存储的数据的接口，可以对常用的数据类型进行加密和解密，比如raw和大对象clob，blob类型。

- raw类型：存储二进制数据或位串。
- blob类型：存储二进制大对象数据（如数码照片）。
- clob类型：存储大对象单字节的字符数据（如大型文本）。
  该包包含以下函数：ENCRYPT、DECRYPT、HASH、MAC、RANDOMBYTES、RANDOMINTEGER、RANDOMNUMBER。

该内置包包含以下函数，其中ENCRYPT、DECRYPT函数均存在两个，下面描述时记作ENCRYPT函数1、ENCRYPT函数2、DECRYPT函数1、DECRYPT函数2。

<table>
<tr>
<th>函数</th>
<th>描述</th>
</tr>
<tr>
<td> <a href="#ENCRYPT1">ENCRYPT函数1</a></td>
<td>使用用户指定的加密套件来对raw类型数据进行加密，返回raw类型数据。 </td> 
</tr>
<tr>
<td><a href="#ENCRYPT2">ENCRYPT函数2</a></td>
<td>使用用户指定的加密套件来对blob、clob类型数据进行加密，返回blob类型数据。 </td> 
 </tr>
<tr>
<td><a href="#DECRYPT1">DECRYPT函数1</a></td>
<td>使用用户指定的加密套件来对raw类型数据进行解密。 </td> 
 </tr>
<tr>
<td><a href="#DECRYPT2">DECRYPT函数2</a></td>
 <td>使用用户指定的加密套件来对blob类型数据进行解密。 </td> 
</tr>
<tr>
<td><a href="#HASH">HASH函数</a></td>
 <td>使用用户指定的hash算法来返回一个raw类型的哈希值，使用哈希值来验证数据是否已更改。算法标识符是hash_md5，整数码为2。 </td> 
</tr>
<tr>
 <td><a href="#MAC">MAC函数</a></td>
 <td>使用用户指定的MAC算法来返回一个bytea类型数据的MAC哈希值。算法标识符是hmac_md5，整数码为1。 </td> 
</tr>
<tr>
  <td><a href="#RANDOMBYTES">RANDOMBYTES函数</a></td>
<td>生成指定长度（范围为[1, 2000]）的随机字节raw值。 </td> 
 </tr>
<tr>
 <td><a href="#RANDOMINTEGER">RANDOMINTEGER函数</a></td>
<td>用于返回integer数据类型的完整范围内的随机整数。 </td> 
 </tr>
<tr>
 <td><a href="#RANDOMNUMBER">RANDOMNUMBER函数</a></td>
<td> 用于返回number数据类型范围为[0, 2^128 - 1]内的随机数据。 </td> 
    </tr>
</table>


**注意事项**

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

- 使用DBMS_CRYPTO内置包前，需要先加载pgcrypto插件。执行如下命令加载插件：

```
CREATE EXTENSION pgcrypto;
```

- ENCRYPT函数和DECRYPT函数支持的加密套件组合码如下表：

<table>
<tr>
<th>  </th>
<th>加密套件标识符</th>
<th>整数码</th>
<tr>
<td>加密算法</td>
<td>encrypt_des、encrypt_3des</td>
<td>1、3</td>
</tr>
<tr>
<td>密码链接修饰符</td>
<td>chain_ecb、chain_cbc</td>
<td>768、256</td>
</tr>
<tr>
<td>密码填充修改器</td>
<td>pad_pkcs、pad_none</td>
<td>4096、8192</td>
</tr>
<tr>
<td rowspan="2">加密套件组合码</td>
<td>des_cbc_pkcs</td>
<td>4353</td>
</tr>
<tr>
<td>des3_cbc_pkcs</td>
<td>4355</td>
</tr>
</table>

**兼容性**

Vastbase G100 V2.2版本与Oracle 11g R2版本相比，不支持的函数有：SING、VERIFY、PKDECRYPT、PKENCRYPT。

#### ENCRYPT函数1 <a id="ENCRYPT1"></a>

**语法格式**

```
DBMS_CRYPTO.ENCRYPT(
   src IN RAW,
   typ IN PLS_INTEGER,
   key IN RAW,
   iv  IN RAW          DEFAULT NULL)
 RETURN RAW;
```

**参数说明**

- src：要破解的RAW数据。
- typ：加密类型。
- key：解密使用的key。
- iv：块密码初始化向量，缺省NULL。

**示例**

- 直接调用。

```
select dbms_crypto.encrypt('ABCABC'::raw, dbms_crypto.des_cbc_pkcs, '123456'::raw);
     encrypt      
------------------
 57E647BDA301C9FA
(1 row)

select dbms_crypto.encrypt('ABCABC'::raw, 4353, '123456'::raw);
     encrypt      
------------------
 57E647BDA301C9FA
(1 row)

select dbms_crypto.encrypt('ABCABC'::raw, dbms_crypto.encrypt_des+dbms_crypto.chain_cbc+dbms_crypto.pad_pkcs, '123456'::raw);
     encrypt      
------------------
 57E647BDA301C9FA
(1 row)

select dbms_crypto.decrypt('57E647BDA301C9FA'::raw, dbms_crypto.des_cbc_pkcs, '123456'::raw);
 decrypt 
---------
 ABCABC
(1 row)
```

- 加密表数据

1、创建测试表并插入数据。

```
create table t_select_raw(id int, col varchar2(20));
insert into t_select_raw values(1,'测试');
insert into t_select_raw values(2,'中文');

create table t_dml_raw(id int, col raw);
insert into t_dml_raw values(1,'1234AABBCCDDEEFF');
```

2、查询结果加密。

```
select dbms_crypto.encrypt(hextoraw(encode(col::bytea, 'hex')), dbms_crypto.des_cbc_pkcs, '123456'::raw) from t_select_raw;

     encrypt      
------------------
 6C5B52D18B146E13
 B01C554C5102A8FE
(2 rows)

select dbms_crypto.encrypt(hextoraw(encode(col::bytea, 'hex')), dbms_crypto.des_cbc_pkcs, hextoraw(encode(col::bytea, 'hex'))) from t_select_raw;
     encrypt      
------------------
 9A5296006CB243A3
 3D172C9ECA9B4A43
(2 rows)

select dbms_crypto.encrypt(hextoraw(encode(col::bytea, 'hex')), dbms_crypto.des_cbc_pkcs, hextoraw(encode(col::bytea, 'hex')),  hextoraw(encode(col::bytea, 'hex'))) from t_select_raw;
     encrypt      
------------------
 58AEFBB87999E13C
 CE070EDF29322C9F
(2 rows)
```

3、查询raw数据

```
select dbms_crypto.encrypt(col, dbms_crypto.des_cbc_pkcs, col,col) from t_dml_raw;
            encrypt              
----------------------------------
 8475EECF02BEE7E870B363D667785B6E
(1 row)
```

4、插入数据加密。

```
insert into t_dml_raw values(2, dbms_crypto.encrypt(hextoraw(encode('中文'::bytea, 'hex')), dbms_crypto.des_cbc_pkcs, '1'::raw));

insert into t_dml_raw values(2, dbms_crypto.encrypt(hextoraw(encode('abc'::bytea, 'hex')), dbms_crypto.des_cbc_pkcs, hextoraw(encode('2'::bytea, 'hex')), hextoraw(encode('2'::bytea, 'hex'))));

update t_dml_raw set col = dbms_crypto.encrypt(hextoraw(encode('测试'::bytea, 'hex')), dbms_crypto.des_cbc_pkcs, '1'::raw) where id = 1;
select * from t_dml_raw;
```

5、显示加密后的结果。

```
select * from t_dml_raw;
 id |       col        
----+------------------
  2 | 634FE089F003C8AA
  2 | 10759AD6661DD7BC
  1 | 350968ECE5EBF882
(3 rows)
```

#### ENCRYPT函数2 <a id="ENCRYPT2"></a>

**语法格式**

```
DBMS_CRYPTO.ENCRYPT(
   dst IN OUT NOCOPY BLOB,
   src IN            BLOB,
   typ IN            PLS_INTEGER,
   key IN            RAW,
   iv  IN            RAW          DEFAULT NULL);

DBMS_CRYPTO.ENCRYPT(
   dst IN OUT NOCOPY BLOB,
   src IN            CLOB         CHARACTER SET ANY_CS,
   typ IN            PLS_INTEGER,
   key IN            RAW,
   iv  IN            RAW          DEFAULT NULL);
```

**参数说明**

- dst：解密数据的输出位置。
- src：要破解的LOB数据。
- typ：加密类型。
- key：解密使用的key。
- iv：块密码初始化向量，缺省NULL。

**示例**

- 对blob类型数据进行加密-直接调用。

```
select dbms_crypto.encrypt('abc'::blob,'ABCABC'::blob,dbms_crypto.des_cbc_pkcs, '123456'::raw);
     encrypt      
------------------
 57E647BDA301C9FA
(1 row)

select dbms_crypto.encrypt('abc'::blob,'ABCABC'::blob, 4353, '123456'::raw);
     encrypt      
------------------
 57E647BDA301C9FA
(1 row)

select dbms_crypto.encrypt('abc'::blob,'ABCABC'::blob, dbms_crypto.encrypt_des+dbms_crypto.chain_cbc+dbms_crypto.pad_pkcs, '123456'::raw);
     encrypt      
------------------
 57E647BDA301C9FA
(1 row)

```

- 对clob类型数据进行加密-直接调用。

```
select dbms_crypto.encrypt('abc'::blob,'ABCABC'::clob,dbms_crypto.des_cbc_pkcs, '123456'::raw);
     encrypt      
------------------
 D552672EE9151C95
(1 row)

select dbms_crypto.encrypt('abc'::blob,'ABCABC'::clob, 4353, '123456'::raw);
     encrypt      
------------------
 D552672EE9151C95
(1 row)

select dbms_crypto.encrypt('abc'::blob,'ABCABC'::clob, dbms_crypto.encrypt_des+dbms_crypto.chain_cbc+dbms_crypto.pad_pkcs, '123456'::raw);
     encrypt      
------------------
 D552672EE9151C95
(1 row)

```

#### DECRYPT函数1<a id="DECRYPT1"></a>

**语法格式**

```
DBMS_CRYPTO.DECRYPT(
   src IN RAW,
   typ IN PLS_INTEGER,
   key IN RAW,
   iv  IN RAW DEFAULT NULL)
 RETURN RAW;
```

**参数说明**

- src：要破解的RAW数据。
- typ：加密类型。
- key：解密使用的key。
- iv：块密码初始化向量，缺省NULL。

**示例**

1、对raw类型数据进行加密,使用相同的方式进行解密-直接调用(获取加密结果)。

```
select dbms_crypto.encrypt('ABCABC'::raw, dbms_crypto.des_cbc_pkcs, '123456'::raw);
```

返回结果为：

```
     encrypt      
------------------
 57E647BDA301C9FA
(1 row)
```

2、将加密结果使用相同的方式进行解密(此处需要使用上一步加密结果数据来替换命令中的加密结果)。

```
select dbms_crypto.decrypt('加密后的结果'::raw, dbms_crypto.des_cbc_pkcs, '123456'::raw);
decrypt 
---------
 ABCABC
(1 row)
select dbms_crypto.decrypt('加密后的结果'::raw, 4353, '123456'::raw);
decrypt 
---------
 ABCABC
(1 row)

```

#### DECRYPT函数2<a id="DECRYPT2"></a>

**语法格式**

```
DBMS_CRYPTO.DECRYPT(
   dst IN OUT NOCOPY BLOB,
   src IN            BLOB,
   typ IN            PLS_INTEGER,
   key IN            RAW,
   iv  IN            RAW          DEFAULT NULL);

DBMS_CRYPT.DECRYPT(
   dst IN OUT NOCOPY CLOB         CHARACTER SET ANY_CS,
   src IN            BLOB,
   typ IN            PLS_INTEGER,
   key IN            RAW,
   iv  IN            RAW          DEFAULT NULL);
```

**参数说明**

- dst：解密数据的输出位置。
- src：要破解的LOB数据。
- typ：加密类型。
- key：解密使用的key。
- iv：块密码初始化向量，缺省NULL。

**示例**

1、对blob类型数据进行解密-直接调用

```
select dbms_crypto.decrypt('abc'::blob,'D552672EE9151C95'::blob,dbms_crypto.des_cbc_pkcs, '123456'::raw);
   decrypt    
--------------
 414243414243
(1 row)


select dbms_crypto.decrypt('abc'::blob,'D552672EE9151C95'::blob, 8449, '123456'::raw, 'ABC'::raw);
   decrypt    
--------------
 414243414243
(1 row)

```

2、对blob类型数据进行加密,使用相同的方式进行解密-直接调用(获取加密结果)。

```
select dbms_crypto.encrypt('abc'::blob,'ABCABC'::blob,dbms_crypto.des_cbc_pkcs, '123456'::raw);
     encrypt      
------------------
 57E647BDA301C9FA
(1 row)
```

3、将加密结果使用相同的方式进行解密(此处需要使用上一步加密结果数据来替换命令中的加密结果)。

```
select dbms_crypto.decrypt('abc'::blob,'加密后的结果'::blob, dbms_crypto.des_cbc_pkcs, '123456'::raw);
 decrypt 
---------
 ABCABC
(1 row)


select dbms_crypto.decrypt('abc'::blob,'加密后的结果'::blob, 4353, '123456'::raw);
 decrypt 
---------
 ABCABC
(1 row)
```

#### HASH函数<a id="HASH"></a>

**语法格式**

```
DBMS_CRYPTO.Hash (
   src IN RAW,
   typ IN PLS_INTEGER)
 RETURN RAW;

DBMS_CRYPTO.Hash (
   src IN BLOB,
   typ IN PLS_INTEGER)
 RETURN RAW;

DBMS_CRYPTO.Hash (
   src IN CLOB CHARACTER SET ANY_CS,
   typ IN PLS_INTEGER)
 RETURN RAW;
```

**参数说明**

- src：要计算的原始数据。
- typ：哈希算法类型。

**示例**

```
select dbms_crypto.hash('57E647BDA301C9FA'::blob, dbms_crypto.hash_md5);
               hash               
----------------------------------
 0625EE8F90EE27BEA4AD2EC9017E5A2E
(1 row)

select dbms_crypto.hash('中文测试'::clob, dbms_crypto.hash_md5);
               hash               
----------------------------------
 089B4943EA034ACFA445D050C7913E55
(1 row)

select dbms_crypto.hash('中文测试'::clob, 2);
               hash               
----------------------------------
 089B4943EA034ACFA445D050C7913E55
(1 row)

```

#### MAC函数<a id="MAC"></a>

**语法格式**

```
DBMS_CRYPTO.MAC (
   src IN RAW,
   typ IN PLS_INTEGER,
   key IN RAW)
 RETURN RAW;

DBMS_CRYPTO.MAC (
   src IN BLOB,
   typ IN PLS_INTEGER
   key IN RAW)
 RETURN RAW;

DBMS_CRYPTO.MAC (
   src IN CLOB CHARACTER SET ANY_CS,
   typ IN PLS_INTEGER
   key IN RAW)
 RETURN RAW;
```

**参数说明**

- src：要计算的原始数据。
- typ：MAC算法类型。
- MAC：MAC算法的key值。

**示例**

```
select dbms_crypto.mac('57E647BDA301C9FA'::blob, dbms_crypto.hmac_md5, '123456ABC'::raw);
              mac                
----------------------------------
 6AB6390193F2DCA79E2CDC22047B2EA8
(1 row)

select dbms_crypto.mac('中文测试'::clob, dbms_crypto.hmac_md5, '123456ABC'::raw);
              mac                
----------------------------------
 2CDC70BD0B266731AEE027C185E77D68
(1 row)

select dbms_crypto.mac('中文测试'::clob, 1, '123456ABC'::raw);
              mac                
----------------------------------
 2CDC70BD0B266731AEE027C185E77D68
(1 row)


```

#### RANDOMBYTES函数<a id="RANDOMBYTES"></a>

**语法格式**

```
DBMS_CRYPTO.RANDOMBYTES (
   number_bytes IN POSITIVE)
 RETURN RAW;
```

**参数说明**

number_bytes：要生成的随机字节数。

**示例**

```
select dbms_crypto.randombytes(10) from dual;

     randombytes      
----------------------
 073B14CADD7E284F2417
(1 row)

```

####  RANDOMINTEGER函数<a id="RANDOMINTEGER"></a>

**语法格式**

```
DBMS_CRYPTO.RANDOMINTEGER
 RETURN BINARY_INTEGER;
```

**示例**

```
select dbms_crypto.randominteger() from dual;

 randominteger 
---------------
    -909240370
(1 row)
```

####  RANDOMNUMBER函数<a id="RANDOMNUMBER"></a>

**语法格式**

```
DBMS_CRYPTO.RANDOMNUMBER
 RETURN NUMBER;
```

**示例**

```
select dbms_crypto.randomnumber() from dual;

         randomnumber          
-------------------------------
 11351894157074763785441053451
(1 row)
```
