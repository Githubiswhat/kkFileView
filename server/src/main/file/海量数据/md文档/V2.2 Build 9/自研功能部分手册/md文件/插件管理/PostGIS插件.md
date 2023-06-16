#### PostGIS插件

**功能描述**

PostGIS是在对象关系型数据库PostgreSQL上增加了存储管理空间数据的能力的开源GIS数据库，它在PostgreSQL的基础上增加了表达地理信息的空间数据类型和操作这些类型的函数。

Vastbase G100 V2.2版本可使用PostGIS的如下模块：

- 支持矢量分析的postgis模块。

- 支持计算几何的sfcgal模块。

- 支持栅格分析的raster模块。

- 支持拓扑分析的topology模块。

**注意事项**

- Vastbase G100 V2.2版本暂不支持的模块包括Address Standardizer和Tiger Geocoder。

- Vastbase创建函数不支持添加WINDOW关键字，所以不支持函数ST_ClusterDBSCAN和ST_ClusterKMeans。

- Vastbase不支持BRIN 索引，所以不支持geog_brin_inclusion_add_value和geom2d_brin_inclusion_add_value等相关函数。

- Vastbase不支持WITH ORDINALITY功能，所以topology.ValidateTopology提供的功能与PostGIS 3.2版本不一致。

- 不支持PostGIS 3.2版本中的ST_AsGeoJson函数。

**示例**

1、获取<a href='http://download.osgeo.org/postgis/source/'>postgis</a>插件

2、解压得到postgis文件夹，将其中的内容复制到GAUSSHOME中（GAUSSHOME为数据库的安装路径）

```
cp -r postgis/* $GAUSSHOME
```

3、yum安装相关依赖（如果不能使用yum可下载对应版本的安装包进行安装）

```
yum install -y gmp gmp-devel            （版本6.0.0）
yum install -y mpfr mpfr-devel            （版本3.1.1）
yum install -y boost boost-devel         （版本1.53.0）
```

3.启动数据库并设置guc参数behavior_compat_options（PGDATA为数据库的实例路径）

```
vb_ctl start
vb_guc reload -D $PGDATA -c " behavior_compat_options = 'bind_procedure_searchpath, display_leading_zero' "
```

4.重启并连接数据库（可能需要指定路径和端口）

```
vb_ctl restart
vsql vastbase -r
```

5、创建插件。

```
create  extension  postgis;
create  extension  postgis_sfcgal;
create  extension  postgis_raster;
create  extension  postgis_topology;
```

6、查询测试。

```
SELECT Box3D(ST_GeomFromEWKT('LINESTRING(1 2 3, 3 4 5, 5 6 5)'));
```

结果返回如下：

```
Box3d
---------
BOX3D(1 2 3,5 6 5)
```

7、删除插件

```
drop  extension  postgis  cascade;
```

