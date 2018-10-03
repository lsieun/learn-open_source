# Lifecycle bindings

```xml
lifecycle
phase    <!-- 本文关注 -->
goal     <!-- 本文关注 -->
plugin
mvn command
```

> The standard Maven lifecycles and their associated bindings are defined under the file `META-INF/plexus/components.xml` of `MAVEN_HOME/lib/maven-core-3.x.x.jar`. [Link](components-a.xml)

!TOC

## phase and goals

### phase

A phase in a lifecycle is just **an ordered placeholder** in the build execution path. For example, the `clean` phase in the `clean` lifecycle cannot do anything on its own. When you type `mvn clean`, it cleans out project's working directory (by default, it's the `target` directory). This is done via **the Maven clean plugin**.

> 每个phase的本质是一个placeholder。

The `plugin goal` to `lifecycle phase` **mapping** can be provided through **the application `POM` file**. If not, it will be inherited from **the super POM file**. The super POM file, which defines the `clean` plugin by default, adds the plugin to the `clean` phase of the `clean` lifecycle.

### plugin goals

To find more details about **the Maven clean plugin**, type the following command. It describes all the goals defined inside the clean plugin:

```bash
$ mvn help:describe -Dplugin=clean
```

Output:

```txt
Name: Apache Maven Clean Plugin
Description: The Maven Clean Plugin is a plugin that removes files generated
  at build-time in a project's directory.
Group Id: org.apache.maven.plugins
Artifact Id: maven-clean-plugin
Version: 3.1.0
Goal Prefix: clean

This plugin has 2 goals:

clean:clean
  Description: Goal which cleans the build.
    This attempts to clean a project's working directory of the files that were
    generated at build-time. By default, it discovers and deletes the
    directories configured in project.build.directory,
    project.build.outputDirectory, project.build.testOutputDirectory, and
    project.reporting.outputDirectory.
    
    Files outside the default may also be included in the deletion by
    configuring the filesets tag.

clean:help
  Description: Display help information on maven-clean-plugin.
    Call mvn clean:help -Ddetail=true -Dgoal=<goal-name> to display parameter
    details.

For more information, run 'mvn help:describe [...] -Ddetail'
```

Everything in Maven is a plugin. Even the command we executed previously to get goal details of **the clean plugin** executes another plugin: **the help plugin**. The following command will describe **the help plugin** itself: 

```bash
$ mvn help:describe -Dplugin=help
``` 

describe is a goal defined inside the help plugin.

## Lifecycle Binding: `clean` 

The `clean` lifecycle is defined with an associated plugin binding to the `clean` goal of `maven-clean-plugin`. The plugin binding is defined under the element `<default-phases>`. The code is as follows:

```xml
<!-- Lifecycle clean -->
<component>
    <role>org.apache.maven.lifecycle.Lifecycle</role>
    <implementation>org.apache.maven.lifecycle.Lifecycle</implementation>
    <role-hint>clean</role-hint>
    <configuration>
        <id>clean</id>
        <phases>
            <phase>pre-clean</phase>
            <phase>clean</phase>
            <phase>post-clean</phase>
        </phases>
        <!-- 关注点 -->
        <default-phases>
            <clean>
                org.apache.maven.plugins:maven-clean-plugin:2.5:clean
            </clean>
        </default-phases>
    </configuration>
</component>
```

The following figure shows the relationship between **the Maven clean plugin goals**
and **the clean lifecycle phases**:

![](images/clean-phase-clean-goal.png)

### phase: `clean`

这里可以看一下clean到底是做了什么呢？

### phase: `pre-clean` and `post-clean`

The objective of the `pre-clean` phase is to perform any operations prior to the cleaning task and the objective of the `post-clean` phase is to perform any operations after the cleaning task. 

> 两个phase的作用。

The `pre-clean` and `post-clean` phases of the `clean` lifecycle do not have any plugin bindings. 

> 两个phase没有任何plugin绑定。

If you need to associate any plugins with these two phases, you simply need to add them to the corresponding plugin configuration.

> 如果有需要，就使用它们两个phase。

我可以做一个播放音乐的功能，在开始之前播放一段音乐，在结束之后播放一段音乐。

## Lifecycle Binding: `site` 

The `site` lifecycle is defined with the associated plugin bindings to the `site` and the `site-deploy` goals of `maven-site-plugin`. The plugin bindings are defined under the element `<default-phases>`. The code is as follows:

```xml
<!-- Lifecycle site -->
<component>
    <role>org.apache.maven.lifecycle.Lifecycle</role>
    <implementation>org.apache.maven.lifecycle.Lifecycle</implementation>
    <role-hint>site</role-hint>
    <configuration>
        <id>site</id>
        <phases>
            <phase>pre-site</phase>
            <phase>site</phase>
            <phase>post-site</phase>
            <phase>site-deploy</phase>
        </phases>
        <default-phases>
            <site>
                org.apache.maven.plugins:maven-site-plugin:3.3:site
            </site>
            <site-deploy>
                org.apache.maven.plugins:maven-site-plugin:3.3:deploy
            </site-deploy>
        </default-phases>
    </configuration>
</component>
```

> 上面是site lifecycle;  
> 下面是site plugin

The `site` plugin is used to generate static HTML content for a project. The generated HTML content will also include appropriate reports corresponding to the project.

The `site` plugin defines eight goals and two of them are directly associated with the phases in the `site` lifecycle.

```bash
mvn help:describe -Dcmd=site
```

![](images/site-lifecycle-and-plugin.png)

The `site` lifecycle has no value without the Maven `site` plugin.

> 如果离开了site plugin，site lifecycle本身并没有多大的意义。  
> have no value，不是“没有参数值”，而是“没有价值、没有意义”。



## Lifecycle Binding: `default` 

The `default` lifecycle is defined without any associated lifecycle
bindings, while both the `clean` and `site` lifecycles are defined with bindings. 



Here is the definition of the `default` lifecycle without the associated **plugin** bindings:

```xml
<!-- Lifecycle default -->
<component>
    <role>org.apache.maven.lifecycle.Lifecycle</role>
    <implementation>org.apache.maven.lifecycle.Lifecycle</implementation>
    <role-hint>default</role-hint>
    <configuration>
        <id>default</id>
        <phases>
            <phase>validate</phase>
            <phase>initialize</phase>
            <phase>generate-sources</phase>
            <phase>process-sources</phase>
            <phase>generate-resources</phase>
            <phase>process-resources</phase>
            <phase>compile</phase>
            <phase>process-classes</phase>
            <phase>generate-test-sources</phase>
            <phase>process-test-sources</phase>
            <phase>generate-test-resources</phase>
            <phase>process-test-resources</phase>
            <phase>test-compile</phase>
            <phase>process-test-classes</phase>
            <phase>test</phase>
            <phase>prepare-package</phase>
            <phase>package</phase>
            <phase>pre-integration-test</phase>
            <phase>integration-test</phase>
            <phase>post-integration-test</phase>
            <phase>verify</phase>
            <phase>install</phase>
            <phase>deploy</phase>
        </phases>
    </configuration>
</component>
```


The `components.xml` file, which is also known as **the component descriptor**, describes the properties required by Maven to manage the lifecycle of a Maven project. 

- `role`: The `<role>` element specifies the Java interface exposed by this lifecycle component and defines the type of the component. All the lifecycle components must have `org.apache.maven.lifecycle.Lifecycle` as role . 
- `implementation`: The `<implementation>` tag specifies the concrete implementation of the interface. 
- `role-hint`: **The identity of a component** is defined by the combination of the `<role>` and the `<role-hint>` elements. The `<role-hint>` element is not a mandatory element; however, if we have multiple elements of the same type, then we must define a `<role-hint>` element. Corresponding to Maven lifecycles, the name of the lifecycle is set as the value of the `<role-hint>` element.

> 注意：上面`<configuration>`标签下的`id`和`phases`和下面`Lifecycle.java`中的属性是一样的。

```java
package org.apache.maven.lifecycle;

import java.util.List;
import java.util.Map;
import org.apache.maven.lifecycle.mapping.LifecyclePhase;

public class Lifecycle
{
    private String id;
    private List<String> phases;
    private Map<String, LifecyclePhase> defaultPhases;
  
    public Lifecycle() {}
    
    public Lifecycle(String id, List<String> phases, Map<String, LifecyclePhase> defaultPhases)
    {
        this.id = id;
        this.phases = phases;
        this.defaultPhases = defaultPhases;
    }
    
    public String getId()
    {
        return this.id;
    }
    
    public List<String> getPhases()
    {
        return this.phases;
    }
    
    public Map<String, LifecyclePhase> getDefaultLifecyclePhases()
    {
        return this.defaultPhases;
    }
    
    @Deprecated
    public Map<String, String> getDefaultPhases()
    {
        return LifecyclePhase.toLegacyMap(getDefaultLifecyclePhases());
    }
    
    public String toString()
    {
        return this.id + " -> " + this.phases;
    }
}
```

**The phases in the `default` lifecycle do not have any associated plugin goals**. The plugin bindings for each phase are defined by the corresponding packaging. If the
type of packaging of your Maven project is `JAR`, then it will define its own set of plugins for each phase. If the packaging type is `WAR`, then it will have its own set of plugins. 

The `packaging` type of a given Maven project is defined under the `<packaging>` element in the `pom.xml` file. If the element is omitted, then Maven assumes it as `jar` packaging.

```xml
<groupId>lsieun</groupId>
<artifactId>aegis</artifactId>
<version>1.0-SNAPSHOT</version>
<!-- packaging的默认值是jar。 -->
<packaging>jar</packaging>
```

![](images/default-lifecycle.png)

```bash
mvn help:describe -Dcmd=deploy
```



Finally, let's have a look at how the `jar` plugin binding for the `default` lifecycle is defined. The following component element defines a plugin binding to an existing lifecycle and the associated lifecycle is defined under the `configuration/lifecycles/lifecycle/id` element:

```xml
<!-- LifecycleMapping jar -->
<component>
    <role>org.apache.maven.lifecycle.mapping.LifecycleMapping</role>
    <role-hint>jar</role-hint>
    <implementation>org.apache.maven.lifecycle.mapping.DefaultLifecycleMapping</implementation>
    <configuration>
        <lifecycles>
            <lifecycle>
                <id>default</id>
                <phases>
                    <process-resources>
                        org.apache.maven.plugins:maven-resources-plugin:2.6:resources
                    </process-resources>
                    <compile>
                        org.apache.maven.plugins:maven-compiler-plugin:3.1:compile
                    </compile>
                    <process-test-resources>
                        org.apache.maven.plugins:maven-resources-plugin:2.6:testResources
                    </process-test-resources>
                    <test-compile>
                        org.apache.maven.plugins:maven-compiler-plugin:3.1:testCompile
                    </test-compile>
                    <test>
                        org.apache.maven.plugins:maven-surefire-plugin:2.12.4:test
                    </test>
                    <package>
                        org.apache.maven.plugins:maven-jar-plugin:2.4:jar
                    </package>
                    <install>
                        org.apache.maven.plugins:maven-install-plugin:2.4:install
                    </install>
                    <deploy>
                        org.apache.maven.plugins:maven-deploy-plugin:2.7:deploy
                    </deploy>
                </phases>
            </lifecycle>
        </lifecycles>
    </configuration>
</component>
```

```java
package org.apache.maven.lifecycle.mapping;

import java.util.List;
import java.util.Map;

public abstract interface LifecycleMapping
{
    @Deprecated
    public static final String ROLE = LifecycleMapping.class.getName();
    
    public abstract Map<String, Lifecycle> getLifecycles();
    
    @Deprecated
    public abstract List<String> getOptionalMojos(String paramString);
    
    @Deprecated
    public abstract Map<String, String> getPhases(String paramString);
}
```

```java
package org.apache.maven.lifecycle.mapping;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DefaultLifecycleMapping implements LifecycleMapping
{
    private List<Lifecycle> lifecycles;
    private Map<String, Lifecycle> lifecycleMap;
    /**
    * @deprecated
    */
    private Map<String, LifecyclePhase> phases;
    
    private void initLifecycleMap()
    {
        if (this.lifecycleMap == null)
        {
            this.lifecycleMap = new HashMap();
            Iterator localIterator;
            Lifecycle lifecycle;
            if (this.lifecycles != null)
            {
                for (localIterator = this.lifecycles.iterator(); localIterator.hasNext();)
                {
                    lifecycle = (Lifecycle)localIterator.next();
                    
                    this.lifecycleMap.put(lifecycle.getId(), lifecycle);
                }
            }
            else
            {
                String[] lifecycleIds = { "default", "clean", "site" };
                for (String lifecycleId : lifecycleIds)
                {
                    Map<String, LifecyclePhase> phases = getLifecyclePhases(lifecycleId);
                    if (phases != null)
                    {
                        Lifecycle lifecycle = new Lifecycle();
                        
                        lifecycle.setId(lifecycleId);
                        lifecycle.setLifecyclePhases(phases);
                        
                        this.lifecycleMap.put(lifecycleId, lifecycle);
                    }
                }
            }
        }
    }
    
    public Map<String, Lifecycle> getLifecycles()
    {
        initLifecycleMap();
        
        return this.lifecycleMap;
    }
    
    public List<String> getOptionalMojos(String lifecycle)
    {
        return null;
    }
    
    private Map<String, LifecyclePhase> getLifecyclePhases(String lifecycle)
    {
        initLifecycleMap();
        
        Lifecycle lifecycleMapping = (Lifecycle)this.lifecycleMap.get(lifecycle);
        if (lifecycleMapping != null) {
            return lifecycleMapping.getLifecyclePhases();
        }
        if ("default".equals(lifecycle)) {
            return this.phases;
        }
        return null;
    }
    
    @Deprecated
    public Map<String, String> getPhases(String lifecycle)
    {
        return LifecyclePhase.toLegacyMap(getLifecyclePhases(lifecycle));
    }
}

```





