# Enabling Maven logging

Everything does not work fine all the time. The connectivity to an external repository or a server can fail due to many reasons. Maven debugging helps you to nail down the root cause in such a situation. To run Maven with **debug level logs** enabled, use the following command:

```bash
$ mvn clean install -X
```

Maven 3.1.0 and higher versions use the `SLF4J` logging API. The following logging configuration available at `MAVEN_HOME/conf/logging/simplelogger.properties` can be used to alter the default behavior of Maven logging:

```properties
org.slf4j.simpleLogger.defaultLogLevel=info
org.slf4j.simpleLogger.showDateTime=false
org.slf4j.simpleLogger.showThreadName=false
org.slf4j.simpleLogger.showLogName=false
org.slf4j.simpleLogger.logFile=System.out
org.slf4j.simpleLogger.levelInBrackets=true
org.slf4j.simpleLogger.log.Sisu=info
org.slf4j.simpleLogger.warnLevelString=WARNING
```

By default, Maven publishes logs to **the console** itself. The following configuration shows how to direct it to a file by editing the `simplelogger.properties` file:

```txt
org.slf4j.simpleLogger.logFile=/Users/[user_name]/maven.log
```
