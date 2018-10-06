# The assembly descriptor

**The assembly descriptor** is an XML-based configuration, which defines how to build an assembly and how its content should be structured.

**The assembly descriptors** for each phase can be specified under the `<descriptors>` element. As in this particular example, there can be multiple `<descriptor>` elements defined under the `<descriptors>` parent element. For the `package` phase, it has the following **three** assembly descriptors:

pom.xml [Link](wso2-pom-simple.xml)

```xml
<execution>
    <id>dist</id>
    <phase>package</phase>
    <goals>
        <goal>attached</goal>
    </goals>
    <configuration>
        <filters>
            <filter>${basedir}/src/assembly/filter.properties</filter>
        </filters>
        <!-- 关注点 -->
        <descriptors>
            <descriptor>src/assembly/bin.xml</descriptor>
            <descriptor>src/assembly/src.xml</descriptor>
            <descriptor>src/assembly/docs.xml</descriptor>
        </descriptors>
    </configuration>
</execution>
```

Each `<descriptor>` element instructs the `assembly` plugin where to load the descriptor, and each descriptor file will be executed sequentially in the defined order.

## descriptor file

Let's have a look at the `src/assembly/bin.xml` file. [Link](wso2-bin-simple.xml)

### formats

```xml
<formats>
    <format>zip</format>
</formats>
```

The value of the `format` element specifies the ultimate type of the artifact to be produced. It can be `zip` , `tar` , `tar.gz` , `tar.bz2` , `jar` , `dir` , or `war` . You can use **the same assembly descriptor** to create **multiple formats**. In this case, you can include multiple `format` elements under the `formats` parent element.

Even though you can specify the `format` of the assembly in **the assembly descriptor**, it is **recommended** that you do this via **the plugin configuration** itself. In the plugin configuration, you can define different formats for your assembly shown as follows. The benefit here is that you can have multiple Maven profiles to build different archive types. 

```xml
<configuration>
    <formats>
        <format>zip</format>
    </formats>
</configuration>
```

### includeBaseDirectory

```xml
<includeBaseDirectory>false</includeBaseDirectory>
```

When the value of the `includeBaseDirectory` element is set to `false` , the artifact will be created with no base directory. If this is set to `true` , which is the default value, the artifact will be created under a base directory. You can specify a value for the base directory under the `baseDirectory` element. **In most of the cases**, the value of `includeBaseDirectory` is set to `false` so that the final distribution unit directly packs all the artifacts right under it, without having another root directory.

### fileSets and fileSet

```xml
<fileSets>
    <fileSet>
        <directory>target/wso2carbon-core-4.2.0</directory>
        <outputDirectory>wso2is-${pom.version}</outputDirectory>
        <excludes>
            <exclude>**/*.sh</exclude>
```

Each `fileSet` element under the `fileSets` parent element specifies the set of files to be assembled to build the final archive. The first `fileSet` element instructs to copy all the content from the `directory` (which is `target/wso2carbon-core-4.2.0`) to the output directory specified under the `outputDirectory` configuration element, excluding all the files defined under each `exclude` element. If no exclusions are defined, then all the content inside directory will be copied to the `outputDirectory`.

In this particular case, the value of `${pom.version}` will be replaced the by version of the artifact, which is defined in the `pom.xml` file under the distribution module.

### exclude

The `exclude` element instructs not to copy any file that has the `.sh` extension from anywhere inside `target/wso2carbon-core-4.2.0` to the `outputDirectory`.

```xml
<exclude>**/*.sh</exclude>
<exclude>**/wso2server.bat</exclude>
<exclude>**/axis2services/sample01.aar</exclude>
```

The `exclude` element instructs not to copy the `sample01.aar` file inside a directory called `axis2services` from anywhere inside `target/wso2carbon-core-4.2.0` to `outputDirectory`.


### include

```xml
<fileSet>
    <directory>
        ../p2-profile-gen/target/wso2carbon-core-4.2.0/repository/conf/identity
    </directory>
    <outputDirectory>wso2is-${pom.version}/repository/conf/identity</outputDirectory>
    <includes>
        <include>**/*.xml</include>
    </includes>
</fileSet>
```

The `include` element instructs to copy only the files that have the `.xml` extension from anywhere inside the `../p2-profile-gen/target/wso2carbon-core-4.2.0/repository/conf/identity` directory to the `outputDirectory`. If no `include` element is defined, everything will be included.

```xml
<fileSet>
    <directory>
        ../p2-profile-gen/target/wso2carbon-core-4.2.0/repository/resources/security/ldif
    </directory>
    <outputDirectory>wso2is-${pom.version}/repository/resources/security/ldif</outputDirectory>
    <includes>
        <include>identityPerson.ldif</include>
        <include>scimPerson.ldif</include>
        <include>wso2Person.ldif</include>
    </includes>
</fileSet>
```


The `include` element instructs to copy only the files having **specific names** from anywhere inside the `../p2-profile-gen/target/wso2carbon-core/4.2.0/repository/resources/security/ldif` directory to the `outputDirectory`.

```xml
<fileSet>
    <directory>
        ../p2-profile-gen/target/wso2carbon-core-4.2.0/repository/deployment/server/webapps
    </directory>
    <outputDirectory>${pom.artifactId}-${pom.version}/repository/deployment/server/webapps</outputDirectory>
    <includes>
        <include>oauth2.war</include>
    </includes>
</fileSet>
```

The `include` element instructs to copy only the WAR file with the name `oauth2.war` from anywhere inside the `../p2-profile-gen/target/wso2carbon-core/4.2.0/repository/resources/deployment/server/webappas` directory to the `outputDirectory` .

```xml
<fileSet>
    <directory>../styles/service/src/main/resources/web/styles/css</directory>
    <outputDirectory>${pom.artifactId}-${pom.version}/resources/allthemes/Default/admin</outputDirectory>
    <includes>
        <include>**/**.css</include>
    </includes>
</fileSet>
```

The `include` element instructs to copy any file with the `.css` extension from anywhere inside the `../styles/service/src/main/resources/web/styles/css` directory to the `outputDirectory`.

```xml
<fileSet>
    <directory>../p2-profile-gen/target/WSO2-CARBON-PATCH-4.2.0-0006</directory>
    <outputDirectory>wso2is-${pom.version}/repository/components/patches/</outputDirectory>
    <includes>
        <include>**/patch0006/*.*</include>
    </includes>
</fileSet>
```

The `include` element instructs to copy all the files inside the `patch006` directory from anywhere inside the `../p2-profile-gen/target/WSO2-CARBON-PATCH-4.2.0-0006` directory to the `outputDirectory`.

### file


The `file` element is very similar to the `fileSet` element in terms of the key functionality. Both can be used to control the content of the assembly.

The `file` element should be used when you are fully aware of the exact source file location, while the `fileSet` element is much more flexible to pick files from a source based on a defined pattern.

The `fileMode` element in the following snippet defines a set of permissions to be attached to the copied file. The permissions are defined as per the four-digit octal notation. You can read more about the four-digit octal notation from http://en.wikipedia.org/wiki/File_system_permissions#Octal_notation_and_additional_permissions :


```xml
<files>
    <file>
        <source>../p2-profile-gen/target/WSO2-CARBON-PATCH-${carbon.kernel.version}-0006/lib/org.wso2.ciphertool-1.0.0-wso2v2.jar</source>
        <outputDirectory>${pom.artifactId}-${pom.version}/lib/</outputDirectory>
        <filtered>true</filtered>
        <fileMode>644</fileMode>
    </file>
</files>
```

### dist.xml

There are three descriptor elements defined under the assembly plugin for the package phase. The one we just discussed earlier will create the binary distribution, while the `src/assembly/src.xml` and `src/assembly/docs.xml` files will create the source distribution and the documentation distribution, respectively.

Let's also look at the assembly descriptor defined for the `test` phase:

```
<descriptors>
    <descriptor>src/assembly/dist.xml</descriptor>
</descriptors>
```

This is quite short and only includes the configuration required to build the initial distribution of WSO2 Identity Server. Even though this project does this at the `test` phase, it seems like it has no value in doing this. In this case, it seems like `maven-antrun-plugin` , which is also associated with the `package` phase but prior to the definition of the `assembly` plugin, needs the ZIP file distribution. Ideally, you should not have the `assembly` plugin run at the `test` phase unless there is a very strong reason. You might need the distribution ready to run the integration tests; however, the integration tests should be executed in the `integration-test` phase, which comes after the `package` phase. **In most of the cases**, the `assembly` plugin is associated with the `package` phase of the Maven `default` lifecycle.

### dependencySets

```xml
<assembly>
	<formats>
		<format>zip</format>
	</formats>
	<includeBaseDirectory>false</includeBaseDirectory>
	<fileSets>
		<!-- Copying p2 profile and osgi bundles-->
		<fileSet>
			<directory>
				../p2-profile-gen/target/wso2carbon-core-4.2.0/repository/components
			</directory>
			<outputDirectory>wso2is-${pom.version}/repository/components</outputDirectory>
			<excludes>
				<exclude>**/eclipse.ini</exclude>
				<exclude>**/*.lock</exclude>
				<exclude>**/.data</exclude>
				<exclude>**/.settings</exclude>
			</excludes>
		</fileSet>
	</fileSets>
    <!-- 关注点 -->
	<dependencySets>
		<dependencySet>
			<outputDirectory>
				wso2is-${pom.version}/repository/deployment/client/modules
			</outputDirectory>
			<includes>
				<include>org.apache.rampart:rampart:mar</include>
			</includes>
		</dependencySet>
	</dependencySets>
</assembly>
```

This configuration introduces a new element that we have not seen before: `dependencySet`. The `dependencySet` element lets you `include/exclude` project dependencies `to/from` the final assembly that we are building. In the previous example, it adds the `rampart` module into the `outputDirectory` . The value of the `include` element should be in the `groupdId:artifactId:type[:classifier][:version]` format. Maven will look for this artifact with the defined coordinates in its local Maven repository first, and if found, it will copy the artifact to the `outputDirectory` element.

Unlike the `fileSet` or `file` configuration, `dependencySet` does not define a concrete path to pick and copy the dependency from. Maven finds artifacts via **the defined coordinates**. If you want to include a dependency just by its `groupId` and the `artifactId` , then you can follow the `groupdId:artifactId` pattern. **The particular artifact** should be defined in **the POM file**, which has the `assembly` plugin defined under the `dependencies` section. You can find the following dependency definition for the `rampart` module in the POM file under the distribution module. If two versions of the same dependency are being defined in the same POM file (rather unlikely), then the last in the order will be copied.

```xml
<dependency>
    <groupId>org.apache.rampart</groupId>
    <artifactId>rampart</artifactId>
    <type>mar</type>
    <version>1.6.1-wso2v12</version>
</dependency>
```

You can also include a dependency by its `groupId` , `artifactId` , and `type` elements, as shown in the following configuration. Then, you can follow the `groupdId:artifactId:type[:classifier]` pattern. This is the exact pattern followed in the previous example:

```xml
<includes>
<include>org.apache.rampart:rampart:mar</include>
</includes>
```

If you want to be more precise, you can also include the `version` in the pattern. Then, it will look like this:

```xml
<includes>
    <include>org.apache.rampart:rampart:mar:1.6.1-wso2v12</include>
</includes>
```

The previous example only covered a very little subset of the assembly descriptor. You can find all available configuration options at http://maven.apache.org/plugins/maven-assembly-plugin/assembly.html , which is a quite exhausting list.

It is **a best practice** or a convention to include **all the assembly descriptor files** inside a directory called `assembly`, though it is not mandatory.
