### DBMS_OBFUSCATION_TOOLKIT

**功能描述**

DBMS_OBFUSCATION_TOOLKIT提供用于加密和解密存储的数据的接口，可以对数据进行DES、Triple DES或者MD5加密。该内置包包含以下函数：

<table>
<tr>
<th>函数</th>
<th>描述</th>
</tr>
<tr>
<td><a href="#TOOKDESENCRYPT">DESENCRYPT</a></td>
<td>
用于使用DES算法对输入数据进行加密，并生成加密格式的数据。
</td>
</tr>
<tr>
<td> <a href="#TOOKDESDECRYPT">DESDECRYPT</a></td>
<td>
用于对使用DES算法所生成的加密数据进行解密。
</td>
</tr>
<tr>
<td> <a href="#TOOKDESGETKEY"> DESGETKEY</a></td>
<td>
生成一个随机字符串，并对其使用DES算法生成一个加密密钥。
</td>
</tr>
<tr>
<td><a href="#TOOKDES3ENCRYPT"> DES3ENCRYPT</a></td>
<td>
用于使用DES3算法对输入数据进行加密，并生成加密格式的数据。
</td>
</tr>
<tr>
    <td><a href="#TOOKDES3DECRYPT">DES3DECRYPT</a></td>
<td>
用于对使用DES3算法所生成的加密数据进行解密。
</td>
</tr>
<tr>
    <td><a href="#TOOKDES3GETKEY"> DES3GETKEY</a></td>
<td>
生成一个随机字符串，并对其使用DES3算法生成一个加密密钥。
</td>
</tr>
<tr>
    <td><a href="#TOOKMD5">MD5</a></td>
<td>
用于使用md5算法生成密码校验码。
</td>
</tr>
</table>


**注意事项**

- 使用DBMS_OBFUSCATION_TOOLKIT内置包前，需要先加载pgcrypto插件。

```
CREATE EXTENSION pgcrypto;
```

-  该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

**兼容性**

完全兼容。


#### DESENCRYPT<a id="TOOKDESENCRYPT"></a>

**语法格式**

```
DBMS_OBFUSCATION_TOOLKIT.DESEncrypt(
   input            IN    RAW,
   key              IN    RAW,
   encrypted_data   OUT   RAW);

DBMS_OBFUSCATION_TOOLKIT.DESEncrypt(
   input_string     IN    VARCHAR2,
   key_string       IN    VARCHAR2,
   encrypted_string OUT   VARCHAR2);

DBMS_OBFUSCATION_TOOLKIT.DESEncrypt(
   input         IN  RAW,
   key           IN  RAW)
  RETURN RAW;

DBMS_OBFUSCATION_TOOLKIT.DESEncrypt(
   input_string  IN  VARCHAR2,
   key_string    IN  VARCHAR2)
  RETURN VARCHAR2;
```

**参数说明**

- input：要加密的数据。
- key：加密密钥。
- encrypted_data：加密数据。
- input_string：要加密的字符串。
- key_string：加密密钥字符串。
- encrypted_string：加密字符串。

**示例**

- 对raw类型进行加密。

```
select dbms_obfuscation_toolkit.desencrypt('ABCDEFEF'::raw,'12345678'::raw);
```

返回结果为：

```
    desencrypt        
--------------------------
5837505A4D3578332F44383D
(1 row)
```

- 对text类型进行加密。

```
select dbms_obfuscation_toolkit.desencrypt('ABCDEFGH'::text,'12345678'::text);
```

返回结果为：

```
    desencrypt        
--------------------------
lt5gPq7WJW/hywLARpfLNA==
(1 row)
```

- 对中文进行加密。

```
select dbms_obfuscation_toolkit.desencrypt('中文中文中文中文'::text,'测试'::text);
```

返回结果为：

```
              desencrypt                  
----------------------------------------------
uJ0LFYdRFGzJdmhYnaG9XXKrM4wcJMz6ncu4SV204yM=
(1 row)
```

#### DESDECRYPT<a id="TOOKDESDECRYPT"></a>

**语法格式**

```
DBMS_OBFUSCATION_TOOLKIT.DESDecrypt(
   input             IN   RAW,
   key               IN   RAW,
   decrypted_data    OUT  RAW);

DBMS_OBFUSCATION_TOOLKIT.DESDecrypt(
   input_string      IN   VARCHAR2,
   key_string        IN   VARCHAR2,
   decrypted_string  OUT  VARCHAR2);

DBMS_OBFUSCATION_TOOLKIT.DESDecrypt(
   input            IN  RAW,
   key              IN  RAW)
  RETURN RAW;

DBMS_OBFUSCATION_TOOLKIT.DESDecrypt(
   input_string     IN  VARCHAR2,
   key_string       IN  VARCHAR2)
  RETURN VARCHAR2;
```

**参数说明**

- input：要加密的数据。
- key：加密密钥。
- decrypted_data：加密数据。
- input_string：要加密的字符串。
- key_string：加密密钥字符串。
- decrypted_string：加密字符串。

**示例**

- 对raw类型进行解密

```
select dbms_obfuscation_toolkit.desdecrypt(dbms_obfuscation_toolkit.desencrypt('ABCDEFEF'::raw,'12345678'::raw),'12345678'::raw);
```

返回结果为：

```
desdecrypt 
------------
ABCDEFEF
(1 row)
```

- 对text类型进行解密

```
select dbms_obfuscation_toolkit.desdecrypt(dbms_obfuscation_toolkit.desencrypt('ABCDEFGH'::text,'12345678'::text) ,'12345678'::text);
```

返回结果为：

```
desdecrypt 
------------
ABCDEFGH
(1 row)
```

#### DESGETKEY<a id="TOOKDESGETKEY"></a>

**语法格式**

```
DBMS_OBFUSCATION_TOOLKIT.DESGetKey(
   seed         IN   RAW,
   key          OUT  RAW);

DBMS_OBFUSCATION_TOOLKIT.DESGetKey(
   seed_string  IN   VARCHAR2,
   key          OUT  VARCHAR2);

DBMS_OBFUSCATION_TOOLKIT.DESGetKey(
   seed IN RAW)
 RETURN RAW;

DBMS_OBFUSCATION_TOOLKIT.DESGetKey(
   seed_string IN VARCHAR2)
 RETURN VARCHAR2;
```

**参数说明**

- seed：长度至少未80个字符的值。
- key：加密密钥。
- key_string：加密密钥字符串。

**示例**

- 用des算法随机生成加密密钥。

```
select dbms_obfuscation_toolkit.desgetkey('sucesss');
```

返回结果为：

```
desgetkey 
-----------
yxvntnex
(1 row)
```

- 用des算法随机生成text类型的加密密钥。

```
select dbms_obfuscation_toolkit.desgetkey('sucesss'::text);
```

返回结果为：

```
desgetkey 
-----------
zmuewwki
(1 row)
```

- 用des算法随机生成raw类型的加密密钥。

```
select dbms_obfuscation_toolkit.desgetkey('ABCDEFEF'::raw);
```

返回结果为：

```
desgetkey     
------------------
6F746E7073696F71
(1 row)
```

#### DES3ENCRYPT<a id="TOOKDES3ENCRYPT"></a>

**语法格式**

```
DBMS_OBFUSCATION_TOOLKIT.DES3Encrypt(
   input           IN     RAW,
   key             IN     RAW,
   encrypted_data  OUT    RAW,
   which           IN     PLS_INTEGER  DEFAULT TwoKeyMode
   iv              IN     RAW          DEFAULT NULL);

DBMS_OBFUSCATION_TOOLKIT.DES3Encrypt(
   input_string      IN     VARCHAR2,
   key_string        IN     VARCHAR2,
   encrypted_string  OUT    VARCHAR2,
   which             IN     PLS_INTEGER  DEFAULT TwoKeyMode
   iv_string         IN     VARCHAR2     DEFAULT NULL);

DBMS_OBFUSCATION_TOOLKIT.DES3Encrypt(
   input        IN RAW,
   key          IN RAW,
   which        IN PLS_INTEGER DEFAULT TwoKeyMode
   iv           IN RAW         DEFAULT NULL)
  RETURN RAW;

DBMS_OBFUSCATION_TOOLKIT.DES3Encrypt(
   input_string  IN VARCHAR2,
   key_string    IN VARCHAR2,
   which         IN PLS_INTEGER DEFAULT TwoKeyMode
   iv_string     IN VARCHAR2    DEFAULT NULL)
  RETURN VARCHAR2;
```

**参数说明**

- input：要加密的数据。
- key：加密密钥。
- encrypted_data：加密数据。
- which：如果 = 0，（默认），则TwoKeyMode使用。如果 = 1，则ThreeKeyMode使用。
- iv：初始化向量。
- input_string：要加密的字符串。
- key_string：加密密钥字符串。
- encrypted_string：加密字符串。
- iv_string：初始化向量。

**示例**

- 对text类型数据加密，密钥长度大于等于16。

```
select dbms_obfuscation_toolkit.des3encrypt('ABCDEFEF'::text,'ABCABCABCABCABCA'::text);
```

返回结果为：

```
       des3encrypt
--------------------------
 K9pRtTB3V7mVThiNt+zYmg==
(1 row)

```

#### DES3GETKEY<a id="TOOKDES3GETKEY"></a>

**语法格式**

```
DBMS_OBFUSCATION_TOOLKIT.DES3GetKey(
   which        IN   PLS_INTEGER DEFAULT TwoKeyMode,
   seed         IN   RAW,
   key          OUT  RAW);

DBMS_OBFUSCATION_TOOLKIT.DES3GetKey(
   which        IN   PLS_INTEGER DEFAULT TwoKeyMode,
   seed_string  IN   VARCHAR2,
   key          OUT  VARCHAR2);

DBMS_OBFUSCATION_TOOLKIT.DES3GetKey(
   which  IN  PLS_INTEGER DEFAULT TwoKeyMode,
   seed   IN  RAW)
 RETURN RAW;

DBMS_OBFUSCATION_TOOLKIT.DES3GetKey(
   which        IN  PLS_INTEGER DEFAULT TwoKeyMode,
   seed_string  IN  VARCHAR2)
 RETURN VARCHAR2;
```

**参数说明**

- which：如果 = 0，（默认），则TwoKeyMode使用。如果 = 1，则ThreeKeyMode使用。
- seed：长度至少为80个字符的值。
- key：加密密钥。
- seed_string：长度至少为80个字符的字符串。

**示例**

- 对text类型数据加密，加密数据字符长度大于等于80个。

```
select dbms_obfuscation_toolkit.des3getkey(0,'ABCDEFEFABCDEFEFABCDEFEFABCDEFEFABCDEFEFABCDEFEFABCDEFEFABCDEFEFABCDEFEFABCDEFFF'::text);
```

返回结果为：

```
    des3getkey
------------------
 soQKaLCAvdTZXPxJ
(1 row)
```

- 对raw类型数据加密，加密数据字符长度小于80个

```
select dbms_obfuscation_toolkit.des3getkey('ABCDEFEF'::raw);
```

返回结果为：

```
            des3getkey
----------------------------------
 69736F6E6662706D7661716768796374
(1 row)

```

#### MD5<a id="TOOKMD5"></a>

**语法格式**

```
DBMS_OBFUSCATION_TOOLKIT.MD5(
   input            IN   RAW,
   checksum         OUT  raw_checksum);

DBMS_OBFUSCATION_TOOLKIT.MD5(
   input_string     IN   VARCHAR2,
   checksum_string  OUT  varchar2_checksum);

DBMS_OBFUSCATION_TOOLKIT.MD5(
   input         IN  RAW)
  RETURN raw_checksum;

DBMS_OBFUSCATION_TOOLKIT.MD5(
   input_string  IN  VARCHAR2)
  RETURN varchar2_checksum;
```

**参数说明**

- input：要生成校验码的数据。
- checksum：128位加密信息摘要。
- input_string：要生成校验码的字符串。
- checksum_string：128位加密信息摘要。

**示例**

- 用md5算法随机生成raw类型密码校验码。

```
select dbms_obfuscation_toolkit.md5('ABCDEFEF'::raw);
```

返回结果为：

```
            md5                
----------------------------------
42BE4B48002C72898ADEDCBA9C6370F6
(1 row)
```

- 用md5算法随机生成text类型密码校验码。

```
select dbms_obfuscation_toolkit.md5('ABCDEFEF'::text);
```

返回结果为：

```
  md5      
---------------
\x18Mr_\x10s"
(1 row)
```
