# PG_COLLATION<a name="ZH-CN_TOPIC_0289900999"></a>

PG_COLLATION系统表描述可用的排序规则，本质上从一个SQL名称映射到操作系统本地类别。

**表 1**  PG_COLLATION字段<a name="zh-cn_topic_0283137275_zh-cn_topic_0237122278_zh-cn_topic_0059779096_tfef590a752224800b5cb4e1f9cb9c250"></a>

| 名称             | 类型    | 引用                                                         | 描述                                                         |
| :--------------- | :------ | :----------------------------------------------------------- | :----------------------------------------------------------- |
| collname         | name    | -                                                            | 排序规则名（每个名称空间和编码唯一）。                       |
| collnamespace    | oid     | [PG_NAMESPACE](https://docs.vastdata.com.cn/zh/docs/VastbaseG100Ver2.2.10/doc/开发者指南/PG_NAMESPACE.html).oid | 包含这个排序规则的名称空间的OID。                            |
| collowner        | oid     | [PG_AUTHID](https://docs.vastdata.com.cn/zh/docs/VastbaseG100Ver2.2.10/doc/开发者指南/PG_AUTHID.html).oid | 排序规则的所有者。                                           |
| collencoding     | integer | -                                                            | 排序规则可用的编码，兼容PostgreSQL所有的字符编码类型，如果适用于任意编码为-1。 |
| collcollate      | name    | -                                                            | 这个排序规则对象的LC_COLLATE。                               |
| collctype        | name    | -                                                            | 这个排序规则对象的LC_CTYPE。                                 |
| collpadattribute | name    | -                                                            | 列校对规则。<br>NO PAD：把字符串尾端的空格当作一个字符处理，即字符串等值比较不忽略尾端空格。<br/>PAD SPACE：字符串等值比较忽略尾端空格。 |

