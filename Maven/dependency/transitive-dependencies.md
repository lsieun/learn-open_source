# Transitive dependencies

The transitive dependency feature was introduced in Maven 2.0, which automatically identifies **the dependencies** of **your project dependencies** and get them all into the build path of your project.

Transitive dependencies can cause some pain too if not used with care.

To avoid such a nightmare, you need to follow a simple rule of thumb. If you have any `import` statements in a Java class, you need to make sure that the dependency `JAR` file corresponding to this is being added to the project POM file.

The Maven `dependency` plugin helps you to find out such inconsistencies in your Maven module. Run the following command and observe its output;

```bash
$ mvn dependency:analyze
```
