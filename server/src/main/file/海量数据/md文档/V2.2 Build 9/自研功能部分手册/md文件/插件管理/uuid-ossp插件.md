#### uuid-ossp插件

**功能描述**

uuid-ossp模块提供了一些函数用来生成通用唯一识别码（UUID），兼容uuid-ossp1.1，使用该模块提供的函数，采用几种标准算法之一产生通用唯一标识符（UUID）。

兼容的uuid-ossp模块的函数分为如下两类：

- 用于产生uuid的函数：

| 函数                                        | 描述                                                         |
| ------------------------------------------- | ------------------------------------------------------------ |
| uuid_generate_v1()                          | 这个函数产生一个版本 1 的 UUID。这涉及到计算机的 MAC 地址和一个时间戳。注：这种 UUID 会泄露产生该标识符的计算机标识以及产生的时间，因此它不适合某些对安全性很敏感的应用。 |
| uuid_generate_v1mc()                        | 这个函数产生一个版本 1 的 UUID，但是使用一个随机广播 MAC 地址而不是该计算机真实的 MAC 地址。 |
| uuid_generate_v3(namespace uuid, name text) | 这个函数使用指定的输入名称在给定名字的空间中产生一个版本 3 的 UUID。该名字空间应该是由uuid_ns_*()函数产生的特殊常量之一（理论上它可以是任意 UUID）。名称是选择的名字空间中的一个标识符。例如：vastbase=# SELECT uuid_generate_v3(uuid_ns_url(), 'http://www.postgresql.org');名称参数将使用 MD5 进行哈希，因此从产生的 UUID 中得不到明文。采用这种方法的 UUID 生成没有随机性并且不涉及依赖于环境的元素，因此是可以重现的。 |
| uuid_generate_v4()                          | 这个函数产生一个版本 4 的 UUID，它完全从随机数产生。         |
| uuid_generate_v5(namespace uuid, name text) | 这个函数产生一个版本 5 的 UUID，它和版本 3 的 UUID 相似，但是采用的是 SHA-1 作为哈希方法。注：版本 5 比版本 3 更好，因为 SHA-1 比 MD5 更安全。 |

- 返回 uuid常量的函数：

| 函数           | 描述                                                         |
| -------------- | ------------------------------------------------------------ |
| uuid_nil()     | 一个“nil” UUID 常量，它不作为一个真正的 UUID 发生。          |
| uuid_ns_dns()  | 为 UUID 指定 DNS 名字空间的常量。                            |
| uuid_ns_url()  | 为 UUID 指定 URL 名字空间的常量。                            |
| uuid_ns_oid()  | 为 UUID 指定 ISO 对象标识符（OID） 名字空间的常量（这属于 ASN.1 OID，它与PostgreSQL使用的 OID 无关）。 |
| uuid_ns_x500() | 为 UUID 指定 X.500 可识别名（DN）名字空间的常量。Constant designating the X.500 distinguished name (DN) namespace for UUIDs。 |



**注意事项**

- Vastbase G100 V2.2版本兼容uuid-ossp插件目前仅兼容ossp，对bsd和e2fs不做硬性要求。

- Vastbase G100 V2.2已经内置了uuid-ossp扩展，您只需要创建扩展即可使用。

**示例**

示例一：

1、创建uuid-ossp扩展。

```
create extension "uuid-ossp";
```

2、使用产生uuid的函数。

- 测试uuid_generate_v1()。

```
select  uuid_generate_v1();
```

结果显示如下：

```
                 uuid_generate_v1
--------------------------------------
  bdf064b8-baef-11ec-8838-000c2948c478
  (1 row)
```

- 测试uuid_generate_v1mc()。

```
select  uuid_generate_v1mc();
```

 结果显示如下：

```
              uuid_generate_v1mc
--------------------------------------
  ce8693b0-baef-11ec-8839-83581df53531
(1 row)
```

- 测试uuid_generate_v3(namespace uuid, name text)。

```
SELECT  uuid_generate_v3(uuid_ns_url(),'http://www.postgresql.org');
```

结果显示如下：

```
                 uuid_generate_v3
--------------------------------------
  cf16fe52-3365-3a1f-8572-288d8d2aaa46
(1 row)
```

- 测试uuid_generate_v4()。

```
select  uuid_generate_v4();
```

 结果显示如下：

```
                uuid_generate_v4
--------------------------------------
  3cad2542-a2a5-43ed-9df4-0dacb2f1d52f
(1 row)
```

- 测试uuid_generate_v5(namespace uuid, name text)。

```
SELECT  uuid_generate_v5(uuid_ns_url(),'http://www.postgresql.org');
```

结果显示如下：   

```
              uuid_generate_v5
--------------------------------------
  e1ee1ad4-cd4e-5889-962a-4f605a68d94e
(1 row)
```

示例二：返回uuid常量的函数

- 测试uuid_nil()函数。

```
select  uuid_nil();
```

结果显示如下：

```
                       uuid_nil
--------------------------------------
  00000000-0000-0000-0000-000000000000
(1 row)
```

- 测试uuid_ns_dns()函数。

```
select  uuid_ns_dns();
```

结果显示如下：

```
                    uuid_ns_dns
--------------------------------------
  6ba7b810-9dad-11d1-80b4-00c04fd430c8
(1 row)
```

- 测试uuid_ns_url()函数。

```
select  uuid_ns_url();
```

结果显示如下：

```
                    uuid_ns_url
--------------------------------------
  6ba7b811-9dad-11d1-80b4-00c04fd430c8
(1 row)
```

- 测试uuid_ns_oid()函数。

```
select  uuid_ns_oid();
```

结果显示如下：

```
                    uuid_ns_oid
--------------------------------------
  6ba7b812-9dad-11d1-80b4-00c04fd430c8
(1 row)
```

- 测试uuid_ns_x500()函数。

```
select  uuid_ns_x500();
```

 结果显示如下：

```
                   uuid_ns_x500
--------------------------------------
  6ba7b814-9dad-11d1-80b4-00c04fd430c8
(1 row)
```

