#### ALL_TYPES

ALL_TYPES视图提供当前用户可访问数据库的对象类型信息。

| 列名             | 类型         | 描述                                            |
| ---------------- | ------------ | ----------------------------------------------- |
| owner            | text         | 类型的所有者                                    |
| type_name        | text         | 类型的名称                                      |
| type_oid         | oid          | 类型的编号                                      |
| typecode         | text         | 类型代码，可能值：object、collection、other     |
| attributes       | integer      | 类型中的属性数（如果有）                        |
| methods          | numeric      | 类型中的方法数（如果有）                        |
| predefined       | varchar(3)   | 类型是否为预定义类型 (  YES ) 或不是 (  NO )    |
| incomplete       | varchar(3)   | 类型是否为不完整类型 (  YES ) 或不是 (  NO )    |
| final            | varchar(3)   | 类型是否为最终类型 (  YES ) 或不是 (  NO )      |
| instantiable     | varchar(3)   | 类型是否为可实例化类型 (  YES ) 或不是 (  NO )  |
| supertype_owner  | varchar(128) | 超类型的所有者（如果类型不是子类型，则为 NULL） |
| supertype_name   | varchar(128) | 超类型的名称（如果类型不是子类型，则为 NULL）   |
| local_attributes | numeric      | 子类型中本地（非继承）属性（如果有）的数量      |
| local_methods    | numeric      | 子类型中本地（非继承）方法（如果有）的数量      |
| typeid           | raw          | 类型 ID 值                                      |
