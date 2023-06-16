## BFILE数据类型

**功能描述**

bfile数据类型用于存储服务器文件系统中的物理二进制文件数据对象。bfile文件存储在操作系统文件中，而不是在数据库中，bfile列存储对操作系统文件的引用。操作系统访问的任何存储设备都可以保存bfile数据，包括硬盘驱动器、CD-ROM、PhotoCD和DVD。如果操作系统支持对操作系统文件的流模式访问，则数据库可以访问bfile。

bfile类型的数据是：

- 在应用程序运行时不会更改的二进制数据，例如图形。
- 加载到其他大型对象类型（例如BLOB或CLOB）中的数据，可以在其中操作数据。
- 适用于字节流访问的数据，例如多媒体数据。

在对bfile类型的SQL操作中，需要支持bfilename函数。bfilename函数返回一个bfile文件定位器，该定位器指向操作系统中一个物理文件。

#### bfilename函数

bfilename函数主要用于bfile类型数据的构造，构造的数据源为一个directory对象和一个操作系统物理二进制文件名，函数语法格式为：

```
BFILENAME('directory', 'filename')
```

- directory对象：指向一个操作系统的物理目录。该目录不应为数据库数据目录，以及操作系统控制文件、日志文件和其他系统文件所在的目录。必须在bfilename函数调用前创建，并且对名区分大小写。
- filename：文件名，操作系统上一个物理二进制文件，由于bfile为只读数据类型，必须在访问文件数据前创建该文件。

bfile数据类型主要用于对bfilename函数构造的数据进行存储，然后调用dbms_lob内置包的接口对该数据进行操作。bfile数据类型的功能主要分为三个部分：

- bfile数据的存储和打印。
- bfile数据的使用，主要为对操作系统物理文件的读取过程，即分为以下三部分：
  - 打开操作系统物理文件。bfile为只读数据类型，因此打开文件的模式只能为只读。
  - 对文件进行读取。可根据参数设置来决定读取文件的字节数，如需读取整个文件，可先调用dbms_lob包的getlength，函数获取文件的大小。需注意的是，单次读取文件的字节数不能超过32767字节，如文件过大，可通过设置参数对文件进行多次读取。
  - 关闭文件。对打开的物理文件进行关闭。

bfile数据的修改：对于bfile数据的持久引用（如存储在表中），bfile数据可以作为表的列值进行修改。

**注意事项**

- bfilename函数依赖openGauss自带的directory功能。
- 读取BFILE字段需要使用DBMS_LOB包。

**使用流程**

1、创建directory对象，创建需访问的文件并将其置于directory对象所指向的操作系统目录中。

2、创建包含bfile数据类型列的表。

3、调用bfilename函数对数据进行构造并插入到表中进行存储。

**示例**

1、操作系统环境下创建文件，并输入数据。

```
 vi /home/vastbase/bfile.data
 cat /home/vastbase/bfile.data
```

文件内容为：

```
张三
李四
```

2、在数据库中执行如下命令创建目录和表。

```
drop directory d_bfile;
drop table testbfile1;
create directory d_bfile as '/home/vastbase';
create table testbfile1(id number,bfile_name bfile);
```

3、插入数据。

```
insert into testbfile1 values (1,bfilename('d_bfile','bfile.data'));
```

4、查询数据。

```
select * from  testbfile1;
```

返回结果为：

```
 id |            bfile_name
----+-----------------------------------
  1 | bfilename('d_bfile','bfile.data')
```
