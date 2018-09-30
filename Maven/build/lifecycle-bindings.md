# Lifecycle bindings

The `default` lifecycle is defined without any associated lifecycle
bindings, while both the `clean` and `site` lifecycles are defined with bindings. 

The standard Maven lifecycles and their associated bindings are defined under the file `META-INF/plexus/components.xml` of `MAVEN_HOME/lib/maven-core-3.x.x.jar`. [Link](components-a.xml)

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
        <default-phases>
            <clean>
                org.apache.maven.plugins:maven-clean-plugin:2.5:clean
            </clean>
        </default-phases>
    </configuration>
</component>
```

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





