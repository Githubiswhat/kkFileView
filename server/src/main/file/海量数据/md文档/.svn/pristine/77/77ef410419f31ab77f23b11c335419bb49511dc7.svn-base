### DBMS_XMLDOM

**功能描述**

dbms_xmldom包用于访问xmltype对象，并实现文档对象模型（DOM）,是用于xml和HTML文档的应用程序编程接口（API）。

DOM把xml文档当成树结构来查看，通过DOM树能访问、修改、删除文档中所有元素以及创建新的元素并插入树中，这些元素、文本以及它们的属性都被认为是节点，这些节点之间是一个继承层次结构。继承层次结构如下图所示：

<div ><img src="image/DOM继承层次结构.png" style="zoom:150%" )</div>

**节点类型**

- DOMNode类型：即Node节点。代表xml文档树中一个单独的节点，可以是其他任何一种节点。

- DOMAttr类型：即Attr节点。表示Element节点中的属性。

- DOMElement类型：即Element节点，代表xml文档中的一个元素。元素可以包含属性，其他元素或文本。

- DOMText类型：即Text节点。表示元素或属性的文本内容。

- DOMCDATASection类型：即CDATASection节点。表示xml文档中的CDATA区段，CDATA区段时一段不会被解析器解析的文本。

- DOMComment类型：即Comment节点。表示xml文档中注释节点的内容。

- DOMEntity类型：即Entity节点。在xml文档中频繁使用某一条数据时，可以预定义一个这条数据的“别名”，即一个Entity，然后在文档中需要的地方进行调用。

- DOMDocument类型：即Documnet节点。代表整个xml文档，是文档树的根，并提供了对文档数据访问的顶层入口。

- DOMDocumentFragment类型：即DocumentFragment节点。文档中的一部分，表示一个或多个邻接的Document节点和它们的所有子孙节点。DocumentFragment节点不属于文档树。

- DOMNotation类型：即Notation元素。Notation元素描述xml文档中非xml数据的格式。

- DOMDocumentType类型：即DocumentType节点。每个xml文档均有一个DOCTYPE属性，此属性的值可谓空，也可以是一个DocumentType对象。DocumentType对象为xml定义的实体提供接口。

- DOMProcessingInstruction类型：即ProcessingInstruction节点。表示xml文档中的一个处理指令。

- DOMText类型：即DOMText节点。XML文档中的文本。

该内置包包含以下函数：

| 函数                                                         | 描述                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [dbms_xmldom.makeNode](#DOMmakeNode)                         | 将其他节点类型转换成DOMNode类型，以便调用DOMNode的方法。     |
| [dbms_xmldom.isNull](#DOMisNull)                             | 检查输入的对象是否为空，为空则返回true，否则返回false。      |
| [dbms_xmldom.freeNode](#DOMfreeNode)                         | 释放与DOMNode关联的所有资源。                                |
| [dbms_xmldom.freeNodeList](#DOMfreeNodeList)                 | 释放与节点列表关联的所有资源。                               |
| [dbms_xmldom.freeDocument](#DOMfreeDocument)                 | 释放一个DOMDocument对象的所有资源。                          |
| [dbms_xmldom.getFirstChild](#DOMgetFirstChild)               | 返回此节点的第一个子节点，如果没有则返回NULL。               |
| [dbms_xmldom.getLocalName](#DOMgetLocalName)                 | 返回节点名称的本地部分。                                     |
| [dbms_xmldom.getNodeType](#DOMgetNodeType)                   | 返回节点类型。                                               |
| [dbms_xmldom.writeToClob](#DOMwriteToClob)                   | 使用数据库字符集将xml节点或xml文档写入指定的clob。           |
| [dbms_xmldom.writeToBuffer](#DOMwriteToBuffer)               | 使用数据库字符集将xml节点或xml文档写入指定的缓冲区。         |
| [dbms_xmldom.getChildNodes](#DOMgetChildNodes)               | 返回当前节点的所有子节点。                                   |
| [dbms_xmldom.getLength](#DOMgetLength)                       | 返回当前对象的长度。                                         |
| [dbms_xmldom.item](#DOMitem)                                 | 返回节点列表或DOMNamedNodeMap无序列表中index参数对应的项。如果index大于或等于列表中的节点数，则返回空。 |
| [dbms_xmldom.makeElement](#DOMmakeElement)                   | 将DOMNode节点转换成DOMElement节点类型。该函数主要用于将DOMElement节点转换成的DOMNode转换回DOMElement，因此除了节点类型为ELEMENT_NODE的节点，其他节点类型不能调用该函数。 |
| [dbms_xmldom.getElementsByTagName](#DOMgetElementsByTagName) | 返回包含指定名称的所有DOMElement的DOMNodeList。              |
| [dbms_xmldom.cloneNode](#DOMcloneNode)                       | 返回此节点的副本，并用作节点的通用复制构造函数。拷贝的节点没有父节点，父节点为空。 |
| [dbms_xmldom.getNodeName](#DOMgetNodeName)                   | 返回节点的节点名称。                                         |
| [dbms_xmldom.createDocument](#DOMcreateDocument)             | 通过制定的命名空间URI、根元素名和doctype创建一个xml文档。    |
| [dbms_xmldom.createElement](#DOMcreateElement)               | 用于创建一个DOMElement节点。                                 |
| [dbms_xmldom.createDocumentFragment](#DOMcreateDocumentFragment) | 用于创建一个DOMDocumentFragment节点。                        |
| [dbms_xmldom.createTextNode](#DOMcreateTextNode)             | 用于创建一个DOMText节点。                                    |
| [dbms_xmldom.createComment](#DOMcreateComment)               | 用于创建一个DOMComment节点。                                 |
| [dbms_xmldom.createCDATASection](#DOMcreateCDATASection)     | 用于创建一个DOMCDATASection节点。                            |
| [dbms_xmldom.createProcessingInstruction](#DOMcreateProcessingInstruction) | 用于创建一个DOMProcessingInstruction节点。                   |
| [dbms_xmldom.createAttribute](#DOMcreateAttribute)           | 用于创建一个DOMAttr节点。                                    |
| [dbms_xmldom.appendChild](#DOMappendChild)                   | 用于将节点newchild添加到该节点的子节点列表的末尾，并返回新添加的节点。如果newchild已经在树中，则先删除。 |
| [dbms_xmldom.getDocumentElement](#DOMgetDocumentElement)     | 用于返回xml文档的根元素节点。                                |
| [dbms_xmldom.setAttribute](#DOMsetAttribute)                 | 用于通过名称设置DOMElement属性的值。                         |
| [dbms_xmldom.setAttributeNode](#DOMsetAttributeNode)         | 用于向DOMElement添加一个新的属性节点。                       |
| [dbms_xmldom.getAttributes](#DOMgetAttributes)               | 用于检索包含此节点属性的NamedNodeMap，只有DOMElement包含该属性，其他节点则返回空。 |
| [dbms_xmldom.getNodeValue](#DOMgetNodeValue)                 | 根据节点的类型获取该节点的值。                               |
| [dbms_xmldom.getNodeValueAsClob](#DOMgetNodeValueAsClob)     | 根据节点的类型获取该节点的值并写入clob中。                   |
| [dbms_xmldom.getChildrenByTagName](#DOMgetChildrenByTagName) | 用于根据指定的名称返回DOMELement的子节点。                   |
| [dbms_xmldom.getOwnerDocument](#DOMgetOwnerDocument)         | 用于检索与此节点关联的Document对象。                         |
| [dbms_xmldom.newDOMDocument](#DOMnewDOMDocument)             | 返回一个新DOMDocument实例。                                  |
| [dbms_xmldom.hasChildNodes](#DOMhasChildNodes)               | 数返回该节点是否有子节点。                                   |
| [dbms_xmldom.insertBefore](#DOMinsertBefore)                 | 用于将节点newchild插入到现有的子节点refchild之前。           |
| [dbms_xmldom.setVersion](#DOMsetVersion)                     | 用于设置xml文档的版本号。                                    |

**注意事项**

- 该功能仅在数据库兼容模式为Oracle时能够使用（即创建DB时DBCOMPATIBILITY='A'），在其他数据库兼容模式下不能使用该特性。

- 系统已经安装python3。

- 创建plpython3u插件，需设置环境变量PYTHONHOME，包括以下内容（重启数据库生效）：

  - PYTHONHOME：python3安装路径。

  - LD_LIBRARY_PATH：增加python3安装路径下的lib目录。

  - PATH：增加python3安装路径bin目录。

#### dbms_xmldom.makeNode<a id="DOMmakeNode"></a>

**语法格式**

```
makeNode(t dbms_xmldom.DOMText)
makeNode(com dbms_xmldom.DOMComment)
makeNode(cds dbms_xmldom.DOMCDATASection)
makeNode(dt dbms_xmldom.DOMDocumentType)
makeNode(n dbms_xmldom.DOMNotation)
makeNode(ent dbms_xmldom.DOMEntity)
makeNode(pi dbms_xmldom.DOMProcessingInstruction)
makeNode(df dbms_xmldom.DOMDocumentFragment)
makeNode(doc dbms_xmldom.DOMDocument)
makeNode(elem dbms_xmldom.DOMElement)
```

**参数说明**

- t：待转换的DOMText对象

- com：待转换的DOMComment对象

- cds：待转换的DOMCDATASection对象

- dt：待转换的DOMDocumentType对象
- n：待转换的DOMNotation对象
- ent：待转换的DOMEntity对象
- pi：待转换的DOMProcessingInstruction对象
- df：待转换的DOMDocumentFragment对象
- doc：待转换的DOMDocument对象
- elem：待转换的DOMElement

#### dbms_xmldom.isNull<a id="DOMisNull"></a>

**语法格式**

```
isNull(n dbms_xmldom.DOMNode)
isNull(di dbms_xmldom.DOMImplementation)
isNull(nl dbms_xmldom.DOMNodeList)
isNull(nnm dbms_xmldom.DOMNamedNodeMap)
isNull(cd dbms_xmldom.DOMCharacterData)
isNull(a dbms_xmldom.DOMAttr)
isNull(elem dbms_xmldom.DOMElement)
isNull(t dbms_xmldom.DOMText)
isNull(com dbms_xmldom.DOMComment)
isNull(cds dbms_xmldom.DOMCDATASection)
isNull(dt dbms_xmldom.DOMDocumentType)
isNull(n dbms_xmldom.DOMNotation)
isNull(ent dbms_xmldom.DOMEntity)
isNull(pi dbms_xmldom.DOMProcessingInstruction)
isNull(df dbms_xmldom.DOMDocumentFragment)
isNull(doc dbms_xmldom.DOMDocument)
```

#### dbms_xmldom.freeNode<a id="DOMfreeNode"></a>

**语法格式**

```
freeNode(n dbms_xmldom.DOMnode)
```

**参数说明**

nl：待释放的节点列表。

#### dbms_xmldom.freeDocument<a id="DOMfreeDocument"></a>

**语法格式**

```
freeDocument(doc dbms_xmldom.DOMDocument)
```

**参数说明**

doc：待释放的DOMDocument节点

#### dbms_xmldom.getFirstChild<a id="DOMgetFirstChild"></a>

**语法格式**

```
getFirstChild(n dbms_xmldom.DOMNode)
```

**参数说明**

n：DOMNode对象

#### dbms_xmldom.getLocalName<a id="DOMgetLocalName"></a>

**语法格式**

```
getLocalName(a dbms_xmldom.DOMAttr)
getLocalName(elem dbms_xmldom.DOMElement)
getLocalName(n dbms_xmldom.DOMnode, data OUT VARCHAR2)
```

**参数说明**

- a：DOMAtrr对象

- elem：DOMElement对象

- n：DOMNode对象

#### dbms_xmldom.getNodeType<a id="DOMgetNodeType"></a>

**语法格式**

```
getNodeType(n dbms_xmldom.DOMNode)
```

**参数说明**

n：DOMNode对象

#### dbms_xmldom.writeToClob<a id="DOMwriteToClob"></a>

**语法格式**

```
writeToClob(n dbms_xmldom.DOMNode, cl IN OUT CLOB)
writeToClob(n dbms_xmldom.DOMNode, cl IN OUT CLOB, pflag IN NUMBER, indent IN NUMBER)
writeToClob(doc dbms_xmldom.DOMDocument, cl IN OUT CLOB)
writeToClob(doc dbms_xmldom.DOMDocument, cl IN OUT CLOB, pflag IN NUMBER, indent IN NUMBER)
```

**参数说明**

- n：DOMNode对象
- cl：指定的clob
- pflag：换行标识。该参数Oracle官方文档无明确说明，经过测试得知取值范围为0-72的整数，所有低三位为4或5的值则表示不换行，其他值则换行。
- indent：缩进长度。该参数Oracle官方文档无明确说明，经过测试得知取值范围为0-12的整数，数值大小表示缩进的空格个数。缩进长度只在pflag为换行时生效。
- doc：xml文档

#### dbms_xmldom.writeToBuffer<a id="DOMwriteToBuffer"></a>

**语法格式**

```
writeToBuffer(n dbms_xmldom.DOMNode, buffer IN OUT VARCHAR2)
writeToBuffer(doc dbms_xmldom.DOMDocument, buffer IN OUT VARCHAR2)
writeToBuffer(df dbms_xmldom.DOMDocumentFragment, buffer IN OUT VARCHAR2)
```

**参数说明**

- n：DOMNode节点对象
- doc：xml文档对象
- df：DOMDocumentFragment对象
- buffer：指定的缓冲区

#### dbms_xmldom.getChildNodes<a id="DOMgetChildNodes"></a>

**语法格式**

```
getChildNodes(n dbms_xmldom.DOMNode)
```

**参数说明**

n：DOMNode节点对象

#### dbms_xmldom.getLength<a id="DOMgetLength"></a>

**语法格式**

```
getLength(nl dbms_xmldom.DOMNodeList)
getLength(nnm dbms_xmldom.DOMNamedNodeMap)
getLength(cd dbms_xmldom.DOMCharacterData)
```

**参数说明**

- nl：DOMNodeList对象

- nnm：DOMNamedNodeMap对象

- cd：DOMCharacterData对象

#### dbms_xmldom.item<a id="DOMitem"></a>

**语法格式**

```
item(nl dbms_xmldom.DOMNodeList, idx IN PLS_INTEGER)
item(nnm dbms_xmldom.DOMNamedNodeMap, idx IN PLS_INTEGER)
```

**参数说明**

- nl：节点列表
- nnm：DOMNamedNodeMap无序列表
- idx：在列表中要检索的索引值

#### dbms_xmldom.makeElement<a id="DOMmakeElement"></a>

**语法格式**

```
makeElement(n dbms_xmldom.DOMNode)
```

**参数说明**

n：DOMNode对象

#### dbms_xmldom.getElementsByTagName<a id="DOMgetElementsByTagName"></a>

**语法格式**

```
getElementsByTagName(elem dbms_xmldom.DOMElement, name IN VARCHAR2)
getElementsByTagName(elem dbms_xmldom.DOMElement, name IN VARCHAR2, ns varchar2)
getElementsByTagName(doc dbms_xmldom.DOMDocument, tagname IN VARCHAR2)
```

**参数说明**

- elem：DOMElement对象
- name：指定名称
- ns：指定的命名空间
- doc：DOMDocument对象

#### dbms_xmldom.cloneNode<a id="DOMcloneNode"></a>

**语法格式**

```
cloneNode(n dbms_xmldom.DOMNode, deep boolean)
```

**参数说明**

- n：DOMNode对象

- deep：是否拷贝子节点

#### dbms_xmldom.getNodeName<a id="DOMgetNodeName"></a>

**语法格式**

```
getNodeName(n dbms_xmldom.DOMNode)
```

**参数说明**

n：DOMNode对象

#### dbms_xmldom.createDocument<a id="DOMcreateDocument"></a>

**语法格式**

```
createDocument(
    namespaceuri IN VARCHAR2, 
    qualifiedname IN VARCHAR2, 
    doctype IN dbms_xmldom.DOMType:= NULL)
```

**语法格式**

- namespaceuri ：命名空间URI
- qualifiedname ：根元素名
- doctype：documentType对象

#### dbms_xmldom.createElement<a id="DOMcreateElement"></a>

**语法格式**

```
createElement(doc dbms_xmldom.DOMDocument, tagname IN VARCHAR2)
createElement(doc dbms_xmldom.DOMDocument, tagname IN VARCHAR2, ns IN VARCHAR2)
```

**参数说明**

- doc：xml文档
- tagname：DOMElement节点的名称
- ns：命名空间URI

#### dbms_xmldom.createDocumentFragment<a id="DOMcreateDocumentFragment"></a>

**语法格式**

```
createDocumentFragment(doc dbms_xmldom.DOMDocument)
```

**参数说明**

doc：xml文档

#### dbms_xmldom.createTextNode<a id="DOMcreateTextNode"></a>

**语法格式**

```
createTextNode(doc dbms_xmldom.DOMDocument, data IN VARCHAR2)
```

**参数说明**

- doc：xml文档

- data：DOMText节点的内容

#### dbms_xmldom.createComment<a id="DOMcreateComment"></a>

**语法格式**

```
createComment(doc dbms_xmldom.DOMDocument, data IN VARCHAR2)
```

**参数说明**

- doc：xml文档
- data：DOMComment节点的内容

#### dbms_xmldom.createCDATASection<a id="DOMcreateCDATASection"></a>

**语法格式**

```
createCDATASection(doc dbms_xmldom.DOMDocument, data IN VARCHAR2)
```

**参数说明**

- doc：xml文档
- data：DOMCDATASection节点的内容

#### dbms_xmldom.createProcessingInstruction<a id="DOMcreateProcessingInstruction"></a>

**语法格式**

```
createProcessingInstruction(doc dbms_xmldom.DOMDocument, target IN VARCHAR2, data IN VARCHAR2)
```

**参数说明**

- doc：xml文档
- target：处理指令的目标
- data：处理指令的内容文本

#### dbms_xmldom.createAttribute<a id="DOMcreateAttribute"></a>

**语法格式**

```
createAttribute(doc dbms_xmldom.DOMDocument, name IN VARCHAR2)
createAttribute(doc dbms_xmldom.DOMDocument, name IN VARCHAR2, ns IN VARCHAR2)
```

**参数说明**

- doc：xml文档

- name：属性的名称
- ns：命名空间URI

#### dbms_xmldom.appendChild<a id="DOMappendChild"></a>

**语法格式**

```
appendChild(n dbms_xmldom.DOMNode, newchild IN dbms_xmldom.DOMNode)
```

**参数说明**

- n：DOMNode节点
- newchild ：待添加的子节点

#### dbms_xmldom.getDocumentElement<a id="DOMgetDocumentElement"></a>

**语法格式**

```
getDocumentElement(doc dbms_xmldom.DOMDocument)
```

**参数说明**

doc：xml文档

#### dbms_xmldom.setAttribute<a id="DOMsetAttribute"></a>

**语法格式**

```
setAttribute(elem dbms_xmldom.DOMElement, name IN VARCHAR2, newvalue IN VARCHAR2)
setAttribute(elem dbms_xmldom.DOMElement, name IN VARCHAR2, newvalue IN VARCHAR2, ns IN VARCHAR2)
```

**参数说明**

- elem：DOMElement节点
- name：属性名
- newvalue：待设置的值
- ns：命名空间URI

#### dbms_xmldom.setAttributeNode<a id="DOMsetAttributeNode"></a>

**语法格式**

```
setAttributeNode(elem dbms_xmldom.DOMElement, newattr IN dbms_xmldom.DOMAttr)
setAttributeNode(elem dbms_xmldom.DOMElement, newattr IN dbms_xmldom.DOMAttr, ns IN VARCHAR2)
```

**参数说明**

- elem：DOMElement节点
- newattr：新的属性节点
- ns：命名空间URI

#### dbms_xmldom.getAttributes<a id="DOMgetAttributes"></a>

**语法格式**

```
getAttributes(n dbms_xmldom.DOMNode)
```

**参数说明**

n：DOMNode节点

#### dbms_xmldom.getNodeValueAsClob<a id="DOMgetNodeValueAsClob"></a>

**语法格式**

```
getNodeValueAsClob(n dbms_xmldom.domnode)
```

**参数说明**

n：DOMNode节点

#### dbms_xmldom.getChildrenByTagName<a id="DOMgetChildrenByTagName"></a>

**语法格式**

```
getChildrenByTagName(elem dbms_xmldom.DOMElement, name varchar2)
getChildrenByTagName(elem dbms_xmldom.DOMElement, name varchar2, ns varchar2)
```

**参数说明**

- elem：DOMELement节点
- name：指定的名称

#### dbms_xmldom.getOwnerDocument<a id="DOMgetOwnerDocument"></a>

**语法格式**

```
getOwnerDocument(n dbms_xmldom.DOMNode)
```

**参数说明**

n：DOMNode节点

#### dbms_xmldom.newDOMDocument<a id="DOMnewDOMDocument"></a>

**语法格式**

```
newDOMDocument()
newDOMDocument(xmldoc IN xmltype)
newDOMDocument(cl IN clob)
```

**参数说明**

- xmldoc ：DOMDocument的xmltype的源数据
- cl：DOMDocument的clob的源数据

#### dbms_xmldom.hasChildNodes<a id="DOMhasChildNodes"></a>

**语法格式**

```
hasChildNodes(n dbms_xmldom.DOMNode)
```

**参数说明**

n：DOMNode节点

#### dbms_xmldom.insertBefore<a id="DOMinsertBefore"></a>

**语法格式**

```
insertBefore(
    n dbms_xmldom.DOMNode, 
    newchild IN dbms_xmldom.DOMNode, 
    refchild IN dbms_xmldom.DOMNode)
```

**参数说明**

n：DOMNode节点

newchild ：待插入的新节点

refchild ：现有的子节点

#### dbms_xmldom.setVersion<a id="DOMsetVersion"></a>

**语法格式**

```
setVersion(doc dbms_xmldom.DOMDocument, version VARCHAR2)
```

**参数说明**

- doc：xml文档
- version：待设置的版本号