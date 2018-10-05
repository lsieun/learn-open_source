# Plexus

Plexus provides an **Inversion of Control** (**IoC**) or a **Dependency Injection** (**DI**) framework similar to Spring. 

If you are new to the concept of Dependency Injection, it's highly recommended that you go through the article by Martin Fowler, **Inversion of Control Containers and the Dependency Injection pattern** at http://martinfowler.com/articles/injection.html.

Forget about Maven for a bit; let's see how to implement Dependency Injection with `Plexus` with the following steps.

Code [Link](code/plexus-di)

```txt
.
├── pom.xml
└── src/main
        └── java/com/lsieun/di/*.java
        └── resources/META-INF/plexus/components.xml
```

pom.xml

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.lsieun</groupId>
    <artifactId>plexus-di</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <dependencies>
        <dependency>
            <groupId>org.codehaus.plexus</groupId>
            <artifactId>plexus-container-default</artifactId>
            <version>1.5.5</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.1</version>
                <configuration>
                    <source>1.7</source>
                    <target>1.7</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```


First, we need to define our own Java interface for our business service as follows. There can be more than one implementation of this service:

```java
package com.lsieun.di;

public interface MessagingService {

    void sendMessage(String recipient, String message);
}
```


Let's write a couple of implementations for the previous interface. The `SMSMessagingService` class will text the message to the recipient while the `EmailMessagingService` class will email the message, shown as follows:

```java
package com.lsieun.di;

public class SMSMessagingService implements MessagingService {

    @Override
    public void sendMessage(String recipient, String message) {
        System.out.println("SMS sent to: " + recipient);
    }
}
```

```java
package com.lsieun.di;

public class EmailMessagingService implements MessagingService {

    @Override
    public void sendMessage(String recipient, String message) {
        System.out.println("Email sent to: " + recipient);
    }
}
```

Now we have multiple implementations of the same `MessagingService` interface. The Plexus DI framework lets you define each implementation in a configuration file (`components.xml`) and pick whatever you need in the runtime, as follows:

```xml
<component-set>
    <components>
        <component>
            <role>com.lsieun.di.MessagingService</role>
            <role-hint>sms</role-hint>
            <implementation>com.lsieun.di.SMSMessagingService</implementation>
        </component>
        <component>
            <role>com.lsieun.di.MessagingService</role>
            <role-hint>email</role-hint>
            <implementation>com.lsieun.di.EmailMessagingService</implementation>
        </component>
    </components>
</component-set>
```

The `role-hint` configuration element in the `components.xml` file helps to identify different implementations of the same interface uniquely. The fully qualified name of the interface is set as the value of the `role` element. In runtime, the lookup is done by both the `role` and `role-hint` elements. If there is only one implementation, then we do not need the `role-hint` element and the lookup can be done only by the value of `role`.

Let's write a client application that loads different implementations of the `MessagingService` interface via `Plexus`. Make sure that you have the previous components.xml file inside the `src/main/resources/META-INF/plexus` directory inside your client project. 

```java
package com.lsieun.di;

import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusContainer;

public class MessageSender {
    public static void main(String[] args) {
        PlexusContainer container = null;
        MessagingService msgService = null;

        try {
            container = new DefaultPlexusContainer();

            // send SMS
            msgService = (MessagingService) container.lookup(MessagingService.class, "sms");
            msgService.sendMessage("+9123456789", "Welcome to Plexus");

            // send Email
            msgService = (MessagingService) container.lookup(MessagingService.class, "email");
            msgService.sendMessage("abc@qq.com", "Welcome to Plexus");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (container != null) {
                container.dispose();
            }
        }
    }
}
```

In practice, each implementation of the service interface can come from different JAR files, and the client application does not need to have any dependency to the implementation classes at the build time. In runtime, the implementation classes will be injected into the system by the Plexus framework.

