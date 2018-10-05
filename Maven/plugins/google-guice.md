# Google Guice

Google Guice is a lightweight DI framework that has support for **JSR 330**. Guice was initially developed by Google under the leadership of Bob Lee. He currently works
as the CTO of Square and was the lead of JSR 330.

Let's rewrite the same example we did with Plexus in Guice, to be JSR 330 compliant, as follows.

```txt
.
├── pom.xml
└── src
    └── main
        └── java
            └── com
                └── lsieun
                    └── di
                        ├── MessagingService.java
                        ├── SMSMessagingService.java
                        ├── EmailMessagingService.java
                        ├── GuiceMessageSender.java
                        ├── GuiceInjector.java
                        └── GuiceClientApplication.java
```

pom.xml

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.lsieun</groupId>
    <artifactId>guice-di</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <dependencies>
        <dependency>
            <groupId>com.google.inject</groupId>
            <artifactId>guice</artifactId>
            <version>4.0</version>
        </dependency>
        <dependency>
            <groupId>javax.inject</groupId>
            <artifactId>javax.inject</artifactId>
            <version>1</version>
        </dependency>
        <dependency>
            <groupId>aopalliance</groupId>
            <artifactId>aopalliance</artifactId>
            <version>1.0</version>
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

First, we need to define our own Java interface for our business service as follows. There can be more than one implementations of this service:

```java
package com.lsieun.di;

public interface MessagingService {

    void sendMessage(String recipient, String message);
}
```


Let's write couple of implementations for the previous interface. The `SMSMessagingService` class will text the message to the `recipient`, while the `EmailMessagingService` class will email the message, shown as follows:

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

Now, we need to write a `GuiceMessageSender` class, which will dynamically pick the `MessagingService` implementation to send the message, shown as follows. The Guice framework will inject the implementation class instance into the method that has the Inject annotation:

```java
package com.lsieun.di;

import javax.inject.Inject;

public class GuiceMessageSender {
    private MessagingService messagingService;

    @Inject
    public void setService(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    public void sendMessage(String recipient, String message) {
        messagingService.sendMessage(recipient, message);
    }
}
```


Now, we need to write a class extending the `AbstractModule` class of the `com.google.inject` package, which will bind an implementation class to the interface, as follows:

```java
package com.lsieun.di;

import com.google.inject.AbstractModule;

public class GuiceInjector extends AbstractModule {

    @Override
    protected void configure() {
        bind(MessagingService.class).to(SMSMessagingService.class);
    }
}
```


Finally, the `GuiceClientApplication` class will send the message using an instance of the `GuiceMessageSender` class, as follows. 

```java
package com.lsieun.di;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceClientApplication {
    public static void main(String[] args) {
        Injector injector;
        GuiceMessageSender messageSender;

        injector = Guice.createInjector(new GuiceInjector());
        messageSender = injector.getInstance(GuiceMessageSender.class);
        messageSender.sendMessage("+9123456789", "Welcome to Guice");
    }
}
```
