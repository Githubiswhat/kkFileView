# 安装配置python3环境

**步骤1** 切换至数据库安装用户。

```shell
su - vastbase
```

**步骤2** 创建python安装路径vb1。

```shell
mkdir /home/vb1
```

**步骤3** 下载python安装包上传到服务器安装路径中。

下载地址：https://www.python.org/downloads/source/

**步骤4** 安装gcc。

```shell
yum install -y gcc
```

**步骤5** 解压python安装包（安装包名称以实际为准）。

```shell
tar xf Python-3.8.10.tar.xz
```

**步骤6** 切换至python安装路径。

```shell
cd /home/vb1/Python-3.8.10
```

**步骤7** 运行configure脚本，为编译安装做准备。

```shell
./configure --prefix=/home/vb1 --enable-shared --with-ssl-default-suites=openssl --with-python3
```

**步骤8**  编译安装python3。

```shell
make&make install
```

**步骤9** 查看安装好的python3。

```shell
/usr/local/python3/bin/python3 -V
```

**步骤10** 设置环境变量。

```shell
vi /etc/profile.d/python3.sh
```

添加如下内容：

```text
alias python='/home/vb1/bin/python3'
```

**步骤11**  设置文件的可执行权限（root用户下）。

```shell
chmod +x /etc/profile.d/python3.sh
```

**步骤12** 重启或重新登录使环境变量生效。

**步骤13**  配置环境变量。

- PYTHONHOME：python3安装路径。
- LD_LIBRARY_PATH：python3安装路径下的lib目录。
- PATH：python3安装路径bin目录。

1、打开配置文件。

```shell
vi /home/vastbase/.Vastbase
```

2、添加如下环境变量。

```text
export PYTHONHOME=/home/vb1
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/home/vb1/lib
export PATH=$PATH:/home/vb1/bin
```

**步骤14**  重启数据库。

```shell
vb_ctl restart
```

**步骤15** 登录vastbase数据库。

```sql
vsql  -r 
```

**步骤16** 创建plpython3u插件。

```sql
create extension plpython3u;
```

