### 一、 前置条件.

1. 下载安装JDK11 [http://jdk.java.net/archive/](https://gitee.com/link?target=http%3A%2F%2Fjdk.java.net%2Farchive%2F) ，并配置 `JAVA_HOME` 环境变量。JDK推荐11.0.2版本

2. 下载安装apache maven3.x，并配置 `M2_HOME` 环境变量。
   Maven推荐3.8.3版本 [https://archive.apache.org/dist/maven/maven-3/3.8.3/binaries/apache-maven-3.8.3-bin.zip](https://gitee.com/link?target=https%3A%2F%2Farchive.apache.org%2Fdist%2Fmaven%2Fmaven-3%2F3.8.3%2Fbinaries%2Fapache-maven-3.8.3-bin.zip)

3. 通过[https://gluonhq.com/products/javafx/](https://gitee.com/link?target=https%3A%2F%2Fgluonhq.com%2Fproducts%2Fjavafx%2F) 
   下载 javafx SDK 17.0.2版本SDK到本地并解压至任意目录。

4. 通过[https://downloads.efxclipse.bestsolution.at/p2-repos/openjfx.p2-17.0.2.zip](https://gitee.com/link?target=https%3A%2F%2Fdownloads.efxclipse.bestsolution.at%2Fp2-repos%2Fopenjfx.p2-17.0.2.zip)
   下载openjfx.p2-17.0.2.zip并解压到本地任意目录。

5. 配置pom文件properties配置信息 javafx.home 和 url.openjfx信息。

   **注意在linux环境下和windows环境下，路径的正反斜杠的区别**

   linux：

   ```
   <javafx.home>本地路径/javafx-sdk-17.0.2</javafx.home>
   <url.openjfx>file:///本地路径/openjfx.p2-17.0.2</url.openjfx>
   ```

   

   示例:
   将下载好的javafx-sdk-17.0.2和openjfx.p2-17.0.2解压到本地目录，配置pom

   ```
   <javafx.home>/home/ubuntu/Downloads/javafx-sdk-17.0.2</javafx.home>
   <url.openjfx>file:///home/ubuntu/Downloads/openjfx.p2-17.0.2</url.openjfx>
   ```

   

   windows：

   ```
   <javafx.home>本地路径\javafx-sdk-17.0.2</javafx.home>
   <url.openjfx>file:\\\本地路径\openjfx.p2-17.0.2</url.openjfx>
   ```

   

   示例:

   将下载好的javafx-sdk-17.0.2和openjfx.p2-17.0.2解压到本地目录D盘，配置pom

   ```
   <javafx.home>D:\javafx-sdk-17.0.2</javafx.home>
   <url.openjfx>file:\\\D:\openjfx.p2-17.0.2</url.openjfx>
   ```



1. 修改pom文件下的插件信息，也就是更换把windows操作系统的环境信息修改成linux操作系统

   ```
   <plugin>
   <groupId>org.eclipse.tycho</groupId>
   <artifactId>target-platform-configuration</artifactId>
   <version>${tycho-version}</version>
   <configuration>
       <pomDependencies>consider</pomDependencies>
       <!-- configure the p2 target environments for multi-platform build -->
       <environments>
           <environment>
               <os>linux</os>
               <ws>gtk</ws>
               <arch>x86_64</arch>
           </environment>
       </environments>
   </configuration>
   </plugin>
   ```

### 二、源码编译

1. 通过 svn 命令行下载并进入VDS 源码src目录:
2. 使用mvn -version命令 检查并确认 maven和JDK版本信息。

```
$ mvn -version
Apache Maven 3.8.3 (ff8e977a158738155dc465c6a97ffaf31982d739)
Maven home: /home/ubuntu/code/apache-maven-3.8.3-bin/apache-maven-3.8.3
Java version: 11.0.2, vendor: Oracle Corporation, runtime: /home/ubuntu/code/openjdk-11.0.2_linux-x64_bin/jdk-11.0.2
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "5.4.0-84-generic", arch: "amd64", family: "unix"
```

3. 修改 `${Data_Studio_code}/code/datastudio/src/org.opengauss.mppdbide.view/META-INF`  中的  `openjfx.graphics.win32_64;bundle-version="17.0.2"`   为  `openjfx.graphics.linux_64;bundle-version="17.0.2"` 
4. 运行编译脚本

```
cd ${Data_Studio_code}/code/datastudio/src
sh copyExternalsToBuild.sh
mvn clean package -Dmaven.test.skip=true
```

5. 生成的安装包位置为：
   `${Data_Studio_code}/code/datastudio/build`



##### 备注：

如果打包工程中提示找不到  `openjfx.graphics.linux_64;bundle-version="17.0.2"` 这个包

有可能是上面第五步的配置中的 `<url.openjfx>file:///本地路径/openjfx.p2-17.0.2</url.openjfx>` 没有写对

如果确定不是路径写错，可以手动在本地新建`.m2\repository\p2\osgi\bundle\org.eclipse.swt.win32.win32.x86_64\`文件夹，然后再里面放入对应的jar包即可
