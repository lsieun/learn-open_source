# Maven in 5 Minutes

URL: https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html

## Installation

Maven is a Java tool, so you must have Java installed in order to proceed.

```bash
mvn --version
```

## Creating a Project

```bash
mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.mycompany.app</groupId>
  <artifactId>my-app</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>jar</packaging>

  <name>my-app</name>
  <url>http://maven.apache.org</url>

  <properties>
      <maven.compiler.source>1.8</maven.compiler.source>
      <maven.compiler.target>1.8</maven.compiler.target>
  </properties>

  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
</project>

```

### What did I just do?

You executed the Maven goal `archetype:generate`, and passed in various parameters to that `goal`. The prefix `archetype` is the `plugin` that contains the `goal`. If you are familiar with `Ant`, you may conceive of this as similar to a task. This goal created a simple project based upon an `archetype`. Suffice it to say for now that **a plugin** is **a collection of goals** with **a general common purpose**. For example the `jboss-maven-plugin`, whose purpose is "deal with various jboss items".

> 这一段理解出3个意思：  
> （1）maven命令是基于plugin的  
> （2）plugin是goal的集合 a plugin is a collection of goals with a general common purpose.   
> （3）每一个goal都完成特定的目标

