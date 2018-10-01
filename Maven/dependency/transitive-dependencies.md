# Transitive dependencies

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





