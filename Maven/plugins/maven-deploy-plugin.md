# The deploy plugin

The `deploy` plugin will deploy the final project artifacts into a remote Maven repository. 

The `deploy` goal of the `deploy` plugin is associated with the `deploy` phase of the `default` Maven lifecycle. To deploy an artifact via the `default` lifecycle, `mvn clean install` is not sufficient; it has to be `mvn clean deploy`. Any guesses why? 

The `deploy` phase of the `default` Maven lifecycle comes after the `install` phase. Executing `mvn clean deploy` will execute all the phases of the `default` Maven lifecycle up to and including the `deploy` phase, which also includes the `install` phase. 

The following command shows how to execute the `deploy` goal of the `deploy` plugin by itself:

```bash
$ mvn deploy:deploy
```

## default bindings

All the Maven projects inherit the `deploy` plugin from **the super POM file**. As shown in the following configuration, **the super POM** defines the `deploy` plugin. It associates the `deploy` goal with the `deploy` phase of the Maven `default` lifecycle:

```xml
<plugin>
    <artifactId>maven-deploy-plugin</artifactId>
    <version>2.7</version>
    <executions>
        <execution>
            <!-- 关注点 -->
            <id>default-deploy</id>
            <phase>deploy</phase>
            <goals>
                <goal>deploy</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## remote maven repo

Before executing either `mvn deploy:deploy` or `mvn deploy`, you need to set up **the remote Maven repository** details in your project POM file, under
the `distributionManagement` section, as follows.

```xml
[...]
<distributionManagement>
    <repository>
        <id>wso2-maven2-repository</id>
        <name>WSO2 Maven2 Repository</name>
        <url>scp://dist.wso2.org/home/httpd/dist.wso2.org/maven2/</url>
    </repository>
</distributionManagement>
[...]
```

In this example, Maven connects to the remote repository via `scp`. **Secure Copy** (**scp**) defines a way of securely transferring files between two nodes in a computer network, which is built on top of the popular SSH. 

To authenticate to the remote server, Maven provides **two ways**. One is based on the `username` and `password`. **The other one** is based on **SSH authentication keys**. To configure `username/password` credential against the Maven repository, we need to add the following `<server>` configuration element to `USER_HOME/.m2/settings.xml` under the `<servers>` parent element.

The value of the `id` element must carry the value of **the remote repository hostname**:

```xml
<server>
    <id>dist.wso2.org</id>
    <username>my_username</username>
    <password>my_password</password>
</server>
```

If the remote repository only supports **SSH authentication keys**, then we need to specify the location of the private key, as follows:

```xml
<server>
    <id>dist.wso2.org</id>
    <username>my_username</username>
    <privateKey>/path/to/private/key</privateKey>
</server>
```

The `deploy` goal of the `deploy` plugin does not have any configurations to be overridden at **the project level**.
