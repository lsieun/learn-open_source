# mvn commands

## 创建maven项目

```bash
mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

## View

```bash
mvn help:effective-pom  # to look at the default configurations of the super POM
```

```bash
mvn dependency:sources  # 能够把当前项目依赖的lib包的源码包从服务器上down下来，不错的命令啊
```