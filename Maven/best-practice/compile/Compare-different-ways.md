# How to specify the JDK version?

URL: https://stackoverflow.com/questions/38882080/specifying-java-version-in-maven-differences-between-properties-and-compiler-p

## 第一种方式

1) `<java.version>` is not referenced in the Maven documentation.
It is a Spring Boot specificity.
It allows to set the `source` and the `target` java version with the same version such as this one to specify java 1.8 for both :

```xml
<properties>
     <java.version>1.8</java.version>
</properties>
```
Feel free to use it if you use Spring Boot.

## 第二种方式

2) Using `maven-compiler-plugin` or `maven.compiler.source`/`maven.compiler.target` properties to specify the `source` and the `target` are equivalent.

```xml
<plugins>
    <plugin>    
        <artifactId>maven-compiler-plugin</artifactId>
        <configuration>
            <source>1.8</source>
            <target>1.8</target>
        </configuration>
    </plugin>
</plugins>
```

and

```xml
<properties>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
</properties>
```

are equivalent according to the [Maven documentation of the compiler plugin](https://maven.apache.org/plugins/maven-compiler-plugin/compile-mojo.html) since the `<source>` and the `<target>` elements in the compiler configuration use the properties `maven.compiler.source` and `maven.compiler.target` if they are defined.

## 第三种方式

3) The maven-compiler-plugin 3.6 and later versions provide a new way :

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.8.0</version>
    <configuration>
        <release>9</release>
    </configuration>
</plugin>
```

You could also declare just :

```xml
<properties>
    <maven.compiler.release>9</maven.compiler.release>
</properties>
```

But at this time it will not work as the maven-compiler-plugin default version you use doesn't rely on a recent enough version.

The Maven release argument conveys `release` : a [new JVM standard option](https://docs.oracle.com/javase/9/tools/javac.htm#JSWOR627) that we could pass from Java 9 : Compiles against the public, supported and documented API for a specific VM version.

This way provides a standard way to specify the same version for the **source**, the **target** and the **bootstrap** JVM options.

> Note that specifying the **bootstrap** is a good practice for **cross compilations** and it will not hurt if you don't make cross compilations either.


## Which is the best way to specify the JDK version?

The first way (`<java.version>`) is allowed only if you use **Spring Boot**.

**For Java 8 and below**:

About the two other ways : valuing the `maven.compiler.source`/`maven.compiler.target` properties or using the `maven-compiler-plugin`, you can use one or the other. It changes nothing in the facts since finally the two solutions rely on the same properties and the same mechanism : the maven core compiler plugin.

Well, if you don't need to specify other properties or behavior than Java versions in the compiler plugin, using this way makes more sense as this is more concise:

```xml
<properties>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
</properties>
```

**From Java 9** :

The `release` argument (third point) is a way to strongly consider if you want to use the same version for the **source** and the **target**.

## What happens if the version differs between the JDK in JAVA_HOME and which one specified in the pom.xml?

It is not a problem if the JDK referenced by the `JAVA_HOME` is compatible with the version specified in the pom but to ensure a better cross-compilation compatibility think about adding the `bootstrap` JVM option with as value the path of the `rt.jar` of the `target` version.

An important thing to consider is that the `source` and the `target` version in the Maven configuration should not be superior to the JDK version referenced by the `JAVA_HOME`.

A older version of the JDK cannot compile with a more recent version since it doesn't know its specification.

## How handle the case of JDK referenced by the JAVA_HOME is not compatible with the java target and/or source versions specified in the pom?

For example, if your `JAVA_HOME` refers to a JDK 1.7 and you specify a JDK 1.8 as `source` and `target` in the compiler configuration of your `pom.xml`, it will be a problem because as explained, the JDK 1.7 doesn't know how to compile with.

From its point of view, it is an unknown JDK version since it was released after it.

In this case, you should configure the Maven compiler plugin to specify the JDK in this way :

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <source>1.8</source>
        <target>1.8</target>
        <compilerVersion>1.8</compilerVersion>      
        <fork>true</fork>
        <executable>D:\jdk1.8\bin\javac</executable>                
    </configuration>
</plugin>
```

You could have more details in [examples with maven compiler plugin](https://maven.apache.org/plugins/maven-compiler-plugin/examples/compile-using-different-jdk.html).
