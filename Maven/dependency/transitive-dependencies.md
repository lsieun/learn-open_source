# Transitive dependencies

## Transitive dependencies

The transitive dependency feature was introduced in Maven 2.0, which automatically identifies **the dependencies** of **your project dependencies** and get them all into the build path of your project.

> 是什么。  
> 如果项目A依赖于项目B，而项目B依赖于项目C；如果在项目A当中添加到项目B的依赖，那么项目C也会被加入到项目A的依赖当中，这就是Transitive dependencies。

**Transitive dependencies** can cause some pain too if not used with care.

> 辩证的看待它。辩证思维，简单来讲就是，要看清楚事物的两面性，而不是简单的认为事物非好即坏，非黑即白。  
> 从两方面来看待Transitive dependencies，一方面它提供了许多的便利，另一方面，使用不当，也会带来许多令人痛苦的事情。

To avoid such a nightmare, you need to follow a simple **rule of thumb**. If you have any `import` statements in a Java class, you need to make sure that the dependency `JAR` file corresponding to this is being added to the project POM file.


> Rule of thumb，在[wiki](https://en.wikipedia.org/wiki/Rule_of_thumb)当中是这么解释的：**rule of thumb** refers to a principle with broad application that is not intended to be strictly accurate or reliable for every situation. It refers to an easily learned and easily applied procedure or standard, based on practical experience rather than theory.   
> rule of thumb，就是一种规则，具有广泛的适用性，但是又不能保证绝对的正确，因此理解为“经验法则”较为合适。

> 对于Transitive dependencies带来的问题，如何解决呢？  
> 下面介绍maven dependency plugin来协助解决问题。  

The Maven `dependency` plugin helps you to find out such inconsistencies in your Maven module. Run the following command and observe its output;

```bash
$ mvn dependency:analyze
```

The **Maven dependency plugin** has several goals to find out inconsistencies and possible loopholes in your dependency management. For more details on this, refer to http://maven.apache.org/plugins/maven-dependency-plugin/.

## Dependency mediation

```txt
mediation
US[ˌmɪdɪˈeɪʃ(ə)n]
n.调解
```

**Dependency mediation** - this **determines what version of an artifact will be chosen** when multiple versions are encountered as dependencies. 

> 这里主要是讲Transitive dependencies的“传递规则”。

Maven picks the "**nearest definition**". That is, it uses the version of the closest dependency to your project in the tree of dependencies.  "**nearest definition**" means that the version used will be the closest one to your project in the tree of dependencies. 

> 讲述“传递规则”是怎么样的。

For example, if dependencies for A, B, and C are defined as A -> B -> C -> D 2.0 and A -> E -> D 1.0, then D 1.0 will be used when building A because the path from A to D through E is shorter. You could explicitly add a dependency to D 2.0 in A to force the use of D 2.0.

> 举例说明之。

You can always guarantee a version by **declaring it explicitly in your project's POM**. Note that if two dependency versions are at the same depth in the dependency tree, the first declaration wins.

> 显式声明。

## Dependency exclusion

**Dependency exclusion** helps avoid getting a selected set of **transitive dependencies**.

> Dependency exclusion是为了解决transitive dependency的“传递规则”中造成的问题。

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.packt</groupId>
    <artifactId>jose.war</artifactId>
    <version>1.0.0</version>
    <version>war</version>

    <dependencies>
        <dependency>
            <groupId>com.packt</groupId>
            <artifactId>jose</artifactId>
            <version>1.0.0</version>
        </dependency>
        <dependency>
            <groupId>com.packt</groupId>
            <artifactId>jose.ext</artifactId>
            <version>1.0.0</version>
            <!-- 注意：这里使用了exclusions -->
            <exclusions>
                <exclusion>
                    <groupId>net.minidev</groupId>
                    <artifactId>json-smart</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    </dependencies>
</project>
```
