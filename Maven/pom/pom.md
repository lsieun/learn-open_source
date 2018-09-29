# POM

**Project Object Model** (POM) is at the heart of any Maven project.

[TOC]

## 1. Project Object Model

Any Maven project must have a `pom.xml` file. `POM` is the Maven project descriptor, just like the `web.xml` file in your Java EE web application or the `build.xml` file in your `Ant` project. 


The following code lists out all the key elements in a Maven pom.xml file.

```xml
<project>
    <parent>...</parent>
    <modelVersion>4.0.0</modelVersion>
    <groupId>...</groupId>
    <artifactId>...</artifactId>
    <version>...</version>
    <packaging>...</packaging>

    <name>...</name>
    <description>...</description>
    <url>...</url>
    <inceptionYear>...</inceptionYear>
    <licenses>...</licenses>
    <organization>...</organization>
    <developers>...</developers>
    <contributors>...</contributors>

    <dependencies>...</dependencies>
    <dependencyManagement>...</dependencyManagement>
    <modules>...</modules>
    <properties>...</properties>
    <build>...</build>

    <reporting>...</reporting>
    <issueManagement>...</issueManagement>
    <ciManagement>...</ciManagement>
    <mailingLists>...</mailingLists>
    <scm>...</scm>
    <prerequisites>...</prerequisites>

    <repositories>...</repositories>
    <pluginRepositories>...</pluginRepositories>
    <distributionManagement>...</distributionManagement>
    <profiles>...</profiles>
</project>
```

## 2. The POM hierarchy, super POM, and parent POM

### 2.1 The POM hierarchy

POM files maintain **a parent-child relationship** between them. A child POM file inherits all the configuration elements from its parent POM file. This is how Maven sticks to its design philosophy, which is **convention over configuration**. The minimal POM configuration for any Maven project is extremely simple, which is as follows:

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>lsieun</groupId>
    <artifactId>myapp</artifactId>
    <version>1.0.0</version>
</project>
```

### 2.2 Super POM

Any POM file can point to its parent POM. If the parent POM element is missing, then there is a system-wide POM file that is automatically treated as the parent POM file. This POM file is well known as **the super POM**. Ultimately, all the application POM files get extended from the super POM. The super POM file is at the top of the POM hierarchy, and it is bundled inside `MAVEN_HOME/lib/maven-model-builder-3.x.x.jar` - `org/apache/maven/model/pom-4.0.0.xml`.

[Link](super-pom.xml)

```xml
<project>
    <modelVersion>4.0.0</modelVersion>

    <!-- Jar仓库：实现项目的功能依赖 -->
    <repositories>
        <repository>
            <id>central</id>
            <name>Central Repository</name>
            <url>https://repo.maven.apache.org/maven2</url>
            <layout>default</layout>
        </repository>
    </repositories>

    <!-- Plugin仓库：代码完成后，项目的构建 -->
    <pluginRepositories>
        <pluginRepository>
            <id>central</id>
            <name>Central Repository</name>
            <url>https://repo.maven.apache.org/maven2</url>
            <layout>default</layout>
        </pluginRepository>
    </pluginRepositories>

    <build>

        <!-- 输出目录：编译、测试、Artifact -->
        <directory>${project.basedir}/target</directory><!-- 有一天，我要弄清楚basedir到底是怎么得到Value的。 -->
        <outputDirectory>${project.build.directory}/classes</outputDirectory>
        <testOutputDirectory>${project.build.directory}/test-classes</testOutputDirectory>
        <finalName>${project.artifactId}-${project.version}</finalName>
        
        <!-- 输入目录：源代码、测试代码、源代码资源目录、测试代码资源目录 -->
        <sourceDirectory>${project.basedir}/src/main/java</sourceDirectory>
        <scriptSourceDirectory>${project.basedir}/src/main/scripts</scriptSourceDirectory>
        <testSourceDirectory>${project.basedir}/src/test/java</testSourceDirectory>
        <resources>
            <resource>
                <directory>${project.basedir}/src/main/resources</directory>
            </resource>
        </resources>
        <testResources>
            <testResource>
                <directory>${project.basedir}/src/test/resources</directory>
            </testResource>
        </testResources>

        <!-- 插件管理： -->
        <pluginManagement>
            <plugins>
                <plugin>
                    <artifactId>maven-antrun-plugin</artifactId>
                    <version>1.3</version>
                </plugin>
                <plugin>
                    <artifactId>maven-assembly-plugin</artifactId>
                    <version>2.2-beta-5</version>
                </plugin>
                <plugin>
                    <artifactId>maven-dependency-plugin</artifactId>
                    <version>2.8</version>
                </plugin>
                <plugin>
                    <artifactId>maven-release-plugin</artifactId>
                    <version>2.3.2</version>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>

    <reporting>
        <outputDirectory>${project.build.directory}/site</outputDirectory>
    </reporting>

    <profiles>
    </profiles>

</project>
```


The Maven `central` is the only repository defined under the `<repositories>` section. It will be inherited by all the Maven application modules. Maven uses the repositories defined under the repositories section to download all the dependent artifacts during a Maven build.

```xml
<repositories>
    <repository>
        <id>central</id>
        <name>Central Repository</name>
        <url>https://repo.maven.apache.org/maven2</url>
        <layout>default</layout>
    </repository>
</repositories>
```

**Plugin repositories** define where to find Maven plugins.

```xml
<pluginRepositories>
    <pluginRepository>
        <id>central</id>
        <name>Central Repository</name>
        <url>https://repo.maven.apache.org/maven2</url>
        <layout>default</layout>
    </pluginRepository>
</pluginRepositories>
```

Once again, if you look at the `<pluginManagement>` section of the super POM file, you will notice that a given plugin artifact is identified only by its `artifactId` and `version` elements. This contradicts: **a given artifact** is uniquely identified by the combination of `groupId`, `artifactId`, and `version`. How is this possible?

```xml
<pluginManagement>
    <plugins>
        <plugin>
            <artifactId>maven-antrun-plugin</artifactId>
            <version>1.3</version>
        </plugin>
        <!-- ... -->
    </plugins>
</pluginManagement>
```

There is an exception for `plugins`. You need not specify `groupId` for a plugin in the POM file; it's optional. By default, Maven uses `org.apache.maven.plugins` or `org.codehaus.mojo as groupId`. Have a look at the following section in `MAVEN_HOME/conf/settings.xml`. If you want to add **additional group IDs** for plugin lookup, you have to uncomment the section below and add them there:

```xml
<!-- pluginGroups
| This is a list of additional group identifiers that will be searched when resolving plugins by their prefix, i.e.
| when invoking a command line like "mvn prefix:goal". Maven will automatically add the group identifiers
| "org.apache.maven.plugins" and "org.codehaus.mojo" if these are not already contained in the list.
|-->
<pluginGroups>
    <!-- pluginGroup
        | Specifies a further group identifier to use for plugin lookup.
    -->
    <pluginGroup>com.your.plugins</pluginGroup>
</pluginGroups>

```

### 2.3 Parent POM file

The parent POM file has the following coordinates:

```xml
<groupId>org.wso2.carbon</groupId>
<artifactId>platform-parent</artifactId>
<version>4.2.0</version>
<packaging>pom</packaging>
```

The value of the `<relativePath>` element, by default, refers to the `pom.xml` file one level above, that is, `../pom.xml`. However, in this case,
it is not the parent POM file; hence, the value of the element must be overridden and set to `../parent/pom.xml`, as shown here:

```xml
<groupId>org.wso2.carbon</groupId>
<artifactId>carbon-components</artifactId>
<version>4.2.0</version>
<parent>
    <groupId>org.wso2.carbon</groupId>
    <artifactId>platform-parent</artifactId>
    <version>4.2.0</version>
    <relativePath>../parent/pom.xml</relativePath>
</parent>
```

Extending and overriding POM files
• Maven coordinates
• Managing dependencies
• Transitive dependencies
• Dependency scopes and optional dependencies















