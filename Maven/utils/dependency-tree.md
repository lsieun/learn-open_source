# Building dependency tree

If you find any issues with any dependencies in your Maven project, the first step is to build a dependency tree. This shows where each dependency comes from. To build the dependency tree, run the following command against your project POM file:

```bash
$ mvn dependency:tree
```

Output:

```txt
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] Building aegis 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-dependency-plugin:2.10:tree (default-cli) @ aegis ---
[INFO] lsieun:aegis:jar:1.0-SNAPSHOT
[INFO] +- org.springframework.boot:spring-boot-starter-web:jar:1.5.16.RELEASE:compile
[INFO] |  +- org.springframework.boot:spring-boot-starter:jar:1.5.16.RELEASE:compile
[INFO] |  |  +- org.springframework.boot:spring-boot:jar:1.5.16.RELEASE:compile
[INFO] |  |  +- org.springframework.boot:spring-boot-autoconfigure:jar:1.5.16.RELEASE:compile
[INFO] |  |  +- org.springframework.boot:spring-boot-starter-logging:jar:1.5.16.RELEASE:compile
[INFO] |  |  |  +- ch.qos.logback:logback-classic:jar:1.1.11:compile
[INFO] |  |  |  |  \- ch.qos.logback:logback-core:jar:1.1.11:compile
[INFO] |  |  |  +- org.slf4j:jcl-over-slf4j:jar:1.7.25:compile
[INFO] |  |  |  +- org.slf4j:jul-to-slf4j:jar:1.7.25:compile
[INFO] |  |  |  \- org.slf4j:log4j-over-slf4j:jar:1.7.25:compile
[INFO] |  |  \- org.yaml:snakeyaml:jar:1.17:runtime
[INFO] |  +- org.springframework.boot:spring-boot-starter-tomcat:jar:1.5.16.RELEASE:compile
[INFO] |  |  +- org.apache.tomcat.embed:tomcat-embed-core:jar:8.5.34:compile
[INFO] |  |  |  \- org.apache.tomcat:tomcat-annotations-api:jar:8.5.34:compile
[INFO] |  |  +- org.apache.tomcat.embed:tomcat-embed-el:jar:8.5.34:compile
[INFO] |  |  \- org.apache.tomcat.embed:tomcat-embed-websocket:jar:8.5.34:compile
[INFO] |  +- org.hibernate:hibernate-validator:jar:5.3.6.Final:compile
[INFO] |  |  +- javax.validation:validation-api:jar:1.1.0.Final:compile
[INFO] |  |  +- org.jboss.logging:jboss-logging:jar:3.3.2.Final:compile
[INFO] |  |  \- com.fasterxml:classmate:jar:1.3.4:compile
[INFO] |  +- com.fasterxml.jackson.core:jackson-databind:jar:2.8.11.2:compile
[INFO] |  |  +- com.fasterxml.jackson.core:jackson-annotations:jar:2.8.0:compile
[INFO] |  |  \- com.fasterxml.jackson.core:jackson-core:jar:2.8.11:compile
[INFO] |  +- org.springframework:spring-web:jar:4.3.19.RELEASE:compile
[INFO] |  |  +- org.springframework:spring-aop:jar:4.3.19.RELEASE:compile
[INFO] |  |  +- org.springframework:spring-beans:jar:4.3.19.RELEASE:compile
[INFO] |  |  \- org.springframework:spring-context:jar:4.3.19.RELEASE:compile
[INFO] |  \- org.springframework:spring-webmvc:jar:4.3.19.RELEASE:compile
[INFO] |     \- org.springframework:spring-expression:jar:4.3.19.RELEASE:compile
[INFO] +- org.springframework.boot:spring-boot-starter-thymeleaf:jar:1.5.16.RELEASE:compile
[INFO] |  +- org.thymeleaf:thymeleaf-spring4:jar:3.0.9.RELEASE:compile
[INFO] |  |  +- org.thymeleaf:thymeleaf:jar:3.0.9.RELEASE:compile
[INFO] |  |  |  +- org.attoparser:attoparser:jar:2.0.4.RELEASE:compile
[INFO] |  |  |  \- org.unbescape:unbescape:jar:1.1.5.RELEASE:compile
[INFO] |  |  \- org.slf4j:slf4j-api:jar:1.7.25:compile
[INFO] |  \- nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:jar:2.3.0:compile
[INFO] |     +- nz.net.ultraq.thymeleaf:thymeleaf-expression-processor:jar:1.1.3:compile
[INFO] |     \- org.codehaus.groovy:groovy:jar:2.4.15:compile
[INFO] \- org.springframework.boot:spring-boot-starter-test:jar:1.5.16.RELEASE:test
[INFO]    +- org.springframework.boot:spring-boot-test:jar:1.5.16.RELEASE:test
[INFO]    +- org.springframework.boot:spring-boot-test-autoconfigure:jar:1.5.16.RELEASE:test
[INFO]    +- com.jayway.jsonpath:json-path:jar:2.2.0:test
[INFO]    |  \- net.minidev:json-smart:jar:2.2.1:test
[INFO]    |     \- net.minidev:accessors-smart:jar:1.1:test
[INFO]    |        \- org.ow2.asm:asm:jar:5.0.3:test
[INFO]    +- junit:junit:jar:4.12:test
[INFO]    +- org.assertj:assertj-core:jar:2.6.0:test
[INFO]    +- org.mockito:mockito-core:jar:1.10.19:test
[INFO]    |  \- org.objenesis:objenesis:jar:2.1:test
[INFO]    +- org.hamcrest:hamcrest-core:jar:1.3:test
[INFO]    +- org.hamcrest:hamcrest-library:jar:1.3:test
[INFO]    +- org.skyscreamer:jsonassert:jar:1.4.0:test
[INFO]    |  \- com.vaadin.external.google:android-json:jar:0.0.20131108.vaadin1:test
[INFO]    +- org.springframework:spring-core:jar:4.3.19.RELEASE:compile
[INFO]    \- org.springframework:spring-test:jar:4.3.19.RELEASE:test
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.180 s
[INFO] Finished at: 2018-09-29T15:07:31+08:00
[INFO] Final Memory: 22M/303M
[INFO] ------------------------------------------------------------------------
```