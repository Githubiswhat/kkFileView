### SAMPLE抽样采集语法

**功能描述**

sample字句允许从表中的随机数据样本中进行选择，而不是从整个表中选择。

**语法格式**

```
TABLESAMPLE sampling_method ( argument [, ...] ) [ REPEATABLE ( seed ) ];
```

**参数说明**

- sampling_method：采样方法。
  - BERNOULLI：随机抽取表的数据行数据，抽样级别为数据行级别。使用全表扫描的采样方法，按采样参数百分比返回。相比于SYSTEM 具有更好的随机性，但是性能要差很多。
  - SYSTEM：块级采样方法，随机抽取表的数据块上的数据，按采样参数百分比返回（被采样到的数据块内的所有记录都将被返回）。因此离散度不如BERNOULLI，但是效率高很多。
  - SYSTEM_ROWS：tsm_system_rows模块提供了表采样方法SYSTEM_ROWS，它可以用SELECT命令的TABLESAMPLE子句中。这种表采样方法接受一个整数参数，它是要读取的最大行数。得到的采样将总是包含正好这么多行，除非该表中没有足够的行，在那种情况下整个表都会被选择出来。和内建的SYSTEM采样方法一样， SYSTEM_ROWS执行块级别的采样，所以采样不是完全随机的，而是服从于聚簇效果，特别是只要求少量行时。
    注意：SYSTEM_ROWS不支持 REPEATABLE子句。

- 要采样的表的百分数，表示为一个0到100之间的百分数。取这个参数可以是任意的实数值表达式（除了BERNOULLI以及SYSTEM采样，其他的采样方法可能接受更多或者不同的参数）.

- REPEATABLE(seed)：采样随机种子，如果种子一样多，南无多次采样得到的结果是一样的。如果忽略REPEATABLE则每次都是使用新的seed值，得到不同的结果。seed取值为任何非空浮点值。

**注意事项**

无。

**示例**

1、创建测试表并插入数据。

```
create table test_sample (id int4,message text);
insert into test_sample(id,message) select n,md5(random()::text) from generate_series(1,1000000) n;
```

2、使用SYSTEM采样方法进行选择。

```
select * from test_sample tablesample system(0.1);
```

返回结果如下，则表示采样成功：

```
   id   |             message
--------+----------------------------------
 108681 | de5d879fcd627af8a0fae0d4e8c99952
 108682 | 348e18ff6e162f774569eb0107882023
 108683 | 67520adbc0d5b6e0a98238559740abf0
 108684 | 9046cc4117baba71ee616de9e389b065
 108685 | 8300ccfb5072ae931be4d2eb82d8048a
 108686 | 08189d0630f699d10f7cf62bec2837a8
 108687 | 9035049b7ae8d70fbde7169b95ba91ec
 108688 | a8d7edb1dd058b2fc2a06d34dc036721
 108689 | 0a08c43a8d052f9e4cb6d66192b5aca5
 108690 | 3e0b3d680e2c78382210170c35d97da4
 108691 | 82b8edbaddd56a3533c166bf6110f11c
 108692 | 2174f782ee8087a4a99744581e1c90df
 108693 | 83682dfb7cb7ab64af11959226996b9e
 108694 | 82cac0957b88e4851d5503dcf2ddedb9
 108695 | 98d40852958d68f8dd469259a07953d3
 108696 | 2c3df1ff75d2bf40cf2ef130427f0099
 108697 | 306165368949c2add290394af980b20c
 108698 | 985a277365a31ae767a53af700220ff6
 108699 | 450ad7e79ded9887bf77adad59d1e30d
 108700 | 224dc5087e8238f885d68d7edb8c0a1c
 108701 | 0ce5ebfab7b11070689d31b1808038c1
 108702 | 5ac2d7bedfe0eb9026916c13e4b32b53
 108703 | 4fbe39926d7177b4ce1a418cdc1b3b1c
 108704 | 9305630170cd961cce5d391dcdbc47f2
 108705 | d0cfef23d155a8644c4f36fd235b60d2
 108706 | 1fa533a6e707a7aae2957ef9ad65414c
 108707 | 532bacb8035fbcd5e15ddd05180ad4a0
 108708 | ed7a3959905c1a5654dc00fc0456c81f
 108709 | e42540d240bb61539eaea8359943eee1
 108710 | 63f390eb4c546b8eb616bc36f8d184ab
 108711 | 01282b0d264b64711dc75a9e26f2cfa6
 108712 | 2e62cd74be4073a5b8c1c5d5dbd613f1
 108713 | 9a46e4b8b64c3d25b9a8b6c64e7f6931
 108714 | c94a77af8e839dd0237560bb4b9e74ba
 108715 | baa94146612c4723c4f8d10b95cef519
 108716 | 5142bc434e8795a83fef656392cbb67f
Cancel request sent
```

3、使用SYSTEM采样方法进行选择。

```
 select ctid,* from test_sample tablesample BERNOULLI(0.05);
```

返回结果如下，则表示采样成功过：

```
   ctid    |   id   |             message
------------+--------+----------------------------------
 (15,95)    |   1520 | fcf619fb848b2bbf4ef634f6e2917e53
 (19,79)    |   1884 | 9ad257e0a82ddd468a8b06d207b1066f
 (21,7)     |   2002 | 9c06503b99a9aa92148c3f0237de5384
 (96,11)    |   9131 | c27b199a6817f30215200af228e883de
 (106,40)   |  10110 | 194d225196af3794ffe57aabd67efc4f
 (121,16)   |  11511 | 7870876d5e85705f791714927e27fb2b
 (162,5)    |  15395 | 6dd96383b1ae18242073fbdd77b67d97
 (165,22)   |  15697 | 47a3ef5636891bff037b5d4d0daa86e1
 (178,83)   |  16993 | 6e3b6184e6435b39ad5c4be20e65c5d8
 (182,6)    |  17296 | 39f018ccf4645f4a173f3793b0219fe2
 (193,62)   |  18397 | af6316ee9907946673972245a94e4795
 (210,79)   |  20029 | 2f239bc81e70858a028efffac0875880
 (238,10)   |  22620 | f6f6d91b804ad3314d6dace36a0af3a5
 (238,52)   |  22662 | f305a3d35adf5f74d5e4fe8a5e8179cf
 (315,44)   |  29969 | 4793f9f7eae1f495231cc2c0a9589fd7
 (318,50)   |  30260 | 8f7bac87228b2ee35cb85715e24ed04c
 (341,13)   |  32408 | 2d9db260cf7b18fd69e991ed50be0695
 (360,28)   |  34228 | 7f405f35572d3d6b58c1ab27bcf30a87
 (383,50)   |  36435 | 38f3fd086c9a3ef3aa3e50e1b6282fdc
 (447,48)   |  42513 | 7de6905b59fcc6ee21e379e168d7c30c
 (447,71)   |  42536 | 69be111ed69babbafb7e1e8519be80b4
 (466,38)   |  44308 | b96a90360c187affd9ecde654490ba04
 (481,50)   |  45745 | 98e2ede63ff315e140248f72a2c9e896
 (484,29)   |  46009 | 16bd12b35fc82f98fd491757e3628377
 (484,62)   |  46042 | e151c49d5ef75098e429da334d89fbf8
 (495,3)    |  47028 | e73a88f5c4e20e88d0857b190ca1d6fb
 (503,6)    |  47791 | f083ec2b5b886e1bf2753e3861dc3aa0
 (514,71)   |  48901 | c2e1bdbaf0b972d7fe06e2fc8eb561d9
 (540,70)   |  51370 | 88fb8fb941646e9388af6af41bc9a8af
 (563,14)   |  53499 | 1ae6a5b32b5e5e2e831f6d0b18f6f507
 (576,63)   |  54783 | 0e311376ccc596be11716d237f3c677b
 (590,54)   |  56104 | b193f27a18bda8b53e1fac3c00a760af
 (592,39)   |  56279 | 3f58a044567207fd50f10c9632315248
 (617,13)   |  58628 | a677c86b9973003543618f1554250492
 (623,27)   |  59212 | 37a23fa568fc89175cc51933e541448c
 (634,95)   |  60325 | 38af9a0188c3bf09445b3b6ecdf86c28
Cancel request sent
```
