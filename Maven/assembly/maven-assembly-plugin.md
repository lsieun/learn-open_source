# The assembly plugin

```bash
$ mvn help:describe -Dplugin=assembly
```

The assembly plugin introduces **eight** goals; however, **six** of them are deprecated, including the `attached` goal. It is not recommended that you use any of the deprecated goals. Later, we'll see how to use the `single` goal of the `assembly` plugin instead of the deprecated `attached` goal. The following lists out the six deprecated goals of the `assembly` plugin. If you are using any of them, you should migrate your project to use the `single` goal, except for the last one, the `unpack` goal. For this, you need to use the `unpack` goal of the Maven `dependency` plugin.

- assembly:assembly
- assembly:attached
- assembly:directory
- assembly:directory-single
- assembly:directory-inline
- assembly:unpack


More details about the Maven `assembly` plugin and its goals can be found at http://maven.apache.org/plugins/maven-assembly-plugin/plugin-info.html.


```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-assembly-plugin</artifactId>
    <executions>
        <execution>
            <id>copy_components</id>
            <phase>test</phase>
            <goals>
                <goal>attached</goal>
            </goals>
            <configuration>
                <filters>
                    <filter>${basedir}/src/assembly/filter.properties</filter>
                </filters>
                <descriptors>
                    <descriptor>src/assembly/dist.xml</descriptor>
                </descriptors>
            </configuration>
        </execution>
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
                <descriptors>
                    <descriptor>src/assembly/bin.xml</descriptor>
                    <descriptor>src/assembly/src.xml</descriptor>
                    <descriptor>src/assembly/docs.xml</descriptor>
                </descriptors>
            </configuration>
        </execution>
    </executions>
</plugin>
```


## Assembly help

As discussed before, the `assembly` plugin currently has **only two active goals**: `single` and `help`; all the others are deprecated. The `single` goal is responsible for creating the archive with all sort of other configurations.

The following command shows how to execute the `help` goal of the `assembly` plugin. This has to be executed from a directory that has a POM file:

```
$ mvn assembly:help -Ddetail=true
```

If you see the following error when you run this command, you might not have the latest version. Update the plugin version to 2.4.1 or higher:

```txt
[ERROR] Could not find goal 'help' in plugin
org.apache.maven.plugins:maven-assembly-plugin:2.2-beta-2 among
available goals assembly, attach-assembly-descriptor, attach-
component-descriptor, attached, directory-inline, directory,
directory-single, single, unpack -> [Help 1]
```







