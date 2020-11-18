# xml 方式装配
Student 采用了2种方式.
getter setter 方法注入
构造器注入.

# annotation的方式装配
先在resources添加 applicationContext.xml，然后添加bean扫描
<context:component-scan base-package="com.example.demo" />


# 自动装配
使用School中使用了Autowired来自动转配teacher.