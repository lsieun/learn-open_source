
The previous step will produce the `mail-maven-plugin-1.0.0.jar` file inside the `target` directory of your Maven project. Extract the JAR file with following command:

```bash
$ jar -xvf mail-maven-plugin-1.0.0.jar
```

The extracted JAR file will have the following directory structure, with the generated metadata files. Only the **key/important** files are shown here:

```txt
|-com/lsieun/plugins/EmailMojo.class
|-META-INF
    |-maven/plugin.xml
```

Let's have a look at the `plugin.xml` file first, which is as follows. A `<mojo>` element will be generated for each MOJO in the plugin project, having the annotation `Mojo`. All the child elements defined under the `<mojo>` element are derived from the annotations. If there is no annotation, the default value is set. We will discuss the key attributes in the `plugin.xml` file later in this chapter.

```xml
<plugin>
  <name>mail-maven-plugin</name>
  <description></description>
  <groupId>com.lsieun.plugins</groupId>
  <artifactId>mail-maven-plugin</artifactId>
  <version>1.0.0</version>
  <goalPrefix>mail</goalPrefix>
  <isolatedRealm>false</isolatedRealm>
  <inheritedByDefault>true</inheritedByDefault>
  <mojos>
    <mojo>
      <goal>mail</goal>
      <requiresDirectInvocation>false</requiresDirectInvocation>
      <requiresProject>true</requiresProject>
      <requiresReports>false</requiresReports>
      <aggregator>false</aggregator>
      <requiresOnline>false</requiresOnline>
      <inheritedByDefault>true</inheritedByDefault>
      <implementation>com.lsieun.plugins.EmailMojo</implementation>
      <language>java</language>
      <instantiationStrategy>per-lookup</instantiationStrategy>
      <executionStrategy>once-per-session</executionStrategy>
      <threadSafe>false</threadSafe>
      <parameters/>
    </mojo>
  </mojos>
  <dependencies>
    <dependency>
      <groupId>org.apache.maven</groupId>
      <artifactId>maven-plugin-api</artifactId>
      <type>jar</type>
      <version>2.0</version>
    </dependency>
  </dependencies>
</plugin>
```

The following Mojo annotation of the `EmailMojo` class will generate exactly the same configuration as shown in the previous step:

```java
@Mojo(name = "mail", requiresDirectInvocation = false,
    requiresProject = true, requiresReports = false,
    aggregator = true, requiresOnline = true,
    inheritByDefault = true, instantiationStrategy =
    InstantiationStrategy.PER_LOOKUP, executionStrategy =
    "once-per-session", threadSafe = false)
```

Before moving any further, let's have a look at the definition of each
configuration element used in the previous Mojo annotation:

- `name`: Every MOJO has a goal. The `name` attribute represents the goal name.
- `requiresDirectInvocation`: A given plugin can be invoked in **two ways**. **The first** is by direct invocation where you invoke the plugin as `mvn plugin-name:goal-name`. **The second way** of invoking a plugin is as part of a Maven lifecycle, where you execute a lifecycle phase, and being part of a lifecycle phase, plugin goals also get executed. If you set `requiresDirectInvocation` to `true`, then you cannot associate the plugin with a lifecycle.
- `requiresProject`: If `requiresProject` is set to `true`, this means you cannot execute the Maven plugin without a Maven project. It must be executed against a Maven POM file.
- `requiresReports`: If your plugin depends on a set of reports, the goal of your plugin is to aggregate, or summarize a set of reports, then you must set the value of `requiresReports` to `true`.
- `aggregator`: If you set the value of `aggregator` to `true`, the corresponding goal of your plugin will get executed only once during the complete build lifecycle. In other words, it won't run for each project build. In our case, we want to send an email when the complete Maven build is executed and not for each project; in this case, we must set the value of the `aggregator` to `true`.
- `requiresOnline`: If you set the value of `requiresOnline` to `true`, the corresponding goal of your plugin will only get executed when you are performing an online build. In our case, we have to set `requiresOnline` to `true`, because you need to be online to send an email.
- `instantiationStrategy`: This is related to Plexus. If the value of `instantiationStrategy` is set to `per-lookup`, then a new instance of the corresponding MOJO will be created each time Maven looks up from Plexus. Other possible values are `keep-alive`, `singleton`, and `poolable`.
- `executionStrategy`: This attribute will be deprecated in the future. It informs Maven when and how to execute a MOJO. The possible values are `once-per-session` and `always`.
- `threadSafe`: Once the value of `threadSafe` is set to `true`, MOJO will execute in a thread-safe manner during parallel builds.
- `inheritByDefault`: If the value of `inheritByDefault` is set to `true`, then any plugin goal associated with a Maven project will be inherited by all its child projects.

Another important element in the generated `plugin.xml` file is `<goalPrefix>`. If nothing is explicitly mentioned in `maven-plugin-plugin`, the value of `goalPrefix` is derived by **the naming convention of the plugin artifactId**. In our case, the artifactId of the plugin is `mail-maven-plugin` and the value before the first hyphen is taken as the `goalPrefix` . Maven uses `goalPrefix` to invoke a plugin goal in the following manner:

```bash
$ mvn goalPrefix:goal
```

In our case, our custom plugin can be executed as follows, where the first `mail` word is the `goalPrefix` , while the second one is the goal name:

```bash
$ mvn mail:mail
```

If you want to override the value of the `goalPrefix` without following the naming convention, then you need to explicitly give a value to the `<goalPrefix>` configuration element of `maven-plugin-plugin` in the POM file of the custom Maven plugin project, as follows:

```xml
<configuration>
    <goalPrefix>email</goalPrefix>
</configuration>
```



























