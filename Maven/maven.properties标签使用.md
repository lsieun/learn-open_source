# properties标签的使用 #

```xml
<properties>  
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>  
  
        <edu-demo.version>1.0-SNAPSHOT</edu-demo.version>  
        <edu-facade-user.version>1.0-SNAPSHOT</edu-facade-user.version>  
        <edu-service-user.version>1.0-SNAPSHOT</edu-service-user.version>  
        <edu-web-boss.version>1.0-SNAPSHOT</edu-web-boss.version>  
  
        <!-- frameworks -->  
        <org.springframework.version>3.2.4.RELEASE</org.springframework.version>  
        <org.apache.struts.version>2.3.15.1</org.apache.struts.version>  
  
</properties>  
```

properties顾名思义就是配置文件,这里的peoperties是管理jar包的版本号的

比如说：

```xml
<dependency>  
    <groupId>org.springframework</groupId>  
    <artifactId>spring-aop</artifactId>  
    <version>${org.springframework.version}</version>  
</dependency>  
<dependency>  
    <groupId>org.springframework</groupId>  
    <artifactId>spring-aspects</artifactId>  
    <version>${org.springframework.version}</version>  
</dependency>  
<dependency>  
    <groupId>org.springframework</groupId>  
    <artifactId>spring-beans</artifactId>  
    <version>${org.springframework.version}</version>  
</dependency> 
```

当我们的某一个文件出现了像如上所写的，版本号被重复使用的话，我们可以规范起来，把版本号写进peoperties文件，然后后面直接引用，这样对于查看和维护来说是非常方便的


