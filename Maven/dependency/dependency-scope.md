# Dependency scopes

Maven defines the following **six scope types**; if there is no `<scope>` element defined for a given dependency, **the default scope**, `compile` , will get applied.

> 这段的两个意思：  
> （1）共有6种类型的dependency scope。  
> （2）默认值是compile

- dependency scope
    - compile
    - provided
    - runtime
    - test
    - system
    - import

1\.  [compile](#compile)  
2\.  [provided](#provided)  
3\.  [runtime](#runtime)  
4\.  [test](#test)  
5\.  [system](#system)  
6\.  [import](#import)  
7\.  [Summary](#summary)  

<a name="compile"></a>

## 1\. compile

This is **the default scope**. 

Any dependency defined under the `compile` scope will be available in all the class paths and also packaged into the final artifact produced by the Maven project. 

> 对于compile scope的dependency：  
> （1）build class path中会出现；  
> （2）artifact中会出现。

If you are building a `WAR` type artifact, then the referred `JAR` file with the `compile` scope will be embedded into the `WAR` file itself.


<a name="provided"></a>

## 2\. provided

This scope expects that the corresponding dependency will be provided either by `JDK` or **a container**, which runs the application. 

Any dependency with the `provided` scope will be available in **the build time class path**, but it won't be packaged into the final artifact. 

> 对于provided scope的dependency：  
> （1）build class path中会出现；  
> （2）artifact中不会出现。

The best example is **the servlet API**. If it's a `WAR` file, **the servlet API** will be available in the class path during the build time, but won't get packaged into the `WAR` file.

```xml
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>3.1.0</version>
    <scope>provided</scope>
</dependency>
```

<a name="runtime"></a>

## 3\. runtime 

Dependencies defined under the `runtime` scope will be available only during the runtime, not in the build time class path. These dependencies will be packaged into the final artifact. 

> 对于runtime scope的dependency：  
> （1）build class path中不会出现；  
> （2）artifact中会出现。

You can have a web app that in runtime talks to a `MySQL` database. Your code does not have any hard
dependency to the `MySQL` database driver. Code is written against the `Java JDBC API`, and it does not need the `MySQL` database driver at the build time. However, during the runtime, it needs the driver to talk to the `MySQL` database. For this, the driver should be packaged into the final artifact.


<a name="test"></a>

## 4\. test

Dependencies are only needed for `test` compilation (for example, `JUnit` and `TestNG`), and execution must be defined under the `test` scope. These dependencies won't get packaged into the final artifact.

> 对于runtime scope的dependency：  
> （1）build class path中不会出现；  
> （2）artifact中不会出现。


<a name="system"></a>

## 5\. system

This is very similar to the scope `provided`. The only difference is with the `system` scope, you need to tell Maven how to find it. 

> system和provied两者相似，但不同。  

> 对于system scope的dependency：  
> （1）build class path中会出现；  
> （2）artifact中不会出现。  
> （3）需要指明jar包的位置

**System dependencies** are useful when you do not have the referred dependency in a Maven repository. With this, you need to make sure that all system dependencies are available to download with the source code itself. 

It is always recommended to avoid using system dependencies. The following code snippets shows how to define a `system` dependency:

```xml
<dependency>
    <groupId>com.packt</groupId>
    <artifactId>jose</artifactId>
    <version>1.0.0</version>
    <scope>system</scope>
    <!-- 指明jar包的位置 -->
    <systemPath>${basedir}/lib/jose.jar</systemPath>
</dependency>
```

`$basedir` is a built-in property defined in Maven to represent **the directory**, which has **the corresponding POM file**.

> 所谓`$basedir`就是存放`pom.xml`文件的位置。

<a name="import"></a>

## 6\. import

This is only applicable for dependencies defined under the `<dependencyManagement>` section with the `pom` packaging type. Let's take the following POM file; it has the `<packaging>` type defined as `pom` :

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.packt</groupId>
    <artifactId>axis2-client</artifactId>
    <version>1.0.0</version>
    <!-- 注意：这里是pom类型 -->
    <packaging>pom</packaging>

    <!-- 注意：这里是dependencyManagement -->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.apache.axis2</groupId>
                <artifactId>axis2-kernel</artifactId>
                <version>1.6.2</version>
            </dependency>
            <dependency>
                <groupId>org.apache.axis2</groupId>
                <artifactId>axis2-adb</artifactId>
                <version>1.6.2</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
</project>
```

Now, from a different Maven module, we add a dependency under the `<dependencyManagement>` section to the previous module, with the `<scope>` value set to `import` and the value of `<type>` set to `pom`:

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.packt</groupId>
    <artifactId>my-axis2-client</artifactId>
    <version>1.0.0</version>

    <!-- 注意：这里是dependencyManagement -->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.packt</groupId>
                <artifactId>axis2-client</artifactId>
                <version>1.0.0</version>
                <!-- 注意：这里type是pom -->
                <type>pom</type>
                <!-- 注意：这里scope是import -->
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
<project>
```

Now, if we run `mvn help:effective-pom` against the last POM file, we will see the dependencies from before are being imported, as shown here:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.apache.axis2</groupId>
            <artifactId>axis2-kernel</artifactId>
            <version>1.6.2</version>
        </dependency>
        <dependency>
            <groupId>org.apache.axis2</groupId>
            <artifactId>axis2-adb</artifactId>
            <version>1.6.2</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

<a name="summary"></a>

## 7\. Summary


|  Scope   | Build Class Path | Artifact |
| :------: | :--------------: | :------: |
| compile  |     :smile:      | :smile:  |
| provided |     :smile:      |  :cry:   |
| runtime  |     :smile:      |  :cry:   |
|   test   |      :cry:       |  :cry:   |
|  system  |     :smile:      |  :cry:   |
|  import  |                  |          |






