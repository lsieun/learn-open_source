#Deploying artifacts

To deploy artifacts into a Maven repository, we need to define a `<distributionManagement>` configuration element in **the application POM file** of the
root Maven module. 

At the time of writing this book, Maven supports SSH, SFTP, FTP, and file-based artifact deployment. Let's have a look at how file-based artifact deployment works.

## Deploying file-based artifacts

The following configuration will deploy the artifacts to the repository at `/Users/prabath/maven/deploy`. This is one of the easiest and the quickest way of building a Maven repository, but **this is not recommended for use** in **a large-scale development project**. Use the `mvn deploy` command to deploy the artifacts into the configured repository.

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.packt</groupId>
    <artifactId>jose</artifactId>
    <version>1.0.0</version>

    <distributionManagement>
        <repository>
            <id>local-file-repository</id>
            <name>Local File Repository</name>
            <url>file:///Users/prabath/maven/deploy</url>
        </repository>
    </distributionManagement>

    <dependencies>
        <dependency>
            <groupId>com.nimbusds</groupId>
            <artifactId>nimbus-jose-jwt</artifactId>
            <version>2.26</version>
        </dependency>
    </dependencies>
</project>
```

## Deploying SSH-based artifacts

### pom.xml配置服务器信息

The following configuration will deploy the artifacts to the repository at `USER_HOME/maven/deploy` of the server that has the IP address `192.168.1.4`:

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.packt</groupId>
    <artifactId>jose</artifactId>
    <version>1.0.0</version>

    <distributionManagement>
        <repository>
            <id>ssh-repository</id>
            <name>SSH Repository</name>
            <url>scpexe://192.168.1.4/maven/deploy</url>
        </repository>
    </distributionManagement>

    <build>
        <extensions>
            <extension>
                <groupId>org.apache.maven.wagon</groupId>
                <artifactId>wagon-ssh-external</artifactId>
                <version>1.0-beta-6</version>
            </extension>
        </extensions>
    </build>

    <dependencies>
        <dependency>
            <groupId>com.nimbusds</groupId>
            <artifactId>nimbus-jose-jwt</artifactId>
            <version>2.26</version>
        </dependency>
    </dependencies>
</project>
```

The `wagon-ssh-external` JAR file is provided as a **build extension** in the previous application POM file. **Extensions** provide **a list of artifacts** that have to be used during a Maven build. **These artifacts** will be included into **the build time class path**.

### settings.xml添加认证信息

To authenticate to the remote server, Maven provides **two ways**. **One** is based on the `username` and `password`. **The other one** is based on **SSH authentication keys**.

The following steps show how to configure `username/password` credentials against a Maven repository:

1. Add the following `<server>` configuration element to `USER_HOME/.m2/settings.xml` under the `<servers>` parent element. The value of the `id` element must carry the value of **the remote repository hostname**.

```xml
<server>
    <id>192.168.1.4</id>
    <username>my_username</username>
    <password>my_password</password>
</server>
```

The password in the previous configuration can be encrypted by following the approach defined in the Encrypting credentials in `settings.xml` section of this chapter.

2. If the remote repository only supports **SSH authentication keys**, then we need to specify **the location of the private key**, as follows:

```xml
<server>
    <id>192.168.1.4</id>
    <username>my_username</username>
    <privateKey>/path/to/private/key</privateKey>
</server>
```

## Deploying FTP-based artifacts

### pom.xml配置服务器信息

The following configuration will deploy the artifacts to the repository at `USER_HOME/maven/deploy` of the server having the IP address `192.168.1.4`:

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.packt</groupId>
    <artifactId>jose</artifactId>
    <version>1.0.0</version>

    <distributionManagement>
        <repository>
            <id>ftp-repository</id>
            <name>FTP Repository</name>
            <url>ftp://192.168.1.4/maven/deploy</url>
        </repository>
    </distributionManagement>

    <dependencies>
        <dependency>
            <groupId>com.nimbusds</groupId>
            <artifactId>nimbus-jose-jwt</artifactId>
            <version>2.26</version>
        </dependency>
    </dependencies>

    <build>
        <extensions>
            <extension>
                <groupId>org.apache.maven.wagon</groupId>
                <artifactId>wagon-ftp</artifactId>
                <version>1.0-beta-6</version>
            </extension>
        </extensions>
    </build>
</project>
```

### settings.xml添加认证信息

To authenticate to the remote FTP server, we need to add the following `<server>` configuration element to `USER_HOME/.m2/settings.xml` under the `<servers>` parent element. The value of the `id` element must carry the value of **the remote repository hostname**.

```xml
<server>
    <id>192.168.1.4</id>
    <username>my_username</username>
    <password>my_password</password>
</server>
```

The password in the previous configuration can be encrypted by following the
approach defined in the [Encrypting credentials in settings.xml](../conf/encrypt-credentials.md).




