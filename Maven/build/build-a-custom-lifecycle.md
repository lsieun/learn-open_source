# Building a custom lifecycle

A lifecycle defines a process. It defines an ordered set of phases that get executed one after the other. The Maven `default` lifecycle is sufficient to address most of the use cases in build management and automation. 

However, we might need to alter the behavior of certain phases. **Defining a phase** and **altering a phase** are two different things and they are done in two different ways. Accepting the `default` lifecycle but altering its default behavior has to be done with **a proper plugin binding**.

## Alter a phase with an existing lifecycle

To associate a plugin goal with an existing lifecycle, you need to define a lifecycle mapping in the `META-INF/plexus/components.xml` file of the corresponding plugin.

The complete `components.xml` file of `axis2-aar-maven-plugin` is available at http://svn.apache.org/repos/asf/axis/axis2/java/core/trunk/modules/tool/axis2-aar-maven-plugin/src/main/resources/META-INF/plexus/components.xml. [Link](aar-components.xml)

The following code snippet shows how the plugin binding for the `aar` packaging type
is defined, with the `axis2-aar-maven-plugin`:

```xml
<component-set>
    <component>
        <role>org.apache.maven.lifecycle.mapping.LifecycleMapping</role>
        <role-hint>aar</role-hint>
        <implementation>
            org.apache.maven.lifecycle.mapping.DefaultLifecycleMapping
        </implementation>

        <configuration>
            <lifecycles>
                <lifecycle>
                <id>default</id>
                <phases>
                    <process-resources>
                        org.apache.maven.plugins:maven-resources-plugin:resources
                    </process-resources>
                    <compile>
                        org.apache.maven.plugins:maven-compiler-plugin:compile
                    </compile>
                    <process-test-resources>
                        org.apache.maven.plugins:maven-resources-plugin:testResources
                    </process-test-resources>
                    <test-compile>
                        org.apache.maven.plugins:maven-compiler-plugin:testCompile
                    </test-compile>
                    <test>
                        org.apache.maven.plugins:maven-surefire-plugin:test
                    </test>
                    <package>
                        org.apache.axis2:axis2-aar-maven-plugin:aar
                    </package>
                    <install>
                        org.apache.maven.plugins:maven-install-plugin:install
                    </install>
                    <deploy>
                        org.apache.maven.plugins:maven-deploy-plugin:deploy
                    </deploy>
                </phases>
                </lifecycle>
            </lifecycles>
        </configuration>
    </component>
</component-set>
```

Let's have a quick look at another example. The following `maven-bundle-plugin` that is available at https://github.com/sonatype/sonatype-bundle-plugin/blob/master/src/main/resources/META-INF/plexus/components.xml defines
a custom behavior for the package , install , and deploy phases of the default
lifecycle for an artifact that has the bundle custom packaging:

```xml
<component-set>
    <components>
        <component>
        <role>org.apache.maven.lifecycle.mapping.LifecycleMapping</role>
        <role-hint>bundle</role-hint>
        <implementation>org.apache.maven.lifecycle.mapping.DefaultLifecycleMapping</implementation>
        <configuration>
            <lifecycles>
            <lifecycle>
                <id>default</id>
                <!-- START SNIPPET: bundle-lifecycle -->
                <phases>
                <process-resources>org.apache.maven.plugins:maven-resources-plugin:resources</process-resources>
                <compile>org.apache.maven.plugins:maven-compiler-plugin:compile</compile>
                <process-test-resources>org.apache.maven.plugins:maven-resources-plugin:testResources</process-test-resources>
                <test-compile>org.apache.maven.plugins:maven-compiler-plugin:testCompile</test-compile>
                <test>org.apache.maven.plugins:maven-surefire-plugin:test</test>
                <package>org.apache.felix:maven-bundle-plugin:bundle</package>
                <install>
                    org.apache.maven.plugins:maven-install-plugin:install,
                    org.apache.felix:maven-bundle-plugin:install
                </install>
                <deploy>
                    org.apache.maven.plugins:maven-deploy-plugin:deploy,
                    org.apache.felix:maven-bundle-plugin:deploy
                </deploy>
                </phases>
                <!-- END SNIPPET: bundle-lifecycle -->
            </lifecycle>
            </lifecycles>
        </configuration>
        </component>
    </components>
</component-set>
```

## Define custom lifecycle

Code [Link](code/custom-lifecycle)

Plugins can introduce custom behaviors for existing lifecycle phases. How can we define our own lifecycle phases? Let's see how to write our own custom lifecycle with the following four phases:

- get-code
- build-code
- run-tests
- notify

The directory structure of the plugin project will be as follows:

```txt
|-pom.xml
|-src/main
        |-java/com/lsieun/lifecycle/sample/*.java
        |-resources/META-INF/plexus/components.xml
```

or

```
.
├── pom.xml
└── src
    └── main
        ├── java
        │   └── com
        │       └── lsieun
        │           └── lifecycle
        │               └── sample
        │                   ├── BuildCodeGoalMojo.java
        │                   ├── GetCodeGoalMojo.java
        │                   ├── NotifyGoalMojo.java
        │                   └── RunTestsGoalMojo.java
        └── resources
            └── META-INF
                └── plexus
                    └── components.xml
```

The steps are as follows:

第一步：定义pom.xml。

First, you can use the following `POM` file to build the plugin project. Here, the value of the `<packaging>` is set to `maven-plugin`, and then Maven knows how to build this project as a plugin. The code is as follows:


```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.lsieun</groupId>
    <artifactId>custom-lifecycle</artifactId>
    <version>1.0.0</version>
    <packaging>maven-plugin</packaging>

    <dependencies>
        <dependency>
            <groupId>org.apache.maven</groupId>
            <artifactId>maven-plugin-api</artifactId>
            <version>2.0</version>
        </dependency>
    </dependencies>
</project>

```


第二步：定义MOJO。

Now we need to write a **Maven plain Old Java Object** (**MOJO**) , which extends from `org.apache.maven.plugin.AbstractMojo`. One `MOJO` can handle only one goal at a time, so we need to have four MOJOs — one for each goal. The plugin goal supported by this class needs to be set as a Javadoc tag: `@goal get-code-goal`. The code is as follows:

```java
package com.lsieun.lifecycle.sample;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal get-code-goal
 * @requires Project false
 */
public class GetCodeGoalMojo extends AbstractMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("get-code-goal");
    }
}

```

In the same way, you need to have three more classes, one for each goal, and make sure that you have the right Javadoc tag in each class.

第三步：定义components.xml配置文件。

Now, we need to define our custom lifecycle phases in a `components.xml`. Inside the `default-phases` element, we associate plugin goals with each of the custom phase. Later, we'll see how to define goals within a plugin. The code is as follows:

```xml
<component-set>
    <components>
        <component>
            <role>org.apache.maven.lifecycle.Lifecycle</role>
            <role-hint>zar</role-hint>
            <implementation>org.apache.maven.lifecycle.Lifecycle</implementation>
            <configuration>
                <id>custom_lifecycle</id> 
                <phases>
                    <phase>get-code</phase>
                    <phase>build-code</phase>
                    <phase>run-tests</phase>
                    <phase>notify</phase>
                </phases>
                <default-phases>
                    <get-code>com.lsieun:custom-lifecycle:get-code-goal</get-code>
                    <build-code>com.lsieun:custom-lifecycle:build-code-goal</build-code>
                    <run-tests>com.lsieun:custom-lifecycle:run-tests-goal</run-tests>
                    <notify>com.lsieun:custom-lifecycle:notify-goal</notify>
                </default-phases>
            </configuration>
        </component> 
    </components>
</component-set>

```

Now, we can build the project using `mvn clean install`. The plugin will get installed in the local Maven repository. The plugin that we created with a custom lifecycle is now ready for use by any Maven project. 

第四步：测试。

Let's create a simple Maven project with just the following POM file to consume this plugin:

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.lsieun</groupId>
    <artifactId>test-lifecycle</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <name>Custom Lifecycle Project</name>

    <build>
        <plugins>
            <plugin>
                <groupId>com.lsieun</groupId>
                <artifactId>custom-lifecycle</artifactId>
                <version>1.0.0</version>
                <extensions>true</extensions>
            </plugin>
        </plugins>
    </build>
</project>

```

Now, you can execute the custom phase in the following manner against the previous POM file:

```bash
$ mvn notify
```

This will execute all the phases in the custom lifecycle up to and including the `notify` phase, and we will get the following output:

```
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] Building Custom Lifecycle Project 1.0.0
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- custom-lifecycle:1.0.0:get-code-goal (default-get-code-goal) @ test-lifecycle ---
get-code-goal
[INFO] 
[INFO] --- custom-lifecycle:1.0.0:build-code-goal (default-build-code-goal) @ test-lifecycle ---
build-code-goal
[INFO] 
[INFO] --- custom-lifecycle:1.0.0:run-tests-goal (default-run-tests-goal) @ test-lifecycle ---
run-tests-goal
[INFO] 
[INFO] --- custom-lifecycle:1.0.0:notify-goal (default-notify-goal) @ test-lifecycle ---
notify-goal
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 0.137 s
[INFO] Finished at: 2018-10-03T18:25:11+08:00
[INFO] Final Memory: 8M/303M
[INFO] ------------------------------------------------------------------------
```
