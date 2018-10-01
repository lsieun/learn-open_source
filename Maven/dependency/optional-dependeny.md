# Optional dependencies

Let's say that we have a Java project that has to work with two different OSGi runtimes. 

> OSGi(Open Service Gateway Initiative)技术是Java动态化模块化系统的一系列规范。

We have written almost all the code to **the OSGi API**, but there are certain parts in the code that consume **OSGi runtime-specific APIs**. 

> OSGi API 应该是“规范”，而OSGi runtime-specific APIs应该是“实现”。这里提供了两种实现：osgi和phoenix-core。

In runtime, only the code path related to **the underneath OSGi runtime** will get executed, not both. This raises the need to have both OSGI runtime JAR files at the build time. However, at runtime, we do not need both the code execution paths, only the one related to the OSGi runtime is needed. We can meet these requirements by **optional dependencies**, which is as follows:

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.packt</groupId>
    <artifactId>osgi.client</artifactId>
    <version>1.0.0</version>

    <dependencies>
        <dependency>
            <groupId>org.eclipse.equinox</groupId>
            <artifactId>osgi</artifactId>
            <version>3.1.1</version>
            <scope>compile</scope>
            <!-- 注意：这里optional为true -->
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.apache.phoenix</groupId>
            <artifactId>phoenix-core</artifactId>
            <version>3.0.0-incubating</version>
            <scope>compile</scope>
            <!-- 注意：这里optional为true -->
            <optional>true</optional>
        </dependency>
    </dependencies>
</project>
```

For any client project that needs `com.packt.osgi.client` to work in **an Equinox OSGi runtime**, it must explicitly add a dependency to **the Equinox JAR file**.

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.packt</groupId>
    <artifactId>my.osgi.client</artifactId>
    <version>1.0.0</version>

    <dependencies>
        <!-- 注意：这里明确引用需要JAR包 -->
        <dependency>
            <groupId>org.eclipse.equinox</groupId>
            <artifactId>osgi</artifactId>
            <version>3.1.1</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>com.packt</groupId>
            <artifactId>osgi.client</artifactId>
            <version>1.0.0</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>
</project>
```

