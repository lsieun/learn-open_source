# The update policy


Does Maven always download from `remote` repositories even if an artifact is already available in the `local` repository? To answer this question correctly, we need to dig deep into how we define `remote` repositories in Maven.


Remote repositories can be further divided into three: `release`, `snapshot`, and `plugin`.

- A `release` repository holds artifacts that have a fixed version. An artifact with the given `groupId` , `artifactId` , and `version` tags (`GAV` coordinates) is **the same all the time**. The following is an example of a released dependency. If you download this dependency today and then again in a month, both will be the same artifact:

```xml
<dependency>
    <groupId>com.googlecode.json-simple</groupId>
    <artifactId>json-simple</artifactId>
    <version>1.1</version>
</dependency>
```

- A `snapshot` repository holds artifacts that have a special version, which ends with `SNAPSHOT`. Any artifact that has the `SNAPSHOT` version **can change over time**. What you download from the repository might not be the same if you download it again in a month. The following is an example of a `SNAPSHOT` dependency. You add a `SNAPSHOT` version to the project artifacts, which are still under development. As a convention, the version to be released will be postfixed by the keyword `SNAPSHOT`:

```xml
<dependency>
    <groupId>org.apache.axis2</groupId>
    <artifactId>axis2-kernel</artifactId>
    <version>1.7.0-SNAPSHOT</version>
</dependency>
```

A `plugin` repository is a remote repository that holds plugins. A plugin repository can be a `release` repository or a `snapshot` repository.


Repositories that are defined in the super POM file are shown in the following configuration. Here, it's the same repository that acts as the `release` repository as well as the `plugin` repository. If we set `<snapshots><enabled>true</enabled></snapshots>`, then the corresponding repository is treated as a `snapshot` repository(我的疑问：应该是指即作为release repository，也作为snapshot repository吧？); if set to `false`, then it's a `release-only` repository:

```xml
  <repositories>
    <repository>
        <id>central</id>
        <name>Central Repository</name>
        <url>https://repo.maven.apache.org/maven2</url>
        <layout>default</layout>
        <!-- 关注点 -->
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
    </repository>
  </repositories>

  <pluginRepositories>
    <pluginRepository>
        <id>central</id>
        <name>Central Repository</name>
        <url>https://repo.maven.apache.org/maven2</url>
        <layout>default</layout>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
        <!-- 关注点 -->
        <releases>
            <updatePolicy>never</updatePolicy>
        </releases>
    </pluginRepository>
  </pluginRepositories>
```

Let's get back to our original question: does Maven always download artifacts from `remote` repositories, even if an artifact is already available in the `local` repository? This relies on the repository configuration. In the preceding code snippet from the super POM file, you will find the following under the `pluginRepository` section:

```xml
<releases>
    <updatePolicy>never</updatePolicy>
</releases>
```

The updatePolicy element can carry any of the values from `always`, `daily`, `interval:X`, or `never`. In this case, it is set to `never`, which means that the artifacts from this repository will be downloaded only if they are not available in the `local` repository. This is a perfectly valid configuration for a `release` repository. If this is a `snapshot` repository, then it won't work, and you might have to work with stale artifacts. For a `snapshot` repository, you have to set the value to `always`, `daily` or `interval:X`.

The `always` value means that Maven will always download the artifacts in every build. If the `updatePolicy` element says `daily`, then Maven will download the artifacts from the remote repository only once for a given day during the build. It compares the metadata associated with the `local` POM file with the remote one to see which has the latest timestamp. If the value is set to `interval:X` where `X` is an integer value in **minutes**, Maven will download the artifact only after this time interval. 

The default value of the `updatePolicy` configuration is `daily`.


