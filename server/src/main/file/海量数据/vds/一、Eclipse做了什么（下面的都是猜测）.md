### 一、Eclipse做了什么（下面的都是猜测）

1、对项目的依赖进行了管理

2、提供了一些启动的配置信息

3、点击启动按钮之后，eclipse的操作

![image-20230213095923839](C:\Users\windows\AppData\Roaming\Typora\typora-user-images\image-20230213095923839.png)

项目右键 Debug As - Debug Configuration - EclipseApplication 选中之前使用的启动的配置, 点击右下角的 Show Command Line, 会出现弹窗

点击启动按钮之后，执行的命令行如图所示。

通过上面的分析，可以得出一个结论，就是如果想要在IDEA中把eclipse rcp的项目跑起来，需要对上述内容进行一个实现。

### 二、实际操作步骤

#### 1、导入项目

![image-20230213100340303](C:\Users\windows\AppData\Roaming\Typora\typora-user-images\image-20230213100340303.png)

##### 2、添加项目模块

![image-20230213100507736](C:\Users\windows\AppData\Roaming\Typora\typora-user-images\image-20230213100507736.png)

选择 import module 

![image-20230213100633171](C:\Users\windows\AppData\Roaming\Typora\typora-user-images\image-20230213100633171.png)

##### 3、添加项目依赖

![image-20230213101141265](C:\Users\windows\AppData\Roaming\Typora\typora-user-images\image-20230213101141265.png)

导入plugin文件夹下面的jar包

#### 4、对各个模块的依赖进行管理

![image-20230213101332565](C:\Users\windows\AppData\Roaming\Typora\typora-user-images\image-20230213101332565.png)

手动添加模块依赖感觉不太现实，所以一个取巧的办法是，在上述操作完成之后，对项目进行一个编译操作，等待IDEA编译过程说少了什么依赖，然后鼠标悬浮等待一两秒，有一个可以import 对应报名的按钮，然后点击即可，这一步可能比较花时间。

#### 5、编写一个启动类

![image-20230213101733881](C:\Users\windows\AppData\Roaming\Typora\typora-user-images\image-20230213101733881.png)

对启动进行一些配置

![image-20230213101823171](C:\Users\windows\AppData\Roaming\Typora\typora-user-images\image-20230213101823171.png)

在第一步我们获取到了Eclipse启动的一些命令参数

粘贴到 Bootstrap 的 Run/Debug Configuration 的 Arguments 选项卡的 Program arguments 输入框里面 

这样就完成了Eclipse做的工作了。

所需要的参数如下图所示，对应的文件路径需要自行修改

![image-20230213102123324](C:\Users\windows\AppData\Roaming\Typora\typora-user-images\image-20230213102123324.png)

之后点击启动按钮，项目就可以正常启动了

![image-20230213102321871](C:\Users\windows\AppData\Roaming\Typora\typora-user-images\image-20230213102321871.png)

### 三、一些小坑

#### 1、编译问题

![image-20230213102444533](C:\Users\windows\AppData\Roaming\Typora\typora-user-images\image-20230213102444533.png)

图中三个Java文件中代码有问题，会导致编译不过，需要对对应的代码进行注释或者修改。

至于为什么eclipse没有这个问题，我想可能是在eclipse环境搭建文档里面把API BaseLine的等级调整了那一步操作导致的，所以eclipse可以正常进行编译。

#### 2、导入依赖问题

![image-20230213102744552](C:\Users\windows\AppData\Roaming\Typora\typora-user-images\image-20230213102744552.png)

注意在导入依赖的时候，不要导入图片里面的这几个jar包

### 四、参考文档

https://blog.csdn.net/u011039332/article/details/112724378

https://blog.csdn.net/u011039332/article/details/112723609