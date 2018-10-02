# Secured repositories

Maven repositories can be protected for legitimate access. If a given repository is protected with HTTP Basic authentication, the corresponding credentials should be defined under the `<server>` element of `MAVEN_HOME/conf/settings.xml`. 

```xml
<settings>
    <localRepository/>
    <interactiveMode/>
    <usePluginRegistry/>
    <offline/>
    <pluginGroups/>
    <servers/> <!-- 本文是讲这里 -->
    <mirrors/>
    <proxies/>
    <profiles/>
    <activeProfiles/>
</settings>
```

The value of the `<id>` element should match the value of the repository `id` element:

```xml
<server>
    <id>central</id>
    <username>my_username</username>
    <password>my_password</password>
</server>
```

If a given repository uses HTTP Basic authentication-based security, make sure that you talk to the server over Transport Layer Security (TLS). Plain HTTP will carry your credentials in cleartext. Read more about TLS from http://en.wikipedia.org/wiki/Transport_Layer_Security.











