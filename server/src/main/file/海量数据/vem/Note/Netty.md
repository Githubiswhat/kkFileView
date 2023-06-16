## Java NIO

核心组件

- Channel 
- Buffer
- Selector



四种IO操作                  

- 阻塞IO			

  ![image-20220726182718557](C:\Users\windows\AppData\Roaming\Typora\typora-user-images\image-20220726182718557.png)													

- 非阻塞IO

  在等待数据这个地方，如果没有数据，会调用失败，然后不断重试

- IO多路复用

  在等待数据这个地方，由于有selecter、epoll这种，可以一个Selecter管理多个文件描述符

- 异步IO

  在等待数据这个地方，如果没有数据，在内核设置回调接口，可以去做别的事情