# maven内置属性（${} properties） #

Maven内置了三大特性：**属性**、**Profile**和**资源过滤**来支持构建的灵活性。

## Maven属性 ##

事实上有六种类型的Maven属性：

- **内置属性**：主要有两个常用内置属性——${basedir}表示项目根目录，即包含pom.xml文件的目录;${version}表示项目版本。
- **POM属性**：pom中对应元素的值。例如`${project.artifactId}`对应了<project><artifactId>元素的值，常用的POM属性包括：
	- `${project.build.sourceDirectory}`:项目的主源码目录，默认为`src/main/java/`.
	- `${project.build.testSourceDirectory}`:项目的测试源码目录，默认为`/src/test/java/`.
	- `${project.build.directory}`:项目构建输出目录，默认为`target/`.
	- `${project.build.outputDirectory}`:项目主代码编译输出目录，默认为`target/classes/`.
	- `${project.build.testOutputDirectory}`:项目测试代码编译输出目录，默认为`target/test-classes/`.
	- `${project.groupId}`:项目的groupId.
	- `${project.artifactId}`:项目的artifactId.
	- `${project.version}`:项目的version,于${version}等价。
	- `${project.build.finalName}`:项目打包输出文件的名称，默认为`${project.artifactId}${project.version}`.


- **自定义属性**：在pom中<properties>元素下自定义的Maven属性。例如

```xml
<project>  
    <properties>  
        <my.prop>hello</my.prop>  
    </properties>  
</project>  
```

- **Settings属性**：与POM属性同理。如`${settings.localRepository}`指向用户本地仓库的地址。
- **Java系统属性**：所有Java系统属性都可以使用Maven属性引用，例如`${user.home}`指向了用户目录。可以通过命令行`mvn help:system`查看所有的Java系统属性
- **环境变量属性**：所有环境变量都可以使用以env.开头的Maven属性引用。例如`${env.JAVA_HOME}`指代了JAVA_HOME环境变量的值。也可以通过命令行`mvn help:system`查看所有环境变量。

## 资源过滤 ##

默认情况下，**Maven属性**只有在POM中才会被解析。**资源过滤**就是指让**Maven属性**在资源文件(`src/main/resources`、`src/test/resources`)中也能被解析。

在POM中添加下面的配置便可以开启资源过滤

```xml
<build>  
    <resources>  
        <resource>  
            <directory>${project.basedir}/src/main/resources</directory>  
            <filtering>true</filtering>  
        </resource>  
    </resources>  
    <testResources>  
        <testResource>  
            <directory>${project.basedir}/src/test/resources</directory>  
            <filtering>true</filtering>  
        </testResource>  
    </testResources>  
</build>  
```

从上面的配置中可以看出，我们其实可以配置多个**主资源目录**和多个**测试资源目录**。

Maven除了可以对主资源目录、测试资源目录过滤外，还能对Web**项目的资源目录**(如css、js目录)进行过滤。这时需要对`maven-war-plugin`插件进行配置。

```xml
<plugin>  
    <groupId>org.apache.maven.plugins</groupId>  
    <artifactId>maven-war-plugin</artifactId>  
    <version>2.1-beta-1</version>  
    <configuration>  
        <webResources>  
            <resource>  
                <filtering>true</filtering>  
                <directory>src/main/webapp</directory>  
                <includes>  
                    <include>**/*.css</include>  
                    <include>**/*.js</include>  
                </includes>  
            </resource>  
        </webResources>  
    </configuration>  
</plugin>  
```

## Maven Profile ##

每个Profile可以看作是POM的一部分配置，我们可以根据不同的环境应用不同的Profile，从而达到不同环境使用不同的POM配置的目的。

profile可以声明在以下这三个文件中：

- pom.xml：很显然，这里声明的profile只对当前项目有效
- 用户settings.xml：.m2/settings.xml中的profile对该用户的Maven项目有效
- 全局settings.xml：conf/settings.xml，对本机上所有Maven项目有效

> 非常值得注意的一点是，profile在pom.xml中可声明的元素在settings.xml中可声明的元素是不一样的：

profile在pom.xml中可声明的元素：

```xml
<project>  
    <repositories></repositories>  
    <pluginRepositories></pluginRepositories>  
    <distributionManagement></distributionManagement>  
    <dependencies></dependencies>  
    <dependencyManagement></dependencyManagement>  
    <modules></modules>  
    <properties></properties>  
    <reporting></reporting>  
    <build>  
        <plugins></plugins>  
        <defaultGoal></defaultGoal>  
        <resources></resources>  
        <testResources></testResources>  
        <finalName></finalName>  
    </build>  
</project>  
```

profile在settings.xml中可声明的元素：

```xml
<project>  
    <repositories></repositories>  
    <pluginRepositories></pluginRepositories>  
    <properties></properties>  
</project>  
```

## 激活Profile ##

有多种激活Profile的方式：

1、命令行方式激活，如有两个profile id为devx和devy的profile：

	mvn clean install  -Pdevx,devy  

2、settings文件显式激活

	<settings>  
	    ...  
	    <activeProfiles>  
	        <activeProfile>devx</activeProfile>  
	        <activeProfile>devy</activeProfile>  
	    </activeProfiles>  
	    ...  
	</settings>  

3、系统属性激活，用户可以配置当某系统属性存在或其值等于期望值时激活profile，如：

	<profiles>  
	    <profile>  
	        <activation>  
	            <property>  
	                <name>actProp</name>  
	                <value>x</value>  
	            </property>  
	        </activation>  
	    </profile>  
	</profiles> 

不要忘了，可以在命令行声明系统属性。如：

	mvn clean install -DactProp=x  

这其实也是一种从命令行激活profile的方法，而且多个profile完全可以使用同一个系统属性来激活。别忘了，系统属性可以通过mvn help:system来查看

4、操作系统环境激活，如

	<profiles>  
	    <profile>  
	        <activation>  
	            <os>  
	                <name>Windows XP</name>  
	                <family>Windows</family>  
	                <arch>x86</arch>  
	                <version>5.1.2600</version>  
	            </os>  
	        </activation>  
	    </profile>  
	</profiles> 

这里的family值包括Window、UNIX和Mac等，而其他几项对应系统属性的os.name、os.arch、os.version

5、文件存在与否激活，Maven能根据项目中某个文件存在与否来决定是否激活profile

	<profiles>  
	    <profile>  
	        <activation>  
	            <file>  
	                <missing>x.properties</missing>  
	                <exists>y.properties</exists>  
	            </file>  
	        </activation>  
	    </profile>  
	</profiles>  

Notice：插件maven-help-plugin提供了一个目标帮助用户了解当前激活的profile：

	mvn help:active-profiles  

另外还有一个目标来列出当前所有的profile：

	mvn help:all-profiles  
