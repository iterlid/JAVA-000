filter 的功能已经添加q41的nio中了.请查看q41环节的代码.



# 
增加了一个基本的filter

在inbound中增加了对应的filter.


# 验证

启用 nio01中的 HttpServer01.java 跑起程序.
开启 nio02中的网关.
访问 localhost:8888/ 返回了 hello nio 文本.

