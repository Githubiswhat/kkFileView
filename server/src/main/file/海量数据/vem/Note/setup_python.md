### 分发工具 setuptools

曾经 Python 的分发工具是 distutils，但它无法定义包之间的依赖关系。setuptools 则是它的增强版，能帮助我们更好的创建和分发 Python 包，尤其是具有复杂依赖关系的包。其通过添加一个基本的依赖系统以及许多相关功能，弥补了该缺陷。他还提供了自动包查询程序，用来自动获取包之间的依赖关系，并完成这些包的安装，大大降低了安装各种包的难度，使之更加方便。

一般 Python 安装会自带 setuptools，如果没有可以使用 pip 安装：

```
$ pip install setuptools
```



现在打包基本分为两种：

- 二进制   bdist
- 源码   sdist 



源码为基本的打包格式：

- zip
- tar
- ztar 

之类的



二进制有常见的打包格式：

- exe
- rpm
- msi



python可以打包成wheel格式，然后上传到PyPI



PyPI 上传推荐配置

- setup.py
  - name
  - version
  - author
  - author_email
  - url
  - packages
  - description
  - package_data/data_files
- setup.cfg
- MANIFEST.in
- README.rst
- LICENSE.txt
- <项目>



### 一个完整的 setup.py 示例

    from setuptools import setup, find_packages
    
    with open('README.rst', 'r', encoding='utf-8') as rd:
        long_description = rd.read()
    
    setup(
        name="HelloWorld",
        version="0.1",
        packages=find_packages(),
        scripts=['say_hello.py'],
    
        # Project uses reStructuredText, so ensure that the docutils get
        # installed or upgraded on the target machine
        install_requires=['docutils>=0.3'],
    
        package_data={
            # If any package contains *.txt or *.rst files, include them:
            '': ['*.txt', '*.rst'],
            # And include any *.msg files found in the 'hello' package, too:
            'hello': ['*.msg'],
        },
    
        # metadata for upload to PyPI
        author="Me",
        author_email="me@example.com",
        description="This is an Example Package",
        long_description = long_description,
        license="PSF",
        keywords="hello world example examples",
        url="http://example.com/HelloWorld/", # project home page, if any
        project_urls={
            "Bug Tracker": "https://bugs.example.com/HelloWorld/",
            "Documentation": "https://docs.example.com/HelloWorld/",
            "Source Code": "https://code.example.com/HelloWorld/",
        }
    
        # could also include long_description, download_url, classifiers, etc.
    )




