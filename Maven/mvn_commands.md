# mvn commands

## Project Creation

```bash
mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

```bash
mvn archetype:generate -DarchetypeCatalog=internal
```

## View

```bash
mvn help:effective-pom  # to look at the default configurations of the super POM
```

## Dependency

```bash
mvn dependency:resolve
mvn dependency:sources  # 能够把当前项目依赖的lib包的源码包从服务器上down下来，不错的命令啊
```

## Build

```bash
mvn clean package -Dmaven.test.skip=true
```
