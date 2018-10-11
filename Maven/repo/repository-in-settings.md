# Repositories in settings.xml

You can also define repositories in `settings.xml` , which is available by default under the `USER_HOME/.m2` directory. The repositories defined in `settings.xml` will **get priority over** all the other repositories. The following explains how to add plugin repositories to `settings.xml` :

Open `USER_HOME/.m2/settings.xml` and look for the `<profiles>` element.

Add the following section under the `<profiles>` element. When you define repositories in `settings.xml` , they must be within a `profile` element. This configuration introduces two plugin repositories and both are defined as snapshot repositories.

```xml
<profile>
    <id>apache</id>
    <pluginRepositories>
        <pluginRepository>
            <id>apache.snapshots</id>
            <name>Apache Snapshot Repository</name>
            <url>http://repository.apache.org/snapshots</url>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>daily</updatePolicy>
            </snapshots>
            <releases>
                <enabled>false</enabled>
            </releases>
        </pluginRepository>
        <pluginRepository>
            <id>apache-snapshots</id>
            <name>Apache Snapshots Repository</name>
            <url>http://people.apache.org/repo/m2-snapshot-repository</url>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>daily</updatePolicy>
            </snapshots>
            <releases>
                <enabled>false</enabled>
            </releases>
        </pluginRepository>
    </pluginRepositories>
</profile>
```

Go to a directory that has any `pom.xml` file and execute the following command, which will display the effective POM. Here, we are executing the
`effective-pom` goal of the `help` Maven plugin with an additional argument, which starts with `-P`. The `-P` tag needs to be post fixed with the name of the profile defined in the `settings.xml`, where we have our plugin repositories. In this case, the name of the profile is `apache`(`<id>apache</id>`):

```bash
$ mvn help:effective-pom -Papache
```

Assuming that you have no repositories defined in your application POM file, the above command will display the following. This includes repositories from the `settings.xml` as well as from **the super POM**:

```xml
<pluginRepositories>
    <pluginRepository>
        <releases>
            <enabled>false</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>daily</updatePolicy>
        </snapshots>
        <id>apache.snapshots</id>
        <name>Apache Snapshot Repository</name>
        <url>http://repository.apache.org/snapshots</url>
    </pluginRepository>
    <pluginRepository>
        <releases>
            <enabled>false</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>daily</updatePolicy>
        </snapshots>
        <id>apache-snapshots</id>
        <name>Apache Snapshots Repository</name>
        <url>http://people.apache.org/repo/m2-snapshot-repository</url>
    </pluginRepository>
    <pluginRepository>
        <releases>
            <updatePolicy>never</updatePolicy>
        </releases>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
        <id>central</id>
        <name>Central Repository</name>
        <url>https://repo.maven.apache.org/maven2</url>
    </pluginRepository>
</pluginRepositories>
```


