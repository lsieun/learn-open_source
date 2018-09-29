# Convention over configuration

**Convention over configuration** is one of the main design philosophies behind Apache Maven.



## Directory Convention(本地，写代码)

- The **Java source code** is available at `{base-dir}/src/main/java`
- **Test cases** are available at `{base-dir}/src/test/java`
- **Compiled class files** are copied into `{base-dir}/target/classes`
- **The final artifact** is copied into `{base-dir}/target`

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.packt</groupId>
    <artifactId>sample-one</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <build>
        <sourceDirectory>${basedir}/src/main/java</sourceDirectory>
        <testSourceDirectory>${basedir}/src/test/java</testSourceDirectory>
        <outputDirectory>${basedir}/target/classes</outputDirectory>
    </build>
</project>
```

## Artifact Convention(本地，代码完成后的阶段)

- A `JAR` file type of artifact is produced

## Repository Convention(远程，)

- The link `http://repo.maven.apache.org/maven2` is used as the repository
