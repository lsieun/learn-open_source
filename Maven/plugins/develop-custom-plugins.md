# Developing custom plugins

Under this section, let's see how to build your own Maven custom plugin from scratch. There are so many Maven plugins out there, and most of the time, you can find a plugin to do whatever you want. 

**Maven plain Old Java Object** (**MOJO**) is at the heart of a Maven plugin. A Maven plugin is a collection of goals, and each goal is implemented via a MOJO. In other words, a Maven plugin is a collection of MOJOs. 

Let's start by defining a use case for our custom plugin. Say, you want to write a plugin to send an email to a given recipient once the build is completed.

To create a custom plugin, proceed with the following steps:

```txt
.
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── lsieun
    │   │           └── plugins
    │   │               └── EmailMojo.java
    │   └── resources
    └── test
        ├── java
        └── resources
```

pom.xml

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.lsieun.plugins</groupId>
    <artifactId>mail-maven-plugin</artifactId>
    <version>1.0.0</version>
    <packaging>maven-plugin</packaging>

    <dependencies>
        <dependency>
            <groupId>org.apache.maven</groupId>
            <artifactId>maven-plugin-api</artifactId>
            <version>2.0</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.maven.plugin-tools</groupId>
            <artifactId>maven-plugin-annotations</artifactId>
            <version>3.2</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.1</version>
                <configuration>
                    <source>1.7</source>
                    <target>1.7</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-plugin-plugin</artifactId>
                <version>3.2</version>
                <configuration>
                    <skipErrorNoDescriptorsFound>true</skipErrorNoDescriptorsFound>
                </configuration>
                <executions>
                    <execution>
                        <id>mojo-descriptor</id>
                        <goals>
                            <goal>descriptor</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

The first step in writing a custom plugin is to identify the goals of the plugin, and then represent (and implement) each of them with a **MOJO**. In our case, we have **a single goal**, that is, to **send an email once the build is completed**.

We will write our own `EmailMojo` class that extends the `AbstractMojo` class of the `org.apache.maven.plugin` package. This class must have the `Mojo` annotation, and the value of the `name` attribute represents **the goal name**. In your custom plugin, if you have multiple goals, then for each goal, you need to have a **MOJO** and override the `execute()` method. The code is as follows:

```java
package com.lsieun.plugins;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name="mail")
public class EmailMojo extends AbstractMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Sending Email...");
    }
}
```

For the time being, let's not worry about the email sending logic. Once you have implemented your business logic inside the `execute()` method of your MOJO, next we need to **package this as a plugin** so that **the Maven plugin execution framework can identify and execute it**.

You can use `maven-plugin-plugin` to generate the metadata related to your custom plugin. The following POM file associates `maven-plugin-plugin` with your custom plugin project. 

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-plugin-plugin</artifactId>
    <version>3.2</version>
    <configuration>
        <skipErrorNoDescriptorsFound>true</skipErrorNoDescriptorsFound>
    </configuration>
    <executions>
        <execution>
            <id>mojo-descriptor</id>
            <goals>
                <goal>descriptor</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

Also, we need to have two dependencies: one for `maven-plugin-api` and the other one for `maven-plugin-annotations`.

```xml
<dependency>
    <groupId>org.apache.maven</groupId>
    <artifactId>maven-plugin-api</artifactId>
    <version>2.0</version>
    <scope>compile</scope>
</dependency>
<dependency>
    <groupId>org.apache.maven.plugin-tools</groupId>
    <artifactId>maven-plugin-annotations</artifactId>
    <version>3.2</version>
    <scope>provided</scope>
</dependency>
```

Make sure that your project structure looks similar to the following structure, and then build the project with `mvn clean install`:

```txt
|-src/main/
|   |-java/org/java/com/packt/plugins
|       |-EmailMojo.java
|-pom.xml
```

Now we need to execute our custom plugin. To execute the Maven plugin without a Maven project (to consume it), you need to set the value of the `requiresProject` annotation attribute to `false`.

In our case, we have not set this attribute in our MOJO, so the default value is set, which is `true`. To execute the Maven plugin without a project (you do not need to have a POM file), you need to set the value of `requiresProject` to `false` and rebuild the plugin project, as follows:

```java
@Mojo( name = "mail", requiresProject=false)
public class EmailMojo extends AbstractMojo
{
}
```

Now try to execute the plugin goal in the following manner:

```bash
$ mvn mail:mail
```

This will result in an error. Any guesses why? This is related to how Maven looks up for plugins. When you execute a plugin by its `goalPrefix`, we do not specify its `groupId`, so the Maven engine will look for it in the local Maven repository (and then in the remote repository) assuming its `groupId` to be one of the default `groupId`s. As this is a custom plugin with our own `groupId` , the Maven engine won't find it. The error is as follows:

```txt
[ERROR] No plugin found for prefix 'mail' in the current
project and in the plugin groups [org.apache.maven.plugins,
org.codehaus.mojo] available from the repositories [local
(/Users/prabath/.m2/repository), Central
(http://repo1.maven.org/maven2)] -> [Help 1]

```

To help Maven to locate the groupId plugin, add the following configuration element to `USER_HOME/.m2/settings.xml` under `<pluginGroups>`:

```xml
<pluginGroup>com.lsieun.plugins</pluginGroup>
```

Now try to execute the plugin goal once again:

```bash
$ mvn mail:mail
```

This will now produce the following output:

```txt
[INFO] --- mail-maven-plugin:1.0.0:mail (default-cli) @ standalone-pom ---
[INFO] Sending Email...
```

Or

```bash
$ mvn com.lsieun.plugins:mail-maven-plugin:1.0.0:mail
```

## Associating a plugin with a lifecycle

A plugin can be executed on its own or as a part of a Maven lifecycle. In the previous section, we went through the former, and now let's see how to associate our custom plugin with the Maven `default` lifecycle. The Maven `default` lifecycle has 23 phases, and let's see how to engage our custom plugin to the `post-integration-test` phase.
We only want to send the email if everything up to the `post-integration-test` phase is successful.

Proceed with the following steps:

1. First, you need to create a Maven project to consume the custom plugin that we just developed. Create a project with the following sample POM file, which associates the `mail-maven-plugin` with the project:

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.lsieun.plugins</groupId>
    <artifactId>plugin-consumer</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>
    <name>Maven Plugin Consumer Project</name>
    <build>
        <plugins>
            <plugin>
                <groupId>com.lsieun.plugins</groupId>
                <artifactId>mail-maven-plugin</artifactId>
                <version>1.0.0</version>
                <executions>
                    <execution>
                        <id>post-integration-mail</id>
                        <phase>post-integration-test</phase>
                        <goals>
                            <goal>mail</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

Inside the `execution` element of the plugin configuration, we associate the corresponding plugin goal with a lifecycle phase.

Just type `mvn clean install` against the previous POM file. It will execute all the phases in the Maven `default` lifecycle up to and including the `install` phase, which also includes the `post-integration-test` phase. The `mail` goal of the plugin will get executed during the `post-integration-test` phase and will result in the following output:

```txt
[INFO] --- mail-maven-plugin:1.0.0:mail (post-integration-mail) @ plugin-consumer ---
[INFO] Sending Email...
```

This is only one way of associating a plugin with a `lifecycle` phase. Here, the responsibility is with the consumer application to define the phase. The other way is that the plugin itself declares the phase it wants to execute in. To do this, you need to add the `Execute` annotation to your MOJO class, shown as follows:

```java
@Mojo( name = "mail", requiresProject=false)
@Execute (phase=LifecyclePhase.POST_INTEGRATION_TEST)
public class EmailMojo extends AbstractMojo
{
}
```

Now, in the POM file of your plugin consumer project, you do not need to define a phase for the plugin. The configuration is as follows:

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.lsieun.plugins</groupId>
    <artifactId>plugin-consumer</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>
    <name>Maven Plugin Consumer Project</name>
    <build>
        <plugins>
            <plugin>
                <groupId>com.lsieun.plugins</groupId>
                <artifactId>mail-maven-plugin</artifactId>
                <version>1.0.0</version>
            </plugin>
        </plugins>
    </build>
</project>
```

## The plugin execution order

When a plugin gets executed through a lifecycle phase, the order of execution is governed by the lifecycle itself. 

If there are multiple plugin goals associated with the same phase, then the order of execution is governed by the order you define the plugins in your application POM file.

## Inside the execute method

The business logic of a Maven plugin is implemented inside the `execute` method. The `execute` method is the only `abstract` method defined in the `org.apache.maven.plugin.AbstractMojo` class. The following Java code shows how to get the details about the current Maven project going through the build. Notice that the instance variable of the `MavenProject` type is annotated with the Component annotation:

```java
package com.lsieun.plugins;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;

@Mojo(name = "mail")
public class EmailMojo extends AbstractMojo {

    @Component
    private MavenProject project;

    public void execute() throws MojoExecutionException {
        getLog().info("Artifact Id" + project.getArtifactId());
        getLog().info("Version " + project.getVersion());
        getLog().info("Packaging " + project.getPackaging());
    }
}
```

The previous code is required to have the following three dependencies:

```xml
<dependency>
    <groupId>org.apache.maven</groupId>
    <artifactId>maven-plugin-api</artifactId>
    <version>2.0</version>
</dependency>
<dependency>
    <groupId>org.apache.maven.plugin-tools</groupId>
    <artifactId>maven-plugin-annotations</artifactId>
    <version>3.2</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>org.apache.maven</groupId>
    <artifactId>maven-core</artifactId>
    <version>3.2.1</version>
</dependency>
```

In the example use case, we took to develop the custom plugin; we need to figure out the list of recipients who we want to send the emails. Also, we might need to get connection parameters related to the mail server. The following code example shows you how to read plugin configuration details from a MOJO:

```java
@Mojo(name = "mail")
public class EmailMojo extends AbstractMojo {
    @Component
    private MavenProject project;

    public void execute() throws MojoExecutionException {
        // get all the build plugins associated with the
        // project under the build.
        List<Plugin> plugins = project.getBuildPlugins();

        if (plugins != null && plugins.size() > 0) {
            for (Iterator<Plugin> iterator = plugins.iterator(); iterator.hasNext();) {
                Plugin plugin = iterator.next();
                // iterate till we find mail-maven-plugin.
                if ("mail-maven-plugin".equals(plugin.getArtifactId()))
                {
                    getLog().info(plugin.getConfiguration().toString());
                    break;
                }
            }
        }
    }
}
```

For the email plugin we developed, the required configuration can be defined inside the plugin definition, shown as follows. This should go into the POM file of the plugin consumer application. Under the `configuration` element of the corresponding plugin, you can define your own XML element to carry out the configuration required by your custom plugin:

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.lsieun.plugins</groupId>
    <artifactId>plugin-consumer</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>
    <name>Maven Plugin Consumer Project</name>
    <build>
        <plugins>
            <plugin>
                <groupId>com.lsieun.plugins</groupId>
                <artifactId>mail-maven-plugin</artifactId>
                <version>1.0.0</version>
                <configuration>
                    <emailList>lucy@qq.com,lily@qq.com</emailList>
                    <mailServer>mail.google.com</mailServer>
                    <password>password</password>
                </configuration>
                <executions>
                    <execution>
                        <id>post-integration-mail</id>
                        <phase>post-integration-test</phase>
                        <goals>
                            <goal>mail</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

When you run the plugin with the previous configuration, it will result in the following output. The MOJO implementation can parse the XML element and get the required values:

```txt
[INFO] <?xml version="1.0" encoding="UTF-8"?>
<configuration>
  <emailList>lucy@qq.com,lily@qq.com</emailList>
  <mailServer>mail.google.com</mailServer>
  <password>password</password>
</configuration>
```
