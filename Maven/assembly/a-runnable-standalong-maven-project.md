# A runnable, standalone Maven project

First, create a directory structure in the following manner:

```txt
|-pom.xml
|-modules
    |- json-parser
        |- src/main/java/com/packt/json/JSONParser.java
        |- pom.xml
    |- distribution
        |- src/main/assembly/dist.xml
        |- pom.xml
```

`JSONParser.java` is a simple Java class, which reads a JSON file and prints to the console, shown as follows:

```java
package com.lsieun.json;

import java.io.File;
import java.io.FileReader;
import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;

public class JSONParser {
    public static void main(String[] args) {
        FileReader fileReader;
        JSONObject json;
//        JSONParser parser = new JSONParser();
        org.json.simple.parser.JSONParser parser;
        parser = new org.json.simple.parser.JSONParser();

        try {
            if (args == null || args.length == 0 || args[0] == null || !new File(args[0]).exists()) {
                System.out.println("No valid JSON file provided");
            }
            else {
                fileReader = new FileReader(new File(args[0]));
                json = (JSONObject) parser.parse(fileReader);
                if (json != null) {
                    System.out.println(json.toString());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
```

Now, we can create a POM file under `modules/json-parser` to build our JAR file, as follows:

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.lsieun</groupId>
    <artifactId>json-parser</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>
    <name>JSON Parser</name>

    <dependencies>
        <dependency>
            <groupId>com.googlecode.json-simple</groupId>
            <artifactId>json-simple</artifactId>
            <version>1.1</version>
        </dependency>
    </dependencies>
</project>
```

Once we are done with the `json-parser` module, the next step is to create the `distribution` module. The `distribution` module will have a POM file and **an assembly descriptor**. Let's first create the POM file under `modules/distribution` , shown as follows. This will associate two plugins with the project: `maven-assembly-plugin` and `maven-jar-plugin`. Both the plugins get executed in the `package` phase of the Maven `default` lifecycle.

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.lsieun</groupId>
    <artifactId>json-parser-dist</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>
    <name>JSON Parser Distribution</name>

    <dependencies>
        <dependency>
            <groupId>com.googlecode.json-simple</groupId>
            <artifactId>json-simple</artifactId>
            <version>1.1</version>
        </dependency>
        <dependency>
            <groupId>com.lsieun</groupId>
            <artifactId>json-parser</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-assembly-plugin</artifactId>
                <executions>
                    <execution>
                        <id>distribution-package</id>
                        <phase>package</phase>
                        <goals>
                            <goal>single</goal>
                        </goals>
                        <configuration>
                            <finalName>json-parser</finalName>
                            <descriptors>
                                <descriptor>src/main/assembly/dist.xml</descriptor>
                            </descriptors>
                        </configuration>
                    </execution>
                </executions>
            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>2.3.1</version>
                <configuration>
                    <archive>
                        <manifest>
                            <addClasspath>true</addClasspath>
                            <classpathPrefix>lib/</classpathPrefix>
                            <mainClass>com.lsieun.json.JSONParser</mainClass>
                        </manifest>
                    </archive>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

Even though the `maven-jar-plugin` is inherited from the super POM, here we have redefined it because we need to add some extra configurations. Since we need to make our final archive executable, we need to define the class to be executable in the jar manifest. Here we have set `com.lsieun.json.JSONParser` as our main class. Also `classpath` is set to the `lib` directory. If you look at the assembly descriptor used in the assembly plugin, you will notice that, the dependent jar files are copied into the `lib` directory. The `manifest` configuration in the `maven-jar-plugin` will result in the following manifest file (`META-INF/MANIFEST.MF`).

The following configuration shows **the assembly descriptor** (`module/distribution/ src/main/assembly/dist.xml`), corresponding to the assembly plugin defined in the previous step:

```xml
<assembly>
    <id>bin</id>
    <formats>
        <format>zip</format>
    </formats>

    <dependencySets>
        <dependencySet>
            <useProjectArtifact>false</useProjectArtifact>
            <outputDirectory>lib</outputDirectory>
            <unpack>false</unpack>
        </dependencySet>
    </dependencySets>

    <fileSets>
        <fileSet>
            <directory>${project.build.directory}</directory>
            <outputDirectory/>
            <includes>
                <include>*.jar</include>
            </includes>
        </fileSet>
    </fileSets>
</assembly>
```

Now, we are done with the `distribution` module too. The next step is to create the root POM file, which aggregates both the `json-parser` and `distribution` modules, as follows:

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.lsieun</groupId>
    <artifactId>json-parser-aggregator</artifactId>
    <version>1.0.0</version>
    <packaging>pom</packaging>
    <name>JSON Parser Aggregator</name>
    <modules>
        <module>modules/json-parser</module>
        <module>modules/distribution</module>
    </modules>
</project>
```

We are all set to build the project. From the root directory, type `mvn clean install` . This will produce the `json-parser-bin.zip` archive inside `modules/distribution/target` . The output is shown as follows:

```txt
[INFO] ----------------------------------------------------
[INFO] Reactor Summary:
[INFO]
[INFO] PACKT JSON Parser............... SUCCESS [ 1.790 s]
[INFO] PACKT JSON Parser Distribution.. SUCCESS [ 0.986 s]
[INFO] PACKT JSON Parser Aggregator.... SUCCESS [ 0.014 s]
[INFO] ----------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ----------------------------------------------------
```

Go to `modules/distribution/target` and unzip `json-parser-bin.zip`.

To run the parser, type the following command, which will produce the No valid JSON file provided output:

```bash
$ java -jar json-parser/json-parser-dist-1.0.0.jar
```

Run the parser again with the following valid JSON file. You need to pass the path to the JSON file as an argument, as shown:

```bash
$ java -jar json-parser/json-parser-dist-1.0.0.jar myjsonfile.json
```

The following is the content of the JSON file:

```txt
{
    "bookName": "Mastering Maven", "publisher" : "PACKT"
}
```




