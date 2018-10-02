# Proxy authentication


During a Maven build, you need to connect to external repositories outside your firewall. In a tight and secured environment, any outbound connection has to go through an internal proxy server. 

```xml
<settings>
    <localRepository/>
    <interactiveMode/>
    <usePluginRegistry/>
    <offline/>
    <pluginGroups/>
    <servers/>
    <mirrors/>
    <proxies/> <!-- 本文是讲这里 -->
    <profiles/>
    <activeProfiles/>
</settings>
```

The following configuration in `MAVEN_HOME/conf/settings.xml` shows how to connect to an external repository via a secured proxy server:

```xml
<proxy>
    <id>internal_proxy</id>
    <active>true</active>
    <protocol>http</protocol>
    <username>proxyuser</username>
    <password>proxypass</password>
    <host>proxy.host.net</host>
    <port>80</port>
    <nonProxyHosts>local.net|some.host.com</nonProxyHosts>
</proxy>
```

配置解析：

- The `<proxy>` child element must be defined under the `<proxies>` element, with the
appropriate configuration. 

- There can be **multiple proxy elements**, but only the first `<proxy>` element where the value is `<active>` will be picked by Maven. 

- If you also have a corporate Maven repository deployed behind a firewall, then the corresponding hostname should be defined under the `<nonProxyHosts>` element. When Maven talks to this repository, it won't go through the proxy.



