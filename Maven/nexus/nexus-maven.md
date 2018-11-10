# Configuring Apache Maven

To use **Nexus repository manager** with **Apache Maven**, configure Maven to check the **repository manager** instead of the default, built-in connection to **the Central Repository**.

> Maven默认情况下，是使用Central Repository仓库下载JAR文件的；  
> 如果要想使用Nexus Repository Manager，而不使用Central Repository仓库，必须要对Maven进行配置才行。

To do this, you add a `mirror` configuration and override the default configuration for the central repository in your `~/.m2/settings.xml`, shown below:

## Configuring Maven to Use a Single Repository Group

```xml
<settings>
  <mirrors>
    <mirror>
      <!--This sends everything else to /public -->
      <id>nexus</id>
      <mirrorOf>*</mirrorOf>
      <!-- 注意：这里地址是否书写正确 -->
      <url>http://localhost:8081/repository/maven-public/</url>
    </mirror>
  </mirrors>
  <profiles>
    <profile>
      <id>nexus</id>
      <!--Enable snapshots for the built in central repo to direct -->
      <!--all requests to nexus via the mirror -->
      <repositories>
        <repository>
          <id>central</id>
          <url>http://central</url>
          <releases><enabled>true</enabled></releases>
          <snapshots><enabled>true</enabled></snapshots>
        </repository>
      </repositories>
     <pluginRepositories>
        <pluginRepository>
          <id>central</id>
          <url>http://central</url>
          <releases><enabled>true</enabled></releases>
          <snapshots><enabled>true</enabled></snapshots>
        </pluginRepository>
      </pluginRepositories>
    </profile>
  </profiles>
  <activeProfiles>
    <!--make the profile active all the time -->
    <activeProfile>nexus</activeProfile>
  </activeProfiles>
</settings>
```

In `~/.m2/settings.xml`, a single profile called `nexus` is defined. It configures a `repository` and a `pluginRepository` with the `id` `central` that overrides the same repositories in the Super POM. The Super POM is internal to every Apache Maven install and establishes default values. **These overrides are important** since they change the repositories by **enabling snapshots** and **replacing the URL with a bogus URL**. This `URL` is overridden by the `mirror` setting in the same `settings.xml` file to point to the URL of your single repository group. This repository group can, therefore, contain `release` as well as `snapshot` components and Maven will pick them up. 

The `mirrorOf` pattern of `*` causes any repository request to be redirected to this mirror and to your single repository group, which in the example is the public group.

It is possible to use other patterns in the `mirrorOf` field. A possible valuable setting is to use `external:*`. This matches all repositories except those using `localhost` or **file based repositories**. This is used in conjunction with a repository manager when you want to exclude redirecting repositories that are defined for integration testing. The integration test runs for Apache Maven itself require this setting.

More documentation about mirror settings can be found in [the mini guide on the Maven web site](http://maven.apache.org/guides/mini/guide-mirror-settings.html).

As a last configuration the `nexus` profile is listed as an active profile in the `activeProfiles` element.

## Deploy

Deployment to a repository is configured in the `pom.xml` for the respective project in the `distributionManagement` section. Using the default repositories of the repository manager:

```xml
<project>
...
<distributionManagement>
    <repository>
      <id>nexus</id>
      <name>Releases</name>
      <url>http://localhost:8081/repository/maven-releases</url>
    </repository>
    <snapshotRepository>
      <id>nexus</id>
      <name>Snapshot</name>
      <url>http://localhost:8081/repository/maven-snapshots</url>
    </snapshotRepository>
</distributionManagement>
...
```

The credentials used for the deployment are found in the server section of your `settings.xml`. In the example below server contains `nexus` as the `id`, along with the default `username` and `password`: 

```xml
<settings>
....
  <servers>
    <server>
      <id>nexus</id>
      <username>admin</username>
      <password>admin123</password>
    </server>
  </servers>
```




